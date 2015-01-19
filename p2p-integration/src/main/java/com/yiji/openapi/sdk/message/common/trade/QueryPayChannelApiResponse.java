package com.yiji.openapi.sdk.message.common.trade;

import com.yiji.openapi.sdk.message.BaseResponse;

import java.util.List;

public class QueryPayChannelApiResponse extends BaseResponse {
	
	private List<String> apis;
	
	private String bankCode;
	
	private String bankName;
	
	private String payChannelApi;
	
	private String channelNo;
	
	private String cardType;
	
	private String publicTag;
	
	private String env;
	
	private String owner;
	
	private String state;
	
	private String batch;
	
	private String memo;
	
	public List<String> getApis() {
		return apis;
	}
	
	public void setApis(List<String> apis) {
		this.apis = apis;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getPayChannelApi() {
		return payChannelApi;
	}
	
	public void setPayChannelApi(String payChannelApi) {
		this.payChannelApi = payChannelApi;
	}
	
	public String getChannelNo() {
		return channelNo;
	}
	
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public String getPublicTag() {
		return publicTag;
	}
	
	public void setPublicTag(String publicTag) {
		this.publicTag = publicTag;
	}
	
	public String getEnv() {
		return env;
	}
	
	public void setEnv(String env) {
		this.env = env;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getBatch() {
		return batch;
	}
	
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
