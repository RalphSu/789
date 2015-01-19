package com.icebreak.util.lang.enums;

import java.util.ArrayList;
import java.util.List;

public enum SysConfigStatusEnum {
	
	ENABLE("ENABLE", "正常"),
	
	DELETE("DELETE", "删除");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>SysConfigStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private SysConfigStatusEnum(String code, String message) {
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
	 * @return SysConfigStatusEnum
	 */
	public static SysConfigStatusEnum getByCode(String code) {
		for (SysConfigStatusEnum cacheCode : values()) {
			if (cacheCode.getCode().equals(code)) {
				return cacheCode;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SysConfigStatusEnum>
	 */
	public List<SysConfigStatusEnum> getAllEnum() {
		List<SysConfigStatusEnum> list = new ArrayList<SysConfigStatusEnum>();
		for (SysConfigStatusEnum cache : values()) {
			list.add(cache);
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
		for (SysConfigStatusEnum cache : values()) {
			list.add(cache.code());
		}
		return list;
	}
}
