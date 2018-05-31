package com.jon.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.jon.common.consts.AppValues;
import com.jon.model.Message;
import com.jon.model.MessageType;

/**
 * All functionalities for the chat room are implemented in this class. This is the
 * medium between WebSocket enddpoint and user interface. Besides, we keep all
 * alive WebSocket sessions here. Having that, we can take control over public
 * messages and direct messages (Using the receiver's nickname).
 * 
 * @author jonferei
 *
 */

public class ChatRoom {

	// -WebSocket sessions
	private List<Session> sessions = new ArrayList<>();
	private static ChatRoom instance = null;

	private ChatRoom() {
	}

	Gson gson = new Gson();
	Logger log = Logger.getLogger(ChatRoom.class);

	// -Methods are synchronized to avoid race conditions in a crowded chatroom.
	public synchronized void join(Session session) {
		sessions.add(session);
		Message onlineUsersMessage = new Message(MessageType.USERS_INFO, getOnlineUsers());
		sendMessage(onlineUsersMessage);
	}

	public synchronized void leave(Session session) {
		Message onlineUsersMessage = new Message(MessageType.USERS_INFO, getOnlineUsers());
		sendMessage(onlineUsersMessage);
		sessions.remove(session);
	}

	public synchronized void sendMessage(Message message) {
		for (Session session : sessions) {
			if (session.isOpen()) {
				try {
					session.getBasicRemote().sendText(gson.toJson(message));
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

	public synchronized void sendDirectMessage(Message message) {
		String onlineUser = "";
		for (Session session : sessions) {
			if (session.isOpen()) {
				onlineUser = session.getUserProperties().get(AppValues.NICK_NAME).toString();
				if (onlineUser.equals(message.getReceiver())) {
					try {
						session.getBasicRemote().sendText(gson.toJson(message));
					} catch (IOException e) {
						log.error(e);
					}
				}
			}
		}
	}

	public synchronized List<String> getOnlineUsers() {
		List<String> onlineUsers = new ArrayList<>();

		for (Session session : sessions) {
			if (session.isOpen()) {
				onlineUsers.add(session.getUserProperties().get(AppValues.NICK_NAME).toString());
			}
		}
		return onlineUsers;
	}

	public synchronized boolean checkNickNameTaken(String nickName) {
		for (String loginNickName : getOnlineUsers()) {
			if (loginNickName.equals(nickName)) {
				return true;
			}
		}
		return false;
	}

	// -Singletone ChatRoom is created here.
	public static synchronized ChatRoom getRoom() {
		if (instance == null) {
			instance = new ChatRoom();
		}
		return instance;
	}

}
