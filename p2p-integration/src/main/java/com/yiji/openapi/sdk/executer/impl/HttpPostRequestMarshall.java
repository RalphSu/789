package com.yiji.openapi.sdk.executer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.exception.OpenApiClientException;
import com.yiji.openapi.sdk.executer.RequestMarshall;
import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.support.Signatures;
import com.yiji.openapi.sdk.util.Encodes;
import com.yiji.openapi.sdk.util.JsonMapper;
import com.yiji.openapi.sdk.util.Reflections;

public class HttpPostRequestMarshall implements RequestMarshall {
	
	private static Logger logger = LoggerFactory.getLogger(HttpPostRequestMarshall.class);
	
	@Override
	public String marshall(BaseRequest request) {
		try {
			List<String> parameters = parseAndSortRequest(request);
			request.setSign(Signatures.sign(parameters));
			StringBuilder sb = new StringBuilder();
			String[] pairs;
			for (String pair : parameters) {
				pairs = pair.split("=");
				sb.append(pairs[0]).append("=")
					.append(Encodes.urlEncode(pairs[1], Constants.CHARSET)).append("&");
			}
			sb.append("sign=").append(request.getSign());
			return sb.toString();
		} catch (Exception e) {
			throw new OpenApiClientException("组装请求报文失敗 request：" + request, e);
		}
		
	}
	
	protected String sign(List<String> parameters) {
		String stringToSign = StringUtils.join(parameters.iterator(), "&");
		String signature = DigestUtils.md5Hex(stringToSign + Constants.SECRETKEY);
		logger.trace("stringToSign:" + stringToSign + ", signature:" + signature);
		return signature;
	}
	
	protected List<String> parseAndSortRequest(BaseRequest request) {
		Set<String> simplePropertyNames = Reflections.getFieldNames(request.getClass());
		String[] propertyNames = simplePropertyNames.toArray(new String[] {});
		
		List<String> pairNameValues = new ArrayList<String>(propertyNames.length);
		String value;
		for (String name : propertyNames) {
			Object obj = Reflections.invokeGetter(request, name);
			value = toValue(obj);
			if (StringUtils.isNotBlank(value)) {
				
				pairNameValues.add(name + "=" + value);
			}
		}
		Collections.sort(pairNameValues);
		return pairNameValues;
	}
	
	private String toValue(Object object) {
		if (object == null) {
			return "";
		}
		
		if (isPrimitiveAndStringType(object)) {
			return object.toString();
		}
		
		return JsonMapper.nonDefaultMapper().toJson(object);
	}
	
	private boolean isPrimitiveAndStringType(Object obj) {
		
		if (obj.getClass().isPrimitive() || obj instanceof String) {
			return true;
		}
		
		return false;
	}
	
}
