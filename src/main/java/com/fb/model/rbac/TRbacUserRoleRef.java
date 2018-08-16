package com.fb.model.rbac;
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

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class TRbacUserRoleRef extends Model<TRbacUserRoleRef> {

	private static final long serialVersionUID = -7987018512535519915L;
	
	public static TRbacUserRoleRef me = new TRbacUserRoleRef();
	
	public List<TRbacUserRoleRef> findRoleByUser(Long userId) {
		return find("select rr.role_id from t_rbac_user_role_ref rr left join t_rbac_role r on r.id = rr.role_id where rr.user_id = ? and r.status = 0", userId);
	}
	
	public void delete(Long userId, Object roleId) {
		Db.update("delete from t_rbac_user_role_ref where user_id = ? and role_id = ?", userId, roleId);
	}
	
	public void removeRoleByUser(Long userId) {
		Db.update("delete from t_rbac_user_role_ref where user_id = ?", userId);
	}

	/**
	 * 查询某个用户的所有角色
	 * @author sun
	 * @date 2016年8月5日 上午10:22:00
	 * @param userId
	 * @return
	 */
	public String searchRoleIds(Long userId){
		String roleIds = null;
		TRbacUserRoleRef urr = findFirst("select GROUP_CONCAT(role_id) as role_ids from t_rbac_user_role_ref where user_id = ?", userId);
		if(urr!=null){
			roleIds = urr.getStr("role_ids");
		}
		return roleIds;
	}
	
}
