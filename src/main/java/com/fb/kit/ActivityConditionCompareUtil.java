package com.fb.kit;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 活动条件范围解析比较工具类
 * 主要用来拿指定的值跟条件表达式进行比较：
 * 例如：
 * 指定时间是否在表达式描述的范围内
 * 指定时间：2016-02-21 00：00：00
 * 比较时会将时间转换为yyyyMMddHHssmm形式
 * 范围表达式：[20160122000000-20160530235959]    
 * 该范围描述的含义为2016年1月22号0点0分0秒到2016年5月30日23点59分59秒之间，包含头包含尾。
 * 
 * 将这个值与表达式比较的结果应当为true
 * 
 * 表达式还可以包含：
 * 区间表达部分："["、"{"、"}"、"]"
 * 
 * 1.{xxxx-xxxx} 表示不包含头 不包含尾
 * 相当于： xxxx<指定值<xxxx      
 * 2.[xxxx-xxxx} 表示包含头 不包含尾
 * 相当于：xxxx<=指定值<xxxx
 * 3.[xxxx-xxxx] 表示包含头 包含尾
 * 相当于：xxxx<=指定值<=xxxx
 * 4.{xxxx-xxxx] 表示不包含头 不包含尾
 * 相当于：xxxx<指定值<=xxxx
 * 
 * 范围表达部分
 * 1.~-xxxx 表示不限制下限
 * 2.xxxx-~ 表示不限制上限
 * @author liucongcong
 *
 */
public class ActivityConditionCompareUtil {
	
	/**
	 * 数字范围表达式正则
	 */
	public static final String EXPRESSION_NUM_REGEX = "[\\[{]([\\d]{1,}|[~]{1})-([\\d]{1,}|[~]{1})[\\]}]";
	/**
	 * 时间范围表达式正则
	 */
	public static final String EXPRESSION_TIME_REGEX = "[\\[{]([\\d]{14}|[~]{1})-([\\d]{14}|[~]{1})[\\]}]";
	
	/**
	 * 传递时间的格式
	 */
	public static final String TIME_FORMAT="yyyyMMddHHmmss";
	/**
	 * 判断时间是否在指定的范围内
	 * @param asignTime 指定时间
	 * @param expression 时间范围表达式
	 * @return 是否满足表达式true满足false不满足
	 */
	public static boolean timeIsInbounds(Date asignTime,String expression){
		if(StringUtils.isEmpty(expression)){
			throw new IllegalArgumentException("指定的数值不能为空！");
		}
		if(null==asignTime){
			throw new IllegalArgumentException("指定的时间不能为空！");
		}
		if(!matchExpressionSyntax(ActivityConditionCompareUtil.EXPRESSION_TIME_REGEX,expression)){
			throw new IllegalArgumentException("指定的时间范围表达式不合法！");
		}
		//将时间转换为Long
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = format.format(asignTime);
		return isInbounds(Long.parseLong(timeStr),expression);
	}
	/**
	 * 判断数字是否在指定的范围内
	 * @param asignNum 指定数字
	 * @param expression 数字范围表达式
	 * @return 是否满足表达式true满足false不满足
	 */
	public static boolean numIsInbounds(Integer asignNum,String expression){
		if(null==asignNum){
			throw new IllegalArgumentException("指定的数值不能为空！");
		}
		return numIsInbounds(Long.valueOf(asignNum.longValue()),expression);
	}
	/**
	 * 判断数字是否在指定的范围内
	 * @param asignNum 指定数字
	 * @param expression 数字范围表达式
	 * @return 是否满足表达式true满足false不满足
	 */
	public static boolean numIsInbounds(Long asignNum,String expression){
		if(StringUtils.isEmpty(expression)){
			throw new IllegalArgumentException("指定的表达式不能为空！");
		}
		if(null==asignNum){
			throw new IllegalArgumentException("指定的数值不能为空！");
		}
		if(!matchExpressionSyntax(ActivityConditionCompareUtil.EXPRESSION_NUM_REGEX,expression)){
			throw new IllegalArgumentException("指定的表达式不合法！");
		}
		return isInbounds(asignNum,expression);
	}
	
	private static boolean isInbounds(Long asignNum,String expression){
		
		//判断表达式是否合法
		//解析表达式的区间部分
		String prefix = expression.substring(0,1);
		String suffix = expression.substring(expression.length()-1);
		String startNumKey = "startNum";
		String endNumKey = "endNum";
		if(prefix.equals("[")){
			//xxx<=指定值
			if(suffix.equals("]")){
				//xxx=<指定值<=xxx
				//解析表达式的范围部分
				Map<String, Long> boundsNum = obtainBoundsNum(startNumKey,endNumKey,expression);
				return GTEAndLTE(startNumKey, endNumKey, boundsNum, asignNum);
			}else if(suffix.equals("}")){
				//xxx=<指定值<xxx
				Map<String, Long> boundsNum = obtainBoundsNum(startNumKey,endNumKey,expression);
				return GTEAndLT(startNumKey, endNumKey, boundsNum, asignNum);
			}
		}else if(prefix.equals("{")){
			//xxx<指定值
			if(suffix.equals("]")){
				//xxx<指定值<=xxx
				Map<String, Long> boundsNum = obtainBoundsNum(startNumKey,endNumKey,expression);
				return GTAndLTE(startNumKey, endNumKey, boundsNum, asignNum);
			}else if(suffix.equals("}")){
				//xxx<指定值<xxx
				Map<String, Long> boundsNum = obtainBoundsNum(startNumKey,endNumKey,expression);
				return GTAndLT(startNumKey, endNumKey, boundsNum, asignNum);
			}
		}
		return false;
	}
	
	//比较包含头包含尾
	private static boolean GTEAndLTE(String startNumKey,String endNumKey,Map<String,Long> boundsNum,Long asignNum){
		Long startNum = boundsNum.get(startNumKey);
		Long endNum = boundsNum.get(endNumKey);
		if(null!=startNum&&null!=endNum){
			return asignNum>=startNum && asignNum<=endNum;
		}else if(null==startNum&&null!=endNum){
			return asignNum<=endNum;
		}else if(null!=startNum&&null==endNum){
			return asignNum>=startNum;
		}
		return false;
	}
	//比较不包含头不包含尾
	private static boolean GTAndLT(String startNumKey,String endNumKey,Map<String,Long> boundsNum,Long asignNum){
		Long startNum = boundsNum.get(startNumKey);
		Long endNum = boundsNum.get(endNumKey);
		if(null!=startNum&&null!=endNum){
			return asignNum>startNum && asignNum<endNum;
		}else if(null==startNum&&null!=endNum){
			return asignNum<endNum;
		}else if(null!=startNum&&null==endNum){
			return asignNum>startNum;
		}
		return false;
	}
	//比较包含头不包含尾
	private static boolean GTEAndLT(String startNumKey,String endNumKey,Map<String,Long> boundsNum,Long asignNum){
		Long startNum = boundsNum.get(startNumKey);
		Long endNum = boundsNum.get(endNumKey);
		if(null!=startNum&&null!=endNum){
			return asignNum>=startNum && asignNum<endNum;
		}else if(null==startNum&&null!=endNum){
			return asignNum<endNum;
		}else if(null!=startNum&&null==endNum){
			return asignNum>=startNum;
		}
		return false;
	}
	//比较不包含头包含尾
	private static boolean GTAndLTE(String startNumKey,String endNumKey,Map<String,Long> boundsNum,Long asignNum){
		Long startNum = boundsNum.get(startNumKey);
		Long endNum = boundsNum.get(endNumKey);
		if(null!=startNum&&null!=endNum){
			return asignNum>startNum && asignNum<=endNum;
		}else if(null==startNum&&null!=endNum){
			return asignNum<=endNum;
		}else if(null!=startNum&&null==endNum){
			return asignNum>startNum;
		}
		return false;
	}
	
	/**
	 * 解析范围表达式数值部分：
	 * ~-xxx 解析成 null - xxx
	 * xxx-~ 解析成 xxx - null
	 * @param startNumKey 指定放入到map中开始值的key 
	 * @param endNumKey 指定放入到map中终止值的key
	 * @param expression 范围表达式
	 * @return Map<String,Long> 解析的结果
	 */
	public static Map<String,Long> obtainBoundsNum(String startNumKey,String endNumKey,String expression){
		Map<String,Long> result = new HashMap<String,Long>();
		//解析表达式的范围部分
		String[] bounds = expression.substring(1, expression.length()-1).split("-");
		String start = bounds[0];
		String end = bounds[1];
		if(!start.equals("~")&&!end.equals("~")){
			//xxx-xxx
			//解析数值
			Long startNum = Long.parseLong(start);
			Long endNum = Long.parseLong(end);
			result.put(startNumKey, startNum);
			result.put(endNumKey, endNum);
		}else if(!start.equals("~")&&end.equals("~")){
			//xxx-~
			Long startNum = Long.parseLong(start);
			result.put(startNumKey, startNum);
			result.put(endNumKey, null);
			
			
		}else if(start.equals("~")&&!end.equals("~")){
			//~-xxx
			Long endNum = Long.parseLong(end);
			result.put(startNumKey, null);
			result.put(endNumKey, endNum);
		}
		return result;
	}
	//--------------ADD--------------
	/**
	 * 将四个条件值转换成表达式
	 * @param prefix
	 * @param suffix
	 * @param start
	 * @param end
	 * @return
	 */
	public static  String convertToCondition(String prefix,String suffix,String start,String end){
		if(StringUtils.isEmpty(start)&&StringUtils.isNotEmpty(end)){
			end=DateUtils.stringtoLong(end)+"";
			return prefix+"~-"+end+suffix;
		}
		
		if(StringUtils.isNotEmpty(start)&&StringUtils.isEmpty(end)){
			start=convertToStr(start)+"";
			return prefix+start+"-~"+suffix;
		}
		if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
			end=DateUtils.stringtoLong(end)+"";
			start=DateUtils.stringtoLong(start)+"";
			return prefix+start+"-"+end+suffix;
		}
		return "";
	}
	/**
	 * 将四个条件值转换成表达式
	 * @param prefix
	 * @param suffix
	 * @param start
	 * @param end
	 * @return
	 */
	public static  String convertToConditionForNum(String prefix,String suffix,String start,String end){
		if(StringUtils.isEmpty(start)&&StringUtils.isNotEmpty(end)){
			return prefix+"~-"+end+suffix;
		}
		
		if(StringUtils.isNotEmpty(start)&&StringUtils.isEmpty(end)){
			return prefix+start+"-~"+suffix;
		}
		if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
			return prefix+start+"-"+end+suffix;
		}
		return "";
	}
	/**
	 * 将时间类型yyyy-MM-dd HH:mm:ss转为yyyyMMddHHmmss
	 * @param value
	 * @return
	 */
	private static String convertToStr(String value){
		if(StringUtils.isNotEmpty(value)){
			if(value.contains("-"))
				return DateUtils.stringtoLong(value,"")+"";
			return value;
		}
		return "";
	}
	/**
	 * 解析范围表达式符号部分：
	 * ~-xxx 解析成 null - xxx
	 * xxx-~ 解析成 xxx - null
	 * @param startNumKey 指定放入到map中开始值的key 
	 * @param endNumKey 指定放入到map中终止值的key
	 * @param expression 范围表达式
	 * @return Map<String,Long> 解析的结果
	 */
	public static Map<String,String> obtainBoundsExp(String startExpKey,String endExpKey,String expression){
		Map<String,String> result = new HashMap<String,String>();
		//解析表达式的范围部分
		if(expression.length()>=1){
			String start = expression.substring(0,1);
			String end = expression.substring(expression.length()-1,expression.length());
			result.put(startExpKey, start);
			result.put(endExpKey, end);
		}
		return result;
	}
	/**
	 * 将[转换为<=，将{转换为<，将]转换为<=，将}转换为<
	 * @param startExpKey
	 * @param endExpKey
	 * @param expression
	 * @return
	 */
	public static Map<String,String> obtainBoundsExp1(String startExpKey,String endExpKey,String expression){
		Map<String,String> result = new HashMap<String,String>();
		//解析表达式的范围部分
		if(expression.length()>=1){
			String start = expression.substring(0,1);
			String end = expression.substring(expression.length()-1,expression.length());
			String startExp="";
			String endExp="";
			if(StringUtils.isNotEmpty(start)&&start.equals("["))
				startExp="<=";
			if(StringUtils.isNotEmpty(start)&&start.equals("{"))
				startExp="<";
			if(StringUtils.isNotEmpty(end)&&end.equals("]"))
				endExp="<=";
			if(StringUtils.isNotEmpty(end)&&end.equals("}"))
				endExp="<";
			result.put(startExpKey, startExp);
			result.put(endExpKey, endExp);
		}
		return result;
	}

	//--------------ADD--------------
	/**
	 * 验证范围表达式格式是否合法
	 * regex使用：
	 * ActivityConditionCompareUtil.EXPRESSION_NUM_REGEX   数字类型正则
	 * ActivityConditionCompareUtil.EXPRESSION_TIME_REGEX  时间类型正则
	 * @param regex 范围表达式的正则表达式
	 * @param expression 范围表达式
	 * @return 是否合法
	 */
	public static boolean matchExpressionSyntax(String regex,String expression){
		return expression.matches(regex);
	}
	
	/**
	 * 获取时间格式化
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(){
		return new SimpleDateFormat(TIME_FORMAT);
	}
	/**
	 * 日期转long格式时间
	 * @param date
	 * @return
	 */
	public static long getLongTimeFormat(Date date){
		return Long.parseLong(getDateFormat().format(date));
	}
	
}
