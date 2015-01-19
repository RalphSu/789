package com.icebreak.p2p.ws.base.info;

import java.io.Serializable;

public class BankBasicInfo implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 7640980845872242259L;
	/** 银行类型 */
	private String				bankCode;
	/** 银行卡中文 名称 */
	private String				bankName;
	/** 银行LOG地址 */
	private String				logoUrl;
	
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
	
	public String getLogoUrl() {
		return logoUrl;
	}
	
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BankBaseInfo [bankCode=");
		builder.append(bankCode);
		builder.append(", bankName=");
		builder.append(bankName);
		builder.append(", logoUrl=");
		builder.append(logoUrl);
		builder.append("]");
		return builder.toString();
	}
	
}
