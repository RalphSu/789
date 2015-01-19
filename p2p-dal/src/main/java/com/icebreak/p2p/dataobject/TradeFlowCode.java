package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class TradeFlowCode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8841036488351137679L;
	private String tblBaseId;
	private long tradeDetailId;
	private String tradeFlowCode;
	private Date rowAddTime;
	private String note;
	private int state;
	public String getTblBaseId() {
		return tblBaseId;
	}
	public void setTblBaseId(String tblBaseId) {
		this.tblBaseId = tblBaseId;
	}
	public long getTradeDetailId() {
		return tradeDetailId;
	}
	public void setTradeDetailId(long tradeDetailId) {
		this.tradeDetailId = tradeDetailId;
	}
	public String getTradeFlowCode() {
		return tradeFlowCode;
	}
	public void setTradeFlowCode(String tradeFlowCode) {
		this.tradeFlowCode = tradeFlowCode;
	}
	public Date getRowAddTime() {
		return rowAddTime;
	}
	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "TradeFlowCode [tblBaseId=" + tblBaseId + ", tradeDetailId="
				+ tradeDetailId + ", tradeFlowCode=" + tradeFlowCode
				+ ", rowAddTime=" + rowAddTime + ", note=" + note + ", state="
				+ state + "]";
	}

}
