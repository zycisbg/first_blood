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
import com.fb.kit.CommonUtils;
import com.fb.kit.DateUtils;
import com.fb.kit.ToolsUtils;
import com.fb.model.TDictionary;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;

/**
 * 数据字典
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/sys/dictionary")
public class DictionaryController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(DictionaryController.class);
	
	private static final String PATH = "/sys/dictionary";
	
	/**
	 * 分页查询所有的数据字典
	 */
	public void search() {
		String keyword = ToolsUtils.trim(getPara("keyword"));
		String chatCode = getPara("chatCode");
		setAttr("page", TDictionary.me.findPage(pageSort("chat_code,sort", "asc,asc"), keyword, chatCode));
		searchConfig();
		keepPara("chatCode");
		keepPara("keyword");
	}

	/**
	 * 查询所有的字典类型， 字典config
	 */
	public void searchConfig() {
		//查询所有的字典类型， 字典config
		setAttr("config", TDictionary.me.findTypeAll());
	}
	
	/**
	 * 添加
	 */
	public void add() {
		if (isGet()) {
			setAttr("model", new TDictionary());
			searchConfig();
			render("_form.html");
		}
		if (isPost()) {
			try {
				TDictionary _model = getModel(TDictionary.class, "model");
				String selectType = getPara("selectChatCode");
				if(!StringUtils.isEmpty(selectType)){
					if("0".equals(selectType)){
						_model.set("chat_code", getPara("inputChatCode"));
					}else{
						_model.set("chat_code", selectType);
					}
					_model.set("type", 0);//0 是默认
					_model.set("platform", CommonUtils.DICTIONARY_PLATFORM_SYSTEM);
					_model.set("add_time", DateUtils.getDate());
					_model.save();
					setMsg(Const.MsgType.SUCCESS, "10101", true);
				}else{
					setMsg(Const.MsgType.SUCCESS, "20101,30501", true);
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
			setAttr("model", TDictionary.me.findById(getParaToLong("id")));
			searchConfig();
			render("_form.html");
		}
		if (isPost()) {
			TDictionary _model = getModel(TDictionary.class, "model");
			try {
				TDictionary model = TDictionary.me.findById(_model.getLong("id"));
//				model.set("chat_code", _model.get("chat_code"));//此项不允许更改
				model.set("label", _model.get("label"));
				model.set("val", _model.get("val"));
				model.set("sort", _model.get("sort"));
				model.set("color", _model.get("color"));
				model.set("description", _model.get("description"));
				model.set("stu", _model.get("stu"));
				model.set("update_time", DateUtils.getDate());
				model.update();
				
				setMsg(Const.MsgType.SUCCESS, "10103", true);
			} catch (Exception e) {
				setMsg(Const.MsgType.ERROR, "20103,90901", true);
			}
			redirect(PATH, "search");
		}
	}
	
	//不开发删除功能
	public void remove() {
		try {
			TDictionary dict = TDictionary.me.findById(getParaToLong("id"));
			if(dict!=null){
				dict.set("stu", -1).set("update_time", DateUtils.getDate()).update();
				setMsg(Const.MsgType.SUCCESS, "10102", true);
			}
		} catch (Exception e) {
			setMsg(Const.MsgType.ERROR, "20102,90901", true);
		}
		redirect(PATH, "search");
	}
	
}
