package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.PermissionDao;
import com.icebreak.p2p.dataobject.Permission;
public class PermissionDaoImpl extends SqlMapClientDaoSupport implements PermissionDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public Permission getByPermissionId(int id) {
		return (Permission)getSqlMapClientTemplate().queryForObject("PERMISSION-GETBYPERMISSIONID", id);
	}

	@Override
	public Permission getByPermissionCode(String code) {
		return (Permission)getSqlMapClientTemplate().queryForObject("PERMISSION-GETBYPERMISSIONCODE", code);
	}

	@Override
	public void add(Permission permission) {
         getSqlMapClientTemplate().insert("PERMISSION-INSERT", permission);
         permission.setId((int)identityObtainer.getPrimaryKey());
	}

	@Override
	public int update(Permission permission) {
		return getSqlMapClientTemplate().update("PERMISSION-UPDATE", permission);
	}

	@Override
	public int deleteByPermissionId(int id) {
		return getSqlMapClientTemplate().delete("PERMISSION-DELETEBYPERMISSIONID", id);
	}

	@Override
	public int deleteByPermissionCode(String code) {
		return getSqlMapClientTemplate().delete("PERMISSION-DELETEBYPERMISSIONCODE", code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getAllPermissions(Map<String, Object> params) {
		return (List<Permission>)getSqlMapClientTemplate().queryForList("PERMISSION-GETALLPERMISSIONS", params);
	}
	
	@Override
	public long getAllPermissionsCount() {
		return (Long)getSqlMapClientTemplate().queryForObject("PERMISSION-GETALLPERMISSIONS-COUNT");
	}

	@Override
	public int exists(String code) {
		return (Integer)getSqlMapClientTemplate().queryForObject("PERMISSION-EXISTS", code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getPermissionsByRoles(List<Integer> ids) {
		return (List<Permission>)getSqlMapClientTemplate().queryForList("PERMISSION-GETPERMISSIONSBYROLES", ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getPermissionsByRole(Map<String, Object> params) {
		return (List<Permission>)getSqlMapClientTemplate().queryForList("PERMISSION-GETPERMISSIONSBYROLE", params);
	}

	@Override
	public long getPermissionsByRoleCount(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("PERMISSION-GETPERMISSIONSBYROLE-COUNT", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getGrantablePermissionsByRoleId(Map<String, Object> params) {
		return (List<Permission>)getSqlMapClientTemplate().queryForList("PERMISSION-GETGRANTABLEPERMISSIONSBYROLEID", params);
	}

	@Override
	public long getGrantablePermissionsByRoleIdCount(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("PERMISSION-GETGRANTABLEPERMISSIONSBYROLEID-COUNT", params);
	}
}
