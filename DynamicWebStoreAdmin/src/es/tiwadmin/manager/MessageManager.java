package es.tiwadmin.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import es.tiwadmin.info.InformationProperties;
import es.tiwadmin.model.MessageCollection;


public class MessageManager {

	private ConnectionFactory factory = null;
	private InitialContext initialContext = null;
	private Destination queue = null;
	private Connection Qcon = null;
	private Session QSes = null;
	private MessageProducer Mpro = null;
	private MessageConsumer Mcon = null;

	public void writeJMS(String sender, String receiver, String message) {

		try {
			initialContext = new InitialContext();
			factory = (ConnectionFactory) initialContext.lookup(InformationProperties.getQCF());
			queue = (Destination) initialContext.lookup(InformationProperties.getQueue());
			
			Qcon = factory.createConnection();
			QSes = Qcon.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Mpro = QSes.createProducer(queue);

			TextMessage msg = QSes.createTextMessage();

			msg.setText(message);
			msg.setJMSCorrelationID(receiver);
			msg.setStringProperty("sender", sender);
			Qcon.start();
			Mpro.send(msg);

			this.Mpro.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (JMSException e) {
			System.out.println("JMS Error: "+ e.getLinkedException().getMessage());
			System.out.println("JMS Error: "+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out.println("Error Exception: "+ e.getMessage());
		}

	}

	public List<MessageCollection> readJMS(String receiver) {

		ArrayList<MessageCollection> messages = new ArrayList<MessageCollection>();
		
		try {
			initialContext = new InitialContext();

			factory = (ConnectionFactory) initialContext.lookup(InformationProperties.getQCF());
			queue = (Destination) initialContext.lookup(InformationProperties.getQueue());
				
			Qcon = factory.createConnection();

			QSes = Qcon.createSession(false, Session.AUTO_ACKNOWLEDGE);

			if (receiver.equals(""))
				Mcon = QSes.createConsumer(queue);
			else
				Mcon = QSes.createConsumer(queue, "JMSCorrelationID = '" + receiver.trim() + "'");
			
			Qcon.start();
			Message message = null;

			while ((message = Mcon.receive(100)) != null) {
				if (message instanceof TextMessage) {
					TextMessage m = (TextMessage) message;
					
					String mSender = m.getStringProperty("sender");
					
					List<MessageCollection> sameSenderList = 
							messages.stream().filter(elem ->  elem.getSender().equals(mSender)).collect(Collectors.toList());
					
					if(sameSenderList.size() != 0) {
						for(MessageCollection msg : sameSenderList) {
							msg.addMessage(m.getText());
							msg.setUnreadMessages(msg.getUnreadMessages() + 1);
						}
					} else  {
						messages.add(new MessageCollection(mSender, m.getText()));
					}
					
				} else {
					System.out.println("Invalid message type. Only TestMessage is allowed.");
					break;
				}	
			}
			
			this.Mcon.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (JMSException jmse) {
			System.out.println("JMS Error: "+ jmse.getLinkedException().getMessage());
			System.out.println("JMS Error: "+ jmse.getLinkedException().toString());
		} catch(NamingException ne) {
			System.out.println("JMS Error: " + ne.getMessage());
		}
		
		return messages.size() == 0 ? null : messages;
	}
	
	public List<MessageCollection> join(List<MessageCollection> sessionStored, List<MessageCollection> newGenerated) {
		if(sessionStored == null)
			return newGenerated;
		else
			if(newGenerated == null)
				return sessionStored;

		List<MessageCollection> commonFromSession =
				sessionStored.stream().filter(elem -> newGenerated.contains(elem)).collect(Collectors.toList());
		
		sessionStored.removeIf(elem -> commonFromSession.contains(elem));
		
		for(MessageCollection msg : newGenerated) {
			int index = commonFromSession.indexOf(msg);
			if(index == -1) continue;

			msg.getMsgs().addAll(0, commonFromSession.get(index).getMsgs());
			msg.setUnreadMessages(commonFromSession.size());
		}
		
		newGenerated.addAll(sessionStored);
		return newGenerated;
	}

}
