package com.general.web;

import com.jfinal.core.JFinal;

public class WebApplication {

	public static void main(String[] args) {
	    JFinal.start("src/main/webapp", 8080, "/", 5);
	}
}
