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
import com.fb.kit.CommonUtils;
import com.fb.kit.DateUtils;
import com.fb.kit.SetOperationUtils;
import com.fb.kit.ToolsUtils;
import com.fb.model.rbac.*;
import com.google.common.collect.Lists;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.EncryptionKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理员
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/sys/user")
public class UserController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(UserController.class);
	
	private static final String PATH = "/sys/user";
	
	/**
	 * 查询用户列表
	 * @author sun
	 * @date 2016年10月13日 下午3:58:33
	 */
	public void search() {
		String keyword = ToolsUtils.trim(getPara("keyword"));
		Long roleId = getParaToLong("roleId");
		String status = getPara("status");


		Page<TRbacUser> page = TRbacUser.me.findPage(pageSort("add_time", "desc"), keyword, roleId,status);
		List<TRbacUser> list = page.getList();
		if(list!=null && list.size()>0){
			
			/** 查询用户密码错误次数太多，被封禁的状态 **/
			String maxNumStr = JfinalUtils.findBySGKey(CommonUtils.SettingGlobal.PASSWORD_ERROR_MAX_NUM, getSession());
			Integer maxNum = null;
			try {
				maxNum = Integer.valueOf(maxNumStr);
			} catch (NumberFormatException e) {maxNum=5;}
			for(TRbacUser vu:list){
				vu.put("is_lock", 0);
				if(vu.getInt("pwd_error_num")!=0 && vu.getInt("pwd_error_num")>=maxNum){
					/** 被封禁的状态 **/
					vu.put("is_lock", 1);
				}
			}
			/** 校验用户是否具有角色权限 **/
			for(TRbacUser vu:list){
				String roleIds = JfinalUtils.getSysRoleIdsBy(vu.getLong("id"), JfinalUtils.getSysUGroupIdsByUserId(vu.getLong("id")));
				
				/** 装载用户组的角色 **/
				Set<String> ids = new HashSet<String>();
				if(StringUtils.isNotEmpty(vu.getStr("role_ids"))){
					ids.addAll(Arrays.asList(vu.getStr("role_ids").split(",")));
				}
				if(StringUtils.isNotEmpty(roleIds)){
					ids.addAll(Arrays.asList(roleIds.split(",")));
				}
				
				if(ids!=null && ids.size()>0){
					String rids = ToolsUtils.join(",", ids.toArray());
					/** 用户自己被分配的角色和用户组所拥有的角色集合 **/
					vu.put("role_ids", rids);
					/** 查询这些角色对应的角色名称 **/
					String names = TRbacRole.me.findBy(rids);
					vu.put("role_name", names);
				}
			}
			for(TRbacUser vu:list){
				if(StringUtils.isEmpty(vu.getStr("role_ids"))){
					//未被分配角色的弹窗提醒
					setGritter(CommonUtils.Gritter.GRITTER_TYPE_ERROR, vu.getStr("username")+" 未分配角色", "<img src=\"/assets/images/emoji/Expression_16@2x.png\" style=\"width:20px; height:20px;\"> 您还没有给我分配对应的角色吆！", vu.getStr("avatar"), 3000, false);
					break;
				}
			}
		}
		setAttr("password_error_max_time",StringUtils.isEmpty(JfinalUtils.findBySGKey(CommonUtils.SettingGlobal.PASSWORD_ERROR_MAX_TIME, getSession()))?"24":JfinalUtils.findBySGKey(CommonUtils.SettingGlobal.PASSWORD_ERROR_MAX_TIME, getSession()));

		//List<TRbacRole> rList = TRbacRole.me.findByPlatform(JfinalUtils.getPlatform(getSession()));
		List<TRbacRole> rList = TRbacRole.me.findByPlatform(null);
		setAttr("roleList", rList);
		setAttr("page", page);
		keepPara("keyword");
		keepPara("roleId");
		keepPara("status");
	}
	
	/**
	 * 添加新用户
	 * @author sun
	 * @date 2016年10月13日 下午4:00:42
	 */
	public void add() {
		if (isGet()) {
			setAttr("model", new TRbacUser());
			render("_form.html");
		}
		if (isPost()) {
			try {

				TRbacUser _model = getModel(TRbacUser.class, "model");
				if(TRbacUser.me.login(_model.getStr("username"))!=null){
					/** 用户名存在 **/
					setMsg(Const.MsgType.ERROR, "20101,30531", true);
				}else{
					_model.set("password", EncryptionKit.md5Encrypt(_model.getStr("password")));
					_model.set("platform", JfinalUtils.getPlatform(getSession()));
					_model.save();

					setMsg(Const.MsgType.SUCCESS, "10101", true);
				}
			} catch (Exception e) {
				setMsg(Const.MsgType.ERROR, "20101,90901", true);
			}
			redirect(PATH, "search");
		}
	}
	
	/**
	 * 编辑
	 */
	public void edit() {
		String key = getPara("key");
		if(StringUtils.isNotEmpty(key) && "unlock".equals(key)){
			/** AJAX的请求, 解锁密码错误次数超限导致的短时间封禁 **/
			TRbacUser model = TRbacUser.me.findById(getParaToLong("id"));
			model.set("pwd_error_num", 0).set("update_time", DateUtils.getDate()).update();
			renderJson(1);
		}else{
			if (isGet()) {
				Long id = getParaToLong("id");
				TRbacUser user = checkAuth(id);

				setAttr("model", user);
				render("_form.html");
			}
			if (isPost()) {
				TRbacUser _model = getModel(TRbacUser.class, "model");
				TRbacUser model = checkAuth(_model.getLong("id"));
				if(model==null){
					return ;
				}


				try {

					model.set("username", _model.get("username"));
					model.set("realname", _model.get("realname"));
					if(!StringUtils.isEmpty(_model.getStr("password"))){
						model.set("password", EncryptionKit.md5Encrypt(_model.getStr("password")));
					}
					model.set("is_root", _model.get("is_root"));
					model.set("email", _model.get("email"));
					model.set("tel", _model.get("tel"));
					model.set("status", _model.get("status"));
					model.set("avatar",_model.get("avatar"));
					model.set("update_time", DateUtils.getDate());
					model.update();
					
					setMsg(Const.MsgType.SUCCESS, "10103", true);
				} catch (Exception e) {
					setMsg(Const.MsgType.ERROR, "20103,90901", true);
				}
				redirect(PATH, "search");
			}
		}
	}

	/**
	 * 用户是否有操作该数据的权限 判断。
	 * @author sun
	 * @date 2016年10月13日 下午5:35:08
	 * @param id
	 * @return
	 */
	private TRbacUser checkAuth(Long id) {
		TRbacUser model = TRbacUser.me.findByIdAndPlatForm(id, JfinalUtils.getPlatformCheckRoot(getSession()));
		if(model==null){
			setMsg(Const.MsgType.ERROR, "30522", true);
			redirect(PATH, "search");
		}
		return model;
	}
	
	/**
	 * 删除
	 */
	public void remove() {
		try {
			TRbacUser model = checkAuth(getParaToLong("id"));
			if(model!=null){
				if("1".equals(model.getStr("is_root"))){
					/** 超级管理员，不允许删除，只允许禁用 **/
					setMsg(Const.MsgType.ERROR, "30551", true);
				}else{
					if(JfinalUtils.getSysId(getSession())==model.getLong("id")){
						/** 不能删除自己 **/
						setMsg(Const.MsgType.ERROR, "30580", true);
					}else{
						model.delete();
						setMsg(Const.MsgType.SUCCESS, "10102", true);
					}
				}
			}else{
				setMsg(Const.MsgType.ERROR, "30551", true);
			}
		} catch (Exception e) {
			setMsg(Const.MsgType.ERROR, "20100,90901", true);
		}
		redirect(PATH, "search");
	}
	
	/**
	 * 角色分配
	 */
	public void role() {
		if (isGet()) {
			TRbacUser user = checkAuth(getParaToLong("id"));
			if(user==null){
				redirect(PATH, "search");
				return ;
			}
			setAttr("roles", TRbacRole.me.findAll(JfinalUtils.getPlatformCheckRoot(getSession())));
			setAttr("roleIds", TRbacUserRoleRef.me.searchRoleIds(getParaToLong("id")));
			setAttr("model", user);
		}
		if (isPost()) {
			TRbacUser user = TRbacUser.me.findByIdAndPlatForm(getParaToLong("id"), JfinalUtils.getPlatformCheckRoot(getSession()));
			if(user==null){
				redirect(PATH, "search");
				return ;
			}
			String roleIds = getPara("roleIds");
			if(!StringUtils.isEmpty(roleIds)){
				
				List<String> vList = Arrays.asList(roleIds.split(","));//前端传值过来的新数据
				String dbStr = TRbacUserRoleRef.me.searchRoleIds(user.getLong("id"));//数据库已存的老数据
				List<String> dbList = Lists.newArrayList();
				if(StringUtils.isNotEmpty(dbStr)){
					dbList = Arrays.asList(dbStr.split(","));//数据库已存的老数据
				}
				
				Object[] deleteList = SetOperationUtils.substract(dbList.toArray(), vList.toArray());//需要在数据库删除的
				if(deleteList!=null && deleteList.length>0){
					for(Object obj:deleteList){
						TRbacUserRoleRef.me.delete(user.getLong("id"), obj);
					}
				}
				
				Object[] addList = SetOperationUtils.substract(vList.toArray(), dbList.toArray());//需要向数据库新增的
				if(addList!=null && addList.length>0){
					TRbacUserRoleRef urr = null;
					for(int i=0;i<addList.length;i++){
						urr = new TRbacUserRoleRef();
						urr.set("user_id", getParaToLong("id"));
						urr.set("role_id", addList[i]);
						urr.save();
					}
				}
				setMsg(Const.MsgType.SUCCESS, "10100", true);
			}else{
				TRbacUserRoleRef.me.removeRoleByUser(getParaToLong("id"));
			}
			redirect(PATH, "search");
		}
	}

}
