package com.icebreak.p2p.dataobject;

import java.util.Date;


public class RechargeFlow {
	private long flowId;
	private String localNo;
	private String outBizNo;
	private String payType;
	private int status;//状态
	private double amount;//充值金额
	private long userId;//用户ID
	private String yjfAccountId;//用户托管机构资金账户ID
	private String bankName;//银行名称
	private String bankAccountNo;//银行卡号
	private String bankAcountName;//银行开户名

	private Date payTime;//充值时间
	private Date payFinishTime;//充值时间

	private String payMemo;
	private String rem1;

	public long getFlowId() {
		return flowId;
	}

	public void setFlowId(long flowId) {
		this.flowId = flowId;
	}

	public String getLocalNo() {
		return localNo;
	}

	public void setLocalNo(String localNo) {
		this.localNo = localNo;
	}

	public String getOutBizNo() {
		return outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getYjfAccountId() {
		return yjfAccountId;
	}

	public void setYjfAccountId(String yjfAccountId) {
		this.yjfAccountId = yjfAccountId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAcountName() {
		return bankAcountName;
	}

	public void setBankAcountName(String bankAcountName) {
		this.bankAcountName = bankAcountName;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(Date payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public String getPayMemo() {
		return payMemo;
	}

	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}

	public String getRem1() {
		return rem1;
	}

	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}
}
