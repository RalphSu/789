package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.ws.result.P2PBaseResult;

public class CustomerResult extends P2PBaseResult {

	private static final long	serialVersionUID	= 3284687900762814235L;
	String						userId;
	boolean						isExsit				= false;
	String						url;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean isExsit() {
		return isExsit;
	}
	
	public void setExsit(boolean isExsit) {
		this.isExsit = isExsit;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerResult [userId=");
		builder.append(userId);
		builder.append(", isExsit=");
		builder.append(isExsit);
		builder.append("]");
		return builder.toString();
	}
	
}
