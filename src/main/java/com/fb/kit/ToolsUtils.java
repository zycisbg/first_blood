package com.fb.kit;

import com.fb.core.Const;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 - 封装
 * @author sun
 * @date 2016年8月23日 上午10:12:30
 */
public class ToolsUtils {

	/**
	 * 生成UUID， 去分隔符 -
	 * @author sun
	 * @date 2016年8月23日 上午10:12:40
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 获取临时上传路径
	 * @author sun
	 * @date 2016年8月23日 上午10:16:42
	 * @param ossDir
	 * @param suffix
	 * @return
	 */
	public static String getUploadPath(String ossDir, String suffix) {
		if(!StringUtils.isEmpty(ossDir) && !StringUtils.isEmpty(suffix)){
			return ossDir + "/" + DateUtils.getDateFormat("yyyyMM") + "/" + DateUtils.getDateFormat("dd") + "/" + DateUtils.getCurrentTimeMillis() + suffix;
		}
		return "";
	}

	/**
	 * 删除本地图片
	 * @author sun
	 * @date 2016年8月23日 上午10:16:57
	 * @param path
	 */
	public static void deleteLocalFile(String path) {
		try {
			File file = new File(path);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断文件目录是否存在 如果不存在，则创建新的文件夹
	 * @author sun
	 * @date 2016年8月23日 上午10:17:13
	 * @param path
	 */
	public static void fileExists(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 去掉字符串两边多余的空格
	 * @author sun
	 * @date 2016年8月2日 下午4:54:18
	 * @param str
	 * @return
	 */
	public static String trim(String str){
		if(!StringUtils.isEmpty(str)){
			return str.trim();
		}
		return str;
	}
	
	/**
	 * 1. 格式化，如果为整数，则整数显示，如果有小数点，则显示小数点(去.0操作)
	 * @author sun
	 * @date 2016年8月2日 下午5:26:43
	 * @param number
	 * @return
	 */
	public static String getPrettyNumber(String number) {
		if(StringUtils.isEmpty(number)){
			return "0";
		}else{
	    	return BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
		}
	}
	
	/**
	 * 2. 正则 格式化，如果为整数，则整数显示，如果有小数点，则显示小数点(去.0操作)
	 * @author sun
	 * @date 2016年8月9日 上午11:07:15
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }
	
	/**
	 * BigDecimal 乘法
	 * @author sun
	 * @date 2016年8月2日 下午5:37:02
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal multiply(String number1, String number2){
		if(!StringUtils.isEmpty(number1) && !StringUtils.isEmpty(number2)){
			return BigDecimal.valueOf(Double.parseDouble(number1)).multiply(BigDecimal.valueOf(Double.parseDouble(number2)));
		}
		return null;
	}
	
	/**
	 * 格式化金额显示方式，加逗号
	 * @author sun
	 * @date 2016年8月9日 上午11:01:13
	 * @param data
	 * @return
	 */
	public static String formatTosepara(double data) {
		DecimalFormat df = new DecimalFormat("#,###.00");
		return df.format(data);
	}
	
	/**
	 * 生成随机数
	 * @author sun
	 * @date 2016年8月25日 下午1:30:00
	 * @param count
	 * @param letters
	 * @param numbers
	 * @return
	 */
	public static String random(int count, boolean letters, boolean numbers){
		return RandomStringUtils.random(count, letters, numbers);
	}
	
	/**
	 * 从指定字符串中生成 随机数
	 * @author sun
	 * @date 2016年8月25日 下午1:33:52
	 * @param count
	 * @param chars
	 * @return
	 */
	public static String random(int count, String chars){
		return RandomStringUtils.random(count, chars);
	}
	
	/**
	 * 枚举遍历form表单参数，拼接字符串返回
	 * @author sun
	 * @date 2016年8月23日 上午10:20:03
	 * @param params
	 * @return
	 */
	public static String iteratorParamsTo(Map<String, String[]> params) {
		String note = Const.BLANK;
//		Map<String, String[]> params = c.getRequest().getParameterMap();
		String queryString = "";  
		for (String key : params.keySet()) {  
		    String[] values = params.get(key);  
		    for (int i = 0; i < values.length; i++) {  
		        String value = values[i];  
				if(key.contains("password")){
					queryString += key + "=" + value + "&";
				}else{
					queryString += key + "=" + value + "&";
				}
		    }  
		}  
		// 去掉最后一个空格  
		if(!StringUtils.isEmpty(queryString)){
			note = queryString.substring(0, queryString.length() - 1);  
		}
		return note;
	}
	
	/**
	 * 日期分割， 开始时间和结束时间
	 * @author sun
	 * @date 2016年9月5日 上午11:32:20
	 * @param rangeDate
	 * @return
	 */
	public static String[] rangeDate(String rangeDate){
		String[] date = new String[2];
		if(StringUtils.isNotEmpty(rangeDate) && rangeDate.indexOf(" - ")!=-1){
			String[] arr = rangeDate.split(" - ");
			if(arr!=null && arr.length==2){
				date[0] = arr[0];
				date[1] = arr[1];
			}
		}
		return date;
	}
	
	/**
	 * 通过身份证号计算年龄
	 * @param idCard
	 * @return
	 */
	public static String calculationAgeByIdCard(String idCard){
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String birthday = "";
		Date date=new Date();     
		Date mydate;
		String year = "";
		if(idCard!=null && !"".equals(idCard)){
			if (idCard.length() == 18) {
				birthday = idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
			}else{
				birthday = "19" + idCard.substring(6, 8)+ "-" + idCard.substring(8, 10) + "-" + idCard.substring(10, 12);
			}
			try {
				mydate = myFormatter.parse(birthday);
				long day=(date.getTime()-mydate.getTime())/(24*60*60*1000) + 1;
				year = new DecimalFormat("###").format(day/365f);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("niu:" + birthday);
		return year;
	}
	

	
	public static String join(String join, Object[] objects) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objects.length; i++) {
			if (i == (objects.length - 1)) {
				sb.append(objects[i]);
			} else {
				sb.append(objects[i]).append(join);
			}
		}

		return sb.toString();
	}
	
	/**
	 * 计算定期宝项目有效期
	 * @return
	 */
	public static String deadLine(Integer term,Integer repayTimePeriod){
		String dead = "";
		String value = term == 30 ? "天" : "月";
		dead = (term * repayTimePeriod) +"";
		return dead + value;
	}
	
	
	public static String formateVal(String str,String val){
		/*<#if startTime??>&startTime=${startTime!}</#if>
		<#if endTime??>&endTime=${endTime!}</#if>
		<#if balanceStart??>&balanceStart=${balanceStart!}</#if>
		<#if balanceEnd??>&balanceEnd=${balanceEnd!}</#if>
		<#if khsource??>&khsource=${khsource!}</#if>
		<#if value??>&value=${value!}</#if>
		<#if userId??>&userId=${userId!}</#if>
		<#if mobile??>&mobile=${mobile!}</#if>
		<#if realname??>&realname=${realname!}</#if>
		<#if referrer??>&referrer=${referrer!}</#if>
		<#if customerId??>&customerId=${customerId!}</#if>*/
		String formatStr = "";
		if(val != null && !"".equals(val)){
			formatStr = "&"+str+"="+val.trim()+" ";
		}
		return formatStr;
	}
	
	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
//		System.out.println(calculationAgeByIdCard("422326750207001"));
		String s = "1,2,3,4,5";
//		System.out.println(stringFormatIn(s));
		System.out.println(getPrettyNumber(""));
	}
	
	/**
	 * add by shi.g.s
	 * 获取充值方式  -------------网银    快捷
	 * rechargeWay---------- 包括各端(PC/M/APP....)的不同充值方式(APP充值/PC快捷支付/PC网银充值/移动WEB站充值/SDK充值...)
	 * @param rechargeWay
	 * @return
	 */
	public static String findRechargeType(String rechargeWay){
		//判断充值方式中是否为网银充值 网银充值带着银行编码 例如 llpay_01050000
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(rechargeWay);
		if(m.matches()){//存在数字则为pc网银充值
			return "网银支付";
		}else{
			return "快捷支付";
		}
		//..........
	}

	/**
	 * 把以逗号分割的字符串转成in条件
	 * @param params
	 * @return
     */
	public static String stringFormatIn(String params){
		params = params.replaceAll("，",",");
		params = params.trim().replace(" ","").replaceAll("\r|\n|\t", "");
		String[] strs = params.split(",");
		if(strs.length <= 0){
			return "'" + params + "'";
		}
		return "'" + params.replaceAll(",","','") + "'";
	}


	/**
	 * 功能：提取文件名的后缀
	 *
	 * @param fileName
	 * @return
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);
	}

	/**
	 * 依据原始文件名生成新文件名
	 * @return
	 */
	public static String getName(String fileName) {
		Random random = new Random();
		return "" + random.nextInt(10000)
				+ System.currentTimeMillis() + getFileExt(fileName);
	}

	/**
	 * 获取文件扩展名
	 *
	 * @return string
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 根据字符串创建本地目录 并按照日期建立子目录返回
	 * @param path
	 * @return
	 */
	public static void mkdir(final String path) {

		File dir = new File( path);
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(src);  

	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    @SuppressWarnings("unchecked")  
	    List<T> dest = (List<T>) in.readObject();  
	    return dest;  
	}
	//
	public static  String  getInsideString(String  str, String strStart, String strEnd ) {
		if ( str.indexOf(strStart) < 0 ){
			return "";
		}
		if ( str.indexOf(strEnd) < 0 ){
			return "";
		}
		return str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
	}

}
