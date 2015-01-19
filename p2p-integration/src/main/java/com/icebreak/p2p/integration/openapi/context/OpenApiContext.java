package com.icebreak.p2p.integration.openapi.context;

import com.icebreak.util.lang.security.DigestUtil.DigestALGEnum;
import com.yiji.openapi.sdk.Constants;

public class OpenApiContext {
	String openApiBizNo;
	String partnerId = Constants.PARTNER_ID;
	String service;
	String securityCheckKey = Constants.SECRETKEY;
	String inputCharset = "utf-8";
	DigestALGEnum signType = DigestALGEnum.MD5;
	String openApiUrl = Constants.OPENAPI_GATEWAY;
	String notifyUrl;
	
	public String getNotifyUrl() {
		return notifyUrl;
	}
	
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	public String getPartnerId() {
		return partnerId;
	}
	
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public String getInputCharset() {
		return inputCharset;
	}
	
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
	
	public String getService() {
		return service;
	}
	
	public DigestALGEnum getSignType() {
		return signType;
	}
	
	public void setSignType(DigestALGEnum signType) {
		this.signType = signType;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getSecurityCheckKey() {
		return securityCheckKey;
	}
	
	public void setSecurityCheckKey(String securityCheckKey) {
		this.securityCheckKey = securityCheckKey;
	}
	
	public String getOpenApiUrl() {
		return openApiUrl;
	}
	
	public void setOpenApiUrl(String openApiUrl) {
		this.openApiUrl = openApiUrl;
	}
	
	public String getOpenApiBizNo() {
		return openApiBizNo;
	}
	
	public void setOpenApiBizNo(String openApiBizNo) {
		this.openApiBizNo = openApiBizNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OpenApiContext [outBizNo=");
		builder.append(openApiBizNo);
		builder.append(", partnerId=");
		builder.append(partnerId);
		builder.append(", service=");
		builder.append(service);
		builder.append(", securityCheckKey=");
		builder.append(securityCheckKey);
		builder.append(", inputCharset=");
		builder.append(inputCharset);
		builder.append(", signType=");
		builder.append(signType);
		builder.append(", openApiUrl=");
		builder.append(openApiUrl);
		builder.append("]");
		return builder.toString();
	}
	
}
