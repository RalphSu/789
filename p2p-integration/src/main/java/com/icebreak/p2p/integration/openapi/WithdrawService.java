package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.WithdrawOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawResult;

public interface WithdrawService {
	WithdrawResult applyWithdrawService(WithdrawOrder order, OpenApiContext openApiContext);
	
	/**
	 * @param order
	 * @param openApiContext
	 * @return
	 */
	WithdrawResult gotoWithdrawUrl(WithdrawOrder order, OpenApiContext openApiContext);
}
