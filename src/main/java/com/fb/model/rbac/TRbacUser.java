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
import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.ext.kit.PageSort;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class TRbacUser extends Model<TRbacUser> {

	private static final long serialVersionUID = -6384287766541316998L;
	
	public static TRbacUser me = new TRbacUser();
	
	/**
	 * 分页查询管理员
	 * @author sun
	 * @date 2016年10月13日 下午3:52:07
	 * @param ps
	 * @param keyword
	 * @param roleId
	 * @return
	 */
	public Page<TRbacUser> findPage(PageSort ps, String keyword, Long roleId, String status) {
		String mSql = Const.BLANK;
		if (!Strings.isNullOrEmpty(keyword)) {
			mSql += " and (u.username like '%" + keyword + "%' or u.realname like '%" + keyword + "%' or u.email like '%" + keyword + "%' or u.tel like '%" + keyword + "%')";
		}

		if(StringUtils.isNotEmpty(status)){
			mSql += " and u.status = "+status;
		}
		if(roleId!=null && roleId>0){
			mSql += " and ur.role_id in ("+roleId+")";
		}
		String select = "select `u`.*,group_concat(DISTINCT `r`.`name` SEPARATOR ',') AS `role_name`,group_concat(DISTINCT `r`.`id` SEPARATOR ',') AS `role_ids`,group_concat(DISTINCT `ru`.`pid` SEPARATOR ',') AS `ugroup_pids`,group_concat(DISTINCT `ru`.`id` SEPARATOR ',') AS `ugroup_ids`,group_concat(DISTINCT `ru`.`name` SEPARATOR ',') AS `ugroup_names`, d.label, d.color";
		String from = "from `t_rbac_user` `u` LEFT JOIN `t_rbac_user_role_ref` `ur` ON (`u`.`id` = `ur`.`user_id`) LEFT JOIN `t_rbac_role` `r` ON (`ur`.`role_id` = `r`.`id`) LEFT JOIN `t_rbac_ugroup_user_ref` `ruur` ON (`ruur`.`user_id` = `u`.`id`) LEFT JOIN `t_rbac_ugroup` `ru` ON `ru`.`id` = `ruur`.`ugroup_id` LEFT JOIN t_dictionary d on d.val = u.platform";
		return paginate(ps.getPageNumber(), ps.getPageSize(), select, from + " where 1=1 "+ mSql + " GROUP BY `u`.`id`" + ps.toString());
	}
	
	public TRbacUser login(String username, String password) {
		return findFirst("select * from t_rbac_user where username = ? and password = ?", username, password);
	}
	
	public TRbacUser findById(Long id) {
		return findFirst("select ru.*, rur.ugroup_id from t_rbac_user ru left join t_rbac_ugroup_user_ref rur on rur.user_id = ru.id where ru.id = ?", id);
	}
	
	public TRbacUser findByIdAndPlatForm(Long id, String platform) {
		String mSql = Const.BLANK;
		if(StringUtils.isNotEmpty(platform)){
			mSql += " and platform = "+platform;
		}
		return findFirst("select * from t_rbac_user where id = ?"+mSql, id);
	}
	
	/**
	 * 新版login方法
	 * @author sun
	 * @date 2016年7月18日 下午5:12:10
	 * @param username
	 * @param password
	 * @return
	 */
	public TRbacUser login(String username) {
		return findFirst("select * from t_rbac_user where username = ? or bind_user = ?", username, username);
	}
	
	/**
	 * 校验登录用户名是否合法
	 * @author sun
	 * @date 2016年7月19日 下午1:57:29
	 * @param username
	 * @param id
	 * @return
	 */
	public TRbacUser verifyUserName(String username, Long id){
		String mSql = Const.BLANK;
		if(id!=null){
			mSql += "and id!="+id;
		}
		return findFirst("select * from t_rbac_user where (username = ? or bind_user = ?) "+mSql, username, username);
	}
	
	/**
	 * 查找所有客服用户信息-------约定配置  ------username以kf_开头
	 * @return
	 */
	public List<TRbacUser> findKfAll(){
		//return find("select * from t_rbac_user where username like 'kf_%' ");
		return find("SELECT u.* FROM t_rbac_user u LEFT JOIN t_rbac_user_role_ref r ON u.id=r.user_id WHERE r.`role_id` IN (SELECT role.`id` FROM t_rbac_role role WHERE role.`name` LIKE '客服%') group by u.username");
	}
	
}
