package com.icebreak.p2p.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum SysUserRoleEnum {
	
	/** 操作员14 */
	OPERATOR("14", 14, "操作员", "operator"),
	/** 借款人 13 */
	LOANER("13", 13, "借款人", "loaner"),
	/** 投资人 12 */
	INVESTOR("12", 12, "投资人", "investor"),
	/** 经纪人11 */
	BROKER("11", 11, "经纪人", "broker"),
	/** 运营机构10 */
	MARKETING("10", 10, "运营机构", "marketing"),
	/** 保荐机构9 */
	SPONSOR("9", 9, "保荐机构", "sponsor"),
	/** 担保机构8 */
	GUARANTEE("8", 8, "担保机构", "guarantee"),
	/** 平台金融7 */
	PLATFORM("7", 7, "平台金融", "platform"),
	/** 平台金融 6 */
	ADMIN3("6", 6, "系统管理员", "admin"),
	/** 系统管理员2 5 */
	ADMIN2("5", 5, "系统管理员", "admin"),
	/** 系统管理员1 4 */
	ADMIN1("4", 4, "系统管理员", "admin"),
	/** 大众1 */
	PUBLIC("1", 1, "大众", "public");
	
	/** 角色id */
	private final String code;
	
	/** 角色编码 */
	private final String roleCode;
	
	/** 枚举描述 */
	private final String message;
	/** 角色id */
	private final int value;
	
	/**
	 * 构造一个<code>SysUserRoleEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private SysUserRoleEnum(String code, int value, String message, String roleCode) {
		this.code = code;
		this.message = message;
		this.value = value;
		this.roleCode = roleCode;
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
	
	public int getValue() {
		return value;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return SysUserRoleEnum
	 */
	public static SysUserRoleEnum getByCode(String code) {
		for (SysUserRoleEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 通过值判断是否是预置的角色
	 * @param value
	 * @return
	 */
	public static boolean isSysRoleByValue(int value) {
		for (SysUserRoleEnum _enum : values()) {
			if (_enum.getValue() == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SysUserRoleEnum>
	 */
	public List<SysUserRoleEnum> getAllEnum() {
		List<SysUserRoleEnum> list = new ArrayList<SysUserRoleEnum>();
		for (SysUserRoleEnum _enum : values()) {
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
		for (SysUserRoleEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	public static int[] getBaseRoleIds() {
		
		return new int[] { PUBLIC.value, BROKER.value, INVESTOR.value, LOANER.value, ADMIN1.value,
							ADMIN2.value, ADMIN3.value };
	}
	
	public static int[] getGeneralUserRoleIds() {
		
		return new int[] { PUBLIC.value, INVESTOR.value };
	}
	
	public static int[] getJGRoleIds() {
		
		return new int[] { PUBLIC.value, GUARANTEE.value, SPONSOR.value, MARKETING.value,
							INVESTOR.value, LOANER.value, PLATFORM.value };
	}
}
