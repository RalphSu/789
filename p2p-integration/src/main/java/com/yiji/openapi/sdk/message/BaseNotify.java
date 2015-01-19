package com.yiji.openapi.sdk.message;

import java.util.Map;

public class BaseNotify {
	/** 官方api文档中无 */
	private String userId;
	private String serviceName;
	/** 通知时间 通知的发送时间。 格式为yyyy-MM-dd HH:mm:ss */
	private String notifyTime;
	/** 签名方式 */
	private String signType;
	/** 签名 */
	private String sign;

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	protected void validate() {

	}

	public void unmarshall(Map<String, String> data) {
		doUnmarshall(data);
	}

	protected void doUnmarshall(Map<String, String> data) {

	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
