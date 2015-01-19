package com.yiji.openapi.sdk.message.common.funds;

import com.yiji.openapi.sdk.message.BaseResponse;


public class DeductDepositApplyResponse extends BaseResponse {

	    private String depositId;
	
	    private String outBizNo;
	
	private String amount;
	
	private String amountIn;
	
	    private String bankAccountNo;
	
	    private String bankAccountName;
	
	    private String bankCode;
	
	    private String bankName;
	
	private String userId;
	
	    private String memo;
	
	private String isSuccess;
	
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
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
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
	
	public String getBankAccountName() {
		return bankAccountName;
	}
	
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getIsSuccess() {
		return isSuccess;
	}
	
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
}
