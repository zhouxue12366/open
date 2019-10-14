package com.m3u8.zg.ws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * websocket
 * @Title MyWebSocket.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月14日 下午4:58:31
 * @version V1.0
 */
@ServerEndpoint("/myapp.ws")
public class MyWebSocket {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 收到客户端消息时触发
	 * 
	 * @Title message
	 * @Description
	 * @param message
	 * @param session
	 * @since 2019年10月14日 下午3:57:13
	 */
	@OnMessage
	public void message(String message, Session session) {
		for (Session s : session.getOpenSessions()) {
			s.getAsyncRemote().sendText(message.toString() + "\t日期:" + sdf.format(new Date()));
		}
	}

	/**
	 * 异常时触发
	 * 
	 * @Title onError
	 * @Description
	 * @param throwable
	 * @param session
	 * @since 2019年10月14日 下午3:57:18
	 */
	@OnError
	public void onError(Throwable throwable, Session session) {
		System.out.println("my websocket error......");
	}

	/**
	 * 关闭连接时触发
	 * 
	 * @Title onClose
	 * @Description
	 * @param session
	 * @since 2019年10月14日 下午3:57:25
	 */
	@OnClose
	public void onClose(Session session) {
		try {
			session.getBasicRemote().sendText("我要关闭了");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
