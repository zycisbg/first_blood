package com.fb.kit;

import com.fb.commons.DateUtil;
import com.fb.core.CommonConst;
import com.google.common.collect.Lists;
import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 日期相关的封装类 apache common-lang包
 * @author sun
 * @date 2016年8月23日 上午10:10:00
 */
public class DateUtils {
	protected static final Logger LOG = Logger.getLogger(DateUtils.class);
	
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String TIME_FORMAT = "HH:mm:ss";
	
	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

	private static final Object object = new Object();
	
	/**
	 * 获取当前日期 Date类型
	 * @author sun
	 * @date 2016年8月23日 上午10:55:21
	 * @return
	 */
	public static Date getDate(){
		return new Date();
	}
	
	/**
	 * 获取系统当前毫秒时间 - 时间戳
	 * @author sun
	 * @date 2016年8月23日 上午10:13:34
	 * @return
	 */
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
	/**
	 * 按照指定格式获取日期字符串, 当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getDateFormat(String pattern) {
		Date date = DateUtils.getDate();
		if(StringUtils.isEmpty(pattern)){
			pattern = DATETIME_FORMAT;
		}
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 丰付上传时间获取
	 * @return
     */
	public static String getNextDate(){
		String nowDate=DateUtils.getDateFormat(DateUtils.DATE_FORMAT);
		String result = nowDate+" 18:00:00";
		Date nowTime=DateUtils.getDate();
		Date targetDate=DateUtils.StringToDate(result,DATETIME_FORMAT);
		if(targetDate.getTime()<=nowTime.getTime()){
			Date date= DateUtil.addDay(new Date(),1);
			return DateUtils.DateToString(date,"yyyyMMdd");
		}else{
			return DateUtils.getDateFormat("yyyyMMdd");
		}
	}
	/**
	 * 格式化日期
	 * @author sun
	 * @date 2016年8月23日 上午10:55:58
	 * @param date	日期
	 * @param pattern	格式，可为空，不传默认yyyy-MM-dd
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if(StringUtils.isEmpty(pattern)){
			pattern = DATE_FORMAT;
		}
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	/**
	 * 获取两个日期间的所有日期
	 * @author sun
	 * @date 2016年8月5日 下午4:47:44
	 * @param begin	开始日期
	 * @param end	结束日期
	 * @return	List 集合
	 */
	public static List<String> twoDayBetweenAddList(String begin, String end){
		List<String> list = Lists.newArrayList();
		if (begin.length() < 11) {
			begin = begin + " 00:00:00";
		}
		if (end.length() < 11) {
			end = end + " 23:59:59";
		}
		Long day = 0L;
		Long beginDateTime = parseDate(begin, DATETIME_FORMAT).getTime();
		Long endDateTime = parseDate(end, DATETIME_FORMAT).getTime();
		Long number = endDateTime - beginDateTime;
		if(number!=null && number>0){
			if(number % 86400000==0){
				day = number / 86400000;
			}else{
				day = number / 86400000 + 1;
			}
		}
		if(day!=null && day>0){
			for(int i=0;i<day;i++){
				list.add(longToStr(beginDateTime, DATE_FORMAT));
				beginDateTime = beginDateTime+86400000;
			}
		}
		return list;
	}
	
	/**
	 * Long 转 String 类型
	 * @author sun
	 * @date 2016年8月23日 上午10:58:54
	 * @param date	long类型日期
	 * @param pattern	格式 默认是yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static final String longToStr(long date, String pattern) {
		if (date == 0) {
			date = DateUtils.getCurrentTimeMillis();
		}
		if (StringUtils.isEmpty(pattern)) {
			pattern = DATETIME_FORMAT;
		}
		return DateFormatUtils.format(date, pattern);
	}
	
	/**
	 * String 转 Date
	 * @author sun
	 * @date 2016年8月23日 上午11:01:07
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String date, String pattern) {
		try {
			if(StringUtils.isEmpty(date))
				return null;
			if (StringUtils.isEmpty(pattern)) {
				pattern = DATETIME_FORMAT;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取上个月的第一天和最后一天
	 * @author sun
	 * @date 2016年8月23日 上午11:04:03
	 * @param flag
	 * @return
	 */
	public static String getPreMonthFirstAndLast(boolean flag) {
		Date nowdate = DateUtils.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        /** 设置为当前时间 **/
        cal.setTime(nowdate);
        /** 当前日期月份-1 **/
        cal.add(Calendar.MONTH, -1);
        if(flag){
        	/** 得到前一个月的第一天 **/ 
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        }else{
        	/** 得到前一个月的最后一天 **/ 
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return sdf.format(cal.getTime());
	}

	/**
	 * 获取前一天
	 * @author sunyiqing
	 * @date 2017年10月11日
	 * @return
	 */
	public static String getLastDate() {
		Date nowdate = DateUtils.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		/** 设置为当前时间 **/
		cal.setTime(nowdate);
		/** 当前日期月份-1 **/
		cal.add(Calendar.DATE,-1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取N天前的日期
	 * @author sun
	 * @date 2016年8月23日 上午11:08:14
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String beforeDay(Integer number, String pattern) {
		if (number == null) {
			number = 7;
		} else {
			if (number <= 1) {
				number = 2;
			}
		}
		Long nowTime = DateUtils.getCurrentTimeMillis();// 当前毫秒数
		Long dateTime = nowTime - 86400000L * (number - 1);// 过去number天的毫秒数

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);
		String result = sdf.format(calendar.getTime());
		return result;
	}

	/**
	 * N小时前、N秒前等显示格式
	 * @author sun
	 * @date 2016年8月23日 上午11:11:07
	 * @param time
	 * @return
	 */
	public static String relativeDateFormat(Long time) {
		String str = null;
		try {
			long ONE_MINUTE = 60000L;
			long ONE_HOUR = 3600000L;
			long ONE_DAY = 86400000L;
			long ONE_WEEK = 604800000L;
			String ONE_SECOND_AGO = "秒前";
			String ONE_MINUTE_AGO = "分钟前";
			String ONE_HOUR_AGO = "小时前";
			String ONE_DAY_AGO = "天前";
			String ONE_MONTH_AGO = "月前";
			String ONE_YEAR_AGO = "年前";

			Date date = new Date(time);

			long delta = DateUtils.getDate().getTime() - date.getTime();
			if (delta < 1L * ONE_MINUTE) {
				long seconds = toSeconds(delta);
				return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
			}
			if (delta < 45L * ONE_MINUTE) {
				long minutes = toMinutes(delta);
				return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
			}
			if (delta < 24L * ONE_HOUR) {
				long hours = toHours(delta);
				return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
			}
			if (delta < 48L * ONE_HOUR) {
				return "昨天";
			}
			if (delta < 30L * ONE_DAY) {
				long days = toDays(delta);
				return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
			}
			if (delta < 12L * 4L * ONE_WEEK) {
				long months = toMonths(delta);
				return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
			} else {
				long years = toYears(delta);
				return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
			}
		} catch (Exception e) {
		}
		return str;
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

	/**
	 * String 转 Long 类型
	 * @author sun
	 * @date 2016年8月23日 上午11:15:01
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Long strToLong(String str, String pattern) {
		Long time = 0L;
		if (str != null) {
			if (StringUtils.isEmpty(pattern)) {
				pattern = DATE_FORMAT;
			}
			try {
				DateFormat df = new SimpleDateFormat(pattern);
				Date date = df.parse(str);
				time = date.getTime();
			} catch (Exception e) {
				// 转换出错
			}
		}
		return time;
	}

	/**
	 * 获取系统最大时间
	 * @author sun
	 * @date 2016年8月23日 上午11:17:18
	 * @return
	 */
	public static Date getMaxDate() {
		String date = "2036-10-01 12:00:00";
		DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 为日期增加当天的开始时间
	 * @author sun
	 * @date 2016年9月5日 上午11:42:01
	 * @param date
	 * @return
	 */
	public static String dateStart(String date){
		String result = null;
		if(StringUtils.isNotEmpty(date)){
			result = date+" 00:00:00";
		}
		return result;
	}
	
	/**
	 * 为日期增加当天的结束时间
	 * @author sun
	 * @date 2016年9月5日 上午11:42:13
	 * @param date
	 * @return
	 */
	public static String dateEnd(String date){
		String result = null;
		if(StringUtils.isNotEmpty(date)){
			result = date+" 23:59:59";
		}
		return result;
	}
	
	/**
	 * 结束日期
	 * @param date
	 * @param num
	 * @param type(month,day)
	 * @return
	 */
	public static Date getEndDate(Date date, Integer num, String type){
		Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        if("month".equals(type)){
        	calender.add(Calendar.MONTH, num);
        }else{
        	calender.add(Calendar.DATE, num);
        }
		return calender.getTime();
	}
	
	/**
	 * 结束倒计时
	 * @param date
	 * @return
	 */
	public static String getCountdown(Date date, Integer num, String type){
		try{
			Date d = getEndDate(date,num,type);
			SimpleDateFormat this_date = new SimpleDateFormat("yyyy-MM-dd");
			String d_ = this_date.format(new Date());
			Date a = this_date.parse(d_);
			long this_time = a.getTime();
			long end_time = d.getTime();
			long poor = end_time - this_time;
			if(poor>0){
				long day = poor/86400000;
				return ""+(day==0?1:day);
			}else{
				return "0";
			}
		}catch(Exception e){
			LOG.error(e.toString());
			return "0";
		}
	}
	/**
	 * 将20161001235959转换为2016-10-01 23:59:59
	 * @param pattern
	 * @return
	 * add by sunyiqing 
	 */
	public static String longToString(Long datel,String pattern){
		if(datel==null)
			return "";
		if(datel==0)
			return "";
		if(StringUtils.isEmpty(pattern))
			pattern=DATETIME_FORMAT;
		String dateStr=datel.toString();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date date = sdf.parse(dateStr);
			SimpleDateFormat sdf1=new SimpleDateFormat(pattern);
			String result = sdf1.format(date);
			return result;
		} catch (ParseException e) {
			return "";
		}
	}

	/**
	 * 将20161001235959转换为2016-10-01 23:59:59
	 * @param pattern
	 * @return
	 * add by sunyiqing
	 */
	public static String stringDateFormat(String date,String pattern){
		if(StringUtils.isEmpty(pattern))
			pattern=DATETIME_FORMAT;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		try {
			Date date1 = sdf.parse(date);
			SimpleDateFormat sdf1=new SimpleDateFormat(pattern);
			String result = sdf1.format(date1);
			return result;
		} catch (ParseException e) {
			return "";
		}
	}
	/**
	 * 将2016-10-01 23:59:59转换为20161001235959
	 * @return
	 * add by sunyiqing
	 */
	public static Long stringtoLong(String dateStr){
		if(StringUtils.isEmpty(dateStr))
			return 0l;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf1.parse(dateStr);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String result = sdf2.format(date);
			return Long.parseLong(result);
		} catch (ParseException e) {
			return 0L;
		}
	}
	/**
	 * endOrStartKey作为标识,开始日期还是结束日期，拼凑完整时间
	 * 将2016-10-01 23:59:59转换为20161001235959
	 * @return
	 * add by sunyiqing
	 */
	public static Long stringtoLong(String dateStr,String endOrStartKey){
		if(StringUtils.isEmpty(dateStr))
			return 0l;
		try {
			if(endOrStartKey.equals(CommonConst.Common.ACTIVITY_START_TIME_KEY))
				dateStr=DateUtils.dateStart(dateStr);
			if(endOrStartKey.equals(CommonConst.Common.ACTIVITY_END_TIME_KEY))
				dateStr=DateUtils.dateEnd(dateStr);
			if(endOrStartKey.equals(CommonConst.Common.ACTIVITY_PRE_TIME_KEY))
				dateStr=DateUtils.dateStart(dateStr);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf1.parse(dateStr);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String result = sdf2.format(date);
			return Long.parseLong(result);
		} catch (ParseException e) {
			return 0L;
		}
	}
	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormatD(String pattern)
			throws RuntimeException {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern, Locale.US);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}
	
	/**
	 * 将日期转化为日期字符串。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, String pattern) {
		String dateString = null;
		if (date != null) {
			try {
				dateString = getDateFormatD(pattern).format(date);
			} catch (Exception e) {
			}
		}
		return dateString;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dateStyle
	 *            日期风格
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, DateStyle dateStyle) {
		String dateString = null;
		if (dateStyle != null) {
			dateString = DateToString(date, dateStyle.getValue());
		}
		return dateString;
	}
	
	/**
	 * 格式化特殊字符串  10/24/2016
	 * @param date
	 * @return
	 */
	public static String formatSpecial(String date){
		String dateString = null;
		if(date!=null && !"".equals(date)){
			if(date.contains("/")){
				String[] str = date.split("/");
				dateString = str[2] + "-" + str[0] + "-" + str[1];
			}
		}
		return dateString;
	}
	
	/**
	 * 获取两个日期相差的天数，后一个日期的天数减去前一个日期的天数。也就是说，如果后者时间大于前者，则返回正值，否则返回负值。
	 * 
	 * @param date
	 *            日期
	 * @param otherDate
	 *            另一个日期
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int calculateIntervalDays(Date date, Date otherDate) {
		int num = -1;
		Date dateTmp = DateUtils.StringToDate(DateUtils.getDate(date),
				DateStyle.YYYY_MM_DD);
		Date otherDateTmp = DateUtils.StringToDate(DateUtils.getDate(otherDate),
				DateStyle.YYYY_MM_DD);
		if (dateTmp != null && otherDateTmp != null) {
			long time = otherDateTmp.getTime() - dateTmp.getTime();
			num = (int) (time / (24 * 60 * 60 * 1000));
		}
		return num;
	}
	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dateStyle
	 *            日期风格
	 * @return 日期
	 */
	public static Date StringToDate(String date, DateStyle dateStyle) {
		Date myDate = null;
		if (dateStyle != null) {
			myDate = StringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}
	
	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 日期
	 */
	public static String getDate(Date date) {
		return DateToString(date, DateStyle.YYYY_MM_DD);
	}
	
	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 日期
	 */
	public static Date StringToDate(String date, String pattern) {
		Date myDate = null;
		if (date != null) {
			try {
				myDate = getDateFormatSimple(pattern).parse(date);
			} catch (Exception e) {
			}
		}
		return myDate;
	}
	

	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormatSimple(String pattern)
			throws RuntimeException {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern, Locale.US);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	public static int getIntervalDaysToToday(Date date) {
		return getIntervalDays(date, new Date());
	}

	/**
	 * @param date
	 *            日期
	 * @param otherDate
	 *            另一个日期
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int getIntervalDays(Date date, Date otherDate) {
		int num = -1;
		Date dateTmp = DateUtils.StringToDate(DateUtils.getDate(date),
				DateStyle.YYYY_MM_DD);
		Date otherDateTmp = DateUtils.StringToDate(DateUtils.getDate(otherDate),
				DateStyle.YYYY_MM_DD);
		if (dateTmp != null && otherDateTmp != null) {
			long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
			num = (int) (time / (24 * 60 * 60 * 1000));
		}
		return num;
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}
	/**
	 * 获取前一个月
	 * @author sunyiqing
	 * @date 2017年10月11日
	 * @return
	 */
	public static String getLastMonth(String lastdate) {
		Date date=DateUtils.parseDate(lastdate,DATE_FORMAT);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		/** 设置为当前时间 **/
		cal.setTime(date);
		/** 当前日期月份-1 **/
		cal.add(Calendar.MONTH,-1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取上个月的第一天和最后一天
	 * @author sun
	 * @date 2016年8月23日 上午11:04:03
	 * @param flag
	 * @return
	 */
	public static String getMonthFirstAndLast(String dateStr,boolean flag) {
		Date date=DateUtils.parseDate(dateStr,DATE_FORMAT);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		/** 设置为当前时间 **/
		cal.setTime(date);
		/** 当前日期月份-1 **/
		cal.add(Calendar.MONTH, 0);
		if(flag){
			/** 得到前一个月的第一天 **/
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		}else{
			/** 得到前一个月的最后一天 **/
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return sdf.format(cal.getTime());
	}

	public static void main(String[] args) {
		DateUtils.stringDateFormat("20100101",DateUtils.DATE_FORMAT);
	}
}
