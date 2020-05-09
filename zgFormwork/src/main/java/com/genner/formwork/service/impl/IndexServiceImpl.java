package com.genner.formwork.service.impl;

import java.util.List;

import com.genner.formwork.annotation.Service;
import com.genner.formwork.model.UserInfo;
import com.genner.formwork.service.IndexService;

@Service("indexService")
public class IndexServiceImpl implements IndexService{

	@Override
	public String getIndexList() {
		List<UserInfo> user = UserInfo.dao.findAll();
//		List<Record> user = Db.findAll("user_info");
		String result = "";
		for(UserInfo u: user){
			result += (","+u.getNickName());
		}
		return result;
	}

}
