package com.basic.framework.controller.user;

import com.basic.framework.controller.BaseController;

public class UserController  extends BaseController{
	
	public void login(){
		String account = get("account");
		String password =get("password");
		if("13677690703".equals(account) && "111111".equals(password)){
			setSessionAttr("loginUserId", account);
			renderJson("200");
		}else{
//			setSessionAttr("loginUserId", null);
			renderJson("100");
		}
	}

}
