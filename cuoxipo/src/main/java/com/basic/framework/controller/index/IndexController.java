package com.basic.framework.controller.index;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.basic.framework.utils.DocumentToolkit;
import com.basic.framework.utils.QQLiveHtmlUtils;
import com.jfinal.core.Controller;
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
	private static String LINK_1 ="https://jx.618g.com/?url=";

	public void index() {
		render("/views/index.html");
	}

	public void app() {
		System.out.println("to app html......");
		render("/views/app.html");
	}
	public void preview() {
		System.out.println("to preview html......");
		render("/views/preview.html");
	}

	public void location() {
		System.out.println("to location html......");
		render("/views/location.html");
	}

	public void search() {
//		List<Record> recordList = spiderQQLive(get("mediaName"));
//		setAttr("recordList", recordList);
		String mediaName = get("mediaName");
		int platform = getInt("platform");
		System.out.println(platform + "搜索" + mediaName);
		switch (platform) {
		case 1://腾讯视频爬虫
			spiderQQLive(mediaName);
			break;
		case 2://爱奇艺爬虫
			spiderIqiyiLive(mediaName);
			break;
		case 3://优酷爬虫
			spiderYoukuLive(mediaName);
			break;
		case 4://芒果爬虫
			spiderMangguoLive(mediaName);
			break;
		default://默认走腾讯视频爬虫
			spiderQQLive(mediaName);
			break;
		}
		
		setAttr("mediaName", get("mediaName"));
		render("/views/mediaList.html");
	}

	/**
	 * 腾讯视频爬虫
	 * @Title spiderQQLive
	 * @Description  
	 * @param mediaName 
	 * @since 2020年3月30日 下午3:01:32
	 */
	private void spiderQQLive(String mediaName) {
		String documentUrl = "https://v.qq.com/x/search/?q=" + mediaName + "&stag=0&smartbox_ab=";
		Document root = QQLiveHtmlUtils.openHtml(documentUrl);
		Elements infos = root.select("._infos");
		Elements result = infos.first().select(".desc_more");
		Document detail = QQLiveHtmlUtils.openHtml(result.attr("href"));
		
		Elements figure_pic= detail.select(".figure_pic");
		String imgSrc = figure_pic.first().attr("src");
		setAttr("imgSrc", imgSrc);
		
		List<Record> recordList = new ArrayList<Record>();
		Elements modEpisode = detail.select(".mod_episode .item");
		
		if(null != modEpisode && modEpisode.size() > 0){
			//电视剧走这里
			for (Element mod : modEpisode) {
				String href = mod.getElementsByTag("a").attr("href");
				String text = mod.getElementsByTag("a").text();
				mod.getElementsByTag("a").attr("href", LINK_1 + href);
				Record  r= new Record();
				r.set("id", text);
				r.set("href", LINK_1 + href);
				boolean flag = false;
				for(Record checkR :recordList){
					if(checkR.get("id").equals(text)){
						flag = true;
					}
				}
				if(!flag){
					recordList.add(r);
				}
			}
		}else{
			//电影走这里
			Elements playList =root.select("._playlist");
			if(null != playList && playList.size() > 0){
				Element btnPrimary = playList.first().getElementsByTag("a").first();
				if(null != btnPrimary){
					String href = btnPrimary.attr("href");
					Record  play = new Record();
					play.set("id", "立即播放");
					play.set("href", LINK_1 + href);
					recordList.add(play);
				}
			}
		}
		
		setAttr("recordList", recordList);
		
//		render(modEpisode.html());
//		return recordList;
	}
	
	/**
	 * 爱奇艺爬虫
	 * @Title spiderIqiyiLive
	 * @Description  
	 * @param mediaName 
	 * @since 2020年3月30日 下午3:46:33
	 */
	private void spiderIqiyiLive(String mediaName){
		String documentUrl = "https://so.iqiyi.com/so/q_"+mediaName+"?source=input&sr=144056830036";
		Document root = DocumentToolkit.getDocument(documentUrl, 1);
		String https = "https:";
		
		Elements figure_pic= root.select(".qy-mod-link");
		String imgSrc = figure_pic.first().select(".qy-mod-cover").first().attr("src");
		setAttr("imgSrc", https + imgSrc);
		
		List<Record> recordList = new ArrayList<Record>();
		Elements albumList = root.select(".album-list");
		if(null != albumList && albumList.size() >0){
			Elements albumLinkList = albumList.first().select(".album-link");
			for (Element album : albumLinkList) {
				String href = album.attr("href");
				String text = album.text();
				
				Record  r= new Record();
				r.set("id", text);
				r.set("href", LINK_1 + href);
				boolean flag = false;
				for(Record checkR :recordList){
					if(checkR.get("id").equals(text)){
						flag = true;
					}
				}
				if(!flag){
					recordList.add(r);
				}
			}
		}else{
			
			Elements playList = root.select(".result-bottom-pos");
			if(null != playList && playList.size() > 0){
				Element btnPrimary = playList.first().getElementsByTag("a").first();
				if(null != btnPrimary){
					String href = btnPrimary.attr("href");
					Record  play = new Record();
					play.set("id", "立即播放");
					play.set("href", LINK_1 + https +href);
					recordList.add(play);
				}
			}
		}
		setAttr("recordList", recordList);
	}
	
	/**
	 * 优酷爬虫
	 * @Title spiderYoukuLive
	 * @Description  
	 * @param mediaName 
	 * @since 2020年3月30日 下午3:46:48
	 */
	private void spiderYoukuLive(String mediaName){
		String documentUrl = "https://so.iqiyi.com/so/q_"+mediaName+"?source=input&sr=144056830036";
		Document root = DocumentToolkit.getDocument(documentUrl, 1);
		
	}
	
	/**
	 * 芒果爬虫
	 * @Title spiderMangguoLive
	 * @Description  
	 * @param mediaName 
	 * @since 2020年3月30日 下午3:50:12
	 */
	private void spiderMangguoLive(String mediaName){
		String documentUrl = "https://so.iqiyi.com/so/q_"+mediaName+"?source=input&sr=144056830036";
		Document root = DocumentToolkit.getDocument(documentUrl, 1);
		
	}
}