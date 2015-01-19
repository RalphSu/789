package com.icebreak.p2p.ibatis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IndexTradeDao;
import com.icebreak.p2p.dataobject.IndexTrade;

public class IndexTradeDaoImpl extends SqlMapClientDaoSupport implements
		IndexTradeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexTrade> getByProperties(Map<String, Object> params) {
		return (List<IndexTrade>)getSqlMapClientTemplate().queryForList("INDEXTRADE-GETBYPROPERTIES", params);
	}

	@Override
	public long getByPropertiesCount(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("INDEXTRADE-GETBYPROPERTIESCOUNT", params);
	}

	@Override
	public Date getInvestCompleteTime(long tradeId) {

		return (Date) getSqlMapClientTemplate().queryForObject("INDEXTRADE-GETINVESTCOMPLETETIME", tradeId);
	}

	

}
