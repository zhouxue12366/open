package com.basic.framework.utils;

import org.jsoup.nodes.Document;
/**
 * 腾讯视频的工具类
 * @Title QQLiveHtmlUtils.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:20:03
 * @version V1.0
 */
public class QQLiveHtmlUtils {

	/**
	 * 获取视频页面html文件
	 * @Title openHtml
	 * @Description  
	 * @param documentUrl
	 * @return 
	 * @since 2019年10月12日 下午5:46:08
	 */
	public static Document openHtml(String documentUrl){
		Document root = DocumentToolkit.getDocument(documentUrl, 1);
//		System.out.println(root);
		return root;
	}
	
	/**
	 * 获取视频页面js变量
	 * @Title getJs
	 * @Description  
	 * @param documentUrl
	 * @return 
	 * @since 2019年10月12日 下午5:46:22
	 */
	public static String getJs(String documentUrl){
		String rootJs = DocumentToolkit.getJSVariable(documentUrl, "VIDEO_INFO");
		System.out.println(rootJs);
		return rootJs;
	}
	
	/**
	 * 获取m3u8的请求参数和地址
	 * @Title getM3u8Url
	 * @Description  
	 * @return 
	 * @since 2019年10月12日 下午5:46:33
	 */
	public static String getM3u8Url(){
		//腾讯的代理地址
		String proxyhttp = "https://vi.l.qq.com/proxyhttp";
//		HttpUtil.sendPostDataByMap(proxyhttp, json, header);
		return "";
	}
}
