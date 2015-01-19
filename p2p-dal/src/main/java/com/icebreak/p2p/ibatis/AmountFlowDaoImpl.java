package com.icebreak.p2p.ibatis;

import com.icebreak.p2p.daointerface.AmountFlowDao;
import com.icebreak.p2p.dataobject.AmountFlow;
import com.icebreak.p2p.dataobject.AmountFlowTrade;
import com.icebreak.p2p.util.CommonUtil;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmountFlowDaoImpl extends SqlMapClientDaoSupport implements
		AmountFlowDao {

	@Override
	public long addAmountFlow(AmountFlow amountFlow) {
		return (Long) getSqlMapClientTemplate().insert("AMOUNTFLOW-ADDAMOUNTFLOW", amountFlow);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmountFlow> getAmountFlowsByStatus(boolean status) {
		return (List<AmountFlow>)getSqlMapClientTemplate().queryForList("AMOUNTFLOW-GETBYSTATUS", status);
	}

	@Override
	public Long getTotalAmountFlow(long userId, Date start, Date end) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("start", start);
		params.put("end", end);
		return (Long)getSqlMapClientTemplate().queryForObject("AMOUNTFLOW-GETTOTALAMOUNTFLOW", params);
	}

	@Override
	public synchronized int  modifyStatus(long id, int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", status);
		return getSqlMapClientTemplate().update("AMOUNTFLOW-MODIFYSTATUS", params);
	}

	@Override
	public void addAmountFlowTrade(AmountFlowTrade amountFlowTrade) {

		getSqlMapClientTemplate().insert("AMOUNTFLOWTRADE-ADDAMOUNTFLOWTRADE", amountFlowTrade);
	}

	@Override
	public AmountFlow getAmountFlowByFlowId(long flowId) {

		return (AmountFlow) getSqlMapClientTemplate().queryForObject("AMOUNTFLOW-GETBYFLOWID", flowId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmountFlowTrade> getAmountFlowTradesByTradeId(long tradeId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		List<AmountFlowTrade> lists = (List<AmountFlowTrade>)getSqlMapClientTemplate().queryForList("AMOUNTFLOWTRADE-GETBYPARAMS", params);
		CommonUtil.clearMap(params);
		return lists;
	}

	@Override
	public long getAmountFlowTradesByParamsCount(Map<String, Object> conditions) {
	
		return (Long) getSqlMapClientTemplate().queryForObject("AMOUNTFLOW-GETAMTFLOWTRADESBYPARAMSCOUNT", conditions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmountFlowTrade> getAmountFlowTradesByParams(
			Map<String, Object> conditions) {
		
		return (List<AmountFlowTrade>)getSqlMapClientTemplate().queryForList("AMOUNTFLOW-GETAMTFLOWTRADESBYPARAMS", conditions);
	}

	@Override
	public AmountFlowTrade getAmountFlowTradeByFlowId(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amountFlowId", id);
		@SuppressWarnings("unchecked")
		List<AmountFlowTrade> lists = (List<AmountFlowTrade>)getSqlMapClientTemplate().queryForList("AMOUNTFLOWTRADE-GETBYPARAMS", params);
		CommonUtil.clearMap(params);
		if(lists !=null && lists.size() > 0){
			return lists.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int transFlowCountByTradeDetailIdType(long tradeDetailId, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeDetailId", tradeDetailId);
		params.put("type", type);
		int count = (Integer) getSqlMapClientTemplate().queryForObject("AMOUNTFLOW-TRANSFLOWCOUNTBYTRADEDETAILIDTYPE", params);
		CommonUtil.clearMap(params);
		return count;
	}

	@Override
	public String getTransPhaseByTradeDetailId(long id) {
		String transPhase =  (String) getSqlMapClientTemplate().queryForObject("AMOUNTFLOW-TRANSOHASEBYTRADEDETAILID", id);
		return transPhase;
	}
	
	@Override
	public int getRepayPeriodNOByTradeDetailId(long id) {
		int  periodNO =  (int) getSqlMapClientTemplate().queryForObject("AMOUNTFLOW-REPAYPERIODNO-BYTRADEDETAILID", id);
		return periodNO;
	}
	
	
	@Override
	public List<AmountFlow> queryPayTogetherForUpdate(long tradeId, int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("status", status);
		return (List<AmountFlow>)getSqlMapClientTemplate().queryForList("AMOUNTFLOW-queryPayTogetherForUpdate", params);
	}
	

	@Override
	public int update(String id, String flowCode, Boolean status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("flowCode", flowCode);
		params.put("status", status);
		return getSqlMapClientTemplate().update("AMOUNTFLOW-UPDATE", params);
	}
}
