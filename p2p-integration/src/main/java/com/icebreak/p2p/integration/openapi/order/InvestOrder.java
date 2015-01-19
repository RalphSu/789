package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.service.Order;

public class InvestOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 投资人ID */
	private String payerUserId;
	
	/** 投资的交易唯一编号 */
	private String tradeNo;
	
	/** 交易额 */
	private Money tradeAmount = Money.zero();
	
	/** 交易名称 */
	private String tradeName = "";
	
	/** 交易备注 */
	private String tradeMemo;
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(payerUserId);
		Assert.hasText(tradeName);
		Assert.hasText(tradeNo);
		Assert.isTrue(tradeAmount.greaterThan(Money.zero()));
	}
	
	public String getPayerUserId() {
		return this.payerUserId;
	}
	
	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getTradeName() {
		return this.tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public Money getTradeAmount() {
		return this.tradeAmount;
	}
	
	public void setTradeAmount(Money tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	public String getTradeMemo() {
		return this.tradeMemo;
	}
	
	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
	
}
