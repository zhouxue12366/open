package com.m3u8.zg;

import com.jfinal.server.undertow.UndertowServer;
import com.m3u8.zg.config.JfinalConf;

/**
 * 程序启动类,两种启动方式
 * @author zg
 *
 */
public class ApplicationMain {

	/**
     * 注意：用于启动的 main 方法可以在任意 java 类中创建，在此仅为方便演示
     *      才将 main 方法放在了 DemoConfig 中
     *
     *      开发项目时，建议新建一个 App.java 或者 Start.java 这样的专用
     *      启动入口类放置用于启动的 main 方法
     */
    public static void main(String[] args) {
        UndertowServer.start(JfinalConf.class, 8080, true);
    }
	
	/**
	 * 与 jfinal undertow 相关小节几乎一样，仅仅是 main 方法中的内容有所不同
	 * @param args
	 */
//	public static void main(String[] args) {
//	    JFinal.start("src/main/webapp", 80, "/", 5);
//	}
	

}
