package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.FundsTransferService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.FundsTransferOrder;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("fundsTransferService")
public class FundsTransferServiceImpl extends OpenApiServiceBase implements FundsTransferService {
	static final String	TRADE_TRANSFER_SERVICE_NAME			= "yzzTradeTransfer";
	static final String	TRADE_EXIST_EXCEPTION				= "TRADE_EXIST_EXCEPTION";
	
	/**
	 * @param order
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.FundsTransferService#tradeTransfer(com.icebreak.p2p.integration.openapi.order.FundsTransferOrder, com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public P2PBaseResult tradeTransfer(FundsTransferOrder order, OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		P2PBaseResult result = new P2PBaseResult();
		try {
			order.check();
			openApiContext.setService(TRADE_TRANSFER_SERVICE_NAME);
			paramMap.put("outBizNo", order.getOrderNo());
			paramMap.put("buyerUserId", order.getBuyerUserId());
			paramMap.put("memo", order.getMemo());
			paramMap.put("name", order.getName());
			
			paramMap.put("payerUserId", order.getBuyerUserId());
			paramMap.put("sellerUserId", order.getSellerUserId());
			paramMap.put("systemId", order.getSystemId());
			paramMap.put("subTransCode", order.getTransCode());
			paramMap.put("tradeMemo", order.getTradeMemo());
			paramMap.put("tradeName", order.getTradeName());
			paramMap.put("tradeType", order.getTradeType());
			paramMap.put("unit", order.getUnit());
			paramMap.put("price", order.getPrice().toString());
			paramMap.put("quantity", String.valueOf(order.getQuantity()));
			paramMap.put("tradeAmount", order.getTradeAmount().toString());
			paramMap.put("transferAction", order.getTransferActionEnum().code());//动作解冻转账冻结
			
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			if (!result.isSuccess()) {
				if (TRADE_EXIST_EXCEPTION.equals(getResultValue(dataMap, "resultCode"))) {
					result.setSuccess(true);
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getLocalizedMessage(), e);
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
