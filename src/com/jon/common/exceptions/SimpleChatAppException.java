package com.jon.common.exceptions;

@SuppressWarnings("serial")
public class SimpleChatAppException extends Exception {

	public SimpleChatAppException(String errorMessage, Exception e) {
		super(errorMessage, e);
	}

	public SimpleChatAppException(String errorMessage) {
		super(errorMessage);
	}
}
