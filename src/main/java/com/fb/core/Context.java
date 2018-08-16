package com.fb.core;
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

import com.fb.interceptor.XssRequestWrapper;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 上下文
 * @author sun
 * @date 2016年7月20日 上午10:24:56
 */
public class Context extends Handler {

	/** 
	 * 上下文，主要是用作返回界面的一些配置信息
	 * @author sun
	 * @date 2016年7月20日 上午10:24:49
	 * @see Handler#handle(String, HttpServletRequest, HttpServletResponse, boolean[])
	 */
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		request.setAttribute("ctx", request.getContextPath());
		request.setAttribute("suffix", PropKit.use(Const.DEFAULT_CONFIG_NAME).get("application.suffix", Const.BLANK));
		request.setAttribute("dev_model", PropKit.use(Const.DEFAULT_CONFIG_NAME).get("dev.mode"));
		request.setAttribute("ossStaticPath", PropKit.use(Const.DEFAULT_CONFIG_NAME).get("alioss.url"));
		/** 下边这俩转移到ChannelController中，因为只有那个search方法用到 **/
//		request.setAttribute("aicailang_website_url", PropKit.use(Const.DEFAULT_CONFIG_NAME).get("aicailang.website.url", Const.BLANK));
//		request.setAttribute("aicailang_wap_url", PropKit.use(Const.DEFAULT_CONFIG_NAME).get("aicailang.wap.url", Const.BLANK));
		nextHandler.handle(target, new XssRequestWrapper(request), response, isHandled);
	}

}
