package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.RepayService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.RepayTradeOrder;
import com.icebreak.p2p.integration.openapi.order.TradeQueryOrder;
import com.icebreak.p2p.integration.openapi.result.RepayTradeQueryResult;
import com.icebreak.p2p.integration.openapi.result.RepayTradeResult;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.yiji.openapi.sdk.Constants;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("repayService")
public class RepayServiceImpl extends OpenApiServiceBase implements RepayService {
	
	/**
	 * @param order
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.RepayService#createRepayTrade(com.icebreak.p2p.integration.openapi.order.RepayTradeOrder,
	 * com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public RepayTradeResult createRepayTrade(RepayTradeOrder order, OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		RepayTradeResult result = new RepayTradeResult();
		try {
			order.check();
			openApiContext.setService(yzzTradeCreatePoolReverse);
			openApiContext.setNotifyUrl(Constants.repayNotifyUrl);
			paramMap.put("payerUserId", order.getPayerUserId());
			paramMap.put("tradeName", order.getTradeName());
			paramMap.put("tradeBizProductCode", order.getTradeBizProductCode());
			paramMap.put("tradeAmount", order.getTradeAmount().toString());
			paramMap.put("memo", order.getTradeMemo());
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			
			result.setChannelId((String) dataMap.get("channelId"));
			result.setOrderNo((String) dataMap.get("orderNo"));
			result.setResultCode((String) dataMap.get("resultCode"));
			result.setResultMessage((String) dataMap.get("resultMessage"));
			if (result.isSuccess()) {
				result.setTradeNo((String) dataMap.get("tradeNo"));
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * @param tradeNo
	 * @param openApiContext
	 * @return
	 * com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public RepayTradeResult closeReplyTrade(String tradeNo) {
		OpenApiContext openApiContext = new OpenApiContext();
		openApiContext.setOpenApiBizNo(BusinessNumberUtil.gainOutBizNoNumber());
		Map<String, String> paramMap = new HashMap<String, String>();
		RepayTradeResult result = new RepayTradeResult();
		try {
			openApiContext.setService(tradeClosePoolReverse);
			paramMap.put("tradeNo", tradeNo);
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			
			result.setOrderNo((String) dataMap.get("orderNo"));
			result.setTradeNo(tradeNo);
			result.setResultCode((String) dataMap.get("resultCode"));
			result.setResultMessage((String) dataMap.get("resultMessage"));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

	/**
	 * @param queryOrder
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.RepayService#tradeQuery(com.icebreak.p2p.integration.openapi.order.TradeQueryOrder, com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public RepayTradeQueryResult tradeQuery(TradeQueryOrder queryOrder,
											OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		RepayTradeQueryResult result = new RepayTradeQueryResult();
		try {
			openApiContext.setService(tradeClosePoolReverse);
//			paramMap.put("tradeNo", tradeNo);
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			
			result.setChannelId((String) dataMap.get("channelId"));
			result.setOrderNo((String) dataMap.get("orderNo"));
			result.setResultCode((String) dataMap.get("resultCode"));
			result.setResultMessage((String) dataMap.get("resultMessage"));
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
	
}
