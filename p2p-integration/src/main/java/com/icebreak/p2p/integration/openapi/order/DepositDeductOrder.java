package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.p2p.ws.enums.CertTypeEnum;
import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.service.Order;
import com.icebreak.p2p.ext.enums.PublicTagEnum;

public class DepositDeductOrder extends ValidateOrderBase implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 132666089469261258L;
	/** 证件类型 */
	
	private CertTypeEnum		certType;
	/** 证件号 */
	private String				certNo;
	/** 银行省名 [<农行cfca 渠道 必须] */
	private String				provName;
	/** 银行市名 [农行cfca 渠道 必须] */
	private String				cityName;
	/** 银行开户账户号 */
	private String				bankAccountNo;
	/** 银行账户开户名 */
	private String				bankAccountName;
	
	/** 手机号 */
	private String				mobileNo;
	/** 账户名 */
	private String				accountName;
	/** 资金账户id */
	protected String			userId;
	/** 代扣金额 */
	protected Money				amount;
	/** 备注 */
	protected String			memo;
	/** 代扣订单 */
	protected String			orderNo;
	/** 银行代码 */
	private String				bankCode;
	/** 银行名称 */
	private String				bankName;
	/** 对公对私 */
	private PublicTagEnum		publicTagEnum;
	
	@Override
	public void check() {
		Assert.notNull(certType);
		Assert.hasText(certNo);
		Assert.hasText(provName);
		Assert.hasText(cityName);
		Assert.hasText(bankAccountNo);
		Assert.hasText(bankAccountName);
		Assert.hasText(userId);
		Assert.notNull(amount);
		Assert.hasText(orderNo);
	}
	
	public PublicTagEnum getPublicTagEnum() {
		return publicTagEnum;
	}
	
	public void setPublicTagEnum(PublicTagEnum publicTagEnum) {
		this.publicTagEnum = publicTagEnum;
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
	
	public String getProvName() {
		return provName;
	}
	
	public void setProvName(String provName) {
		this.provName = provName;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	
	public String getBankAccountName() {
		return bankAccountName;
	}
	
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DepositDeductOrder [certType=");
		builder.append(certType);
		builder.append(", certNo=");
		builder.append(certNo);
		builder.append(", provName=");
		builder.append(provName);
		builder.append(", cityName=");
		builder.append(cityName);
		builder.append(", bankAccountNo=");
		builder.append(bankAccountNo);
		builder.append(", bankAccountName=");
		builder.append(bankAccountName);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", bankCode=");
		builder.append(bankCode);
		builder.append(", publicTagEnum=");
		builder.append(publicTagEnum);
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	
}
