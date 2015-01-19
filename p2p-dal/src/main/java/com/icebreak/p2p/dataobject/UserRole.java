package com.icebreak.p2p.dataobject;

import java.io.Serializable;


public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户角色ID
	 */
	private long id = 0;
	/**
	 * 用户ID
	 */
	private long userId = 0;
	/**
	 * 角色ID
	 */
	private int roleId = 0;
	
	public UserRole(){
		
	}
	
	public UserRole(long userId, int roleId){
		this.userId = userId;
		this.roleId = roleId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "id:" + id + ", userId:" + userId + ", roleId:" + roleId;
	}
    
	
}
