package com.fb.model;
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

/**
 * 安全管理接口配置
 * @author sun
 * @date 2016年6月16日 下午6:10:34
 */
public class TRestfulSetting extends Model<TRestfulSetting> {

	private static final long serialVersionUID = 3990798227774784248L;
	public static TRestfulSetting me = new TRestfulSetting();
	
	public Page<TRestfulSetting> findPage(PageSort ps, String keyword){
		String mSql = Const.BLANK;
		if (!Strings.isNullOrEmpty(keyword)) {
			mSql += " and (`name` like '%"+keyword+"%' or `key` like '%"+keyword+"%' or ips like '%"+keyword+"%' or description like '%"+keyword+"%')";
		}
		return paginate(ps.getPageNumber(), ps.getPageSize(), "select * ","from t_restful_setting where stu!=-1 "+mSql+ ps.toString());
	}
	
}
