package com.jon.socket;

import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.jon.common.ChatRoom;
import com.jon.common.config.ServletEndpointConfig;
import com.jon.common.consts.AppValues;
import com.jon.model.Message;
import com.jon.model.MessageType;

/**
 * WebSocket endpoint for chatroom. The interaction between UI and application
 * core in the chatroom starts here. by setting 'configurator' in the
 * annotation, we take control over HTTPSession as well(beside WebSocket
 * sessions)
 * 
 * @author jonferei
 *
 */

@ServerEndpoint(value = "/chat", configurator = ServletEndpointConfig.class)
public class ChatSocket {

	private ChatRoom chatRoom = ChatRoom.getRoom();
	private EndpointConfig config;
	private Gson gson = new Gson();
	Logger log = Logger.getLogger(ChatSocket.class);

	@OnOpen
	public void onOpen(Session websocketSession, EndpointConfig config) {
		this.config = config;
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(AppValues.HTTP_SESSION);
		String nickName = httpSession.getAttribute(AppValues.NICK_NAME).toString();
		Map<String, Object> properties = websocketSession.getUserProperties();
		properties.put(AppValues.NICK_NAME, nickName);
		Message message = new Message("INFO", MessageType.PUBLIC_MESSAGE, nickName + " - Joined the chat room");
		chatRoom.join(websocketSession);
		chatRoom.sendMessage(message);
	}

	@OnMessage
	public void onMessage(Session websocketSession, String message) {
		String nickName = websocketSession.getUserProperties().get(AppValues.NICK_NAME).toString();
		Message messageObject = gson.fromJson(message, Message.class);
		messageObject.setSender(nickName);
		if (messageObject.getMessageType() == MessageType.PUBLIC_MESSAGE) {
			chatRoom.sendMessage(messageObject);
		} else if (messageObject.getMessageType() == MessageType.DIRECT_MESSAGE) {
			chatRoom.sendDirectMessage(messageObject);
		}
	}

	@OnClose
	public void onClose(Session websocketSession) {
		chatRoom.leave(websocketSession);
		log.info("session is closing by user");
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		log.error(throwable.getMessage());
	}
}
