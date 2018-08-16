package com.fb.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ${DESCRIPTION}
 * User: zp
 * Date: 2017/4/21
 * Time: 17:13
 * Project: hnjb
 * 版权所有(C) 2017，银信天下网信息科技服务有限公司
 */
public class UploadUtil {


    /**
     * 补充0
     * @param content
     * @param length
     * @return
     */
    private static String contentZero(String content, int length){
        StringBuffer result = new StringBuffer();
        if(StringUtils.isNotEmpty(content)){
            length = length-content.length();
        }else{
        	content="";
        }
        for (int i=0;i<length;i++){
            result.append("0");
        }
        return result.toString()+content;
    }

    /**
     * 补充空格
     * @param content
     * @param length
     * @return
     */
    private static String contentSpace(String content, int length){
        StringBuffer result = new StringBuffer();
        if(StringUtils.isNotEmpty(content)){
            length = length-content.length();
        }else{
        	content="";
        }
        for (int i=0;i<length;i++){
            result.append(" ");
        }
        return content+result.toString();
    }


    /**
     * 中文补充空格
     * @param content
     * @param length
     * @return
     */
    private static String chineseContentSpace(String content, int length){
        StringBuffer result = new StringBuffer();
        if(StringUtils.isNotEmpty(content)){
            length = length-content.length()*2;
        }else{
            content="";
        }
        for (int i=0;i<length;i++){
            result.append(" ");
        }
        return content+result.toString();
    }

    /**
     * 数据保留两位小数,去除小数点
     * @param content
     * @param pointLength 保留小数位位数,如果小数位空直接返回数字
     * @return
     */
    private static String removePoint(String content,Integer pointLength){
    	if(pointLength==null)
    		return content;
        content= StringUtils.isEmpty(content)?"0.0":content;
        Double d=0.0;
        try{
            d=Double.valueOf(content);
        }catch (Exception e){
            d=0.0;
        }
        BigDecimal bg = new BigDecimal(d).setScale(pointLength, BigDecimal.ROUND_HALF_UP);
        String[] contents= (bg+"").split("\\.");
        return contents[0]+contents[1];
    }
    /**
     * 不为空,数值类型的数据处理
     * @param value
     * @param length
     * @param txt 异常内容关键字
     * @param pointLength 保留小数位位数,如果小数位空直接返回数字
     * @return
     */
    public static String notEmptyAndInt(String value,int length,String txt,Integer pointLength){
		if(StringUtils.isEmpty(value))
			throw new IllegalArgumentException(String.format("%s值不可为空", txt));
		if(StringUtils.length(value)>length)
			throw new IllegalArgumentException(String.format("%s长度不能大于%s位", txt,length));
		value=UploadUtil.removePoint(value,pointLength);
		if(StringUtils.length(value)>length)
			throw new IllegalArgumentException(String.format("%s转换后长度不能大于%s位", txt,length));
		value=UploadUtil.contentZero(value, length);
		return value;
    }
    /**
     * 不为空,字符串类型的数据处理
     * @param value
     * @param length
     * @param txt 异常内容关键字
     * @return
     */
    public static String notEmptyAndStr(String value,int length,String txt){
		if(StringUtils.isEmpty(value))
			throw new IllegalArgumentException(String.format("%s值不可为空", txt));
		if(StringUtils.length(value)>length)
			throw new IllegalArgumentException(String.format("%s长度不能大于%s位", txt,length));
		value=UploadUtil.contentSpace(value, length);
		return value;
    }
    /**
     * 不为空,字符串类型的数据处理
     * @param value
     * @param length
     * @param txt 异常内容关键字
     * @return
     */
    public static String chineseNotEmptyAndStr(String value,int length,String txt){
        if(StringUtils.isEmpty(value))
            throw new IllegalArgumentException(String.format("%s值不可为空", txt));
        if(StringUtils.length(value)>length)
            throw new IllegalArgumentException(String.format("%s长度不能大于%s位", txt,length));
        value=UploadUtil.chineseContentSpace(value, length);
        return value;
    }
    /**
     * 可以为空,数值类型的数据处理
     * @param value
     * @param length
     * @param txt 异常内容关键字
     * @param pointLength 保留小数位位数,如果小数位空直接返回数字
     * @return
     */
    public static String emptyAndInt(String value,int length,String txt,Integer pointLength){
		if(StringUtils.length(value)>length)
			throw new IllegalArgumentException(String.format("%s长度不能大于%s位", txt,length));
		value=UploadUtil.removePoint(value, pointLength);
		if(StringUtils.length(value)>length)
			throw new IllegalArgumentException(String.format("%s转换后长度不能大于%s位", txt,length));
		value=UploadUtil.contentZero(value, length);
		return value;
    }
    //为空,字符串
    /**
     * 可以为空,字符串类型的数据处理
     * @param value
     * @param length
     * @param txt 异常内容关键字
     * @return
     */
    public static String emptyAndStr(String value,int length,String txt){
		if(StringUtils.length(value)>length)
			throw new IllegalArgumentException(String.format("%s长度不能大于%s位", txt,length));
		value=UploadUtil.contentSpace(value, length);
		return value;
    }
    
    
    /**
     * 不为空,字符串类型的数据处理
     * @param value
     * @param length
     * @param txt 异常内容关键字
     * @return
     */
    public static String chineseOrCharNotEmptyAndStr(String value,int length,String txt){
        if(StringUtils.isEmpty(value))
            throw new IllegalArgumentException(String.format("%s值不可为空", txt));
        value=UploadUtil.chineseOrCharContentSpace(value, length,txt);
        return value;
    }
    
    /**
     * 中文或者字符补充空格
     * @param content
     * @param length
     * @return
     */
    private static String chineseOrCharContentSpace(String content, int length,String txt){
        StringBuffer result = new StringBuffer();
        if(StringUtils.isNotEmpty(content)){
            byte[] contentBytes;
            int contentBytesLength;
            try {
                contentBytes = content.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("不支持的编码");
            }
            contentBytesLength=contentBytes.length;
            if(contentBytesLength>length)
                throw new IllegalArgumentException(String.format("%s长度不能大于%s位", txt,length));
            length = length-contentBytesLength;
        }else{
            content="";
        }
        for (int i=0;i<length;i++){
            result.append(" ");
        }
        return content+result.toString();
    }
    /**
     * 判读是否是中文内容
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
			flg = true;

		return flg;
	}
    public static void main(String[] args) throws UnsupportedEncodingException {

        String str="haha&&121121&&我爱北京";
       String st=chineseOrCharContentSpace(str,30,"ghag");
        System.out.println(st.length());
//    	String test="真a";
//    	System.out.println(chineseOrCharContentSpace(test,4));
//        String test="231.12";
//        test=UploadUtil.notEmptyAndInt(test,8,null,2);
//        System.out.println(test);
//    	String value=UploadUtil.NotEmptyAndInt("1", 3, "测试",3);
//    	System.out.println(value);
//    	String value1=UploadUtil.NotEmptyAndStr("1111", 3, "测试");
//    	System.out.println(value1);
//    	String value2=UploadUtil.emptyAndInt("1", 3, "测试",null);
//    	System.out.println(value2);
//    	String value3=UploadUtil.EmptyAndStr("", 3, "测试");
//    	System.out.println(value3);
//    	String test=null;
//    	test=UploadUtil.contentZero(test, 6);
//    	System.out.println(test);
//    	String test1="1";
//    	test=UploadUtil.contentSpace(test1, 6);
//    	System.out.println(test);
    	
//    	List<String> strs=	null;
//    	for (String string : strs) {
//			System.out.println(string);
//		}
//    	String test="84";
//    	test=UploadUtil.contentZero(test, 6);
//    	System.out.println(test);
//        String s1=removePoint("");
//        String s2=removePoint("0");
//        String s3=removePoint("1");
//        String s4=removePoint("1.16");
//        String s5=removePoint("200");
//        System.out.println(s1);
//        System.out.println(s2);
//        System.out.println(s3);
//        System.out.println(s4);
//        System.out.println(s5);


    }
}
