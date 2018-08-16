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

import com.fb.controller.BaseController;
import com.fb.kit.CommonUtils;
import com.fb.kit.DateUtils;
import com.fb.kit.JsonUtils;
import com.fb.model.rbac.TRbacUserConfig;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;

import java.util.Map;
import java.util.Set;

/**
 * 个人基本设置
 * @author sun
 * @date 2016年7月28日 下午3:56:08
 */
@ControllerBind(controllerKey = "/profile/config")
public class ProfileConfigController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(ProfileConfigController.class);
	
	private static final String PATH = "/profile/config";
	
	/**
	 * 编辑个人设置 
	 * @author sun
	 * @date 2016年7月28日 下午3:56:39
	 */
	public void edit() {
		if (isGet()) {
			//系统个人用户的配置信息
			TRbacUserConfig colors = (TRbacUserConfig)getSession().getAttribute(CommonUtils.SettingGlobal.SYSTEM_SETTING_COLOR);
			if(colors!=null){
				String val = colors.getStr("val");
				Map<String, Object> map = JsonUtils.parseJSON2Map(val);
				if(map!=null && map.size()>0){
					Set<String> ks = map.keySet();
					for(String k:ks){
						setAttr(k, map.get(k));
					}
				}
			}
			render("_form.html");
		}
		if (isPost()) {
			//系统个人用户的配置信息
			TRbacUserConfig colors = (TRbacUserConfig)getSession().getAttribute(CommonUtils.SettingGlobal.SYSTEM_SETTING_COLOR);
			if(colors!=null){
				String val = colors.getStr("val");
				Map<String, Object> map = JsonUtils.parseJSON2Map(val);
				if(map!=null && map.size()>0){
					Set<String> ks = map.keySet();
					for(String k:ks){
						String value = getPara(k);
						map.put(k, value);
					}
					//保存新map数据
					String json = JsonUtils.obj2Json(map);
					colors.set("val", json).set("update_time", DateUtils.getDate()).update();
				}
				//更新session信息
				getSession().setAttribute(CommonUtils.SettingGlobal.SYSTEM_SETTING_COLOR, colors);
			}
			redirect(PATH, "edit");
		}
	}

}
