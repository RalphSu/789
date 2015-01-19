package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;

public class SignManyBankNotify extends BaseNotify {

	private String certNo;
	private String name;
	private String bankShort;
	private String bankName;
	private String message;
	private String isSuccess;
	/**
	 * 卡号
	 */
	private String cardNo;
	/**
	 * 持卡人姓名
	 */
	private String cardName;
	/**
	 * 手机号
	 */
	private String userPhoneNo;
	/**
	 * 卡类型，默认储蓄卡
	 */
	private String cardType = "D";

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(null == cardName) {
			cardName = name;
		}
		this.name = name;
	}

	public String getBankShort() {
		return bankShort;
	}

	public void setBankShort(String bankShort) {
		this.bankShort = bankShort;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getUserPhoneNo() {
		return userPhoneNo;
	}

	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}

	@Override
	public String toString() {
		return "SignManyBankNotify{" +
				"certNo='" + certNo + '\'' +
				", name='" + name + '\'' +
				", bankShort='" + bankShort + '\'' +
				", bankName='" + bankName + '\'' +
				", message='" + message + '\'' +
				", isSuccess='" + isSuccess + '\'' +
				'}';
	}
}
