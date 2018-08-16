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
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public class TRbacUgroupUserRef extends Model<TRbacUgroupUserRef> {

	private static final long serialVersionUID = -5675677422950086291L;
	
	public static TRbacUgroupUserRef me = new TRbacUgroupUserRef();
	
	/**
	 * 查询用户和用户组绑定的信息
	 * @author sun
	 * @date 2016年10月11日 下午6:14:25
	 * @param ugroupId
	 * @param userId
	 * @return
	 */
	public TRbacUgroupUserRef findBy(Long ugroupId, Long userId) {
		String mSql = Const.BLANK;
		if(ugroupId!=null){
			mSql += " and ugroup_id="+ugroupId;
		}
		return findFirst("select * from t_rbac_ugroup_user_ref where user_id = ? "+mSql, userId);
	}
	
	/**
	 * 查询某用户已经绑定的用户组ID
	 * @author sun
	 * @date 2016年10月12日 上午10:13:17
	 * @param userId
	 * @return
	 */
	public List<String> findBy(Long userId) {
		List<String> list = Lists.newArrayList();
		TRbacUgroupUserRef ruur = findFirst("select GROUP_CONCAT(ugroup_id) as ugroup_ids from t_rbac_ugroup_user_ref where user_id = ?", userId);
		if(ruur!=null){
			String ugroupIds = ruur.getStr("ugroup_ids");
			if(StringUtils.isNotEmpty(ugroupIds)){
				list = Arrays.asList(ugroupIds.split(","));
			}
		}
		return list;
	}
	
	/**
	 * 删除
	 * @author sun
	 * @date 2016年10月12日 上午10:28:52
	 * @param ugroupId
	 * @param userId
	 */
	public void deleteBy(Object ugroupId, Long userId){
		Db.update("delete from t_rbac_ugroup_user_ref where ugroup_id = ? and user_id = ?", ugroupId, userId);
	}
	
	/**
	 * 查询某组下所有的用户信息
	 * @author sun
	 * @date 2016年10月11日 下午6:46:04
	 * @param ugroupId
	 * @return
	 */
	public List<TRbacUgroupUserRef> findByGroupId(Long ugroupId) {
		return find("select * from t_rbac_ugroup_user_ref where ugroup_id = ? ", ugroupId);
	}
	
}
