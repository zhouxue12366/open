package com.demo.bookspublish;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BooksPublishValidator extends Validator {
	protected void validate(Controller controller) {
		validateRequiredString("booksPublish.publishname", "publishnameMsg", "请输入出版社名称!");
		validateRequiredString("booksPublish.ISBN", "ISBNMsg", "请输入ISBN!");
	}

	@Override
	protected void handleError(Controller controller) {
		controller.keepModel(BooksPublish.class);
		String actionKey = getActionKey();
		if (actionKey.equals("/booksPublish/save"))
			controller.render("add.jsp");
		else if (actionKey.equals("/booksPublish/update"))
			controller.render("edit.jsp");
		
	}
}
