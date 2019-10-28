package com.m3u8.zg.controller.csgo;

import java.io.IOException;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.m3u8.zg.utils.HttpUtils;

/**
 * csgo游戏抓取的esports站点的新闻数据
 * 
 * @Title EsportsController.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月28日 下午4:21:07
 * @version V1.0
 */
public class EsportsController extends Controller {

	public void getList() {
		String url = "https://esports-cms.thescore.com/api/v2/rivers/csgo/news?page=1";
		String resultData = "";
		try {
			resultData = HttpUtils.get(url).body();
			Record record = new Record();
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderJson(resultData);
	}
	
	public void getDetail(){
		
	}
}
