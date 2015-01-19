package com.icebreak.p2p.activity;


public enum ActivityEnum {

	REGISTER_GOLD_EXP("register", "注册体验金"),

	OBN("老客户带新客户", "老客户带新客户活动");

	public String code = null;
	public String message = null;

	private ActivityEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
}
