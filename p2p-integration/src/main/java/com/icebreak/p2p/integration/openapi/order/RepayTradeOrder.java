package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.service.Order;
import com.yiji.openapi.sdk.Constants;

public class RepayTradeOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 付款人 */
	private String payerUserId;
	
	/** 交易名称 */
	private String tradeName = "";
	
	/** 交易额 */
	private Money tradeAmount = Money.zero();
	
	/** 业务产品编号 */
	private String tradeBizProductCode = Constants.tradeBizProductCode;
	
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
		Assert.hasText(tradeBizProductCode);
		Assert.isTrue(tradeAmount.greaterThan(Money.zero()));
	}
	
	public String getPayerUserId() {
		return this.payerUserId;
	}
	
	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
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
	
	public String getTradeBizProductCode() {
		return this.tradeBizProductCode;
	}
	
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	
	public String getTradeMemo() {
		return this.tradeMemo;
	}
	
	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
	
}
