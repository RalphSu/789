package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.RoleDao;
import com.icebreak.p2p.dataobject.Role;
public class RoleDaoImpl extends SqlMapClientDaoSupport implements RoleDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public Role getByRoleId(int id) {
		return (Role)getSqlMapClientTemplate().queryForObject("ROLE-GETBYROLEID", id);
	}

	@Override
	public Role getByRoleCode(String code) {
		return (Role)getSqlMapClientTemplate().queryForObject("ROLE-GETBYROLECODE", code);
	}

	@Override
	public void add(Role role) {
		getSqlMapClientTemplate().insert("ROLE-INSERT", role);
		role.setId((int)identityObtainer.getPrimaryKey());
	}

	@Override
	public int deleteByRoleId(int id) {
		return getSqlMapClientTemplate().delete("ROLE-DELETEBYROLEID", id);
	}

	@Override
	public int deleteByRoleCode(String code) {
		return getSqlMapClientTemplate().delete("ROLE-DELETEBYROLECODE", code);
	}

	@Override
	public int update(Role role) {
		return getSqlMapClientTemplate().update("ROLE-UPDATE", role);
	}

	@Override
	public int exists(String code) {
		return (Integer)getSqlMapClientTemplate().queryForObject("ROLE-EXISTS", code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles(Map<String, Object> params) {
		return (List<Role>)getSqlMapClientTemplate().queryForList("ROLE-GETALLROLES", params);
	}
    
	@Override
	public long getAllRolesCount() {
		return (Long)getSqlMapClientTemplate().queryForObject("ROLE-GETALLROLES-COUNT");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getChildrenRoles(Map<String, Object> params) {
		return (List<Role>)getSqlMapClientTemplate().queryForList("ROLE-GETCHILDRENROLES", params);
	}
	
	public long getChildrenRolesCount(Map<String, Object> params){
		return (Long)getSqlMapClientTemplate().queryForObject("ROLE-GETCHILDRENROLES-COUNT", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByUserId(Map<String, Object> params) {
		return (List<Role>)getSqlMapClientTemplate().queryForList("ROLE-GETROLESBYUSERID", params);
	}
	
	@Override
	public long getRolesByUserIdCount(Map<String, Object> params){
		return (Long)getSqlMapClientTemplate().queryForObject("ROLE-GETROLESBYUSERID-COUNT", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getGrantableRolesByUserId(Map<String, Object> params) {
		return (List<Role>)getSqlMapClientTemplate().queryForList("ROLE-GETGRANTABLEROLESBYUSERID", params);
	}

	@Override
	public long getGrantableRolesByUserIdCount(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("ROLE-GETGRANTABLEROLESBYUSERID-COUNT", params);
	}
}
