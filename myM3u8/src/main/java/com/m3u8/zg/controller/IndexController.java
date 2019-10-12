package com.m3u8.zg.controller;

import com.jfinal.core.Controller;

public class IndexController extends Controller {

	public void index() {
		renderText("Hello JFinal World.");
	}
}
