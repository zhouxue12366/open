package com.basic.framework.controller.user;

import java.util.ArrayList;
import java.util.List;

import com.basic.framework.controller.BaseController;
import com.basic.framework.interceptor.LoginInterceptor;
import com.basic.framework.utils.ResultMessage;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Record;

public class UserController extends BaseController {
	
	private static List<Record> userList = new ArrayList<Record>();
	static {
		Record user = new Record();
		user.set("account", "13677690703");
		user.set("password", "111111");
		
		Record ranChao = new Record();
		ranChao.set("account", "15223461428");
		ranChao.set("password", "111111");
		
		userList.add(user);
		userList.add(ranChao);
	}

	@Clear(LoginInterceptor.class)
	public void toLogin() {
		render("/pages/user/login.html");
	}

	@Clear(LoginInterceptor.class)
	public void login() {
		String account = get("account");
		String password = get("password");
		ResultMessage result = new ResultMessage();

		for(Record record :userList){
			if (account.equals(record.get("account")) && password.equals(record.get("password"))) {
				setSessionAttr("loginUserId", account);
				result.setCode(200);
				result.setMessage("login success");
				renderJson(result);
				break;
			} else {
				// setSessionAttr("loginUserId", null);
				result.setCode(100);
				result.setMessage("login fail");
				renderJson(result);
			}
		}
		
	}

	public void loginOut() {
		setSessionAttr("loginUserId", null);
		ResultMessage result = new ResultMessage();
		result.setCode(200);
		result.setMessage("login success");
		renderJson(result);
	}

}
