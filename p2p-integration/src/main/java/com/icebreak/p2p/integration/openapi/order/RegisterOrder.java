package com.icebreak.p2p.integration.openapi.order;

import com.icebreak.p2p.ext.enums.BankAccountTypeEnum;
import com.icebreak.p2p.ws.enums.CertTypeEnum;
import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.service.Order;
import org.springframework.util.Assert;

public class RegisterOrder extends ValidateOrderBase implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long		serialVersionUID	= -5137145119038902180L;
	/** 核心会员账户id */
	String							userId;
	/** 用户名 */
	String							userName;
	
	/** 注册类型 */
	String							userType			= "P";
	/** 用户名 */
	String							realName;
	/** 用户身份类型 */
	private CertTypeEnum			certType=CertTypeEnum.Identity_Card;
	/** 证件号 */
	private String					certNo;
	/** 手机 */
	private String					mobile;
	
	/** 银行卡号 */
	protected String				bankCardNo;
	/** 联行号 */
	protected String				bankKey;
	/** 银行类型 */
	protected String				bankType;
	/** 银行卡类型 */
	protected String		bankCardType		= "DEBIT_CARD";
	/** 银行账户类型 */
	protected BankAccountTypeEnum	bankAccountType		= BankAccountTypeEnum.PERSONAL;
	
	/** 省份 */
	protected String				province;
	/** 城市 */
	protected String				city;
	
	/** 银行地址 */
	protected String				bankCardUserAddress;
	/** 用户状态 */
	protected String				userStatus;
	/** 邮件地址 */
	protected String				email;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public CertTypeEnum getCertType() {
		return certType;
	}
	
	public void setCertType(CertTypeEnum certType) {
		this.certType = certType;
	}
	
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	
	public String getBankType() {
		return bankType;
	}
	
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	
	public BankAccountTypeEnum getBankAccountType() {
		return bankAccountType;
	}
	
	public void setBankAccountType(BankAccountTypeEnum bankAccountType) {
		this.bankAccountType = bankAccountType;
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
	
	public String getBankCardUserAddress() {
		return bankCardUserAddress;
	}
	
	public void setBankCardUserAddress(String bankCardUserAddress) {
		this.bankCardUserAddress = bankCardUserAddress;
	}
	
	public String getUserStatus() {
		return userStatus;
	}
	
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		if (StringUtil.isBlank(userId)) {
			Assert.hasText(userName);
			Assert.hasText(userType);
		}
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterOrder [userName=");
		builder.append(userName);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", realName=");
		builder.append(realName);
		builder.append(", certType=");
		builder.append(certType);
		builder.append(", certNo=");
		builder.append(certNo);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", bankCardNo=");
		builder.append(bankCardNo);
		builder.append(", bankKey=");
		builder.append(bankKey);
		builder.append(", bankType=");
		builder.append(bankType);
		builder.append(", bankCardType=");
		builder.append(bankCardType);
		builder.append(", bankAccountType=");
		builder.append(bankAccountType);
		builder.append(", province=");
		builder.append(province);
		builder.append(", city=");
		builder.append(city);
		builder.append(", bankCardUserAddress=");
		builder.append(bankCardUserAddress);
		builder.append("]");
		return builder.toString();
	}
	
}
