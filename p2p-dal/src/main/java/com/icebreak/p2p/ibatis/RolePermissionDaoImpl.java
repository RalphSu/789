package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.RolePermissonDao;
import com.icebreak.p2p.dataobject.RolePermission;

public class RolePermissionDaoImpl extends SqlMapClientDaoSupport implements RolePermissonDao {

	@Override
	public void add(RolePermission rolePermission) {
		getSqlMapClientTemplate().insert("ROLEPERMISSION-INSERT", rolePermission);
	}

	@Override
	public int delete(int roleId, int permissionId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		param.put("permissionId", permissionId);
		return getSqlMapClientTemplate().delete("ROLEPERMISSION-DELETE", param);
	}

	@Override
	public int deleteByRoleId(int roleId) {
		return getSqlMapClientTemplate().delete("ROLEPERMISSION-DELETEBYROLEID", roleId);
	}

	@Override
	public int deleteByPermissionId(int permissionId) {
		return getSqlMapClientTemplate().delete("ROLEPERMISSION-DELETEBYPERMISSIONID", permissionId);
	}
}
