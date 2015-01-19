package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.FundsTransferOrder;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public interface FundsTransferService {
	/**
	 * 买家卖家支付转账
	 * @param order
	 * @return
	 */
	public P2PBaseResult tradeTransfer(FundsTransferOrder order, OpenApiContext openApiContext);
	
}
