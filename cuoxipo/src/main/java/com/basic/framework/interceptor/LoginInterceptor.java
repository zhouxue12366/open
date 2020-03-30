package com.basic.framework.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 登录拦截器
 * @Title LoginInterceptor.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年3月30日 下午5:57:37
 * @version V1.0
 */
public class LoginInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		String loginUserId = inv.getController().getSessionAttr("loginUserId");
		inv.invoke();
		if(StringUtils.isBlank(loginUserId)){
			System.out.println("没有登录");
			inv.getController().render("/views/user/login.html");
		}else{
			System.out.println("已经登录成功!");
		}
		
	}

}
