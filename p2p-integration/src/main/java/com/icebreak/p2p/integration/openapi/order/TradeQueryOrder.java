package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.util.service.Order;

public class TradeQueryOrder implements Order {
	
	private static final long serialVersionUID = -4785083019448254123L;
	
	/** 查询的交易类型 */
	private String tradeType = "POOL_REVERSE";
	
	/** 还款交易唯一编号 */
	private String tradeNo = "";
	
	/** 请求还款交易付款服务所同步返回的“批次号” */
	private String createBatchNo = "";
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(tradeType);
		Assert.hasText(tradeNo);
	}
	
	public String getTradeType() {
		return this.tradeType;
	}
	
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getCreateBatchNo() {
		return this.createBatchNo;
	}
	
	public void setCreateBatchNo(String createBatchNo) {
		this.createBatchNo = createBatchNo;
	}
	
}
