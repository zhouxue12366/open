package com.softisland.common.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgProducer.class);
	private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static final String SUBJECT = "test-activemq-queue";

	public static void main(String[] args) throws JMSException {
		// send("hello active mq 中文");
	}

	public static ActiveMQConnectionFactory init() {
		// 初始化连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
		return connectionFactory;
	}

	// 初始化连接工厂
	static ActiveMQConnectionFactory  connectionFactory = init();

	public static void send(String text) throws JMSException {
		// 获得连接
		Connection conn = connectionFactory.createConnection();
		// 启动连接
		conn.start();

		// 创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 创建队列
		Destination dest = session.createQueue(SUBJECT);
		// createTopic方法用来创建Topic
		// session.createTopic("TOPIC");

		// 通过session可以创建消息的生产者
		MessageProducer producer = session.createProducer(dest);
		// for (int i = 0; i < 1; i++) {
		// 初始化一个mq消息
		TextMessage message = session.createTextMessage(text);
		// 发送消息
		producer.send(message);
		LOGGER.info("send message :{}", text);
		// text += i;
		// }
		// 关闭mq连接
		conn.close();
	}

}
