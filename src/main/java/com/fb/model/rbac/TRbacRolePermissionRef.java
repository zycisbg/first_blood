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

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class TRbacRolePermissionRef extends Model<TRbacRolePermissionRef> {

	private static final long serialVersionUID = -3919178253813536368L;
	
	public static TRbacRolePermissionRef me = new TRbacRolePermissionRef();
	
	public void removePermissionsByRole(Long roleId) {
		Db.update("delete from t_rbac_role_permission_ref where role_id = ?", roleId);
	}
	
	public List<String> findBy(Long roleId, String platform) {
		List<String> list = Lists.newArrayList();
		List<TRbacRolePermissionRef> rps = find("select DISTINCT rpr.permission_id as permission_id from t_rbac_role_permission_ref rpr LEFT JOIN t_rbac_permission p on p.id = rpr.permission_id where rpr.role_id = ? and p.platform = ?", roleId, platform);
		if(rps != null){
			for (TRbacRolePermissionRef rp : rps) {
				list.add(rp.getLong("permission_id").toString());
			}
		}
		return list;
	}
	
	public void deleteBy(Object permissionId, Long roleId){
		Db.update("delete from t_rbac_role_permission_ref where permission_id = ? and role_id = ?", permissionId, roleId);
	}

}
