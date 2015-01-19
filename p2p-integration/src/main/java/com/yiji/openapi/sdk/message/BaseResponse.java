package com.yiji.openapi.sdk.message;

import com.yiji.openapi.sdk.util.JsonMapper;

public abstract class BaseResponse {
	
	private String channelId;
	/** 客户方交易唯一标准，可以作为交易编号 */
	private String orderNo;
	private String success;
	private String signType;
	private String sign;
	private String tradeNo;
	private String resultCode;
	private String resultCodeDesc;
	private String resultMessage;
	
	public boolean success() {
		if("success".equals(getResultCode())) {
			setSuccess("T");
			return true;
		}
		return "T".equals(success);
	}
	
	public String getChannelId() {
		return channelId;
	}
	
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}
	
	public String getSignType() {
		return signType;
	}
	
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public String getResultCodeDesc() {
		return this.resultCodeDesc;
	}
	
	public void setResultCodeDesc(String resultCodeDesc) {
		this.resultCodeDesc = resultCodeDesc;
	}
	
	protected void validate() {
		
	}
	
	public void unmarshall(String message) {
		doUnmarshall(message);
	}
	
	protected void doUnmarshall(String message) {
		
	}
	
	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
	
}
