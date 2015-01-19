package com.yiji.openapi.sdk.executer;

import com.yiji.openapi.sdk.message.BaseRequest;

public interface RequestMarshall {
	
	/**
	 * 将request对象拼装成HttpPost请求报文
	 * @param request
	 * @return
	 */
	String marshall(BaseRequest request);
	
}
