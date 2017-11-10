package com.etri.main;

public class WsMessage {
	private MessageType messageType;
	private boolean isuser = true;
	private String message;
	
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isIsuser() {
		return isuser;
	}
	public void setIsuser(boolean isuser) {
		this.isuser = isuser;
	}
	
	
}
