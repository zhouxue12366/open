package com.softisland.business.index.service;

import java.util.List;

import com.softisland.common.model.MostViewed;

public class MostViewedService {

	public boolean save(MostViewed model) {
		return model.save();
	}
	
	public MostViewed getById(String oldId){
		return MostViewed.dao.findFirst(" select * from  most_viewed where old_id = ?",oldId);
	}
	
	public List<MostViewed> getByUpdateStatus(){
		return MostViewed.dao.find(" select * from  most_viewed where update_status = ? order by id desc limit 1 ",0);
	}

}
