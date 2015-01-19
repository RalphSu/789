package com.icebreak.util.charge.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.icebreak.util.lang.util.StringUtil;

public enum BankEnum {

    SDB("SDB", "深圳发展银行"),

    ICBC("ICBC", "工商银行"),

    ABC("ABC", "农业银行"),

    BOC("BOC", "中国银行"),

    CCB("CCB", "建设银行"),

    COMM("COMM", "交通银行"),

    CMB("CMB", "招商银行"),

    CITIC("CITIC", "中信银行"),

    CEB("CEB", "光大银行"),

    CIB("CIB", "兴业银行"),

    CMBC("CMBC", "民生银行"),

    HXB("HXB", "华夏银行"),

    SPDB("SPDB", "浦发银行"),

    PSBC("PSBC", "邮政储蓄银行"),

    PINGANBK("PINGANBK", "平安银行"),

    BKSH("BKSH", "上海银行"),

    BOBJ("BOBJ", "北京银行"),

    CQTGB("CQTGB", "重庆三峡银行"),

    CGB("CGB", "广发银行"),
    GZCB("GZCB", "广州银行"),
    HSBC("HSBC", "汇丰银行"),
    KMCB("KMCB", "富滇银行"),

    CQRCB("CQRCB", "重庆农村商业银行"),
    CDRCB("CDRCB", "成都农村商业银行"),
    SHRCB("SHRCB", "上海农村商业银行"),
    BJRCB("BJRCB", "北京农村商业银行"),
    TJRCB("TJRCB", "天津农村商业银行"),
    SZRCB("SZRCB", "深圳农村商业银行"),
    GZRCB("GZRCB", "广州农村商业银行"),

    GYCB("GYCB", "贵阳市商业银行"),
    HBCB("HBCB", "哈尔滨银行"),
    WHCB("WHCB", "汉口银行"),
    EBCL("EBCL", "恒丰银行"),
    JLCB("JLCB", "吉林银行"),
    TZBK("TZBK", "台州银行"),
    HZCB("HZCB", "杭州银行"),
    XMCB("XMCB", "厦门银行"),
    LUOYBK("LUOYBK", "洛阳市商业银行"),
    ZHESHANGB("ZHESHANGB", "浙商银行"),
    CQCB("CQCB", "重庆银行"),
    BOCD("BOCD", "成都银行"),
    CBHB("CBHB", "渤海银行"),
    DLCB("DLCB", "大连银行"),
    NJCB("NJCB", "南京银行"),
    TJCB("TJCB", "天津银行"),
    HSB("HSB", "徽商银行"),
    BTCB("BTCB", "包商银行"),
    BONX("BONX", "宁夏银行"),
    NBCB("NBCB", "宁波银行"),
	BOGZ("BOGZ", "贵州银行");

    private final String code;

    private final String message;

	private BankEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

	public static BankEnum getByCode(String code) {
		for (BankEnum _enum : values()) {
			if (StringUtil.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
    }

	public static List<BankEnum> getAllBanks() {
		
		return Arrays.asList(values());
    }

	public static BankEnum getByMessage(String message) {
		for (BankEnum _enum : values()) {
			if (_enum.getMessage().equals(message)) {
				return _enum;
			}
		}
		return null;
    }

	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (BankEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
    }

	public String getCode() {
		return code;
    }

	public String getMessage() {
		return message;
    }

	public String code() {
		return code;
    }

	public String message() {
		return message;
    }

}
