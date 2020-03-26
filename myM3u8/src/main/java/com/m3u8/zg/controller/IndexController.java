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
		render("/views/index.html");
	}
	
	public void app(){
		System.out.println("to app html......");
		render("/views/app.html");
	}
	public void location(){
		System.out.println("to location html......");
		render("/views/location.html");
	}
}
