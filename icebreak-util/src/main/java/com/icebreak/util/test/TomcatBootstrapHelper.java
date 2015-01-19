package com.icebreak.util.test;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;

import javax.servlet.ServletException;

public class TomcatBootstrapHelper {
	private static final char ENTER_CHAR = '\n';
	private static final int DEFAULT_PORT = 8080;
	private static final String DEFULT_ENV = "dev";
	private int port = DEFAULT_PORT;
	
	private boolean isServlet3Enable = false;
	
	public TomcatBootstrapHelper(int port, boolean isServlet3Enable, String env) {
		System.setProperty("spring.profiles.active", env);
		this.port = port;
		this.isServlet3Enable = isServlet3Enable;
	}
	
	public TomcatBootstrapHelper(int port, boolean isServlet3Enable) {
		this(port, isServlet3Enable, DEFULT_ENV);
	}
	
	public TomcatBootstrapHelper(int port) {
		this(port, false);
	}
	
	public TomcatBootstrapHelper() {
		this(DEFAULT_PORT);
	}
	
	public void start() {
		try {
			long begin = System.currentTimeMillis();
			Tomcat tomcat = new Tomcat();
			configTomcat(tomcat);
			tomcat.start();
			long end = System.currentTimeMillis();
			log(end - begin);
			//在控制台回车就可以重启，提高效率
			while (true) {
				char c = (char) System.in.read();
				if (c == ENTER_CHAR) {
					begin = System.currentTimeMillis();
					System.out.println("重启tomcat...");
					tomcat.stop();
					tomcat.start();
					end = System.currentTimeMillis();
					log(end - begin);
				}
			}
		} catch (Exception e) {
			System.err.println("非常抱歉，貌似启动挂了...");
			e.printStackTrace();
		}
		
	}
	
	private void configTomcat(final Tomcat tomcat) throws ServletException {
		tomcat.setBaseDir("target");
		tomcat.setPort(port);
		Connector connector = new Connector("HTTP/1.1");
		connector.setPort(port);
		connector.setURIEncoding("utf-8");
		tomcat.setConnector(connector);
		tomcat.getService().addConnector(connector);
		String webappPath = getWebappsPath();
		System.out.println("webapp目录：" + webappPath);
		Context ctx = tomcat.addWebapp("/", webappPath);
		StandardJarScanner scanner = (StandardJarScanner) ctx.getJarScanner();
		if (!isServlet3Enable) {
			scanner.setScanAllDirectories(false);
			scanner.setScanClassPath(false);
		}
		tomcat.setSilent(true);
		System.setProperty("org.apache.catalina.SESSION_COOKIE_NAME", "JSESSIONID" + port);
	}
	
	private void log(long time) {
		System.out.println("********************************************************");
		System.out.println("启动成功: http://127.0.0.1:" + port + "   in:" + time + "ms");
		System.out.println("********************************************************");
	}
	
	public String getWebappsPath() {
		String file = getClass().getClassLoader().getResource(".").getFile();
		return file.substring(0, file.indexOf("target")) + "src/main/webapp";
	}
}
