package com.fb.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;


public class Config extends Model<Config>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3338711729308067948L;
	public static Config me=new Config();


	public List<Config> findPasswordConfigList(){
		return find("select * from config where id in('password_interval_day','password_regulation','password_sms_verification','report_password_sms_verification')");
	}

	public List<Config> findPasswordConfigSysList(){
		return find("select * from config where id in('password_sms_verification_sys')");
	}

	public List<Config> findWorldCupConfigList(){
		return find("select * from config where id in('activity_ced05f46af9d42a1b40f23382b080c6a','activity_ztsb','activity_1317909889d84b0699c6676f4d87de32','activity_cbbbb53967dd40bd8cef7d9227beddf0')");
	}

	public String getConfigValue(String configId) throws Exception {
		Config config = get(configId);
		if (config != null) {
			return config.getStr("value");
		}
		throw new Exception("config ID:"
				+ config.getStr("id") + "对象为空！");
	}
}
