package com.fb.controller.platform;

import com.fb.controller.BaseController;
import com.fb.kit.ToolsUtils;
import com.fb.model.TProduct;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;


@ControllerBind(controllerKey = "/platform")

public class PlatformController extends BaseController {
    protected static final com.jfinal.log.Logger LOG = com.jfinal.log.Logger.getLogger(PlatformController.class);

    public void welcome(){

    }


}
