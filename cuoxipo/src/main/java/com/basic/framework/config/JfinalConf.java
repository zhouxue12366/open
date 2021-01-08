package com.basic.framework.config;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.JFinalBeetlRenderFactory;

import com.basic.framework.interceptor.LoginInterceptor;
import com.basic.framework.model._MappingKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

/**
 * jfinal配置类
 * 
 * @Title JfinalConf.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月15日 上午10:53:51
 * @version V1.0
 */
public class JfinalConf extends JFinalConfig {

	static Prop prop;

	/**
	 * PropKit.useFirstFound(...) 使用参数中从左到右最先被找到的配置文件
	 * 从左到右依次去找配置，找到则立即加载并立即返回，后续配置将被忽略
	 */
	static void loadConfig() {
		if (prop == null) {
			prop = PropKit.useFirstFound("config-pro.txt", "config.txt");
		}
	}
	
	@Override
	public void configConstant(Constants me) {
		
		loadConfig();
		me.setDevMode(prop.getBoolean("devMode", false));
		// 开启对 jfinal web 项目组件 Controller、Interceptor、Validator 的注入
		me.setInjectDependency(true);
		// 开启对超类的注入。不开启时可以在超类中通过 Aop.get(...) 进行注入
		me.setInjectSuperClass(true);
		
		//配置beetl模板
		this.beetlConfig(me);
		me.setEncoding("UTF-8");
		// 配置 404、500 页面
	    me.setError404View("/comm/404.html");
	    me.setError500View("/comm/500.html"); 
	}
	
	@Override
	public void configRoute(Routes me) {
		// 如果要将控制器超类中的 public 方法映射为 action 配置成 true，一般不用配置
		me.setMappingSuperClass(true);
		me.add(new RouteConfig());
	}

	@Override
	public void configEngine(Engine engine) {
//		// 支持模板热加载，绝大多数生产环境下也建议配置成 true，除非是极端高性能的场景
//		engine.setDevMode(true);
//		// 配置极速模式，性能提升 13%
//		Engine.setFastMode(true);
	}

	@Override
	public void configPlugin(Plugins me) {
		//配置数据库连接
		DruidPlugin dp = new DruidPlugin(prop.get("jdbcUrl"), prop.get("userName"), prop.get("password").trim());
		//指定编码为utf8mb4    
		dp.setConnectionInitSql("set names utf8mb4");
	    me.add(dp);
	    //配置Record插件
	    ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
	    me.add(arp);
	    //实体类直接dao方法
	    _MappingKit.mapping(arp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new LoginInterceptor());
		me.add(new SessionInViewInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctxPath")); 
	}
	
	/**
	 * 创建连接池插件
	 * @Title createDruidPlugin
	 * @Description  
	 * @return 
	 * @since 2020年4月10日 上午9:22:55
	 */
	public static DruidPlugin createDruidPlugin() {
		loadConfig();
		return new DruidPlugin(prop.get("jdbcUrl"), prop.get("userName"), prop.get("password").trim());
	}
	
	/**
	 * 配置beetl模板
	 * @Title beetlConfig
	 * @Description  
	 * @param me 
	 * @since 2020年3月31日 上午11:20:45
	 */
	private void beetlConfig(Constants me){
		//配置beetl模板
		JFinalBeetlRenderFactory rf = new JFinalBeetlRenderFactory();
        rf.config();
        me.setRenderFactory(rf);
//        GroupTemplate gt = rf.groupTemplate;
	}
	
}
