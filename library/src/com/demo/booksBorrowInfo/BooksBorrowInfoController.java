package com.demo.booksBorrowInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.demo.booksEmployee.BooksEmployee;
import com.demo.booksInfo.BooksInfo;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class BooksBorrowInfoController extends Controller {

	public void index() {
		render("booksBorrowInfo.jsp");
	}

	public void queryBorrowInfo() {
		String employeeId = getPara("employeeId");
		BooksEmployee employee = BooksEmployee.me.findOne(employeeId);
		setAttr("employee", employee);
		setAttr("booksBorrowInfoPage", BooksBorrowInfo.me.paginateBorrowInfo(getParaToInt(0, 1), 20, employeeId));
		render("booksBorrowInfo.jsp");
	}

	// 借书
	@Before(BooksBorrowInfoValidator.class)
	public void borrow() {
		String employeeId = getPara("employeeId");
		BooksEmployee employee = BooksEmployee.me.findOne(employeeId);
		int employee_id = employee.getInt("id");

		int booksInfoId = getParaToInt("booksInfoId");
		String agreeReturnDate = getPara("agreeReturnDate");
		BooksBorrowInfo booksBorrowInfo = getModel(BooksBorrowInfo.class);
		booksBorrowInfo.set("employee_id", employee_id);
		booksBorrowInfo.set("booksInfo_id", booksInfoId);
		booksBorrowInfo.set("agreeReturnDate", agreeReturnDate);

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		booksBorrowInfo.set("lendDate", time); // 借出时间
		booksBorrowInfo.set("deposit", "0");
		booksBorrowInfo.set("status", "0"); // 未还

		// 图书登记为已借出
		BooksInfo.me.updateBorrow(time, booksInfoId);

		// 保存借阅情况
		booksBorrowInfo.save();

		// 查出借阅情况
		setAttr("booksBorrowInfoPage", BooksBorrowInfo.me.paginateBorrowInfo(getParaToInt(0, 1), 20, employeeId));
		render("booksBorrowInfo.jsp");
	}

	// 还书
	public void returnBook() {
		BooksBorrowInfo booksBorrowInfo = BooksBorrowInfo.me.findById(getParaToInt("id"));

		Integer employeeId = 0;
		if (booksBorrowInfo != null) {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String time = format.format(date);
			booksBorrowInfo.set("realReturnDate", time); // 还书时间
			booksBorrowInfo.set("status", "1"); // 已还

			Object id = booksBorrowInfo.get("employee_id");
			BooksEmployee employee = BooksEmployee.me.findById(id);
			employeeId = employee.getInt("employeeId");
			// 图书登记为已借出
			BooksInfo booksInfo = BooksInfo.me.findById(booksBorrowInfo.getStr("booksInfo_id"));
			booksInfo.set("status", "0");// 未借出
			booksInfo.update();
			// 保存借阅情况
			booksBorrowInfo.update();
			// 查出借阅情况
			setAttr("booksBorrowInfoPage",
					BooksBorrowInfo.me.paginateBorrowInfo(getParaToInt(0, 1), 20, employeeId + ""));
		}
		render("booksBorrowInfo.jsp"); // 图书借阅页面

	}
}
