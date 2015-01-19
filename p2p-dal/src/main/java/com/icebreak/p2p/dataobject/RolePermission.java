package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class RolePermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色权限ID
	 */
	private int id = 0;
	/**
	 * 角色ID
	 */
	private int roleId = 0;
	/**
	 * 权限ID
	 */
	private int permissionId = 0;
	
	public RolePermission(){
	}
	
	public RolePermission(int roleId, int permissionId){
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public String toString() {
		return "id:" + id + ", roleId:" + roleId + ", permissionId:" + permissionId;
	}
}
