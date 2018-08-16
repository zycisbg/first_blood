package com.fb.kit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * COOKIE 操作类
 * 
 * @author sun
 * @date 2016年8月23日 上午10:33:56
 */
public class CookieUtils {

	public final static int TIME = 100 * 24 * 60 * 60;

	/**
	 * 写入cookie
	 * @author sun
	 * @date 2016年8月23日 上午10:36:15
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param time
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int time) {
		boolean flag = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					// 更新
					cookie.setValue(value);
					// cookie.setPath(request.getContextPath());
					cookie.setPath("/");
					cookie.setMaxAge(time);
					response.addCookie(cookie);
					flag = true;
					break;
				}
			}
		}
		if (!flag) {
			// 新增
			Cookie cookie = new Cookie(name.trim(), value.trim());
			cookie.setMaxAge(time);// 设置为100天
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}

	/**
	 * 修改cookie
	 * 注意一、修改、删除Cookie时，新建的Cookie除value、maxAge之外的所有属性，例如name、path、domain等，都要与原Cookie完全一样。否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。
	 * @author sun
	 * @date 2016年8月23日 上午10:36:35
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void editCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int time) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(value);
					cookie.setPath("/");
					cookie.setMaxAge(time);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}

	/**
	 * 删除 cookie
	 * @author sun
	 * @date 2016年8月23日 上午10:37:19
	 * @param request
	 * @param response
	 * @param name
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(null);
					cookie.setMaxAge(0);// 立即销毁cookie
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
}
