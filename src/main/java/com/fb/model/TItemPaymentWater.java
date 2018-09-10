package com.fb.model;

import com.fb.core.Const;
import com.jfinal.ext.kit.PageSort;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

public class TItemPaymentWater extends Model<TItemPaymentWater> {
    private static final long serialVersionUID = 7744980420917147608L;
    public static TItemPaymentWater me = new TItemPaymentWater();

    public Page<TItemPaymentWater> findPage(PageSort ps, String mobile,String startTime, String endTime,String status){
        String mSql = Const.BLANK;
        if (!StringUtils.isEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
            mSql += " and p.payment_time >= '" + startTime + "'";
        }
        if (!StringUtils.isEmpty(endTime)) {
            endTime = endTime + " 23:59:59";
            mSql += " and p.payment_time <= '" + endTime + "'";
        }
        if (StringUtils.isNotEmpty(mobile)) {
            mSql += " and u.mobile = '" + mobile + "'";
        }
        if (StringUtils.isNotEmpty(status)) {
            mSql += " and p.status = '" + status + "'";
        }

        return paginate(ps.getPageNumber(), ps.getPageSize(),
                "select p.*,u.nickName,u.mobile,a.address_detail,case when p.status='1' then '付款成功' else '付款时间' end as statusText ",
                "from t_item_payment_water p left join t_xcx_user u on u.id = p.user_id left join t_xcx_user_address a on a.id = p.address_id where 1=1 "+mSql+ps.toString());
    }
}
