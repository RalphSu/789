package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.util.JsonMapper;

public class FastpayRequest extends BaseRequest {

	private String payerUserId;
	private String transferAmount;
	private String payeeUserId;
	private String bizProductCode="20130326_together";
	private String transferMemo;

	public FastpayRequest() {
		super();
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	public String getBizProductCode() {
		return bizProductCode;
	}

	public void setBizProductCode(String bizProductCode) {
		this.bizProductCode = bizProductCode;
	}

	public String getTransferMemo() {
		return transferMemo;
	}

	public void setTransferMemo(String transferMemo) {
		this.transferMemo = transferMemo;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
	
}
