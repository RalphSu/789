package com.icebreak.p2p.task.job;

import java.util.Date;

import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.task.AbstractBaseJob;
import com.icebreak.p2p.task.job.inter.YrdProcessJobService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service("onTimePublishLoanDemandJob")
public class OnTimePublishLoanDemandJob extends AbstractBaseJob implements YrdProcessJobService {
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());
	@Autowired
	LoanDemandManager		loanDemandManager;
	
	@Scheduled(cron = "0/59 * * * * ? ")
	@Override
	public synchronized void doJob() throws Exception {
        if(!canRun()){
            return;
        }


//		logger.info("需求定时发布需求任务开始在 " + new Date().toString());
		try {
			loanDemandManager.executeOnTimePublishLoanDemandJob();
		} catch (Exception e) {
			logger.info(
				"需求定时发布需求任务执行异常：异常时间--- " + new Date().toString() + "---异常原因---" + e.getMessage(),
				e);
		}
//		logger.info("需求定时发布需求任务结束在 " + new Date().toString());
	}
	
	public LoanDemandManager getLoanDemandManager() {
		return loanDemandManager;
	}
	
	public void setLoanDemandManager(LoanDemandManager loanDemandManager) {
		this.loanDemandManager = loanDemandManager;
	}
	
}
