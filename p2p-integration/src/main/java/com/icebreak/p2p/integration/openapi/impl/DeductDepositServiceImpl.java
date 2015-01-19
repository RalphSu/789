package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.DeductDepositService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.DepositDeductOrder;
import com.icebreak.p2p.integration.openapi.order.EBankDepositDeductOrder;
import com.icebreak.p2p.integration.openapi.result.DeductDepositResult;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.message.common.funds.DeductDepositApplyResponse;
import com.yiji.openapi.sdk.util.BeanMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("deductDepositService")
public class DeductDepositServiceImpl extends OpenApiServiceBase implements DeductDepositService {
	
	private static final String	DEDUCT_DEPOSIT_SERVICE_NAME			= "yzzNewDeduct";
	private static final String	APPLY_EBANK_DEPOSIT_SERVICE_NAME	= "deposit";
	
	@Override
	public DeductDepositApplyResponse applyDeductDeposit(DepositDeductOrder order,
													OpenApiContext openApiContext) {
		DeductDepositApplyResponse response = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			order.check();
			openApiContext.setService(DEDUCT_DEPOSIT_SERVICE_NAME);
			paramMap.put("outBizNo", order.getOrderNo());
			paramMap.put("bankProvName", order.getProvName());
			paramMap.put("bankCityName", order.getCityName());
			paramMap.put("bankAccountNo", order.getBankAccountNo());
			paramMap.put("bankAccountName", order.getBankAccountName());
			paramMap.put("memo", order.getMemo());
			paramMap.put("userId", order.getUserId());
			//paramMap.put("amount", order.getAmount().toString());
			paramMap.put("money", order.getAmount().toString());
			paramMap.put("bankCode", order.getBankCode());
			paramMap.put("notifyUrl", Constants.DEPOSIT_NOTIFY_URL);
			Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
			response = BeanMapper.map(dataMap, DeductDepositApplyResponse.class);
			//MiscUtil.setInfoPropertyByMap(instructionInfo, depositInfo);
			if (StringUtil.equalsIgnoreCase("T",response.getSuccess())) {
			} else {
				if ("BIZ_PROCESSING".equals(response.getResultCode())) {
				} else {
				}
			}
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return response;
	}
	
	@Override
	public DeductDepositResult applyEBankDeposit(EBankDepositDeductOrder order) {
		DeductDepositResult result = new DeductDepositResult();
		try {
			order.setService(APPLY_EBANK_DEPOSIT_SERVICE_NAME);
			order.setReturnUrl(Constants.eBankDepositReturnUrl);
			order.setNotifyUrl(Constants.eBankDepositNotifyUrl);
			order.setUserId(order.getUserId());
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			Map<String, String> paramMap = MiscUtil.covertPoToMapNoNullValue(order);
			String url = makeRequestUrl(paramMap);
			result.setUrl(url);
			result.setDepositId(order.getOrderNo());
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
}
