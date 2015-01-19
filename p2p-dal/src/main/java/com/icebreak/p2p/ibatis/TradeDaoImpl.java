package com.icebreak.p2p.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.TradeDao;
import com.icebreak.p2p.dataobject.AgentInvestorTrade;
import com.icebreak.p2p.dataobject.AgentLoanerTrade;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeFlowCode;
import com.icebreak.p2p.util.DateUtil;

public class TradeDaoImpl extends SqlMapClientDaoSupport implements TradeDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}
	
	@Override
	public Trade getByTradeId(long id) {
		return (Trade) getSqlMapClientTemplate().queryForObject("TRADE-GETBYTRADEID", id);
	}
	
	@Override
	public Trade getByTradeCode(String code) {
		return (Trade) getSqlMapClientTemplate().queryForObject("TRADE-GETBYTRADECODE", code);
	}
	
	@Override
	public void addTrade(Trade trade) {
		getSqlMapClientTemplate().insert("TRADE-ADDTRADE", trade);
		trade.setId(identityObtainer.getPrimaryKey());
	}
	
	@Override
	public void modifyTradeCode(long tradeId, String tradeCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("tradeCode", tradeCode);
		getSqlMapClientTemplate().update("TRADE-MODIFYTRADECODE", params);
	}
	
	@Override
	public Trade getByDemandId(long id) {
		return (Trade) getSqlMapClientTemplate().queryForObject("TRADE-GETBYDEMANDID", id);
	}
	
	@Override
	public int modifyStatus(long tradeId, short status, Date effectiveDateTime, Date expireDateTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("status", status);
		params.put("effectiveDateTime", effectiveDateTime);
		params.put("expireDateTime", expireDateTime);
		return getSqlMapClientTemplate().update("TRADE-UPDATESTATUS-BY-FULL", params);
	}

	public int modifyStatus(long tradeId, short status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("status", status);
		return getSqlMapClientTemplate().update("TRADE-UPDATESTATUS", params);
	}
	
	@Override
	public int addLoanedAmount(long tradeId, long amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("amount", amount);
		int count = getSqlMapClientTemplate().update("TRADE-ADDLOANEDAMOUNT", params);
		if (count < 1) {
			throw new RuntimeException("修改已借款金额失败，交易ID：" + tradeId + "， 可能是不存在该交易...");
		} else {
			return count;
		}
	}
	
	@Override
	public int modifyFinishDate(long tradeId, Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("date", date);
		return getSqlMapClientTemplate().update("TRADE-MODIFYFINISHDATE", params);
	}
	
	@Override
	public long queryCountByCondition(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("RM-TRADE-QUERY_COUNT_BY_CONDITION",
			condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Trade> queryListByCondition(Map<String, Object> condition)
																			throws DataAccessException {
		return getSqlMapClientTemplate()
			.queryForList("RM-TRADE-QUERY_LIST_BY_CONDITION", condition);
	}
	
	@Override
	public List<Trade> queryByStatus(int status) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("RM-TRADE-BY_STATUS", status);
	}
	
	@Override
	public void addEffectiveDateTime(Long tradeId, Date expireDateTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		String effectiveDateTime = DateUtil.simpleFormatYmdhms(new Date());
		params.put("tradeId", tradeId);
		params.put("effectiveDateTime", effectiveDateTime);
		params.put("expireDateTime", expireDateTime);
		getSqlMapClientTemplate().update("TRADE-CREATEEFFECTIVEDATE", params);
	}
	
	@Override
	public long queryCountByConditionForJob(Map<String, Object> condition) {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"RM-TRADE-QUERY_COUNT_BY_CONDITION_FORJOB", condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Trade> queryListByConditionForJob(Map<String, Object> condition) {
		return getSqlMapClientTemplate().queryForList("RM-TRADE-QUERY_LIST_BY_CONDITION_FORJOB",
			condition);
	}
	
	@Override
	public void updateTradeDetailForOrg(int roleId, long tradeId, double amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("tradeId", tradeId);
		params.put("amount", amount);
		int count = getSqlMapClientTemplate().update("TRADE-ADDAMOUNTTOORGS", params);
		if (count < 1) {
			throw new RuntimeException("修改交易金额失败，交易ID：" + tradeId + "， 可能是不存在该交易...");
		}
	}
	
	@Override
	public long queryAgentInvestorCountByCondition(Map<String, Object> condition) {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"RM-AGENT-TRADE-QUERY_COUNT_BY_CONDITION", condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AgentInvestorTrade> queryAgentInvestorListByCondition(Map<String, Object> condition) {
		
		return getSqlMapClientTemplate().queryForList("RM-AGENT-TRADE-QUERY_LIST_BY_CONDITION",
			condition);
	}
	
	@Override
	public long queryAgencyLoanerCountByCondition(Map<String, Object> condition) {
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"RM-AGENT-LOAN-TRADE-QUERY_COUNT_BY_CONDITION", condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AgentLoanerTrade> queryAgencyLoanerListByCondition(Map<String, Object> condition) {
		
		return getSqlMapClientTemplate().queryForList(
			"RM-AGENT-LOAN-TRADE-QUERY_LIST_BY_CONDITION", condition);
	}
	
	@Override
	public long updateIsNotifiedLoaner(long tradeId, String identifier) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("isNotifiedLoaner", identifier);
		return getSqlMapClientTemplate().update("TRADE-UPDATE-ISNOTIFIED-LOANER", params);
	}
	
	@Override
	public Trade getByTradeIdWithRowLock(long id) {
		return (Trade) getSqlMapClientTemplate().queryForObject("TRADE-GETBYTRADEID-WITHROWLOCK",
			id);
	}
	
	@Override
	public long addRechargeFlow(RechargeFlow rechargeFlow) {

		return (Long) getSqlMapClientTemplate().insert("RECHARGEFLOW-INSERT", rechargeFlow);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RechargeFlow> getFlowList(Map<String, Object> condition) {

		List<RechargeFlow> queryForList = null;
		try {
			queryForList = getSqlMapClientTemplate().queryForList("RECHARGEFLOW-SELECT", condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return queryForList;
	}
	
	@Override
	public RechargeFlow getFlowByBizNo(String outBizNo) {
		return (RechargeFlow) getSqlMapClientTemplate().queryForObject(
			"RECHARGEFLOW-SELECT-OUTBIZNO", outBizNo);
	}
	
	@Override
	public int updateStatus(RechargeFlow rechargeFlow) {
		return getSqlMapClientTemplate().update("RECHARGEFLOW-UPDATE", rechargeFlow);
	}
	
	@Override
	public double getUserAmount(Map<String, Object> condtion) {
		double returnAmount = 0.0;
		Double amount = (Double) getSqlMapClientTemplate().queryForObject("SELECT-SUM-AMOUNT",
			condtion);
		if (amount != null) {
			returnAmount = amount;
		}
		return returnAmount;
	}
	
	@Override
	public long getFlowCount(Map<String, Object> condition) {

		try {
			return (Long) getSqlMapClientTemplate().queryForObject("RECHARGEFLOW-COUNT", condition);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long countInvestedTransactions(long tradeId) {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"TRADEDETAIL-COUNT-INVEST-TRANSACTIONS", tradeId);
	}
	
	@Override
	public void addTradeFlowCode(TradeFlowCode tradeFlow) {
		getSqlMapClientTemplate().insert("TRADE-FLOW-CODE-INSERT", tradeFlow);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeFlowCode> getTradeFlowCodes(long tradeId) {
		return getSqlMapClientTemplate().queryForList("TRADE-FLOW-CODE-GETBYTRADEID", tradeId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeFlowCode> getListTradeFlowCode(Map<String, Object> conditions) {

		return getSqlMapClientTemplate().queryForList("TRADE-FLOW-CODE-BYCONDITIONS", conditions);
	}
	
	@Override
	public TradeFlowCode getTradeFlowCodeByDetailId(long detailId) {
		@SuppressWarnings("unchecked")
		List<TradeFlowCode> codes = getSqlMapClientTemplate().queryForList(
			"TRADE-FLOW-CODE-GETBYTRADEDETAILID", detailId);
		if (codes != null && codes.size() > 0) {
			return codes.get(0);
		}
		return null;
	}
	
	@Override
	public int updateTradeAmount(long id, long amount) {

		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tradeId", id);
		conditions.put("amount", amount);
		return getSqlMapClientTemplate().update("TRADE-UPDATEAMOUNT", conditions);
	}
	
	@Override
	public int executeTradeExpireDateUpdate(Date expireDate, long tradeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("expireDate", expireDate);
		return getSqlMapClientTemplate().update("TRADE-UPDATE-TRADE-EXPIRE-DATE", params);
	}
	
	@Override
	public int updateTradeFlowCode(TradeFlowCode tradeFlow) {
		if (tradeFlow == null) {
			throw new IllegalArgumentException("Can't update by a null data object.");
		}
		return getSqlMapClientTemplate().update("TRADE-FLOW-CODE-UPDATE", tradeFlow);
		
	}
	
	@Override
	public long queryAllAmount(Map<String, Object> condition) {

		return (Long) getSqlMapClientTemplate().queryForObject("RM-AGENT-TRADE-QUERY_ALLAMOUNT",
			condition);
	}
	
	@Override
	public long getAllLoandAmount(Map<String, Object> condition) {

		return (Long) getSqlMapClientTemplate().queryForObject(
			"RM-TRADE-QUERY_ALLLOANEDAMOUNT_BY_CONDITION", condition);
	}
	
	@Override
	public void deleteTrade(long tradeId) {
		getSqlMapClientTemplate().delete("TRADE-DELETEBYTRADEID", tradeId);
	}
	
	@Override
	public void deleteTradeFlowCode(long loanDetailId) {
		getSqlMapClientTemplate().delete("TRADE-FLOW-CODE-DELETEBYTRADEDETAILID", loanDetailId);
	}
	
	@Override
	public Long sumPaidDivisionAmount(Long userId) {

		return (Long) getSqlMapClientTemplate().queryForObject(
			"DIVISIONDETAIL-SUMPAIDAMOUNTBYUSERID", userId);
	}
	
	@Override
	public long countAmountByParams(Map<String, Object> params) {
		
		return (Long) getSqlMapClientTemplate().queryForObject("TRADE-COUNTAMOUNTBYPARAMS", params);
	}
	
	@Override
	public long countInterestAmountByParams(Map<String, Object> params) {

		Long value = (Long) getSqlMapClientTemplate().queryForObject(
			"TRADEDETAIL-COUNTINTERESTAMOUNTBYPARAMS", params);
		return value == null ? 0 : value;
	}
	
	@Override
	public long countInvestTimesByParams(Map<String, Object> condition) {

		return (Long) getSqlMapClientTemplate().queryForObject("RM-AGENT-TRADE-QUERY_INVEST_TIMES",
			condition);
	}
}
