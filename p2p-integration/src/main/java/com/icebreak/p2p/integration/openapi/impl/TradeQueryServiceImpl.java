package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.TradeQueryService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.result.TradeQueryResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service("tradeQueryService")
public class TradeQueryServiceImpl extends OpenApiServiceBase implements TradeQueryService {
	
	/**
	 * @param userId
	 * @param openApiContext
	 * @return
	 */
	protected TradeQueryResult makeCommonUrl(String userId, String serviceName,
												OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		TradeQueryResult result = new TradeQueryResult();
		try {
			Assert.hasText(userId, "userId不能为空");
			openApiContext.setService(serviceName);
			paramMap.put("userId", userId);
			paramMap.put("notifyUrl", openApiContext.getNotifyUrl() + "/usercenter/userHome");
			paramMap.put("returnUrl", openApiContext.getNotifyUrl() + "/usercenter/userHome");
			String url = makeResposeUrl(paramMap, openApiContext);
			result.setUrl(url);
			result.setSuccess(true);
			return result;
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
