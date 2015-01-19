package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.p2p.ws.enums.CertTypeEnum;
import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.service.Order;

public class VerifyBankCardOrder extends ValidateOrderBase implements Order {

	private static final long	serialVersionUID	= 7253247484185530520L;
	/** 银行简称 */
	String						bankCode;
	/** 银行卡户名 */
	String						accountName;
	/** 银行卡卡号 */
	String						accountNo;
	/**
	 * 银行卡类型 "C", "信用卡类型" "D", "借记卡类型"
	 */
	String						cardType			= "D";
	/** 证件类型 */
	CertTypeEnum				certType;
	/** 证件号码 */
	String						certNo;
	String						phoneNo;
	/** 有效期 */
	String						validDate;
	/** cvv2 */
	String						cvv2;
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
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
	
	public String getPhoneNo() {
		return phoneNo;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getValidDate() {
		return validDate;
	}
	
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	
	public String getCvv2() {
		return cvv2;
	}
	
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(bankCode);
		Assert.hasText(accountName);
		Assert.hasText(accountNo);
		Assert.notNull(certType);
		Assert.hasText(certNo);
	}
}
