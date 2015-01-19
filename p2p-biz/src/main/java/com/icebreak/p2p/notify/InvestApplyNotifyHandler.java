package com.icebreak.p2p.notify;

import com.icebreak.p2p.daointerface.AmountFlowDao;
import com.icebreak.p2p.integration.openapi.RemoteTradeService;
import com.icebreak.p2p.trade.InvestService;
import com.icebreak.p2p.trade.TradeService;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.InvestApplyNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("investApplyNotifyHandler")
public class InvestApplyNotifyHandler implements NotifyHandler {
	
	private static Logger logger = LoggerFactory.getLogger(InvestApplyNotifyHandler.class);
	
	@Resource
	private InvestService investService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private RemoteTradeService remoteTradeService;
	@Autowired
	private AmountFlowDao amountFlowDao;
	
	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("投资结果回推：" + notify);
		
		InvestApplyNotify nty = (InvestApplyNotify) notify;
		
		try {
			investService.investNotify(nty);
		} catch (Exception e) {
			logger.error("", e);
		}
		
	}
	
}
