package com.yiji.openapi.sdk.message.common.funds;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.util.JsonMapper;

public class DepositNotify extends BaseNotify {
	private String isSuccess; // 成功与否
	private String orderNo; // 外部订单号
	private String depositId; // 支付流水
	private String depositAmount; // 充值金额
	private String amountIn; // 实际到账金额
	private String notifyType;

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}


	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getAmountIn() {
		return amountIn;
	}

	public void setAmountIn(String amountIn) {
		this.amountIn = amountIn;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
