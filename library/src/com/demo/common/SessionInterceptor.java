package com.demo.common;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class SessionInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		System.out.println("url: " + inv.getActionKey());
		Controller controller = inv.getController();
		HttpSession session = controller.getRequest().getSession();
		System.out.println("libraryCode="+session.getAttribute("libraryCode")+"===========");
		//判断是否有session值
		if(session.getAttribute("libraryCode") == null){
			controller.render("/index/login.jsp");
			return;
		} 
		
		inv.invoke();	
		
	}

}
