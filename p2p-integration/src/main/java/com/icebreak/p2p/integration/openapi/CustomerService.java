package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.MobileBindOrder;
import com.icebreak.p2p.integration.openapi.result.CustomerResult;
import com.icebreak.p2p.integration.openapi.result.RealNameStatusResult;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public interface CustomerService {
	
	/**
	 * 查询实名状态
	 * @param userId
	 * @param openApiContext
	 * @return
	 */
	RealNameStatusResult queryRealNameCert(String userId, OpenApiContext openApiContext);
	
	/**
	 * @param mobileBindOrder
	 * @param openApiContext
	 * @return
	 */
	P2PBaseResult updateMobileBinding(MobileBindOrder mobileBindOrder, OpenApiContext openApiContext);
	
	/**
	 * 跳转至易极付官网
	 * @param userId
	 * @return
	 */
	CustomerResult gotoYjfSit(String userId);

	/**
	 * 跳转至易极付做增强实名认证
	 * @param userId
	 * @return
	 */
	CustomerResult gotoYjfRealNameCert(String userId);
}
