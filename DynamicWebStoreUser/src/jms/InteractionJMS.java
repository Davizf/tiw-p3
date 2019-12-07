package jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.TextMessage;

import model.User;
import controllers.UserController;
import jms.info.InformationProperties;
import model.Messages;


public class InteractionJMS {

	private javax.jms.ConnectionFactory factory = null;
	private javax.naming.InitialContext initialContext = null;
	private javax.jms.Destination queue = null;
	private javax.jms.Connection Qcon = null;
	private javax.jms.Session QSes = null;
	private javax.jms.MessageProducer Mpro = null;
	private javax.jms.MessageConsumer Mcon = null;

	public void writeJMS(String message, String receiver, String sender) {

		try {

			initialContext = new javax.naming.InitialContext();
			factory = (javax.jms.ConnectionFactory) initialContext.lookup(InformationProperties.getQCF());
			queue = (javax.jms.Destination) initialContext.lookup(InformationProperties.getQueue());
			
			Qcon = factory.createConnection();
			QSes = Qcon.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			Mpro = QSes.createProducer(queue);

			javax.jms.TextMessage msg = QSes.createTextMessage();

			msg.setText(message);
			msg.setJMSCorrelationID(receiver);
			msg.setStringProperty("sender",sender);
			Qcon.start();
			Mpro.send(msg);

			this.Mpro.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (javax.jms.JMSException e) {
			System.out.println(".....JHC *************************************** JMS Error: "+ e.getLinkedException().getMessage());
			System.out.println(".....JHC *************************************** JMS Error: "+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out.println("JHC *************************************** Error Exception: "+ e.getMessage());
		}

	}
	
	
	
	public void writeJMSToAllBuyers(String message, String sender) {

		List<User>buyers = UserController.getAllUsersByType(0);
		for(User buyer: buyers) {	
				writeJMS(message, buyer.getEmail(), sender);
		}

	}
	
	
	public void confirmPurchase(String creditCard, String totalPrice) {

		try {

			initialContext = new javax.naming.InitialContext();

		
			factory = (javax.jms.ConnectionFactory) initialContext.lookup(InformationProperties.getQCF());
			queue = (javax.jms.Destination) initialContext.lookup(InformationProperties.getQueueAsincrona());
				
			Qcon = factory.createConnection();
			QSes = Qcon.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			Mpro = QSes.createProducer(queue);
			javax.jms.TextMessage msg = QSes.createTextMessage();

			
			msg.setStringProperty("creditCard",creditCard);
			msg.setStringProperty("totalPrice",totalPrice);
			Qcon.start();
			Mpro.send(msg);

			this.Mpro.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (javax.jms.JMSException e) {
			System.out
					.println(".....JHC *************************************** JMS Error: "
							+ e.getLinkedException().getMessage());
			System.out
					.println(".....JHC *************************************** JMS Error: "
							+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out
					.println("JHC *************************************** Error Exception: "
							+ e.getMessage());
		}

	}
	
	
	
	
	
	

	public ArrayList<Messages> readJMS(String receiver) {

		ArrayList<Messages> messages = new ArrayList<Messages>();
		
		int messageCount = 0;
		int sameSender = 0;
		
		try {
			initialContext = new javax.naming.InitialContext();

			factory = (javax.jms.ConnectionFactory) initialContext.lookup(InformationProperties.getQCF());
			queue = (javax.jms.Destination) initialContext.lookup(InformationProperties.getQueue());
				
			Qcon = factory.createConnection();

			QSes = Qcon.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			if (receiver.equals("")) {
				Mcon = QSes.createConsumer(queue);
			} else {
				Mcon = QSes.createConsumer(queue, "JMSCorrelationID = '" + receiver.trim() + "'");
			}
			
			Qcon.start();
			Message message = null;

			while (true) {
				message = Mcon.receive(100);
				if (message != null) {
					messageCount ++;

					
					if (message instanceof TextMessage) {
						TextMessage m = (TextMessage) message;
						
						// if same sender the msg will be stored by concatenate
						for(Messages msg : messages ) {
							if(msg.getSender().equalsIgnoreCase(m.getStringProperty("sender")) ){
								msg.setMsg( msg.getMsg().concat("<br>Message: " +m.getText())  );
								sameSender = 1;
								break;
							}
							
						}
						if(sameSender == 0) {
							messages.add(new Messages("Message: "+m.getText(),m.getStringProperty("sender") ));
						}
						
						sameSender = 0;	// reset sameSender
					} else {
						System.out.println("The type of JHC is not correct");
						break;
					}
					
					
					
					
					
					
				} else
				{
					// message doesn't exist
					break;
				}

			}
			this.Mcon.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (javax.jms.JMSException e) {
			System.out.println(".....JHC *************************************** Error de JMS: "+ e.getLinkedException().getMessage());
			System.out.println(".....JHC *************************************** Error de JMS: "+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out.println("JHC *************************************** Error Exception: "+ e.getMessage());
		}
		
		if(messageCount == 0) {
			return null;
		}
		return messages;

	}
	
	
	
	
	
	public String readConfirm( String receiver) {

		StringBuffer mSB = new StringBuffer(64);
		try {
			initialContext = new javax.naming.InitialContext();
			factory = (javax.jms.ConnectionFactory) initialContext.lookup(InformationProperties.getQCF());
			queue = (javax.jms.Destination) initialContext.lookup(InformationProperties.getQueue());
			Qcon = factory.createConnection();
			QSes = Qcon.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			if (receiver.equals("")) {
				Mcon = QSes.createConsumer(queue);
			} else {
				Mcon = QSes.createConsumer(queue, "JMSCorrelationID = '" + receiver.trim() + "'");
			}
			
			Qcon.start();
			Message message = null;
			while (true) {
				message = Mcon.receive(100);
				if (message != null) {
					if (message instanceof TextMessage) {
						TextMessage m = (TextMessage) message;
						mSB.append(m.getText());
					} else {
						System.out.println("The type of JHC is not correct");
						break;
					}
				} else 
				{
					// message doesn't exist
					break;
				}

			}
			this.Mcon.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (javax.jms.JMSException e) {
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().getMessage());
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out
					.println("JHC *************************************** Error Read Exception: "
							+ e.getMessage());
		}

		return mSB.toString();

	}
	
	

}
