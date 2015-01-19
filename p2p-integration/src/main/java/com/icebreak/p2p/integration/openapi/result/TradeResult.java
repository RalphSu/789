package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.ws.result.P2PBaseResult;

public class TradeResult extends P2PBaseResult {
	
	private static final long serialVersionUID = 9086185514887765864L;
	
	/** 渠道ID */
	String channelId;
	
	/** 合作伙伴网站唯一订单号 */
	String orderNo;
	
	/** 应答码 */
	String resultCode;
	
	/** 应答提示消息 */
	String resultMessage;
	
	/** 创建的交易唯一编号 */
	String tradeNo;
	
	public String getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getResultCode() {
		return this.resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMessage() {
		return this.resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	@Override
	public String toString() {
		return "RepayTradeResult [channelId=" + channelId + ", orderNo=" + orderNo
				+ ", resultCode=" + resultCode + ", resultMessage=" + resultMessage + ", tradeNo="
				+ tradeNo + "]";
	}
	
}
