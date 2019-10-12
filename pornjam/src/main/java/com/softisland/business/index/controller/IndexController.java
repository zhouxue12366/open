package com.softisland.business.index.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.softisland.business.spider.PornjamSpider;
import com.softisland.common.activemq.MsgProducer;
import com.softisland.common.model.Menu;
import com.softisland.common.templ.BaseController;

public class IndexController extends BaseController {
	PornjamSpider pornjamSpider = new PornjamSpider();
	MsgProducer msgProducer = new MsgProducer();

	public void index() {
		 pornjamSpider.httpGet("https://www.pornjam.com/top-rated/m/");
//		List<Videos> videos = Videos.dao.find(" select * from videos limit 100");
//		setAttr("videos", videos);
//		List<MostViewed> videos = MostViewed.dao.find(" select * from most_viewed limit 100");
//		setAttr("videos", videos);
		renderJson();
	}
	
	public void updateMenu(){
		 pornjamSpider.updateMenu();
		 renderJson();
	}
	
	public void updateSubMenu(){
		try {
			Menu menu = new Menu();
			List<Menu> menus = menu.dao().find("select * from menu where parent_menu_id in (1,2)");
			for(Menu model : menus){
				pornjamSpider.updateSubMenu(model);
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		renderJson("扫描结束........");
	}
	
	public void list(){
		render("/views/index.html");
	}
	
	public void getList(){
		String name = "张三";
		int sex =18;
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("sex", sex);
		renderJson(json.toJSONString());
	}
}
