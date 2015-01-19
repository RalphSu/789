package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseRequest;

public class YzzUserAccountQueryRequest extends BaseRequest {

	private String userId;

	public YzzUserAccountQueryRequest() {
		super();
	}

	public YzzUserAccountQueryRequest(String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
