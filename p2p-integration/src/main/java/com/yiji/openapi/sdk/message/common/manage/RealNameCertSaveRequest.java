package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

public class RealNameCertSaveRequest extends BaseRequest {
	
	////////////////////////公有/////////////////////////
	/**
	 * 用户类型个人：Personal 企业：business
	 */
	@NotEmpty(message = "认证类型不能为空，个人：personal 企业：business")
	private String certType;
	
	/** 真实姓名 **/
	@NotEmpty(message = "真实姓名不能为空")
	private String nickname;
	
	/** 核心用户id */
	@NotEmpty(message = "核心用户ID不能为空")
	private String coreCustomerUserId;
	
	/** 核心用户名 **/
	private String coreCustomerUserName;
	
	////////////////////个人实名认证参数////////////////////
	/***
	 * 身份证类型 1.一代身份证 2.二代身份证 3.临时身份证 4.回乡证 5.台胞证 6.护照 7.港澳身份证 8.台湾身份证 9.营业执照
	 * 10.其它
	 **/
	private String cardtype;
	
	/*** 身份证号 **/
	private String cardid;
	
	/** 身份证正面 **/
	private String cardpic;
	
	/** 身份证背面 **/
	private String cardpic1;
	
	/*** 电话 **/
	private String phone;
	
	/*** 手机 **/
	private String mobile;
	
	/*** 联系地址 **/
	private String address;
	
	/** 身份证到期时间 正常：20120911, 长期为0 **/
	private Integer cardoff;
	
	////////////////////企业实名认证参数////////////////////
	/** 城市名称 */
	private String cityname;
	
	/** 联系地址 */
	private String comAddress;
	
	/** 注册资金 */
	private BigDecimal comAmount;
	
	/** 营业期限 正常：20120911, 长期为0 */
	private Integer comCycle;
	
	/** 企业名称 **/
	private String comName;
	
	/** 营业范围 **/
	private String comScope;
	
	/** 联系人身份证号 **/
	private String conCardid;
	
	/** 传真号 */
	private String conFax;
	
	/** 手机 */
	private String conMobile;
	
	private String conName;
	
	/** 公司联系电话 */
	private String conPhone;
	
	/** 邮编 */
	private Integer conZip;
	
	/** 驳回原因 */
	private String errormsg;
	
	/** 所属行业 */
	private String industry;
	
	/** 营业执照副本 */
	private String licence;
	
	/** 营业执照副本加盖公章 */
	private String licencecopy;
	
	/** 营业执照号码 */
	private String licencenum;
	
	/** 税务登记号 **/
	private String taxAuthority;
	
	/** 注册机构代码 */
	private String organizationcode;
	
	/** 省份名称 */
	private String provname;
	
	/** 职位 */
	private String strPosition;
	
	/** 法人身份证正面 */
	private String legalPersonCardPic;
	
	/** 法人身份证背面 */
	private String legalPersonCardPic1;
	
	/** 法人身份类型 */
	private String legalPersonCardType;
	
	/** 法人身份到期时间 */
	private Integer legalPersonCardOff;
	
	/** 法人身份证号 */
	private String legalPersonCardid;
	
	/** 经办人姓名 */
	private String agentPersonName;
	
	/** 经办人身份证号 */
	private String agentPersonCardid;
	
	/** 担保函 */
	private String backLetterPic;
	
	/** 是否以经办人作为实名认证 Y:是 N:否 */
	private String isLegalPerAudit;
	
	/** 经办人身份证类型 */
	private String agentPersonCardType;
	
	/** 经办人身份证到期时间 */
	private Integer agentPersonCardOff;
	
	/** 经办人身份正面图片 */
	private String agentPersonCardPic;
	
	/** 经办人身份证背面图片 */
	private String agentPersonCardPic1;
	
	private String source;
	
	/** 联系人姓名 **/
	private String contextName;
	
	/** 联系人电话 **/
	private String contextPhone;
	
	public String getCertType() {
		return certType;
	}
	
	public void setCertType(String certType) {
		this.certType = certType;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getCoreCustomerUserId() {
		return coreCustomerUserId;
	}
	
	public void setCoreCustomerUserId(String coreCustomerUserId) {
		this.coreCustomerUserId = coreCustomerUserId;
	}
	
	public String getCoreCustomerUserName() {
		return coreCustomerUserName;
	}
	
	public void setCoreCustomerUserName(String coreCustomerUserName) {
		this.coreCustomerUserName = coreCustomerUserName;
	}
	
	public String getCardtype() {
		return cardtype;
	}
	
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	public String getCardid() {
		return cardid;
	}
	
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	
	public String getCardpic() {
		return cardpic;
	}
	
	public void setCardpic(String cardpic) {
		this.cardpic = cardpic;
	}
	
	public String getCardpic1() {
		return cardpic1;
	}
	
	public void setCardpic1(String cardpic1) {
		this.cardpic1 = cardpic1;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getCardoff() {
		return cardoff;
	}
	
	public void setCardoff(Integer cardoff) {
		this.cardoff = cardoff;
	}
	
	public String getCityname() {
		return cityname;
	}
	
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	
	public String getComAddress() {
		return comAddress;
	}
	
	public void setComAddress(String comAddress) {
		this.comAddress = comAddress;
	}
	
	public BigDecimal getComAmount() {
		return comAmount;
	}
	
	public void setComAmount(BigDecimal comAmount) {
		this.comAmount = comAmount;
	}
	
	public Integer getComCycle() {
		return comCycle;
	}
	
	public void setComCycle(Integer comCycle) {
		this.comCycle = comCycle;
	}
	
	public String getComName() {
		return comName;
	}
	
	public void setComName(String comName) {
		this.comName = comName;
	}
	
	public String getComScope() {
		return comScope;
	}
	
	public void setComScope(String comScope) {
		this.comScope = comScope;
	}
	
	public String getConCardid() {
		return conCardid;
	}
	
	public void setConCardid(String conCardid) {
		this.conCardid = conCardid;
	}
	
	public String getConFax() {
		return conFax;
	}
	
	public void setConFax(String conFax) {
		this.conFax = conFax;
	}
	
	public String getConMobile() {
		return conMobile;
	}
	
	public void setConMobile(String conMobile) {
		this.conMobile = conMobile;
	}
	
	public String getConName() {
		return conName;
	}
	
	public void setConName(String conName) {
		this.conName = conName;
	}
	
	public String getConPhone() {
		return conPhone;
	}
	
	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}
	
	public Integer getConZip() {
		return conZip;
	}
	
	public void setConZip(Integer conZip) {
		this.conZip = conZip;
	}
	
	public String getErrormsg() {
		return errormsg;
	}
	
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	public String getIndustry() {
		return industry;
	}
	
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	public String getLicence() {
		return licence;
	}
	
	public void setLicence(String licence) {
		this.licence = licence;
	}
	
	public String getLicencecopy() {
		return licencecopy;
	}
	
	public void setLicencecopy(String licencecopy) {
		this.licencecopy = licencecopy;
	}
	
	public String getLicencenum() {
		return licencenum;
	}
	
	public void setLicencenum(String licencenum) {
		this.licencenum = licencenum;
	}
	
	public String getTaxAuthority() {
		return taxAuthority;
	}
	
	public void setTaxAuthority(String taxAuthority) {
		this.taxAuthority = taxAuthority;
	}
	
	public String getOrganizationcode() {
		return organizationcode;
	}
	
	public void setOrganizationcode(String organizationcode) {
		this.organizationcode = organizationcode;
	}
	
	public String getProvname() {
		return provname;
	}
	
	public void setProvname(String provname) {
		this.provname = provname;
	}
	
	public String getStrPosition() {
		return strPosition;
	}
	
	public void setStrPosition(String strPosition) {
		this.strPosition = strPosition;
	}
	
	public String getLegalPersonCardPic() {
		return legalPersonCardPic;
	}
	
	public void setLegalPersonCardPic(String legalPersonCardPic) {
		this.legalPersonCardPic = legalPersonCardPic;
	}
	
	public String getLegalPersonCardPic1() {
		return legalPersonCardPic1;
	}
	
	public void setLegalPersonCardPic1(String legalPersonCardPic1) {
		this.legalPersonCardPic1 = legalPersonCardPic1;
	}
	
	public String getLegalPersonCardType() {
		return legalPersonCardType;
	}
	
	public void setLegalPersonCardType(String legalPersonCardType) {
		this.legalPersonCardType = legalPersonCardType;
	}
	
	public Integer getLegalPersonCardOff() {
		return legalPersonCardOff;
	}
	
	public void setLegalPersonCardOff(Integer legalPersonCardOff) {
		this.legalPersonCardOff = legalPersonCardOff;
	}
	
	public String getLegalPersonCardid() {
		return legalPersonCardid;
	}
	
	public void setLegalPersonCardid(String legalPersonCardid) {
		this.legalPersonCardid = legalPersonCardid;
	}
	
	public String getAgentPersonName() {
		return agentPersonName;
	}
	
	public void setAgentPersonName(String agentPersonName) {
		this.agentPersonName = agentPersonName;
	}
	
	public String getAgentPersonCardid() {
		return agentPersonCardid;
	}
	
	public void setAgentPersonCardid(String agentPersonCardid) {
		this.agentPersonCardid = agentPersonCardid;
	}
	
	public String getBackLetterPic() {
		return backLetterPic;
	}
	
	public void setBackLetterPic(String backLetterPic) {
		this.backLetterPic = backLetterPic;
	}
	
	public String getIsLegalPerAudit() {
		return isLegalPerAudit;
	}
	
	public void setIsLegalPerAudit(String isLegalPerAudit) {
		this.isLegalPerAudit = isLegalPerAudit;
	}
	
	public String getAgentPersonCardType() {
		return agentPersonCardType;
	}
	
	public void setAgentPersonCardType(String agentPersonCardType) {
		this.agentPersonCardType = agentPersonCardType;
	}
	
	public Integer getAgentPersonCardOff() {
		return agentPersonCardOff;
	}
	
	public void setAgentPersonCardOff(Integer agentPersonCardOff) {
		this.agentPersonCardOff = agentPersonCardOff;
	}
	
	public String getAgentPersonCardPic() {
		return agentPersonCardPic;
	}
	
	public void setAgentPersonCardPic(String agentPersonCardPic) {
		this.agentPersonCardPic = agentPersonCardPic;
	}
	
	public String getAgentPersonCardPic1() {
		return agentPersonCardPic1;
	}
	
	public void setAgentPersonCardPic1(String agentPersonCardPic1) {
		this.agentPersonCardPic1 = agentPersonCardPic1;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getContextName() {
		return contextName;
	}
	
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}
	
	public String getContextPhone() {
		return contextPhone;
	}
	
	public void setContextPhone(String contextPhone) {
		this.contextPhone = contextPhone;
	}
	
}
