package com.basic.framework.interceptor;

import com.basic.framework.utils.Constants;
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
//		Integer loginUserId = inv.getController().getSessionAttr(Constants.login_User_Id);
		inv.invoke();
//		if(null == loginUserId || loginUserId.intValue() == 0){
//			System.out.println("没有登录");
//			inv.getController().render("/user/login.html");
//		}else{
//			System.out.println("已经登录成功!");
//		}
		
	}

}
