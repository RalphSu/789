package com.icebreak.p2p.integration.openapi.info;

import java.io.Serializable;

import com.icebreak.p2p.integration.openapi.enums.UserStatusEnum;
import com.icebreak.util.lang.util.money.Money;

public class ThirdPartAccountInfo implements Serializable {

	private static final long serialVersionUID = 1488521729939362390L;
	private String accountNo;
	private String userId;
	private String accountAlias;

	private String accountTitleId;

	private String titleName;
	private String currency;
	private Money freezeAmount;

	private String balance; // 账户余额
	private String availableBalance; // 可用余额
	private String freezeBalance; // 冻结余额
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 账户状态
	 */
	private String status;

	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 用户登录名
	 */
	private String userName;
	/**
	 * 用户真实名称
	 */
	private String realName;
	/**
	 * 用户状态
	 */
	private UserStatusEnum userStatus;
	/**
	 * 用户身份类型
	 */
	private String certType;
	/**
	 * 证件号
	 */
	private String certNo;
	/**
	 * 证件有效期
	 */
	private String licenseValidTime;
	/**
	 * 认证状态
	 */
	private String certifyStatus;

	/**
	 * 运行时状态
	 */
	private String runtimeStatus;

	private String registerFrom;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountAlias() {
		return accountAlias;
	}

	public void setAccountAlias(String accountAlias) {
		this.accountAlias = accountAlias;
	}

	public String getAccountTitleId() {
		return accountTitleId;
	}

	public void setAccountTitleId(String accountTitleId) {
		this.accountTitleId = accountTitleId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public UserStatusEnum getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getLicenseValidTime() {
		return licenseValidTime;
	}

	public void setLicenseValidTime(String licenseValidTime) {
		this.licenseValidTime = licenseValidTime;
	}

	public String getCertifyStatus() {
		return certifyStatus;
	}

	public void setCertifyStatus(String certifyStatus) {
		this.certifyStatus = certifyStatus;
	}

	public String getRuntimeStatus() {
		return runtimeStatus;
	}

	public void setRuntimeStatus(String runtimeStatus) {
		this.runtimeStatus = runtimeStatus;
	}

	public String getRegisterFrom() {
		return registerFrom;
	}

	public void setRegisterFrom(String registerFrom) {
		this.registerFrom = registerFrom;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public Money getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Money freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(String freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

}
