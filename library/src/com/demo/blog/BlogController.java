package com.demo.blog;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController 
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {

	public void index() {
		System.out.println("=======1111=====index====");
		setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 5));
		render("blog.jsp");
		System.out.println("=======2222=====index====");
	}

	public void add() {
	}

	@Before(BlogValidator.class)
	public void save() throws Exception {
		getModel(Blog.class).save();
		redirect("/blog");
	}

	public void edit() {
		setAttr("blog", Blog.me.findById(getParaToInt()));
		System.out.println( Blog.me.findById(getParaToInt()));
	}

	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}

	public void delete() {
		Blog.me.deleteById(getParaToInt());
		redirect("/blog");
	}
}
