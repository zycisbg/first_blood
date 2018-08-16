package com.fb.model.rbac;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 客服访问用户记录表
 * @author shi.g.s
 * @date 2016年9月20日
 */
public class TRbacUserViewRecord extends Model<TRbacUserViewRecord> {

	private static final long serialVersionUID = 5828687404375998680L;
	public static TRbacUserViewRecord me = new TRbacUserViewRecord();
	
	/**
	 * 查询用户被访问的记录
	 * @author shi.g.s
	 * @date 2016年9月20日
	 * @return
	 */
	public List<TRbacUserViewRecord> findByUserId(String userId){
		String sql = "select r.id,r.rbac_user_id,r.desc,r.rbac_user_name from t_rbac_user_view_record r where r.user_id = ? and r.market = 0";
		return find(sql, userId);
	}
}
