package jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageConsumer;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AsynchConsumer {

	// @Resource(mappedName = "jms/cf1.1")
	// private static ConnectionFactory connectionFactory;
	// @Resource(mappedName = "jms/queue1.1")
	// private static Queue queue;

	private InitialContext initialContext = null;
	private ConnectionFactory factory = null;
	private Destination queue = null;
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private TextListener listener = null;

	public void asynchRead() {

		try {

			initialContext = new InitialContext();
			factory = (javax.jms.ConnectionFactory) initialContext.lookup("jms/cf1.1");
			queue = (javax.jms.Destination) initialContext.lookup("jms/queueAsincrona");
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(queue);
			listener = new TextListener();
			consumer.setMessageListener(listener);
			System.out.println("AsynchReadServlet-->Listener up");
			connection.start();

		} catch (NamingException ne) {
			System.out
					.println("readAsynch.NamingException....JHC *************************************** Error de JMS: " + ne.getMessage());

		} catch (JMSException e) {
			System.out
					.println("readAsynch.....JHC *************************************** Error de JMS: " + e.getLinkedException().getMessage());
			System.out
					.println("readAsynch.....JHC *************************************** Error de JMS: " + e.getLinkedException().toString());
		}
	}
}
