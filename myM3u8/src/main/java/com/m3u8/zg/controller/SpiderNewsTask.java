package com.m3u8.zg.controller;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.m3u8.zg.utils.DocumentToolkit;

/**
 * 
 * @Title SpiderNewsTask.java
 * @Description 
 * @Company: 软岛
 * @author zg
 * @date 2019年12月18日 下午5:20:08
 * @version V1.0
 */
public class SpiderNewsTask {
	private static String URL = "http://www.gov.cn/zhengce/2019-12/15/content_5461432.htm";

	public static void main(String[] args) {
		Document root = DocumentToolkit.getDocument(URL,1);
		getNews(root);
	}

	public static void getNews(Document root) {
		if (null == root) {
			System.out.println("没有获取到数据");
			return;
		}
		Elements newsTitle = root.getElementsByClass("article oneColumn pub_border");
		Element newsContent = root.getElementById("UCAP-CONTENT");
		
		String title = newsTitle.get(0).getElementsByTag("h1").text();
		System.out.println("title:"+title);
		newsContent.getElementsByTag("p").removeAttr("style");
		newsContent.getElementsByTag("span").removeAttr("style");
		System.out.println("content:"+newsContent.html());
		
	}
}
