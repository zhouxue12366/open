package com.genner.formwork.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.genner.formwork.annotation.Autowired;
import com.genner.formwork.annotation.Controller;
import com.genner.formwork.annotation.RequestMapping;
import com.genner.formwork.annotation.RequestParam;
import com.genner.formwork.service.IndexService;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private IndexService indexService;

	@RequestMapping("/hello")
	public String hello(HttpServletRequest request) {
		System.out.println("start hello....");
		String serviceList = indexService.getIndexList();
		request.setAttribute("result", serviceList);
		return "index";
	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String name) {
		String serviceList = indexService.getIndexList();
		String result =  "hello,欢迎来到" + name + "正在获取业务层:" + serviceList;
		request.setAttribute("result", result);
		return "index";
	}
}
