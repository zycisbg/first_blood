package com.fb.commons;
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

import com.fb.kit.CommonUtils;
import com.fb.pojos.MpSession;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Iterator;
import java.util.Set;

/**
 * session 监听
 * @author sun
 * @date 2016年8月23日 上午10:09:15
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		Set<String> keyset = CommonUtils.sessionMap.keySet();
		Iterator<String> it = keyset.iterator();
		while (it.hasNext()) {
			String key = it.next();// 用户名
			MpSession mps = CommonUtils.sessionMap.get(key);
			if (mps != null && !StringUtils.isEmpty(mps.getSessionId())) {
				if (mps.getSessionId().equals(sessionId)) {
					// 匹配到cache中存在这个sessionid， 去除
					CommonUtils.sessionMap.remove(key);// 内存cache中删除
					//记录自动退出日志
					//JfinalUtils.logToTask("/sessionInvalidate", "GET", "/logout", "sesion被销毁", "", "127.0.0.1", null, 1, session, null);
					break;
				}
			}
		}
	}

}
