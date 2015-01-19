package com.icebreak.p2p.ext.enums;

import java.util.ArrayList;
import java.util.List;

public enum PayToolAccessEnum {
		
	EBANK("EBANK", "异步网关"),
	
	OFFLINE("OFFLINE", "线下"),
	
	DEDUCT("DEDUCT", "代扣"),
	
	QUICK("QUICK", "快捷支付"),
	
	CERTIFY("CERTIFY", "证书充值"),
	
	VERIFY("VERIFY", "验证类充值"),
	
	EBANK_B2C("EBANK_B2C", "异步网关B2C"),
	
	EBANK_B2B("EBANK_B2B", "异步网关B2B"),
	
	;
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>PayToolAccessEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private PayToolAccessEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return PayToolAccessEnum
	 */
	public static PayToolAccessEnum getByCode(String code) {
		for (PayToolAccessEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<PayToolAccessEnum>
	 */
	public List<PayToolAccessEnum> getAllEnum() {
		List<PayToolAccessEnum> list = new ArrayList<PayToolAccessEnum>();
		for (PayToolAccessEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (PayToolAccessEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
