package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public interface SMSService {

	P2PBaseResult sendValidateCode(SmsBizType bizCode, String code, String mobileNumber,
									OpenApiContext openApiContext);
	
	P2PBaseResult sendSMS(String mobileNumber, String smsContent, OpenApiContext openApiContext);
}
