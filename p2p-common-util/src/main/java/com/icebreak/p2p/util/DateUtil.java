package com.icebreak.p2p.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

	public static final String		dtSimpleYmdh		= "yyyy-MM-dd HH:00:00";
	
	public static final String		dtSimpleYmdhms		= "yyyy-MM-dd HH:mm:ss";
	
	private static SimpleDateFormat	format				= new SimpleDateFormat(dtSimpleYmdhms);
	
	public static final String		dtSimpleYmdhChinese	= "yyyy年MM月dd日 HH:00:00";
	
	public static final String		mdhChinese			= "yyyy年MM月dd日";
	
	public static final String		MdHmsChinese		= "MM月dd日   HH:mm:ss";
	
	/** 完整时间 yyyy-MM-dd */
	public static final String		dtSimple			= "yyyy-MM-dd";
	
	public static String simpleMdHmsChinese(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(MdHmsChinese).format(date);
	}
	
	public static String simpleDateFormatChinese(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(dtSimpleYmdhChinese).format(date);
	}
	
	public static String simpleDateFormatmdhChinese(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(mdhChinese).format(date);
	}
	
	/**
	 * 获取格式
	 * 
	 * @param format
	 * @return
	 */
	public static final DateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}
	
	/**
	 * yyyy-MM-dd HH:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static final String simpleFormatYmdh(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(dtSimpleYmdh).format(date);
	}
	
	public static final String simpleFormatYmdhms(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(dtSimpleYmdhms).format(date);
	}
	
	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static final String simpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(dtSimple).format(date);
	}
	
	/**
	 * 获取指定时间的前一天时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeforeDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取指定时间的后一天时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getAfterDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取一周后的日期(当前日期往后推7天)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekdayAfterDate(Date date) {
		long beforeTime = (date.getTime() / 1000) + 24 * 60 * 60 * 7;
		date.setTime(beforeTime * 1000);
		return date;
	}
	/**
	 * 获取一周前的日期(当前日期往前推7天)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekdayBeforeDate(Date date) {
		long beforeTime = (date.getTime() / 1000) - 24 * 60 * 60 * 7;
		date.setTime(beforeTime * 1000);
		return date;
	}
	
	/**
	 * 获取一周前的日期(当前日期往前推7天)
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekdayBeforeNow(Date date) {
		java.text.Format formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		long beforeTime = (date.getTime() / 1000) - 24 * 60 * 60 * 7;
		date.setTime(beforeTime * 1000);
		return formatter.format(date);
	}
	/**
	 * 获取一周后的日期(当前日期往后推7天)
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekdayAfterDateNow(Date date) {
		java.text.Format formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		long beforeTime = (date.getTime() / 1000) + 24 * 60 * 60 * 7;
		date.setTime(beforeTime * 1000);
		return formatter.format(date);
	}
	/**
	 * 获取一月前的日期(当前日期往前推7天)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthdayBeforeDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取一月前的日期(当前日期往前推7天)
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthdayBeforeNow(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		date = calendar.getTime();
		java.text.Format formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	/**
	 * 获取指定时间的前三月时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getThreeMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + 1);
		calendar.add(Calendar.MONTH, -3);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取指定时间的前六月时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSixMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -6);
		date = calendar.getTime();
		return date;
	}
	
	/***
	 * 获取指定时间所在月份第一天字符串
	 * @param date
	 * @return
	 */
	public static String getCurrMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 01);
		java.text.Format formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(calendar.getTime());
	}
	
	/***
	 * 获取指定时间所在月份的上个月第一天字符串
	 * @param date
	 * @return
	 */
	public static String getLastMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 01);
		java.text.Format formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(calendar.getTime());
	}
	
	/**
	 * 转换日期yyyy-MM-dd hh:mm:ss
	 * @param source
	 * @return
	 * @throws  
	 */
	public static Date parse(String source, Date defaultDate) {
		try {
			return format.parse(source);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return defaultDate;
		}
	}
	
	/**
	 * 转换日期yyyy-MM-dd hh:mm:ss
	 * @param source
	 * @return
	 * @throws  
	 */
	public static Date parse(String source) {
		return parse(source, new Date());
	}
	
	/**
	 * 日期加月
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getDateByMonth(Date date, int months) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	
	public static String getDateByLongStr(String unixDate) {
		SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long unixLong = 0;
		String date = "";
		try {
			unixLong = Long.parseLong(unixDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			date = fm1.format(unixLong);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}
	
	/**
	* 判断一个日期是否日节假日
	* 法定节假日只判断月份和天，不判断年
	* @param date
	* @return
	*/
	public static boolean isFestival(Date date) {
		boolean festival = false;
		Calendar fcal = Calendar.getInstance();
		Calendar dcal = Calendar.getInstance();
		dcal.setTime(date);
		//		List<Date> list = getFestival();
		//		for (Date dt : list) {
		//			fcal.setTime(dt);
		//			
		//			//法定节假日判断
		//			if (fcal.get(Calendar.MONTH) == dcal.get(Calendar.MONTH)
		//				&& fcal.get(Calendar.DATE) == dcal.get(Calendar.DATE)) {
		//				festival = true;
		//			}
		//		}
		return festival;
	}
	
	private static List<Date> getSpecialWorkDay() {
		List<Date> specialWork = new ArrayList<Date>();
		String specialWorkDays = PropertiesUtil
			.getCommonConfigProperty("specialWorkingDays");
		String[] specialWorkDaysArr = specialWorkDays.split(",");
		if (specialWorkDaysArr.length > 0) {
			for (String day : specialWorkDaysArr) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse(day);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				specialWork.add(date);
			}
		}
		return specialWork;
	}
	
	/**
	* 周六周日判断
	* @param date
	* @return
	*/
	public static boolean isWeekend(Date date) {
		boolean weekend = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
			|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			weekend = true;
		}
		return weekend;
	}
	
	/**
	* 是否是工作日
	* 法定节假日和周末为非工作日
	* @param date
	* @return
	*/
	public static boolean isWorkDay(Date date) {
		boolean workday = true;
		if (isFestival(date) || isWeekend(date)) {
			workday = false;
		}
		return workday;
	}

	public boolean isBeforeNow(Date date) {
		return (new Date()).before(date);
	}
	
	public static void main(String[] args) {
		System.err.println(isWorkDay(new Date()));
	}
}
