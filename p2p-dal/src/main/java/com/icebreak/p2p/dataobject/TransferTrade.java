package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class TransferTrade implements Serializable {
	
	private static final long	serialVersionUID	= 1L;
	/**
	 * 用户ID
	 */
	private long				userId				= 0;
	/**
	 * 托管机构帐号
	 */
	private String				yjfUserName			= null;
	/**
	 * 金额
	 */
	private long				amount				= 0;
	
	/**
	 * 子交易ID
	 */
	private long				tradeDetailId		= 0;
	
	private String 				transferPhase       = null;
	
	public String getTransferPhase() {
		return transferPhase;
	}

	public void setTransferPhase(String transferPhase) {
		this.transferPhase = transferPhase;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getYjfUserName() {
		return yjfUserName;
	}
	
	public void setYjfUserName(String yjfUserName) {
		this.yjfUserName = yjfUserName;
	}
	
	public long getAmount() {
		return amount;
	}
	
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public long getTradeDetailId() {
		return tradeDetailId;
	}
	
	public void setTradeDetailId(long tradeDetailId) {
		this.tradeDetailId = tradeDetailId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransferTrade [userId=");
		builder.append(userId);
		builder.append(", yjfUserName=");
		builder.append(yjfUserName);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", tradeDetailId=");
		builder.append(tradeDetailId);
		builder.append("]");
		return builder.toString();
	}
	
}
