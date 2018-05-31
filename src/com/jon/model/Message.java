package com.jon.model;

import java.util.ArrayList;
import java.util.List;

public class Message {

	private MessageType messageType;
	private String messageText;
	private String sender;
	private String receiver;
	private List<String> onlineUsers;

	// Constructor for public chat messages:
	public Message(MessageType messageType, String messageText) {
		this.messageType = messageType;
		this.messageText = messageText;
	}

	// Constructor for direct chat messages:
	public Message(MessageType messageType, String messageText, String receiver) {
		this.messageType = messageType;
		this.messageText = messageText;
		this.receiver = receiver;
	}

	// Constructor for system messages:
	public Message(String sender, MessageType messageType, String messageText) {
		this.sender = sender;
		this.messageType = messageType;
		this.messageText = messageText;
	}

	// Constructor for showing information to users:
	public Message(MessageType messageType, List<String> onlineUsers) {
		this.messageType = messageType;
		this.onlineUsers = onlineUsers;
	}

	public Message(MessageType messageType, String messageText, String sender, String receiver,
			List<String> onlineUsers) {
		super();
		this.messageType = messageType;
		this.messageText = messageText;
		this.sender = sender;
		this.receiver = receiver;
		this.onlineUsers = onlineUsers;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public List<String> getOnlineUsers() {
		if (onlineUsers == null)
			return new ArrayList<>();
		return onlineUsers;
	}

	public void setOnlineUsers(List<String> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

}
