package com.demo.booksOverdue;

import com.demo.booksBorrowInfo.BooksBorrowInfo;
import com.jfinal.core.Controller;

public class BooksOverdueController extends Controller{
	
	public void index(){
		setAttr("booksBorrowInfoPage",BooksBorrowInfo.me.paginateOverdue(getParaToInt(0, 1), 20));
		render("booksOverdue.jsp");
	}
}
