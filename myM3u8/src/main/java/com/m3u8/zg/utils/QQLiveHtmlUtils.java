package com.m3u8.zg.utils;

import org.jsoup.nodes.Document;

public class QQLiveHtmlUtils {

	public static Document openHtml(String documentUrl){
		Document root = DocumentToolkit.getDocument(documentUrl, 1);
		System.out.println(root);
		return root;
	}
	
	public static String getJs(String documentUrl){
		String rootJs = DocumentToolkit.getJSVariable(documentUrl, "COVER_INFO");
		System.out.println(rootJs);
		return rootJs;
	}
	
	public static String getM3u8Url(){
		//腾讯的代理地址
		String proxyhttp = "https://vi.l.qq.com/proxyhttp";
//		HttpUtil.sendPostDataByMap(proxyhttp, json, header);
		return "";
	}
}
