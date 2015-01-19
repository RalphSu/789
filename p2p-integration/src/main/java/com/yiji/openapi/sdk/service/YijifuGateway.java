package com.yiji.openapi.sdk.service;

import com.yiji.openapi.sdk.executer.OpenApiServiceExecuter;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;
import com.yiji.openapi.sdk.service.common.impl.OpenApiGatewayServiceImpl;
import com.yiji.openapi.sdk.service.yzz.YzzGatewayService;
import com.yiji.openapi.sdk.service.yzz.impl.YzzGatewayServiceImpl;

public class YijifuGateway {

	private static OpenApiServiceExecuter openApiServiceExecuter = new OpenApiServiceExecuter();

	private static Object lock = new Object();

	private static OpenApiGatewayService openApiGatewayService;
	private static YzzGatewayService yzzGatewayService;

	public static OpenApiGatewayService getOpenApiGatewayService() {
		if (openApiGatewayService == null) {
			synchronized (lock) {
				if (openApiGatewayService == null) {
					openApiGatewayService = new OpenApiGatewayServiceImpl(openApiServiceExecuter);
				}
			}
		}
		return openApiGatewayService;
	}
	
	public static YzzGatewayService getYzzGatewayService() {
		if (yzzGatewayService == null) {
			synchronized (lock) {
				if (yzzGatewayService == null) {
					yzzGatewayService = new YzzGatewayServiceImpl(openApiServiceExecuter);
				}
			}
		}
		return yzzGatewayService;
	}

}
