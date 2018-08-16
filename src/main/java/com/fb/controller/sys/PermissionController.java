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
import com.fb.model.rbac.TRbacFunction;
import com.fb.model.rbac.TRbacPermission;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 权限管理
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/sys/permission")
public class PermissionController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(PermissionController.class);
	
	private static final String PATH = "/sys/permission";
	
	/**
	 * 查询
	 */
	public void search() {
		setAttr("list", TRbacPermission.me.findRecursiveAll(JfinalUtils.getPlatform(getSession())));
		keepPara("keyword");
	}
	
	/**
	 * 添加
	 */
	public void add() {
		if (isGet()) {
			setAttr("pList", TRbacPermission.me.findByPid(0L, JfinalUtils.getPlatform(getSession())));//权限上级目录，相当于菜单
			setAttr("model", new TRbacPermission());
			setAttr("functions", TRbacFunction.me.findAll());
			render("_form.html");
		}
		if (isPost()) {
			try {
				TRbacPermission _model = getModel(TRbacPermission.class, "model");
				String[] functionIds = getParaValues("function_ids");
				Long default_function_id = getParaToLong("default_function_id");
				if(_model.getLong("pid")==0L){
					_model.set("level", 1);
				}else{
					Integer level = TRbacPermission.me.findLevel(_model.getLong("pid"));
					_model.set("level", level);
				}
				_model.set("platform", JfinalUtils.getPlatform(getSession()));
				_model.set("function_id", default_function_id);
				_model.set("add_time", DateUtils.getDate());
				_model.save();//保存模块信息
				
				/** 保存模块下分配的方法权限信息 **/
				if(functionIds!=null && functionIds.length>0){
					Long pid = _model.getLong("id");
					for(int i=0;i<functionIds.length;i++){
						_model.remove("name");
						_model.set("pid", pid);
						_model.remove("id");//删除上一次保存的id
						_model.set("level", 3);
						_model.set("function_id", functionIds[i]);
						_model.set("sort", i+1);
						_model.save();
					}
				}
				setMsg(Const.MsgType.SUCCESS, "10101", true);
			} catch (Exception e) {
				e.printStackTrace();
				setMsg(Const.MsgType.ERROR, "20101,90901", true);
			}
			redirect(PATH, "search");
		}
	}
	
	/**
	 * 编辑
	 * @author sun
	 * @date 2016年10月14日 下午3:06:12
	 */
	public void edit(){
		if (isGet()) {
			setAttr("pList", TRbacPermission.me.findByPid(0L, JfinalUtils.getPlatform(getSession())));//权限上级目录，相当于菜单
			TRbacPermission model = TRbacPermission.me.findById(getParaToLong("id"), JfinalUtils.getPlatform(getSession()));
			if(model==null){
				model = new TRbacPermission();
			}
			setAttr("model", model);
			setAttr("functions", TRbacFunction.me.findAll());
			render("_form.html");
		}
		if (isPost()) {
			TRbacPermission _model = getModel(TRbacPermission.class, "model");
			try {
				TRbacPermission model = checkAuth(_model.getLong("id"));
				if(model!=null){
					if(_model.getLong("pid")!=null){
						model.set("pid", _model.get("pid"));
					}
					if(model.getInt("level")!=3){
						Long default_function_id = getParaToLong("default_function_id");
						model.set("function_id", default_function_id);
					}
					model.set("params", _model.get("params"));
					model.set("is_show", _model.get("is_show"));
					model.set("target", _model.get("target"));
					model.set("is_default", _model.get("is_default"));
					model.set("icon", _model.get("icon"));
					if(StringUtils.isNotEmpty( _model.getStr("name"))){
						model.set("name", _model.get("name"));
					}
					model.set("controller_url", _model.get("controller_url"));
					model.set("sort", _model.get("sort"));
//					model.set("description", _model.get("description"));
					model.set("update_time", DateUtils.getDate());
					model.update();
					
					setMsg(Const.MsgType.SUCCESS, "10103", true);
				}else{
					setMsg(Const.MsgType.ERROR, "20103,30522", true);
				}
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
	private TRbacPermission checkAuth(Long id) {
		TRbacPermission model = TRbacPermission.me.findById(id, JfinalUtils.getPlatform(getSession()));
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
			TRbacPermission model = checkAuth(getParaToLong("id"));
			if (model != null) {
//				if(model.getLong("pid")==0L){
					/** 删除其下子节点 **/
					Db.update("delete from t_rbac_permission where pid = ?", model.getLong("id"));
//				}
				model.delete();
				setMsg(Const.MsgType.SUCCESS, "10102", true);
			}
		} catch (Exception e) {
			setMsg(Const.MsgType.SUCCESS, "20102", true);
		}
		redirect(PATH, "search");
	}
	
	/**
	 * 排序修改操作
	 */
	public void sort() {
		try {
			Map<String, String[]> params = getParaMap();
			String[] ids = params.get("id");
			String[] sorts = params.get("sort");
			int ind = 0;
			String platformId = JfinalUtils.getPlatform(getSession());
			for (String id : ids) {
				TRbacPermission rp = TRbacPermission.me.findById(Long.valueOf(id), platformId);
				if(rp!=null){
					rp.set("sort", sorts[ind]);
					rp.update();
					ind++;
				}
			}
			setMsg(Const.MsgType.SUCCESS, "10100", true);
		} catch (Exception e) {
			setMsg(Const.MsgType.ERROR, "20100,90901", true);
		}
		redirect(PATH, "search");
	}

}
