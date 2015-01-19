package com.yiji.openapi.sdk.message.common.funds;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.util.JsonMapper;

public class DeductDepositNotify extends BaseNotify {
	private String amountIn; // 实际到账金额
	private String bankAccountNo;
	private String amount;
	private String orderNo; // 外部订单号
	private String userId;
	private String depositId; // 支付流水
	private String outBizNo;
	private String success;
	private String bankName;
	private String resultCode;

	public String getAmountIn() {
		return amountIn;
	}

	public void setAmountIn(String amountIn) {
		this.amountIn = amountIn;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public String getOutBizNo() {
		return outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
