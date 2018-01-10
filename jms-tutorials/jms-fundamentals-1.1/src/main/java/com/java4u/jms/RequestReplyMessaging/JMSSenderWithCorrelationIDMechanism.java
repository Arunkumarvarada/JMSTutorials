package com.java4u.jms.RequestReplyMessaging;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSenderWithCorrelationIDMechanism {

	public static void main(String[] args) {
		try {
			Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createQueueConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue queueReq = session.createQueue("EM_TRADE_REQ.Q");
			Queue queueResp = session.createQueue("EM_TRADE_RESP.Q");
			
			TextMessage message= session.createTextMessage("BUY AAPL 10000 SHARES");
			message.setJMSReplyTo(queueResp);
			
			MessageProducer sender= session.createProducer(queueReq);
			sender.send(message);
			System.out.println("Message Sent!!");

			
			String filter = "JMSCorrellationID='"+message.getJMSCorrelationID()+"'";
			MessageConsumer  receiver = session.createConsumer(queueResp,filter);
			TextMessage megIn= (TextMessage) receiver.receive();
			System.out.println("Confirmation= "+megIn.getText());
			
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
