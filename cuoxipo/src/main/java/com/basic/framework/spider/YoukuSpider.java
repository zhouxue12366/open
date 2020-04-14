package com.basic.framework.spider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.basic.framework.model.VideoTv;
import com.basic.framework.utils.DocumentToolkit;
import com.basic.framework.utils.QQLiveHtmlUtils;
import com.jfinal.plugin.activerecord.Record;

public class YoukuSpider {
	private static Logger log = Logger.getLogger(YoukuSpider.class);

	/**
	 * 腾讯视频电视剧爬虫
	 * 
	 * @Title spiderQQLive
	 * @Description
	 * @param mediaName
	 * @since 2020年3月30日 下午3:01:32
	 */
	public static List<VideoTv> getVideoList() {
		String documentUrl = "https://www.youku.com/";// + mediaName + "";
		// String documentUrl = "https://so.youku.com/search_video/q_" +
		// mediaName;
		Document root = DocumentToolkit.getDocument(documentUrl, 0);

		Element nuxt = root.getElementById("app");
		Elements modulelist = nuxt.select(".modulelist_s_body .common_container .hot-g-row .pack_pack_cover");
		// List<VideoTv> videoTvList = new ArrayList<VideoTv>();
		if (null == modulelist || modulelist.size() <= 0) {
			return null;
		} else {
			return getVideoTv(modulelist);
		}
	}

	private static List<VideoTv> getVideoTv(Elements modulelist) {
		List<VideoTv> videoTvList = new ArrayList<VideoTv>();
		String https = "https:";
		for (Element module : modulelist) {
			Element album = module.getElementsByTag("a").first();

			String tvHref = https + album.attr("href");
			Element figurePic = album.getElementsByTag("img").first();
			String figurePicSrc = https + figurePic.attr("src");
			String tvName = album.attr("title");
			String tvTitle = album.attr("title");

			VideoTv tv = new VideoTv();

			tv.setImgUrl(figurePicSrc);
			String name = tvName;
			if (tvName.contains("[") && tvName.contains("]")) {
				name = tvName.substring(0, tvName.indexOf("["));
			}
			tv.setName(name);
			tv.setPlayUrl("/youku/play/?name=" + name + "&playUrl=" +  tvHref+"&imgSrc="+figurePicSrc);
			tv.setShowName(tvName);
			tv.setTitle(tvTitle);
			tv.setDescption(tvTitle);
			tv.setCreateTime(new Date());
			tv.setPlatform("youku");
			tv.setScore("");

			videoTvList.add(tv);
		}
		return videoTvList;
	}

}
