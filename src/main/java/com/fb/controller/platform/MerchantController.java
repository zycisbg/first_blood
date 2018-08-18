package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.core.Const;
import com.fb.kit.ToolsUtils;
import com.fb.model.TProduct;
import com.fb.model.TXcxUser;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;


@ControllerBind(controllerKey = "/platform/merchant")

public class MerchantController extends BaseController {
    protected static final com.jfinal.log.Logger LOG = com.jfinal.log.Logger.getLogger(MerchantController.class);

    public void welcome(){

    }

    public void search(){
        String mobileNumber = getPara("mobileNumber");
        String lastLoginTime = getPara("lastLoginTime");
        String nickName = getPara("nickName");
        String realname = getPara("realname");
        String address = getPara("address");
        String[] lastLoginTimeArr = ToolsUtils.rangeDate(lastLoginTime);
        Page<TXcxUser> page = TXcxUser.me.findPage(pageSort("register_time", "desc"),realname,nickName,mobileNumber,lastLoginTimeArr[0],lastLoginTimeArr[1],"2",address,null);
        setAttr("page",page);
        keepPara("mobileNumber","lastLoginTime","nickName","realname","address");
    }

    public void view(){
        setAttr("model",TXcxUser.me.findById(getPara("id")));
    }

    public void remove(){
        String id = getPara("id");
        String status = getPara("status");
        TXcxUser tXcxUser = new TXcxUser();
        tXcxUser.set("id",id);
        tXcxUser.set("status",status);
        tXcxUser.update();
        if("2".equals(status)){
            setMsg(Const.MsgType.SUCCESS, "该商户禁用成功");
        }else{
            setMsg(Const.MsgType.SUCCESS, "该商户启用成功");

        }
        redirect(getAttr("ctx") + "/platform/merchant" + "/" + "search" + getAttr("suffix"));
    }


}
