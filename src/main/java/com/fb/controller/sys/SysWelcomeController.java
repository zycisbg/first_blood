package com.fb.controller.sys;
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
import com.fb.interceptor.AuthExclusion;
import com.fb.kit.CommonUtils;
import com.fb.kit.DateUtils;
import com.fb.model.TDictionary;
import com.fb.pojos.MpSession;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 系统管理首页、欢迎页
 * 
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/sys")
public class SysWelcomeController extends BaseController {

	protected static final Logger LOG = Logger.getLogger(SysWelcomeController.class);
	
	public void welcome(){
		String type = getPara("type");
		if(!StringUtils.isEmpty(type) && "1".equals(type)){
			//踢下线
			//查询在线人数
			String userName = getPara("userName");
			if(CommonUtils.sessionMap!=null && CommonUtils.sessionMap.size()>0){
				MpSession mps = CommonUtils.sessionMap.get(userName);
				if(mps!=null){
					HttpSession session = mps.getSession();
					session.invalidate();//销毁session
//					Const.sessionMap.remove(userName);//内存cache中删除
					renderJson(200);
					return ;
				}
			}
		}else{
			
			//查询系统信息
			setAttr("java_version", System.getProperty("java.version"));
			setAttr("os", System.getProperty("os.name") + "&nbsp;&nbsp;&nbsp;"+ System.getProperty("os.arch") + "&nbsp;&nbsp;&nbsp;"+ System.getProperty("os.version"));
			setAttr("port", getRequest().getLocalPort());
			//查询在线管理员列表
			List<MpSession> list = new ArrayList<MpSession>();
			if(CommonUtils.sessionMap!=null && CommonUtils.sessionMap.size()>0){
				Set<String> keyset = CommonUtils.sessionMap.keySet();
				Iterator<String> it = keyset.iterator();
				while(it.hasNext()){
					String key = it.next();
					MpSession mps = CommonUtils.sessionMap.get(key);
					String loginTime =  mps.getLoginTime();
					//计算在线时长  和  转换时间格式
					mps.setTotalTime(DateUtils.relativeDateFormat(DateUtils.strToLong(loginTime, DateUtils.DATETIME_FORMAT)).replace("前", ""));
					list.add(mps);
				}
			}
			setAttr("online", list);
		}
	}
	
	/**
	 * 只有超级管理员is_root=1有权限操作
	 * @author sun
	 * @date 2016年10月26日 下午3:59:24
	 */
	@AuthExclusion
	public void change(){
		/** 只有超级管理员(is_root=1)才有此权限！ **/
		if(JfinalUtils.getSysIsRoot(getSession())){
			String projectName = getPara("platform", "report");
			TDictionary dict = TDictionary.me.findByCodeAndLabel(CommonUtils.DictionaryKey.SYSTEM_PLATFORM, projectName);
			if(dict!=null){
				getSession().setAttribute(Const.PLATFORM_SESSION_ID, dict.getStr("val"));
				getSession().setAttribute(Const.PLATFORM_SESSION_NAME, dict.getStr("label"));
				setGritter(CommonUtils.Gritter.GRITTER_TYPE_CENTER, dict.getStr("label")+" 系统平台", "已切换到 "+dict.getStr("label")+" 系统平台，用户、角色和权限等操作都将绑定到 "+dict.getStr("label")+" 平台下！", null, 3000, false);
			}
		}
		renderJson(1);
	}

}
