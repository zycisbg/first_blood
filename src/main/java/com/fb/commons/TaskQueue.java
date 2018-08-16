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

import com.fb.core.Const;
import com.fb.kit.DateUtils;
import com.fb.model.TSysLog;
import com.fb.model.rbac.TRbacUser;
import com.jfinal.kit.EncryptionKit;
import com.jfinal.log.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 任务队列
 * @author sun
 *
 */
public class TaskQueue implements Runnable {

	protected static final Logger LOG = Logger.getLogger(TaskQueue.class);
	
	public static final String QUEUE_TYPE_LOG = "t_wechat_log";// 日志操作类
	
	private static TaskQueue uniqueInstance = null;// 单例

	public Queue<Map<String, Object>> queue = new LinkedBlockingDeque<Map<String, Object>>();

	public static TaskQueue getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new TaskQueue();
			new Thread(uniqueInstance).start();
		}
		return uniqueInstance;
	}

	/**
	 * 通知有任务
	 * @param paramMap
	 */
	public void notifyTask(Map<String, Object> paramMap) {
		queue.add(paramMap);
		synchronized (queue) {
			queue.notify();
		}
	}

	public void run() {
		while (true) {
			getTask();
		}
	}

	/**
	 * 监听
	 */
	private void getTask() {
		Map<String, Object> taskMap = null;
		synchronized (queue) {
			taskMap = queue.poll();// 用于检索并移除此队列的头，则返回null，如果此队列为空
		}
		if (taskMap == null) {
			try {
				synchronized (queue) {
					queue.wait();// 继续等待
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getTask();
		} else {
			doTask(taskMap);//去执行任务
		}
	}

	/**
	 * 执行任务
	 * @param map
	 */
	private void doTask(Map<String, Object> map) {
		if(map!=null && map.size()>0){
			Set<String> keyset = map.keySet();
			Iterator<String> it = keyset.iterator();
			while(it.hasNext()){
				try {
					String key = it.next();
					if(key.equalsIgnoreCase(QUEUE_TYPE_LOG)){
						//写入日志至数据库
						writeLog(map, key);
					}else{
						LOG.warn("doTask key 异常, 无匹配~~~");
					}
				} catch (Exception e) {
					LOG.info("doTask while 异常!~~~");
				}
			}
		}else{
			LOG.info("queue接收到任务执行doTask, 但是map为空。 异常警告~~~");
		}
	}

	/**
	 * Const.QUEUE_TYPE_LOG	记录操作访问日志
	 * @param param
	 * @param keyType
	 */
	@SuppressWarnings("unchecked")
	private void writeLog(Map<String, Object> param, String keyType) {
		Map<String, Object> map = (Map<String, Object>)param.get(keyType);
		String requestUri = map.get("requestUri")+"";
		String method = map.get("method")+"";//get、post
		requestUri = requestUri.replaceAll(Const.MP_SUFFIX, "");//去掉后缀
		String note = map.get("note")+"";
		String userAgent = map.get("userAgent")+"";
		String ip = map.get("ip")+"";
		String functionName = map.get("function")+"";
		Integer stu = Integer.valueOf(map.get("stu")+"");
		
		//列表、查看和排序不记录日志
		String module = requestUri.substring(0, requestUri.lastIndexOf("/"));
		TSysLog log = new TSysLog();
		if(functionName.equalsIgnoreCase("/logout")){
			log.set("type", 2);
			module = "退出";
			//session自动销毁
			if(requestUri.startsWith("/sessionInvalidate")){
				log.set("type", 2);
				module = "被退出";
			}else{
				note = "退出系统";
			}
			
		}else if(functionName.equalsIgnoreCase("/login")){
			log.set("type", 1);
			if(stu==1){
				module = "登录成功";
				note = "登录系统";
			}else{
				module = "登录失败";
			}
		}else{
			log.set("type", 3);//操作 类型
		}
		
		if(method.equalsIgnoreCase("post")){
			note = map.get("parameterMap")+"";//form表单值
		}
		log.set("platform", Const.PROJECT_PLATFORM);
		
		
		TRbacUser user = (TRbacUser)map.get("user");
		String userName = null;
		String password = null;
		if(user!=null){
			userName = user.getStr("username");
			password = user.getStr("password");
		}else{
			userName = map.get("username")+"";
			password = map.get("password")+"";
		}
		
		//管理员必须不能为空
		if(functionName.equalsIgnoreCase("/login")){
			String un = map.get("username")+"";
			if(stu==1){
				String up = map.get("password")+"";
				if(userName.equals(un) && password.equals(EncryptionKit.md5Encrypt(up))){
					log.set("user_name", userName);
				}else{
					log.set("user_name", userName+":"+un);
				}
			}else{
				log.set("user_name", un);
			}
		}else{
			log.set("user_name", userName);
		}
		log.set("ip", ip);
		log.set("stu", stu);
		log.set("user_agent", userAgent);
		log.set("module", module);
		log.set("function", functionName);
		log.set("description", note);
		log.set("add_time", DateUtils.getDate());
		log.save();
		
	}

}
