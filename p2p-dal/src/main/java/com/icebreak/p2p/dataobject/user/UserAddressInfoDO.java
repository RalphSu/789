package com.icebreak.p2p.dataobject.user;

import java.io.Serializable;
import java.util.Date;

public class UserAddressInfoDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String baseId;//主键
	private long userId;//用户ID
	private String addressType;//地址类型
	private String recipient;//收件人
	private String recipientMobile;//手机号
	private String districtCode;//区号
	private String officeNumber;//办公电话
	private String  extNumber;//分机号
	private String province;//省份
	private String city;//城市
	private String district;//区县
	private String villages;//乡镇
	private String addressDetail;//详细地址
	private String postcCode;//邮编
	private short isDefault;//是否默认地址：1：是，2：否
	private Date addTime;//新增时间
	private Date updateTime;//更新时间
	private String rem1;//备用字段1
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecipientMobile() {
		return recipientMobile;
	}
	public void setRecipientMobile(String recipientMobile) {
		this.recipientMobile = recipientMobile;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getOfficeNumber() {
		return officeNumber;
	}
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	public String getExtNumber() {
		return extNumber;
	}
	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getVillages() {
		return villages;
	}
	public void setVillages(String villages) {
		this.villages = villages;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public String getPostcCode() {
		return postcCode;
	}
	public void setPostcCode(String postcCode) {
		this.postcCode = postcCode;
	}
	
	public short getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(short isDefault) {
		this.isDefault = isDefault;
	}
	public String getRem1() {
		return rem1;
	}
	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}
	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "UserAddressInfoDO [baseId=" + baseId + ", userId=" + userId
				+ ", addressType=" + addressType + ", recipient=" + recipient
				+ ", recipientMobile=" + recipientMobile + ", districtCode="
				+ districtCode + ", officeNumber=" + officeNumber
				+ ", extNumber=" + extNumber + ", province=" + province
				+ ", city=" + city + ", district=" + district + ", villages="
				+ villages + ", addressDetail=" + addressDetail
				+ ", postcCode=" + postcCode + ", isDefault=" + isDefault
				+ ", addTime=" + addTime + ", updateTime=" + updateTime
				+ ", rem1=" + rem1 + "]";
	}
	
}
