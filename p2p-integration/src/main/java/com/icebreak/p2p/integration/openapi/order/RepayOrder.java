package com.icebreak.p2p.integration.openapi.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.icebreak.util.service.Order;

public class RepayOrder extends BaseOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 还款的交易唯一编号 */
	private String tradeNo;
	
	/** 归还的借款交易唯一编号 */
	private String refTradeNo;
	
	/** 付款用户ID */
	private String payerUserId = "";
	
	/** 还款订单明细 */
	private List<RepaySubOrder> subOrders = new ArrayList<RepaySubOrder>();
	
	/** 还款分润订单 */
	private RepaySubOrder shardOrder;
	
	@Override
	public void check() {
		Assert.hasText(payerUserId);
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getRefTradeNo() {
		return this.refTradeNo;
	}
	
	public void setRefTradeNo(String refTradeNo) {
		this.refTradeNo = refTradeNo;
	}
	
	public String getPayerUserId() {
		return this.payerUserId;
	}
	
	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}
	
	public List<RepaySubOrder> getSubOrders() {
		return this.subOrders;
	}
	
	public void setSubOrders(List<RepaySubOrder> subOrders) {
		this.subOrders = subOrders;
	}
	
	public RepaySubOrder getShardOrder() {
		return this.shardOrder;
	}
	
	public void setShardOrder(RepaySubOrder shardOrder) {
		this.shardOrder = shardOrder;
	}
	
}
