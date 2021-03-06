package com.icebreak.util.lang.security;

import java.io.Serializable;

public class LogonInfo implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -2988869523091094018L;
	
	/** 最后登录IP地址 */
	private String lastLoginIp;
	
	/** 本次登录IP地址 */
	private String loginIp;
	
	/** 上一次登录时间 */
	private String lastLoginDate;
	
	/**
	 * @return Returns the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	
	/**
	 * @param lastLoginIp The lastLoginIp to set.
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	/**
	 * @return Returns the loginIp
	 */
	public String getLoginIp() {
		return loginIp;
	}
	
	/**
	 * @param loginIp The loginIp to set.
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	/**
	 * @return Returns the lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	
	/**
	 * @param lastLoginDate The lastLoginDate to set.
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("LogonInfo [lastLoginIp=%s, loginIp=%s, lastLoginDate=%s]",
			lastLoginIp, loginIp, lastLoginDate);
	}
	
}
