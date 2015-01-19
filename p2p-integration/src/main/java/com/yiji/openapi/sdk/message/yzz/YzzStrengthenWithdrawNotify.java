package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.util.JsonMapper;

public class YzzStrengthenWithdrawNotify extends BaseNotify {
	
	private String amountIn;
	private String amount;
	private String outBizNo;
	private String success;
	private String resultMessage;
	private String payNo;
	private String message;
	private String payType;
	private String payTypeMessage;
	private String resultCode;
	private String settleReason;
	
	public String getPayType() {
		return payType;
	}
	
	public void setPayType(String payType) {
		this.payType = payType;
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
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public String getPayNo() {
		return payNo;
	}
	
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getAmountIn() {
		return amountIn;
	}
	
	public void setAmountIn(String amountIn) {
		this.amountIn = amountIn;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getPayTypeMessage() {
		return this.payTypeMessage;
	}
	
	public void setPayTypeMessage(String payTypeMessage) {
		this.payTypeMessage = payTypeMessage;
	}
	
	public String getResultCode() {
		return this.resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getSettleReason() {
		return this.settleReason;
	}
	
	public void setSettleReason(String settleReason) {
		this.settleReason = settleReason;
	}
	
	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
}
