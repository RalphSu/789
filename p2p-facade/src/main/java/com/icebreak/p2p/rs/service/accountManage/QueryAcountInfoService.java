package com.icebreak.p2p.rs.service.accountManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryRequest;

public class QueryAcountInfoService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		if (SessionLocalManager.getSessionLocal() == null) {
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		try {
			String acountId = SessionLocalManager.getSessionLocal().getAccountId();
			YzzUserAccountQueryRequest queryRequest = new YzzUserAccountQueryRequest(acountId);
			YzzUserAccountQueryResponse queryAccountResult = openApiGatewayService
				.userAccountQuery(queryRequest);
			if (queryAccountResult.success()) {
				json.put("code", 1);
				json.put("availableBalance",
					new Money(queryAccountResult.getAvailableBalance()).toStandardString());
				json.put("certifyStatus", queryAccountResult.getCertifyStatus());
				json.put("message", "查询成功");
			} else {
				json.put("code", 0);
				json.put("message", "查询失败");
			}
		} catch (Exception e) {
			logger.error("queryUserAccount is error", e);
			json.put("code", 0);
			json.put("message", "查询失败");
		}
		
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.queryAcountInfoService;
	}
	
}
