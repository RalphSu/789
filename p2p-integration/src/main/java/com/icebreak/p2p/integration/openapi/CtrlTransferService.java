package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.order.TransferOrder;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.result.TradeResult;

public interface CtrlTransferService {
	
	String accountCtrlTransfer = "account.ctrl.transfer";
	
	/**
	 * 风控站内转账
	 * @param order
	 * @param openApiContext
	 * @return
	 */
	TradeResult transfer(TransferOrder order, OpenApiContext openApiContext);
	
}
