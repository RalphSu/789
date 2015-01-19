package com.icebreak.util.lang.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(URLUtil.class);
	
	/**
	 * 字符串 URLEncode
	 * @param String source
	 * @return String
	 */
	public static String urlEncode(String source) {
		
		if (StringUtil.isBlank(source)) {
			return null;
		}
		
		try {
			
			return java.net.URLEncoder.encode(source, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			logger.error("URLEncoder 异常 ; 参数  ：source = " + source, e);
		}
		return null;
	}
	
	/**
	 * 字符串 URLDecode
	 * @param String source
	 * @return String
	 */
	public static String urlDecode(String source) {
		
		if (StringUtil.isBlank(source)) {
			return null;
		}
		
		try {
			
			return java.net.URLDecoder.decode(source, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			logger.error("URLDecode 异常 ; 参数  ：source = " + source, e);
		}
		return null;
	}
}
