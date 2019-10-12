package com.softisland.business.spider;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softisland.business.index.service.MostViewedService;
import com.softisland.common.activemq.MsgConsumer;
import com.softisland.common.activemq.MsgProducer;
import com.softisland.common.model.Menu;
import com.softisland.common.model.MostViewed;

public class PornjamSpider {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgConsumer.class);

	MostViewedService mostViewedService = new MostViewedService();

	MsgProducer msgProducer = new MsgProducer();

	private static final String WWW = "https://www.pornjam.com";
	// private static final String rootUrl =https://www.pornjam.com/top-rated/m/
	// "https://www.pornjam.com/top-rated/m/";

	// public static void main(String[] args) {
	// Document root = DocumentToolkit.getDocumentBy(rootUrl, "UTF-8");
	// root.getAllElements();
	// }

	public void updateMenu() {
		Document root = DocumentToolkit.getDocument(WWW + "/top-rated/m/");
		Elements elements = root.getAllElements();
		for (Element element : elements.next()) {
			Element menuLast = element.getElementsByClass("menu").last();
			if (null == menuLast) {
				LOGGER.info("网站顶部菜单扫描结束!");
				break;
			}
			Elements menus = menuLast.getElementsByTag("a");
			for (Element menu : menus) {
				Elements a = menu.getElementsByTag("a");
				if (null == a || a.size() <= 0) {// 不是a标签的过滤掉
					continue;
				}
				String href = a.attr("href");
				String id = a.attr("id");
				String title = a.attr("title");
				Menu isExist = Menu.dao.findFirst(" select * from menu where name = ?", title);
				if (null != isExist) {
					LOGGER.info("菜单已存在不录入...");
					break;
				}
				Menu dao = new Menu();
				dao.setHref(href);
				dao.setName(title);
				dao.setSubMenuId(id);
				dao.save();
				LOGGER.info(href + "---" + id + "===" + title);
			}
		}

	}

	public void updateSubMenu(Menu model) {
		Document root = DocumentToolkit.getDocument(WWW + model.getHref());
		Elements elements = root.getAllElements();
		for (Element element : elements.next()) {
			Elements menuLasts = element.getElementsByClass("ordenar-categorias ordenar-index");
			if (null == menuLasts || menuLasts.size() <= 0) {
				LOGGER.info("网站子菜单扫描结束!");
				break;
			}
			// Elements menus = menuLasts.getElementsByTag("a");
			for (Element menu : menuLasts) {
				Elements as = menu.getElementsByTag("a");
				if (null == as || as.size() <= 0) {// 不是a标签的过滤掉
					continue;
				}
				for (Element a : as) {
					String href = a.attr("href");
					// String id = a.attr("id");
					String title = a.attr("title");
					Menu isExist = Menu.dao.findFirst(" select * from menu where name = ? and href = ? ", title, href);
					if (null != isExist) {
						LOGGER.info("子菜单已存在,不录入..." + title);
						continue;
					}
					Menu dao = new Menu();
					dao.setHref(href);
					dao.setName(title);
					dao.setParentMenuId(model.getId() + "");
					dao.save();
					LOGGER.info(href + "---" + title);
				}
			}
		}

	}

	public void httpGet(String rootUrl) {
		// String resultJson = HttpKit.get(rootUrl);
		int condition = 1;
		do {
			if (condition <= -1) {// 结束当前页面的分页
				break;
			}
			String url = WWW + rootUrl;
			if (condition >= 2) {
				url = WWW + rootUrl + "page" + condition + ".html";
			}
			Document root = DocumentToolkit.getDocument(url);
			if (null == root) {
				break;
			}
			condition = next(root.getAllElements(), condition);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (condition >= 1);
		LOGGER.info("爬取数据" + condition + "--end......" + rootUrl);
	}

	public int next(Elements elements, int condition) {
		for (Element element : elements.next()) {
			if (null == element) {
				LOGGER.info("没有");
				condition = -1;
				break;
			}
			Elements muestraCanals = element.getElementsByClass("muestra-canal");
			if (null == muestraCanals) {
				LOGGER.info("未找到首节点,程序返回false");
				return 0;
			}
			for (Element muestraCanal : muestraCanals.next()) {
				if (null == muestraCanal) {
					break;
				}
				Elements thumb = muestraCanal.getElementsByClass("thumb");
				String href = thumb.attr("href");
				if (null == thumb.first()) {
					LOGGER.info("没有获取到图片结束本次循环;");
					break;
				}
				Elements img = thumb.first().getElementsByClass("thumbs-changer");
				String src = img.attr("src");
				String oldId = img.attr("id");
				MostViewed record = mostViewedService.getById(oldId);
				if (null != record) {
					LOGGER.info(oldId + "已存在,不用再次循环......");
					continue;
				}

				LOGGER.info("正在爬取数据:href = " + href + ",    src = " + src);
				MostViewed model = new MostViewed();
				model.setImgSrc(src);
				model.setOldId(oldId);
				model.setHtmlUrl(href);
				boolean result = mostViewedService.save(model);
				LOGGER.info("保存基本信息:" + result + "!!!!!!!!!!");
				// 发mq
				// try {
				//// msgProducer.send(WWW + href);
				// } catch (JMSException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// Document root = DocumentToolkit.getDocument(WWW+href);
			}
		}
		return condition += 1;
	}

	private MostViewed httpVideoDetail(String url) throws Exception {
		Document root = DocumentToolkit.getDocument(url);
		if (null == root) {
			return null;
		}
		Element videoDIV = root.getElementById("videoDIV");
		Elements videos = videoDIV.getElementsByTag("video");
		Elements sources = videoDIV.getElementsByTag("source");

		MostViewed model = new MostViewed();
		model.setPlayImgSrc(videos.first().attr("poster"));
		model.setTagVideo(videos.first().toString());
		model.setName(videos.attr("data-stats-video-name"));
		model.setPlayUrl(sources.attr("src"));

		return model;
	}

	public int updateVideo() {
		int result = 0;
		List<MostViewed> mostVieweds = mostViewedService.getByUpdateStatus();
		if(null == mostVieweds || mostVieweds.size()<=0){
			LOGGER.info("没有可更新的视频...");
			return 0;
		}
		for (MostViewed dao : mostVieweds) {
			try {
				String url = WWW + dao.getHtmlUrl();
				MostViewed model = this.httpVideoDetail(url);
				model.setId(dao.getId());
				model.setUpdateStatus(1l);
				boolean flag = model.update();
				if (flag) {
					result++;
				}
			} catch (Exception e) {
				LOGGER.error(dao.toJson() + "更新详情出错:", e);
			}
		}
		LOGGER.info("更新完详情结束,共更新:" + result + "条!!!");
		return result;
	}

}
