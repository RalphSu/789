package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseResponse;

/**
 * 集资借款还款-批量还款 响应报文
 * 
 *
 * 
 */
public class TradePayPoolReverseResponse extends BaseResponse {

	private String tradeNo;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

}
