package com.icebreak.p2p.trade.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.BaseAutowiredService;
import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.trade.TradeDetailService;
@Service
public class TradeDetailServiceImpl extends BaseAutowiredService implements TradeDetailService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TradeDetailDao tradeDetailDao;
	@Override
	public long sumAmountByCondition(Map<String, Object> params) {
		return tradeDetailDao.sumAmountByCondition(params);
	}
	@Override
	public Map<String, Object> createAmounMap(long id) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		//投资人金额
		Map<String, Object> paramsInvestor = new HashMap<String,Object>(); 
		paramsInvestor.put("tradeId", id);
		paramsInvestor.put("roleId", 12);
		paramsInvestor.put("profitType", 0);
		paramsInvestor.put("transferPhase", "repay");
		long payInvestorAmount = sumAmountByCondition(paramsInvestor);
		result.put("payInvestorAmount", payInvestorAmount);
		//经纪人赠送投资人金额
		Map<String, Object> paramsInvestorSend = new HashMap<String,Object>(); 
		paramsInvestorSend.put("tradeId", id);
		paramsInvestorSend.put("roleId", 12);
		paramsInvestorSend.put("profitType", 1);
		paramsInvestorSend.put("transferPhase", "invest");
		long payInvestorAmountSend = sumAmountByCondition(paramsInvestorSend);
		result.put("payInvestorAmountSend", payInvestorAmountSend);
		//经纪人
		Map<String, Object> paramBroker = new HashMap<String,Object>(); 
		paramBroker.put("tradeId", id);
		paramBroker.put("roleId", 11);
		paramBroker.put("profitType", 0);
		paramBroker.put("transferPhase", "invest");
		long payBrokerAmount = sumAmountByCondition(paramBroker);
		result.put("payBrokerAmount", payBrokerAmount);
		//营销机构
		Map<String, Object> paramMarketting = new HashMap<String,Object>(); 
		paramMarketting.put("tradeId", id);
		paramMarketting.put("roleId", 10);
		paramMarketting.put("profitType", 0);
		paramMarketting.put("transferPhase", "invest");
		long payMarkettingAmount = sumAmountByCondition(paramMarketting);
		result.put("payMarkettingAmount", payMarkettingAmount);
		//担保机构
		Map<String, Object> paramGuarantee = new HashMap<String,Object>(); 
		paramGuarantee.put("tradeId", id);
		paramGuarantee.put("roleId", 8);
		paramGuarantee.put("profitType", 0);
		paramGuarantee.put("transferPhase", "invest");
		long payGuaranteeAmount = sumAmountByCondition(paramGuarantee);
		result.put("payGuaranteeAmount", payGuaranteeAmount);
		//平台
		Map<String, Object> paramPlateform = new HashMap<String,Object>(); 
		paramPlateform.put("tradeId", id);
		paramPlateform.put("roleId", 7);
		paramPlateform.put("profitType", 0);
		paramPlateform.put("transferPhase", "invest");
		long payPlateformAmount = sumAmountByCondition(paramPlateform);
		result.put("payPlateformAmount", payPlateformAmount);
		long totalPayAmount = payInvestorAmount + payInvestorAmountSend+payBrokerAmount+payMarkettingAmount+payGuaranteeAmount+payPlateformAmount;
		result.put("totalPayAmount", totalPayAmount);
		
		return result;
	}
}
