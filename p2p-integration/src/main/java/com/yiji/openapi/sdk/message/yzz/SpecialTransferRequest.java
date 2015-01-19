package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.util.JsonMapper;

public class SpecialTransferRequest extends BaseRequest {

	private String tradeNo;
	private String payeeUserId;
	private String transName;

	public SpecialTransferRequest() {
		super();
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
	
}
