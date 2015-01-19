package com.icebreak.util.lang.enums;

import java.util.ArrayList;
import java.util.List;

public enum CertTypeEnum {
	
	/** 身份证 */
	Identity_Card("ID", "身份证"),
	/** 军官证 */
	Army_Identity_Card("ARMY_ID", "军官证"),
	/** 护照 */
	Passport("PASSPORT", "护照"),
	/** 回乡证 */
	Home_Return_Permit("HOME_RETURN", "回乡证"),
	/** 台胞证 */
	Taiwan_Compatriot_Entry_Permit("TAIWAN", "台胞证"),
	/** 警官证 */
	OFFICERS_CARD("OFFICERS_CARD", "警官证"),
	/** 士兵证 */
	Soldiers_Card("SOLDIER_CARD", "士兵证"),

    BUSINESS_LICENSE("BUSINESS_LICENSE","营业执照"),
    /** 户口簿 */
    HOUSEHOLD_REGISTER("HOUSEHOLD_REGISTER", "户口簿"),

    /** 港澳通行证 */
    HK_MACAO_PASS("HK_MACAO_PASS","港澳通行证"),

	/** 其它证件 */
	Other("Other", "其它证件");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CertTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CertTypeEnum(String code, String message) {
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
	 * @return CertTypeEnum
	 */
	public static CertTypeEnum getByCode(String code) {
		for (CertTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CertTypeEnum>
	 */
	public List<CertTypeEnum> getAllEnum() {
		List<CertTypeEnum> list = new ArrayList<CertTypeEnum>();
		for (CertTypeEnum _enum : values()) {
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
		for (CertTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
