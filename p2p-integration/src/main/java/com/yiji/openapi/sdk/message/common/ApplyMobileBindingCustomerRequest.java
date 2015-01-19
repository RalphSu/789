package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseRequest;

/**
 * 绑定手机号码 request
 * 
 *
 * 
 */
public class ApplyMobileBindingCustomerRequest extends BaseRequest {

	private String userId;
	private String mobile;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
