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

import java.util.List;

public class TRbacFunction extends Model<TRbacFunction> {

	private static final long serialVersionUID = 22770640984231714L;
	
	public static TRbacFunction me = new TRbacFunction();
	
	public Page<TRbacFunction> findAll(int pageNumber, int pageSize, String keyword) {
		String mSql = Const.BLANK;
		if (!Strings.isNullOrEmpty(keyword)) {
			mSql = "and (name like '%" + keyword + "%' or method_key like '%" + keyword + "%')";
		}
		return paginate(pageNumber, pageSize, "select *", String.format("from t_rbac_function where 1 = 1 %s order by add_time ASC", mSql));
	}
	
	public List<TRbacFunction> findAll() {
		return find("SELECT * FROM t_rbac_function ORDER BY add_time ASC");
	}
	
	/**
	 * 检测某权限方法是否已启用<br/>
	 * 一旦禁用，全局禁用。所有角色均不可使用
	 * @author sun
	 * @date 2016年10月17日 上午11:13:12
	 * @param methodKey
	 * @return
	 */
	public boolean verify(String methodKey){
		TRbacFunction rf = findFirst("select status from t_rbac_function where method_key = ?", methodKey);
		if(rf!=null){
			if("0".equals(rf.getStr("status"))){
				return true;
			}
		}	
		return false;
	}

}
