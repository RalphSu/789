package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.CtrlTransferService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.TransferOrder;
import com.icebreak.p2p.integration.openapi.result.TradeResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("ctrlTransferService")
public class CtrlTransferServiceImpl extends OpenApiServiceBase implements CtrlTransferService {
	
	/**
	 * @param order
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.CtrlTransferService#transfer(com.icebreak.p2p.integration.openapi.order.TransferOrder,
	 * com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public TradeResult transfer(TransferOrder order, OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		TradeResult result = new TradeResult();
		try {
			order.check();
			openApiContext.setService(accountCtrlTransfer);
			paramMap.put("ctrlTransferType", openApiContext.getPartnerId());
			paramMap.put("tradeBizProductCode", order.getTradeBizProductCode());
			paramMap.put("tradeName", order.getTradeName());
			paramMap.put("payerUserId", order.getPayerUserId());
			paramMap.put("sellerUserId", order.getSellerUserId());
			paramMap.put("tradeAmount", order.getTradeAmount().toString());
			paramMap.put("tradeMemo", order.getTradeMemo());
			
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			setBaseResult(result, dataMap);
			
			if (result.isSuccess()) {
				result.setTradeNo((String) dataMap.get("tradeNo"));
			}
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
