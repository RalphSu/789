package com.icebreak.p2p.ext.enums;

import java.util.ArrayList;
import java.util.List;

public enum DepositStatusEnum {
	
	INITIAL("INITIAL", "初始"),
	
	SUBMIT_SETTLED("SUBMIT_SETTLED", "已报清算"),
	
	DEPOSITED("DEPOSITED", "已充值"),
	
	SUCCESS("SUCCESS", "处理成功"),
	
	FAILURE("FAILURE", "处理失败"),
	
	DEPOSIT_BACKED("DEPOSIT_BACKED", "已充退"),
	
	CHARGED("CHARGED", "已收费"),
	
	PRE_SETTLED("PRE_SETTLED", "已预清算"),
	
	CANCELED("CANCELED", "已撤销"),
	
	;
	
	/** 枚举值 */
	private final String							code;
	
	/** 枚举描述 */
	private final String							message;
	
	private static final List<DepositStatusEnum>	successStatus		= new ArrayList<DepositStatusEnum>();
	
	private static final List<String>				successStrStatus	= new ArrayList<String>();
	
	static {
		successStatus.add(DEPOSITED);
		successStatus.add(SUCCESS);
		
		successStrStatus.add(DEPOSITED.getCode());
		successStrStatus.add(SUCCESS.getCode());
	}
	
	/**
	 * 构造一个<code>DepositStatusEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private DepositStatusEnum(String code, String message) {
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
	
	public static List<DepositStatusEnum> getSuccessstatus() {
		return successStatus;
	}
	
	public static List<String> getSuccessstrstatus() {
		return successStrStatus;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return DepositStatusEnum
	 */
	public static DepositStatusEnum getByCode(String code) {
		for (DepositStatusEnum _enum : values()) {
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
	public List<DepositStatusEnum> getAllEnum() {
		List<DepositStatusEnum> list = new ArrayList<DepositStatusEnum>();
		for (DepositStatusEnum _enum : values()) {
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
		for (DepositStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 判断是否为充值成功状态。
	 * 
	 * @param status
	 * @return boolean
	 */
	public static boolean isSuccess(DepositStatusEnum status) {
		return successStatus.contains(status) ? true : false;
	}
}
