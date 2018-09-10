package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.kit.ToolsUtils;
import com.fb.model.TItemPaymentOrder;
import com.fb.model.TItemPaymentWater;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey = "/platform/merchantOrder")

public class OrderController extends BaseController {
    protected static final com.jfinal.log.Logger LOG = com.jfinal.log.Logger.getLogger(OrderController.class);

    public void search(){
        String sellerMobile = getPara("sellerMobile");
        String buyerMobile = getPara("buyerMobile");
        String waterId =getPara("waterId");
        String oderTime = getPara("orderTime");
        String[] orderTime = ToolsUtils.rangeDate(oderTime);
        String status = getPara("status");
        Page<TItemPaymentOrder> page = TItemPaymentOrder.me.findPage(pageSort("id", "desc"),sellerMobile,buyerMobile,waterId,orderTime[0], orderTime[1],status);
        setAttr("page",page);
        keepPara("sellerMobile","buyerMobile","waterId","orderTime","status");
    }


}
