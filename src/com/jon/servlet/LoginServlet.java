package com.jon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.jon.common.ChatRoom;
import com.jon.common.consts.AppValues;

/**
 * -Login servlet.
 * 
 * @author jonferei
 *
 */

@WebServlet(value = "/login", loadOnStartup = 1)
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	final Logger log = Logger.getLogger(LoginServlet.class);
	private final ChatRoom chatRoom = ChatRoom.getRoom();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// -Any unauthorized access within the app is redirected to the login page.
			request.getRequestDispatcher("views/login.jsp").forward(request, response);
		} catch (ServletException e) {
			log.error(e.getMessage());
		} catch (IOException e1) {
			log.error(e1.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// -Logging procedure
			// -Here we are storing the login user in HTTPSession.
			String nickName = request.getParameter(AppValues.NICK_NAME);
			if (request.getSession(false) == null) {
				// --Session already invalidated, back to login page.
				doGet(request, response);
			}
			if (chatRoom.checkNickNameTaken(nickName)) {
				log.info("Dispatching to Login again");
				request.setAttribute("errormsg", AppValues.ERR_NICKNAME_TAKEN);
				doGet(request, response);
			} else {
				log.info("Redirecting from Login to ChatRoom Servlet");
				request.getSession(false).setAttribute(AppValues.NICK_NAME, nickName);// -Used in the WebSocket
				request.setAttribute(AppValues.NICK_NAME, nickName); // -Used in the UI (sent via ChatRoom servlet)
				request.getRequestDispatcher("/chatRoom").forward(request, response);
			}
		} catch (ServletException e) {
			log.error(e.getMessage());
		} catch (IOException e1) {
			log.error(e1.getMessage());
		}
	}
}
