package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class UserPasswordExtend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long tbBaseId;
	/**用户baseId*/
	private String userBaseId;
	/**用户Id*/
	private long userId;
	/**用户密码*/
	private String password;
	/**用户密码类型*/
	private String passwordType;
	/**添加时间*/
	private Date rowAddTime;
	/**最近更新时间*/
	private Date rowUpdateTime;
	public long getTbBaseId() {
		return tbBaseId;
	}
	public void setTbBaseId(long tbBaseId) {
		this.tbBaseId = tbBaseId;
	}
	public String getUserBaseId() {
		return userBaseId;
	}
	public void setUserBaseId(String userBaseId) {
		this.userBaseId = userBaseId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordType() {
		return passwordType;
	}
	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}
	public Date getRowAddTime() {
		return rowAddTime;
	}
	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}
	public Date getRowUpdateTime() {
		return rowUpdateTime;
	}
	public void setRowUpdateTime(Date rowUpdateTime) {
		this.rowUpdateTime = rowUpdateTime;
	}
	@Override
	public String toString() {
		return "UserPasswordExtend [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
