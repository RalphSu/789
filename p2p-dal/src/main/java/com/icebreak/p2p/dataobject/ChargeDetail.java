package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class ChargeDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收费明细ID
	 */
	private long id = 0;
	/**
	 * 用户ID
	 */
	private long userId = 0;
	/**
	 * 金额
	 */
	private long amount = 0;
	/**
	 * 状态0:已缴费，1：未缴费
	 */
	private boolean status = false;
	/**
	 * 交易明细ID
	 */
	private Long tradeDetailId = null;
	/**
	 * 实际缴费时间
	 */
	private Date actualDate = null;
	/**
	 * 日期
	 */
	private Date date = new Date();
	/**
	 * 备注
	 */
	private String note = null;
	
	public ChargeDetail(){}
	
	public ChargeDetail(long userId, long amount, boolean status, Date date, String note, Long tradeDetailId) {
		this.userId = userId;
		this.amount = amount;
		this.status = status;
		this.date = date;
		this.note = note;
		this.tradeDetailId = tradeDetailId;
	}
	
	public Long getTradeDetailId() {
		return tradeDetailId;
	}

	public void setTradeDetailId(Long tradeDetailId) {
		this.tradeDetailId = tradeDetailId;
	}

	public Date getActualDate() {
		return actualDate;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "id:" + id + ", userId:" + userId + ", amount:" + amount + ", date:" + date + ", note:" + note + ", actualDate:" + ", status:" + status;
	}
}
