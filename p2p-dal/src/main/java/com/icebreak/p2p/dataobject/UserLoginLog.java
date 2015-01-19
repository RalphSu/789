package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class UserLoginLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 表ID
	 */
	private String tblBaseId;
	/**
	 * userId
	 */
	private long userId;
	/**
	 * 用户登录IP
	 */
	private String loginIp;
	/**
	 * 登录地域
	 */
	private String loginAddress;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	public String getTblBaseId() {
		return tblBaseId;
	}
	public void setTblBaseId(String tblBaseId) {
		this.tblBaseId = tblBaseId;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getLoginAddress() {
		return loginAddress;
	}
	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	

}
