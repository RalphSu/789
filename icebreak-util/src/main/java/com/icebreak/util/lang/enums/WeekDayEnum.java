package com.icebreak.util.lang.enums;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.util.lang.util.StringUtil;

public enum WeekDayEnum {
	
	/** 星期一 */
	MONDAY("MONDAY", "星期一"),
	
	/** 星期二 */
	TUESDAY("TUESDAY", "星期二"),
	
	/** 星期三 */
	WEDNESDAY("WEDNESDAY", "星期三"),
	
	/** 星期四 */
	THURSDAY("THURSDAY", "星期四"),
	
	/** 星期五 */
	FRIDAY("FRIDAY", "星期五"),
	
	/** 星期六 */
	SATURDAY("SATURDAY", "星期六"),
	
	/** 星期天 */
	SUNDAY("SUNDAY", "星期天");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>WeekDayEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private WeekDayEnum(String code, String message) {
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
	 * @return WeekDayEnum
	 */
	public static WeekDayEnum getByCode(String code) {
		for (WeekDayEnum _enum : values()) {
			if (StringUtil.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<WeekDayEnum>
	 */
	public static List<WeekDayEnum> getAllEnum() {
		List<WeekDayEnum> list = new ArrayList<WeekDayEnum>();
		for (WeekDayEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (WeekDayEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * @param weekDays
	 * @return
	 */
	public static List<WeekDayEnum> getWeekDays(String[] weekDays) {
		List<WeekDayEnum> list = new ArrayList<WeekDayEnum>();
		
		for (String weekDat : weekDays) {
			list.add(WeekDayEnum.getByCode(weekDat));
		}
		
		return list;
	}
}
