package com.icebreak.util.lang.security;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.util.lang.util.StringUtil;

public enum OperationRoleEnum {
	
	/** 前台用户角色 */
	ROLE_USER("ROLE_USER", "未知异常"),
	
	/** 后台操作人员角色 */
	ROLE_OPERATOR("ROLE_OPERATOR", "执行成功"),
	
	/** 系统角色 */
	ROLE_SYSTEM("ROLE_SYSTEM", "未知异常"),
	
	/** 系统管理员 */
	ROLE_ADMIN("ROLE_ADMIN", "未知异常"),
	
	/** 未登陆用户 */
	ROLE_LOGIN_ANONYMOUS("ROLE_LOGIN_ANONYMOUS", "未知异常"),
	
	/** 正常登陆用户 */
	ROLE_LOGIN_NORMAL("ROLE_LOGIN_NORMAL", "正常登陆用户"),
	
	/** 支付宝登录用户 */
	ROLE_LOGIN_ALIPAY("ROLE_LOGIN_ALIPAY", "支付宝登录用户");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>MemberRoleEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private OperationRoleEnum(String code, String message) {
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
	 * @return MemberRoleEnum
	 */
	public static OperationRoleEnum getByCode(String code) {
		for (OperationRoleEnum _enum : values()) {
			if (StringUtil.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<MemberRoleEnum>
	 */
	public List<OperationRoleEnum> getAllEnum() {
		List<OperationRoleEnum> list = new ArrayList<OperationRoleEnum>();
		for (OperationRoleEnum _enum : values()) {
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
		for (OperationRoleEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
