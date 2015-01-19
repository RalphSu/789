package com.icebreak.p2p.util;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icebreak.util.lang.util.StringUtil;

public class NumberUtil {
	static final Logger	logger	= LoggerFactory.getLogger(NumberUtil.class);
	
	/**
	 * 格式化数字
	 * @param num
	 * @param format
	 * @return
	 */
	public static String format(Number num, String format) {
		DecimalFormat nf = new DecimalFormat(format);
		return nf.format(num);
	}
	
	public static int parseInt(String strInt) {
		return (int) parseLong(strInt, 0);
	}
	
	public static long parseLong(String strLong, long defaultValue) {
		if (StringUtil.isEmpty(strLong))
			return defaultValue;
		try {
			return Long.parseLong(strLong);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("parseLong error", e);
		}
		return defaultValue;
	}
	
	public static double parseDouble(String strDouble, double defaultValue) {
		if (StringUtil.isEmpty(strDouble))
			return defaultValue;
		try {
			return Double.parseDouble(strDouble);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("parseLong error", e);
		}
		return defaultValue;
	}
	
	public static double parseDouble(String strDouble) {
		return parseDouble(strDouble, 0.0);
	}
	
	public static long parseLong(String strLong) {
		return parseLong(strLong, 0);
	}
	
	public static String formatMoney(double num) {
		double tempNum = num / 100.0;
		DecimalFormat nf = new DecimalFormat("###,###,##0.00");
		return nf.format(tempNum);
	}
	
	public static String parseMoneyToString(String strMoney) {
		double tempNum = parseDouble(strMoney) * 100;
		long value = new Double(tempNum).longValue();
		return String.valueOf(value);
	}
	
	public static long parseMoneyToLong(String strMoney) {
		double tempNum = parseDouble(strMoney) * 100;
		long value = new Double(tempNum).longValue();
		return value;
	}
}
