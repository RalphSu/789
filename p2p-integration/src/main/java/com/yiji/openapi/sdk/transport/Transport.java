package com.yiji.openapi.sdk.transport;

public interface Transport {
	
	/**
	 * 发送请求，接收响应
	 * @param request
	 * @return
	 */
	String exchange(String request);
}
