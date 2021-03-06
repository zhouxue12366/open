package com.m3u8.zg.config;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.JFinalBeetlRenderFactory;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.template.Engine;

/**
 * jfinal配置类
 * 
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

		// 开启对 jfinal web 项目组件 Controller、Interceptor、Validator 的注入
		me.setInjectDependency(true);

		// 开启对超类的注入。不开启时可以在超类中通过 Aop.get(...) 进行注入
		me.setInjectSuperClass(true);
		
//		JFinalBeetlRenderFactory rf = new JFinalBeetlRenderFactory();
//        rf.config();
//        me.setRenderFactory(rf);
//        GroupTemplate gt = rf.groupTemplate;
//        
	}

	public void configRoute(Routes me) {
		// 如果要将控制器超类中的 public 方法映射为 action 配置成 true，一般不用配置
		me.setMappingSuperClass(true);
		me.add(new RouteConfig());
	}

	public void configEngine(Engine engine) {
		// 支持模板热加载，绝大多数生产环境下也建议配置成 true，除非是极端高性能的场景
		engine.setDevMode(true);
		 
		// 添加共享模板函数
//		engine.addSharedFunction("_layout.html");
		 
		// 配置极速模式，性能提升 13%
		Engine.setFastMode(true);
	}

	public void configPlugin(Plugins me) {
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}
}
