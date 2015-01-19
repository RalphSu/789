package com.icebreak.p2p.dataobject;

import java.io.Serializable;
public class AmountFlowTrade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private long baseId = 0;
	/**
	 * 流水id
	 */
	private long amountFlowId = 0;
	/**
	 * 交易id
	 */
	private long tradeId = 0;
	
	/**
	 * 子交易ID
	 */
	private long tradeDetailId = 0;
	

	public AmountFlowTrade(long amountFlowId, long tradeId, long tradeDetailId) {
		super();
		this.amountFlowId = amountFlowId;
		this.tradeId = tradeId;
		this.tradeDetailId = tradeDetailId;
	}
	
	public AmountFlowTrade() {
		super();
	}
	public long getBaseId() {
		return baseId;
	}
	public void setBaseId(long baseId) {
		this.baseId = baseId;
	}
	public long getAmountFlowId() {
		return amountFlowId;
	}
	public void setAmountFlowId(long amountFlowId) {
		this.amountFlowId = amountFlowId;
	}
	public long getTradeId() {
		return tradeId;
	}
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	public long getTradeDetailId() {
		return tradeDetailId;
	}
	public void setTradeDetailId(long tradeDetailId) {
		this.tradeDetailId = tradeDetailId;
	}
	@Override
	public String toString() {
		return "AmountFlowTrade [baseId=" + baseId + ", amountFlowId="
				+ amountFlowId + ", tradeId=" + tradeId + ", tradeDetailId="
				+ tradeDetailId + "]";
	}
	
}
