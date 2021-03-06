package com.java4u.jms.sharedsubs;

import java.text.DecimalFormat;

import javax.jms.JMSContext;
import javax.jms.Topic;

import com.sun.messaging.ConnectionFactory;

public class JMS2SharedPublisher {

	public static void main(String[] args) throws Exception {

		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();		
		try (JMSContext jmsContext = cf.createContext();) {
			Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");
			DecimalFormat df = new DecimalFormat("##.00");
			String price = df.format(98.0 + Math.random());
			jmsContext.createProducer().send(topic, price);
			System.out.println("Message published");
		} 
	}
}










