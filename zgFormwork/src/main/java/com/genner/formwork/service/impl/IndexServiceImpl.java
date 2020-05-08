package com.genner.formwork.service.impl;

import com.genner.formwork.annotation.Service;
import com.genner.formwork.service.IndexService;

@Service("indexService")
public class IndexServiceImpl implements IndexService{

	@Override
	public String getIndexList() {
		return "张三，李四，王二，麻子";
	}

}
