package jms;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;

import java.util.Random;

import javax.jms.JMSException;


public class TextListener implements MessageListener {


	public void onMessage(Message message) {
        TextMessage msg = null;
        InteractionJMS mq=new InteractionJMS();
        Random rand = new Random();
        int n = rand.nextInt(1234567);
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                System.out.println("*******************************************************");
                System.out.println("Reading card --> " + msg.getStringProperty("creditCard"));
                System.out.println("Reading order'price --> " + msg.getStringProperty("totalPrice"));
                System.out.println("The order has been successfully completed!");
                System.out.println("*******************************************************");
                mq.writeJMS(String.valueOf(n), "confirm", "process");
            } else {
                System.err.println("Message is not a TextMessage");
            }
        } catch (JMSException e) {
            System.err.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.err.println("Exception in onMessage():" + t.getMessage());
        }
    }
}

