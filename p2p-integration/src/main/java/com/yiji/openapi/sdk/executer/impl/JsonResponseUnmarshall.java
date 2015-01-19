package com.yiji.openapi.sdk.executer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.yiji.openapi.sdk.exception.OpenApiClientException;
import com.yiji.openapi.sdk.exception.OpenApiServiceException;
import com.yiji.openapi.sdk.executer.ResponseUnmarshall;
import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.message.MessageFactory;
import com.yiji.openapi.sdk.support.Signatures;
import com.yiji.openapi.sdk.util.BeanMapper;
import com.yiji.openapi.sdk.util.JsonMapper;
import com.yiji.openapi.sdk.util.Reflections;

public class JsonResponseUnmarshall implements ResponseUnmarshall<String, BaseResponse> {
	
	private static Logger logger = LoggerFactory.getLogger(JsonResponseUnmarshall.class);
	
	private static List<String> paticularCaseList = Lists.newArrayList("tradeQuery");
	
	@Override
	public BaseResponse unmarshall(String serviceName, String responseMessage) {
		try {
			
			String sign = null;
			
			BaseResponse response = MessageFactory.getResponse(serviceName);
			BeanMapper.copy(
				JsonMapper.nonDefaultMapper().fromJson(responseMessage, response.getClass()),
				response);
			response.unmarshall(responseMessage);
			if (StringUtils.isNotBlank(response.getSignType())
				&& StringUtils.isNotBlank(response.getSign())) {
				
				if (paticularCaseList.contains(serviceName)) {
					sign = unwarpDataMapAndSign(serviceName, response);
				} else {
					sign = Signatures.sign(response);
				}
				if (StringUtils.isNotBlank(sign) && !StringUtils.equals(sign, response.getSign())) {
					throw new OpenApiServiceException("验签失败，期望：" + sign + " 实际："
														+ response.getSign());
				}
			}
			logger.info("解析响应报文[" + response.getOrderNo() + ":" + serviceName + "] : " + response);
			return response;
		} catch (OpenApiServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new OpenApiClientException("解析响应报文失败[" + serviceName + "]", e);
		}
		
	}
	
	/**
	 * @param serviceName
	 * @param response
	 * @return
	 */
	private String unwarpDataMapAndSign(String serviceName, BaseResponse response) {
		String sign = null;
		Set<String> simplePropertyNames = Reflections.getSimpleFieldNames(response.getClass());
		String[] propertyNames = simplePropertyNames.toArray(new String[] {});
		
		List<String> propertyNameList = new ArrayList<String>(propertyNames.length);
		if (propertyNameList.contains("data")) {
			@SuppressWarnings("unchecked")
			HashMap<String, String> data = (HashMap<String, String>) Reflections.invokeGetter(
				response, "data");
			sign = Signatures.sign(data);
		}
		return sign;
	}
}
