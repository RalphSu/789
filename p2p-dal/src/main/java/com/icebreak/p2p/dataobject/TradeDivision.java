package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class TradeDivision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易分润ID
	 */
	private long id = 0;
	/**
	 * 分润模版ID
	 */
	private long templateId = 0;
	/**
	 * 交易ID
	 */
	private long tradeId = 0;
	
	public TradeDivision(){}
	
	public TradeDivision(long templateId, long tradeId) {
		this.templateId = templateId;
		this.tradeId = tradeId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}

	@Override
	public String toString() {
		return "id:" + id + ", templateId:" + templateId + ", tradeId:" + tradeId;
	}
}
