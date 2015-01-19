package com.yiji.openapi.sdk.executer.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiji.openapi.sdk.exception.OpenApiClientException;
import com.yiji.openapi.sdk.executer.ResponseUnmarshall;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.MessageFactory;
import com.yiji.openapi.sdk.support.Signatures;
import com.yiji.openapi.sdk.util.BeanMapper;

public class HttpPostNotifyUnmarshall implements
										ResponseUnmarshall<Map<String, String>, BaseNotify> {
	private static Logger logger = LoggerFactory.getLogger(HttpPostNotifyUnmarshall.class);
	/**
	 * 签名key
	 */
	public static final String SIGN_KEY = "sign";
	
	@Override
	public BaseNotify unmarshall(String serviceName, Map<String, String> data) {
		try {
//			verifySign(data);
			BaseNotify notify = MessageFactory.getNotify(serviceName);
			notify.setServiceName(serviceName);
			notify = BeanMapper.map(data, notify.getClass());
			notify.unmarshall(data);
			logger.info("解析通知报文[" + serviceName + "] -> " + notify);
			return notify;
		} catch (Exception e) {
			logger.info("解析通知报文失败[" + serviceName + "]:" + e.getMessage(), e);
			throw new OpenApiClientException("解析通知报文失败[" + serviceName + "]", e);
		}
	}
	
	public void verifySign(Map<String, String> data) {
		String serverSign = data.get(SIGN_KEY);
		data.remove(SIGN_KEY);
		String signature = Signatures.sign(data);
		if (!StringUtils.equals(signature, serverSign)) {
			logger.info("server-sign:" + serverSign + "}");
			throw new OpenApiClientException("签名验证未通过");
		} else {
			logger.info("notify verify sign ok");
		}
	}
}
