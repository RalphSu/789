package com.yiji.openapi.sdk.support;

import java.util.Map;
import java.util.TreeMap;

public class Banks {
	public static final String ABC = "ABC"; // //中国农业银行
	public static final String BOC = "BOC"; // 中国银行
	public static final String COMM = "COMM"; // 交通银行
	public static final String CCB = "CCB"; // 中国建设银行
	public static final String CEB = "CEB"; // 中国光大银行
	public static final String CIB = "CIB"; // 兴业银行
	public static final String CMB = "CMB"; // 招商银行
	public static final String CMBC = "CMBC"; // 民生银行
	public static final String CITIC = "CITIC"; // 中信银行
	public static final String CQRCB = "CQRCB"; // 重庆农村商业银行
	public static final String ICBC = "ICBC"; // 中国工商银行
	public static final String PSBC = "PSBC"; // 中国邮政储蓄银行
	public static final String SPDB = "SPDB"; // 浦发银行
	public static final String UNION = "UNION"; // 中国银联
	public static final String CQCB = "CQCB"; // 重庆银行
	public static final String GDB = "GDB"; // 广东发展银行
	public static final String SDB = "SDB"; // 深圳发展银行
	public static final String HXB = "HXB"; // 华夏银行
	public static final String CQTGB = "CQTGB"; // 重庆三峡银行
	public static final String PINGANBANK = "PINGANBANK"; // 平安银行
	public static final String BANKSH = "BANKSH"; // 上海银行

	public static final Map<String, String> BANK_MAPPING = new TreeMap<String, String>();
	static {
		BANK_MAPPING.put("ABC", "中国农业银行");
		BANK_MAPPING.put("BOC", "中国银行");
		BANK_MAPPING.put("COMM", "交通银行");
		BANK_MAPPING.put("CCB", "中国建设银行");
		BANK_MAPPING.put("CEB", "中国光大银行");
		BANK_MAPPING.put("CIB", "兴业银行");
		BANK_MAPPING.put("CMB", "招商银行");
		BANK_MAPPING.put("CMBC", "民生银行");
		BANK_MAPPING.put("CITIC", "中信银行");
		BANK_MAPPING.put("CQRCB", "重庆农村商业银行");
		BANK_MAPPING.put("ICBC", "中国工商银行");
		BANK_MAPPING.put("PSBC", "中国邮政储蓄银行");
		BANK_MAPPING.put("SPDB", "浦发银行");
		BANK_MAPPING.put("UNION", "中国银联");
		BANK_MAPPING.put("CQCB", "重庆银行");
		BANK_MAPPING.put("GDB", "广东发展银行");
		BANK_MAPPING.put("SDB", "深圳发展银行");
		BANK_MAPPING.put("HXB", "华夏银行");
		BANK_MAPPING.put("CQTGB", "重庆三峡银行");
		BANK_MAPPING.put("PINGANBANK", "平安银行");
		BANK_MAPPING.put("BANKSH", "上海银行");
	}

}
