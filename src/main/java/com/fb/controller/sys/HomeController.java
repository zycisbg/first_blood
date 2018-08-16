	package com.fb.controller.sys;
//                         _ooOoo_  
//                        o8888888o  
//                        88" . "88  
//                        (| -_- |)  
//                         O\ = /O  
//                     ____/`---'\____  
//                   .   ' \\| |// `.  
//                    / \\||| : |||// \  
//                  / _||||| -:- |||||- \  
//                    | | \\\ - /// | |  
//                  | \_| ''\---/'' | |  
//                   \ .-\__ `-` ___/-. /  
//                ___`. .' /--.--\ `. . __  
//             ."" '< `.___\_<|>_/___.' >'"".  
//            | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//              \ \ `-. \_ __\ /__ _/ .-` / /  
//      ======`-.____`-.___\_____/___.-`____.-'======  
//                         `=---='  
//
//      .............................................  
//               佛祖保佑             永无BUG 
//       佛曰:  
//               写字楼里写字间，写字间里程序员；  
//               程序人员写程序，又拿程序换酒钱。  
//               酒醒只在网上坐，酒醉还来网下眠；  
//               酒醉酒醒日复日，网上网下年复年。  
//               但愿老死电脑间，不愿鞠躬老板前；  
//               奔驰宝马贵者趣，公交自行程序员。  
//               别人笑我忒疯癫，我笑自己命太贱；  
//               不见满街漂亮妹，哪个归得程序员？ 

    import com.fb.commons.JfinalUtils;
    import com.fb.controller.BaseController;
    import com.fb.core.Const;
    import com.fb.interceptor.AuthExclusion;
    import com.fb.kit.*;
    import com.fb.model.TDictionary;
    import com.fb.model.rbac.TRbacPermission;
    import com.fb.model.rbac.TRbacUser;
    import com.fb.model.rbac.TRbacUserConfig;
    import com.fb.util.ModelTools;
    import com.google.common.collect.Lists;
    import com.jfinal.aop.Before;
    import com.jfinal.ext.interceptor.NoUrlPara;
    import com.jfinal.ext.route.ControllerBind;
    import com.jfinal.kit.EncryptionKit;
    import com.jfinal.kit.PropKit;
    import com.jfinal.log.Logger;
    import com.jfinal.plugin.activerecord.Db;
    import com.jfinal.plugin.activerecord.Record;
    import org.apache.commons.lang.StringUtils;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpSession;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    /**
     * 系统后台登录退出 - 默认页
     * @author sun
     */
    @ControllerBind(controllerKey = "/")
    public class HomeController extends BaseController {

        protected static final Logger LOG = Logger.getLogger(HomeController.class);

        /**
         * 系统首页
         * @author sun
         * @date 2016年7月20日 上午9:21:06
         */
        @Before(NoUrlPara.class)
        @AuthExclusion
        public void index() {
            if (getSession().getAttribute(Const.SER_USER) == null) {
                redirect();
            } else {
                // 已登录, 正常跳转配置的默认首页
                redirect(PropKit.use("config.properties").get("application.welcome"));
            }
        }

        /**
         * 登录方法
         * @author sun
         * @date 2016年7月20日 上午9:21:12
         */
        @AuthExclusion
        public void login() {
            HttpSession session = getSession();
            if (JfinalUtils.isLogin(session)) {
                verify();
                redirect("/");
                return;
            }
            if (isGet()) {
                //打开登录页 get 请求
                verify();
                setAttr("login_background", JfinalUtils.getDefaultLoginTemplate(getSession()));
            } else if (isPost()) {
                String ip = IpUtils.getRealIp(getRequest());
                String username = getPara("username");
                String pwd = getPara("password");
                if(StringUtils.isEmpty(username) || StringUtils.isEmpty(pwd)){
                    session.setAttribute("msg", "用户名或密码不能为空!");
                    redirect("/");
                    return;
                }

                /** 校验验证码是否正确 **/
                if(JfinalUtils.getSwitch(CommonUtils.SettingGlobal.SYSTEM_LOGIN_VERIFYCODE_SWITCH, getSession()) && JfinalUtils.verifyGlobalWhiteList(ip, getSession())){
                    String enteredImagecode = getPara("entered_imagecode");
                    LOG.warn("用户输入校验码:"+enteredImagecode);
                    String rand=(String)session.getAttribute("rand");
                    LOG.warn("系统给的校验码:"+rand);
                    if(!StringUtils.isEmpty(enteredImagecode) && StringUtils.equalsIgnoreCase(enteredImagecode,rand)){
                        session.removeAttribute("rand");
                    }else{
                        session.setAttribute("msg", "验证码错误!");
                        redirect("/");
                        return;
                    }
                }

                String password = EncryptionKit.md5Encrypt(pwd);

                //**********验证多帐号*******************************************************************************

                TRbacUser user = TRbacUser.me.login(username);

                if (user == null) {
                    //管理员账户不存在
                    session.setAttribute("msg", "用户名或密码错误!");
                    redirect();
                    return ;
                }

                /** 当前用户所属平台 **/
                session.setAttribute(Const.PLATFORM_SESSION_ID, user.getInt("platform")+"");
                TDictionary dict = TDictionary.me.findPlatFormByVal(user.getInt("platform"));
                if(dict!=null){
                    session.setAttribute(Const.PLATFORM_SESSION_NAME, dict.getStr("label"));
                }

                /** 查询密码错误次数 **/
                //********************************************************************************************
                if(user.getInt("pwd_error_num")>0){
                    String maxNumStr = JfinalUtils.findBySGKey(CommonUtils.SettingGlobal.PASSWORD_ERROR_MAX_NUM, getSession());
                    String maxTimeStr = JfinalUtils.findBySGKey(CommonUtils.SettingGlobal.PASSWORD_ERROR_MAX_TIME, getSession());

                    Integer maxNum = 100;
                    try {
                        maxNum = Integer.valueOf(maxNumStr);
                    } catch (NumberFormatException e) {maxNum = 100;}

                    Date lastLoginTime = user.getDate("last_login_time");
                    Integer passwordErrorNum = user.getInt("pwd_error_num");

                    if(StringUtils.isNotEmpty(maxTimeStr)){

                        Long maxTime = null;
                        try {
                            maxTime = Long.valueOf(maxTimeStr);
                        } catch (NumberFormatException e) {}
                        long time = maxTime*60*60*1000;

                        //当前时间 - 最后一次登录时间 = 如果大约约定时间，则放行
                        if(DateUtils.getCurrentTimeMillis() - lastLoginTime.getTime()<time){
                            //已经锁定的状态
                            session.setAttribute("msg", "密码错误次数超限, 已锁定"+maxTime+"小时!");
                            redirect();
                            return ;
                        }
                    }else{
                        if(lastLoginTime!=null){
                            String lastTime = DateUtils.formatDate(lastLoginTime, DateUtils.DATE_FORMAT)+" 23:59:59";
                            lastLoginTime = DateUtils.parseDate(lastTime, DateUtils.DATETIME_FORMAT);
                            if(!new Date().after(lastLoginTime)){
                                //限制24小时， 第二天解锁
                                if(passwordErrorNum>=maxNum){
                                    //已经锁定的状态
                                    session.setAttribute("msg", "密码错误次数超限, 请次日再试!");
                                    redirect();
                                    return ;
                                }
                            }
                        }
                    }
                }
                //********************************************************************************************

                boolean flag = false;
                if(username.equals(user.getStr("username"))){
                    if(!password.equals(user.getStr("password"))){
                        //密码错误
                        flag = false;
                    }else{
                        flag = true;
                    }
                }

                if(!flag){
                    //密码错误
                    passwordError(user);//记录日志

                    /**封禁**/
                    // TODO

                    session.setAttribute("msg", "用户名或密码错误!");
                    redirect();
                    return ;
                }
                if ("1".equals(user.getStr("status"))) { // 用户被禁用
                    session.setAttribute("msg", "帐号被锁定, 请联系管理员!");
                    redirect();
                    return ;
                }

    //			/** 查询用户组 **/
                String ugroupIds = JfinalUtils.getSysUGroupIdsByUserId(user.getLong("id"));
    //			if(StringUtils.isEmpty(ugroupIds)){
    //				session.setAttribute("msg", "该管理员所属用户组数据异常!");
    //				redirect();
    //				return ;
    //			}
    //			user.put("ugroup_ids", ugroupIds);
    //			user.put("ugroup_pid", JfinalUtils.getSysParentUGroupIdsByUserId(ugroupIds));

                String ids = JfinalUtils.getSysRoleIdsBy(user.getLong("id"), ugroupIds);

                if(StringUtils.isEmpty(ids)){
                    session.setAttribute("msg", "此帐号还没有分配权限, 请联系管理员.");
                    redirect();
                    return ;
                }

                user.set("last_login_time", DateUtils.getDate());
                user.set("last_login_ip", ip);
                user.set("pwd_error_num", 0);//失败次数清0
                user.update();

                /**
                 * 记录在线用户
                 * 统计在线人数
                 */
                String date = DateUtils.formatDate(DateUtils.getDate(), DateUtils.DATETIME_FORMAT);
                JfinalUtils.recordOnlineUsers(user, date, ip, getSession());//记录在线用户

                /** 查询菜单 **/
    //			session.setAttribute(Const.SER_MENUS, VRbacRoleMenu.me.findAll(ids));

                /** 新版查询权限 **/

                Map<String, TRbacPermission> allAuth = new HashMap<String, TRbacPermission>();
                Map<Long, TRbacPermission> longAuth = new HashMap<Long, TRbacPermission>();
                List<TRbacPermission> moduleMap = Lists.newArrayList();
                Map<String, Integer> levelMap = new HashMap<String, Integer>();
                Map<String, TRbacPermission> paramsMap = new HashMap<String, TRbacPermission>();

                List<TRbacPermission> list = TRbacPermission.me.findByRoleIdAndPid(ids, 0L);//一级导航，顶部显示的
                Map<Object, List<TRbacPermission>> map = new HashMap<Object, List<TRbacPermission>>();
                if(list!=null && list.size()>0){
                    for(TRbacPermission rp:list){
                        /** 顶部导航栏，一级导航 **/
                        String url = getUrl(rp, paramsMap);
                        longAuth.put(rp.getLong("id"), rp);
                        allAuth.put(url, rp);
                        moduleMap.add(rp);
                        levelMap.put(url, rp.getInt("level"));
                        List<TRbacPermission> chileList = TRbacPermission.me.findByRoleIdAndPid(ids, rp.getLong("id"));//二级导航，左侧栏显示的
                        if(chileList!=null && chileList.size()>0){
                            map.put("controller_"+rp.getLong("id"), chileList);
                            for(TRbacPermission rp2:chileList){
                                /** 遍历二级导航 **/
                                url = getUrl(rp2, paramsMap);
                                levelMap.put(url, rp2.getInt("level"));
                                longAuth.put(rp2.getLong("id"), rp2);
                                allAuth.put(url, rp2);
                                List<TRbacPermission> sunList = TRbacPermission.me.findByRoleIdAndPid(ids, rp2.getLong("id"));//方法级权限
                                if(sunList!=null && sunList.size()>0){
                                    map.put("method_"+rp2.getLong("id"), sunList);
                                    for(TRbacPermission rp3:sunList){
                                        /** 遍历三级权限， 方法级别的 **/
                                        url = getUrl(rp3, paramsMap);
                                        levelMap.put(url, rp3.getInt("level"));
                                        longAuth.put(rp3.getLong("id"), rp3);
                                        allAuth.put(url, rp3);
                                    }
                                }
                            }
                        }
                    }
                }
                session.setAttribute(Const.RoleAuth.ALL_ROLEID, ids);			//add by sunyiqing 将用户的角色放入缓存中
                session.setAttribute(Const.RoleAuth.MODULE_PERMISSION, moduleMap);
                session.setAttribute(Const.RoleAuth.AUTH_PERMISSION, map);
                session.setAttribute(Const.RoleAuth.ALL1_PERMISSION, allAuth);
                session.setAttribute(Const.RoleAuth.ALL2_PERMISSION, longAuth);
                session.setAttribute(Const.RoleAuth.LEVEL_PERMISSION, levelMap);
                session.setAttribute(Const.RoleAuth.PARAMS_PERMISSION, paramsMap);

                /*//权限数据放入Session
                Map<String, String> permissions = Maps.newHashMap();
                for (String str:ids.split(",")) {
                    if(StringUtils.isNotEmpty(str)){
                        Long roleId = Long.valueOf(str);
                        List<VRbacRolePermission> rolePermissions = VRbacRolePermission.me.findPermissions(roleId);
                        for (VRbacRolePermission rolePermission : rolePermissions) {
                            permissions.put(rolePermission.getStr("controller_key") + rolePermission.getStr("method_key"), rolePermission.getStr("function_name") + "_#_" + rolePermission.getStr("module_name"));
                        }
                    }
                }
                session.setAttribute(Const.SER_PERMISSIONS, permissions);*/
                //用户信息放入session
                session.setAttribute(Const.SER_USER, user);
                //用户配置放入session
                TRbacUserConfig userConfig = JfinalUtils.findUserConfig(user.getLong("id"), getSession());
                session.setAttribute(CommonUtils.SettingGlobal.SYSTEM_SETTING_COLOR, userConfig);
                JfinalUtils.writeLog(this);

                // 跳转至首页
                redirect("/");
            } else { // 杜绝其他方式的请求
                renderError(404);
            }
        }

        private String getUrl(TRbacPermission rp, Map<String, TRbacPermission> paramsMap) {
            String url = rp.getStr("controller_url")+rp.getStr("method_key")+Const.MP_SUFFIX/*+(StringUtils.isNotEmpty(rp.getStr("params"))?rp.getStr("params"):"")*/;
            if(StringUtils.isNotEmpty(rp.getStr("params"))){
                /** URL带参数的 **/
                String params = StringUtils.isNotEmpty(rp.getStr("params"))?rp.getStr("params"):"";
                paramsMap.put(url+params, rp);
            }
            rp.put("action_key", url);
            return url;
        }

        /**
         * 退出登录
         * @author sun
         * @date 2016年7月20日 上午10:03:03
         */
        @AuthExclusion
        public void logout() {
            // 销毁Session
            getSession().invalidate();
            // 跳转至登录页
            redirect();
        }

        private void verify(){
            int result = 0;
            if(JfinalUtils.getSwitch(CommonUtils.SettingGlobal.SYSTEM_LOGIN_VERIFYCODE_SWITCH, getSession())){
                result = JfinalUtils.verifyGlobalWhiteList(IpUtils.getRealIp(getRequest()), getSession())==true?1:0;
            }
            String passwordSmsVerification = "0";
            Record first = Db.findFirst("select * from config where id = 'password_sms_verification_sys'");
            if(first!=null){
                passwordSmsVerification = first.get("value");
            }
            setAttr("password_sms_verification_sys",passwordSmsVerification);
            setAttr("isRand", result);
        }

        /**
         * 密码错误， 记录日志
         * @author sun
         * @date 2016年9月19日 下午1:15:02
         */
        private void passwordError(TRbacUser user){
            /** 记录失败次数 **/
            user.set("pwd_error_num", user.getInt("pwd_error_num")+1).update();

            /** 记录日志 **/
            JfinalUtils.logToTask("/login", "POST", "/login", "密码错误", getRequest().getHeader("user-agent"), IpUtils.getRealIp(getRequest()), ToolsUtils.iteratorParamsTo(getRequest().getParameterMap()), 0, getSession(), getRequest());
        }

        /**
         * redirect到登录页
         * @author sun
         * @date 2016年7月20日 上午10:03:51
         */
        private void redirect() {
            verify();
            redirect("/login" + getAttr("suffix"));
        }


    }
