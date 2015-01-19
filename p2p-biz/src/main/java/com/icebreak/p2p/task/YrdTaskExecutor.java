package com.icebreak.p2p.task;

import com.icebreak.util.session.DefaultRmcThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class YrdTaskExecutor {
	/**
	 * 日志
	 */
	private final Logger				logger						= LoggerFactory
																		.getLogger(getClass());
	/**
	 * 线程名称前缀
	 */
	private String						prefix						= "thread-";
	/**
	 * 核心线程数
	 */
	private int							corePool					= 100;
	/**
	 * 任务列表
	 */
	private AbstractTask[]				tasks						= null;
	/**
	 * 执行器
	 */
	private ScheduledExecutorService	scheduledExecutorService	= null;
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setCorePool(int corePool) {
		this.corePool = corePool;
	}
	
	public void setTasks(AbstractTask[] tasks) {
		this.tasks = tasks;
	}
	
	public void init() {
		logger.info("正在初始化任务执行器");
		scheduledExecutorService = Executors.newScheduledThreadPool(corePool,
			new DefaultRmcThreadFactory(prefix));
		if (tasks != null && tasks.length > 0) {
			for (AbstractTask task : tasks) {
				logger.info("正在添加任务:" + task.getClass().getCanonicalName());
				scheduledExecutorService.scheduleWithFixedDelay(task, task.getDelay(),
					task.getPeriod(), TimeUnit.MILLISECONDS);
				logger.info("完成添加任务:" + task.getClass().getCanonicalName() + ", 执行任务前的延迟时间:"
							+ task.getDelay() + "ms, " + "执行任务之间的时间间隔:" + task.getPeriod() + "ms");
			}
		}
		logger.info("完成初始化任务执行器");
	}
	
	public void shutdown() {
		logger.info("停止任务执行器...");
		if (scheduledExecutorService != null) {
			scheduledExecutorService.shutdown();
		}
	}
}
