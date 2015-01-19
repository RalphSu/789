package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class UserBaseInfoDO implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;

	// ========== properties ==========

	private String userBaseId;

	private long userId;

	private String userName;

	private String realName;

	private String logPassword;

	private String payPassword;

	private String accountId;

	private String accountName;

	private String mobile;

	private String mobileBinding = "NO";

	private String mail;

	private String mailBinding = "NO";;

	private String type = "GR";

	private String state;

	private Date rowAddTime = new Date();

	private Date rowUpdateTime;

	private String identityName;

	private String identityStartNo;

	private String identityEndNo;

	private String exIdentityNo;
	
	private String realNameAuthentication;
	
	private Integer pwdErrorCount;
	
	private Date changeLockTime;

	// ========== getters and setters ==========

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLogPassword() {
		return logPassword;
	}

	public void setLogPassword(String logPassword) {
		this.logPassword = logPassword;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobileBinding() {
		return mobileBinding;
	}

	public void setMobileBinding(String mobileBinding) {
		this.mobileBinding = mobileBinding;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailBinding() {
		return mailBinding;
	}

	public void setMailBinding(String mailBinding) {
		this.mailBinding = mailBinding;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public String getIdentityStartNo() {
		return identityStartNo;
	}

	public void setIdentityStartNo(String identityStartNo) {
		this.identityStartNo = identityStartNo;
	}

	public String getIdentityEndNo() {
		return identityEndNo;
	}

	public void setIdentityEndNo(String identityEndNo) {
		this.identityEndNo = identityEndNo;
	}

	public String getExIdentityNo() {
		return exIdentityNo;
	}

	public void setExIdentityNo(String exIdentityNo) {
		this.exIdentityNo = exIdentityNo;
	}
	
	
	public String getRealNameAuthentication() {
		return realNameAuthentication;
	}

	public void setRealNameAuthentication(String realNameAuthentication) {
		this.realNameAuthentication = realNameAuthentication;
	}
	
	public Integer getPwdErrorCount() {
		return pwdErrorCount;
	}

	public void setPwdErrorCount(Integer pwdErrorCount) {
		this.pwdErrorCount = pwdErrorCount;
	}

	public Date getChangeLockTime() {
		return changeLockTime;
	}

	public void setChangeLockTime(Date changeLockTime) {
		this.changeLockTime = changeLockTime;
	}

	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
