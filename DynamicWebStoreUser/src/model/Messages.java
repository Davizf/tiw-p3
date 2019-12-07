package model;

public class Messages {
	
	String msg;
	String sender;
	
	public Messages(String msg, String sender) {
		this.msg = msg;
		this.sender = sender;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
}
