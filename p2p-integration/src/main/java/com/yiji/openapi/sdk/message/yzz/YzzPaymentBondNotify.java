package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.util.JsonMapper;

public class YzzPaymentBondNotify extends BaseNotify {
	
	private String payerMoney;
	private String orderNo;
	private String notifyTime;
	private String tradeNo;
	private String paymentUser;

	public String getPayerMoney() {
		return payerMoney;
	}

	public void setPayerMoney(String payerMoney) {
		this.payerMoney = payerMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String getNotifyTime() {
		return notifyTime;
	}

	@Override
	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPaymentUser() {
		return paymentUser;
	}

	public void setPaymentUser(String paymentUser) {
		this.paymentUser = paymentUser;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
}
