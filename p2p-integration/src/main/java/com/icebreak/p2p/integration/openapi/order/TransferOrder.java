package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.service.Order;
import com.yiji.openapi.sdk.Constants;

public class TransferOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 控制转账的交易类型编码|只支持在风控转账中配置的类型编码，默认传入 partnerId。 */
	private String ctrlTransferType = Constants.PARTNER_ID;
	
	/** 业务产品编号 */
	private String tradeBizProductCode = Constants.tradeBizProductCode;
	
	/** 交易名称 */
	private String tradeName = "";
	
	/** 付款账户Id */
	private String payerUserId = "";
	
	/** 收款账户Id */
	private String sellerUserId = "";
	
	/** 交易额 */
	private Money tradeAmount = Money.zero();
	
	/** 交易备注 */
	private String tradeMemo;
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(sellerUserId);
		Assert.hasText(tradeName);
		Assert.hasText(tradeBizProductCode);
		Assert.isTrue(tradeAmount.greaterThan(Money.zero()));
	}
	
	public String getCtrlTransferType() {
		return this.ctrlTransferType;
	}
	
	public void setCtrlTransferType(String ctrlTransferType) {
		this.ctrlTransferType = ctrlTransferType;
	}
	
	public String getPayerUserId() {
		return this.payerUserId;
	}
	
	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}
	
	public String getSellerUserId() {
		return this.sellerUserId;
	}
	
	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
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
