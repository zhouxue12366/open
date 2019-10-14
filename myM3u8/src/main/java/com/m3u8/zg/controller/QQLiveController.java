package com.m3u8.zg.controller;

import org.jsoup.nodes.Document;

import com.jfinal.core.Controller;
import com.m3u8.zg.utils.QQLiveHtmlUtils;

public class QQLiveController extends Controller{

	public void getHtml(){
		String documentUrl ="https://v.qq.com/x/cover/bzfkv5se8qaqel2.html?ptag=qqbrowser";
		Document root = QQLiveHtmlUtils.openHtml(documentUrl);
		renderHtml(root.html());
	}
	
	public void getJs(){
		String documentUrl ="https://v.qq.com/x/cover/bzfkv5se8qaqel2.html?ptag=qqbrowser";
//		String rootJs = QQLiveHtmlUtils.getJs(documentUrl);
//		set("rootJs", rootJs);
		render("/views/index.html");
	}
}
