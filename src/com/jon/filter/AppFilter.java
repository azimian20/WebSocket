package com.jon.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * -Servlet filter to prohibit unauthorized access to the app servlets and socket endpoint.
 * In this app, login page only helps user to pick a nickname.
 * @author jonferei
 *
 */

@WebFilter("/*")
public class AppFilter implements Filter {

	final Logger log = Logger.getLogger(AppFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session == null) {
			log.info("session is dead");
			request.getRequestDispatcher("/login").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		log.info("WebFilter is destroyed");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("WebFilter is started");
	}
}
