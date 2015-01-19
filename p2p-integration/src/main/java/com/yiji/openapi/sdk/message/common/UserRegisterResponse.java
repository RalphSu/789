package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.util.JsonMapper;

public class UserRegisterResponse extends BaseResponse {
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
