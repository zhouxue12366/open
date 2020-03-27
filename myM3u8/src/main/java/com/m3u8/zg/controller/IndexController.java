package com.m3u8.zg.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.m3u8.zg.utils.QQLiveHtmlUtils;

/**
 * 
 * @Title IndexController.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:20:26
 * @version V1.0
 */
public class IndexController extends Controller {

	public void index() {
		render("/views/index.html");
	}

	public void app() {
		System.out.println("to app html......");
		render("/views/app.html");
	}

	public void location() {
		System.out.println("to location html......");
		render("/views/location.html");
	}

	public void search() {
		System.out.println(get("platform") + "搜索" + get("mediaName"));
//		List<Record> recordList = spiderQQLive(get("mediaName"));
//		setAttr("recordList", recordList);
		spiderQQLive(get("mediaName"));
		setAttr("mediaName", get("mediaName"));
		render("/views/mediaList.html");
	}

	private void spiderQQLive(String mediaName) {
		String documentUrl = "https://v.qq.com/x/search/?q=" + mediaName + "&stag=0&smartbox_ab=";
		Document root = QQLiveHtmlUtils.openHtml(documentUrl);
		Elements infos = root.select("._infos");
		Elements result = infos.first().select(".desc_more");
		Document detail = QQLiveHtmlUtils.openHtml(result.attr("href"));
		
		Elements figure_pic= detail.select(".figure_pic");
		String imgSrc = figure_pic.first().attr("src");
		setAttr("imgSrc", imgSrc);
		
		List<Record> recordList = new ArrayList<Record>();
		Elements modEpisode = detail.select(".mod_episode .item");
		for (Element mod : modEpisode) {
			String href = mod.getElementsByTag("a").attr("href");
			String text = mod.getElementsByTag("a").text();
			mod.getElementsByTag("a").attr("href", "https://jx.618g.com/?url=" + href);
			Record  r= new Record();
			r.set("id", text);
			r.set("href", "https://jx.618g.com/?url=" + href);
			boolean flag = false;
			for(Record checkR :recordList){
				if(checkR.get("id").equals(text)){
					flag = true;
				}
			}
			if(!flag){
				recordList.add(r);
			}
		}
		setAttr("recordList", recordList);
		
//		render(modEpisode.html());
//		return recordList;
	}
}
