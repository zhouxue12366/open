package com.demo.common;

import com.demo.blog.Blog;
import com.demo.blog.BlogController;
import com.demo.booksBorrowInfo.BooksBorrowInfo;
import com.demo.booksBorrowInfo.BooksBorrowInfoController;
import com.demo.booksEmployee.BooksEmployee;
import com.demo.booksEmployee.BooksEmployeeController;
import com.demo.booksInfo.BooksInfo;
import com.demo.booksInfo.BooksInfoController;
import com.demo.booksOverdue.BooksOverdueController;
import com.demo.booksUser.BooksUser;
import com.demo.booksUser.BooksUserController;
import com.demo.booksclass.BooksClass;
import com.demo.booksclass.BooksClassController;
import com.demo.bookspublish.BooksPublish;
import com.demo.bookspublish.BooksPublishController;
import com.demo.index.IndexController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		PropKit.use("a_little_config.txt"); // 加载少量必要配置，随后可用PropKit.get(...)获取值
		me.setDevMode(true);// 开发模式
		me.setEncoding("utf-8");// 设置编码
		me.setViewType(ViewType.JSP);// 设置视图为JSP
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "/index"); // 第三个参数为该Controller的视图存放路径
		me.add("/blog", BlogController.class,"/blog"); // 第三个参数省略时默认与第一个参数值相同，在此即为"/blog"
		me.add("/booksUser", BooksUserController.class,"/booksUser");
		me.add("/booksClass",BooksClassController.class,"/booksClass");
		me.add("/booksPublish",BooksPublishController.class,"/booksPublish");
		me.add("/booksInfo",BooksInfoController.class,"/booksInfo");
		me.add("/booksBorrowInfo",BooksBorrowInfoController.class,"/booksBorrowInfo");
		me.add("/booksEmployee",BooksEmployeeController.class,"/booksEmployee");
		me.add("/booksOverdue",BooksOverdueController.class,"/booksOverdue");
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"),
				PropKit.get("user"), PropKit.get("password").trim());
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		// 配置Oracle方言
		arp.setDialect(new MysqlDialect());
		// 配置属性名(字段名)大小写不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		me.add(arp);
		arp.addMapping("blog", Blog.class); // 映射blog 表到 Blog模型
		arp.addMapping("booksUser", BooksUser.class); // 映射管理员表
		arp.addMapping("booksclass",BooksClass.class);//映射图书分类表
		arp.addMapping("booksPublish", BooksPublish.class);//映射出版社表
		arp.addMapping("booksInfo", BooksInfo.class);//映射图书表
		arp.addMapping("booksBorrowInfo", BooksBorrowInfo.class);//映射图书借阅表
		arp.addMapping("booksEmployee", BooksEmployee.class);//映射员工表
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
		//判断是否有session值
		me.add(new SessionInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("contextPath"));//获取
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8080, "/library",5);
	}
}
