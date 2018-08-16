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
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public class TRbacUgroupRoleRef extends Model<TRbacUgroupRoleRef> {

	private static final long serialVersionUID = -2108903007751203927L;
	
	public static TRbacUgroupRoleRef me = new TRbacUgroupRoleRef();
	
	/**
	 * 查询某一组用户组拥有的所有角色
	 * @author sun
	 * @date 2016年10月12日 下午1:44:32
	 * @param userId
	 * @return
	 */
	public String findByGroupIds(String ugroupIds){
		String roleIds = null;
		TRbacUgroupRoleRef urr = findFirst("select GROUP_CONCAT(role_id) as role_ids from t_rbac_ugroup_role_ref where ugroup_id in ("+ugroupIds+") ");
		if(urr!=null){
			roleIds = urr.getStr("role_ids");
		}
		return roleIds;
	}
	
	/**
	 * 查询某一个用户组拥有的所有角色
	 * @author sun
	 * @date 2016年10月12日 下午1:44:32
	 * @param userId
	 * @return
	 */
	public String findByGroupId(Long ugroupId){
		String roleIds = null;
		TRbacUgroupRoleRef urr = findFirst("select GROUP_CONCAT(role_id) as role_ids from t_rbac_ugroup_role_ref where ugroup_id = ? ", ugroupId);
		if(urr!=null){
			roleIds = urr.getStr("role_ids");
		}
		return roleIds;
	}
	
	/**
	 * 查询某用户组已经绑定的所有角色
	 * @author sun
	 * @date 2016年10月12日 上午10:13:17
	 * @param userId
	 * @return
	 */
	public List<String> findBy(Long ugroupId) {
		List<String> list = Lists.newArrayList();
		TRbacUgroupRoleRef ruur = findFirst("select GROUP_CONCAT(role_id) as role_ids from t_rbac_ugroup_role_ref where ugroup_id = ?", ugroupId);
		if(ruur!=null){
			String roleIds = ruur.getStr("role_ids");
			if(StringUtils.isNotEmpty(roleIds)){
				list = Arrays.asList(roleIds.split(","));
			}
		}
		return list;
	}
	
	/**
	 * 删除
	 * @author sun
	 * @date 2016年10月12日 上午10:28:52
	 * @param ugroupId
	 * @param roleId
	 */
	public void deleteBy(Long ugroupId, Object roleId){
		Db.update("delete from t_rbac_ugroup_role_ref where ugroup_id = ? and role_id = ?", ugroupId, roleId);
	}
	
	public void deleteBy(Long ugroupId){
		Db.update("delete from t_rbac_ugroup_role_ref where ugroup_id = ?", ugroupId);
	}
	
	/**
	 * 查询某个用户组所拥有的角色
	 * @author sun
	 * @date 2016年10月12日 下午3:28:12
	 * @param ugroupId
	 * @return
	 */
	public TRbacUgroupRoleRef findByUGroupId(Long ugroupId){
		String sql = "SELECT GROUP_CONCAT(DISTINCT(rr.id)) AS role_ids, GROUP_CONCAT(DISTINCT(rr.`name`)) AS role_names FROM t_rbac_role rr LEFT JOIN t_rbac_ugroup_role_ref rurr ON rurr.role_id = rr.id WHERE rurr.ugroup_id = ?";
		return findFirst(sql, ugroupId);
	}
	
}
