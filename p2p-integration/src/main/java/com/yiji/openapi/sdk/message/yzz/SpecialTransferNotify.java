package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.util.JsonMapper;

public class SpecialTransferNotify extends BaseNotify {
	
	private String orderNo;
	private String tradeNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
