package com.icebreak.p2p.util;

import com.icebreak.util.lang.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentConfig {
	protected final Logger	logger			= LoggerFactory.getLogger(this.getClass());

	private String			environmentType	= "";
	
	public String getEnvironmentType() {
		return environmentType;
	}
	
	public void setEnvironmentType(String environmentType) {
		this.environmentType = environmentType;
	}
	
	public EnvironmentConfig() {
		ApplicationConstant.CURRENT_STORE_FILE_TYPE = ApplicationConstant.STORE_FILE_TYPE_ABSOLUTE_PATH;
		ApplicationConstant.HTTP_PATH_ROOT = "";
		ApplicationConstant.FILE_PATH_ROOT = "/var/www/upload/";
		environmentType = ApplicationConstant.ENVIRONMENT_TYPE_PRODUCTION;
		if (StringUtil.isNotBlank(PropertiesUtil.getProperty("upload.filepath.root"))) {
			ApplicationConstant.FILE_PATH_ROOT = PropertiesUtil.getProperty("upload.filepath.root");
		}
		if (StringUtil.isNotBlank(PropertiesUtil.getProperty("upload.http.domain"))) {
			ApplicationConstant.HTTP_DOMAIN_URL = PropertiesUtil.getProperty("upload.http.domain");
		}
		if (StringUtil.isNotBlank(PropertiesUtil.getProperty("upload.env"))) {
			environmentType = PropertiesUtil.getProperty("upload.env");
		}
		logger.info("ApplicationConstant.FILE_PATH_ROOT=" + ApplicationConstant.FILE_PATH_ROOT
					+ ",ApplicationConstant.CURRENT_STORE_FILE_TYPE="
					+ ApplicationConstant.CURRENT_STORE_FILE_TYPE
					+ ",ApplicationConstant.HTTP_DOMAIN_URL=" + ApplicationConstant.HTTP_DOMAIN_URL
					+ ",environmentType =" + environmentType);
		
	}
	
	//	private static void getIpAddress() {
	//		try {
	//			InetAddress addr = null;
	//			addr = InetAddress.getLocalHost();
	//			ipAddress = addr.getHostAddress();
	//			logger.info(" java Get locahost ipAddress :" + ipAddress
	//						+ " ================================================");
	//		} catch (UnknownHostException e) {
	//			logger.error("get ip address error", e);
	//		}
	//		
	//	}
	
}
