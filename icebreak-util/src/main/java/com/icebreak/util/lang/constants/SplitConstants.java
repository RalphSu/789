package com.icebreak.util.lang.constants;

public class SplitConstants {
	
	public final static String SEPARATOR_CHAR_COMMA = ",";
	
	public final static String SEPARATOR_CHAR_SLASH = "/";
	
	public final static String SEPARATOR_CHAR_HYPHEN = "-";
	
	public final static String SEPARATOR_CHAR_PERIOD = ".";
	
	public final static String SEPARATOR_CHAR_UNDERLINE = "_";
	
	public final static String SEPARATOR_CHAR_ASTERISK = "*";
	
	public final static String SEPARATOR_CHAR_COLON = ":";
	
	/**
	 * win下的换行符
	 */
	public final static String SEPARATOR_NEWLINE_WIN = "\r\n";
	
	/**
	 * linux,UNIX mac下的换行符 MAC os x之前的系统我们接触不到，他的换行符是\r，不考虑了
	 * 参考：https://en.wikipedia.org/wiki/Newline
	 */
	public final static String SEPARATOR_NEWLINE_LINUX = "\n";
	
	/**
	 * 获取当前系统换行符
	 * @return
	 */
	public static String getNewLineSymbol() {
		return System.getProperty("line.separator", "\n");
	}
	
}
