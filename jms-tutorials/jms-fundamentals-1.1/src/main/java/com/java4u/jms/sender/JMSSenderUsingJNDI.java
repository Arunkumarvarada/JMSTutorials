package com.java4u.jms.sender;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSSenderUsingJNDI {

	public static void main(String[] args) {

		try {
			Context context = new InitialContext();
			Connection connection = ((ConnectionFactory) context.lookup("ConnectionFactory")).createConnection();
			connection.start();
			Queue queue = (Queue) context.lookup("TRADE.Q");
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("SELL AAPL 1000 SHARES");
			message.setStringProperty("TraderName", "Arun");
			producer.send(message);
			System.out.println("Message Sent");
			connection.close();

		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}

	}

}
