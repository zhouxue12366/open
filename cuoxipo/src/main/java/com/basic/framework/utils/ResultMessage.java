package com.basic.framework.utils;

/**
 * 返回对象
 * @Title ResultMessage.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年3月31日 上午10:34:49
 * @version V1.0
 */
public class ResultMessage {

	private String message;
	private int code;
	private Object data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
