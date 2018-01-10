package com.java4u.jms.headers;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSenderWithHeadersInfo {

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE.Q");
		MessageProducer producer = session.createProducer(queue);
		TextMessage msg = session.createTextMessage("BUY AAPL 1000 SHARES");
		msg.setStringProperty("Amount", "1111.aa");
		msg.setStringProperty("not", "1111.aa");

		msg.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		msg.setJMSExpiration(new Date().getTime() + 1234567);
		producer.setTimeToLive(1234567);

		msg.setJMSPriority(9);
		producer.setPriority(9);
		displayHeaders(msg);
		producer.send(msg);
		System.out.println("Message Sent!!");
		displayHeaders(msg);
		connection.close();

	}

	private static void displayHeaders(TextMessage msg) {
		try {
			System.out.println("JMS Delivery Mode : " + msg.getJMSDeliveryMode());
			System.out.println("JMS Expiration    : " + msg.getJMSExpiration());
			System.out.println("JMS Priority      : " + msg.getJMSPriority());
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
