package com.java4u.jms.RequestReplyMessaging;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSRecieverWithCorrelationIDMechanism {

	public static void main(String[] args) {
		 try {
			Connection connection= new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			connection.start();
			
			Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue= session.createQueue("EM_TRADE_REQ.Q");
			MessageConsumer receiver= session.createConsumer(queue);
			TextMessage message=(TextMessage) receiver.receive();
			System.out.println("Processing Trade:: "+ message.getText());
			Thread.sleep(4000);
			String confirmation="EQ-12345";
			System.out.println("trade confirmation: "+ confirmation);
			
			TextMessage outMessage= session.createTextMessage(confirmation);
			outMessage.setJMSCorrelationID(message.getJMSMessageID());
			MessageProducer sender= session.createProducer((Queue)message.getJMSReplyTo());
			sender.send(outMessage);
			
			connection.close();
			
		} catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
