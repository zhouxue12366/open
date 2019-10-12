package com.m3u8.zg.config;

import com.jfinal.config.Routes;
import com.m3u8.zg.controller.IndexController;
import com.m3u8.zg.controller.QQLiveController;
import com.m3u8.zg.controller.VideoController;
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
		add("/index", IndexController.class);
		add("/video", VideoController.class);
		add("/qqLive", QQLiveController.class);
	}


}
