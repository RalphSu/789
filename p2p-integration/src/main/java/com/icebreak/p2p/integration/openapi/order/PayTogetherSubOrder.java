package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.service.Order;

public class PayTogetherSubOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 分润订单号 */
	private String orderNo;
	
	/** 分润订单名称 */
	private String tradeName;
	
	/** 平台分润的收款人ID */
	private String payeeUserId;
	
	/** 分润金额 */
	private String transferAmount;
	
	/** 备注 */
	private String memo;
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(tradeName);
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
