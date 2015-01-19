package com.icebreak.p2p.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum TradeStatusEnum {
	
	/** 待审核 */
	CHECK_PENDING("CHECK_PENDING", 0, "待审核"),
	
	/** 募集中-待成立 */
	TRADING("TRADING", 1, "募集中-待成立"),
	
	/**  已成立 */
	REPAYING("REPAYING", 2, " 已成立"),
	
	/**交易完成 */
	REPAY_FINISH("REPAY_FINISH", 3, "交易完成"),
	
	/** 交易失败 */
	FAILED("FAILED", 4, "交易失败"),
	
	/** 还款失败 */
	REPAYING_FAILD("REPAYING_FAILD", 5, "交易失败"),
	
	/** 担保公司审核中 */
	GUARANTEE_AUDITING("GUARANTEE_AUDITING", 6, "担保公司审核中"),
	
	/** 违约代偿完成 */
	COMPENSATORY_REPAY_FINISH("COMPENSATORY_REPAY_FINISH", 7, "违约代偿完成"),
	
	/** 待还款 */
	DOREPAY("DOREPAY", 8, "待还款");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	/** 存储值 */
	private final int		value;
	
	/**
	 * 构造一个<code>TradeStatusEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private TradeStatusEnum(String code, int value, String message) {
		this.code = code;
		this.message = message;
		this.value = value;
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

	public int getValue() {
		return value;
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
	 * @return TradeStatusEnum
	 */
	public static TradeStatusEnum getByCode(String code) {
		for (TradeStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<TradeStatusEnum>
	 */
	public List<TradeStatusEnum> getAllEnum() {
		List<TradeStatusEnum> list = new ArrayList<TradeStatusEnum>();
		for (TradeStatusEnum _enum : values()) {
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
		for (TradeStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
