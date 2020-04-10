package com.general.web.comm.jfinal.route;

import com.general.web.controller.index.IndexController;
import com.jfinal.config.Routes;
/**
 * 
 * @Title RouteConfig.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年4月10日 上午9:23:53
 * @version V1.0
 */
public class RouteConfig extends Routes{

	@Override
	public void config() {
		add("/", IndexController.class);
//		add("/video", VideoController.class);
//		add("/qqLive", QQLiveController.class);
	}


}
