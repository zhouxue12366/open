package com.basic.framework.service.qq;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class QQLiveService {

	public Record getDefaultPalyUrl(){
//		ApiUrlConfig dao = new ApiUrlConfig();
		Record urlConfig = Db.findFirst("select * from api_url_config where default_play = 1 order by id ASC" );
		return urlConfig;
	}
}
