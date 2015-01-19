package com.icebreak.p2p.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum LoanLimitUnitEnum {
	
	/**  按天 */
	LOAN_BY_DAY("D", " 按天"),
	/**  按周期 */
	LOAN_BY_PEROIDW("W", "按周期"),
	/**  按月 */
	LOAN_BY_MONTH("M", "按月"),
	/**  按年 */
	LOAN_BY_YEAR("Y", "按年");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>LoanLimitUnitEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private LoanLimitUnitEnum(String code, String message) {
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
	 * @return LoanLimitUnitEnum
	 */
	public static LoanLimitUnitEnum getByCode(String code) {
		for (LoanLimitUnitEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<LoanLimitUnitEnum>
	 */
	public List<LoanLimitUnitEnum> getAllEnum() {
		List<LoanLimitUnitEnum> list = new ArrayList<LoanLimitUnitEnum>();
		for (LoanLimitUnitEnum _enum : values()) {
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
		for (LoanLimitUnitEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
