package com.basic.framework.controller.youku;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.basic.framework.controller.BaseController;
import com.basic.framework.interceptor.LoginInterceptor;
import com.basic.framework.model.VideoTv;
import com.basic.framework.spider.YoukuSpider;
import com.basic.framework.utils.DocumentToolkit;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Record;

public class YoukuController extends BaseController {

	@Clear(LoginInterceptor.class)
	public void index() {
		int type = getInt(0, 1);
		List<VideoTv> tvList = YoukuSpider.getVideoList();
		if (type == 2) {// 显示电影
			tvList = YoukuSpider.getVideoList();
		} else {// 显示电视剧
			tvList = YoukuSpider.getVideoList();
		}

		set("tvList", tvList);
		render("/index.html");
	}

	/**
	 * 优酷播放页
	 * @Title play
	 * @Description   
	 * @since 2020年4月14日 下午2:10:03
	 */
	@Clear(LoginInterceptor.class)
	public void play() {
		String mediaName = get("name", "");
		String imgSrc = get("imgSrc", "");
		String playUrl = getPara("playUrl");
		set("mediaName", mediaName);
		set("imgSrc", imgSrc);

		Document root = DocumentToolkit.getDocument(playUrl, 0);
		List<Record> recordList = new ArrayList<Record>();

		Element nuxt = root.selectFirst(".listbox .anthology-content");
		Elements modulelist = nuxt.getElementsByTag("a");
		String link_2 = "https://api.sigujx.com/?url=";
		String url8090 = "https://www.8090g.cn/jiexi/?url=";
		if (null != modulelist && modulelist.size() > 0) {
			for (Element module : modulelist) {
				// if (mediaName.contains(module.attr("title"))) {
				String href = module.attr("href");
				String text = module.text();

				Record r = new Record();
				r.set("id", text);
				r.set("href", link_2 + href);
				r.set("source_href", href);
				boolean flag = false;
				for (Record checkR : recordList) {
					if (checkR.get("id").equals(text)) {
						flag = true;
					}
				}
				if (!flag) {
					recordList.add(r);
				}
			}
			// }

		} else {
			if (null != modulelist && modulelist.size() > 0) {
				Element btnPrimary = modulelist.first().getElementsByTag("a").first();
				if (null != btnPrimary) {
					String href = btnPrimary.attr("href");
					Record play = new Record();
					play.set("id", "立即播放");
					play.set("href", link_2 + href);
					play.set("source_href", href);
					recordList.add(play);
				}
			}
		}
		
		setAttr("url8090", url8090);
		setAttr("recordList", recordList);

		render("/youku/play.html");
	}

}
