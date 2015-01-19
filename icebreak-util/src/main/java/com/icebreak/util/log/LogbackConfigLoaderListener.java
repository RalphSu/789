package com.icebreak.util.log;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LogbackConfigLoaderListener implements ServletContextListener {
	
	private final static String LOGBACK_CONFIG_PARAMETER_NAME = "logbackConfigLocation";
	
	private static String logbackConfigLocation = "/WEB-INF/logback.xml";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		String t = sc.getInitParameter(LOGBACK_CONFIG_PARAMETER_NAME);
		if(t != null && t.trim().length() > 0){
			logbackConfigLocation = Env.analyze(t);
		}
		logbackConfigLocation = sc.getRealPath(logbackConfigLocation);
		System.out.println("logback config location:" + logbackConfigLocation);
		Log.logback(logbackConfigLocation);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
