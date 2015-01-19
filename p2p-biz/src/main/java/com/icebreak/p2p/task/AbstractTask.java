package com.icebreak.p2p.task;



public abstract class AbstractTask extends AbstractBaseJob implements Runnable {

	/**
	 * 执行任务前的延迟时间，单位是毫秒
	 */
	private long delay = 0;
	/**
	 * 执行各后续任务之间的时间间隔，单位是毫秒
	 */
	private long period = 0;

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public long getDelay() {
		return delay;
	}

	public long getPeriod() {
		return period;
	}



}
