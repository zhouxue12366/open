package com.softisland.common.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MqMain {
	private static final String BROKER_URL = "tcp://172.16.20.160:61616";//ActiveMQConnection.DEFAULT_BROKER_URL;
	private static final String SUBJECT = "pornjam.queue";

	public static void start() throws JMSException {
		
		// 初始化ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

		// 创建mq连接
		Connection conn = connectionFactory.createConnection();
		// 启动连接
		conn.start();

		// 创建会话
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 通过会话创建目标
		Destination dest = session.createQueue(SUBJECT);
		// 创建mq消息的消费者
		MessageConsumer consumer = session.createConsumer(dest);

		// 初始化MessageListener
		MsgConsumer me = new MsgConsumer();

		// 给消费者设定监听对象
		consumer.setMessageListener(me);
	}
	
}
