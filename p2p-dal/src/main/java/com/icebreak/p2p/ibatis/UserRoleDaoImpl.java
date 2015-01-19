package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.UserRoleDao;
import com.icebreak.p2p.dataobject.UserRole;

public class UserRoleDaoImpl extends SqlMapClientDaoSupport implements UserRoleDao {

	@Override
	public void add(UserRole userRole) {
		getSqlMapClientTemplate().insert("USERROLE-INSERT", userRole);
	}

	@Override
	public int delete(long userId, int roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("roleId", roleId);
		return getSqlMapClientTemplate().delete("USERROLE-DELETE", param);
	}

	@Override
	public int deleteByUserId(long userId) {
		return getSqlMapClientTemplate().delete("USERROLE-DELETEBYUSERID", userId);
	}

	@Override
	public int deleteByRoleId(int roleId) {
		return getSqlMapClientTemplate().delete("USERROLE-DELETEBYROLEID", roleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> getUserRoles(String[] statuses, String[] roleCodes) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("statuses", statuses);
		params.put("roleCodes", roleCodes);
		return (List<UserRole>)getSqlMapClientTemplate().queryForList("USERROLE-GETALL", params);
	}
}
