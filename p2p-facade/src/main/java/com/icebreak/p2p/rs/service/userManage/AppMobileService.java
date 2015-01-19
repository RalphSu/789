package com.icebreak.p2p.rs.service.userManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public class AppMobileService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		String mobile = params.get("mobile").toString();
		String business = params.get("business").toString();
		try {
			P2PBaseResult result = smsService.sendSMS(mobile, business, this.getOpenApiContext());
			if (result.isSuccess()) {
				json.put("code", 1);
				json.put("message", "发送手机消息成功");
				logger.info("发送手机消息成功");
			} else {
				json.put("code", 0);
				json.put("message", "发送手机消息失败");
				logger.error("发送手机消息失败--手机号码：" + mobile);
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "参数异常,发送手机消息异常");
			logger.error("参数异常,发送手机消息异常", e);
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.appMobileService;
	}
	
}
