package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.core.Const;
import com.fb.kit.ToolsUtils;
import com.fb.model.TProduct;
import com.fb.model.TXcxUser;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;


@ControllerBind(controllerKey = "/platform/buyer")

public class BuyerController extends BaseController {
    protected static final com.jfinal.log.Logger LOG = com.jfinal.log.Logger.getLogger(BuyerController.class);

    public void welcome(){

    }

    public void search(){
        String mobileNumber = getPara("mobileNumber");
        String lastLoginTime = getPara("lastLoginTime");
        String nickName = getPara("nickName");
        String realname = getPara("realname");
        String[] lastLoginTimeArr = ToolsUtils.rangeDate(lastLoginTime);
        Page<TXcxUser> page = TXcxUser.me.findPage(pageSort("register_time", "desc"),realname,nickName,mobileNumber,lastLoginTimeArr[0],lastLoginTimeArr[1],"1",null,null);
        setAttr("page",page);
        keepPara("mobileNumber","lastLoginTime","nickName","realname");
    }

    public void view(){
        setAttr("model",TProduct.me.findById(getPara("id")));
    }


}
