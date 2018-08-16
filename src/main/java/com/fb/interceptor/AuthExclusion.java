package com.fb.interceptor;

import java.lang.annotation.*;

/**
 * 用途：排除不需要登录验证的方法<br/>
 * 目前应用于退出方法上
 * 
 * @author sun
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AuthExclusion {
	
	// Nothing to do

}
