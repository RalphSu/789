package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.util.JsonMapper;

public class TradeFinishPoolReverseResponse extends BaseResponse {
	private String tradeNo;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Override
	public String toString() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}

}
