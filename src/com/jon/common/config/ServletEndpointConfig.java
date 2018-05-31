package com.jon.common.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import com.jon.common.consts.AppValues;

/**
 * To map Http Sessions and Websocket Sessions, I modified socket handshake initializer.
 * This is called when a websoket opens on user demand on client-side.
 * @author jonferei
 *
 */
public class ServletEndpointConfig extends ServerEndpointConfig.Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		config.getUserProperties().put(AppValues.HTTP_SESSION, httpSession);
	}

}
