package com.icebreak.p2p.common.services;

import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public interface SmsManagerService {
	public SmsCodeResult verifySmsCode(String account, SmsBizType bizCode, String capNumber,
										boolean del);
	
	public P2PBaseResult sendSmsCode(String account, SmsBizType bizType);


}
