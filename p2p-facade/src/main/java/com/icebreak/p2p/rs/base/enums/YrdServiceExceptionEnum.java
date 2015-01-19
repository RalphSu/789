package com.icebreak.p2p.rs.base.enums;

import java.util.ArrayList;
import java.util.List;

public enum YrdServiceExceptionEnum {
	
	
	/**
	 *不存在此服务
	 */
	NON_EXISTS_SERVICE("NON_EXISTS_SERVICE", "不存在此服务"),
	
	/**
	 *接口已关闭
	 */
	EXTERFACE_IS_CLOSED("EXTERFACE_IS_CLOSED", "接口已关闭"),
	/**
	 *系统错误
	 */
	SYSTEM_ERROR("STEM_ERROR", "系统错误"),
	
	/** 执行成功 */
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功");
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>OpenApiExceptionEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private YrdServiceExceptionEnum(String code, String message) {
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
	 * @return OpenApiExceptionEnum
	 */
	public static YrdServiceExceptionEnum getByCode(String code) {
		for (YrdServiceExceptionEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<OpenApiExceptionEnum>
	 */
	public List<YrdServiceExceptionEnum> getAllEnum() {
		List<YrdServiceExceptionEnum> list = new ArrayList<YrdServiceExceptionEnum>();
		for (YrdServiceExceptionEnum _enum : values()) {
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
		for (YrdServiceExceptionEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	

}
