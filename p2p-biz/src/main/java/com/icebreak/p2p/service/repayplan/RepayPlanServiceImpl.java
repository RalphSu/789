package com.icebreak.p2p.service.repayplan;


import com.icebreak.p2p.dal.daointerface.RepayPlanDAO;
import com.icebreak.p2p.dal.dataobject.RepayPlanDO;
import com.icebreak.p2p.service.convert.RepayPlanConvertor;
import com.icebreak.p2p.ws.base.PageComponent;
import com.icebreak.p2p.ws.enums.RepayPlanStatusEnum;
import com.icebreak.p2p.ws.info.RepayPlanInfo;
import com.icebreak.p2p.ws.service.RepayPlanService;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.p2p.ws.service.query.order.RepayPlanQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("repayPlanService")
public class RepayPlanServiceImpl  implements RepayPlanService {
    private static final Logger logger = LoggerFactory.getLogger(RepayPlanServiceImpl.class);
    @Autowired
    RepayPlanDAO repayPlanDAO ;


    @Override
    public QueryBaseBatchResult<RepayPlanInfo> queryRepayPlanInfoGuarantee(RepayPlanQueryOrder repayPlanQueryOrder) {
        QueryBaseBatchResult<RepayPlanInfo> batchResult = new QueryBaseBatchResult<RepayPlanInfo>();
        try {
            repayPlanQueryOrder.check();
            List<RepayPlanInfo> pageList = new ArrayList<RepayPlanInfo>(
                    (int) repayPlanQueryOrder.getPageSize());
            RepayPlanDO repayPlanDO = new RepayPlanDO();
            RepayPlanConvertor.convert(repayPlanQueryOrder, repayPlanDO);
            long totalCount = repayPlanDAO.findByConditionCountGuarantee(repayPlanDO, repayPlanQueryOrder.getStartDate(), repayPlanQueryOrder.getEndDate(),repayPlanQueryOrder.getStartActualDate(),repayPlanQueryOrder.getEndActualDate(),repayPlanQueryOrder.getStatusList(),
                    repayPlanQueryOrder.getGuaranteeId());
            PageComponent component = new PageComponent(repayPlanQueryOrder, totalCount);

            List<RepayPlanDO> recordList = repayPlanDAO.findByConditionGuarantee(repayPlanDO, repayPlanQueryOrder.getLimitStart(),
                    repayPlanQueryOrder.getPageSize(),repayPlanQueryOrder.getStartDate(), repayPlanQueryOrder.getEndDate(),repayPlanQueryOrder.getStartActualDate(),repayPlanQueryOrder.getEndActualDate(),repayPlanQueryOrder.getStatusList(),repayPlanQueryOrder.getGuaranteeId());
            if(ListUtil.isNotEmpty(recordList)){
                for (RepayPlanDO item : recordList) {
                    RepayPlanInfo info = new RepayPlanInfo();
                    RepayPlanConvertor.convert(item,info);
                    if( RepayPlanStatusEnum.NOTPAY.code().equals(info.getStatus())&&guaranteeCanPay(info)){
                        info.setCanPay("Y");
                    }
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

    @Override
    public QueryBaseBatchResult<RepayPlanInfo> queryRepayPlanInfo(RepayPlanQueryOrder repayPlanQueryOrder) {
        QueryBaseBatchResult<RepayPlanInfo> batchResult = new QueryBaseBatchResult<RepayPlanInfo>();
        try {
            repayPlanQueryOrder.check();
            List<RepayPlanInfo> pageList = new ArrayList<RepayPlanInfo>(
                    (int) repayPlanQueryOrder.getPageSize());
            RepayPlanDO repayPlanDO = new RepayPlanDO();
            RepayPlanConvertor.convert(repayPlanQueryOrder, repayPlanDO);
            long totalCount = repayPlanDAO.findByConditionCount(repayPlanDO, repayPlanQueryOrder.getStartDate(), repayPlanQueryOrder.getEndDate(),repayPlanQueryOrder.getStartActualDate(),repayPlanQueryOrder.getEndActualDate(),repayPlanQueryOrder.getStatusList());
            PageComponent component = new PageComponent(repayPlanQueryOrder, totalCount);

            List<RepayPlanDO> recordList = repayPlanDAO.findByCondition(repayPlanDO, repayPlanQueryOrder.getLimitStart(),
                    repayPlanQueryOrder.getPageSize(),repayPlanQueryOrder.getStartDate(), repayPlanQueryOrder.getEndDate(),repayPlanQueryOrder.getStartActualDate(),repayPlanQueryOrder.getEndActualDate(),repayPlanQueryOrder.getStatusList());
            if(ListUtil.isNotEmpty(recordList)){
                for (RepayPlanDO item : recordList) {
                    RepayPlanInfo info = new RepayPlanInfo();
                    RepayPlanConvertor.convert(item,info);
                    if( RepayPlanStatusEnum.NOTPAY.code().equals(info.getStatus())&&loanerCanPay(info)){
                        info.setCanPay("Y");
                    }
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
    
    @Override
    public RepayPlanInfo findByRepayPlanId(long repayPlanId){
    	
    	RepayPlanDO repayPlanDO = repayPlanDAO.findById(repayPlanId);
    	RepayPlanInfo info = new RepayPlanInfo();
        RepayPlanConvertor.convert(repayPlanDO,info);
    	return info;
    	
    }
    
    @Override
    public RepayPlanInfo findByRepayPlanIdwithrowLock(long repayPlanId){
    	
    	RepayPlanDO repayPlanDO = repayPlanDAO.findByIdwithrowLock(repayPlanId);
    	RepayPlanInfo info = new RepayPlanInfo();
        RepayPlanConvertor.convert(repayPlanDO,info);
    	return info;
    	
    }
    
    
    
    @Override
    public void updateStatus(long repayPlanId,String status){
		RepayPlanDO repayPlanDO = repayPlanDAO.findById(repayPlanId);
		if (repayPlanDO == null) {
			logger.info("未找到还款记录:" + repayPlanId);
		} else {
			repayPlanDO.setStatus(status);
			try {
				repayPlanDAO.update(repayPlanDO);
			} catch (DataAccessException e) {
                logger.info("更新还款记录状态失败:", e);
			}
		}
    }
    @Override
    public void updateActualRepayDate(long repayPlanId,Date actualRepayDate){
    	RepayPlanDO repayPlanDO = repayPlanDAO.findById(repayPlanId); 
    	if(repayPlanDO!=null){
    		repayPlanDO.setActualRepayDate(actualRepayDate);
        	repayPlanDAO.update(repayPlanDO);
    	}
    }

    /**
     * 借款人还款日前后七天，都可以还款
     * @param info
     * @return
     */
    public boolean loanerCanPay(RepayPlanInfo info){
        Date repayDate = info.getRepayDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(repayDate);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Long beforeSevenDay = calendar.getTimeInMillis();
        calendar.setTime(repayDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Long afterSevenDay = calendar.getTimeInMillis();
        Date nowDate = new Date();
        calendar.setTime(nowDate);

        Long nowDay = calendar.getTimeInMillis();
        if(nowDay>=beforeSevenDay && nowDay<= afterSevenDay){
            return true;
        }
        return false;
    }


    /**
     * 担保机构还款日，七天后，可以还款
     * @param info
     * @return
     */
    public boolean guaranteeCanPay(RepayPlanInfo info){
        Date repayDate = info.getRepayDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(repayDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Long afterSevenDay = calendar.getTimeInMillis();
        Date nowDate = new Date();
        calendar.setTime(nowDate);

        Long nowDay = calendar.getTimeInMillis();
        if( nowDay > afterSevenDay){
            return true;
        }
        return false;
    }
    
}
