package com.icebreak.p2p.trade.impl;

import com.icebreak.p2p.base.BaseAutowiredService;
import com.icebreak.p2p.daointerface.RechargeFlowDao;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.icebreak.p2p.trade.RechercheFlowOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("rechargeFlowService")
public class RechargeFlowServiceImpl extends  BaseAutowiredService implements RechargeFlowService{
	@Autowired
	private RechargeFlowDao rechargeFlowDao;
	@Override
	public void addRechargeFlow(RechargeFlow rechargeFlow) throws Exception{
		if(rechargeFlow==null){
			throw new Exception("can not find rechargeFlow");
		}else{
			rechargeFlowDao.addRechargeFlow(rechargeFlow);
		}
	}

	@Override
	public List<RechargeFlow> getFlow(String payType, int status,
			Date startTime, Date endTime) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("payType", payType);
		condition.put("status", status);
		condition.put("startTime", startTime);
		condition.put("endTime", endTime);
		return rechargeFlowDao.getFlowList(condition);
	}

	@Override
	public RechargeFlow queryByOutBizNo(String outBizNo){
		RechargeFlow params = new RechargeFlow();
		params.setOutBizNo(outBizNo);
		RechargeFlow rechargeFlow = rechargeFlowDao.getFlow(params);
		return rechargeFlow;
	}

	@Override
	public RechargeFlow queryByLocalNo(String localNo){
		RechargeFlow params = new RechargeFlow();
		params.setLocalNo(localNo);
		RechargeFlow rechargeFlow = rechargeFlowDao.getFlow(params);
		return rechargeFlow;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int updateStatus(RechargeFlow rechargeFlow){
		int status = 0;
		try{
			status = rechargeFlowDao.update(rechargeFlow);
		}catch(Exception e){
			logger.error("更新充值或提现流水异常",e);
		}
		return status;
	}
	
	@Override
	public double getUserSumAmount(RechercheFlowOrder rechercheFlowOrder){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("bankAccountNo", rechercheFlowOrder.getBankCardNo().trim());
		condition.put("payType", rechercheFlowOrder.getPayType().trim());
		condition.put("endTime", rechercheFlowOrder.getEndTime().trim());
		condition.put("startTime", rechercheFlowOrder.getStartTime());
		condition.put("status", rechercheFlowOrder.getStatus());
		condition.put("userId", rechercheFlowOrder.getUserId());
		return 0;
	}

	@Override
	public Page<RechargeFlow> getFlow(QueryTradeOrder queryTradeOrder,
			PageParam pageParam) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("payType", queryTradeOrder.getPayType());
		if(queryTradeOrder.getStatus() != null && queryTradeOrder.getStatus().size() >0){
			if(queryTradeOrder.getStatus().get(0) != ""){
				if(Integer.parseInt(queryTradeOrder.getStatus().get(0)) >=0){
					condition.put("status", queryTradeOrder.getStatus().get(0));
				}
			}
		}
		condition.put("startTime", queryTradeOrder.getStartDate());
		condition.put("endTime", queryTradeOrder.getEndDate());
		condition.put("accountId", queryTradeOrder.getAccountId());
		condition.put("userId", queryTradeOrder.getUserId());
		long totalSize = rechargeFlowDao.getFlowCount(condition);
		int start = PageParamUtil.startValue((int) totalSize,pageParam.getPageSize(), pageParam.getPageNo());
		condition.put("limitStart", start);
		condition.put("pageSize", pageParam.getPageSize());
		List<RechargeFlow> result = rechargeFlowDao.getFlowList(condition);
		Page<RechargeFlow> page = new Page<RechargeFlow>(start, totalSize,pageParam.getPageSize(), result);
		return page;
	}

}
