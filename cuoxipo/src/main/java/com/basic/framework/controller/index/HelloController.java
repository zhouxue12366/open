package com.basic.framework.controller.index;

import com.basic.framework.controller.BaseController;

public class HelloController extends BaseController {

	public void index() {
		renderText("Hello JFinal World.");
	}
}
