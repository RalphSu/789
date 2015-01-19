package com.icebreak.p2p.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class CommonCardBinDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private int id;

	private String bankName;

	private String bankId;

	private String cardName;

	private String cardType;

	private int cardLength;

	private String cardNum;

	private int cardFlagLen;

	private String cardFlag;

	private String userId;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getCardType() {
		return cardType;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}


	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
