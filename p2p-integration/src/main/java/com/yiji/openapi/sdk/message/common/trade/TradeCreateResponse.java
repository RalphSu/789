package com.yiji.openapi.sdk.message.common.trade;

import com.yiji.openapi.sdk.message.BaseResponse;

public class TradeCreateResponse extends BaseResponse {
	
	private String resultMessage;
	
	private String channelId;
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public String getChannelId() {
		return channelId;
	}
	
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
