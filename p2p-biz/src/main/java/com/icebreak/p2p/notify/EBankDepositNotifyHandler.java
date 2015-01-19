package com.icebreak.p2p.notify;

import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.common.funds.DepositNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("eBankDepositNotifyHandler")
public class EBankDepositNotifyHandler implements NotifyHandler {
	
	private static Logger logger = LoggerFactory.getLogger(EBankDepositNotifyHandler.class);

	@Resource
	private RechargeFlowService rechargeFlowService;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("网银充值回推处理：" + notify);

		DepositNotify nty = (DepositNotify) notify;

		String outBizNo = nty.getDepositId();
		RechargeFlow flow = rechargeFlowService.queryByOutBizNo(outBizNo);

		if ("true".equals(nty.getIsSuccess())) {
			flow.setStatus(1);
		} else {
			flow.setStatus(0);
		}
		flow.setPayFinishTime(new Date());
		rechargeFlowService.updateStatus(flow);
	}
	
}
