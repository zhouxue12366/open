package com.general.admin;

import com.jfinal.core.JFinal;

public class AdminApplication {

	public static void main(String[] args) {
	    JFinal.start("src/main/webapp", 8080, "/", 5);
	}
}
