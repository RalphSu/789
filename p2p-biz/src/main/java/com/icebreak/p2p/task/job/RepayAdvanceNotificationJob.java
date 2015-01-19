package com.icebreak.p2p.task.job;

import java.util.Date;

import com.icebreak.p2p.task.AbstractBaseJob;
import com.icebreak.p2p.task.job.inter.YrdProcessJobService;
import com.icebreak.p2p.trade.TradeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service("repayAdvanceNotificationJob")
public class RepayAdvanceNotificationJob extends AbstractBaseJob implements YrdProcessJobService {
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());
	@Autowired
	private TradeService	tradeService;
	
	//@Scheduled(cron = "0 0/2 15-17 * * ?")
	//@Scheduled(cron = "0/58 * * * * ? ")
	@Override
	public synchronized void doJob() throws Exception {
        if(!canRun()){
            return;
        }

		logger.info("借款到期提前通知借款人每日任务开始在 " + new Date().toString());
		try {
			tradeService.executeRepayAdvanceNotificationJob();
		} catch (Exception e) {
			logger.error("借款到期提前通知借款人异常---异常原因：" + e.getMessage(), e);
		}
		logger.info("借款到期提前通知借款人每日任务结束在 " + new Date().toString());
	}
	
	public TradeService getTradeService() {
		return tradeService;
	}
	
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
}
