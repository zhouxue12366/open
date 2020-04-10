package com.general.admin.service;

import java.util.List;

import com.general.admin.model.ProductInfo;

public class IndexService {
	
	public List<ProductInfo> getProductList(){
		return ProductInfo.dao.find("select * from product_info");
	}
}
