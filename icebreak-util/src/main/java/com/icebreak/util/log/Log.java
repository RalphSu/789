package com.icebreak.util.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class Log {
	/**
	 * logback配置
	 * @param logbackConfigLocation
	 */
	public static void logback(String logbackConfigLocation){
		LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
		loggerContext.reset();
		JoranConfigurator joranConfigurator = new JoranConfigurator();
		joranConfigurator.setContext(loggerContext);
		try {
			joranConfigurator.doConfigure(logbackConfigLocation);
		} catch (JoranException e) {
			e.printStackTrace();
		}
	}

}
