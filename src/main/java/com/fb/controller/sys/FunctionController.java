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


import com.fb.controller.BaseController;
import com.fb.core.Const;
import com.fb.kit.DateUtils;
import com.fb.kit.ToolsUtils;
import com.fb.model.rbac.TRbacFunction;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;

/**
 * 权限－方法
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/sys/function")
public class FunctionController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(FunctionController.class);
	
	private static final String PATH = "/sys/function";
	
	/**
	 * 分页查询
	 */
	public void search() {
		String keyword = ToolsUtils.trim(getPara("keyword"));
		setAttr("page", TRbacFunction.me.findAll(getParaToInt("pageNumber", Const.PAGE_NUMBER), getParaToInt("pageSize", Const.PAGE_SIZE), keyword));
		keepPara("keyword");
	}
	
	/**
	 * 添加
	 */
	public void add() {
		if (isGet()) {
			setAttr("model", new TRbacFunction());
			render("_form.html");
		}
		if (isPost()) {
			try {
				TRbacFunction _model = getModel(TRbacFunction.class, "model");
				_model.save();
				setMsg(Const.MsgType.SUCCESS, "10101", true);
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
			setAttr("model", TRbacFunction.me.findById(getParaToLong("id")));
			render("_form.html");
		}
		
		if (isPost()) {
			TRbacFunction _model = getModel(TRbacFunction.class, "model");
			
			try {
				TRbacFunction model = TRbacFunction.me.findById(_model.getLong("id"));
				model.set("name", _model.get("name"));
				model.set("method_key", _model.get("method_key"));
				model.set("remark", _model.get("remark"));
				model.set("icon", _model.get("icon"));
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
	 * 删除
	 */
	public void remove() {
		try {
			TRbacFunction.me.deleteById(getParaToLong("id"));
			setMsg(Const.MsgType.SUCCESS, "10102", true);
		} catch (Exception e) {
			setMsg(Const.MsgType.ERROR, "20102,90901", true);
		}
		redirect(PATH, "search");
	}

}
