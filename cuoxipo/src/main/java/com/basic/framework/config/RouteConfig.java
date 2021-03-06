package com.basic.framework.config;

import com.basic.framework.controller.index.HelloController;
import com.basic.framework.controller.index.IndexController;
import com.basic.framework.controller.mangGuo.MangGuoController;
import com.basic.framework.controller.netease.NeteaseCloudMusicController;
import com.basic.framework.controller.netease.QQMusicController;
import com.basic.framework.controller.user.UserController;
import com.basic.framework.controller.youku.YoukuController;
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
		add("/", IndexController.class,"index");
		add("/hello", HelloController.class);
		add("/index", IndexController.class);
		add("/user", UserController.class);
		add("/youku", YoukuController.class);
		add("/mangGuo", MangGuoController.class);
		add("/music/163", NeteaseCloudMusicController.class);
		add("/music/qq", QQMusicController.class);
//		add("/qqLive", QQLiveController.class);
	}


}
