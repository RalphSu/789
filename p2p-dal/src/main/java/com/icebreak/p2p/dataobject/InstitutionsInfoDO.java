package com.icebreak.p2p.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class InstitutionsInfoDO extends UserBaseInfoDO implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;

	// ========== properties ==========

	private long id;

	private String userBaseId;

	private String enterpriseName;

	private String organizationCode;

	private String taxRegistrationNo;

	private String businessLicenseNo;

	private String businessLicenseProvince;

	private String businessLicenseCity;

	private String commonlyUsedAddress;

	private String businessPeriod;

	private String legalRepresentativeName;

	private String legalRepresentativeCardNo;

	private String businessLicensePath;

	private String businessLicenseCachetPath;

	private String certFrontPath;

	private String certBackPath;

	private String openingLicensePath;

	private String bankOpenName;

	private String bankCardNo;

	private String bankType;

	private String bankKey;

	private String bankProvince;

	private String bankCity;

	private String bankAddress;

	private String institutionsInCode;

	private String institutionsThemRoughly;

	private String referees;
	
	private String contactName;
	/*身份证期限*/
	private String cardPeriod;
	/*联系人身份证号*/
	private String contactCertNo;
	/*comPhone公司联系电话*/
	private String comPhone;
	/**邮政编码*/
	private String zipCode;

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

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getTaxRegistrationNo() {
		return taxRegistrationNo;
	}

	public void setTaxRegistrationNo(String taxRegistrationNo) {
		this.taxRegistrationNo = taxRegistrationNo;
	}

	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}

	public String getBusinessLicenseProvince() {
		return businessLicenseProvince;
	}

	public void setBusinessLicenseProvince(String businessLicenseProvince) {
		this.businessLicenseProvince = businessLicenseProvince;
	}

	public String getBusinessLicenseCity() {
		return businessLicenseCity;
	}

	public void setBusinessLicenseCity(String businessLicenseCity) {
		this.businessLicenseCity = businessLicenseCity;
	}

	public String getCommonlyUsedAddress() {
		return commonlyUsedAddress;
	}

	public void setCommonlyUsedAddress(String commonlyUsedAddress) {
		this.commonlyUsedAddress = commonlyUsedAddress;
	}

	public String getBusinessPeriod() {
		return businessPeriod;
	}

	public void setBusinessPeriod(String businessPeriod) {
		this.businessPeriod = businessPeriod;
	}

	public String getLegalRepresentativeName() {
		return legalRepresentativeName;
	}

	public void setLegalRepresentativeName(String legalRepresentativeName) {
		this.legalRepresentativeName = legalRepresentativeName;
	}

	public String getLegalRepresentativeCardNo() {
		return legalRepresentativeCardNo;
	}

	public void setLegalRepresentativeCardNo(String legalRepresentativeCardNo) {
		this.legalRepresentativeCardNo = legalRepresentativeCardNo;
	}

	public String getBusinessLicensePath() {
		return businessLicensePath;
	}

	public void setBusinessLicensePath(String businessLicensePath) {
		this.businessLicensePath = businessLicensePath;
	}

	public String getBusinessLicenseCachetPath() {
		return businessLicenseCachetPath;
	}

	public void setBusinessLicenseCachetPath(String businessLicenseCachetPath) {
		this.businessLicenseCachetPath = businessLicenseCachetPath;
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

	public String getOpeningLicensePath() {
		return openingLicensePath;
	}

	public void setOpeningLicensePath(String openingLicensePath) {
		this.openingLicensePath = openingLicensePath;
	}

	public String getBankOpenName() {
		return bankOpenName;
	}

	public void setBankOpenName(String bankOpenName) {
		this.bankOpenName = bankOpenName;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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

	public String getInstitutionsInCode() {
		return institutionsInCode;
	}

	public void setInstitutionsInCode(String institutionsInCode) {
		this.institutionsInCode = institutionsInCode;
	}

	public String getInstitutionsThemRoughly() {
		return institutionsThemRoughly;
	}

	public void setInstitutionsThemRoughly(String institutionsThemRoughly) {
		this.institutionsThemRoughly = institutionsThemRoughly;
	}

	public String getReferees() {
		return referees;
	}

	public void setReferees(String referees) {
		this.referees = referees;
	}
	
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCardPeriod() {
		return cardPeriod;
	}

	public void setCardPeriod(String cardPeriod) {
		this.cardPeriod = cardPeriod;
	}

	public String getContactCertNo() {
		return contactCertNo;
	}

	public void setContactCertNo(String contactCertNo) {
		this.contactCertNo = contactCertNo;
	}
	public String getComPhone() {
		return comPhone;
	}

	public void setComPhone(String comPhone) {
		this.comPhone = comPhone;
	}
	

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
