package com.basic.framework.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * @Title UserLocation.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年3月31日 下午2:11:25
 * @version V1.0
 */
public class UserLocation extends Model<UserLocation>{
	/**
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	
	public static final UserLocation dao = new UserLocation().dao();
	
}
