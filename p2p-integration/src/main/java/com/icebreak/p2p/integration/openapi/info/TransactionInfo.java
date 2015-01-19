package com.icebreak.p2p.integration.openapi.info;

public class TransactionInfo {

	private String				userId;
	private String settleChannelApi;
	private String memo;
	private String status;
	private String accBizNo;
	private String amountIn;
	private String settleBizNo;
	private String bankAccountNo;
	private String accountNo;
	private String bankName;
	private String payTime;
	private String charge;
	private String outBizNo;
	private String amount;
	private String				payNo;
	
	public String getSettleBizNo() {
		return settleBizNo;
	}
	
	public void setSettleBizNo(String settleBizNo) {
		this.settleBizNo = settleBizNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSettleChannelApi() {
		return settleChannelApi;
	}
	
	public void setSettleChannelApi(String settleChannelApi) {
		this.settleChannelApi = settleChannelApi;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccBizNo() {
		return accBizNo;
	}

	public void setAccBizNo(String accBizNo) {
		this.accBizNo = accBizNo;
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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getPayTime() {
		return payTime;
	}
	
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	public String getCharge() {
		return charge;
	}
	
	public void setCharge(String charge) {
		this.charge = charge;
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
	
	public String getPayNo() {
		return payNo;
	}
	
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
}
