package com.basic.framework.controller.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.basic.framework.interceptor.LoginInterceptor;
import com.basic.framework.model.VideoTv;
import com.basic.framework.spider.QQLiveSpider;
import com.basic.framework.utils.DocumentToolkit;
import com.basic.framework.utils.QQLiveHtmlUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @Title IndexController.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:20:26
 * @version V1.0
 */
public class IndexController extends Controller {
	private Logger log = Logger.getLogger(IndexController.class);
//	private static String LINK_1 = "https://jx.618g.com/?url=";
	private static String LINK_1 = "";

	@Clear(LoginInterceptor.class)
	public void index() {
		int type = getInt(0, 1);
		List<VideoTv> tvList = QQLiveSpider.spiderQQLiveTv();
		if (type == 2) {// 显示电影
			tvList = QQLiveSpider.spiderQQLiveMovie();
		} else {// 显示电视剧
			tvList = QQLiveSpider.spiderQQLiveTv();
		}

		set("type", type);
		set("tvList", tvList);
		render("/index.html");
	}
	
	public void app() {
		log.info("to app html......");
		render("/app.html");
	}

	public void preview() {
		log.info("to preview html......");
		render("/preview.html");
	}

	/**
	 * 文武要获取定位信息
	 * 
	 * @Title jiangying
	 * @Description
	 * @since 2020年4月1日 下午5:28:41
	 */
	@Clear(LoginInterceptor.class)
	public void jiangying() {
		log.info("to location html......");
		render("/location.html");
	}

	/**
	 * 
	 * @Title jiangying
	 * @Description
	 * @since 2020年4月13日 下午5:15:36
	 */
	@Clear(LoginInterceptor.class)
	public void jiangyingQQ() {
		log.info("to location html......");
		render("/location_qq.html");
	}

	/***
	 * 文武的查看定位信息
	 * 
	 * @Title wenwu
	 * @Description
	 * @since 2020年4月1日 下午5:27:43
	 */
	@Clear(LoginInterceptor.class)
	public void wenwu() {
		log.info("to location html......");
		Record record = Db.findFirst("select * from user_location order by id desc");
		JSONObject obj = JSONObject.parseObject(record.get("location"));
		setAttr("record", record);
		setAttr("xy", "[" + obj.getString("xy") + "]");
		render("/locationShow.html");
	}

	public void search() {
		// List<Record> recordList = spiderQQLive(get("mediaName"));
		// setAttr("recordList", recordList);
		String mediaName = get("mediaName");
		int platform = getInt("platform");
		log.info(platform + "搜索" + mediaName);
		switch (platform) {
		case 1:// 腾讯视频爬虫
			spiderQQLive(mediaName);
			break;
		case 2:// 爱奇艺爬虫
			spiderIqiyiLive(mediaName);
			break;
		case 3:// 优酷爬虫
			spiderYoukuLive(mediaName);
			break;
		case 4:// 芒果爬虫
			spiderMangguoLive(mediaName);
			break;
		default:// 默认走腾讯视频爬虫
			spiderQQLive(mediaName);
			break;
		}

		setAttr("mediaName", get("mediaName"));
		render("/mediaList.html");
	}

	/**
	 * 腾讯视频搜索爬虫
	 * 
	 * @Title spiderQQLive
	 * @Description
	 * @param mediaName
	 * @since 2020年3月30日 下午3:01:32
	 */
	private void spiderQQLive(String mediaName) {
		String documentUrl = "https://v.qq.com/x/search/?q=" + mediaName + "&stag=0&smartbox_ab=";
		Document root = QQLiveHtmlUtils.getHtml(documentUrl, 1);
		Elements infos = root.select("._infos");
		Elements result = infos.first().select(".desc_more");
		Document detail = QQLiveHtmlUtils.getHtml(result.attr("href"), 1);

		if(null != detail){
			Elements figure_pic = detail.select(".figure_pic");
			String imgSrc = figure_pic.first().attr("src");
			setAttr("imgSrc", imgSrc);
		}
		

		List<Record> recordList = new ArrayList<Record>();
		Elements modEpisode = detail.select(".mod_episode .item");

		if (null != modEpisode && modEpisode.size() > 0) {
			// 电视剧走这里
			for (Element mod : modEpisode) {
				String href = mod.getElementsByTag("a").attr("href");
				String text = mod.getElementsByTag("a").text();
				mod.getElementsByTag("a").attr("href", LINK_1 + href);
				Record r = new Record();
				r.set("id", text);
				r.set("href", LINK_1 + href);
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
		} else {
			// 电影走这里
			Elements playList = root.select("._playlist");
			if (null != playList && playList.size() > 0) {
				Element btnPrimary = playList.first().getElementsByTag("a").first();
				if (null != btnPrimary) {
					String href = btnPrimary.attr("href");
					Record play = new Record();
					play.set("id", "立即播放");
					play.set("href", LINK_1 + href);
					recordList.add(play);
				}
			}
		}

		setAttr("recordList", recordList);

		// render(modEpisode.html());
		// return recordList;
	}

	/**
	 * 爱奇艺爬虫
	 * 
	 * @Title spiderIqiyiLive
	 * @Description
	 * @param mediaName
	 * @since 2020年3月30日 下午3:46:33
	 */
	private void spiderIqiyiLive(String mediaName) {
		String documentUrl = "https://so.iqiyi.com/so/q_" + mediaName + "?source=input&sr=144056830036";
		Document root = DocumentToolkit.getDocument(documentUrl, 1);
		String https = "https:";

		Elements figure_pic = root.select(".qy-mod-link");
		String imgSrc = figure_pic.first().select(".qy-mod-cover").first().attr("src");
		setAttr("imgSrc", https + imgSrc);

		List<Record> recordList = new ArrayList<Record>();
		Elements albumList = root.select(".result-bottom").select(".album-list");
		if (null != albumList && albumList.size() > 0) {
			Elements albumLinkList = albumList.first().select(".album-link");
			for (Element album : albumLinkList) {
				String href = album.attr("href");
				String text = album.text();

				Record r = new Record();
				r.set("id", text);
				r.set("href", LINK_1 + https + href);
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
		} else {
			Elements playList = root.select(".result-bottom-pos");
			if (null != playList && playList.size() > 0) {
				Element btnPrimary = playList.first().getElementsByTag("a").first();
				if (null != btnPrimary) {
					String href = btnPrimary.attr("href");
					Record play = new Record();
					play.set("id", "立即播放");
					play.set("href", LINK_1 + https + href);
					recordList.add(play);
				}
			}
		}
		setAttr("recordList", recordList);
	}

	/**
	 * 优酷爬虫
	 * 
	 * @Title spiderYoukuLive
	 * @Description
	 * @param mediaName
	 * @since 2020年3月30日 下午3:46:48
	 */
	private void spiderYoukuLive(String mediaName) {
		String documentUrl = "https://www.youku.com/";// + mediaName + "";
//		String documentUrl = "https://so.youku.com/search_video/q_" + mediaName;
		Document root = DocumentToolkit.getDocument(documentUrl, 0);
		List<Record> recordList = new ArrayList<Record>();

		Element nuxt = root.getElementById("app");
		Elements modulelist = nuxt.select(".modulelist_s_body .common_container .hot-g-row .pack_pack_cover");
		if(null == modulelist || modulelist.size() <= 0){
			setAttr("recordList", recordList);
			return;
		}
		String link_2 = "https://api.sigujx.com/?url=";
		if (null != modulelist && modulelist.size() > 0) {
			for (Element module : modulelist) {
				Element album = module.getElementsByTag("a").first();
				if (album.attr("title").contains(mediaName)) {
					String href = album.attr("href");
					String text = album.text();

					Record r = new Record();
					r.set("id", text);
					r.set("href", link_2 + href);
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
			}

		} else {
			if (null != modulelist && modulelist.size() > 0) {
				Element btnPrimary = modulelist.first().getElementsByTag("a").first();
				if (null != btnPrimary) {
					String href = btnPrimary.attr("href");
					Record play = new Record();
					play.set("id", "立即播放");
					play.set("href", link_2 + href);
					recordList.add(play);
				}
			}
		}
		setAttr("recordList", recordList);
	}

	/**
	 * 芒果爬虫
	 * 
	 * @Title spiderMangguoLive
	 * @Description
	 * @param mediaName
	 * @since 2020年3月30日 下午3:50:12
	 */
	private void spiderMangguoLive(String mediaName) {
		String documentUrl = "https://so.mgtv.com/so?k=" + mediaName + "&lastp=v_progdtl";
		Document root = DocumentToolkit.getDocument(documentUrl, 1);

	}
}
