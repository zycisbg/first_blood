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
import com.jfinal.kit.EncryptionKit;
import com.jfinal.log.Logger;

/**
 * 用户密码
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/profile/pwd")
public class ProfilePwdController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(ProfilePwdController.class);
	
	/**
	 * 编辑用户密码
	 */
	public void edit() {
		if (isPost()) {
			TRbacUser user = TRbacUser.me.login(JfinalUtils.getSysName(getSession()), EncryptionKit.md5Encrypt(getPara("oldpassword")));
			if (user == null) {
				setMsg(Const.MsgType.ERROR, "30590", false);
			} else {
				user.set("password", EncryptionKit.md5Encrypt(getPara("newpassword")));
				user.set("update_time", DateUtils.getDate());
				user.update();
				setMsg(Const.MsgType.ERROR, "10103", false);
			}
		}
	}

}
