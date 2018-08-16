package com.fb.core;

import java.util.concurrent.ConcurrentHashMap;
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

/**
 * 系统常量配置
 * 
 * @author sun
 * @date 2016年7月20日 上午10:25:27
 */
public class Const {

	/** 默认的配置文件名称 */
	public static final String DEFAULT_CONFIG_NAME = "config.properties";
	/** error code **/
	public static final String DEFAULT_ERROR_CONFIG_NAME = "error.properties";

	/** 通用常量 */
	public static final String BLANK = "";

	/** 写 */
	public static final String DB_MASTER = "master";
	
	/** 读 */
	public static final String DB_SLAVE = "slave";
	
	/** 分页常量 */
	public static final int PAGE_SIZE = 10;
	public static final int PAGE_NUMBER = 1;
	
	/** 系统访问链接后缀 */
	public static String MP_SUFFIX;
	
	/** 站点所属平台 (系统配置文件配置的) **/
	public static String PROJECT_PLATFORM = null;
	
	/** 数据所属平台 **/
	public static String PLATFORM_SESSION_ID = "platform_id";
	public static String PLATFORM_SESSION_NAME = "platform_name";

	/**
	 * 请求方式
	 * @date 2016年10月14日 上午8:59:48
	 */
	public final static class Request{
		/** 请求方式 */
		public static final String REQUEST_ALL = "ALL";
		public static final String REQUEST_POST = "POST";
		public static final String REQUEST_GET = "GET";
	}
	
	/**
	 * 存放角色和权限的变量定义
	 * @date 2016年10月15日 上午9:42:26
	 */
	public final static class RoleAuth{
		/** 大模块权限 **/
		public static final String MODULE_PERMISSION = "module_permission";
		/** 具体权限 **/
		public static final String AUTH_PERMISSION = "auth_permission";
		/** 所有权限 **/
		public static final String ALL1_PERMISSION = "all1_permission";
		/** 所有权限 **/
		public static final String ALL2_PERMISSION = "all2_permission";
		/** TODO **/
		public static final String LEVEL_PERMISSION = "level_permission";
		/** URL带参数的 **/
		public static final String PARAMS_PERMISSION = "params_permission";

		/** 所有的roleid **/
		public static final String ALL_ROLEID = "all_releid";
		
	}
	
	
	/** SESSION常量 */
	public static final String SER_USER = "__ser_user__";
	public static final String PUBLIC_ACCOUNT = "pa";
	public static final String SER_PERMISSIONS = "__ser_permissions__";
	public static final String SER_MENUS = "__ser_menus__";

	/** 页面常量 */
	public static final String PAGE_USER = "page_user";
	public static final String PAGE_PA = "page_pa";
	public static final String PAGE_MENUS = "page_menus";
	public static final String PAGE_ACTION = "page_action";
	public static final String PAGE_NAME = "page_name";
	public static final String PAGE_NAME_MODULE = "page_name_module";
	public static final String PAGE_NAME_FUNCTION = "page_name_function";

	/** 系统消息 */
	public static final String MSG_CONTENT = "msg_content";
	public static final String MSG_TYPE = "msg_type";
	
	/**
	 * 消息提醒类型
	 * @date 2016年10月14日 上午10:50:13
	 */
	public final static class MsgType{
		/** 成功类型 **/
		public static final String SUCCESS = "alert-success";
		/** 提示类型 **/
		public static final String INFO = "alert-info";
		/** 警告类型 **/
		public static final String WARN = "alert-warning";
		/** 操作失败类型 **/
		public static final String ERROR = "alert-danger";
	}
	
	public static final String MSG_EDIT_SUCCESS = "数据修改成功.";
	public static final String MSG_UNLOCK_SUCCESS = "解锁成功";
	public static final String MSG_EDIT_FAILED = "数据修改失败, ID为［%s］的数据项可能已经不存在或数据项重复.";
	public static final String MSG_SAVE_SUCCESS = "数据保存成功.";
	public static final String MSG_SAVE_FAILED = "数据保存失败, 数据项可能重复.";
	public static final String MSG_SAVE_ZJ = "父类不能勾选自己";
	public static final String MSG_REMOVE_SUCCESS = "数据删除成功.";
	public static final String MSG_REMOVE_FAILED = "数据删除失败, ID为［%s］的数据项可能在其他地方被使用.";
	public static final String MSG_REMOVE_FAILED_LOCK = "数据删除失败, 此数据已被锁定.";
	public static final String MSG_REMOVE_FAILED_403 = "数据删除失败, 此数据不存在 或者 您无权操作此数据.";
	public static final String MSG_REMOVE_FAILED_NOD = "数据删除失败, 此数据还有子节点或者关联数据，请先删除子节点和关联数据 .";
	public static final String MSG_ADD_MORE = "数据添加失败, 最多允许添加%s个%s.";
	public static final String MSG_OFF_SUCCESS = "数据下架成功.";
	public static final String MSG_OFF_FAILED = "数据操作失败, 参数异常.";
	public static final String MSG_SUPER_FAILED = "你操作的对象是超级管理员， 禁止操作";
	public static final String MSG_ZJ_FAILED = "你操作的对象目前正在使用， 禁止操作";
	public static final String MSG_PARAM_FAILED = "请至少选择一个所属用户组";
	public static final String MSG_AUTH_FAILED = "您无权操作";

	public static final ConcurrentHashMap<String,Long> userCurrentLimite =new ConcurrentHashMap<>(512);
	public static final ConcurrentHashMap<String,Long> userCurrentLimiteForCash =new ConcurrentHashMap<>(512);
}
