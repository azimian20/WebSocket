package com.jon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jon.common.consts.AppValues;

/**
 * This servlet is devised for possible additional functionality for the app in
 * the future.
 * 
 * @author jonferei
 *
 */
@WebServlet("/chatRoom")
@SuppressWarnings("serial")
public class ChatRoomServlet extends HttpServlet {
	final Logger log = Logger.getLogger(ChatRoomServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("session_timout", AppValues.SESSION_TIMEOUT);
			request.setAttribute("chat_socket_uri", AppValues.CHAT_SOCKET_URI);
			request.getRequestDispatcher("views/chatRoom.jsp").forward(request, response);
		} catch (ServletException e) {
			log.error(e.getMessage());
		} catch (IOException e1) {
			log.error(e1.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
