package com.icebreak.p2p.ibatis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.DivisionDao;
import com.icebreak.p2p.dataobject.Division;

public class DivisionDaoImpl extends SqlMapClientDaoSupport implements DivisionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Division> getDivisions(long tradeId, String... roles) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		if(roles != null && roles.length > 0){
		  params.put("roles", Arrays.asList(roles));
		}
		return (List<Division>)getSqlMapClientTemplate().queryForList("DIVIDSION-GETDIVISIONS", params);
	}

	@Override
	public Division getParentDivision(long tradeId, long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("tradeId", tradeId);
		return (Division)getSqlMapClientTemplate().queryForObject("DIVIDSION-GETPARENTDIVISION", params);
	}

	@Override
	public double getRuleByTradeIdAndUserId(long tradeId, long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("tradeId", tradeId);
		Double d = (Double)getSqlMapClientTemplate().queryForObject("DIVISION-GETRULEBYTRADEIDANDUSERID", params);
		return d == null ? 0 : d;
	}
}
