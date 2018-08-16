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
import com.fb.kit.*;
import com.fb.model.TDictionary;
import com.fb.model.TSysLog;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 系统操作日志
 * @author sun
 *
 */
@ControllerBind(controllerKey = "/sys/log")
public class LogController extends BaseController {
	
	protected static final Logger LOG = Logger.getLogger(LogController.class);
	
	/**
	 * 分页查询日志信息
	 */
	public void search() {
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		if(StringUtils.isEmpty(startTime)){
			startTime = DateUtils.getDateFormat(DateUtils.DATE_FORMAT);
		}
		if(StringUtils.isEmpty(endTime)){
			endTime = DateUtils.getDateFormat(DateUtils.DATE_FORMAT);
		}
		String keyword = ToolsUtils.trim(getPara("keyword"));
		Integer platform = getParaToInt("platform");
		Integer type = getParaToInt("type");
		Page<TSysLog> page = TSysLog.me.findPage(pageSort("id", "desc"), keyword, type, platform, startTime, endTime);
		if(page.getList()!=null && page.getList().size()>0){
			for(TSysLog sl:page.getList()){
				if(sl.getInt("platform")==1){
					sl.put("platform_name", "系统后台");
				}else{
					String label = JfinalUtils.findByCodeAndVal(CommonUtils.DictionaryKey.SYSTEM_PLATFORM, sl.getInt("platform")+"", true);
					sl.put("platform_name", label);
				}
				if(StringUtils.isEmpty(sl.getStr("user_agent"))){
					continue;
				}
				UserAgent userAgent = UserAgent.parseUserAgentString(sl.getStr("user_agent"));
				if(userAgent!=null){
					sl.put("browser", userAgent.getBrowser().getManufacturer()+" - "+userAgent.getBrowser().getName());
				}
			}
		}
		
		/** 查询平台  **/
		List<TDictionary> dictList = TDictionary.me.findByChatCode(CommonUtils.DictionaryKey.SYSTEM_PLATFORM);
		setAttr("dictList", dictList);
		
		setAttr("page", page);
		setAttr("startTime", startTime);
		setAttr("endTime", endTime);
		keepPara("keyword");
		keepPara("platform");
		keepPara("type");
	}
	
	public void remove(){
		redirect("/sys/log", "search");
	}

	/**
	 * 查看日志详情
	 */
	public void view(){
		Long id = getParaToLong("id");
		TSysLog sl = TSysLog.me.findById(id);
		if(sl!=null){
			if(!StringUtils.isEmpty(sl.getStr("user_agent"))){
				UserAgent userAgent = UserAgent.parseUserAgentString(sl.getStr("user_agent"));
				if(userAgent!=null){
					sl.put("browser", userAgent.getBrowser().getManufacturer()+" - "+userAgent.getBrowser().getName());
				}
			}
			
			String address = "未知";
			String ip = sl.getStr("ip");
			if(!StringUtils.isEmpty(ip)){
				if(!"127.0.0.1".equals(ip) && !"localhost".equals(ip)){
					try {
						String json = IpUtils.ipsearch(ip);
						if(!StringUtils.isEmpty(json)){
							Map<String, Object> obj = JsonUtils.parseJSON2Map(json);
							Map<String, Object> map = JsonUtils.parseJSON2Map(obj.get("showapi_res_body").toString());
							if(map.get("ret_code")!=null){
								address = "IP地址："+map.get("country")+" - "+map.get("region")+" - "+map.get("city")+"  <br/>ISP提供商："+map.get("isp");
							}
						}
					} catch (Exception e) {e.printStackTrace();}
				}else{
					address = "本机";
				}
			}
			sl.put("ipAddress", address);
		}
		setAttr("model", sl);
		render("_view.html");
	}
	
}
