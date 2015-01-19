package com.yiji.openapi.sdk.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.util.Reflections;

public class Signatures {
	
	private static final Logger logger = LoggerFactory.getLogger(Signatures.class);
	
	public static String sign(Object object) {
		Set<String> simplePropertyNames = Reflections.getSimpleFieldNames(object.getClass());
		String[] propertyNames = simplePropertyNames.toArray(new String[] {});
		
		List<String> pairNameValues = new ArrayList<String>(propertyNames.length);
		String value;
		for (String name : propertyNames) {
			value = (String) Reflections.invokeGetter(object, name);
			if (StringUtils.isNotBlank(value) && !StringUtils.equals(name, "sign")) {
				pairNameValues.add(name + "=" + value);
			}
		}
		Collections.sort(pairNameValues);
		return sign(pairNameValues);
	}
	
	public static String sign(List<String> parameters) {
		String stringToSign = StringUtils.join(parameters.iterator(), "&");
		String signature = DigestUtils.md5Hex(stringToSign + Constants.SECRETKEY);
		logger.debug("stringToSign:" + stringToSign + ", signature:" + signature);
		return signature;
	}
	
	public static String sign(Map<String, String> formData) {
		Map<String, String> sortedMap = new TreeMap<String, String>(formData);
		if (sortedMap.containsKey("sign")) {
			sortedMap.remove("sign");
		}
		StringBuffer stringToSign = new StringBuffer();
		if (sortedMap.size() > 0) {
			for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
				stringToSign.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
			}
			stringToSign.deleteCharAt(stringToSign.length() - 1);
		}
		stringToSign.append(Constants.SECRETKEY);
		String signature = DigestUtils.md5Hex(stringToSign.toString());
		logger.info("stringToSign:" + stringToSign + ",signature:" + signature);
		return signature;
	}
	
	public static void main(String[] args) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", "12348912398741234");
		sign(data);
	}
	
}
