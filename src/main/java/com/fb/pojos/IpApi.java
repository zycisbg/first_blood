package com.fb.pojos;

import java.util.HashMap;
import java.util.Map;

/**
 * 接收json字符串所需 <br/>
 * IpUtils->ipsearch method 所需
 * 
 * @author sun
 * @date 2016年8月25日 下午1:35:43
 */
public class IpApi {

	private Integer showapi_res_code;
	private String showapi_res_error;
	private Map<String, String> showapi_res_body = new HashMap<String, String>();

	public Integer getShowapi_res_code() {
		return showapi_res_code;
	}

	public void setShowapi_res_code(Integer showapi_res_code) {
		this.showapi_res_code = showapi_res_code;
	}

	public String getShowapi_res_error() {
		return showapi_res_error;
	}

	public void setShowapi_res_error(String showapi_res_error) {
		this.showapi_res_error = showapi_res_error;
	}

	public Map<String, String> getShowapi_res_body() {
		return showapi_res_body;
	}

	public void setShowapi_res_body(Map<String, String> showapi_res_body) {
		this.showapi_res_body = showapi_res_body;
	}

}
