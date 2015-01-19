package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;

public class CtrlTransferNotify extends BaseNotify {

	private String tradeNo;
	private String orderNo;
	private String resultCode;
	private String resultMessage;

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	@Override
	public String toString() {
		return "CtrlTransferNotify [tradeNo=" + tradeNo + ", orderNo="
				+ orderNo + ", resultCode=" + resultCode + ", resultMessage="
				+ resultMessage + "]";
	}

}
