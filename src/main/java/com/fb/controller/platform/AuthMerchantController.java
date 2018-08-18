package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.core.Const;
import com.fb.kit.ToolsUtils;
import com.fb.model.TProduct;
import com.fb.model.TXcxUser;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;


@ControllerBind(controllerKey = "/platform/authMerchant")

public class AuthMerchantController extends BaseController {
    protected static final com.jfinal.log.Logger LOG = com.jfinal.log.Logger.getLogger(AuthMerchantController.class);

    public void welcome(){

    }

    public void search(){
        String mobileNumber = getPara("mobileNumber");
        String lastLoginTime = getPara("lastLoginTime");
        String nickName = getPara("nickName");
        String realname = getPara("realname");
        String address = getPara("address");
        String[] lastLoginTimeArr = ToolsUtils.rangeDate(lastLoginTime);
        Page<TXcxUser> page = TXcxUser.me.findPage(pageSort("register_time", "desc"),realname,nickName,mobileNumber,lastLoginTimeArr[0],lastLoginTimeArr[1],"2",address,"0");
        setAttr("page",page);
        keepPara("mobileNumber","lastLoginTime","nickName","realname","address");
    }

    public void view(){
        setAttr("model",TXcxUser.me.findById(getPara("id")));
    }

    public void edit(){
        String id = getPara("id");
        String remark = getPara("remark");
        String status = getPara("status");
        TXcxUser tXcxUser = new TXcxUser();
        tXcxUser.set("id",id);
        tXcxUser.set("is_auth",status);
        tXcxUser.set("auth_time",new Date());
        tXcxUser.set("auth_remark",remark);
        tXcxUser.update();
        if("1".equals(status)){
            setMsg(Const.MsgType.SUCCESS, "该商户认证已通过");
        }else{
            setMsg(Const.MsgType.ERROR, "该商户认证已拒绝");

        }
        redirect(getAttr("ctx") + "/platform/authMerchant" + "/" + "search" + getAttr("suffix"));
    }


}
