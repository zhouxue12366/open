package com.basic.framework;

import com.basic.framework.config.JfinalConf;
import com.jfinal.server.undertow.UndertowServer;

/**
 * 程序启动类,程序启动入口
 * @Title ApplicationMain.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月15日 上午11:01:05
 * @version V1.0
 */
public class ApplicationMain {
	/**
	 * jfinal undertow启动方式
     * 注意：用于启动的 main 方法可以在任意 java 类中创建，在此仅为方便演示
     *      才将 main 方法放在了 DemoConfig 中
     *
     *      开发项目时，建议新建一个 App.java 或者 Start.java 这样的专用
     *      启动入口类放置用于启动的 main 方法
     */
    public static void main(String[] args) {
        UndertowServer.start(JfinalConf.class, 8080, true);
    }
    
//	/**
//	 * websocket启动法法
//	 * @Title main
//	 * @Description  
//	 * @param args 
//	 * @since 2019年10月14日 下午4:04:53
//	 */
//    public static void main(String[] args) {
//    	UndertowServer.create(JfinalConf.class)
//        .configWeb( builder -> {
////            // 配置 Filter
////            builder.addFilter("myFilter", "com.abc.MyFilter");
////            builder.addFilterUrlMapping("myFilter", "/*");
////            builder.addFilterInitParam("myFilter", "key", "value");
////            
////            // 配置 Servlet
////            builder.addServlet("myServlet", "com.abc.MyServlet");
////            builder.addServletMapping("myServlet", "*.do");
////            builder.addServletInitParam("myServlet", "key", "value");
////            
////            // 配置 Listener
////            builder.addListener("com.abc.MyListener");
//            
//            // 配置 WebSocket，MyWebSocket 需使用 ServerEndpoint 注解
//            builder.addWebSocketEndpoint("com.m3u8.zg.ws.MyWebSocket");
//         })
//        .start();
//    }
	
	/**
	 * 与 jetty-server启动方式,相关小节几乎一样，仅仅是 main 方法中的内容有所不同
	 * @param args
	 */
//	public static void main(String[] args) {
//	    JFinal.start("src/main/webapp", 80, "/", 5);
//	}
}
