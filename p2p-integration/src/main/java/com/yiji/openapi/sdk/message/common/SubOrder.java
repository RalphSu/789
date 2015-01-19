package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.util.JsonMapper;

/**
 * 子订单
 * 
 *
 * 
 */
public class SubOrder {
	/** 订单号 */
	private String orderNo;
	/** 付款人 */
	private String payerUserId;
	/** 收款人 */
	private String payeeUserId;
	/** 交易名称 */
	private String tradeName;
	/** 付款金额 */
	private String transferAmount;

	private String tradeNo;

	public SubOrder() {
		super();
	}

	public SubOrder(String orderNo, String payerUserId, String payeeUserId, String transferAmount) {
		super();
		this.orderNo = orderNo;
		this.payerUserId = payerUserId;
		this.payeeUserId = payeeUserId;
		this.transferAmount = transferAmount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public String getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Override
	public String toString() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}

}
