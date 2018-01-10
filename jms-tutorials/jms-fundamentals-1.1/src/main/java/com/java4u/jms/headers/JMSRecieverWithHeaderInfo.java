package com.java4u.jms.headers;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSRecieverWithHeaderInfo {

	public static void main(String[] args) {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Connection connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			MessageConsumer consumer = session.createConsumer(queue);

			TextMessage message = (TextMessage) consumer.receive();
			displayHeaders(message);

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	private static void displayHeaders(TextMessage message) {
		try {
			System.out.println("JMS Delivery Mode : " + message.getJMSDeliveryMode());
			System.out.println("JMS Expiration    : " + message.getJMSExpiration());
			System.out.println("JMS Priority      : " + message.getJMSPriority());
			System.out.println("Amount:           : " + message.getStringProperty("Amount"));
			System.out.println("not:              : " + message.getStringProperty("not"));
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
