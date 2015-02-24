package com.oms.util;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * 验证签名
 * 
 */
public class SignUtil {

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String token, String signature,
			String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比
		return tmpStr != null ? tmpStr.equals(signature.toLowerCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/**
	 * 微信支付签名算法
	 * 
	 * @param obj
	 *            实体类
	 * @param key
	 *            商户api密钥
	 */
	public static String createPaySignByObj(Object obj, String key) {
		StringBuilder sb = new StringBuilder();
		Class<?> clazz = obj.getClass();

		Field[] fields = clazz.getDeclaredFields();

		List<Field> fieldList = Arrays.asList(fields);
		Comparator<Field> comparator = new Comparator<Field>() {
			@Override
			public int compare(Field f1, Field f2) {
				return f1.getName().compareTo(f2.getName());
			}
		};

		Collections.sort(fieldList, comparator);

		for (Field field : fieldList) {
			field.setAccessible(true);
			if (field.getType().equals(String.class)) {
				try {
					String value = (String) field.get(obj);
					if (value != null && value.trim().length() > 0) {
						sb.append(field.getName()).append("=").append(value)
								.append("&");
					}
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
		if (sb.charAt(sb.length() - 1) == '&') {
			sb.append("key=").append(key);
		}

		String sign = MD5.MD5Encode(sb.toString(), "UTF-8");
		return sign;
	}

	/**
	 * 微信支付签名算法
	 * 
	 * @param map
	 *            键值对
	 * @param key
	 *            商家api密钥
	 * @return
	 */
	public static String createPaySignByMap(Map<String, String> map, String key) {
		StringBuilder sb = new StringBuilder();
		Comparator<String> comparator = new Comparator<String>() {
			@Override
			public int compare(String f1, String f2) {
				return f1.compareTo(f2);
			}
		};
		TreeMap<String, String> treeMap = new TreeMap<String, String>(
				comparator);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String value = entry.getValue();
			if (value != null && value.trim().length() > 0) {
				treeMap.put(entry.getKey(), value);
			}
		}

		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			sb.append(name).append("=").append(value).append("&");
		}
		if (sb.charAt(sb.length() - 1) == '&') {
			sb.append("key=").append(key);
		}

		String sign = MD5.MD5Encode(sb.toString(), "UTF-8");
		return sign;
	}

	/**
	 * jsapi签名,使用SHA1算法
	 * 
	 * @param map
	 * @return
	 */
	public static String createJsapiSignByMap(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		Comparator<String> comparator = new Comparator<String>() {
			@Override
			public int compare(String f1, String f2) {
				return f1.compareTo(f2);
			}
		};
		TreeMap<String, String> treeMap = new TreeMap<String, String>(
				comparator);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String value = entry.getValue();
			if (value != null && value.trim().length() > 0) {
				treeMap.put(entry.getKey(), value);
			}
		}

		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			sb.append(name).append("=").append(value).append("&");
		}
		if (sb.charAt(sb.length() - 1) == '&') {
			sb.deleteCharAt(sb.length() - 1);
		}

		String sign = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(sb.toString().getBytes());
			sign = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sign;
	}

	private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 生成伪随机字符，适用于对唯一性要求不高的随机字符
	 * 
	 * @param length
	 *            随机字符的长度
	 * @return
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

}
