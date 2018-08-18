package com.fb.model;

import com.fb.core.Const;
import com.google.common.base.Strings;
import com.jfinal.ext.kit.PageSort;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

public class TProduct extends Model<TProduct> {
    private static final long serialVersionUID = 7744980420917147608L;
    public static TProduct me = new TProduct();

    public Page<TProduct> findPage(PageSort ps, String method, String name,String username, String startTime, String endTime){
        String mSql = Const.BLANK;
        if (!StringUtils.isEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
            mSql += " and p.list_time >= '" + startTime + "'";
        }
        if (!StringUtils.isEmpty(endTime)) {
            endTime = endTime + " 23:59:59";
            mSql += " and p.list_time <= '" + endTime + "'";
        }
        if (StringUtils.isNotEmpty(method)) {
            mSql += " and p.status = '" + method + "'";
        }
        if (StringUtils.isNotEmpty(name)) {
            mSql += " and p.name like '%" + name + "%'";
        }
        if (StringUtils.isNotEmpty(username)) {
            mSql += " and u.realname like '%" + username + "%'";
        }

        return paginate(ps.getPageNumber(), ps.getPageSize(),
                "select p.*,u.realname,case when p.status = '1' then '待上架' when p.status = '2' then '已上架' when p.status = '3' then '申请上架中' else '已驳回' end as statusText ",
                "from t_product p left join t_xcx_user u on p.user_id = u.id where 1=1 "+mSql+ps.toString());
    }
}
