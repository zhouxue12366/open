package com.basic.framework.config;

import com.basic.framework.controller.index.HelloController;
import com.basic.framework.controller.index.IndexController;
import com.basic.framework.controller.user.UserController;
import com.jfinal.config.Routes;
/**
 * 自定义路由
 * @Title RouteConfig.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:20:19
 * @version V1.0
 */
public class RouteConfig extends Routes{

	@Override
	public void config() {
		setBaseViewPath("views");
		add("/", IndexController.class,"index");
		add("/hello", HelloController.class);
		add("/index", IndexController.class);
		add("/user", UserController.class);
//		add("/video", VideoController.class);
//		add("/qqLive", QQLiveController.class);
	}


}
