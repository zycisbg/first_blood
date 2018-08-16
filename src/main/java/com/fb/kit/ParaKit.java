package com.fb.kit;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 处理多搜索条件
 * @author shi.g.s
 * @date 2016年9月19日 下午2:48:03
 */
public class ParaKit {

	/**
	 * 自动拼接sql和urlParas
	 * @param obj
	 * @param objSearch
	 * @param paraMap
	 * @return
	 */
	public static String[] makePara(Object obj, String objSearch, Map<String,String[]> paraMap) {
		String[] returnStr = new String[2];
		StringBuffer sqlStr = new StringBuffer(128);
		StringBuffer paraStr = new StringBuffer(128);
		Set<String> nameSet = paraMap.keySet();
		try {
			for(String name:nameSet){
				String[] props = name.split("\\.");
				if(props[0].equals(objSearch)){
					Class<?> type = obj.getClass().getDeclaredField(props[1]).getType();
					Method[] methods = obj.getClass().getMethods();
					String methodTemp = "set"+firstToUpper(props[1]);
					for (int i = 0; i < methods.length; i++) { 
						if(methods[i].getName().equals(methodTemp)){
			                if(type == String.class){
			                	methods[i].invoke(obj,(String)paraMap.get(name)[0]);  
							}else if(type == Integer.class){
								methods[i].invoke(obj, new Integer((String)paraMap.get(name)[0]));  
							}else if(type == Double.class){
								methods[i].invoke(obj,Double.parseDouble((String)paraMap.get(name)[0]));  
							}else if(type == Boolean.class){
								methods[i].invoke(obj,Boolean.getBoolean((String)paraMap.get(name)[0])); 
							}else if(type == Date.class){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
							    Date date = sdf.parse(paraMap.get(name)[0]); 
								methods[i].invoke(obj,date); 
							}else{
								//....
							}
							break;
						}
					}
					if (paraMap.get(name) != null && !paraMap.get(name)[0].equals("")) {
						if(type == String.class){
							if(props[1].equals("value")){//value属性字段不需模糊查询
								sqlStr.append(" and ").append(props[1]).append(" = ").append(paraMap.get(name)[0]).append("");
							}else{
								sqlStr.append(" and ").append(props[1]).append(" like '%").append(paraMap.get(name)[0]).append("%'");
							}
						}else if(type == Integer.class){
							sqlStr.append(" and ").append(props[1]).append("=").append(paraMap.get(name)[0]);
						}else if(type == Double.class){
							sqlStr.append(" and ").append(props[1]).append("=").append(paraMap.get(name)[0]);
						}else if(type == Boolean.class){
							sqlStr.append(" and ").append(props[1]).append("=").append(paraMap.get(name)[0]);
						}else if(type == Date.class){
							sqlStr.append(" and ").append(props[1]).append(" ='").append(paraMap.get(name)[0]).append("'");
						}
						paraStr.append("&").append(name).append("=").append(paraMap.get(name)[0]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		returnStr[0] = sqlStr.toString();
		returnStr[1] = paraStr.toString();
		return returnStr;
	}
	
	public static <T> String[] makePara(T t, Map<String,String[]> paraMap) {
		
		return null;
	}
	
	/**
	 * 首字母大写
	 * @param val
	 * @return
	 */
    public static String firstToUpper(String val) {  
        return val.substring(0, 1).toUpperCase() + val.substring(1);  
    }
}
