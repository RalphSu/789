package com.yiji.openapi.sdk.service.yzz.impl;

import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.executer.OpenApiServiceExecuter;
import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.message.URLResult;
import com.yiji.openapi.sdk.message.yzz.*;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import com.yiji.openapi.sdk.service.AbstractGatewayService;
import com.yiji.openapi.sdk.service.yzz.YzzGatewayService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class YzzGatewayServiceImpl extends AbstractGatewayService implements YzzGatewayService {
	
	public YzzGatewayServiceImpl() {
		super();
	}
	
	public YzzGatewayServiceImpl(OpenApiServiceExecuter executer) {
		setExecuter(executer);
	}
	
	@Override
	public YzzUserSpecialRegisterResponse yzzUserRegister(YzzUserSpecialRegisterRequest request) {
		if (StringUtil.equalsIgnoreCase("longTime", request.getCertValidTime())) {
			request.setCertValidTime("0");
		} else {

		}
		return (YzzUserSpecialRegisterResponse) assertExcecuteSuccess(execute(request));
	}
	
	/**
	 * @param data
	 * @param handler
	 * @see com.yiji.openapi.sdk.service.yzz.YzzGatewayService#notifyCtrlTransfer(java.util.Map,
	 * com.yiji.openapi.sdk.notify.NotifyHandler)
	 */
	@Override
	public void notifyCtrlTransfer(Map<String, String> data, NotifyHandler handler) {
		CtrlTransferNotify notify = (CtrlTransferNotify) executor.parseNotify("ctrlTransfer", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
	}
	
	/**
	 * @param data
	 * @param handler
	 * @see com.yiji.openapi.sdk.service.yzz.YzzGatewayService#notifyPayTogether(java.util.Map,
	 * com.yiji.openapi.sdk.notify.NotifyHandler)
	 */
	@Override
	public void notifyPayTogether(Map<String, String> data, NotifyHandler handler) {
		PayTogetherNotify notify = (PayTogetherNotify) executor.parseNotify("payTogether", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
	}

	/**
	 * @param data
	 * @param handler
	 * @see com.yiji.openapi.sdk.service.yzz.YzzGatewayService#notifyRepay(java.util.Map, com.yiji.openapi.sdk.notify.NotifyHandler)
	 */
	@Override
	public void notifyRepay(Map<String, String> data, NotifyHandler handler) {
		RepayNotify notify = (RepayNotify) executor.parseNotify("repay", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
	}

	/**
	 * @param data
	 * @param handler
	 * @see com.yiji.openapi.sdk.service.yzz.YzzGatewayService#yzzStrengthenWithdraw(java.util.Map, com.yiji.openapi.sdk.notify.NotifyHandler)
	 */
	@Override
	public void yzzStrengthenWithdraw(Map<String, String> data, NotifyHandler handler) {
		YzzStrengthenWithdrawNotify notify = (YzzStrengthenWithdrawNotify) executor.parseNotify("yzzStrengthenWithdraw", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
	}

	@Override
	public void notifyInvestApply(Map<String, String> data,
			NotifyHandler handler) {
		InvestApplyNotify notify = (InvestApplyNotify) executor.parseNotify("investApply", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
		
	}

	@Override
	public URLResult jumpToYJF(BaseRequest request) {
		URLResult url = new URLResult();
		url.setUrl(executor.signAndBuildURL(request));
		return url;
	}

	@Override
	public void notifyFastpay(Map<String, String> data, NotifyHandler handler) {
		InvestApplyNotify notify = (InvestApplyNotify) executor.parseNotify("investApply", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
	}

	@Override
	public SpecialTransferResponse specialTransfer(SpecialTransferRequest request) {
		return (SpecialTransferResponse) assertExcecuteSuccess(execute(request));
	}

	@Override
	public void notifySpecialTransfer(Map<String, String> data, NotifyHandler handler) {
		SpecialTransferNotify notify = (SpecialTransferNotify) executor.parseNotify("specialTransfer", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
	}

	@Override
	public void notifyYzzPaymentBond(Map<String, String> data, NotifyHandler handler) {
		YzzPaymentBondNotify notify = (YzzPaymentBondNotify) executor.parseNotify("yzzPaymentBond", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
	}
}
