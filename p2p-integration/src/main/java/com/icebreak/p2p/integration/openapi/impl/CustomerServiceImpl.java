package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.CustomerService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.enums.RealNameBusinessStatusEnum;
import com.icebreak.p2p.integration.openapi.order.MobileBindOrder;
import com.icebreak.p2p.integration.openapi.result.CustomerResult;
import com.icebreak.p2p.integration.openapi.result.RealNameStatusResult;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.ws.enums.BooleanEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.yiji.openapi.sdk.APITool;
import com.yiji.openapi.sdk.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service("customerService")
public class CustomerServiceImpl extends OpenApiServiceBase implements CustomerService {

    public static final String REAL_NAME_CERT_QUERY = "realNameCert.query";
    public static final String UPDATE_MOBILE_BINDING_CUSTOMER = "updateMobileBindingCustomer";
    public static final String APPLY_MOBILE_BINDING_CUSTOMER = "applyMobileBindingCustomer";
    public static final String FORWARD_YJF_SIT = "forwardYJFSit";
	public static final String FORWARD_YJF_NEW_AUTHORIZE = "new_authorize";

	public static final String USER_ID = "userId";

    @Override
	public CustomerResult gotoYjfSit(String userId) {
		Map<String, String> paramMap;
        CustomerResult result = new CustomerResult();
        try {
			      paramMap = APITool.initBaseParams(FORWARD_YJF_SIT);
            if(null != userId && !"".equals(userId)) {
                paramMap.put(USER_ID, userId);
            }
			      String url = makeRequestUrl(paramMap);
            result.setUrl(url);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            logger.error(e.getLocalizedMessage(), e);
        }
        return result;
    }



    @SuppressWarnings("unchecked")
    @Override
    public P2PBaseResult updateMobileBinding(MobileBindOrder mobileBindOrder,
                                             OpenApiContext openApiContext) {
        Map<String, String> paramMap = new HashMap<String, String>();
        P2PBaseResult result = new P2PBaseResult();
        try {
            mobileBindOrder.check();
            if (mobileBindOrder.getIsNew() == BooleanEnum.NO)
                openApiContext.setService(UPDATE_MOBILE_BINDING_CUSTOMER);
            else
                openApiContext.setService(APPLY_MOBILE_BINDING_CUSTOMER);
            paramMap.put("userId", mobileBindOrder.getUserId());
            paramMap.put("mobile", mobileBindOrder.getMobile());
            Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
            setReturnResult(result, dataMap);
            if (!result.isSuccess()) {
                if ("NO_INFO".equals(getResultValue(dataMap, RESULT_CODE))
                        && mobileBindOrder.getIsNew() == BooleanEnum.NO) {
                    mobileBindOrder.setIsNew(BooleanEnum.YES);
                    openApiContext.setOpenApiBizNo(BusinessNumberUtil.gainOutBizNoNumber());
                    result = this.updateMobileBinding(mobileBindOrder, openApiContext);
                }
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




    @Override
    public RealNameStatusResult queryRealNameCert(String userId, OpenApiContext openApiContext) {
        Map<String, String> paramMap = new HashMap<String, String>();
        RealNameStatusResult result = new RealNameStatusResult();
        try {
            Assert.hasText(userId, "accountId不能为空");
            openApiContext.setService(REAL_NAME_CERT_QUERY);

            paramMap.put("userId", userId);

            Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
            setReturnResult(result, dataMap);
            if (isSuccess(dataMap)) {
                result.setBusinessStatusEnum(RealNameBusinessStatusEnum.getByCode((String) dataMap
                        .get("stauts")));
                result.setMsg((String) dataMap.get("msg"));
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
	
	@Override
	public CustomerResult gotoYjfRealNameCert(String userId) {
		Map<String, String> paramMap;
		CustomerResult result = new CustomerResult();
		try {
			paramMap = APITool.initBaseParams(FORWARD_YJF_NEW_AUTHORIZE);
			paramMap.put(USER_ID, userId);
			paramMap.put("notifyUrl", Constants.realNameCertNotifyUrl);
			paramMap.put("returnUrl", Constants.realNameCertReturnUrl);
			String url = makeRequestUrl(paramMap);
			result.setUrl(url);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
}
