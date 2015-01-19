package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.UserInvestEntryDao;
import com.icebreak.p2p.dataobject.UserInvestEntry;

public class UserInvestEntryDaoImpl extends SqlMapClientDaoSupport implements UserInvestEntryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInvestEntry> getByProperties(Map<String, Object> params) {
		return (List<UserInvestEntry>)getSqlMapClientTemplate().queryForList("USERINVESTENTRY-GETBYPROPERTIES", params);
	}

	@Override
	public long getCountByProperties(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("USERINVESTENTRY-GETCOUNTBYPROPERTIES", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInvestEntry> getEntriesByTradeParams(Map<String, Object> params) {
		return (List<UserInvestEntry>)getSqlMapClientTemplate().queryForList("USERINVESTENTRY-GETENTRIESBYTRADEID", params);
	}

	@Override
	public long getAllInverstAmount(Map<String, Object> conditions) {

		return (Long)getSqlMapClientTemplate().queryForObject("USERINVESTENTRY-ALLAMOUNT", conditions);
	}
}
