package com.softisland.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMenu<M extends BaseMenu<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setParentMenuId(java.lang.String parentMenuId) {
		set("parent_menu_id", parentMenuId);
		return (M)this;
	}
	
	public java.lang.String getParentMenuId() {
		return getStr("parent_menu_id");
	}

	public M setHref(java.lang.String href) {
		set("href", href);
		return (M)this;
	}
	
	public java.lang.String getHref() {
		return getStr("href");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setSubMenuId(java.lang.String subMenuId) {
		set("sub_menu_id", subMenuId);
		return (M)this;
	}
	
	public java.lang.String getSubMenuId() {
		return getStr("sub_menu_id");
	}

}