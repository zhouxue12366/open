package com.basic.framework.config.beetl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

/**
 * 扩展Web提供的全局变量 request 中的所有attribute.在模板中可以直接通过attribute name 来引用，如在controller层
 * request.setAttribute("user",user),则在模板中可以直接用${user.name} . session
 * 提供了session会话，模板通过session["name"],或者session.name
 * 引用session里的变量.注意，session并非serlvet里的标准session对象。参考servlet来获取HTTPSession。
 * request
 * 标准的HTTPServletRequest,可以在模板里引用request属性（getter），如${request.requestURL}。
 * parameter 读取用户提交的参数。如${parameter.userId} (仅仅2.2.7以上版本支持) ctxPath
 * Web应用ContextPath servlet
 * 是WebVariable的实例，包含了HTTPSession,HTTPServletRequest,HTTPServletResponse.三个属性，
 * 模板中可以通过request,response,session 来引用，如 ${servlet.request.requestURL};
 * 所有的GroupTemplate的共享变量 pageCtx是一个内置方法
 * ，仅仅在web开发中，用于设置一个变量，然后可以在页面渲染过程中，调用此api获取，如pageCtx("title","用户添加页面")，在其后任何地方，
 * 可以pageCtx("title") 获取该变量。(仅仅2.2.7以上版本支持)
 * 
 * @Title BeetlGlobalExt.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年3月31日 上午11:16:40
 * @version V1.0
 */
public class BeetlGlobalExt implements WebRenderExt {

	/**
	 * 你可以在模板任何地方访问这些变量
	 * 
	 * @Title modify
	 * @Description
	 * @param template
	 * @param groupTemplate
	 * @param request
	 * @param response
	 * @since 2019年8月20日 上午10:39:34
	 */
	@Override
	public void modify(Template template, GroupTemplate groupTemplate, HttpServletRequest request,
			HttpServletResponse response) {

		// 当前货币符号,例如:$
		// String currencySymbol =
		// CurrencySymbolEnum.getSymbol(BaseControl.getCurrency());
		// template.binding("currencySymbol",currencySymbol);
		// //当前币种简称,例如:USD
		// template.binding("currencyCode",BaseControl.getCurrency());
		// //所有货币
		// template.binding("currencyCodeValues",CurrencyCodeEnum.values());
		//团队默认图标
//		template.binding("defaultTeamImage","/static/img/index/team-head.png");
//		template.binding("imageServerUrl","http://img.zhuge.gg/");
		
	}

}
