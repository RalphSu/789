package com.icebreak.util.lang.enums;

import java.util.ArrayList;
import java.util.List;

public enum DataOperationCodeEnum {
	
	DATA_ACCESS_EXCEPTION("DB_EXCEPTION", "数据库异常"),
	
	UN_KNOWN_EXCEPTION("DATA_ACCESS_EXCEPTION", "未知异常"),
	
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),
	
	EXECUTE_FAIL("EXECUTE_FAIL", "执行失败"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>LocalCacheEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private DataOperationCodeEnum(String code, String message) {
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
	 * @return DataOperationCodeEnum
	 */
	public static DataOperationCodeEnum getByCode(String code) {
		for (DataOperationCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<DataOperationCodeEnum>
	 */
	public List<DataOperationCodeEnum> getAllEnum() {
		List<DataOperationCodeEnum> list = new ArrayList<DataOperationCodeEnum>();
		for (DataOperationCodeEnum _enum : values()) {
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
		for (DataOperationCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
