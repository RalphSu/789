package com.icebreak.util.lang.enums;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.util.lang.util.StringUtil;

public enum GenderEnum {
	
	/** 男性 */
	MALE("M", "男性"),
	
	/** 女性 */
	FEMALE("F", "女性"),
	
	/** 未知 */
	UNKNOWN("U", "未知");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>GenderEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private GenderEnum(String code, String message) {
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
	 * @return GenderEnum
	 */
	public static GenderEnum getByCode(String code) {
		for (GenderEnum _enum : values()) {
			if (StringUtil.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<GenderEnum>
	 */
	public List<GenderEnum> getAllEnum() {
		List<GenderEnum> list = new ArrayList<GenderEnum>();
		for (GenderEnum _enum : values()) {
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
		for (GenderEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
