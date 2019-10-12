package com.softisland.task;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.softisland.business.spider.PornjamSpider;
import com.softisland.common.activemq.MsgProducer;

public class UpdateVideosTask implements Job {
	
	PornjamSpider pornjamSpider = new PornjamSpider();
	MsgProducer msgProducer = new MsgProducer();
	private Logger logger=Logger.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("定时更新视频详情任务开始......");
		try {
			int result = pornjamSpider.updateVideo();
			logger.info("更新"+result+"条,结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("定时更新视频详情任务扫描结束........");
	}

}
