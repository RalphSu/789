
package com.icebreak.util.env;

import com.icebreak.util.lang.exception.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.util.ArrayList;

public enum Env {
	online,
	test,
	dev,
	unknown;
	public static final String ENV_KEY = "spring.profiles.active";
	private static Env currEnv = null;
	private static String currEnvStr = null;
	private static final Logger logger = LoggerFactory.getLogger(Env.class.getName());
	private static String port = null;
	private static final String ERROR_PORT = "-1";
	private static final String httpProtocol = "http";
	
	public static String getEnv() {
		if (currEnvStr == null) {
			currEnvStr = System.getProperty(ENV_KEY);
			if (currEnvStr == null) {
				currEnvStr = System.getenv(ENV_KEY);
			}
			if (currEnvStr != null) {
				try {
					currEnv = Env.valueOf(currEnvStr);
				} catch (IllegalArgumentException e) {
					logger.warn("不识别的环境:spring.profiles.active=" + currEnvStr);
				}
			} else {
				throw Exceptions.newRuntimeException("需要配置系统变量或者环境变量spring.profiles.active");
			}
		}
		return currEnvStr;
	}
	
	public static boolean isOnline() {
		return is(online);
	}

	public static boolean isDev() {
		return is(dev);
	}
	public static boolean isTest() {
		return is(test);
	}

	/**
	 * 判读是否是某环境
	 * 
	 * @param env 环境
	 */
	public static boolean is(Env env) {
		if (env == null) {
			throw Exceptions.newRuntimeException("env不能为null");
		}
		if (currEnv == null) {
			getEnv();
		}
		return env == currEnv;
	}
	
	public static String getPort() {
		if (port == null) {
			try {
				//for tomcat 7
				initPortByMBean(new ObjectName("Catalina", "type", "Service"));
				//for tomcat embed 7
				initPortByMBean(new ObjectName("Tomcat", "type", "Service"));
				
			} catch (Exception e) {
				logger.warn("获取端口失败:{}", e.getMessage());
			}
			if (port == null) {
				port = ERROR_PORT;
			}
		}
		return port;
	}
	
	public static boolean isErrorPort(String port) {
		return ERROR_PORT.equals(port);
	}
	
	private static void initPortByMBean(ObjectName name) {
		if (port == null) {
			ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
			if (mBeanServers != null && !mBeanServers.isEmpty()) {
				MBeanServer mBeanServer = mBeanServers.get(0);
				try {
					ObjectName[] objNames = (ObjectName[]) mBeanServer.getAttribute(name,
						"connectorNames");
					for (ObjectName on : objNames) {
						String protocol = (String) mBeanServer.getAttribute(on, "protocol");
						if (protocol.toLowerCase().contains(httpProtocol)) {
							Integer p = (Integer) mBeanServer.getAttribute(on, "localPort");
							if (p != null) {
								port = String.valueOf(p);
								logger.info("获取服务端口成功:{}", port);
							}
						}
					}
				} catch (Exception e) {
					logger.warn("通过{}获取服务端口失败", name.toString());
				}
			}
		}
	}
}
