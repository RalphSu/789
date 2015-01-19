package com.icebreak.p2p.integration.openapi.impl;

import com.icebreak.p2p.integration.openapi.SMSService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.client.sms.ISender;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.util.env.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("smsService")
public class SMSServiceImpl extends OpenApiServiceBase implements SMSService {
	public final static String PHONE_NOS = "phoneNos";
	public final static String SMS_CTX = "smsContext";
	public final static String SMS_SERVICE = "sms";
	public final static String STRATEGY = "strategy";

	@Resource(name = "linkSmsSender")
//	@Resource(name = "smsSender")
	private ISender smsSender;
	
	@Override
	public P2PBaseResult sendValidateCode(SmsBizType bizCode, String code, String mobileNumber,
											OpenApiContext openApiContext) {
		P2PBaseResult resultBase = new P2PBaseResult();
		if (!CommonUtil.checkMobile(mobileNumber)) {
			resultBase.setMessage("电话号码不正确");
			return resultBase;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("您本次").append(bizCode.getMessage()).append("的验证码为：").append(code)
			.append("，请妥善保管。");
		//非本地环境才发送验证码
		if(!Env.isDev()) {
			smsSender.send(mobileNumber,sb.toString());
		}
		resultBase.setSuccess(true);
		return resultBase;
	}
	
	@Override
	public P2PBaseResult sendSMS(String mobileNumber, String smsContent,
									OpenApiContext openApiContext) {
		P2PBaseResult resultBase = new P2PBaseResult();
		try {
			smsSender.send(mobileNumber,smsContent);
			resultBase.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultBase;
	}
}
