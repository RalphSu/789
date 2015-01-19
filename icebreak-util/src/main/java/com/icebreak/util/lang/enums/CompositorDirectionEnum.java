package com.icebreak.util.lang.enums;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.util.lang.util.StringUtil;

public enum CompositorDirectionEnum {
	
	/** 倒序 */
	DESC("DESC", "倒序"),
	
	/** 正序 */
	ASC("ASC", "正序");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CompositorDirectionEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CompositorDirectionEnum(String code, String message) {
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
	 * @return CompositorDirectionEnum
	 */
	public static CompositorDirectionEnum getByCode(String code) {
		for (CompositorDirectionEnum _enum : values()) {
			if (StringUtil.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CompositorDirectionEnum>
	 */
	public List<CompositorDirectionEnum> getAllEnum() {
		List<CompositorDirectionEnum> list = new ArrayList<CompositorDirectionEnum>();
		for (CompositorDirectionEnum _enum : values()) {
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
		for (CompositorDirectionEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
