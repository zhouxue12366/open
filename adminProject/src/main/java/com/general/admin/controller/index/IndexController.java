package com.general.admin.controller.index;

import java.util.List;

import org.apache.log4j.Logger;

import com.general.admin.controller.BaseController;
import com.general.admin.model.ProductInfo;
import com.general.admin.service.IndexService;
import com.jfinal.aop.Inject;

/**
 * 
 * @Title IndexController.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年4月10日 上午9:32:19
 * @version V1.0
 */
public class IndexController extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	
	@Inject
	private IndexService indexService;
	
	public void index(){
		log.info("正在访问首页...");
		set("name", "我是demo");
		List<ProductInfo> productInfoList = indexService.getProductList();
		set("productInfoList", productInfoList);
		log.error("访问首页出错了");
		render("index.html");
	}
}
