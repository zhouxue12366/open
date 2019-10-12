package com.softisland.business.index.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.softisland.business.spider.DocumentToolkit;
import com.softisland.common.templ.BaseController;

/**
 * 获取一个卖酒水站点的数据
 * @Title BeerController.java
 * @Description TODO
 * @Company: 软岛
 * @author zg
 * @date 2019年5月6日 下午4:05:34
 * @version V1.0
 */
public class BeerController extends BaseController{
	List<Map<String,String>>list=new ArrayList<Map<String,String>>();
	
	public void getAll(){
		for(int i=0;i<=4;i++){
			getWWW(i);
		}
		
		setAttr("result", list);
		renderJson();
	}
	
	public void getWWW(int j){
		Document root = DocumentToolkit.getDocument("https://www.1919.cn/search.html?sort=DEFAULT_SORT&page="+j+"&size=16&cat=337063315819859969_啤酒");
		Elements elements = root.getAllElements();
		Element element =elements.get(0).getElementById("productList");
		Elements li = element.getElementsByTag("li");
		
		
		for (int i = 0; i < li.size(); i++) {
			Map<String,String> result = new HashMap<String,String>();
			String product_detail=li.get(i).getElementsByClass("product-detail").text();
			String curr_price = li.get(i).getElementsByClass("ml-pri-curr").text();
			String mod_price = li.get(i).getElementsByClass("ml-pri").text();
			result.put("product_detail", product_detail);
			result.put("curr_price", curr_price);
			result.put("mod_price", mod_price);
			list.add(result);
			System.out.println(product_detail+"--------当前价格："+curr_price+"---原价:"+mod_price);	
		}
		
		
	}
}
