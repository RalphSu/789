package com.yiji.openapi.sdk.message;

import com.yiji.openapi.sdk.message.common.UserRegisterResponse;
import com.yiji.openapi.sdk.support.MessageSupport;
import com.yiji.openapi.sdk.support.PackageScan;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class MessageFactory {
	
	private static Map<String, Class<?>> messages = new HashMap<String, Class<?>>();
	private static String rootPackage = "com.yiji.openapi.sdk.message";
	static {
		load();
	}
	
	public static BaseResponse getResponse(String serviceName) {
		return (BaseResponse) newInstance(messages.get(MessageSupport
			.getResponseServiceName(serviceName) + "Response"));
	}
	
	public static BaseNotify getNotify(String serviceName) {
		return (BaseNotify) newInstance(messages
			.get(StringUtils.capitalize(serviceName) + "Notify"));
	}
	
	public static <T> T newInstance(Class<T> clazz) {
		try {
			return (T) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("InstantiationException:" + clazz);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("IllegalAccessException:" + clazz);
		}
	}
	
	private static void load() {
		if (messages.isEmpty()) {
			synchronized (messages) {
				if (messages.isEmpty()) {
					messages = PackageScan.scan(rootPackage);
				}
			}
		}
	}
	
	private MessageFactory() {
		super();
	}
	
	public static void main(String[] args) {
		UserRegisterResponse urr = (UserRegisterResponse) MessageFactory
			.getResponse("UserRegister");
		System.out.println(urr);
	}
	
}
