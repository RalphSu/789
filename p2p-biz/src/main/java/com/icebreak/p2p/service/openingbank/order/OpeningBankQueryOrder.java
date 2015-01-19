package com.icebreak.p2p.service.openingbank.order;

import org.springframework.util.Assert;

import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.service.Order;

public class OpeningBankQueryOrder extends ValidateOrderBase implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 5768064498841378320L;
	/**
	 * 卡号
	 */
	private String				cardNumber;
	
	private String				accountName;
	
	private String				bankCode;
	private String				certNo;
	private CardTypeEnum		cardType;
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public CardTypeEnum getCardType() {
		return cardType;
	}
	
	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpeningBankQueryOrder [cardNumber=" + cardNumber + "]";
	}
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(cardNumber, "cardNumber 不是字符串");
	}
	
}
