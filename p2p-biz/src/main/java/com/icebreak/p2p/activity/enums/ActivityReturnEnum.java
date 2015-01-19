package com.icebreak.p2p.activity.enums;

public enum ActivityReturnEnum {
	/** 处理成功 */
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "处理成功"),
	/** 处理失败 */
	EXECUTE_FAILURE("EXECUTE_FAILURE", "处理失败"),
	/** 未知异常 */
	UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常"),
	/** 数据库异常 */
	DATA_ACCESS_EXCEPTION("DATA_ACCESS_EXCEPTION", "数据库异常");
	
	private final String code;
	private final String message;
	private ActivityReturnEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
}
