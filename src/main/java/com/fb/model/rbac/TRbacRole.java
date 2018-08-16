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
import com.fb.kit.CommonUtils;
import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.ext.kit.PageSort;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class TRbacRole extends Model<TRbacRole> {

	private static final long serialVersionUID = -8391140490115724853L;
	
	public static TRbacRole me = new TRbacRole();
	
	public Page<TRbacRole> findAll(PageSort ps, String keyword) {
		String mSql = Const.BLANK;
		if (!Strings.isNullOrEmpty(keyword)) {
			mSql += " and (js.name like '%" + keyword + "%' or js.remark like '%" + keyword + "%')";
		}

		return paginate(ps.getPageNumber(), ps.getPageSize(), "SELECT js.*, d.label, d.color", "from t_rbac_role js LEFT JOIN t_dictionary d on d.val = js.platform where 1=1 " + mSql + ps.toString());
	}
	
	public List<TRbacRole> findAll(String platform) {
		String mSql = Const.BLANK;
		if(StringUtils.isNotEmpty(platform)){
			mSql += " where platform = "+platform;
		}
		return find("select * from t_rbac_role "+mSql+" order by add_time ASC");
	}
	
	public String findBy(String ids){
		TRbacRole role = findFirst("select GROUP_CONCAT(`name`) as role_names from t_rbac_role where id in ("+ids+")");
		String roleNames = null;
		if(role!=null){
			roleNames = role.getStr("role_names");
		}
		return roleNames;
	}
	
	/**
	 * 根据ID查询角色
	 * @author sun
	 * @date 2016年10月13日 下午2:50:39
	 * @param id
	 * @param ugroupPid
	 * @return
	 */
	public TRbacRole findBy(Long id, String platform){
		return findFirst("select * from t_rbac_role where id = ? and platform = ?", id, platform);
	}
	
	/**
	 * 根据系统平台查询对应的角色
	 * @author sun
	 * @date 2016年10月28日 下午4:02:27
	 * @param platform
	 * @return
	 */
	public List<TRbacRole> findByPlatform(String platform){
		String mSql = Const.BLANK;
		if(StringUtils.isNotEmpty(platform)){
			mSql += " and platform = "+platform;
		}
		return find("select * from t_rbac_role where `status` = '0' "+mSql+" order by add_time desc");
	}
	
	public TRbacRole findByName(String name){
		return findFirst("select * from t_rbac_role where name = ?", name);
	}

}
