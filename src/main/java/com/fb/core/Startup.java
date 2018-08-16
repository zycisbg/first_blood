package com.fb.core;

import com.jfinal.core.JFinal;

/**
 * 开发时使用：启动服务
 * 
 * @author sun
 */
public class Startup {

	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 9005, "/", 5);
	}

}
