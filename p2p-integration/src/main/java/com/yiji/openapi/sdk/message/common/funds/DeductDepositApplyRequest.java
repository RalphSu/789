package com.yiji.openapi.sdk.message.common.funds;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.openapi.sdk.message.BaseRequest;

public class DeductDepositApplyRequest extends BaseRequest {
	
	@NotEmpty(message = "代扣类型不能为空")
	@Length(max = 10, message = "代扣类型长度不能超过10")
	private String deductType;
	
	@NotEmpty(message = "银行开户账号不能为空")
	@Length(max = 30, message = "银行开户账号长度不能超过30")
	private String bankAccountNo;
	
	@Length(max = 30, message = "银行账户开户名长度不能超过30")
	private String bankAccountName;
	
	@Length(max = 20, message = "支付渠道API扩展服务单据号长度不能超过20")
	private String apiExtBizNo;
	
	@Length(max = 20, message = "手机号长度不能超过20")
	private String mobileNo;
	
	@Length(max = 30, message = "银行省名长度不能超过30")
	private String provName;
	
	@Length(max = 20, message = "银行市名长度不能超过20")
	private String cityName;
	
	@NotEmpty(message = "证件类型不能为空")
	@Length(max = 10, message = "证件类型长度不能超过10")
	private String certType;
	
	@NotEmpty(message = "证件号不能为空")
	@Length(max = 20, message = "证件号长度不能超过20")
	private String certNo;
	
	@Length(max = 30, message = "充值支付渠道API长度不能超过30")
	private String payChannelApi;
	
	@NotEmpty(message = "充值账户ID不能为空")
	@Length(max = 20, message = "充值账户ID长度不能超过20")
	private String userId;
	
	@Length(max = 30, message = "提现账户名长度不能超过30")
	private String accountName;
	
	@NotEmpty(message = "代扣充值金额不能为空")
	@Length(max = 17, message = "代扣充值金额长度不能超过17")
	private String amount;
	
	@Length(max = 10, message = "货币类型长度不能超过10")
	private String currency;
	
	@Length(max = 20, message = "手续费长度不能超过20")
	private String charge;
	
	@Length(max = 20, message = "收费模型长度不能超过20")
	private String chargeRule;
	
	@Length(max = 20, message = "冻结码长度不能超过20")
	private String freezeType;
	
	@NotEmpty(message = "业务请求者身份标识不能为空")
	@Length(max = 20, message = "业务请求者身份标识长度不能超过20")
	private String bizIdentity;
	
	@NotEmpty(message = "业务号不能为空")
	@Length(max = 10, message = "业务号长度不能超过10")
	private String bizNo;
	
	@NotEmpty(message = "外部订单号不能为空")
	@Length(max = 20, message = "外部订单号长度不能超过20")
	private String outBizNo;
	
	@Length(max = 20, message = "渠道所属来源长度不能超过20")
	private String owner;
	
	@Length(max = 30, message = "参与者ID长度不能超过30")
	private String tradePartnerId;
	
	@Length(max = 30, message = "平台商户ID长度不能超过30")
	private String tradeMerchantId;
	
	@NotEmpty(message = "业务产品编号不能为空")
	@Length(max = 30, message = "业务产品编号长度不能超过30")
	private String tradeBizProductCode;
	
	@Length(max = 20, message = "产品子集长度不能超过20")
	private String subOwner;
	
	@Length(max = 200, message = "备注长度不能超过200")
	private String memo;
	
	public String getDeductType() {
		return deductType;
	}
	
	public void setDeductType(String deductType) {
		this.deductType = deductType;
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
	
	public String getApiExtBizNo() {
		return apiExtBizNo;
	}
	
	public void setApiExtBizNo(String apiExtBizNo) {
		this.apiExtBizNo = apiExtBizNo;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getProvName() {
		return provName;
	}
	
	public void setProvName(String provName) {
		this.provName = provName;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCertType() {
		return certType;
	}
	
	public void setCertType(String certType) {
		this.certType = certType;
	}
	
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getPayChannelApi() {
		return payChannelApi;
	}
	
	public void setPayChannelApi(String payChannelApi) {
		this.payChannelApi = payChannelApi;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getCharge() {
		return charge;
	}
	
	public void setCharge(String charge) {
		this.charge = charge;
	}
	
	public String getChargeRule() {
		return chargeRule;
	}
	
	public void setChargeRule(String chargeRule) {
		this.chargeRule = chargeRule;
	}
	
	public String getFreezeType() {
		return freezeType;
	}
	
	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}
	
	public String getBizIdentity() {
		return bizIdentity;
	}
	
	public void setBizIdentity(String bizIdentity) {
		this.bizIdentity = bizIdentity;
	}
	
	public String getBizNo() {
		return bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public String getOutBizNo() {
		return outBizNo;
	}
	
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getTradePartnerId() {
		return tradePartnerId;
	}
	
	public void setTradePartnerId(String tradePartnerId) {
		this.tradePartnerId = tradePartnerId;
	}
	
	public String getTradeMerchantId() {
		return tradeMerchantId;
	}
	
	public void setTradeMerchantId(String tradeMerchantId) {
		this.tradeMerchantId = tradeMerchantId;
	}
	
	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}
	
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	
	public String getSubOwner() {
		return subOwner;
	}
	
	public void setSubOwner(String subOwner) {
		this.subOwner = subOwner;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
