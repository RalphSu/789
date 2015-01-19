package com.icebreak.p2p.integration.openapi.impl;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.integration.openapi.RemoteTradeService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.InvestOrder;
import com.icebreak.p2p.integration.openapi.order.PayTogetherOrder;
import com.icebreak.p2p.integration.openapi.order.PayTogetherSubOrder;
import com.icebreak.p2p.integration.openapi.order.TradeOrder;
import com.icebreak.p2p.integration.openapi.result.InvestReturnRequest;
import com.icebreak.p2p.integration.openapi.result.TradeResult;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.util.lang.util.ListUtil;
import com.yiji.openapi.sdk.Constants;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("remoteTradeService")
public class RemoteTradeServiceImpl extends OpenApiServiceBase implements RemoteTradeService {
	
	@Override
	public TradeResult createTrade(TradeOrder order, OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		TradeResult result = new TradeResult();
		try {
			order.check();
			openApiContext.setNotifyUrl(Constants.investNotifyUrl);
			openApiContext.setService(yzzTradeCreatePoolTogether);
			paramMap.put("sellerUserId", order.getSellerUserId());
			paramMap.put("tradeName", order.getTradeName());
			paramMap.put("tradeBizProductCode", order.getTradeBizProductCode());
			paramMap.put("tradeAmount", order.getTradeAmount().toString());
			paramMap.put("tradeMemo", order.getTradeMemo());
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			setBaseResult(result, dataMap);
			
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
	 * @param order
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.RemoteTradeService#invest(com.icebreak.p2p.integration.openapi.order.InvestOrder,
	 * com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public InvestReturnRequest invest(InvestOrder order, OpenApiContext openApiContext) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		InvestReturnRequest result = new InvestReturnRequest();
		try {
			order.check();
			openApiContext.setService(payerApply);
			paramMap.put("payerUserId", order.getPayerUserId());
			paramMap.put("tradeNo", order.getTradeNo());
			paramMap.put("tradeAmount", order.getTradeAmount().toString());
			paramMap.put("tradeName", order.getTradeName());
			paramMap.put("tradeMemo", order.getTradeMemo());
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			result.setTradeNo(get("tradeNo", dataMap));
			result.setSubTradeNo(get("subTradeNo", dataMap));
			result.setTradeStatus(get("tradeStatus", dataMap));
			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return result;
		
	}
	
	/**
	 * @param order
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.RemoteTradeService#payTogether(com.icebreak.p2p.integration.openapi.order.PayTogetherOrder,
	 * com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public TradeResult payTogether(PayTogetherOrder order, OpenApiContext openApiContext) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		TradeResult result = new TradeResult();
		try {
			order.check();
			openApiContext.setService(tradePayPoolTogether);
			paramMap.put("tradeNo", order.getTradeNo());
			paramMap.put("tradeMemo", order.getTradeMemo());
			List<PayTogetherSubOrder> subOrders =  order.getTradePoolSubTansferOrders();
			if (ListUtil.isNotEmpty(subOrders)) {
				PayTogetherSubOrder subOrder = subOrders.get(0);
				if (new BigDecimal(subOrder.getTransferAmount()).longValue() > 0) {
					paramMap.put("tradePoolSubTansferOrders",
							JSONObject.toJSONString(order.getTradePoolSubTansferOrders()));
				}
			}

			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			setBaseResult(result, dataMap);
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
	
	/**
	 * @param tradeNo
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.RemoteTradeService#closeTrade(java.lang.String,
	 * com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@Override
	public TradeResult closeTrade(String tradeNo, OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		TradeResult result = new TradeResult();
		try {
			/***
			 *
			 service	接口名称		不可空	tradeClosePoolTogether
			 partnerId	 合作伙伴ID	 String(20)	不可空	 20121015300000032621
			 orderNo	 合作伙伴网站唯一订单号	 String(16)	 不可空	2014070100000001
			 sign	 签名		 不可空	 7d314d22efba4f336fb187697793b9d2
			 signType	 签名方式
			 可空，如果为空，签名方式采用签约时约定的方式	 MD5
			 returnUrl	 页面跳转同步通知页面路径	 String(64)	 可空	http://api.test.yiji.net/atinterface/receive_return.htm
			 errorNotifyUrl	 请求出错时的通知页面路径	 String(64)	 可空	 http://api.test.yiji.net/atinterface/receive_return.htm
			 普通参数	参数描述	类型/长度	是否可空	特殊说明/举例
			 tradeNo	交易唯一编号	String(20)	不可空
			 *
			 */
			openApiContext.setService(tradeClosePoolTogether);
			openApiContext.setOpenApiBizNo(BusinessNumberUtil.gainOutBizNoNumber());
			paramMap.put("tradeNo", tradeNo);
			paramMap.put("returnUrl", "");
			paramMap.put("errorNotifyUrl", "");

			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			setReturnResult(result, dataMap);
			setBaseResult(result, dataMap);
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
