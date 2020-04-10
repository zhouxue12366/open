package com.basic.framework.spider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.basic.framework.model.VideoTv;
import com.basic.framework.utils.QQLiveHtmlUtils;

public class QQLiveSpider {
	private static Logger log = Logger.getLogger(QQLiveSpider.class);

	/**
	 * 腾讯视频电视剧爬虫
	 * 
	 * @Title spiderQQLive
	 * @Description
	 * @param mediaName
	 * @since 2020年3月30日 下午3:01:32
	 */
	public static List<VideoTv> spiderQQLiveTv() {
		String documentUrl = "https://v.qq.com/channel/tv";
		Document root = QQLiveHtmlUtils.getHtml(documentUrl, 0);
		if (null == root) {
			return null;
		}
		Elements tvListItem = root.select("._quickopen_duration .mod_figure .list_item");
		// log.info(tvListItem);
		List<VideoTv> videoTvList = getVideoTv(tvListItem);
		return videoTvList;
	}

	private static List<VideoTv> getVideoTv(Elements tvListItem) {
		List<VideoTv> videoTvList = new ArrayList<VideoTv>();
		for (Element element : tvListItem) {
			Element figure = element.selectFirst(".figure");
			String tvHref = figure.attr("href");
			Element figurePic = element.selectFirst(".figure_pic");
			String figurePicSrc = figurePic.attr("src");
			String tvName = figurePic.attr("alt");

			Element figureDesc = element.selectFirst(".figure_desc");
			String tvTitle = "";
			if (null != figureDesc) {
				tvTitle = figureDesc.attr("title");
			}
			Element figureScore = element.selectFirst(".figure_score");
			String tvScore = null;
			if (null != figureScore) {
				tvScore = figureScore.text();
			}

			VideoTv tv = new VideoTv();
			tv.setPlayUrl(tvHref);
			tv.setImgUrl(figurePicSrc);
			String name = tvName;
			if (tvName.contains("[") && tvName.contains("]")) {
				name = tvName.substring(0, tvName.indexOf("["));
			}
			tv.setName(name);
			tv.setShowName(tvName);
			tv.setTitle(tvTitle);
			tv.setDescption(tvTitle);
			tv.setCreateTime(new Date());
			tv.setPlatform("qq");
			tv.setScore(tvScore);

			videoTvList.add(tv);
		}
		return videoTvList;
	}

	/**
	 * 腾讯视频电影爬虫
	 * 
	 * @Title spiderQQLiveMovie
	 * @Description
	 * @since 2020年4月10日 下午2:41:07
	 */
	public static List<VideoTv> spiderQQLiveMovie() {
		String documentUrl = "https://v.qq.com/channel/movie";
		Document root = QQLiveHtmlUtils.getHtml(documentUrl, 0);
		if (null == root) {
			return null;
		}
		Elements tvListItem = root.select("._quickopen_duration .mod_figure .list_item");
		// log.info(tvListItem);
		List<VideoTv> videoTvList = getVideoTv(tvListItem);
		return videoTvList;
	}
}
