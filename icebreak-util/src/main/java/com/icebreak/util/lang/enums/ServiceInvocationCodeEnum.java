package com.icebreak.util.lang.enums;

import java.util.ArrayList;
import java.util.List;

public enum ServiceInvocationCodeEnum {
	
	INVOCATE_SUCCESS("INVOCATE_SUCCESS", "调用成功"),
	
	INVOCATE_FAIL("INVOCATE_FAIL", "调用失败"),
	
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),
	
	EXECUTE_FAIL("EXECUTE_FAIL", "执行失败"),
	
	INVOCATE_EXCEPTION("INVOCATE_EXCEPTION", "调用异常"),
	
	EXECUTE_EXCEPTION("EXECUTE_EXCEPTION", "执行异常"),
	
	UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", "未知异常"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>SerciceInvocationCodeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ServiceInvocationCodeEnum(String code, String message) {
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
	 * @return SerciceInvocationCodeEnum
	 */
	public static ServiceInvocationCodeEnum getByCode(String code) {
		for (ServiceInvocationCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SerciceInvocationCodeEnum>
	 */
	public static List<ServiceInvocationCodeEnum> getAllEnum() {
		List<ServiceInvocationCodeEnum> list = new ArrayList<ServiceInvocationCodeEnum>();
		for (ServiceInvocationCodeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (ServiceInvocationCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
