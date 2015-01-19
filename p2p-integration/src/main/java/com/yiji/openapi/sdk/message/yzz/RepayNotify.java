package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;

public class RepayNotify extends BaseNotify {
	
	private String executeAction;
	private String createBatchNo;
	private String tradeNo;
	private String tradeOrderNo;
	private String executeStatus;
	
	public String getExecuteAction() {
		return this.executeAction;
	}
	
	public void setExecuteAction(String executeAction) {
		this.executeAction = executeAction;
	}
	
	public String getCreateBatchNo() {
		return this.createBatchNo;
	}
	
	public void setCreateBatchNo(String createBatchNo) {
		this.createBatchNo = createBatchNo;
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
	
}
