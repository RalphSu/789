package com.icebreak.util.service;

import javax.validation.ConstraintViolation;
import java.util.Set;

public abstract class OrderBase implements Order {
	
	private static final long serialVersionUID = 1L;
	
	private String gid;
	
	public String getGid() {
		return gid;
	}
	
	public void setGid(String gid) {
		this.gid = gid;
	}

	public abstract boolean isCheckGid();
	
	@Override
	public void check() {
	}
	
	/**
	 * 通过jsr303规范的注解来校验参数
	 * @param groups 校验groups
	 */
	public void checkWithGroup(Class<?>... groups) {
	}
	
	private void validate(Set<ConstraintViolation<OrderBase>> constraintViolations) {
	}
}
