package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.RolePermission;

public interface RolePermissonDao {
	/**
	 * 添加
	 * @param rolePermission
	 */
	public void add(RolePermission rolePermission);
	/**
	 * 删除
	 * @param roleId
	 * @param permission
	 * @return
	 */
	public int delete(int roleId, int permissionId);
	/**
	 * 根据角色ID删除
	 * @param roleId
	 * @return
	 */
	public int deleteByRoleId(int roleId);
	/**
	 * 根据权限ID删除
	 * @param permissionId
	 * @return
	 */
	public int deleteByPermissionId(int permissionId);

}
