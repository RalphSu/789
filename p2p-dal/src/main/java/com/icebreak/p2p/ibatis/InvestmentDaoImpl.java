package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.InvestmentDao;
import com.icebreak.p2p.dataobject.Investment;

public class InvestmentDaoImpl extends SqlMapClientDaoSupport implements
		InvestmentDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Investment> getByProperties(Map<String, Object> params) {
		return (List<Investment>)getSqlMapClientTemplate().queryForList("INVESTMENT-GETBYPROPERTIES", params);
	}

	@Override
	public long getCountByProperties(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("INVESTMENT-GETBYPROPERTIESCOUNT", params);
	}

	@Override
	public Investment getById(long id) {
		return (Investment)getSqlMapClientTemplate().queryForObject("INVESTMENT-GETBYID", id);
	}
}
