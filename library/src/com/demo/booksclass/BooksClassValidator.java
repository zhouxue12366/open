package com.demo.booksclass;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BooksClassValidator extends Validator  {
	protected void validate(Controller controller) {
		validateRequiredString("booksClass.className", "classNameMsg", "请输入类目名称!");
		validateRequiredString("booksClass.classNum", "classNumMsg", "请输入分类号!");
	}

	@Override
	protected void handleError(Controller controller) {
	controller.keepModel(BooksClass.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/booksClass/save"))
			controller.render("add.jsp");
		else if (actionKey.equals("/booksClass/update"))
			controller.render("edit.jsp");
		
	}
}
