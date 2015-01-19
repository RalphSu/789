package com.icebreak.p2p.integration.openapi.order;

import java.util.List;

import org.springframework.util.Assert;

import com.icebreak.util.service.Order;

public class PayTogetherOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 投资的交易唯一编号 */
	private String tradeNo;
	
	/** 交易备注 */
	private String tradeMemo;
	
	/** 交易名称 */
	private List<PayTogetherSubOrder> tradePoolSubTansferOrders;
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(tradeNo);
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getTradeMemo() {
		return this.tradeMemo;
	}
	
	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
	
	public List<PayTogetherSubOrder> getTradePoolSubTansferOrders() {
		return this.tradePoolSubTansferOrders;
	}
	
	public void setTradePoolSubTansferOrders(List<PayTogetherSubOrder> tradePoolSubTansferOrders) {
		this.tradePoolSubTansferOrders = tradePoolSubTansferOrders;
	}
	
}
