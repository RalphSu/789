package com.icebreak.p2p.trade;

import java.io.Serializable;

public class SplitProfit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 分润订单ID
	 */
	private String orderNo;
	/**
	 * 交易名称
	 */
	private String tradeName;
	/**
	 * 收款人ID
	 */
	private String payeeUserId;
	/**
	 * 分润金额
	 */
	private String transferAmount;
	/**
	 * 备注
	 */
	private String memo;
	
	public SplitProfit() {
	}

	public SplitProfit(String orderNo, String tradeName, String payeeUserId,
			String transferAmount, String memo) {
		this.orderNo = orderNo;
		this.tradeName = tradeName;
		this.payeeUserId = payeeUserId;
		this.transferAmount = transferAmount;
		this.memo = memo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
