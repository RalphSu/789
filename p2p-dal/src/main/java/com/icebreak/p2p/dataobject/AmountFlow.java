package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class AmountFlow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 资金流向ID
	 */
	private long id = 0;
	/**
	 * 流水号
	 */
	private String flowCode = null;
	/**
	 * 入金用户ID
	 */
	private long inUserId = 0;
	/**
	 * 出金用户ID
	 */
	private long outUserId = 0;
	/**
	 * 出账账户
	 */
	private String amountOut = null;
	/**
	 * 入账账户
	 */
	private String amountIn = null;
	/**
	 * 金额
	 */
	private long amount = 0;
	/**
	 * 资金流类型
	 */
	private String type = null;
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 描述
	 */
	private String note = null;
	/**
	 * 日期
	 */
	private Date date = new Date();

	public AmountFlow() {
	}
	
	public AmountFlow(String flowCode, String amountOut, String amountIn, long amount,
			int status, String note, Date date, long inUserId, long outUserId, String type) {
		this.amountOut = amountOut;
		this.amountIn = amountIn;
		this.amount = amount;
		this.status = status;
		this.note = note;
		this.date = date;
		this.inUserId = inUserId;
		this.outUserId = outUserId;
		this.type = type;
		this.flowCode = flowCode;
	}
	
	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public long getInUserId() {
		return inUserId;
	}

	public void setInUserId(long inUserId) {
		this.inUserId = inUserId;
	}

	public long getOutUserId() {
		return outUserId;
	}

	public void setOutUserId(long outUserId) {
		this.outUserId = outUserId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAmountOut() {
		return amountOut;
	}

	public void setAmountOut(String amountOut) {
		this.amountOut = amountOut;
	}

	public String getAmountIn() {
		return amountIn;
	}

	public void setAmountIn(String amountIn) {
		this.amountIn = amountIn;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "id:" + id + ", amountOut:" + amountOut + ", amountIn:" + amountIn + ", amount:" + amount + ", status:" + status + ", note:" + note + ", date:" + date;
	}

}
