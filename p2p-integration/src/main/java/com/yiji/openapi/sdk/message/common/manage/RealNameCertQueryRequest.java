package com.yiji.openapi.sdk.message.common.manage;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.openapi.sdk.message.BaseRequest;

public class RealNameCertQueryRequest extends BaseRequest {
	
	/**
	 * 易极付用户ID
	 */
	@NotEmpty(message = "易极付用户ID不能为空")
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
