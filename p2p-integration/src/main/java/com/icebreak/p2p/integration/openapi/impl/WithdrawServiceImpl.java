package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.WithdrawService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.WithdrawOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawResult;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.ws.service.ResultEnum;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service("withdrawService")
public class WithdrawServiceImpl extends OpenApiServiceBase implements WithdrawService {
	
	String	WITHDRAW_SERVICE_NAME			= "applyWithdraw";
	String	GOTO_WITHDRAW_URL_SERVICE_NAME	= "ppmStrengthenWithdraw";
	
	/**
	 * @param order
	 * @param openApiContext
	 * @return
	 * @see com.icebreak.p2p.integration.openapi.WithdrawService#applyWithdrawService(com.icebreak.p2p.integration.openapi.order.WithdrawOrder, com.icebreak.p2p.integration.openapi.context.OpenApiContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WithdrawResult applyWithdrawService(WithdrawOrder order, OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		WithdrawResult result = new WithdrawResult();
		try {
			order.check();
			openApiContext.setService(WITHDRAW_SERVICE_NAME);
			paramMap.put("outBizNo", order.getOrderNo());
			paramMap.put("provName", order.getProvName());
			paramMap.put("cityName", order.getCityName());
			paramMap.put("bankAccountNo", order.getBankAccountNo());
			paramMap.put("bankAccountName", order.getBankAccountName());
			paramMap.put("memo", order.getMemo());
			paramMap.put("bizIdentity", "EASY_TURNOVER");
			paramMap.put("bizNo", "520");
			paramMap.put("bankCnapsNo", order.getBankCnapsNo());
			paramMap.put("owner", "EASY_TURNOVER");
			paramMap.put("subOwner", "p2pCommonProduct");
			// TODO 根据用户真实类型选择
			paramMap.put("publicTag", order.getPublicTag().getCode());
			paramMap.put("userId", order.getUserId());
			paramMap.put("amount", order.getAmount().toString());
			paramMap.put("bankCode", order.getBankCode());
			
			paramMap.put("realName", order.getBankAccountName());
			paramMap.put("notifyUrl", openApiContext.getNotifyUrl()
										+ "/asynchronous/asysGetWithdrawalRsult");
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			JSONObject jsonObject = new JSONObject();
			jsonObject.putAll(dataMap);
			result.setMemo(jsonObject.toJSONString());
			setReturnResult(result, dataMap);
			if (result.isSuccess()) {
				String withdrawId = getResultValue(dataMap, "instructionInfo", "withdrawId");
				result.setWithdrawId(withdrawId);
				String openApiErrorCode = getResultValue(dataMap, "errCodeCtx", "code");
				result.setOpenApiErrorCode(openApiErrorCode);
				if (result.isRepeat()) {
					String openApiResultCode = getResultValue(dataMap, "errCodeCtx", "message");
					if (!ResultEnum.EXECUTE_SUCCESS.code().equals(openApiResultCode)) {
						result.setSuccess(false);
						result.setOpenApiErrorCode(openApiResultCode);
					}
				}
			} else {
				String resultCode = (String) dataMap.get("resultCode");
				result.setOpenApiErrorCode(resultCode);
				//				if (!result.isRepeat()) {
				//					result.setOpenApiErrorCode(resultCode);
				//				}
			}
			result.setOutBizNo(order.getOrderNo());
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
	
	@Override
	public WithdrawResult gotoWithdrawUrl(WithdrawOrder order, OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();
		WithdrawResult result = new WithdrawResult();
		try {
			Assert.hasText(order.getUserId(), "userId 不能为空");
			openApiContext.setService(GOTO_WITHDRAW_URL_SERVICE_NAME);
			paramMap.put("userId", order.getUserId());
			paramMap.put("chargeCode", AppConstantsUtil.getWithdrawChargeCode());

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
