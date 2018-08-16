package com.fb.kit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * json 工具类
 * @author sun
 * @date 2016年8月25日 下午1:39:39
 */
@SuppressWarnings("unchecked")
public class JsonUtils {

	/**
	 * json 转 List<Map<String, Object>>
	 * @author sun
	 * @date 2016年8月25日 下午1:37:56
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * json 转 Map<String, Object>
	 * @author sun
	 * @date 2016年8月25日 下午1:38:17
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new TreeMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * json 转 Map<String, Object>
	 * @author sun
	 * @date 2016年8月25日 下午1:38:51
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> parseJSON2Map2(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		org.json.JSONObject json = new org.json.JSONObject(jsonStr);
		Set se = json.keySet();
		Iterator it = se.iterator();
		while (it.hasNext()) {
			Object key = it.next();
			map.put(key + "", json.get(key + ""));
		}
		return map;
	}

	/**
	 * 对象 转 json (JSONObject)
	 * @author sun
	 * @date 2016年8月25日 下午1:39:00
	 * @param obj
	 * @return
	 */
	public static String obj2Json(Object obj) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		String json = jsonObject.toString();
		return json;
	}

	/**
	 * 对象 转 json (JSONArray方式)
	 * @author sun
	 * @date 2016年8月25日 下午1:39:12
	 * @param obj
	 * @return
	 */
	public static String obj2Json2(Object obj) {
		JSONArray json = JSONArray.fromObject(obj);
		String str = json.toString();
		return str;
	}

}
