package com.demo.bookspublish;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class BooksPublishController extends Controller {
	public void index() {
		setAttr("booksPublishList", BooksPublish.me.findAll());
		render("booksPublish.jsp");
	}

	public void add() {

	}

	// 新增出版社
	@Before(BooksPublishValidator.class)
	public void save() {
		getModel(BooksPublish.class).save();
		redirect("/booksPublish");
	}

	// 根据id查询出版社详情
	public void edit() {
		setAttr("booksPublish", BooksPublish.me.findById(getParaToInt()));
	}

	// 修改出版社
	@Before(BooksPublishValidator.class)
	public void update() {
		getModel(BooksPublish.class).update();
		redirect("/booksPublish");
	}

	// 删除出版社
	public void delete() {
		BooksPublish.me.deleteById(getParaToInt());
		redirect("/booksPublish");
	}

	// 查询出版社管理列表的数据
	public void list() {
		setAttr("booksPublishList", BooksPublish.me.findAll());
		render("list.jsp");
	}
}
