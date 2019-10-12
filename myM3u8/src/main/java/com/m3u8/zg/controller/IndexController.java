package com.m3u8.zg.controller;

import com.jfinal.core.Controller;

/**
 * 
 * @Title IndexController.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:20:26
 * @version V1.0
 */
public class IndexController extends Controller {

	public void index() {
		renderText("Hello JFinal World.");
	}
}
