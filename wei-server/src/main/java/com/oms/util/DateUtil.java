package com.oms.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * 将当前时间转换为微信中的CreateTime整数格式
	 * @param _int
	 * @return Date
	 */
	public static int now2Int(){
		long now = System.currentTimeMillis();
		return (int)(now/1000L);
	}
	/**
	 * 将微信中的CreateTime整数格式转换为java.util.Date类型
	 * @param _int
	 * @return Date
	 */
	public static Date int2Date(int _int){
		long _long = _int * 1000L;
		return new Date(_long);
	}
	  
}
