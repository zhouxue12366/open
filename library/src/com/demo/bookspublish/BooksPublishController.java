package com.demo.bookspublish;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class BooksPublishController extends Controller {
	public void index() {
		setAttr("booksPublishList",BooksPublish.me.findAll());
		render("booksPublish.jsp");
	}
	public void add(){
		
	}
	@Before(BooksPublishValidator.class)
	public void save(){
		getModel(BooksPublish.class).save();
		redirect("/booksPublish");
	}
	public void edit(){
		setAttr("booksPublish", BooksPublish.me.findById(getParaToInt()));
	}
	@Before(BooksPublishValidator.class)
	public void update(){
		getModel(BooksPublish.class).update();
		redirect("/booksPublish");
	}
	public void delete(){
		BooksPublish.me.deleteById(getParaToInt());
		redirect("/booksPublish");
	}
	public void list() {
		setAttr("booksPublishList",BooksPublish.me.findAll());
		render("list.jsp");
	}
}
