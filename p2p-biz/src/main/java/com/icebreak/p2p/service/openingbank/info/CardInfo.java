package com.icebreak.p2p.service.openingbank.info;

import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;

public class CardInfo {
	/**
	 * 银行全称
	 */
	private String			bankName;
	/**
	 * 银行英文简称
	 */
	private String			bankId;
	/**
	 * 卡产品名称
	 */
	private String			cardName;
	/**
	 * 卡类型
	 */
	private CardTypeEnum	cardType;
	/**
	 * 卡号长度
	 */
	private int				cardLength;
	
	/**
	 * 示范卡号.
	 */
	private String			cardNum;
	
	/**
	 * 卡标记的长度.
	 */
	private int				cardFlagLen;
	/**
	 * 卡识别标记.
	 */
	private String			cardFlag;
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public CardTypeEnum getCardType() {
		return cardType;
	}
	
	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public int getCardLength() {
		return cardLength;
	}
	
	public void setCardLength(int cardLength) {
		this.cardLength = cardLength;
	}
	
	public String getCardNum() {
		return cardNum;
	}
	
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	public int getCardFlagLen() {
		return cardFlagLen;
	}
	
	public void setCardFlagLen(int cardFlagLen) {
		this.cardFlagLen = cardFlagLen;
	}
	
	public String getCardFlag() {
		return cardFlag;
	}
	
	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
	
	@Override
	public String toString() {
		return "CardInfo [bankName=" + bankName + ", bankId=" + bankId + ", cardName=" + cardName + ", cardType=" + cardType + ", cardLength="
				+ cardLength + ", cardNum=" + cardNum + ", cardFlagLen=" + cardFlagLen + ", cardFlag=" + cardFlag + "]";
	}
	
}
