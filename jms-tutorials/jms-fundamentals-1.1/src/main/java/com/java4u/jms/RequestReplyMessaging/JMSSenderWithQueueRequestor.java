package com.java4u.jms.RequestReplyMessaging;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueRequestor;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSenderWithQueueRequestor {

	public static void main(String[] args) {
		QueueConnection connection;
		try {
			connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createQueueConnection();
			connection.start();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			TextMessage message = session.createTextMessage("BUY AAPL 10000 SHARES");
			Queue queueReq = session.createQueue("EM_TRADE_REQ.Q");
			QueueRequestor requestor = new QueueRequestor(session, queueReq);

			TextMessage messageResp = (TextMessage) requestor.request(message);
			System.out.println("Confirmation" + messageResp.getText());

			connection.close();

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
