package com.icebreak.p2p.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum InsureWayEnum {
	
	/**其他*/
	OTHER("0000000000", "其他"),
	/**担保*/
	GUARANTEE("0000000001", "担保"),
	
	/**信用*/
	CREDIT("0000000010", "信用"),
	
	/**质押*/
	PLEDGE("0000000100", "质押"),
	
	/**抵押*/
	MORTGAGE("0000001000", "抵押");
	

	
	
	
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>InsureWayEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private InsureWayEnum(String code, String message) {
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
	 * @return LoanBizTypeEnum
	 */
	public static InsureWayEnum getByCode(String code) {
		for (InsureWayEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<LoanBizTypeEnum>
	 */
	public static List<InsureWayEnum> getAllEnum() {
		List<InsureWayEnum> list = new ArrayList<InsureWayEnum>();
		for (InsureWayEnum _enum : values()) {
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
		for (InsureWayEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

}
