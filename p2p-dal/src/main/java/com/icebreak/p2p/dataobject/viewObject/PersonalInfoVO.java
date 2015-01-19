package com.icebreak.p2p.dataobject.viewObject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.icebreak.p2p.dataobject.PersonalInfoDO;


public class PersonalInfoVO implements Serializable  {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	// ========== properties ==========
	//用户编号
	private String memberNo;
	/**
	 * UserBaseInfoDO
	 */
	private String userBaseId;

	private long userId;

	private String userName;

	private String realName;

	private String logPassword;

	private String payPassword;

	private String accountId;

	private String accountName;

	private String mobile;

	private String mobileBinding;

	private String mail;

	private String mailBinding;

	private String type;

	private String state;

	private Date rowAddTime = new Date();

	private Date rowUpdateTime;

	private String identityName;

	private String identityStartNo;

	private String identityEndNo;

	private String exIdentityNo;
	
	/**
	 * personalInfoDO
	 */
	private long id;
	
	private String certNo;

	private String businessPeriod;

	private String certFrontPath;

	private String certBackPath;

	private String bankOpenName;

	private String bankCardNo;

	private String bankType;

	private String bankKey;

	private String bankProvince;

	private String bankCity;

	private String bankAddress;

	private int gender;

	private String referees;
	
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getBankOpenName() {
		return bankOpenName;
	}

	public void setBankOpenName(String bankOpenName) {
		this.bankOpenName = bankOpenName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankKey() {
		return bankKey;
	}

	public void setBankKey(String bankKey) {
		this.bankKey = bankKey;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getReferees() {
		return referees;
	}

	public void setReferees(String referees) {
		this.referees = referees;
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
