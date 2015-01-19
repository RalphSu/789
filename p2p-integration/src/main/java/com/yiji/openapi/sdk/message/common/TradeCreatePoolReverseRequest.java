package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseRequest;

/**
 * 集资借款-创建还款交易
 * 
 *
 * 
 */
public class TradeCreatePoolReverseRequest extends BaseRequest {
	private String tradeName; // 交易名称
	private String tradeBizProductCode = ""; // *业务产品编号
	private String payerUserId; // *付款人
	private String tradeAmount = "1"; // *交易额
	private String tradeType = "POOL_REVERSE"; // *交易类型
	private String product = "poolReverse"; // 流程产品
	private String gatheringType = "SERVICE_BUY"; // 交易子类型

	private String tradeMemo = "创建转账支付交易"; // 交易备注
	// private String buyerMarkerMemo; // 买家备注
	// private String sellerMarkerMemo; // 卖家备注

	public TradeCreatePoolReverseRequest() {
		super();
	}

	public TradeCreatePoolReverseRequest(String payerUserId, String tradeAmount) {
		super();
		this.payerUserId = payerUserId;
		this.tradeAmount = tradeAmount;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}

	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getGatheringType() {
		return gatheringType;
	}

	public void setGatheringType(String gatheringType) {
		this.gatheringType = gatheringType;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getTradeMemo() {
		return tradeMemo;
	}

	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
}
