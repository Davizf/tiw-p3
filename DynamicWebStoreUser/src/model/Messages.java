package model;

public class Messages {
	
	String receiver;
	String sender;
	String message;
	
	public Messages(String sender,String receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
	
	public Messages() {
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
