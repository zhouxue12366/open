package com.general.web.comm.jfinal.config;

import org.beetl.ext.jfinal.JFinalBeetlRenderFactory;

import com.general.web.comm.jfinal.route.RouteConfig;
import com.general.web.model._MappingKit;
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
 * 
 * @Title JfinalConfig.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年4月10日 上午9:41:46
 * @version V1.0
 */
public class JfinalConfig extends JFinalConfig {

	static Prop prop;

	/**
	 * PropKit.useFirstFound(...) 使用参数中从左到右最先被找到的配置文件
	 * 从左到右依次去找配置，找到则立即加载并立即返回，后续配置将被忽略
	 */
	static void loadConfig() {
		if (prop == null) {
			prop = PropKit.useFirstFound("config-pro.txt", "config-dev.txt");
		}
	}

	@Override
	public void configConstant(Constants me) {
		loadConfig();

		me.setDevMode(prop.getBoolean("devMode", false));

		/**
		 * 支持 Controller、Interceptor、Validator 之中使用 @Inject 注入业务层，并且自动实现 AOP
		 * 注入动作支持任意深度并自动处理循环注入
		 */
		me.setInjectDependency(true);

		// 配置对超类中的属性进行注入
		me.setInjectSuperClass(true);
		// 配置beetl模板
		this.beetlConfig(me);
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
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(prop.get("jdbcUrl"), prop.get("userName"), prop.get("password").trim());
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(true);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
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
	 * 
	 * @Title beetlConfig
	 * @Description
	 * @param me
	 * @since 2020年3月31日 上午11:20:45
	 */
	private void beetlConfig(Constants me) {
		// 配置beetl模板
		JFinalBeetlRenderFactory rf = new JFinalBeetlRenderFactory();
		rf.config();
		me.setRenderFactory(rf);
		// GroupTemplate gt = rf.groupTemplate;
	}
}
