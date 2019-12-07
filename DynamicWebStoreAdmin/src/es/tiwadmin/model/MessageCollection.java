package es.tiwadmin.model;

import java.util.ArrayList;

public class MessageCollection {
	
	private String sender;
	private ArrayList<String> messages;
	private int unreadMessages;
	
	
	public MessageCollection(String sender, String initialMessage) {
		this.messages = new ArrayList<String>();
		this.messages.add(initialMessage);
		this.sender = sender;
		this.unreadMessages = 1;
	}

	public ArrayList<String> getMsgs() {
		return messages;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public int getUnreadMessages() {
		return this.unreadMessages;
	}
	
	public void setUnreadMessages(int unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != this.getClass())
			return false;

		return ((MessageCollection) obj).getSender().equals(this.sender);
	}
	
}
