package com.general.web.service;

import java.util.List;

import com.general.web.model.ProductInfo;

public class IndexService {
	
	public List<ProductInfo> getProductList(){
		return ProductInfo.dao.find("select * from product_info");
	}
}
