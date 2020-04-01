package com.basic.framework.controller.user;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.basic.framework.controller.BaseController;
import com.basic.framework.interceptor.LoginInterceptor;
import com.basic.framework.utils.Constants;
import com.basic.framework.utils.MD5;
import com.basic.framework.utils.ResultMessage;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @Title UserController.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年3月31日 下午3:36:02
 * @version V1.0
 */
public class UserController extends BaseController {
	
	/**
	 * 
	 * @Title toRegister
	 * @Description   
	 * @since 2020年3月31日 下午3:35:52
	 */
	@Clear(LoginInterceptor.class)
	public void toRegister(){
		render("/user/register.html");
	}
	
	/**
	 * 
	 * @Title register
	 * @Description   
	 * @since 2020年3月31日 下午3:38:32
	 */
	@Clear(LoginInterceptor.class)
	public void register() {
		ResultMessage result = new ResultMessage();
		String account = get("account");
		String password = get("password");
		String repassword = get("repassword");
		if(StringUtils.isBlank(account)){
			result.setCode(400);
			result.setMessage("注册失败,手机号不能为空");
			renderJson(result);
			return;
		}
		if(StringUtils.isBlank(password)){
			result.setCode(400);
			result.setMessage("注册失败,密码不能为空");
			renderJson(result);
			return;
		}
		
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
		Matcher m = Pattern.compile(regex).matcher(account);
		if(!m.matches()){
			result.setCode(400);
			result.setMessage("注册失败,手机号格式错误!");
			renderJson(result);
			return;
		}else if(!password.equalsIgnoreCase(repassword)){
			result.setCode(400);
			result.setMessage("注册失败,密码不一致");
			renderJson(result);
			return;
		}
		
		password = MD5.sign(password, MD5.KEY, MD5.CHARSET);
		Record userInfo = Db.findFirst("select * from user_info where account = ?", account );
		if (null != userInfo) {
			setSessionAttr(Constants.login_User_Id, userInfo.get("id"));
			setSessionAttr(Constants.login_Account, userInfo.get("account"));
			result.setCode(600);
			result.setMessage("注册失败,账号已存在");
			renderJson(result);
		} else {
			userInfo = new Record();
			userInfo.set("account", account);
			userInfo.set("nick_name", account);
			userInfo.set("phone", account);
			userInfo.set("password", password);
			userInfo.set("vip", 0);
			userInfo.set("status", 0);
			userInfo.set("register_time", new Date());
			userInfo.set("create_time", new Date());
			Db.save("user_info", userInfo);
			// setSessionAttr("loginUserId", null);
			result.setCode(200);
			result.setMessage("注册成功");
			renderJson(result);
		}
		
	}
	
	/**
	 * 
	 * @Title toLogin
	 * @Description   
	 * @since 2020年3月31日 下午3:35:55
	 */
	@Clear(LoginInterceptor.class)
	public void toLogin() {
		render("/user/login.html");
	}

	/**
	 * 
	 * @Title login
	 * @Description   
	 * @since 2020年3月31日 下午3:35:58
	 */
	@Clear(LoginInterceptor.class)
	public void login() {
		String account = get("account");
		String password = get("password");
		ResultMessage result = new ResultMessage();
		password = MD5.sign(password, MD5.KEY, MD5.CHARSET);
//		Record userInfo = Db.findFirst("select * from user_info where account = ? and password = ?", account ,password);
		//测试用下面的,不跑数据库
		Record userInfo = null;
		if("13677690703".equals(account) || "15223461428".equals(account)){
			userInfo = new Record();
			userInfo.set("id", 1);
			userInfo.set("account", account);
			userInfo.set("vip", 12);
		}
		
		if (null != userInfo) {
			setSessionAttr(Constants.login_User_Id, userInfo.get("id"));
			setSessionAttr(Constants.login_Account, userInfo.get("account"));
			setSessionAttr(Constants.login_vip, userInfo.get("vip"));
			result.setCode(200);
			result.setMessage("login success");
			renderJson(result);
		} else {
			// setSessionAttr("loginUserId", null);
			result.setCode(100);
			result.setMessage("login fail");
			renderJson(result);
		}
		
	}

	/**
	 * 退出登陆
	 * @Title loginOut
	 * @Description   
	 * @since 2020年3月31日 下午2:59:59
	 */
	public void loginOut() {
		setSessionAttr(Constants.login_User_Id, null);
		ResultMessage result = new ResultMessage();
		result.setCode(200);
		result.setMessage("login success");
		renderJson(result);
	}

	@Clear(LoginInterceptor.class)
	public void saveLocation(){
		String location = get("location");
		String locationHtml = get("locationHtml");
		Record record = new Record();
		record.set("location", location);
		record.set("location_html", locationHtml);
		record.set("create_time", new Date());
		boolean saveResult = Db.save("user_location", record);
		ResultMessage result = new ResultMessage();
		result.setCode(500);
		result.setMessage("save fial");
		if(saveResult){
			result.setCode(200);
			result.setMessage("save success");
		}
		renderJson(result);
	}
	
	/**
	 * 查询是否有新的定位记录
	 * @Title refreshLocation
	 * @Description   
	 * @since 2020年4月1日 下午5:47:29
	 */
	@Clear(LoginInterceptor.class)
	public void refreshLocation(){
		Integer id = getInt("id");
		Record record = Db.findFirst("select * from user_location where id = ?",id+1);
		ResultMessage result = new ResultMessage();
		if(null == record){
			result.setCode(500);
		}else{
			result.setCode(200);
		}
		renderJson(result);
	}
	
}
