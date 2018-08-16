package com.fb.kit;

import com.fb.core.Const;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import org.apache.commons.lang.StringUtils;

/**
 * 返回码、错误码
 * @date 2016年10月14日 上午10:32:37
 */
public class ErrorUtils {
	
	static Prop prop = null;
	
	static{
		 prop = PropKit.use(Const.DEFAULT_ERROR_CONFIG_NAME);
	}
	
	/**
	 * 查询对应的message信息
	 * @param codes
	 * @return
	 */
	public static String getMessage(Object codes){
		if(!StringUtils.isEmpty(codes.toString())){
			String[] arr = codes.toString().split(",");
			if(arr!=null && arr.length>0){
				StringBuilder sb = new StringBuilder();
				for(String code:arr){
					if(StringUtils.isNotEmpty(code.trim())){
						sb.append(prop.get(code.trim(), Const.BLANK)).append("，");
					}
				}
				return sb.substring(0, sb.length()-1) + "！";
			}
		}
		return null;
	}
	
	public static void main(String[] args) {//10101
		String message = getMessage("10101");
		System.out.println(message);
	}
}
