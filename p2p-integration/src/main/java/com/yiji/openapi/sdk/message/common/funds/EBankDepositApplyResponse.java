package com.yiji.openapi.sdk.message.common.funds;

import com.yiji.openapi.sdk.message.BaseResponse;

public class EBankDepositApplyResponse extends BaseResponse {

	private String isSuccess;
	private String depositId;
	private String depositAmount;
	private String notifyTime;
	private String notifyType;
	private String amountIn;

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getAmountIn() {
		return amountIn;
	}

	public void setAmountIn(String amountIn) {
		this.amountIn = amountIn;
	}
}
