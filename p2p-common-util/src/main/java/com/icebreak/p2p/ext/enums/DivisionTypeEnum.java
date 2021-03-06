package com.icebreak.p2p.ext.enums;

import java.util.ArrayList;
import java.util.List;

public enum DivisionTypeEnum {

	PROFIT("PROFIT", "利息"),

	REPAY("REPAY", "还款"),

	DEPOSIT("DEPOSIT", "充值"),

	WITHDRAW("WITHDRAW", "提现"),

	INVEST("INVEST", "投资"),

	FEE("FEE", "手续费"),

	LOAN("LOAN", "借款"),

	SIGN("SIGN", "签约"),
	;

	/** 枚举值 */
	private final String							code;

	/** 枚举描述 */
	private final String							message;

	/**
	 * 构造一个<code>DepositStatusEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private DivisionTypeEnum(String code, String message) {
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
	 * @return DepositStatusEnum
	 */
	public static DivisionTypeEnum getByCode(String code) {
		for (DivisionTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<DepositStatusEnum>
	 */
	public List<DivisionTypeEnum> getAllEnum() {
		List<DivisionTypeEnum> list = new ArrayList<DivisionTypeEnum>();
		for (DivisionTypeEnum _enum : values()) {
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
		for (DivisionTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
}
