package com.fb.commons;

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


import com.fb.core.Const;
import com.fb.kit.CommonUtils;
import com.fb.kit.DateUtils;
import com.fb.kit.IpUtils;
import com.fb.kit.ToolsUtils;
import com.fb.model.TDictionary;
import com.fb.model.TSettingsGlobal;
import com.fb.model.rbac.*;
import com.fb.pojos.MpSession;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 
 * 通用方法
 * @author sun
 * @date 2016年8月23日 上午9:31:03
 */
public class JfinalUtils {
	protected static final Logger LOG = Logger.getLogger(JfinalUtils.class);
	/**
	 * 系统模块开关查询
	 * @author sun
	 * @date 2016年8月23日 上午9:34:42
	 * @param key
	 * @return
	 */
	public static boolean getSwitch(String key, HttpSession session){
		boolean flag = false;
		TSettingsGlobal entity = TSettingsGlobal.me.findByKey(key, JfinalUtils.getPlatform(session));
		String val = "no";
		if(entity!=null){
			val = entity.getStr("value");
		}
		if("yes".equalsIgnoreCase(val)){
			flag = true;//开启
		}
		return flag;
	}

	/**
	 * 记录操作日志
	 * @author sun
	 * @date 2016年8月23日 上午9:33:29
	 * @param c
	 */
	public static void writeLog(Controller c){
		String requestUri = c.getRequest().getRequestURI();
		if(!StringUtils.isEmpty(requestUri)){
			String method = c.getRequest().getMethod();//get、post
			requestUri = requestUri.replaceAll(Const.MP_SUFFIX, "");//去掉后缀
			String functionName = requestUri.substring(requestUri.lastIndexOf("/"), requestUri.length());
			if (!functionName.equalsIgnoreCase("/memory") && !functionName.equalsIgnoreCase("/search") && !functionName.equalsIgnoreCase("/getData")
					&& !functionName.equalsIgnoreCase("/view")
					&& (!functionName.equalsIgnoreCase("/add") || method
							.equalsIgnoreCase("post"))
					&& (!functionName.equalsIgnoreCase("/edit") || method
							.equalsIgnoreCase("post")) && !functionName.equalsIgnoreCase("/") && !functionName.endsWith("search") && !functionName.equalsIgnoreCase("/welcome")) {
				Integer status = 1;//操作状态
				logToTask(requestUri, method, functionName, c.getRequest().getQueryString(), c.getRequest().getHeader("user-agent"), IpUtils.getRealIp(c.getRequest()), ToolsUtils.iteratorParamsTo(c.getRequest().getParameterMap()), status, c.getSession(), c.getRequest());
			}
		}
	}

	/**
	 * 记录日志提交至Queue任务队列
	 * @author sun
	 * @date 2016年8月8日 上午9:49:29
	 * @param requestUri
	 * @param method
	 * @param functionName
	 * @param note
	 * @param userAgent
	 * @param ip
	 * @param form
	 * @param session
	 * @param request
	 */
	public static void logToTask(String requestUri, String method, String functionName, String note, String userAgent, String ip, String form, Integer stu, HttpSession session, HttpServletRequest request) {
		if(getSwitch(CommonUtils.SettingGlobal.SYSTEM_LOG_SWITCH, session)){
			TRbacUser user = (TRbacUser)session.getAttribute(Const.SER_USER);
			//后台管理系统, 未登录的用户不记录日志
			if(user!=null || stu==0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				Map<String, Object> param2Map = new HashMap<String, Object>();
				param2Map.put("method", method);
				param2Map.put("user", user);//对象
				if(functionName.equalsIgnoreCase("/login")){
					//查询登录者使用的登录用户名
					param2Map.put("username", request.getParameter("username"));//登录账户
					param2Map.put("password", request.getParameter("password"));//登录密码
				}
				param2Map.put("stu", stu);//操作成功与否， 登陆是否成功  0：false  1：true
				param2Map.put("function", functionName);
				param2Map.put("requestUri", requestUri);
				param2Map.put("note", note);
				param2Map.put("userAgent", userAgent);
				param2Map.put("ip", ip);
				param2Map.put("parameterMap", form);
				param2Map.put("platform", JfinalUtils.getPlatform(session));
				paramMap.put(TaskQueue.QUEUE_TYPE_LOG, param2Map);
				TaskQueue.getInstance().notifyTask(paramMap);
			}
		}
	}

	/**
	 * 获取后台登录用户的登录名
	 * @author sun
	 * @date 2016年8月23日 上午9:38:58
	 * @param session
	 * @return
	 */
	public static String getSysName(HttpSession session){
		TRbacUser user = (TRbacUser)session.getAttribute(Const.SER_USER);
		String loginName = "未知";
		if(user!=null){
			loginName = user.getStr("username");
		}
		return loginName;
	}
	
	/**
	 * 获取后台登录用户ID
	 * @author sun
	 * @date 2016年8月23日 上午9:38:58
	 * @param session
	 * @return
	 */
	public static Long getSysId(HttpSession session){
		TRbacUser user = (TRbacUser)session.getAttribute(Const.SER_USER);
		Long id = null;
		if(user!=null){
			id = user.getLong("id");
		}
		return id;
	}
	
	/**
	 * 获取所属系统平台
	 * @author sun
	 * @date 2016年10月26日 下午4:04:37
	 * @param session
	 * @return
	 */
	public static String getPlatform(HttpSession session){
		String platform = (String)session.getAttribute(Const.PLATFORM_SESSION_ID);
		if(StringUtils.isEmpty(platform)){
			platform = Const.PROJECT_PLATFORM;
		}
		return platform;
	}
	
	/**
	 * 获取所属系统平台、如果是root管理员则忽略此判断
	 * @author sun
	 * @date 2016年10月28日 下午4:04:52
	 * @param session
	 * @return
	 */
	public static String getPlatformCheckRoot(HttpSession session){
		if(getSysIsRoot(session)){
			return null;
		}
		return getPlatform(session);
	}

	/**
	 * 是否包含某个角色
	 * @param roleId
	 * @param session
	 * @return
	 */
	public static boolean isContainRoleIds(String roleId,HttpSession session){
		if(StringUtils.isEmpty(roleId)){return  false;}
		String roleIds=(String)session.getAttribute(Const.RoleAuth.ALL_ROLEID);
		String[] rs= StringUtils.split(roleIds,",");
		if(rs==null){return false;}
		List<String> roleIdList=Arrays.asList(rs);
		return roleIdList.contains(roleId);
	}
	/**
	 * 获取当前登录用户所具有的所有角色ID
	 * @author sun
	 * @date 2016年10月12日 下午4:49:49
	 * @param session
	 * @return
	 */
	public static String getSysRoleIdsBySession(HttpSession session){
		TRbacUser user = (TRbacUser)session.getAttribute(Const.SER_USER);
		Long userId = null;
		String ugroupIds = null;
		if(user!=null){
			userId = user.getLong("id");
			ugroupIds = user.getStr("ugroup_ids");
		}
		if(userId!=null && StringUtils.isNotEmpty(ugroupIds)){
			/** 查询用户被分配的角色 **/
			List<TRbacUserRoleRef> roleIds = TRbacUserRoleRef.me.findRoleByUser(userId);
			/** 查询所属用户组的角色 **/
			String roids = TRbacUgroupRoleRef.me.findByGroupIds(ugroupIds);

			Set<String> idsList = new HashSet<String>();
			for (TRbacUserRoleRef roleId : roleIds) {
				idsList.add(roleId.getLong("role_id").toString());
			}
			/** 合并用户组角色 **/
			if(StringUtils.isNotEmpty(roids)){
				idsList.addAll(Arrays.asList(roids.split(",")));
			}
			return ToolsUtils.join(",", idsList.toArray());
		}
		return null;
	}
	
	/**
	 * 根据用户ID获取对应的用户组
	 * @author sun
	 * @date 2016年10月12日 下午6:25:23
	 * @param userId
	 * @return
	 */
	public static String getSysUGroupIdsByUserId(Long userId){
		List<String> ugList = TRbacUgroupUserRef.me.findBy(userId);
		if(ugList!=null && ugList.size()>0){
			return ToolsUtils.join(",", ugList.toArray());
		}
		return null;
	}
	
	/**
	 * 根据用户ID和用户组查询所具有的所有角色ID
	 * @author sun
	 * @date 2016年10月12日 下午4:44:32
	 * @return
	 */
	public static String getSysRoleIdsBy(Long userId, String ugroupIds){
		/** 查询用户被分配的角色 **/
		List<TRbacUserRoleRef> roleIds = TRbacUserRoleRef.me.findRoleByUser(userId);
		/** 查询所属用户组的角色 **/
		String roids = TRbacUgroupRoleRef.me.findByGroupIds(ugroupIds);
		
		Set<String> idsList = new HashSet<String>();
		for (TRbacUserRoleRef roleId : roleIds) {
			idsList.add(roleId.getLong("role_id").toString());
		}
		/** 合并用户组角色 **/
		if(StringUtils.isNotEmpty(roids)){
			idsList.addAll(Arrays.asList(roids.split(",")));
		}
		return ToolsUtils.join(",", idsList.toArray());
	}
	
	/**
	 * 判断是否是超级管理员
	 * @author sun
	 * @date 2016年10月11日 下午5:22:07
	 * @param session
	 * @return
	 */
	public static boolean getSysIsRoot(HttpSession session){
		TRbacUser user = (TRbacUser)session.getAttribute(Const.SER_USER);
		boolean flag = false;
		if(user!=null){
			String isRoot = user.getStr("is_root");
			if("1".equals(isRoot)){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否已经登录后台管理系统
	 * @author sun
	 * @date 2016年8月23日 上午9:39:31
	 * @param session
	 * @return
	 */
	public static boolean isLogin(HttpSession session){
		boolean flag = false;
		TRbacUser user = (TRbacUser)session.getAttribute(Const.SER_USER);
		if(user!=null){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 检测某IP是否在白名单中存在
	 * @author sun
	 * @date 2016年8月19日 下午4:29:16
	 * @param ip
	 * @return
	 */
	public static boolean verifyGlobalWhiteList(String ip, HttpSession session){
		boolean flag = true;//默认false
		if(!StringUtils.isEmpty(ip)){
			String ips = whiteListIps(session);
			if(!StringUtils.isEmpty(ips)){
				List<String> list = Arrays.asList(ips.split(","));
				if(list.contains(ip)){
					flag = false;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取系统全局白名单IP集合
	 * @author sun
	 * @date 2016年8月19日 下午4:23:10
	 * @return
	 */
	public static String whiteListIps(HttpSession session){
		String ips = null;
		TSettingsGlobal entity = TSettingsGlobal.me.findByKey(CommonUtils.SettingGlobal.SG_WHITE_LIST_IPS, JfinalUtils.getPlatform(session));
		if(entity!=null){
			ips = entity.getStr("value");
		}
		return ips;
	}
	
	/**
	 * 获取后台登录页-页面背景模版
	 * @author sun
	 * @date 2016年7月20日 上午9:25:55
	 * @return
	 */
	public static String getDefaultLoginTemplate(HttpSession session){
		String loginBackGround = "blur";
		TSettingsGlobal entity = TSettingsGlobal.me.findByKey(CommonUtils.SettingGlobal.SYSTEM_LOGIN_BACKGROUND, JfinalUtils.getPlatform(session));
		if(entity!=null){
			loginBackGround = entity.getStr("value");
		}
		return loginBackGround;
	}
	
	/**
	 * 获取注释
	 * @author sun
	 * @date 2016年9月19日 上午11:42:44
	 * @return
	 */
	public static String getAnnotation(String key, HttpSession session){
		String annotation = Const.BLANK;
		TSettingsGlobal entity = TSettingsGlobal.me.findByKey(key, JfinalUtils.getPlatform(session));
		if(entity!=null){
			annotation = entity.getStr("value");
		}
		return annotation;
	}
	
	/**
	 * 查询 全局配置
	 * @author sun
	 * @date 2016年9月19日 下午2:51:28
	 * @param key
	 * @return
	 */
	public static String findBySGKey(String key, HttpSession session){
		String annotation = Const.BLANK;
		TSettingsGlobal entity = TSettingsGlobal.me.findByKey(key, JfinalUtils.getPlatform(session));
		if(entity!=null){
			annotation = entity.getStr("value");
		}
		return annotation;
	}
	
	/**
	 * 查询用户自定义后台配置 (目前包含导航条颜色和icon颜色)
	 * @author sun
	 * @date 2016年7月20日 上午9:59:13
	 * @param userId
	 * @return
	 */
	public static TRbacUserConfig findUserConfig(Long userId, HttpSession session) {
		TRbacUserConfig uc = TRbacUserConfig.me.findByChatCode(userId, CommonUtils.SettingGlobal.SYSTEM_SETTING_COLOR);
		if(uc==null){
			TSettingsGlobal entity = TSettingsGlobal.me.findByKey(CommonUtils.SettingGlobal.SYSTEM_SETTING_COLOR, JfinalUtils.getPlatform(session));
			if(entity!=null){
				uc = new TRbacUserConfig();
				uc.set("user_id", userId);
				uc.set("chat_code", entity.getStr("key"));
				uc.set("label", entity.getStr("name"));
				uc.set("val", entity.getStr("value"));
				uc.set("stu", 1);
				uc.set("type", 1);
				uc.set("add_time", DateUtils.getDate());
				uc.save();
			}
		}
		return uc;
	}
	
	/**
	 * 
	 * @Description: TODO(记录在线用户)  
	 * @author sun
	 * @date 2016年8月5日 下午12:55:19
	 * @param user
	 * @param loginTime
	 * @param loginIp
	 * @param sessionParam
	 */
	public static void recordOnlineUsers(TRbacUser user, String loginTime, String loginIp, HttpSession sessionParam) {
		//统计在线人数
		String userName = user.getStr("username");//作为map key使用
		if(!StringUtils.isEmpty(userName)){
			if(CommonUtils.sessionMap.get(userName)!=null){
				//证明此用户已有人登录, 之前登录的用户将被踢下线
				MpSession mps = CommonUtils.sessionMap.get(userName);
				if(mps!=null){
					LOG.warn("用户："+userName+" 将要被挤下线！");
					HttpSession session = mps.getSession();
					try {
						TRbacUser u = (TRbacUser)session.getAttribute(Const.SER_USER);
						if(u !=null){//session未失效   手动销毁
							System.out.print("Session is activiting");
							session.invalidate();
						}else{
							System.out.print("Session has invalidated");
						}
					} catch (Exception e) {
						e.printStackTrace();
						LOG.warn("old sessionId is " + session.getId() + " has invalidated");
					}
				}
			}
			//将登录信息以及session存入到cache中
			CommonUtils.sessionMap.put(userName, new MpSession(user, loginTime, loginIp, sessionParam.getId(), sessionParam));
		}
	}


	/**
	 * 根据chatCode和val查询对应的字典数据
	 * @param chatCode
	 * @param val
	 * @param isColor
	 * @return
	 */
	public static String findByCodeAndVal(String chatCode, String val, boolean isColor){
		String label = null;
		if(!StringUtils.isEmpty(chatCode) && val!=null){
			TDictionary dict = TDictionary.me.findByCodeAndVal(chatCode, val);
			if(dict!=null){
				label = dict.getStr("label");
				if(isColor){
					if(!StringUtils.isEmpty(label)){
						label = "<font color='"+dict.getStr("color")+"'>"+label+"</font>";
					}
				}
			}
		}
		return label;
	}
}
