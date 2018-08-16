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
import com.fb.kit.DateUtils;
import com.fb.model.rbac.TRbacUser;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;

/**
 * 用户信息
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/profile/info")
public class ProfileInfoController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(ProfileInfoController.class);
	
	/**
	 * 编辑用户信息
	 */
	public void edit() {
		TRbacUser user = TRbacUser.me.findById(JfinalUtils.getSysId(getSession()));

		if (isGet()) {
			setAttr("user", user);
		}
		if (isPost()) {
			user.set("realname", getPara("realname"));
			user.set("email", getPara("email"));
			user.set("tel", getPara("tel"));
			user.set("avatar", getPara("avatar"));
			user.set("update_time", DateUtils.getDate());
			user.update();

			setAttr("user", user);
			getSession().setAttribute(Const.SER_USER, user);

			setAttr(Const.MSG_TYPE, Const.MsgType.SUCCESS);
			setAttr(Const.MSG_CONTENT, Const.MsgType.SUCCESS);
		}

	}

}
