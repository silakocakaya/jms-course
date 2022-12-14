package jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class FirstQueue {

	public static void main(String[] args) throws Exception {

		InitialContext initialContext = null;
		Connection connection = null;
		try {
			initialContext = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection = cf.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");
			
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("I am the creator of my destiny");
			producer.send(message);
			System.out.println("Message Sent: " + message.getText());

			MessageConsumer consumer = session.createConsumer(queue);
			TextMessage messageReceived = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received: " + messageReceived.getText());
		} finally {
			initialContext.close();
			connection.close();
		}
	}

}
