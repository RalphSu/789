package com.yiji.openapi.sdk.service.yzz;

import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.message.URLResult;
import com.yiji.openapi.sdk.message.yzz.*;
import com.yiji.openapi.sdk.notify.NotifyHandler;

import java.util.Map;

public interface YzzGatewayService {

	URLResult jumpToYJF(BaseRequest request);
	
	YzzUserSpecialRegisterResponse yzzUserRegister(YzzUserSpecialRegisterRequest request);
	
	void notifyCtrlTransfer(Map<String, String> data, NotifyHandler handler);
	
	void notifyPayTogether(Map<String, String> data, NotifyHandler handler);
	
	void notifyInvestApply(Map<String, String> data, NotifyHandler handler);
	
	void notifyRepay(Map<String, String> data, NotifyHandler handler);
	
	void yzzStrengthenWithdraw(Map<String, String> data, NotifyHandler handler);

	void notifyFastpay(Map<String, String> data, NotifyHandler handler);

	SpecialTransferResponse specialTransfer(SpecialTransferRequest request);

	void notifySpecialTransfer(Map<String, String> data, NotifyHandler handler);

	void notifyYzzPaymentBond(Map<String, String> data, NotifyHandler handler);

}
