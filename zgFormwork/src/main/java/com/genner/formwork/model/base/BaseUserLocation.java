package com.genner.formwork.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUserLocation<M extends BaseUserLocation<M>> extends Model<M> implements IBean {

	/**
	 * 主键
	 */
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * 主键
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 定位获取的信息(JSON)
	 */
	public M setLocation(java.lang.String location) {
		set("location", location);
		return (M)this;
	}
	
	/**
	 * 定位获取的信息(JSON)
	 */
	public java.lang.String getLocation() {
		return getStr("location");
	}

	/**
	 * html地图信息
	 */
	public M setLocationHtml(java.lang.String locationHtml) {
		set("location_html", locationHtml);
		return (M)this;
	}
	
	/**
	 * html地图信息
	 */
	public java.lang.String getLocationHtml() {
		return getStr("location_html");
	}

	/**
	 * 创建时间
	 */
	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
