package com.fb.interceptor;
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

import com.fb.commons.JfinalUtils;
import com.fb.controller.BaseController;
import com.fb.core.Const;
import com.fb.kit.CommonUtils;
import com.fb.kit.CookieUtils;
import com.fb.kit.DateUtils;
import com.fb.kit.JsonUtils;
import com.fb.model.TDictionary;
import com.fb.model.TSettingsGlobal;
import com.fb.model.rbac.TRbacPermission;
import com.fb.model.rbac.TRbacUser;
import com.fb.model.rbac.TRbacUserConfig;
import com.google.common.base.Strings;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 拦截器
 * @author sun
 */
public class AuthInterceptor implements Interceptor {

	protected static final Logger LOG = Logger.getLogger(AuthInterceptor.class);

	/**
	 * intercept
	 * @author sun
	 * @date 2016年7月20日 上午10:22:56
	 * @see Interceptor#intercept(ActionInvocation)
	 */
	@SuppressWarnings("unchecked")
	public void intercept(ActionInvocation ai) {
		BaseController c = (BaseController) ai.getController();
		HttpSession session = c.getSession();
		HttpServletRequest req = c.getRequest();

		//获得所有请求参数名
		if(!"/sys/sqlandxss/edit.action".equals(req.getRequestURI())){
			Enumeration enumeration = req.getParameterNames();
			StringBuffer param = new StringBuffer();
			while (enumeration.hasMoreElements()) {
				//得到参数名
				String name = enumeration.nextElement().toString();

				//得到参数对应值
				String[] value = req.getParameterValues(name);
					for (int i = 0; i < value.length; i++) {
                        param = param.append(value[i]);
                    }

			}

		}

		//*******查询系统配置start*****************************************************************************
		//查询系统配置，例如：系统项目名称、logo等等信息
		List<TSettingsGlobal> sgList = TSettingsGlobal.me.findAll(Const.PROJECT_PLATFORM);
		if (sgList != null && sgList.size() > 0) {
			for(TSettingsGlobal sg : sgList){
				c.setAttr(sg.getStr("key"), sg.getStr("value"));
			}
		}
		//*******查询系统配置end*******************************************************************************

		// 去除没有继承AppController的类
		if (!(ai.getController() instanceof BaseController)) {
			ai.invoke();
			return;
		}

		//记录日志
		JfinalUtils.writeLog(c);

		// 将临时信息输出到页面
		if (!Strings.isNullOrEmpty((String) session.getAttribute(Const.MSG_CONTENT)))  {
			c.setAttr(Const.MSG_TYPE, session.getAttribute(Const.MSG_TYPE));
			c.setAttr(Const.MSG_CONTENT, session.getAttribute(Const.MSG_CONTENT));
			session.removeAttribute(Const.MSG_TYPE);
			session.removeAttribute(Const.MSG_CONTENT);
		}

		// 将gritter临时信息输出到页面
		if (!Strings.isNullOrEmpty((String) session.getAttribute(CommonUtils.Gritter.GRITTER_CONTENT)))  {
			c.setAttr(CommonUtils.Gritter.GRITTER_TYPE, session.getAttribute(CommonUtils.Gritter.GRITTER_TYPE));
			c.setAttr(CommonUtils.Gritter.GRITTER_TITLE, session.getAttribute(CommonUtils.Gritter.GRITTER_TITLE));
			c.setAttr(CommonUtils.Gritter.GRITTER_CONTENT, session.getAttribute(CommonUtils.Gritter.GRITTER_CONTENT));
			c.setAttr(CommonUtils.Gritter.GRITTER_IMAGE, session.getAttribute(CommonUtils.Gritter.GRITTER_IMAGE));
			c.setAttr(CommonUtils.Gritter.GRITTER_TIME, session.getAttribute(CommonUtils.Gritter.GRITTER_TIME));
			c.setAttr(CommonUtils.Gritter.GRITTER_LIGHT, session.getAttribute(CommonUtils.Gritter.GRITTER_LIGHT));
			session.removeAttribute(CommonUtils.Gritter.GRITTER_TYPE);
			session.removeAttribute(CommonUtils.Gritter.GRITTER_TITLE);
			session.removeAttribute(CommonUtils.Gritter.GRITTER_CONTENT);
			session.removeAttribute(CommonUtils.Gritter.GRITTER_IMAGE);
			session.removeAttribute(CommonUtils.Gritter.GRITTER_TIME);
			session.removeAttribute(CommonUtils.Gritter.GRITTER_LIGHT);
		}

		// 去除通过@AuthExclusion的类&方法
		if (c.getClass().getAnnotation(AuthExclusion.class) != null || ai.getMethod().getAnnotation(AuthExclusion.class) != null) {
			ai.invoke();
			return;
		}

		//从session中获取user对象信息
		TRbacUser user = (TRbacUser)session.getAttribute(Const.SER_USER);
		if (user == null) {
			// 销毁所有Session
			session.invalidate();
			c.redirect("/login" + c.getAttr("suffix"));
			return ;
		}

		// 权限验证
		Map<String, TRbacPermission> allPermissions = (Map<String, TRbacPermission>) session.getAttribute(Const.RoleAuth.ALL1_PERMISSION);
		boolean flag = true;

		String actionKey = ai.getActionKey()+Const.MP_SUFFIX;
		TRbacPermission entity = allPermissions.get(actionKey);//当前点击的权限对象
		String params = c.getRequest().getQueryString();
		String requestUrl = actionKey+(StringUtils.isNotEmpty(params)?"?"+params:"");
		if(entity==null){
			flag = false;//无权限
		}else{
			if(StringUtils.isNotEmpty(entity.getStr("params"))){
				if(!entity.getStr("params").equalsIgnoreCase("?"+params)){
					flag = false;//该权限是带参数的，参数未匹配成功，权限不足
				}else{
					String clickUrl = entity.getStr("action_key")+entity.getStr("params");
					c.setAttr("click_url", clickUrl);
				}
				if(!flag){
					String ppp = actionKey+"?"+params;
					Map<String, TRbacPermission> paramsMap = (Map<String, TRbacPermission>) session.getAttribute(Const.RoleAuth.PARAMS_PERMISSION);
					Set<String> ks = paramsMap.keySet();
					Iterator<String> it = ks.iterator();
					while(it.hasNext()){
						String k = it.next();
						if(ppp.indexOf(k)!=-1){
							String dypa = ppp.replace(k, "");
							if(StringUtils.isEmpty(dypa) || dypa.startsWith("&")){
								flag = true;
								c.setAttr("click_url", k);
								break;
							}
						}
					}
//					if(paramsMap!=null && paramsMap.get(ppp)!=null){
//						flag = true;
//					}
				}
			}
		}

		if (allPermissions != null && allPermissions.containsKey(actionKey) && flag) { // 认证成功
			/** 权限 function 全局禁用检测  TODO **/
			interceptorExecute(c, session, user, actionKey, entity, requestUrl);
			ai.invoke();
		} else {
			/** 无权限 **/
//			interceptorExecute(c, session, user, actionKey, entity, requestUrl);
			c.render("/WEB-INF/view/error/403.html");
		}
	}

	/**
	 * 拦截器处理
	 * @author sun
	 * @date 2016年10月17日 上午8:58:11
	 * @param c
	 * @param session
	 * @param user
	 * @param actionKey
	 * @param entity
	 * @param requestUrl
	 */
	@SuppressWarnings("unchecked")
	private void interceptorExecute(BaseController c, HttpSession session,
									TRbacUser user, String actionKey, TRbacPermission entity,
									String requestUrl) {
		Map<Long, TRbacPermission> longPermissions = (Map<Long, TRbacPermission>) session.getAttribute(Const.RoleAuth.ALL2_PERMISSION);

		Map<String, Integer> levelMap = (Map<String, Integer>) session.getAttribute(Const.RoleAuth.LEVEL_PERMISSION);
		Integer level = levelMap.get(actionKey);//点击的菜单，属于哪个模块。

		Map<Object, List<TRbacPermission>> childMap = (Map<Object, List<TRbacPermission>>) session.getAttribute(Const.RoleAuth.AUTH_PERMISSION);

		c.setAttr(Const.PAGE_USER, user);
		/** 一级导航 **/
		c.setAttr("module_menu", session.getAttribute(Const.RoleAuth.MODULE_PERMISSION));
		/** 当前路径 **/
		c.setAttr("page_action", actionKey);

		Long moduleId = 0L;
		if(entity!=null){
			/** 面包屑 **/
			if(level==1){
				/** 点击的一级 **/
				moduleId = entity.getLong("id");
				c.setAttr("module_entity", entity);
			}else if(level==2){
				/** 点击的二级 **/
				TRbacPermission moduleEntity = longPermissions.get(entity.getLong("pid"));
				moduleId = moduleEntity.getLong("id");
				c.setAttr("module_entity", moduleEntity);
				c.setAttr("controller_entity", entity);
			}else if(level==3){
				/** 点击的三级 **/
				TRbacPermission controllerEntity = longPermissions.get(entity.getLong("pid"));
				TRbacPermission moduleEntity = longPermissions.get(controllerEntity.getLong("pid"));
				moduleId = moduleEntity.getLong("id");
				c.setAttr("module_entity", moduleEntity);
				c.setAttr("controller_entity", controllerEntity);
				if(StringUtils.isNotEmpty(requestUrl)){
					Map<String, TRbacPermission> paramsMap = (Map<String, TRbacPermission>) session.getAttribute(Const.RoleAuth.PARAMS_PERMISSION);
					if(paramsMap!=null && paramsMap.get(requestUrl)!=null){
						c.setAttr("function_entity", paramsMap.get(requestUrl));
					}else{
						c.setAttr("function_entity", entity);
					}
				}else{
					c.setAttr("function_entity", entity);
				}
			}
			c.setAttr("menu_level", level);
		}else{

		}
		/** 二级菜单，左侧栏。 controller **/
		List<TRbacPermission> childList = childMap.get("controller_"+moduleId);
		if(childList!=null && childList.size()>0){
			for(TRbacPermission child:childList){
				List<TRbacPermission> sunList = childMap.get("method_"+child.getLong("id"));
				child.put("children", sunList);
			}
		}
		c.setAttr("menu_children", childList);

		//获取后台模版信息
		TSettingsGlobal skinClass = TSettingsGlobal.me.findByKey(CommonUtils.SettingGlobal.SYSTEM_SKIN_CLASS, JfinalUtils.getPlatform(c.getSession()));
		//获取后台边栏锁定设置信息
		TSettingsGlobal fixed = TSettingsGlobal.me.findByKey(CommonUtils.SettingGlobal.SYSTEM_FIXED_SETTING, JfinalUtils.getPlatform(c.getSession()));
		if(skinClass!=null){
			//保存cookie
			CookieUtils.addCookie(c.getRequest(), c.getResponse(), "ace_skin", skinClass.getStr("value"), CookieUtils.TIME);
		}
		if(fixed!=null){
			String val = fixed.getStr("value");
			Map<String, Object> map = JsonUtils.parseJSON2Map(val);
			if(map!=null && map.size()>0){
				Set<String> ks = map.keySet();
				for(String k:ks){
					c.setAttr(k, map.get(k));
				}
			}
		}

		String btnStyle = null;//列表操作按钮的颜色
		//查询后台系统颜色设置
		TRbacUserConfig colors = (TRbacUserConfig)session.getAttribute(CommonUtils.SettingGlobal.SYSTEM_SETTING_COLOR);
		if(colors!=null){
			String val = colors.getStr("val");
			Map<String, Object> map = JsonUtils.parseJSON2Map(val);
			if(map!=null && map.size()>0){
				Set<String> ks = map.keySet();
				for(String k:ks){
					c.setAttr(k, map.get(k));
					if("tabStyle".equalsIgnoreCase(k)){
						btnStyle = map.get(k).toString();
					}
				}
			}
		}

		//如果有更改，在此处修改或者修改成默认的
		c.setAttr("btnStyle", btnStyle);
		c.setAttr("number", 0);
		c.setAttr("im_number", 0);
		//当天的日期
		c.setAttr("date", DateUtils.getDateFormat(DateUtils.DATE_FORMAT));

		/** 超级管理员,具有查询所有信息的权限 **/
		List<TDictionary> dictList = TDictionary.me.findPlatFormBy(JfinalUtils.getPlatformCheckRoot(c.getSession()));
		/** 所有的系统平台 **/
		c.setAttr("platformList", dictList);

		/** 只有超级管理员(is_root=1)才有此权限！ **/
		if(JfinalUtils.getSysIsRoot(c.getSession())){

			c.setAttr("is_root", user.getStr("is_root"));
		}
		c.setAttr(Const.PLATFORM_SESSION_ID, JfinalUtils.getPlatform(c.getSession()));
		c.setAttr(Const.PLATFORM_SESSION_NAME, session.getAttribute(Const.PLATFORM_SESSION_NAME));

		/** tooltip-warning、tooltip-error、tooltip-info、tooltip-success **/
//		c.setAttr("tooltipClass", "tooltip-info");//tooltip-error
	}


	//效验
	protected boolean sqlValidate(String str) {
		str = str.toLowerCase();//统一转为小写
		String badStr="'|and|or|select|update|delete|insert";

		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			//循环检测，判断在请求参数当中是否包含SQL关键字
			if (str.indexOf(badStrs[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
}
