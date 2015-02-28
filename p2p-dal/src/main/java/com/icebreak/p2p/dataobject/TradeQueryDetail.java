package com.icebreak.p2p.dataobject;

import java.util.Date;

public class TradeQueryDetail extends TradeDetail {
	
	private static final long	serialVersionUID	= 2668025060802679820L;
	private short				status				= 0;
	private String				name				= null;
	private long				demandId			= 0;
	
	private long				tradeAmount			= 0;
	private long				loanedAmount		= 0;
	private Date				effectiveDate;
	private double				income				= 0;
	private double 				rule				= 0.0;
	/**
	 * 投资期限
	 */
	private int timeLimit;
	/**
	 * 借款期限单位：D：天，W：周期，M：月，Y：年
	 */
	private String timeLimitUnit;
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getRule() {
		return rule;
	}

	public void setRule(double rule) {
		this.rule = rule;
	}

	public short getStatus() {
		return status;
	}
	
	public void setStatus(short status) {
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getDemandId() {
		return demandId;
	}
	
	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}
	
	public long getTradeAmount() {
		return tradeAmount;
	}
	
	public void setTradeAmount(long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	public long getLoanedAmount() {
		return loanedAmount;
	}
	
	public void setLoanedAmount(long loanedAmount) {
		this.loanedAmount = loanedAmount;
	}

	public String getTimeLimitUnit() {
		return timeLimitUnit;
	}

	public void setTimeLimitUnit(String timeLimitUnit) {
		this.timeLimitUnit = timeLimitUnit;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TradeQueryDetail [status=");
		builder.append(status);
		builder.append(", name=");
		builder.append(name);
		builder.append(", demandId=");
		builder.append(demandId);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
