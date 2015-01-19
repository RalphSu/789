package com.icebreak.p2p.notify;

import com.icebreak.p2p.integration.openapi.RepayService;
import com.icebreak.p2p.trade.TradeService;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.RepayNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("repayNotifyHandler")
public class RepayNotifyHandler implements NotifyHandler {

	private static Logger logger = LoggerFactory
			.getLogger(RepayNotifyHandler.class);

	@Resource
	private TradeService tradeService;
	@Resource
	private RepayService repayService;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("通知默认处理[打印]：" + notify);

		RepayNotify nty = (RepayNotify) notify;

		try {
			tradeService.repayNotify(nty);

			logger.info("关闭交易");
			repayService.closeReplyTrade(nty.getTradeNo());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
