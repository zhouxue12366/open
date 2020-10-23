package com.basic.framework.controller.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
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
	// private static String LINK_1 = "https://jx.618g.com/?url=";
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
		int platform = getInt("platform", 1);
		log.info(platform + "搜索" + mediaName);
		setAttr("platform", platform);
		setAttr("mediaName", mediaName);

		switch (platform) {
		case 1:// 腾讯视频爬虫
			int qqResult = getApiQQLive(mediaName);
			if(qqResult ==-1){
				render("/comm/404.html");
			}else{
				render("/qqlive/mediaList.html");
			}
			
			return;
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
		render("/mediaList.html");
	}

	private int getApiQQLive(String mediaName) {
		try {
			int type = 0;// 2:电影,4:电视剧
			String documentUrl = "https://v.qq.com/x/search/?q=" + mediaName + "&stag=0&smartbox_ab=";
			Document root = QQLiveHtmlUtils.getHtml(documentUrl, 1);
			Elements resultItem = root.select(".result_item");
			// 视频的id
			String liveDataId = resultItem.attr("data-id");

			Elements resultTabs = root.select("._playlist .result_tabs");
			Elements resultTabsItem = resultTabs.select(".item");
			int maxNum = 1000;
			// 有tab（1-50，50-100）这样的时候处理
			if (null != resultTabsItem && resultTabsItem.size() > 0) {
				for (Element item : resultTabsItem) {
					// 循环获取最大集数
					String dataRange = item.attr("data-range");
					String lastNum = dataRange.substring(dataRange.indexOf("-") + 1, dataRange.length());
					if (StringUtils.isBlank(lastNum)) {
						continue;
					}
					int lastNumber = Integer.parseInt(lastNum);
					if (lastNumber > maxNum) {
						maxNum = lastNumber;
					}
				}
				
				Elements resultEpisodeList = root.select("._playlist .result_episode_list");
				Elements resultEpisode = resultEpisodeList.select(".item");
				if (resultEpisode.size() > 0) {
					// 这里不能直接取最后一个,因为最后一个是收起,只能取倒数第二个为最大集数
					Element lastResultEpisode = resultEpisode.get(resultEpisode.size() - 2);
					String lastNum = lastResultEpisode.text();
					if (StringUtils.isNotBlank(lastNum)) {
						int lastNumber = Integer.parseInt(lastNum);
						maxNum = lastNumber;
					}
				}
			}

			// 获取视频图片
			Elements infos = root.select("._infos");
			String resultHref = documentUrl;
			if(null != infos.first()){
				Elements result = infos.first().select(".desc_more");
				resultHref = result.attr("href");
			}
			
			Document detail = QQLiveHtmlUtils.getHtml(resultHref, 1);
			if (null != detail) {
				Elements figure_pic = detail.select(".figure_pic");
				String imgSrc = figure_pic.first().attr("src");
				setAttr("imgSrc", imgSrc);
			}

			Elements modEpisode = detail.select(".mod_episode .item");
			if (null != modEpisode && modEpisode.size() > 0) {
				// 电视剧走这里
				type = 4;
				// 通过api请求获取到视频的json信息
				JSONObject resultObj = QQLiveHtmlUtils.getApiJson(liveDataId, type, maxNum);
				JSONObject playlistItem = resultObj.getJSONObject("PlaylistItem");
				if (null == playlistItem) {
					return -1;
				}
				JSONArray videoPlayList = playlistItem.getJSONArray("videoPlayList");
				setAttr("playlistItem", playlistItem);
				setAttr("videoPlayList", videoPlayList);
			} else {
				// 电影走这里
				type = 2;
				// 通过api请求获取到视频的json信息
				JSONObject resultObj = QQLiveHtmlUtils.getApiJson(liveDataId, type, maxNum);
				JSONObject playlistItem = resultObj.getJSONObject("PlaylistItem");
				if(null != playlistItem){
					JSONArray videoPlayList = playlistItem.getJSONArray("videoPlayList");
					setAttr("videoPlayList", videoPlayList);
				}
				
				setAttr("playlistItem", playlistItem);
			}
			setAttr("type", type);

		} catch (Exception e) {
			log.error("腾讯视频api请求异常:", e);
		}

		return 0;
	}

	/**
	 * 腾讯视频搜索爬虫(爬虫解析的html页面)
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

		if (null != detail) {
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
				Elements markPic = mod.getElementsByClass("mark_pic");
				String alt = "";
				if (null != markPic) {
					alt = markPic.attr("alt");
					if (null != alt && alt.contains("VIP")) {
						alt = "VIP";
					}
				}
				if (StringUtils.isNotBlank(alt)) {
					alt = "(" + alt + ")";
				}

				mod.getElementsByTag("a").attr("href", LINK_1 + href);
				Record r = new Record();
				r.set("id", text);
				r.set("alt", alt);
				r.set("href", LINK_1 + href);
				boolean flag = false;
				for (Record checkR : recordList) {
					if (checkR.get("id").equals(text) && checkR.get("alt").equals(alt)) {
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
		Elements albumList = root.select(".qy-search-result-album").select(".album-list");
		if (null != albumList && albumList.size() > 0) {
			Elements albumLinkList = null;
			for (int i = 0; i < albumList.size(); i++) {
				if (albumList.get(i).attr("style").contains("display:none;")) {
					albumLinkList = albumList.get(i).select(".album-item");
					break;
				}
			}

			for (Element album : albumLinkList) {
				Element a = album.getElementsByTag("a").get(0);
				String href = a.attr("href");
				String text = a.text();
				Elements alt = album.getElementsByTag("img");
				String altStr = alt.attr("alt");
				if (StringUtils.isNotBlank(altStr)) {
					altStr = "(" + altStr + ")";
				}

				Record r = new Record();
				r.set("id", text);
				r.set("alt", altStr);
				r.set("href", LINK_1 + https + href);
				boolean flag = false;
				for (Record checkR : recordList) {
					if (checkR.get("id").equals(text) && checkR.get("alt").equals(alt)) {
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
		// String documentUrl = "https://so.youku.com/search_video/q_" +
		// mediaName;
		Document root = DocumentToolkit.getDocument(documentUrl, 0);
		List<Record> recordList = new ArrayList<Record>();

		Element nuxt = root.getElementById("app");
		Elements modulelist = nuxt.select(".modulelist_s_body .common_container .hot-g-row .pack_pack_cover");
		if (null == modulelist || modulelist.size() <= 0) {
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
