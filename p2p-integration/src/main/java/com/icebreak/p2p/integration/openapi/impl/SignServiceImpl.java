package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.SignService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.info.SignBankInfo;
import com.icebreak.p2p.integration.openapi.order.QuerySignBankOrder;
import com.icebreak.p2p.integration.openapi.order.SignOrder;
import com.icebreak.p2p.integration.openapi.result.SignBankResult;
import com.icebreak.p2p.integration.openapi.result.SignResult;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.yiji.openapi.sdk.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("signService")
public class SignServiceImpl extends OpenApiServiceBase implements SignService {

    public final static String PACT_SIGN_SERVICE_NAME = "signmanybank"; //跳转签约平台，服务名

    public final static String PACT_QUERY_SIGN_SERVICE_NAME = "signmaybankquery"; //查询签约银行卡


    @Override
	public SignResult sign(SignOrder signOrder) {
        SignResult result = new SignResult();
        try {
			signOrder.setService(PACT_SIGN_SERVICE_NAME);
			signOrder.setReturnUrl(Constants.signManyBankReturnUrl);
			signOrder.setNotifyUrl(Constants.signManyBankNotifyUrl);
			signOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			Map<String, String> paramMap = MiscUtil.covertPoToMapNoNullValue(signOrder);
			String url = makeRequestUrl(paramMap);
            result.setUrl(url);
            result.setSuccess(true);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
        }
        return result;
    }

    @Override
    public SignBankResult querySignBankCard(QuerySignBankOrder querySignBankOrder, OpenApiContext openApiContext) {
        Map<String, String> paramMap = new HashMap<String, String>();
        SignBankResult result = new SignBankResult();
        try {
            querySignBankOrder.check();
            openApiContext.setService(PACT_QUERY_SIGN_SERVICE_NAME);
            paramMap.put("userId", querySignBankOrder.getUserId());
            paramMap.put("upUserNo", querySignBankOrder.getUpUserNo());
            paramMap.put("afterStatus", querySignBankOrder.getAfterStatus());
            Map<String, Object> dataMap = senderPost(paramMap, openApiContext);
            result.setSuccess(isSuccessStringSuccess(dataMap));
            if (result.isSuccess()) {
                List<SignBankInfo> signBankInfos = new ArrayList<SignBankInfo>();
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("list");
                for (Map<String, Object> item : mapList) {
                    SignBankInfo signBankInfo = new SignBankInfo();
                    MiscUtil.setInfoPropertyByMap(item, signBankInfo);
                    signBankInfos.add(signBankInfo);
                }
                result.setSignBankInfos(signBankInfos);
            }
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
        }
        return result;
    }


}
