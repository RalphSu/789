package com.icebreak.p2p.ext.enums;

import java.util.ArrayList;
import java.util.List;

public enum BankAccountTypeEnum {
	
	/** 对公账户*/
	PUBLIC("PUBLIC", "对公"),
	
	/** 个人账户 */
	PERSONAL("PERSONAL", "对私");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>BankAccountTypeEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private BankAccountTypeEnum(String code, String message) {
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
	 * @return BankAccountTypeEnum
	 */
	public static BankAccountTypeEnum getByCode(String code) {
		for (BankAccountTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<BankAccountTypeEnum>
	 */
	public List<BankAccountTypeEnum> getAllEnum() {
		List<BankAccountTypeEnum> list = new ArrayList<BankAccountTypeEnum>();
		for (BankAccountTypeEnum _enum : values()) {
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
		for (BankAccountTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
