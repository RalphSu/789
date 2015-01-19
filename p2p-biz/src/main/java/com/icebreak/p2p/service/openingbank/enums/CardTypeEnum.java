package com.icebreak.p2p.service.openingbank.enums;

import java.util.ArrayList;
import java.util.List;

public enum CardTypeEnum {
	
	/**借记卡 */
	JJ("JJ", "借记卡"),
	/**准贷记卡 */
	ZD("ZD", "准贷记卡"),
	/**预付费卡 */
	YF("YF", "预付费卡"),
	/**贷记卡 */
	DJ("DJ", "贷记卡");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>CardTypeEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private CardTypeEnum(String code, String message) {
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
	 * @return CardTypeEnum
	 */
	public static CardTypeEnum getByCode(String code) {
		for (CardTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CardTypeEnum>
	 */
	public List<CardTypeEnum> getAllEnum() {
		List<CardTypeEnum> list = new ArrayList<CardTypeEnum>();
		for (CardTypeEnum _enum : values()) {
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
		for (CardTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
