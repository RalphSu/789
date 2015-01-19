package com.icebreak.p2p.ibatis;

import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.dataobject.InvestDetailDO;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.*;

public class TradeDetailDaoImpl extends SqlMapClientDaoSupport implements TradeDetailDao {
	
	@Override
	public long addTradeDetail(TradeDetail detail) {
		return (Long) getSqlMapClientTemplate().insert("TRADEDETAIL-ADDTRADEDETAIL", detail);
	}
	
	@Override
	public void invest(TradeDetail detail) {
		getSqlMapClientTemplate().insert("TRADEDETAIL-INVEST", detail);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeDetail> getByTradeIdAndRoles(long tradeId, String... roles) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		if (roles != null && roles.length > 0) {
			params.put("roles", Arrays.asList(roles));
		}
		return getSqlMapClientTemplate().queryForList("TRADEDETAIL-GETBYTRADEIDANDROLES", params);
	}
	
	@Override
	public String getYjfUserNameByUserId(long userId) {
		return (String) getSqlMapClientTemplate().queryForObject(
			"TRADEDETAIL-GETYJFUSERNAMEBYUSERID", userId);
	}
	
	@Override
	public long getAmount(long userId, String roleCode, short... status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("roleCode", roleCode);
		if (status != null && status.length > 0) {
			params.put("statuses", status);
		}
		return (Long) getSqlMapClientTemplate().queryForObject("TRADEDETAIL-GETAMOUNT", params);
	}
	
	@Override
	public int check(long tradeId, long userId, String role) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("userId", userId);
		params.put("role", role);
		return (Integer) getSqlMapClientTemplate().queryForObject("TRADEDETAIL-CHECK", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeQueryDetail> getTradeDetailByConditions(Map<String, Object> conditions) {
		return getSqlMapClientTemplate().queryForList("TRADEDETAIL-GETTRADEDETAILBYCONDITIONS",
			conditions);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeDetail> getInvestProfitTrade(long detailId) {

		return getSqlMapClientTemplate().queryForList("TRADEDETAIL-GETPRTDETAILBYINVESTDETAILID",
			detailId);
	}
	
	@Override
	public long getTradeDivisionDetailByConditionsCount(Map<String, Object> conditions) {
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"TRADEDETAIL-GETTRADEDIVISIONSCOUNTBYPARAMS", conditions);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeDetail> getTradeDivisionDetailByConditions(Map<String, Object> conditions) {
		return getSqlMapClientTemplate().queryForList("TRADEDETAIL-GETTRADEDIVISIONSBYPARAMS",
			conditions);
	}
	
	@Override
	public void deleteloanDetail(long loanDetailId) {
		getSqlMapClientTemplate().delete("TRADEDETAIL-DELETEBYTRADEDETAILID", loanDetailId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvestDetailDO> queryInvestDetail(Map<String, Object> condition) {
		return getSqlMapClientTemplate().queryForList("TRADEDETAIL-GETINVESTDETAILBYPARAMS",
			condition);
	}
	
	@Override
	public long sumAmountByCondition(Map<String, Object> params) {
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"TRADEDETAIL-GETTRADEAMOUNSUMBYPARAMS", params);
	}
	
	
  	@Override
	public void updateRepaydate(Long tradeDetailId, Date repayDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeDetailId", tradeDetailId);
		params.put("repayDate", repayDate);
		getSqlMapClientTemplate().update("INVESTDETAIL-UPDATEREPAYDATE", params);
	}
	@Override
  	public void updatetActualRepayDate(Long tradeDetailId, Date actualRepayDate){
  		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeDetailId", tradeDetailId);
		params.put("actualRepayDate", actualRepayDate);
		getSqlMapClientTemplate().update("INVESTDETAIL-UPDATE_ACTUALREPAYDATE", params);
  	}
	
	@Override
	public void updatetStatus(Long tradeDetailId, String  status){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeDetailId", tradeDetailId);
		params.put("status", status);
		getSqlMapClientTemplate().update("INVESTDETAIL_UPDATE_STATUS", params);
	}


    @Override
    public List<TradeQueryDetail> getCollectionByConditions(Map<String, Object> conditions) {
        return getSqlMapClientTemplate().queryForList("TRADEDETAIL-QUERYCOLLECTIONBYPARAMS",
                conditions);
    }

    @Override
    public long getCollectionByConditionsCount(Map<String, Object> conditions) {

        return (Long) getSqlMapClientTemplate().queryForObject(
                "TRADEDETAIL-QUERYCOLLECTIONCOUNTBYPARAMS", conditions);
    }

	@Override
    public TradeDetail getById(long tradeDetailId) {

        return (TradeDetail) getSqlMapClientTemplate().queryForObject(
                "TRADEDETAIL-getById", tradeDetailId);
    }
}
