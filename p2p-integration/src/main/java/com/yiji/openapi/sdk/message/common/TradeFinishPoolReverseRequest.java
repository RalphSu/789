package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseRequest;

/**
 * 批量还款交易-交易完成
 * 
 *
 * 
 */
public class TradeFinishPoolReverseRequest extends BaseRequest {
	private String tradeNo;
	private String tradeMemo;

	public TradeFinishPoolReverseRequest() {
		super();
	}

	/**
	 * @param tradeNo
	 */
	public TradeFinishPoolReverseRequest(String tradeNo) {
		super();
		this.tradeNo = tradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeMemo() {
		return tradeMemo;
	}

	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}

}
