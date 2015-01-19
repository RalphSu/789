package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.util.JsonMapper;

public class YzzPaymentBondRequest extends BaseRequest {

	private String payerUserId;
	private String payerMoney;
	private String tradeNo;
	private String partnerName;

	public YzzPaymentBondRequest() {
		super();
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public String getPayerMoney() {
		return payerMoney;
	}

	public void setPayerMoney(String payerMoney) {
		this.payerMoney = payerMoney;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
	
}
