package com.fb.core;


import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.fb.interceptor.AuthInterceptor;
import com.fb.interceptor.FreemarkerSessionInViewInterceptor;
import com.fb.interceptor.UrlSkipHandler;
import com.jfinal.config.*;
import com.jfinal.ext.handler.FakeStaticHandler;
import com.jfinal.ext.plugin.quartz.QuartzPlugin;
import com.jfinal.ext.plugin.sqlinxml.SqlInXmlPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.plugin.tablebind.SimpleNameStyles;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;

/**
 * 系统核心配置文件
 * @author sun
 *
 */
public class Config extends JFinalConfig {
	
	/**
	 * 配置系统常量
	 */
	public void configConstant(Constants me) {
		// 系统通用配置 ========================================= START =========================================
		loadPropertyFile(Const.DEFAULT_CONFIG_NAME);
		
		// 设置开发者模式
		me.setDevMode(getPropertyToBoolean("dev.mode", false));
		// 配置视图路径
		me.setBaseViewPath(getProperty("view.path"));
		// 配置404、500页面路径
		me.setError404View(getProperty("error.page.404"));
		me.setError500View(getProperty("error.page.500"));
		
		// 系统通用配置 =========================================  END  =========================================
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		// 系统通用配置 ========================================= START =========================================
		// 配置Druid统计信息
		me.add(new DruidStatViewHandler("/druid"));
		// 配置上下文路径
		me.add(new Context());
		me.add(new UrlSkipHandler());
		// 配置伪静态
		String suffix = getProperty("application.suffix");
		me.add(new FakeStaticHandler(suffix));
		Const.MP_SUFFIX = suffix;
//		me.add(new UrlSkipHandler(".+\\.\\w{1,4}", false));
		// 系统通用配置 =========================================  END  =========================================
	}

	/**
	 * 配置拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new AuthInterceptor());
		me.add(new FreemarkerSessionInViewInterceptor());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 系统通用配置 ========================================= START =========================================
		WallFilter wall = new WallFilter();;
		wall.setDbType("mysql");
		// 主库连接
		
		DruidPlugin dp = new DruidPlugin(getProperty("db.slave.url"), getProperty("db.slave.user"), getProperty("db.slave.pass"));
		// 配置Druid统计信息 开始
		dp.addFilter(new StatFilter());
		dp.addFilter(wall);
		// 配置Druid统计信息 结束
		me.add(dp);
		AutoTableBindPlugin atbp = new AutoTableBindPlugin(dp, SimpleNameStyles.LOWER_UNDERLINE);
		atbp.addScanPackages("com.fb.model");
		atbp.setShowSql(false);
		me.add(atbp);
		//数据源1 读
		/*DruidPlugin dp1 = new DruidPlugin(getProperty("db.slave.url"), getProperty("db.slave.user"), getProperty("db.slave.pass"));
		// 配置Druid统计信息 结束
		me.add(dp1);
		AutoTableBindPlugin atbp1 = new AutoTableBindPlugin(Const.DB_SLAVE, dp1, SimpleNameStyles.LOWER_UNDERLINE);
		atbp1.addScanPackages("com.aicailang.report.model");
		atbp1.setShowSql(getPropertyToBoolean("dev.mode", false));
		me.add(atbp1);
		
		//数据源2 写
		DruidPlugin dp2 = new DruidPlugin(getProperty("db.master.url"), getProperty("db.master.user"), getProperty("db.master.pass"));
		me.add(dp2);
		AutoTableBindPlugin atbp2 = new AutoTableBindPlugin(Const.DB_MASTER, dp2, SimpleNameStyles.LOWER_UNDERLINE);
		atbp2.addScanPackages("com.aicailang.report.model");
		atbp2.setShowSql(getPropertyToBoolean("dev.mode", false));
		me.add(atbp2);*/
		
		// 配置SQL在XML文件中
		me.add(new SqlInXmlPlugin());
		//定时任务调度插件
		//QuartzPlugin quartzPlugin =  new QuartzPlugin("job.properties");
        //me.add(quartzPlugin);
		me.add(new QuartzPlugin());//自定义持久化quartze
		// 系统通用配置 =========================================  END  =========================================
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		// 系统通用配置 ========================================= START =========================================
		// 添加自动绑定Controller
		me.add(new AutoBindRoutes());
		// 系统通用配置 =========================================  END  =========================================
	}

	/**
	 * 主要用于配置Freemarker自定义标签
	 */
	public void afterJFinalStart() {
		super.afterJFinalStart();
		String projectName = getProperty("project.name");
		Const.PROJECT_PLATFORM = "4";
	}

}