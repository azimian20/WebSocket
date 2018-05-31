package com.jon.common.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.jon.common.consts.AppValues;

/**
 * -This listener is created to take control over the timeout of all http sessions.
 * @author jonferei
 */

@WebListener
public class SessionListener implements HttpSessionListener {

	final Logger log = Logger.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(AppValues.SESSION_TIMEOUT);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
	}
}
