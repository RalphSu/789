
package com.yiji.openapi.sdk.message.common.manage;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.openapi.sdk.message.BaseRequest;

public class UpdateMobileBindingCustomerRequest extends BaseRequest {
	
	@NotEmpty(message = "易极付用户ID不能为空")
	@Length(max = 20, message = "易极付用户ID长度不能超过20")
	private String userId;
	
	@NotEmpty(message = "手机号码不能为空")
	@Length(max = 20, message = "手机号码长度不能超过20")
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
