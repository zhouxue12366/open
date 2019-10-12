package com.softisland.business.spider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTime {

	public static void main(String[] args) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date endTime = sdf.parse("2018-02-12 16:00:00");
			while(true){
				Date nowDate = new Date();
				long diff = (endTime.getTime() - nowDate.getTime())/1000;
				System.out.println("距离最后一天下班的时间还剩" + diff + "秒");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
