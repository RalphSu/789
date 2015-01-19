package com.icebreak.p2p.integration.openapi.order;

import com.yiji.openapi.sdk.message.BaseRequest;

public class EBankDepositDeductOrder extends BaseRequest {

	protected String			userId;
	protected String			depositAmount;
	protected String			tradePartnerId;
	protected String			payMode;

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getTradePartnerId() {
		return tradePartnerId;
	}

	public void setTradePartnerId(String tradePartnerId) {
		this.tradePartnerId = tradePartnerId;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EBankDepositDeductOrder [userId=");
		builder.append(userId);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
