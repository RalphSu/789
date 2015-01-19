package com.icebreak.p2p.task.job;

import java.util.Date;

import com.icebreak.p2p.task.AbstractBaseJob;
import com.icebreak.p2p.task.job.inter.YrdProcessJobService;
import com.icebreak.p2p.user.UserBaseInfoManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service("unlockUserJob")
public class UnlockUserJob extends AbstractBaseJob implements YrdProcessJobService {
	protected final Logger		logger	= LoggerFactory.getLogger(getClass());
	@Autowired
	private UserBaseInfoManager	userBaseInfoManager;
	
//	@Scheduled(cron = "0 0 0 * * ? ")
	@Scheduled(cron = "0/58 * * * * ? ")
	@Override
	public synchronized void doJob() throws Exception {
        if(!canRun()){
            return;
        }

//		logger.info("锁定到期解锁用户任务开始在 " + new Date().toString());
		try {
			userBaseInfoManager.executeUnlockUserJob();
		} catch (Exception e) {
			logger.error("查锁定到期解锁用户任务异常---异常原因：" + e.getMessage(), e);
		}
//		logger.info("锁定到期解锁用户任务结束在 " + new Date().toString());
	}
}
