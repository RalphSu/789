package com.icebreak.p2p.ibatis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.DivisionDetailDao;
import com.icebreak.p2p.dataobject.DivisionDetail;

public class DivisionDetailDaoImpl extends SqlMapClientDaoSupport implements
		DivisionDetailDao {

	@Override
	public void addDivisionDetail(DivisionDetail detail) {
		getSqlMapClientTemplate().insert("DIVISIONDETAIL-INSERT", detail);
	}

	@Override
	public synchronized int modifyStatus(long id, int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", status);
		return getSqlMapClientTemplate().update("DIVISIONDETAIL-UPDATESTATUS",
				params);
	}

	@Override
	public int getCount(long tradeId, long userId, boolean status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("status", status);
		params.put("userId", userId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"DIVISIONDETAIL-GETBYTRADEIDANDROLESANDSTSTUSCOUNT", params);
	}

	@Override
	public int modifyAmount(long tradeId, long userId, boolean status,
			long amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("status", status);
		params.put("userId", userId);
		params.put("amount", amount);
		return getSqlMapClientTemplate().update("DIVISIONDETAIL-MODIFYAMOUNT",
				params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DivisionDetail> getByTradeIdAndRoles(long tradeId,
			String... roles) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		if (roles != null && roles.length > 0) {
			params.put("roles", Arrays.asList(roles));
		}
		return (List<DivisionDetail>) getSqlMapClientTemplate().queryForList(
				"DIVISIONDETAIL-GETBYTRADEIDANDROLES", params);
	}

	@Override
	public int getCountByTradeAndUsrCount(Long tradeId, Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("userId", userId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"DIVISIONDETAIL-GETBYTRADEIDANDUSERIDCOUNT", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DivisionDetail> getCountByTradeAndUsr(Long tradeId, Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("userId", userId);
		return (List<DivisionDetail>) getSqlMapClientTemplate().queryForList(
				"DIVISIONDETAIL-GETBYTRADEIDANDUSERID", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DivisionDetail> getByStatus(int status) {
		return (List<DivisionDetail>) getSqlMapClientTemplate().queryForList(
				"DIVISIONDETAIL-GETBYSTATUS", status);
	}

	@Override
	public long getDivisionAmountFlowTradesByParamsCount(
			Map<String, Object> conditions) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				"DIVISIONDETAIL-GETDVSAMTFLOWTRADESBYPARAMSCOUNT", conditions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DivisionDetail> getDivisionAmountFlowTradesByParams(
			Map<String, Object> conditions) {
		return (List<DivisionDetail>) getSqlMapClientTemplate().queryForList(
				"DIVISIONDETAIL-GETDVSAMTFLOWTRADESBYPARAMS", conditions);
	}

	@Override
	public int transDivisionCountByTradeDetailId(long tradeDetailId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"DIVISIONDETAIL-TRANSDIVISIONCOUNTBYTRADEDETAILID",
				tradeDetailId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update(DivisionDetail detail) {
		getSqlMapClientTemplate().update("DIVISIONDETAIL-UPDATE", detail);

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<DivisionDetail> query(DivisionDetail divisionDetail) {
		return (List<DivisionDetail>) getSqlMapClientTemplate().queryForList(
				"DIVISIONDETAIL-query", divisionDetail);
	}
}
