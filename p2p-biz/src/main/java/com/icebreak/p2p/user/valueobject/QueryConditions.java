package com.icebreak.p2p.user.valueobject;

import java.util.Date;

public class QueryConditions {
	/** 用户ID */
	private long userId;
	/** 易代网用户名ID */
	private String userBaseId;
	/** 易代网用户名 */
	private String userName;
	/** 资金账户名 */
	private String accountName;
	/** 资金账ID */
	private String accountId;
	/** 真实姓名 */
	private String realName;
	/** 身份证号 */
	private String certNo;
	/** 用户状态 */
	private String state;
	/*实名状态*/
	private String realNameAuthentication;
	/*用户编号*/
	private String memberNo;
	private String mobile;
	/*用户角色*/
	private String role;
	/*推荐人编号*/
	private String referees;
	/*创建开始时间*/
	private String startCreateTime;
	/*创建结束时间*/
	private String endCreateTime;
	/*(申请)更新实名开始时间*/
	private String startUpdateTime;
	/*(申请)更新实名结束时间*/
	private String endUpdateTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserBaseId() {
		return userBaseId;
	}

	public void setUserBaseId(String userBaseId) {
		this.userBaseId = userBaseId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRealNameAuthentication() {
		return realNameAuthentication;
	}

	public void setRealNameAuthentication(String realNameAuthentication) {
		this.realNameAuthentication = realNameAuthentication;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getReferees() {
		return referees;
	}

	public void setReferees(String referees) {
		this.referees = referees;
	}

	public String getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getStartUpdateTime() {
		return startUpdateTime;
	}

	public void setStartUpdateTime(String startUpdateTime) {
		this.startUpdateTime = startUpdateTime;
	}

	public String getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(String endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}


}
