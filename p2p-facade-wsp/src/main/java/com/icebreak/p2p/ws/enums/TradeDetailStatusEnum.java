package com.icebreak.p2p.ws.enums;

public enum TradeDetailStatusEnum {

	/** 创建中 */
	IT("IT", "创建中"),
	
	/** 申请成功 */
	AS("AS", "申请成功"),

	/** 申请失败 */
	AF("AF", "申请失败"),

	/** 投资支付是失败 */
	PF("PF", "投资支付是失败"),

	/** 投资支付成功 */
	PS("PS", "投资支付成功");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private TradeDetailStatusEnum(String code, String message) {
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
	 * @return TradeDetailStatusEnum
	 */
	public static TradeDetailStatusEnum getByCode(String code) {
		for (TradeDetailStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<TradeDetailStatusEnum>
	 */
	public static java.util.List<TradeDetailStatusEnum> getAllEnum() {
		java.util.List<TradeDetailStatusEnum> list = new java.util.ArrayList<TradeDetailStatusEnum>(
			values().length);
		for (TradeDetailStatusEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (TradeDetailStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		TradeDetailStatusEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(TradeDetailStatusEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
