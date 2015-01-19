package com.icebreak.p2p.integration.openapi.result;


import com.yiji.openapi.sdk.message.BaseResponse;

public class InvestReturnRequest extends BaseResponse {
	
	/** 投资人此次投资生成的唯一编号 */
	String subTradeNo;
	
	/** 投资人此次投资的结果 */
	String tradeStatus;
	
	public String getSubTradeNo() {
		return this.subTradeNo;
	}
	
	public void setSubTradeNo(String subTradeNo) {
		this.subTradeNo = subTradeNo;
	}
	
	public String getTradeStatus() {
		return this.tradeStatus;
	}
	
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
}
