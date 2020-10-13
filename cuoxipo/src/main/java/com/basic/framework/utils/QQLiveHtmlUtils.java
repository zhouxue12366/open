package com.basic.framework.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;
import com.basic.framework.controller.index.IndexController;

/**
 * 腾讯视频的工具类
 * 
 * @Title QQLiveHtmlUtils.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:20:03
 * @version V1.0
 */
public class QQLiveHtmlUtils {
	private static Logger log = Logger.getLogger(QQLiveHtmlUtils.class);

	private static String API_URL = "https://s.video.qq.com/get_playsource?";

	/**
	 * 获取视频页面html文件
	 * 
	 * @Title openHtml
	 * @Description
	 * @param documentUrl
	 * @return
	 * @since 2019年10月12日 下午5:46:08
	 */
	public static Document getHtml(String documentUrl, int method) {
		Document root = DocumentToolkit.getDocument(documentUrl, method);
		// System.out.println(root);
		return root;
	}

	/**
	 * 通过腾讯json接口请求获取数据
	 * 
	 * @Title getApiJson
	 * @Description
	 * @param id
	 *            视频id
	 * @param type:2=电影,4=电视剧
	 * @param maxNum
	 *            最后一集
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @since 2020年10月13日 上午9:24:58
	 */
	public static JSONObject getApiJson(String id, int type, int maxNum) throws ClientProtocolException, IOException {
		String idStr = "id=" + id;
		String param = "&plat=2&type=" + type + "&data_type=2&video_type=3&range=1-" + maxNum + "&plname=qq&otype=json&num_mod_cnt=20&callback=_jsonp_2_9fb6&_t=1595406976612";
		String url = API_URL + idStr + param;
		log.info("腾讯视频api请求url地址:" + url);
		String videoStr = HttpUtil.sendGetData(url, "UTF-8");
		videoStr = videoStr.substring(videoStr.indexOf("(") + 1, videoStr.lastIndexOf(")"));
		JSONObject obj = JSONObject.parseObject(videoStr);
		return obj;
	}

	/**
	 * 获取视频页面js变量
	 * 
	 * @Title getJs
	 * @Description
	 * @param documentUrl
	 * @return
	 * @since 2019年10月12日 下午5:46:22
	 */
	public static String getJs(String documentUrl) {
		String rootJs = DocumentToolkit.getJSVariable(documentUrl, "VIDEO_INFO");
		System.out.println(rootJs);
		return rootJs;
	}

	/**
	 * 获取m3u8的请求参数和地址
	 * 
	 * @Title getM3u8Url
	 * @Description
	 * @return
	 * @since 2019年10月12日 下午5:46:33
	 */
	public static String getM3u8Url() {
		// 腾讯的代理地址
		String proxyhttp = "https://vi.l.qq.com/proxyhttp";
		// HttpUtil.sendPostDataByMap(proxyhttp, json, header);
		return "";
	}
}
