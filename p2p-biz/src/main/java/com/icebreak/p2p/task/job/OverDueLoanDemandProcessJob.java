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


@Service("overDueLoanDemandProcessJob")
public class OverDueLoanDemandProcessJob extends AbstractBaseJob implements YrdProcessJobService {
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());
	@Autowired
	private TradeService	tradeService;
	
	//@Scheduled(cron = "0/5 * * * * ? ")
	@Override
	public synchronized void doJob() throws Exception {
        if(!canRun()){
            return;
        }

		logger.info("查询到期交易任务开始在 " + new Date().toString());
		try {
			tradeService.executeOverDueLoanDemandProcessJob();
		} catch (Exception e) {
			logger.error("查询到期未满标任务异常时间：" + new Date().toString(), e);
		}
		logger.info("查询到期交易任务结束在 " + new Date().toString());
	}
	
	public TradeService getTradeService() {
		return tradeService;
	}
	
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
}
