package com.icebreak.p2p.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SessionCookieServletContextListener implements
		ServletContextListener {
	
	protected final static String SESSION_COOKIE_NAME_KEY = "sessionCookieName";
	
	protected static String SESSION_COOKIE_NAME = "jsessionId";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String t = servletContext.getInitParameter(SESSION_COOKIE_NAME_KEY);
		if(t != null && t.length() > 0){
			SESSION_COOKIE_NAME = t;
		}
		servletContext.getSessionCookieConfig().setName(SESSION_COOKIE_NAME);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
