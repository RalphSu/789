package com.yiji.openapi.sdk.support;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.yiji.openapi.sdk.message.BaseRequest;

public class MessageSupport {
	
	/**
	 * 特例缓存
	 */
	private static BiMap<String, String> particularCaseCache = HashBiMap.create();
	
	static {
		particularCaseCache.put("RealNameCertQuery", "RealNameCert.query");
		particularCaseCache.put("RealNameCertSave", "RealNameCert.save");
		particularCaseCache.put("BankCodeBindingAddInfo", "BankCodeBinding.addInfo");
		particularCaseCache.put("BankCodeBindingSetDefault", "BankCodeBinding.setDefault");
		particularCaseCache.put("DeductDepositApply", "DeductDeposit.apply");
		particularCaseCache.put("SpecialTransfer", "trade.pool.transfer2p");
	}
	
	public static String getRequestServiceName(BaseRequest request) {
		
		String serviceNameConvention = StringUtils.substringBefore(request.getClass()
			.getSimpleName(), "Request");
		
		if (particularCaseCache.containsKey(serviceNameConvention)) {
			return StringUtils.uncapitalize(particularCaseCache.get(serviceNameConvention));
		}
		
		return StringUtils.uncapitalize(serviceNameConvention);
	}
	
	public static String getResponseServiceName(String responseServiceName) {
		String capitalize = StringUtils.capitalize(responseServiceName);
		BiMap<String, String> inverseMap = particularCaseCache.inverse();
		if (inverseMap.containsKey(capitalize)) {
			return inverseMap.get(capitalize);
		}
		
		return capitalize;
	}
	
	public static String convertPaticularCase(String particularCase) {
		if (particularCaseCache.containsValue(particularCase)) {
			return StringUtils.capitalize(particularCaseCache.inverse().get(particularCase));
		}
		return StringUtils.capitalize(particularCase);
	}
	
}
