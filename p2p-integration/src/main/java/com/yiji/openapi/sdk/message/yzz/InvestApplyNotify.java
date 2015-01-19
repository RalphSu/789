package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;

public class InvestApplyNotify extends BaseNotify {
	
	private String executeAction;
	private String tradeNo;
	private String orderNo;
	private String subTradeNo;
	private String tradeAmount;
	private String executeStatus;
	private String executeMessage;
	
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
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getSubTradeNo() {
		return this.subTradeNo;
	}
	
	public void setSubTradeNo(String subTradeNo) {
		this.subTradeNo = subTradeNo;
	}
	
	public String getTradeAmount() {
		return this.tradeAmount;
	}
	
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	public String getExecuteStatus() {
		return this.executeStatus;
	}
	
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	
	public String getExecuteMessage() {
		return this.executeMessage;
	}
	
	public void setExecuteMessage(String executeMessage) {
		this.executeMessage = executeMessage;
	}
	
}
