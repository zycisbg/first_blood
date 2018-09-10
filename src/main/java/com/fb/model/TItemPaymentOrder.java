package com.fb.model;

import com.fb.core.Const;
import com.jfinal.ext.kit.PageSort;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

public class TItemPaymentOrder extends Model<TItemPaymentOrder> {
    private static final long serialVersionUID = 7744980420917147608L;
    public static TItemPaymentOrder me = new TItemPaymentOrder();

    public Page<TItemPaymentOrder> findPage(PageSort ps, String sellerMobile,String userMobile,String waterId, String startTime, String endTime, String status){
        String mSql = Const.BLANK;
        if (!StringUtils.isEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
            mSql += " and p.time_start >= '" + startTime + "'";
        }
        if (!StringUtils.isEmpty(endTime)) {
            endTime = endTime + " 23:59:59";
            mSql += " and p.time_start <= '" + endTime + "'";
        }
        if (StringUtils.isNotEmpty(sellerMobile)) {
            mSql += " and s.mobile = '" + sellerMobile + "'";
        }
        if (StringUtils.isNotEmpty(userMobile)) {
            mSql += " and b.mobile = '" + userMobile + "'";
        }
        if (StringUtils.isNotEmpty(waterId)) {
            mSql += " and p.payment_water_id = '" + waterId + "'";
        }
        if (StringUtils.isNotEmpty(status)) {
            mSql += " and p.status = '" + status + "'";
        }

        return paginate(ps.getPageNumber(), ps.getPageSize(),
                "select p.*,b.nickName,s.realname as sName,b.mobile as bMobile,b.nickName,case when p.order_status='1' then '付款成功' else '付款时间' end as statusText ",
                "from t_item_payment_order p left join t_xcx_user s on p.seller_id = s.id left join t_xcx_user b on b.id = p.user_id where 1=1 "+mSql+ps.toString());
    }
}
