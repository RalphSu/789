package com.icebreak.p2p.synchro;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.icebreak.p2p.util.DateUtil;

public class SynchroTask {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	public static void beepForTime() {
		final Runnable beeper = new Runnable() {
			int i = 0;
			public void run() {
				System.out.println("beep:" + (i++) + "----编写业务逻辑"+ DateUtil.simpleFormatYmdhms(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		int startTime = 3; //将于几秒后开始执行
		int each = 1;//每几秒执行一次
		
		System.out.println("任务将于" + startTime + "秒后开始，每" + each + "秒执行1次");
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleWithFixedDelay(beeper, startTime, each,TimeUnit.SECONDS);
		/**
		 * 创建并执行在给定延迟后启用的一次性操作。
		 * 
		 * 这里用于在N时间后取消任务
		 */
		scheduler.schedule(new Runnable() {
			public void run() {
				System.out.println("取消任务");
				beeperHandle.cancel(true);
			}
		},
		60, TimeUnit.SECONDS);
	}

	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		beepForTime();
	}
	
}
