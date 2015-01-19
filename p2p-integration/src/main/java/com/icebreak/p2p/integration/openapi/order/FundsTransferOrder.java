package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.p2p.integration.openapi.enums.TransferActionEnum;
import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.service.Order;

public class FundsTransferOrder extends ValidateOrderBase implements Order {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 订单号 */
	private String orderNo;
	
	private TransferActionEnum transferActionEnum = TransferActionEnum.NORMAL;
	/** 付款人 */
	private String payerUserId;
	
	/** 交易名称 */
	private String tradeName = "二手房交易";
	
	/** 交易额 */
	private Money tradeAmount = Money.zero();
	/** 交易类型 */
	private String tradeType = "FASTPAYTRADE";
	/** 交易码 300012普通转账,700000收费 */
	private String transCode = "300012";
	/** 来源系统 */
	protected boolean isFreeze = true;
	/** 来源系统 */
	protected String systemId;
	
	/** 交易备注 */
	protected String tradeMemo;
	
	/** 买家 */
	protected String buyerUserId;
	
	/** 卖家 */
	private String sellerUserId;
	
	/** 商品名称 */
	private String name;
	
	/** 商品详情 */
	private String memo;
	
	/** 商品单价 */
	private Money price = Money.zero();
	
	/** 商品数量 */
	private int quantity = 1;
	
	/** 商品单位 */
	private String unit = "";
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(orderNo);
		Assert.hasText(payerUserId);
		Assert.hasText(tradeName);
		Assert.isTrue(tradeAmount.greaterThan(Money.zero()));
		Assert.hasText(buyerUserId);
		Assert.hasText(sellerUserId);
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
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getTradeMemo() {
		return tradeMemo;
	}
	
	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
	
	public String getBuyerUserId() {
		return buyerUserId;
	}
	
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	
	public String getSellerUserId() {
		return sellerUserId;
	}
	
	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public Money getTradeAmount() {
		return tradeAmount;
	}
	
	public String getTradeType() {
		return tradeType;
	}
	
	public Money getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public void setTradeAmount(Money tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public void setPrice(Money price) {
		this.price = price;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getTransCode() {
		return transCode;
	}
	
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	
	public boolean isFreeze() {
		return isFreeze;
	}
	
	public void setFreeze(boolean isFreeze) {
		this.isFreeze = isFreeze;
	}
	
	public TransferActionEnum getTransferActionEnum() {
		return transferActionEnum;
	}
	
	public void setTransferActionEnum(TransferActionEnum transferActionEnum) {
		this.transferActionEnum = transferActionEnum;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FundsTransferOrder [orderNo=");
		builder.append(orderNo);
		builder.append(", payerUserId=");
		builder.append(payerUserId);
		builder.append(", tradeName=");
		builder.append(tradeName);
		builder.append(", tradeAmount=");
		builder.append(tradeAmount);
		builder.append(", tradeType=");
		builder.append(tradeType);
		builder.append(", transCode=");
		builder.append(transCode);
		builder.append(", isFreeze=");
		builder.append(isFreeze);
		builder.append(", systemId=");
		builder.append(systemId);
		builder.append(", tradeMemo=");
		builder.append(tradeMemo);
		builder.append(", buyerUserId=");
		builder.append(buyerUserId);
		builder.append(", sellerUserId=");
		builder.append(sellerUserId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", price=");
		builder.append(price);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append("]");
		return builder.toString();
	}
	
}
