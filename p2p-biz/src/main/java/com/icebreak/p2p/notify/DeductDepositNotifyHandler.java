package com.icebreak.p2p.notify;

import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.common.funds.DeductDepositNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("deductDepositNotifyHandler")
public class DeductDepositNotifyHandler implements NotifyHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DeductDepositNotifyHandler.class);

	@Resource
	private RechargeFlowService rechargeFlowService;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("代扣充值回推处理：" + notify);

		DeductDepositNotify nty = (DeductDepositNotify) notify;

		String outBizNo = nty.getDepositId();
		RechargeFlow flow = rechargeFlowService.queryByOutBizNo(outBizNo);

		if ("true".equals(nty.getSuccess()) && "DEPOSIT_SUCCESS".equals(nty.getResultCode())) {
			flow.setStatus(1);
		} else {
			flow.setStatus(0);
		}
		flow.setPayFinishTime(new Date());
		rechargeFlowService.updateStatus(flow);
	}
	
}
