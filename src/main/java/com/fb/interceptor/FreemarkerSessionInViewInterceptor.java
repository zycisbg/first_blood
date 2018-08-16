package com.fb.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.template.ObjectWrapper;

import javax.servlet.http.HttpSession;

/**
 * freemark框架session拦截器、过滤器
 * 
 * @author sun
 */
public class FreemarkerSessionInViewInterceptor implements Interceptor {
	@Override
	public void intercept(ActionInvocation ai) {
		ai.invoke();
		Controller c = ai.getController();
		HttpSession hs = c.getSession(false);
		if (hs != null) {
			c.setAttr("session", new HttpSessionHashModel(hs, ObjectWrapper.DEFAULT_WRAPPER));
		}
	}
}