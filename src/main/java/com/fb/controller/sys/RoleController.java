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
import com.fb.kit.DateUtils;
import com.fb.kit.JsonUtils;
import com.fb.kit.SetOperationUtils;
import com.fb.kit.ToolsUtils;
import com.fb.model.rbac.TRbacPermission;
import com.fb.model.rbac.TRbacRole;
import com.fb.model.rbac.TRbacRolePermissionRef;
import com.fb.pojos.Ztree;
import com.google.common.collect.Lists;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限－角色
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/sys/role")
public class RoleController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(RoleController.class);
	
	private static final String PATH = "/sys/role";
	
	/**
	 * 查询
	 */
	public void search() {
		String keyword = ToolsUtils.trim(getPara("keyword"));
		setAttr("page", TRbacRole.me.findAll(pageSort("js.id", "desc"), keyword));
		keepPara("keyword");
	}
	
	/**
	 * 添加
	 */
	public void add() {
		if (isGet()) {
			setAttr("model", new TRbacRole());
			render("_form.html");
		}
		
		if (isPost()) {
			try {
				TRbacRole _model = getModel(TRbacRole.class, "model");
				TRbacRole role = TRbacRole.me.findByName(_model.getStr("name"));
				if(role!=null){
					setMsg(Const.MsgType.ERROR, "20101,30532", true);
				}else{
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
		if (isGet()) {
			TRbacRole ru = checkAuth(getParaToLong("id"));
			if(ru==null){
				ru = new TRbacRole();
			}
			setAttr("model", ru);
			render("_form.html");
		}
		
		if (isPost()) {
			TRbacRole _model = getModel(TRbacRole.class, "model");
			try {
				TRbacRole model = checkAuth(_model.getLong("id"));
				if(model==null){
					return ;
				}
				model.set("name", _model.get("name"));
				model.set("remark", _model.get("remark"));
				model.set("status", _model.get("status"));
				model.set("update_time", DateUtils.getDate());
				model.update();
				setMsg(Const.MsgType.SUCCESS, "10103", true);
			} catch (Exception e) {
				setMsg(Const.MsgType.ERROR, "20103,90901", true);
			}
			redirect(PATH, "search");
		}
	}

	/**
	 * 用户是否有操作该数据的权限 判断。
	 * @author sun
	 * @date 2016年10月13日 下午3:26:51
	 * @param id
	 * @return
	 */
	private TRbacRole checkAuth(Long id) {
		TRbacRole model = null;
		if(JfinalUtils.getSysIsRoot(getSession())){
			model = TRbacRole.me.findById(id);
		}else{
			model = TRbacRole.me.findBy(id, JfinalUtils.getPlatform(getSession()));
		}
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
			TRbacRole model = checkAuth(getParaToLong("id"));
			if(model!=null){
				model.delete();
				setMsg(Const.MsgType.SUCCESS, "10102", true);
			}
		} catch (Exception e) {
			setMsg(Const.MsgType.ERROR, "20102,90901", true);
		}
		redirect(PATH, "search");
	}
	
	/**
	 * 权限管理
	 */
	public void permission() {
		
		Long roleId = getParaToLong("id");
		TRbacRole model = checkAuth(roleId);
		if(model==null){
			setMsg(Const.MsgType.ERROR, "30522", true);
			redirect(PATH, "search");
		}
		if(model==null){
			return ;
		}
		/** 查询树形结构权限 **/
		List<String> dbList = TRbacRolePermissionRef.me.findBy(roleId, JfinalUtils.getPlatform(getSession()));
		
		if (isGet()) {
			
			List<TRbacPermission> list = TRbacPermission.me.findRecursiveAll(JfinalUtils.getPlatform(getSession()));
			List<Ztree> mList = Lists.newArrayList();
			if(list!=null && list.size()>0){
				
				Ztree z = null;
				for(int i=0;i<list.size();i++){
					TRbacPermission rp = list.get(i);
					z = new Ztree();
					z.setId(rp.getLong("id"));
					z.setpId(rp.getLong("pid"));
					/** ICON遍历 **/
					if(rp.getInt("level")<=2){
						z.setIcon(rp.getStr("icon"));
					}else{
						z.setIcon(rp.getStr("function_icon"));
					}
					if(rp.getInt("level")>2){
						if(StringUtils.isNotEmpty(rp.getStr("name"))){
							z.setName(rp.getStr("name"));
						}else{
							z.setName(rp.getStr("function_name"));
						}
					}else{
						z.setName(rp.getStr("name"));
					}
					/** 是否已勾选 **/
					if(dbList.contains(rp.getLong("id").toString())){
						z.setChecked(true);
					}
					/** 是否为默认必须选择的权限 **/
					if(rp.getInt("is_default")==1){
						z.setChecked(true);
						z.setChkDisabled(true);
					}
					z.setFont("{'color':'red'}");
					mList.add(z);
				}
			}
			setAttr("permissions", JsonUtils.obj2Json2(mList));
			setAttr("model", model);
		}
		
		if (isPost()) {
			String defaultIds = TRbacPermission.me.getIsDefaultPermission(JfinalUtils.getPlatform(getSession()));
			String newPermissionIds = getPara("permissionIds")+((StringUtils.isNotEmpty(",") && !StringUtils.isNotEmpty(defaultIds))?",":"")+defaultIds;
			if(StringUtils.isNotEmpty(newPermissionIds)){
				
				List<String> vList = Arrays.asList(newPermissionIds.split(","));//前端传值过来的
				
				
				Set<String> qucList = new HashSet<String>();
				qucList.addAll(vList);
				
				Object[] deleteList = SetOperationUtils.substract(dbList.toArray(), vList.toArray());//需要在数据库删除的
				if(deleteList!=null && deleteList.length>0){
					for(Object obj:deleteList){
						TRbacRolePermissionRef.me.deleteBy(obj,  roleId);
					}
				}
				
				Object[] addList = SetOperationUtils.substract(vList.toArray(), dbList.toArray());//需要向数据库新增的
				if(addList!=null && addList.length>0){
					TRbacRolePermissionRef rp = null;
					for(Object obj:addList){
						rp = new TRbacRolePermissionRef();
						rp.set("permission_id", obj);
						rp.set("role_id", roleId);
						rp.save();
					}
				}
			}else{
				/** 全部删除 **/
				TRbacRolePermissionRef.me.removePermissionsByRole(getParaToLong("id"));
			}
			setMsg(Const.MsgType.SUCCESS, "10100", true);
			redirect(getAttr("ctx") + PATH + "/search" + getAttr("suffix"));
		}
	}

}
