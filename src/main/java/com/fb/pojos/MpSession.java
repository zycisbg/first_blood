package com.fb.pojos;

import com.fb.model.rbac.TRbacUser;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @Description: TODO(mp系统 session 封装类)
 * @author sun
 * @date 2015年12月11日 上午10:46:41
 * 
 */
public class MpSession implements Serializable {

	private static final long serialVersionUID = 3299079200295293980L;
	private TRbacUser user;// 登录用户信息
	private String loginTime;// 登录时间
	private String totalTime;// 在线时长
	private String loginIp;// 登录ip
	private String sessionId;// session唯一id
	private HttpSession session;// session信息

	public MpSession(TRbacUser user, String loginTime, String loginIp,
			String sessionId, HttpSession session) {
		super();
		this.user = user;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.sessionId = sessionId;
		this.session = session;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public TRbacUser getUser() {
		return user;
	}

	public void setUser(TRbacUser user) {
		this.user = user;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

}
