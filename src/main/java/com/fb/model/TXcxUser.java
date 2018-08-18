package com.fb.model;

import com.fb.core.Const;
import com.jfinal.ext.kit.PageSort;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

public class TXcxUser extends Model<TXcxUser> {
    private static final long serialVersionUID = 7744980420917147608L;
    public static TXcxUser me = new TXcxUser();

    public Page<TXcxUser> findPage(PageSort ps, String realname, String nickname, String mobile, String startTime, String endTime,String role,String address,String isAuth){
        String mSql = Const.BLANK;
        if (!StringUtils.isEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
            mSql += " and u.last_login_time >= '" + startTime + "'";
        }
        if (!StringUtils.isEmpty(endTime)) {
            endTime = endTime + " 23:59:59";
            mSql += " and u.last_login_time <= '" + endTime + "'";
        }
        if (StringUtils.isNotEmpty(realname)) {
            mSql += " and u.realname like '%" + realname + "%'";
        }
        if (StringUtils.isNotEmpty(nickname)) {
            mSql += " and u.nickName like '%" + nickname + "%'";
        }
        if (StringUtils.isNotEmpty(address)) {
            mSql += " and u.home_address like '%" + address + "%'";
        }
        if (StringUtils.isNotEmpty(mobile)) {
            mSql += " and u.mobile = '" + mobile + "'";
        }
        if (StringUtils.isNotEmpty(role)) {
            mSql += " and u.role = '" + role + "'";
        }
        if (StringUtils.isNotEmpty(isAuth)) {
            mSql += " and u.is_auth = '" + isAuth + "'";
        }

        return paginate(ps.getPageNumber(), ps.getPageSize(),
                "select u.*,case when sex = '1' then '男'  else '女' end as sexText,case when is_auth ='1' then '已认证' else '未认证' end as authText,case when status = '1' then '正常' else '已禁用' end as statusText ",
                "from t_xcx_user u where 1=1 "+mSql+ps.toString());
    }
}
