package com.icebreak.p2p.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BusinessNumberUtil {

	/**
	 * 随机数生成器
	 */
	private static Random random = new Random();

	/**
	 * 20位
	 * 
	 * @return
	 */
	public static String gainNumber() {
		StringBuilder number = new StringBuilder();
		number.append(System.currentTimeMillis());
		int i = random.nextInt(9000000) + 1000000;
		number.append(i);
		return number.toString();
	}

	/**
	 * 16位
	 * 
	 * @return
	 */
	public static String gainOutBizNoNumber() {
		StringBuilder number = new StringBuilder();
		number.append(System.currentTimeMillis());
		int i = random.nextInt(900) + 100;
		number.append(i);
		return number.toString();
	}

	public static String getSerialNo() {
		StringBuilder number = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		number.append(sdf.format(new Date()));
		int i = random.nextInt(900000) + 100000;
		number.append(i);
		return number.toString();
	}
}
