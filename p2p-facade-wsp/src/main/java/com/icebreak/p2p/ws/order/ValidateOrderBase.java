package com.icebreak.p2p.ws.order;

import org.springframework.util.Assert;

import com.icebreak.util.service.OrderBase;

public class ValidateOrderBase extends OrderBase {
	
	private static final long serialVersionUID = 1L;
	
	public String getGid() {
		return null;
	}
	
	public void setGid(String gid) {
		
	}
	
	protected void validateHasText(String validateField, String fieldDes) {
		Assert.hasText(validateField, fieldDes + "不能为空!");
	}
	
	protected void validateHasZore(long vlaue, String fieldDes) {
		Assert.isTrue(vlaue > 0, fieldDes + "不能为空!");
	}
	
	protected void validateNotNull(Object validateField, String fieldDes) {
		Assert.notNull(validateField, fieldDes + "不能为空!");
	}
	
	protected void validateGreaterThan(Number validateField, String fieldDes) {
		Assert.isTrue(validateField.doubleValue() > 0, fieldDes + "必须大于零!");
	}
	
	public boolean isCheckGid() {
		return false;
	}
}
