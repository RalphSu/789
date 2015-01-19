package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.Permission;

public interface PermissionDao {
	/**
	 * 根据权限ID获取权限
	 * @param id
	 * @return
	 */
	public Permission getByPermissionId(int id);
	/**
	 * 根据权限代码获取权限
	 * @param code
	 * @return
	 */
	public Permission getByPermissionCode(String code);
	/**
	 * 添加权限
	 * @param permission
	 */
	public void add(Permission permission);
	/**
	 * 修改权限
	 * @param permission
	 * @return
	 */
	public int update(Permission permission);
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteByPermissionId(int id);
    /**
     * 根据权限代码删除
     * @param code
     * @return
     */
	public int deleteByPermissionCode(String code);
	/**
	 * 获取所有权限
	 * @return
	 */
	public List<Permission> getAllPermissions(Map<String, Object> params);
	/**
	 * 获取所有权限的条数
	 * @return
	 */
	public long getAllPermissionsCount();
	/**
	 * 验证权限是否已经存在
	 * @param code
	 * @return
	 */
	public int exists(String code);
	/**
	 * 根据角色ID获取权限
	 * @param id
	 * @return
	 */
	public List<Permission> getPermissionsByRoles(List<Integer> ids);
	
	/**
	 * 根据角色ID获取权限
	 * @param id
	 * @return
	 */
	public List<Permission> getPermissionsByRole(Map<String, Object> params);
	/**
	 * 根据角色获取权限的条数
	 * @param params
	 * @return
	 */
	public long getPermissionsByRoleCount(Map<String, Object> params);
	/**
	 * 根据角色ID获取可授予的权限
	 * @param params
	 * @return
	 */
	public List<Permission> getGrantablePermissionsByRoleId(Map<String, Object> params);
	/**
	 * 根据用户ID获取可授予的权限条数
	 * @param params
	 * @return
	 */
	public long getGrantablePermissionsByRoleIdCount(Map<String, Object> params);
}
