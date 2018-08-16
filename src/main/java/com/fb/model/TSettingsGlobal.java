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

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 全局设置
 * 
 * @author sun
 * @date 2016年5月27日 下午2:09:23
 */
public class TSettingsGlobal extends Model<TSettingsGlobal> {

	private static final long serialVersionUID = 1L;
	public static TSettingsGlobal me = new TSettingsGlobal();

	/**
	 * 根据key查询配置信息
	 * 
	 * @author sun
	 * @date 2016年5月27日 下午2:07:09
	 * @param key
	 * @return
	 */
	public TSettingsGlobal findByKey(String key, String platform) {
		return findFirst("select * from t_settings_global where platform = ? and `key` = ? and stu = 1", platform, key);
	}

	/**
	 * 查询所有生效的全局设置
	 * @author sun
	 * @date 2016年5月27日 下午2:10:57
	 * @return
	 */
	public List<TSettingsGlobal> findAll(String platform) {
		return find("select * from t_settings_global where platform = ? and stu = 1", platform);
	}
	
	/**
	 * 查询库中所有的平台数据
	 * @author sun
	 * @date 2016年10月17日 上午11:33:55
	 * @return
	 */
	public List<String> findPlatformBy(){
		List<String> list = Lists.newArrayList();
		List<TSettingsGlobal> sgList = find("select DISTINCT(platform) from t_settings_global");
		if(sgList!=null && sgList.size()>0){
			for(TSettingsGlobal sg:sgList){
				list.add(sg.getStr("platform"));
			}
		}
		return list;
	}

}
