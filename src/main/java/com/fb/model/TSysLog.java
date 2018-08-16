package com.fb.model;

import com.fb.core.Const;
import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.ext.kit.PageSort;
import org.apache.commons.lang.StringUtils;

/**
 * @Description: TODO(微信登录、操作日志表)
 * @author sun
 * @date 2015年11月28日 下午3:01:37
 * 
 */
public class TSysLog extends Model<TSysLog> {

	private static final long serialVersionUID = 7744980420917147608L;
	public static TSysLog me = new TSysLog();
	
	public Page<TSysLog> findPage(PageSort ps, String keyword, Integer type, Integer platform, String startTime, String endTime){
		String mSql = Const.BLANK;
		if (!StringUtils.isEmpty(startTime)) {
			startTime = startTime + " 00:00:00";
			mSql += " and add_time >= '" + startTime + "'";
		}
		if (!StringUtils.isEmpty(endTime)) {
			endTime = endTime + " 23:59:59";
			mSql += " and add_time <= '" + endTime + "'";
		}
		if (type!=null) {
			mSql += " and type = "+type;
		}
		if (platform!=null) {
			mSql += " and platform = "+platform;
		}
		if (!Strings.isNullOrEmpty(keyword)) {
			mSql += " and (user_name = '"+keyword+"' or description like '%"+keyword+"%')";
		}
		return paginate(ps.getPageNumber(), ps.getPageSize(), "select * ","from t_sys_log where 1=1 "+mSql+ps.toString());
	}
}
