package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.util.JsonMapper;

public class ApplyWithdrawInstructionInfo {
	private String withdrawId; // 提现流水号
	private String bizIdentity; // 业务请求者身份标识码
	private String outBizNo; // 外部订单号
	private String freezeType; // 账务冻结码
	private String receiptUrl; // 回执产品层的通知地址
	private String priority; // 回执优先级
	private String subContractCode; // 子协议代码
	private String status; // 指令状态
	private String withdrawType; // 提现类型
	private String payAmountCurrency; // 应付额币种
	private String payAmount; // 应付额
	private String payAmountIn; // 应付实收额
	private String accountNo; // 账户账号
	private String accountName; // 账户名
	private String accountType; // 账户类别
	private String bankAccountNo; // 用户银行账号
	private String bankAccountName; // 用户开户名
	private String bankCode; // 银行代码
	private String bankCnapsNo; // 银行联号
	private String bankName; // 银行名称
	private String bankAddress; // 银行地址
	private String memo; // 备注
	private String reserved1; // 保留字段1
	private String tradeBizProductCode; // 交易产品编码
	private String reserved2; // 保留字段2

	public String getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
	}

	public String getBizIdentity() {
		return bizIdentity;
	}

	public void setBizIdentity(String bizIdentity) {
		this.bizIdentity = bizIdentity;
	}

	public String getOutBizNo() {
		return outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public String getFreezeType() {
		return freezeType;
	}

	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSubContractCode() {
		return subContractCode;
	}

	public void setSubContractCode(String subContractCode) {
		this.subContractCode = subContractCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getPayAmountCurrency() {
		return payAmountCurrency;
	}

	public void setPayAmountCurrency(String payAmountCurrency) {
		this.payAmountCurrency = payAmountCurrency;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayAmountIn() {
		return payAmountIn;
	}

	public void setPayAmountIn(String payAmountIn) {
		this.payAmountIn = payAmountIn;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getBankCnapsNo() {
		return bankCnapsNo;
	}

	public void setBankCnapsNo(String bankCnapsNo) {
		this.bankCnapsNo = bankCnapsNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}

	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
}
