package model;

public class Message {
	
	String id;
	String receiver;
	String sender;
	String message;
	
	public Message(String sender,String receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
	
	public Message() {
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
