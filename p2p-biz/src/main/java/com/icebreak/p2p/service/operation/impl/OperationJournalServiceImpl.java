package com.icebreak.p2p.service.operation.impl;

import com.icebreak.p2p.dal.daointerface.OperationJournalDAO;
import com.icebreak.p2p.dal.dataobject.OperationJournalDO;
import com.icebreak.p2p.service.convert.OperationJournalConvertor;
import com.icebreak.p2p.ws.base.PageComponent;
import com.icebreak.p2p.ws.info.OperationJournalInfo;
import com.icebreak.p2p.ws.order.OperationJournalAddOrder;
import com.icebreak.p2p.ws.result.OperationJournalServiceResult;
import com.icebreak.p2p.ws.service.OperationJournalService;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.p2p.ws.service.query.order.OperationJournalQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("operationJournalService")
public class OperationJournalServiceImpl  implements OperationJournalService {
	private static final Logger logger = LoggerFactory.getLogger(OperationJournalServiceImpl.class);
	
	@Autowired
	OperationJournalDAO operationJournalDAO;
	

	@Override
	public OperationJournalServiceResult addOperationJournalInfo(	OperationJournalAddOrder operationJournalAddOrder) {
		logger.info("进入OperationJournalServiceImpl的addOperationJournalInfo方法!添加一条日志信息,入参: "
					+ operationJournalAddOrder);
		OperationJournalServiceResult operationJournalServiceResult = new OperationJournalServiceResult();
		try {
			//参数校验
			operationJournalAddOrder.check();
			// 参数设置
			OperationJournalDO operationJournalDO = new OperationJournalDO();
			OperationJournalConvertor.convert(operationJournalAddOrder, operationJournalDO);
			operationJournalDO.setRawAddTime(new Date());
			// 插入
			long identity = operationJournalDAO.insert(operationJournalDO);
			// 设置返回结果
			operationJournalDO.setIdentity(identity);
			OperationJournalInfo operationJournalInfo = new OperationJournalInfo();
			OperationJournalConvertor.convert(operationJournalDO, operationJournalInfo);
			operationJournalServiceResult.setOperationJournalInfo(operationJournalInfo);
			setResultInfo(operationJournalServiceResult, "添加一条操作日志信息成功!",
				ResultEnum.EXECUTE_SUCCESS, true);
			logger.info("添加一条操作日志信息成功!resultCode=[ "
						+ operationJournalServiceResult.getResultEnum() + " ]");
		} catch (IllegalArgumentException e) {
			setResultInfo(operationJournalServiceResult, e.getMessage(),
				ResultEnum.INCOMPLETE_REQ_PARAM, false);
			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：", e);
		} catch (DataAccessException e) {
			setResultInfo(operationJournalServiceResult,
				ResultEnum.DATABASE_EXCEPTION.getMessage(), ResultEnum.DATABASE_EXCEPTION,
				false);
			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：", e);
		} catch (Exception e) {
			setResultInfo(operationJournalServiceResult,
				ResultEnum.UN_KNOWN_EXCEPTION.getMessage(), ResultEnum.UN_KNOWN_EXCEPTION,
				false);
			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：", e);
		}
		return operationJournalServiceResult;
	}
	
	protected void setResultInfo(OperationJournalServiceResult operationJournalResultBase,
									String message, ResultEnum resultCode, boolean success) {
		if (operationJournalResultBase != null) {
			operationJournalResultBase.setMessage(message);
			operationJournalResultBase.setResultEnum(resultCode);
			operationJournalResultBase.setSuccess(success);
		}
	}

    @Override
    public QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(OperationJournalQueryOrder operationJournalQueryOrder) {
        QueryBaseBatchResult<OperationJournalInfo> batchResult = new QueryBaseBatchResult<OperationJournalInfo>();
        try {
            operationJournalQueryOrder.check();
            List<OperationJournalInfo> pageList = new ArrayList<OperationJournalInfo>(
                    (int) operationJournalQueryOrder.getPageSize());
            OperationJournalDO operationJournalDO = new OperationJournalDO();
            OperationJournalConvertor.convert(operationJournalQueryOrder,operationJournalDO);
            long totalCount = operationJournalDAO.queryOperationJournalPageListCount(operationJournalDO, operationJournalQueryOrder.getOperatorTimeStart(), operationJournalQueryOrder.getOperatorTimeEnd());
            PageComponent component = new PageComponent(operationJournalQueryOrder, totalCount);
            List<OperationJournalDO> recordList = operationJournalDAO.queryOperationJournalPageList(operationJournalDO,operationJournalQueryOrder.getOperatorTimeStart(),operationJournalQueryOrder.getOperatorTimeEnd(), operationJournalQueryOrder.getLimitStart(),
                    operationJournalQueryOrder.getPageSize());
            if(ListUtil.isNotEmpty(recordList)){
                for (OperationJournalDO item : recordList) {
                    OperationJournalInfo info = new OperationJournalInfo();
                    OperationJournalConvertor.convert(item,info);
                    pageList.add(info);
                }
            }
            batchResult.initPageParam(component);
            batchResult.setSuccess(true);
            batchResult.setPageList(pageList);
        } catch (IllegalArgumentException e) {
            batchResult.setSuccess(false);
            batchResult.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
            batchResult.setMessage(e.getMessage());
        } catch (Exception e) {
            batchResult.setSuccess(false);
            batchResult.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
            logger.error(e.getLocalizedMessage(), e);
        }

        return batchResult;
    }
}
