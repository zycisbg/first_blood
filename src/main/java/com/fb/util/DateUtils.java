package com.fb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils{
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATEYEAR = "yyyy";
	public static final String DATEMONTH = "yyyy-MM";
	public static final String DATEHMS = "HH:mm:ss";
	public static final String DATEHOUR = "yyyy-MM-dd-HH";
	public static final String DATETIMETIGHT = "yyyyMMddHHmmss";
	public static final String DATETIGHT = "yyyyMMdd";
	
	/**
	 * @function 将日期转换成字符串
	 * @param date 日期
	 * @return 日期字符串
	 */
	public static String formatDate(Date date) {
		if(date==null){
			date = Calendar.getInstance().getTime();
		}
		return formatDate(date,DATE);
	}
	
	/**
	 * @function 将日期转换成字符串
	 * @param dateStr 日期
	 * @return 日期字符串
	 */
	public static Date parseDateTime(String dateStr) {
		return parseDate(dateStr,DATETIME);
	}
	
	/**
	 * @function 将日期转换成字符串
	 * @param date 日期
	 * @param pattern 格式
	 * @return 日期字符串
	 */
	public static String formatDate(Date date,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
     * 将字符串转换为日期
     * @param dateStr 日期形式的字符串
     * @return 日期
     */
    public static Date parseDate(String dateStr){
		return parseDate(dateStr,DATE);
	}
    
    /**
     * 将字符串转换为日期
     * @param dateStr 日期形式的字符串
     * @param pattern 日期类型
     * @return 日期
     */
    public static Date parseDate(String dateStr,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);			
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}		
	}
    
    /**
	 * 获取n分后的日期
	 * @param rptdate
	 * @param n
	 * @return
	 */
	public static Date getTheNextMinute(Date rptdate,Integer n){	
		Calendar cd = Calendar.getInstance();
		cd.setTime(rptdate);	
		cd.add(Calendar.MINUTE, n);		 	
		return cd.getTime();
	}
    
    /**
	 * 获取n天后的日期
	 * @param rptdate
	 * @param n
	 * @return
	 */
	public static Date getTheNextDate(Date rptdate,Integer n){	
		Calendar cd = Calendar.getInstance();
		cd.setTime(rptdate);	
		cd.add(Calendar.DATE, n);		 	
		return cd.getTime();
	}
	
}
