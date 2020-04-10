package com.general.admin.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSiteInfo<M extends BaseSiteInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 网站的名称
	 */
	public M setWebName(java.lang.String webName) {
		set("web_name", webName);
		return (M)this;
	}
	
	/**
	 * 网站的名称
	 */
	public java.lang.String getWebName() {
		return getStr("web_name");
	}

	/**
	 * 后台管理的名称
	 */
	public M setAdminName(java.lang.String adminName) {
		set("admin_name", adminName);
		return (M)this;
	}
	
	/**
	 * 后台管理的名称
	 */
	public java.lang.String getAdminName() {
		return getStr("admin_name");
	}

	/**
	 * 网站的logo地址
	 */
	public M setWebLogo(java.lang.String webLogo) {
		set("web_logo", webLogo);
		return (M)this;
	}
	
	/**
	 * 网站的logo地址
	 */
	public java.lang.String getWebLogo() {
		return getStr("web_logo");
	}

	/**
	 * 后台管理系统的logo地址
	 */
	public M setAdminLogo(java.lang.String adminLogo) {
		set("admin_logo", adminLogo);
		return (M)this;
	}
	
	/**
	 * 后台管理系统的logo地址
	 */
	public java.lang.String getAdminLogo() {
		return getStr("admin_logo");
	}

	/**
	 * 样式名字,方便以后换皮肤
	 */
	public M setStyleName(java.lang.String styleName) {
		set("style_name", styleName);
		return (M)this;
	}
	
	/**
	 * 样式名字,方便以后换皮肤
	 */
	public java.lang.String getStyleName() {
		return getStr("style_name");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}
