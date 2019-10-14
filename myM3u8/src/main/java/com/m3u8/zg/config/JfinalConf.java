package com.m3u8.zg.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.template.Engine;

/**
 * jfinal配置类
 * @Title JfinalConf.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:20:11
 * @version V1.0
 */
public class JfinalConf extends JFinalConfig {

	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	public void configRoute(Routes me) {
		// 如果要将控制器超类中的 public 方法映射为 action 配置成 true，一般不用配置
		me.setMappingSuperClass(true);
		 me.setBaseViewPath("/views");
		me.add(new RouteConfig());
	}

	public void configEngine(Engine me) {
	}

	public void configPlugin(Plugins me) {
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}
}
