package com.demo.booksUser;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.demo.util.BASE64CodeUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

public class BooksUserController extends Controller {
	public void index() {

	}

	@Clear
	public void login() throws Exception {
		String username = getPara("username");
		String password = getPara("password");
		List<BooksUser> user1 = BooksUser.me.findByUsername(username);
		if (user1.size() == 0) {
			renderJson("errorMsg", "用户名不存在");
			return;
		}
		password = BASE64CodeUtil.encryptBASE64(password.getBytes()); // 加密
		System.out.println("password:" + password);
		List<BooksUser> user2 = BooksUser.me.findByUsernameAndPassword(username, password);
		if (user2.size() == 0) {
			renderJson("errorMsg", "用户名或密码错误");
			return;
		}

		// 获得HttpSession对象
		HttpSession session = getRequest().getSession();
		// 设置session对象5分钟失效
		session.setMaxInactiveInterval(5 * 60);
		session.setAttribute("libraryCode", getRandom());
		renderJson("errorMsg", "登录成功");
	}

	// 获取随机数
	public String getRandom() {
		String result = "";
		for (int i = 0; i < 10; i++) {
			int intVal = (int) (Math.random() * 26 + 97);
			result = result + (char) intVal;
		}
		return result;
	}

}
