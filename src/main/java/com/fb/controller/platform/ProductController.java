package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.core.Const;
import com.fb.kit.ToolsUtils;
import com.fb.model.TProduct;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;


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

    public void view(){
        setAttr("model",TProduct.me.findById(getPara("id")));
    }

    public void own(){
        String id = getPara("id");
        String status = getPara("status");
        String remark = getPara("remark");
        TProduct tProduct = new TProduct();
        tProduct.set("id",id);
        tProduct.set("update_time",new Date());
        if("pass".equals(status)){
            tProduct.set("status","2");
            tProduct.set("list_time",new Date());
            setMsg(Const.MsgType.SUCCESS, "审核成功,该商品已上架");
        }else{
            tProduct.set("status","4");
            tProduct.set("remark",remark);
            setMsg(Const.MsgType.SUCCESS, "审核拒绝,该商品已拒绝上架");
        }
        tProduct.update();
        redirect(getAttr("ctx") + "/platform/product" + "/" + "search" + getAttr("suffix") + "?method=3");
    }
}
