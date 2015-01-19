package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.InvestOrder;
import com.icebreak.p2p.integration.openapi.order.PayTogetherOrder;
import com.icebreak.p2p.integration.openapi.order.TradeOrder;
import com.icebreak.p2p.integration.openapi.result.InvestReturnRequest;
import com.icebreak.p2p.integration.openapi.result.TradeResult;

public interface RemoteTradeService {
	
	String yzzTradeCreatePoolTogether = "yzzTradeCreatePoolTogether";
	String payerApply = "payerApply";
	String tradePayPoolTogether = "tradePayPoolTogether";
	String tradeClosePoolTogether = "tradeClosePoolTogether";
	String tradeQuery = "tradeQuery";
	
	/**
	 * 创建交易
	 * @param order
	 * @param openApiContext
	 * @return
	 */
	TradeResult createTrade(TradeOrder order, OpenApiContext openApiContext);
	
	/**
	 * 投资
	 * @param order
	 * @param openApiContext
	 * @return
	 */
	InvestReturnRequest invest(InvestOrder order, OpenApiContext openApiContext);
	
	/**
	 * 集体付款
	 * @param order
	 * @param openApiContext
	 * @return
	 */
	TradeResult payTogether(PayTogetherOrder order, OpenApiContext openApiContext);
	
	/**
	 * 关闭
	 * @param tradeNo
	 * @param openApiContext
	 * @return
	 */
	TradeResult closeTrade(String tradeNo, OpenApiContext openApiContext);
}
