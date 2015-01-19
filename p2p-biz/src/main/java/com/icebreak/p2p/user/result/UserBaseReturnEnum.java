package com.icebreak.p2p.user.result;

import java.util.ArrayList;
import java.util.List;

public enum UserBaseReturnEnum {
	/** 处理成功 */
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "处理成功"),
	/** 处理失败 */
	EXECUTE_FAILURE("EXECUTE_FAILURE", "处理失败"),
	/**密码错误*/
	PASSWORD_ERROR("PASSWORD_ERROR", "密码错误"),
	/**密码正确*/
	PASSWORD_SUCCESS("PASSWORD_SUCCESS", "密码正确"),
	/**用户名已存在*/
	USERNAME_ERROR("USERNAME_ERROR", "用户名已存在"),
	/**用户名不存在*/
	USERNAME_SUCCESS("USERNAME_SUCCESS", "用户名不存在"),
	/** 未知异常 */
	UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常"),
	/** 数据库异常 */
	DATA_ACCESS_EXCEPTION("DATA_ACCESS_EXCEPTION", "数据库异常");
	
	private final String code;
	private final String message;

	private UserBaseReturnEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

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
	 * @return LogResultEnum
	 */
	public static UserBaseReturnEnum getByCode(String code) {
		for (UserBaseReturnEnum _enum : values()) {
			if (_enum.getCode().equalsIgnoreCase(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 * 
	 * @return List<LogResultEnum>
	 */
	public List<UserBaseReturnEnum> getAllEnum() {
		List<UserBaseReturnEnum> list = new ArrayList<UserBaseReturnEnum>();
		for (UserBaseReturnEnum _enum : values()) {
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
		for (UserBaseReturnEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

}
