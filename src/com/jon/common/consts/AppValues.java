 package com.jon.common.consts;

/**
 * -Constant that are used all over the code are listed here for ease of use.
 * 
 * @author jonferei
 *
 */

public class AppValues {
	private AppValues() {
		//-(This class should not be instatiated). 
	}

	public static final String NICK_NAME = "nickName";
	public static final String HTTP_SESSION = "httpSession";
	public static final String ERR_NICKNAME_TAKEN = "This nickname is already taken, try another one!";
	public static final String ERR_INTERNAL = "Internal server error";
	public static final String CHAT_SOCKET_URI = "ws://localhost:8080/SimpleChatApplication/chat";
	public static final int SESSION_TIMEOUT = 10 * 60; // --Default app session timeout in seconds.

}
