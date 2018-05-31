package com.jon.servlet;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(value = "/logout", loadOnStartup = 1)
@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {
	final Logger log = Logger.getLogger(LogoutServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info("Dispatching to Login servlet");
			request.getSession(false).invalidate();
			response.sendRedirect("login");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
