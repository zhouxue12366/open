package com.softisland.common.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.softisland.business.spider.DocumentToolkit;

public class MsgConsumer implements MessageListener {
	
	private static final String WWW = "https://www.pornjam.com";
	private static final String rootUrl = "https://www.pornjam.com/top-rated/m/";
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgConsumer.class);
//    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
//    private static final String SUBJECT = "pornjam.queue";

    @Override
    public void onMessage(Message message) {
        TextMessage txtMessage = (TextMessage)message;
        try {
            LOGGER.info ("get message: " + txtMessage.getText());
//            getDetail(txtMessage.getText());
        } catch (Exception e) {
            LOGGER.error("error {}", e);
        }
    }
    
    public void getDetail(String jsonStr){
    	JSONObject jsonObject = JSONObject.parseObject(jsonStr);
    	String url = "http://localhost:8080/springBootDubboConsumer/home?oldId="+jsonObject.getString("oldId")+"&poster="+jsonObject.getString("poster")+"&play_url="+jsonObject.getString("play_url");
		String result = HttpKit.get(url);
    	
//    	MsgProducer.send(text);
//    	String rootUrl = WWW+jsonObject.getString("path");
//    	LOGGER.info("请求地址为："+rootUrl);
//    	Document root = DocumentToolkit.getDocument(rootUrl);
//    	Elements players = root.getElementsByClass("player");
//    	for(Element player: players){
//    		Elements videos = player.getElementsByTag("video");
//    		String poster = videos.first().attr("poster");
//    		
//    		Element source = player.getElementsByTag("source").first();
//    		String play_url = source.attr("src");
//    		String oldId = poster.substring(poster.lastIndexOf("/")+1, poster.indexOf(".mp4-")+4);
    		
//    		String url = "http://localhost:8080/springBootDubboConsumer/index?oldId="+oldId+"&poster="+poster+"&play_url="+play_url;
//    		String result = HttpKit.get(url);
//    		Record model = Db.findFirst("select * from most_viewed where old_id= ?", oldId);
//    		model.set("poster", poster);
//    		model.set("play_url", play_url);
////    		model.setPlayUrl(play_url);
//    		boolean result = Db.update("most_viewed", model);
//    		MsgProducer.send(text);
//    		LOGGER.info("MQ执行修改数据ID={}结果为:{}",oldId,result);
//    	}
    	LOGGER.info("MQ执行完毕......"+url);
    }

}
