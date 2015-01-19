package com.yiji.openapi.sdk.message.common;

import java.util.ArrayList;
import java.util.List;

import com.yiji.openapi.sdk.message.BaseRequest;

/**
 * 集资借款还款-批量还款
 * 
 *
 * 
 */
public class TradePayPoolReverseRequest extends BaseRequest {

	private String tradeNo;
	private String tradeMemo;
	private List<SubOrder> subOrders = new ArrayList<SubOrder>();

	public TradePayPoolReverseRequest(String tradeNo, String tradeMemo) {
		super();
		this.tradeNo = tradeNo;
		this.tradeMemo = tradeMemo;
	}
	
	public TradePayPoolReverseRequest() {
		super();
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeMemo() {
		return tradeMemo;
	}

	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}

	public List<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

	public void addSubOrder(SubOrder subOrder) {
		subOrders.add(subOrder);
	}
}
