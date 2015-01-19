package com.yiji.openapi.sdk;

import com.icebreak.p2p.util.BusinessNumberUtil;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryRequest;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;
import com.yiji.openapi.sdk.support.Signatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service("apiTool")
public class APITool {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private OpenApiGatewayService openApiGatewayService;
	
	public YzzUserAccountQueryResponse queryUserAccount(String userId) {
		YzzUserAccountQueryRequest queryRequest = new YzzUserAccountQueryRequest(userId);
		return openApiGatewayService.userAccountQuery(queryRequest);
	}

	public static Map<String, String> initBaseParams(String service) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", service);
		params.put("partnerId", Constants.PARTNER_ID);
		params.put("orderNo", BusinessNumberUtil.gainOutBizNoNumber());
		params.put("signType", "MD5");
		return params;
	}

	public String makeRequestUrl(Map<String, String> paramMap) {
		paramMap.put("sign", Signatures.sign(paramMap));
		BufferedReader br = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(com.yiji.openapi.sdk.Constants.OPENAPI_GATEWAY).append("?");
			StringBuffer sb1 = new StringBuffer();
			Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet().iterator();
			int i = 0;
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if (i == 0)
					sb1.append(entry.getKey()).append("=").append(entry.getValue());
				else
					sb1.append("&").append(entry.getKey()).append("=").append(entry.getValue());
				i++;
			}
			sb.append(sb1.toString());
			return sb.toString();
		} catch (Exception e) {
			logger.error("send openApi error", e);
		}
		return "";

	}
}
