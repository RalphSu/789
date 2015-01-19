package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.util.JsonMapper;

public class SpecialTransferResponse extends BaseResponse {
	private String tradeNo;
	private String bondAmount;
	private String transferAmount;
	private String successTransferAmount;
	private String thisTransferAmount;

	@Override
	public String getTradeNo() {
		return tradeNo;
	}

	@Override
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBondAmount() {
		return bondAmount;
	}

	public void setBondAmount(String bondAmount) {
		this.bondAmount = bondAmount;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getSuccessTransferAmount() {
		return successTransferAmount;
	}

	public void setSuccessTransferAmount(String successTransferAmount) {
		this.successTransferAmount = successTransferAmount;
	}

	public String getThisTransferAmount() {
		return thisTransferAmount;
	}

	public void setThisTransferAmount(String thisTransferAmount) {
		this.thisTransferAmount = thisTransferAmount;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
