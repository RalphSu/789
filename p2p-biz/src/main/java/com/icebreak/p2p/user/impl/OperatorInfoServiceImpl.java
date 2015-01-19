package com.icebreak.p2p.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icebreak.p2p.daointerface.IOperatorInfoDao;
import com.icebreak.p2p.dataobject.OperatorInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.ws.enums.OperatorInfoEnum;
import com.icebreak.util.lang.util.StringUtil;

@Service
public class OperatorInfoServiceImpl implements IOperatorInfoService {
	@Autowired
	IOperatorInfoDao operatorInfoDao;
	
	@Transactional(rollbackFor = Throwable.class, value = "transactionManager")
	@Override
	public long addOperatorInfo(OperatorInfoDO info) {
		return operatorInfoDao.addOperatorInfo(info);
	}
	
	@Override
	public boolean isFromSameOrgan(String userBaseId1, String userBaseId2) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId1", userBaseId1);
		condition.put("userBaseId2", userBaseId2);
		return operatorInfoDao.isFromSameOrgan(condition);
	}
	
	@Override
	public List<OperatorInfoDO> queryOperatorsByProperties(Map<String, Object> conditions) {
		
		return operatorInfoDao.queryOperatorsByProperties(conditions);
	}
	
	@Override
	public Page<OperatorInfoDO> queryOperatorPage(QueryConditions queryConditions,
													PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("parentId", queryConditions.getUserId());
		if (StringUtil.isNotBlank(queryConditions.getUserName())) {
			condition.put("userName", queryConditions.getUserName());
		}
		if (StringUtil.isNotBlank(queryConditions.getRealName())) {
			condition.put("remark", queryConditions.getRealName());
		}
		long totalSize = operatorInfoDao.queryOperatorsByPropertiesCount(condition);
		condition.put(
			"limitStart",
			PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo()));
		condition.put("pageSize", pageParam.getPageSize());
		List<OperatorInfoDO> result = operatorInfoDao.queryOperatorsByProperties(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		return new Page<OperatorInfoDO>(start, totalSize, pageParam.getPageSize(), result);
	}
	
	@Override
	public OperatorInfoEnum updateOperatorInfo(OperatorInfoDO operatorInfo) {
		
		OperatorInfoEnum returnEnum = OperatorInfoEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", operatorInfo.getUserBaseId());
		condition.put("remark", operatorInfo.getRemark());
		condition.put("operatorType", operatorInfo.getOperatorType());
		
		int resultSet = operatorInfoDao.updateOperatorInfo(condition);
		if (resultSet > 0) {
			returnEnum = OperatorInfoEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
}
