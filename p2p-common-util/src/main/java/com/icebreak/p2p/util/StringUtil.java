package com.icebreak.p2p.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil extends com.icebreak.util.lang.util.StringUtil {

	private final static String REGXP_HTML = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
	private final static String REGXP_IMGTAG = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签
	private final static String REGXP_IMATAGSRCATTRIB = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性
	
	/**
	 * subString("acd中央人民广播电台", 5, "..") 返回： “acd中央人民..”
	 * @param text 原始字符串
	 * @param length 截取长度
	 * @param endWith 超过截取长度时，用来什么字符串省略代替，
	 * @return subString("acd中央人民广播电台", 5, "..") 返回： “acd中央人民..”
	 */
	public static String subString(String text, int length, String endWith) {
		if (text == null) {
			return "";
		}
		int textLength = text.length();
		int byteLength = 0;
		StringBuffer returnStr = new StringBuffer();
		for (int i = 0; i < textLength && byteLength < length * 2; i++) {
			String str_i = text.substring(i, i + 1);
			if (str_i.getBytes().length == 1) {//英文   
				byteLength++;
			} else {//中文   
				byteLength += 2;
			}
			returnStr.append(str_i);
		}
		try {
			if (byteLength < text.getBytes("GBK").length) {//getBytes("GBK")每个汉字长2，getBytes("UTF-8")每个汉字长度为3   
				returnStr.append(endWith);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnStr.toString();
	}

	/***
	 * 删除所有html标签
	 * @param str
	 * @return
	 */
	public static String replaceHtml(String str){
		if(str == null) return "";
		Pattern pattern = Pattern.compile(REGXP_HTML);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static String nullToEmpty(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	/***
	 * 字节长度
	 * @param s
	 * @return
	 */
	public static int getWordCount(String s){
		int  length  =   0 ;
		for ( int  i  =   0 ; i  <  s.length(); i ++ )
		{
			int  ascii  =  Character.codePointAt(s, i);
			if (ascii  >=   0   &&  ascii  <= 255 )
				length ++ ;
			else
				length  +=  2 ;
		}
		return  length;

	}

	/**
	 * 判断一个字符是否占两个字节
	 */
	public static boolean isTwoByte(char c) {
		return !(c >=0 && c <= 255);
	}

	public static String subStr(String str, int subSLength)
			throws UnsupportedEncodingException{
		if (str == null)
			return "";
		else{
			int tempSubLength = subSLength;//截取字节数
			String subStr = str.substring(0, str.length()<subSLength ? str.length() : subSLength);//截取的子串
			int subStrByetsL = subStr.getBytes("GBK").length;//截取子串的字节长度
			//int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度
			// 说明截取的字符串中包含有汉字
			while (subStrByetsL > tempSubLength){
				int subSLengthTemp = --subSLength;
				subStr = str.substring(0, subSLengthTemp>str.length() ? str.length() : subSLengthTemp);
				subStrByetsL = subStr.getBytes("GBK").length;
				//subStrByetsL = subStr.getBytes().length;
			}
			return subStr;
		}
	}


	public static void main(String[] args) {
		String str = "<a>ddddd<b>sdsddsd</b></b>";
		System.out.println(replaceHtml(str));

		String sss = "我要融&大多数%dd%=ddd?dsd";
		sss = sss.replaceAll("&", "＆");
		sss = sss.replaceAll("\\?", "？");
		sss = sss.replaceAll("%", "％");
		sss = sss.replaceAll("=","＝");
		System.out.println(sss);
	}
	
}
