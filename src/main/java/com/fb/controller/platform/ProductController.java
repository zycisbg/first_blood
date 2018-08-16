package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.kit.ToolsUtils;
import com.fb.model.TProduct;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;


@ControllerBind(controllerKey = "/platform/product")

public class ProductController extends BaseController {
    protected static final com.jfinal.log.Logger LOG = com.jfinal.log.Logger.getLogger(ProductController.class);

    public void welcome(){

    }

    public void search(){
        String method = getPara("method");
        if("all".equals(method)){
            method="";
        }
        String listTime = getPara("listTime");
        String name = getPara("name");
        String username = getPara("username");
        String[] listTimeArr = ToolsUtils.rangeDate(listTime);
        Page<TProduct> page = TProduct.me.findPage(pageSort("update_time", "desc"), method, name, username, listTimeArr[0], listTimeArr[1]);
        setAttr("page",page);
        keepPara("method","listTime","name","username");
    }
}
