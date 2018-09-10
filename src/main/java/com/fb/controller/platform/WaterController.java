package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.kit.ToolsUtils;
import com.fb.model.TItemPaymentWater;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey = "/platform/water")

public class WaterController extends BaseController {
    protected static final com.jfinal.log.Logger LOG = com.jfinal.log.Logger.getLogger(WaterController.class);

    public void search(){
        String mobile = getPara("mobile");
        String oderTime = getPara("orderTime");
        String[] orderTime = ToolsUtils.rangeDate(oderTime);
        String status = getPara("status");
        Page<TItemPaymentWater> page = TItemPaymentWater.me.findPage(pageSort("id", "desc"), mobile, orderTime[0], orderTime[1],status);
        setAttr("page",page);
        keepPara("mobile","orderTime","status");
    }


}
