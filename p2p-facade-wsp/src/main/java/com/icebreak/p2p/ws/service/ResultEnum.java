package com.icebreak.p2p.ws.service;

import java.util.ArrayList;
import java.util.List;

public enum ResultEnum {
	
	/** 未知异常 */
	UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常"),
	/**请求参数不完整*/
	INCOMPLETE_REQ_PARAM("INCOMPLETE_REQ_PARAM", "请求参数不完整"),
	/**数据库异常*/
	DATABASE_EXCEPTION("DATABASE_EXCEPTION", "数据库异常"),
	/**没有数据*/
	HAVE_NOT_DATA("HAVE_NOT_DATA", "没有数据"),
	
	/**该用户对该数据无访问权限*/
	NO_ACCESS("NO_ACCESS", "该用户对该数据无访问权限"),
	/**执行OPENAPI失败*/
	OPENAPI_ACCESS_FAILURE("OPENAPI_ACCESS_FAILURE", "执行OPENAPI失败"),
	/**OPENAPI重复回执*/
	OPENAPI_REPEAT_NOTIFY("OPENAPI_ACCESS_FAILURE", "OPENAPI重复回执"),
	/**数据处理状态不对或已经处理完成*/
	DO_ACTION_STATUS_ERROR("DO_ACTION_STATUS_ERROR", "数据处理状态不对或已经处理完成"),
	
	/**贷款额度不足*/
	LENDING_AMOUNT_LIMIT("LENDING_AMOUNT_LIMIT", "贷款额度不足"),
	
	/** 执行成功 */
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),
	
	/** 执行失败 */
	EXECUTE_FAILURE("EXECUTE_FAILURE", "执行失败");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>EstateResultEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private ResultEnum(String code, String message) {
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
	 * @return EstateResultEnum
	 */
	public static ResultEnum getByCode(String code) {
		for (ResultEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EstateResultEnum>
	 */
	public List<ResultEnum> getAllEnum() {
		List<ResultEnum> list = new ArrayList<ResultEnum>();
		for (ResultEnum _enum : values()) {
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
		for (ResultEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
