package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;

public class PayTogetherNotify extends BaseNotify {
	
	private String executeAction;
	private String tradeNo;
	private String tradeOrderNo;
	private String executeStatus;
	
	public String getExecuteAction() {
		return this.executeAction;
	}
	
	public void setExecuteAction(String executeAction) {
		this.executeAction = executeAction;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getTradeOrderNo() {
		return this.tradeOrderNo;
	}
	
	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	
	public String getExecuteStatus() {
		return this.executeStatus;
	}
	
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}

	@Override
	public String toString() {
		return "PayTogetherNotify{" +
				"executeAction='" + executeAction + '\'' +
				", tradeNo='" + tradeNo + '\'' +
				", tradeOrderNo='" + tradeOrderNo + '\'' +
				", executeStatus='" + executeStatus + '\'' +
				'}';
	}
}
