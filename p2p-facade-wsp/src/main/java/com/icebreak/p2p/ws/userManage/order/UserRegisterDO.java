package com.icebreak.p2p.ws.userManage.order;

import java.io.Serializable;

public class UserRegisterDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名(不可空)
	 */
	private String userName;
	/**
	 * 真实姓名(不可空)
	 */
	private String realName;
	/**
	 * 邮箱 (不可空)
	 */
	private String mail;
	/**
	 * 电话(不可空)
	 */
	private String mobile;
	/**
	 * 身份证号(不可空)
	 */
	private String certNo;
	/**
	 * 身份证到期时间(不可空)
	 */
	private String businessPeriod;
	/**
	 * 推荐人编号(可空)
	 */
	private String referess;
	/**
	 * 托管机构资金账户ID(不可空)
	 */
	private String accountId;
	/**
	 * 托管机构资金账户名(不可空)
	 */
	private String accountName;
	/**
	 * 身份证正面照路径(不可空)
	 */
	private String certFrontPath;
	/**
	 * 身份证背面照路径(不可空)
	 */
	private String certBackPath;
	/**
	 * 登录密码(不可空)
	 */
	private String logPassword;
	/**
	 * 支付密码(不可空)
	 */
	private String payPassword;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getBusinessPeriod() {
		return businessPeriod;
	}
	public void setBusinessPeriod(String businessPeriod) {
		this.businessPeriod = businessPeriod;
	}
	public String getReferess() {
		return referess;
	}
	public void setReferess(String referess) {
		this.referess = referess;
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
	public String getCertFrontPath() {
		return certFrontPath;
	}
	public void setCertFrontPath(String certFrontPath) {
		this.certFrontPath = certFrontPath;
	}
	public String getCertBackPath() {
		return certBackPath;
	}
	public void setCertBackPath(String certBackPath) {
		this.certBackPath = certBackPath;
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


}
