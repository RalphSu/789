package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class PersonalInfoDO extends UserBaseInfoDO implements Serializable  {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;

	// ========== properties ==========

	private long id;

	private String userBaseId;

	private String realName;

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
	private String customerSource;

	private String referees;
	private String memberNo;
	private int role;
	

	// ========== getters and setters ==========

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserBaseId() {
		return userBaseId;
	}

	public void setUserBaseId(String userBaseId) {
		this.userBaseId = userBaseId;
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
	
	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
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
