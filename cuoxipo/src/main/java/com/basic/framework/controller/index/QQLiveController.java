package com.basic.framework.controller.index;

import org.jsoup.nodes.Document;

import com.basic.framework.utils.QQLiveHtmlUtils;
import com.jfinal.core.Controller;

public class QQLiveController extends Controller{

	public void getHtml(){
		String documentUrl ="https://v.qq.com/x/cover/bzfkv5se8qaqel2.html?ptag=qqbrowser";
		Document root = QQLiveHtmlUtils.getHtml(documentUrl,1);
		renderHtml(root.html());
	}
	
	public void getJs(){
		String documentUrl ="https://v.qq.com/x/cover/bzfkv5se8qaqel2.html?ptag=qqbrowser";
//		String rootJs = QQLiveHtmlUtils.getJs(documentUrl);
//		set("rootJs", rootJs);
		render("/views/index.html");
	}
}
