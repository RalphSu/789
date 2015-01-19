package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.RepayTradeOrder;
import com.icebreak.p2p.integration.openapi.order.TradeQueryOrder;
import com.icebreak.p2p.integration.openapi.result.RepayTradeQueryResult;
import com.icebreak.p2p.integration.openapi.result.RepayTradeResult;

public interface RepayService {
	
	String yzzTradeCreatePoolReverse = "yzzTradeCreatePoolReverse";
	String tradePoolPay = "tradePoolPay";
	String tradeClosePoolReverse = "tradeClosePoolReverse";
	
	/**
	 * 创建还款交易
	 * @param order
	 * @param openApiContext
	 * @return
	 */
	RepayTradeResult createRepayTrade(RepayTradeOrder order, OpenApiContext openApiContext);
	
	/**
	 * 关闭
	 * @param tradeNo
	 * @return
	 */
	RepayTradeResult closeReplyTrade(String tradeNo);
	
	/**
	 * 查询
	 * @param queryOrder
	 * @param openApiContext
	 * @return
	 */
	RepayTradeQueryResult tradeQuery(TradeQueryOrder queryOrder, OpenApiContext openApiContext);
}
