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
import com.fb.kit.CommonUtils;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.ext.kit.PageSort;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @Description: TODO(数据字典)
 * @author sun
 * @date 2015年12月12日 下午2:44:55
 * 
 */
public class TDictionary extends Model<TDictionary> {

	private static final long serialVersionUID = 7984717557662854404L;
	public static TDictionary me = new TDictionary();
	
	public Page<TDictionary> findPage(PageSort ps, String keyword, String chatCode){
		String mSql = "";
		if(!StringUtils.isEmpty(keyword)){
			mSql += " and label like '%"+keyword+"%' or description like '%"+keyword+"%'";
		}
		if(!StringUtils.isEmpty(chatCode)){
			mSql += " and chat_code = '"+chatCode+"'";
		}
		return paginate(ps.getPageNumber(), ps.getPageSize(), "select *", "from t_dictionary where is_lock = 0 "+mSql+" and stu != -1" + ps.toString());
	}
	
	/**  
	 * @Description: TODO(查询所有的字典类型， 字典config)  
	 * @author sun
	 * @date 2015年12月12日 下午2:46:28
	 * @param @return    设定文件  
	 * @return List<TWechatDictionary>    返回类型  
	 */
	public List<TDictionary> findTypeAll(){
		return find("select chat_code from t_dictionary where is_lock = 0 and stu = 1 group by chat_code order by add_time asc");
	}
	
	/**  
	 * @Description: TODO(查询某类型的所有字典数据)  
	 * @author sun
	 * @date 2015年12月12日 下午2:57:32
	 * @param @param chatCode
	 * @param @return    设定文件  
	 * @return List<TWechatDictionary>    返回类型  
	 */
	public List<TDictionary> findByChatCode(String chatCode){
		return find("select * from t_dictionary where chat_code = ? and stu = 1 order by sort asc ", chatCode);
	}
	
	/**  
	 * @Description: TODO(查询锁定的字典数据)  
	 * @author sun
	 * @date 2015年12月16日 上午11:50:26
	 * @param @param chatCode
	 * @param @return    设定文件  
	 * @return TWechatDictionary    返回类型  
	 */
	public TDictionary findByChatCodeIsLock(String chatCode){
		return findFirst("select * from t_dictionary where chat_code = ? and is_lock = 1", chatCode);
	}
	
	/**  
	 * @Description: TODO(根据类型config和val值查询对应的数据)  
	 * @author sun
	 * @date 2015年12月12日 下午3:06:03
	 * @param @param chatCode
	 * @param @param val
	 * @param @return    设定文件  
	 * @return TWechatDictionary    返回类型  
	 */
	public TDictionary findByCodeAndVal(String chatCode, String val){
		return findFirst("select * from t_dictionary where chat_code = ? and val = ? and stu = 1", chatCode, val);
	}
	
	public TDictionary findByCodeAndLabel(String chatCode, String label){
		return findFirst("select * from t_dictionary where chat_code = ? and label = ? and stu = 1", chatCode, label);
	}
	
	/**  
	 * @Description: TODO(根据主键ID和chatcode确定某数据是否真实存在)  
	 * @author sun
	 * @date 2015年12月16日 上午11:35:09
	 * @param @param id
	 * @param @param chatCode
	 * @param @return    设定文件  
	 * @return TWechatDictionary    返回类型  
	 */
	public TDictionary findByIdAndCode(Long id, String chatCode){
		return findFirst("select * from t_dictionary where id = ? and chat_code = ?", id, chatCode);
	}
	
	/**
	 * 查询平台的所有的字典基本配置信息
	 * @param platform
	 * @return
	 */
	public List<TDictionary> findByPlatform(Integer platform){
		return find("select * from t_dictionary where platform = ? order by type asc", platform);
	}
	
	/**
	 * 查询所有平台
	 * @author sun
	 * @date 2016年10月26日 下午3:55:18
	 * @return
	 */
	public List<TDictionary> findPlatFormBy(String platform){
		String mSql = Const.BLANK;
		if(StringUtils.isNotEmpty(platform)){
			mSql += " and d.val = '"+platform+"'";
		}
		return find("select d.*, (select sg.`value` from t_settings_global sg where sg.`key` = 'project_name' and sg.platform = d.val) as project_name, (select sg.`value` from t_settings_global sg where sg.`key` = 'system_left_top_logo' and sg.platform = d.val) as system_left_top_logo, (select sg.`value` from t_settings_global sg where sg.`key` = 'company_name' and sg.platform = d.val) as company_name from t_dictionary d where d.chat_code = ? and d.stu = 1"+mSql, CommonUtils.DictionaryKey.SYSTEM_PLATFORM);
	}
	
	public TDictionary findPlatFormByVal(Integer val){
		return findFirst("select * from t_dictionary d where d.chat_code = ? and d.val = ?", CommonUtils.DictionaryKey.SYSTEM_PLATFORM, val);
	}
	
}
