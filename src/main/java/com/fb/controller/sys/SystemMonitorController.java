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
import com.fb.interceptor.AuthExclusion;
import com.fb.kit.ToolsUtils;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;

/**
 * 系统监控
 * @author sun
 */
@ControllerBind(controllerKey = "/monitor")
public class SystemMonitorController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(SystemMonitorController.class);
	
	/**
	 * 内存实时监控
	 */
	@AuthExclusion
	public void memory(){
		Runtime runtime = Runtime.getRuntime();
	    double freeMemory = (double)runtime.freeMemory()/(1024*1024);
		double totalMemory = (double)runtime.totalMemory()/(1024*1024);
		double usedMemory = totalMemory - freeMemory;
		double percentFree = (usedMemory/totalMemory)*100.0;
		renderJson(ToolsUtils.formatTosepara(percentFree));
	}
	
}
