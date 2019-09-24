package com.demo.booksInfo;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BooksInfoValidator extends Validator  {
	protected void validate(Controller controller) {
		//validateRequiredString("booksClass.className", "classNameMsg", "请输入类目名称!");
		//validateRequiredString("booksClass.classNum", "classNumMsg", "请输入分类号!");
	}

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		
	}
 
}
