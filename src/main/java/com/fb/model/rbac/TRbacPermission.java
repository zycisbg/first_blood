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

import com.fb.core.Const;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class TRbacPermission extends Model<TRbacPermission> {

	private static final long serialVersionUID = 603260283152796107L;
	
	public static TRbacPermission me = new TRbacPermission();
	
	private void findByParent(Long parentId, List<TRbacPermission> list, String platform) {
		List<TRbacPermission> permisstions = find("select rp.*,rf.method_key, rf.`name` as function_name, rf.icon as function_icon from t_rbac_permission rp LEFT JOIN t_rbac_function rf on rf.id = rp.function_id where rp.pid = ? and platform = ? and rp.stu!=-1 order by rp.sort ASC", parentId, platform);
		for (TRbacPermission p : permisstions) {
			list.add(p);
			findByParent(p.getLong("id"), list, platform);
		}
	}
	
	/**
	 * 递归查询权限
	 * @return
	 */
	public List<TRbacPermission> findRecursiveAll(String platform) {
		List<TRbacPermission> list = Lists.newArrayList();
		findByParent(0L, list, platform);
		return list;
	}
	
	public TRbacPermission findById(Long id, String platform) {
		return findFirst("select rp.*,rf.method_key, rf.`name` as function_name, rf.icon as function_icon from t_rbac_permission rp LEFT JOIN t_rbac_function rf on rf.id = rp.function_id where rp.id = ? and rp.platform = ?", id, platform);
	}
	
	/**
	 * 默认需要分配的权限
	 * @author sun
	 * @date 2016年10月17日 上午10:45:03
	 * @param isDefault
	 * @return
	 */
	public String getIsDefaultPermission(String platform){
		TRbacPermission rp = findFirst("select GROUP_CONCAT(id) as ids from t_rbac_permission where is_default = 1 and platform = ?", platform);
		String ids = "";
		if(rp!=null){
			ids = rp.getStr("ids");
		}
		return ids;
	}
	
	/**
	 * 根据父节点查询子节点。 如果为0，则表示查询所有的父节点
	 * @author sun
	 * @date 2016年10月14日 下午1:17:33
	 * @param pid
	 * @return
	 */
	public List<TRbacPermission> findByPid(Long pid, String platform){
		List<TRbacPermission> list = Lists.newArrayList();
		List<TRbacPermission> permisstions = find("select p.* from t_rbac_permission p where p.pid = ? and platform = ? order by p.sort ASC", pid, platform);
		if(permisstions!=null && permisstions.size()>0){
			for(TRbacPermission p:permisstions){
				list.add(p);
				List<TRbacPermission> childList = find("select p.* from t_rbac_permission p where p.pid = ? and platform = ? order by p.sort ASC", p.getLong("id"), platform);
				list.addAll(childList);
			}
		}
		return list;
	}
	
	/**
	 * 获取等级
	 * @author sun
	 * @date 2016年10月14日 下午8:08:30
	 * @param id
	 * @return
	 */
	public Integer findLevel(Long id){
		Integer level = 1;
		TRbacPermission p = findFirst("select * from t_rbac_permission where id = ?", id);
		if(p!=null){
			level = p.getInt("level");
		}
		return level+1;
	}

	/**
	 * 查询角色对应的权限
	 * @author sun
	 * @date 2016年10月15日 上午9:11:27
	 * @param roleId
	 * @param pid
	 * @return
	 */
	public List<TRbacPermission> findByRoleIdAndPid(String roleIds, Long pid){
		return find("select rp.*,rf.method_key, rf.`name` as function_name, rf.icon as function_icon from t_rbac_permission rp LEFT JOIN t_rbac_role_permission_ref rrp on rrp.permission_id = rp.id LEFT JOIN t_rbac_function rf on rf.id = rp.function_id where rp.platform = ? and rrp.role_id in ("+roleIds+") and pid = ? GROUP BY rp.id order by sort asc", Const.PROJECT_PLATFORM, pid);
	}
	
}
