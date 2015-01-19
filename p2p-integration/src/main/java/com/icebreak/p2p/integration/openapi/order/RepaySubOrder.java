package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.util.service.Order;

public class RepaySubOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 转账明细订单号 */
	private String orderNo;
	
	/** 转账订单名称 */
	private String tradeName = "";
	
	/** 还款的收款人ID */
	private String payeeUserId = "";
	
	/** 分润金额 */
	protected String transferAmount;
	
	/** 备注 */
	private String memo = "";
	
	@Override
	public void check() {
		Assert.hasText(orderNo);
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getTradeName() {
		return this.tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public String getPayeeUserId() {
		return this.payeeUserId;
	}
	
	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}
	
	public String getTransferAmount() {
		return this.transferAmount;
	}
	
	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
