package com.icebreak.p2p.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum DivisionWayEnum {
	
	SIT_WAY("sit", "到期还本付息"),

	MONTH_WAY("month", "按月付息，到期还本");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>DivisionWayEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private DivisionWayEnum(String code, String message) {
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
	 * @return DivisionWayEnum
	 */
	public static DivisionWayEnum getByCode(String code) {
		for (DivisionWayEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<DivisionWayEnum>
	 */
	public static List<DivisionWayEnum> getAllEnum() {
		List<DivisionWayEnum> list = new ArrayList<DivisionWayEnum>();
		for (DivisionWayEnum _enum : values()) {
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
		for (DivisionWayEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
