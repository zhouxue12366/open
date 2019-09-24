package com.demo.booksBorrowInfo;

import com.demo.booksEmployee.BooksEmployee;
import com.demo.booksInfo.BooksInfo;
import com.demo.booksclass.BooksClass;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BooksBorrowInfoValidator extends Validator  {
	protected void validate(Controller controller) {
		String employeeId = controller.getPara("employeeId");
		BooksEmployee employee = BooksEmployee.me.findOne(employeeId);
		if(employee == null){
			addError("employeeIdMsg", "借书人工号不存在!!");
		}
		int booksInfoId = controller.getParaToInt("booksInfoId");
		BooksInfo booksInfo = BooksInfo.me.findById(booksInfoId);
		if(booksInfo.getInt("status") == 1){
			addError("booksInfoMsg", "该图书已借出！！");
		}
	}

	@Override
	protected void handleError(Controller controller) {
	controller.keepModel(BooksClass.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/booksBorrowInfo/borrow"))
			controller.render("booksBorrowInfo.jsp");			
	} 

}
