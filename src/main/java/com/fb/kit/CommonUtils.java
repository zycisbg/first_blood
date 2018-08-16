package com.fb.kit;
//                         _ooOoo_  
//                        o8888888o  
//                        88" . "88  
//                        (| -_- |)  
//                         O\ = /O  
//                     ____/`---'\____  
//                   .   ' \\| |// `.  
//                    / \\||| : |||// \  
//                  / _||||| -:- |||||- \  
//                    | | \\\ - /// | |  
//                  | \_| ''\---/'' | |  
//                   \ .-\__ `-` ___/-. /  
//                ___`. .' /--.--\ `. . __  
//             ."" '< `.___\_<|>_/___.' >'"".  
//            | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//              \ \ `-. \_ __\ /__ _/ .-` / /  
//      ======`-.____`-.___\_____/___.-`____.-'======  
//                         `=---='  
//
//      .............................................  
//               佛祖保佑             永无BUG 
//       佛曰:  
//               写字楼里写字间，写字间里程序员；  
//               程序人员写程序，又拿程序换酒钱。  
//               酒醒只在网上坐，酒醉还来网下眠；  
//               酒醉酒醒日复日，网上网下年复年。  
//               但愿老死电脑间，不愿鞠躬老板前；  
//               奔驰宝马贵者趣，公交自行程序员。  
//               别人笑我忒疯癫，我笑自己命太贱；  
//               不见满街漂亮妹，哪个归得程序员？ 


import com.fb.pojos.MpSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量配置
 * @author sun
 * @date 2016年8月23日 上午10:33:42
 */
public class CommonUtils {

	/** 网站后台的字典基本配置信息 **/
	public static final int DICTIONARY_PLATFORM_SYSTEM = 1;
	
	/** 网站前台的字典基本配置信息 **/
	public static final int DICTIONARY_PLATFORM_WEBSITE = 2;
	
	/** memcache key 用来保存在线管理员 **/
	public static final Map<String, MpSession> sessionMap = new HashMap<String, MpSession>();
	
	/**
	 * SettingGlobal 全局配置表
	 * @author sun
	 * @date 2016年8月23日 上午10:32:51
	 */
	public final static class SettingGlobal{

		/** 系统操作日志开关 **/
		public static final String SYSTEM_LOG_SWITCH = "system_log_switch";
		
		/** 后台登录是否开启验证码的开关 **/
		public static final String SYSTEM_LOGIN_VERIFYCODE_SWITCH = "system_login_verifycode_switch";
		
		/** 登陆页面背景模版 **/
		public static final String SYSTEM_LOGIN_BACKGROUND = "system_login_background";
		
		/** 后台模版 **/
		public static final String SYSTEM_SKIN_CLASS = "system_skin_class";
		
		/** 后台边栏锁定 **/
		public static final String SYSTEM_FIXED_SETTING = "system_fixed_setting";
		
		/** 后台系统颜色设置 **/
		public static final String SYSTEM_SETTING_COLOR = "system_setting_color";
		
		/** setting_global 表的全局设置 **/
		public static final String SG_WHITE_LIST_IPS = "white_list_ips";
		
		/** 密码错误次数 **/
		public static final String PASSWORD_ERROR_MAX_NUM = "password_error_max_num";
		
		/** 密码错误锁定时长 **/
		public static final String PASSWORD_ERROR_MAX_TIME = "password_error_max_time";

		public static final String MESSAGE_DYNAMIC_FIELD = "message_dynamic_field";

		public static final String DYNAMIC_SMS = "dynamic_sms";

		public static final String KF_DYNAMIC_MESSAGE = "kf_dynamic_message";
	}
	
	/**
	 * 字典表Key值
	 * @author sun
	 * @date 2016年8月23日 上午10:33:01
	 */
	public final static class DictionaryKey{
		/** 部门渠道 **/
		public static final String DEPT_CHANNEL = "DEPT_CHANNEL";
		
		/** 惠农聚宝标的产品类型 **/
		public static final String HNJB_LOAN_PRODUCT_TYPE = "hnjb_loan_product_type";
		
		/** 十月复投活动返现开关，只允许执行一次 **/
		public static final String OCT_RECEIVE_ENABLE = "OCT_RECEIVE_ENABLE";
		
		/** 平台 **/
		public static final String SYSTEM_PLATFORM = "SYSTEM_PLATFORM";
		
	}
	
	/**
	 * gritter 弹窗提醒相关变量
	 * @date 2016年10月14日 上午8:58:29
	 */
	public final static class Gritter{
		/** Girtter 通知窗口 */
		public static final String GRITTER_TYPE = "gritter_type";//类型(不可为空)， 包含	gritter-default、gritter-info、gritter-warning、gritter-error、gritter-success、gritter-center
		public static final String GRITTER_CONTENT = "gritter_content";//通知内容(不可为空)
		public static final String GRITTER_TITLE = "gritter_title";//通知窗口标题
		public static final String GRITTER_IMAGE = "gritter_image";//头像
		public static final String GRITTER_TIME = "gritter_time";//窗口关闭时间，毫秒
		public static final String GRITTER_LIGHT = "gritter_light";//是否开灯照亮， 说的是背景颜色
		
		/** Gritter 通知窗口类型 **/
		public static final String GRITTER_TYPE_DEFAULT = "gritter-default";
		public static final String GRITTER_TYPE_INFO = "gritter-info";
		public static final String GRITTER_TYPE_WARNING = "gritter-warning";
		public static final String GRITTER_TYPE_ERROR = "gritter-error";
		public static final String GRITTER_TYPE_SUCCESS = "gritter-success";
		public static final String GRITTER_TYPE_CENTER = "gritter-center";
	}
	
	/**
	 * Statistics包下 - 统计类的常量
	 * @date 2016年10月14日 上午9:02:54
	 */
	public final static class Statistics{
		/** 统计名字 */
		public static final String PF1 = "registerDD";
		public static final String PF2 = "newuserInvest";
		public static final String PF3 = "newuserRechange";
		public static final String PF4 = "olduserInvest";
		public static final String PF5 = "olduserRechange";
		public static final String PF6 = "pvuv";
		public static final String PF7 = "rechange_wc";
		
		/** 平台统计 **/
		public static final String TPF1 = "total_user";
		public static final String TPF2 = "total_auth_user";
		public static final String TPF3 = "total_recharge";
		public static final String TPF4 = "total_invest";
		public static final String TPF5 = "total_wc";
	}

	public static String switchHtml(String method){
		String page = null;
		if("all".equals(method)){
			page = "allsearch.html";
		}else if("hetang".equals(method)){
			page = "hetangsearch.html";
		}else if("natural".equals(method)){
			page = "naturalsearch.html";
		}else if("detail".equals(method)){
			page = "detailsearch.html";
		}else if("md".equals(method)){
			page = "mdsearch.html";
		}else if("offline".equals(method)){
			page = "offlinesearch.html";
		}else if("od".equals(method)){
			page = "odsearch.html";
		}else if("not_active".equals(method)){//未激活列表
			page = "notActivesearch.html";
		}else if("activated".equals(method)){//已激活列表
			page = "activatedsearch.html";
		}else if("data_count".equals(method)){//优惠卡数据统计
			page = "dataCountsearch.html";
		}
		return page;
	}

}
