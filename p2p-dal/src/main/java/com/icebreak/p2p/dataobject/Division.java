package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class Division implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private long userId = 0;
	/**
	 * 托管机构账户
	 */
	private String yjfUserName = null;
	/**
	 * 规则
	 */
	private double rule = 0.0;
	/**
	 * 投资金额
	 */
	private long amount = 0L;
	
	public String getYjfUserName() {
		return yjfUserName;
	}

	public void setYjfUserName(String yjfUserName) {
		this.yjfUserName = yjfUserName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getRule() {
		return rule;
	}

	public void setRule(double rule) {
		this.rule = rule;
	}
	
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "userId:" + userId + ", rule:" + rule + ", yjfUserName:" + yjfUserName + ", amount:" + amount;
	}
}
