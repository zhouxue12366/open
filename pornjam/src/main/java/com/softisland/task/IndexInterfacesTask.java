package com.softisland.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.softisland.business.spider.PornjamSpider;
import com.softisland.common.activemq.MsgProducer;
import com.softisland.common.model.Menu;

public class IndexInterfacesTask implements Job{
	PornjamSpider pornjamSpider = new PornjamSpider();
	MsgProducer msgProducer = new MsgProducer();
	private Logger logger=Logger.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("定时任务开始......");
		try {
			Menu menu = new Menu();
			List<Menu> menus = menu.dao().find("select * from menu where parent_menu_id in (1,2)");
			for(Menu model : menus){
				pornjamSpider.httpGet(model.getHref());
//				Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("定时任务扫描结束........");
	}

}
