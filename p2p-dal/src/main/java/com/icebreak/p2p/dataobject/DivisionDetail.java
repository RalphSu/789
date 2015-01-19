package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class DivisionDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private long id = 0;

	private String businessCode = null;

	/** 外部流水号 */
	private String outBizNo = null;
	/**
	 * 用户ID
	 */
	private long userId = 0;
	/**
	 * 交易ID
	 */
	private long tradeId = 0;
	/**
	 * 交易ID
	 */
	private long tradeDetailId = 0;
	/**
	 * 金额
	 */
	private long amount = 0;
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 日期
	 */
	private Date date = new Date();

	public DivisionDetail() {
	}

	public DivisionDetail(long userId, String businessCode, long tradeId,
			long amount, int status) {
		this.userId = userId;
		this.businessCode = businessCode;
		this.tradeId = tradeId;
		this.amount = amount;
		this.status = status;
	}

	public DivisionDetail(long userId, String businessCode, long tradeId,
			long tradeDetailId, long amount, int status, Date date) {
		this.userId = userId;
		this.businessCode = businessCode;
		this.tradeId = tradeId;
		this.tradeDetailId = tradeDetailId;
		this.amount = amount;
		this.status = status;
		this.date = date;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
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

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTradeDetailId() {
		return tradeDetailId;
	}

	public void setTradeDetailId(long tradeDetailId) {
		this.tradeDetailId = tradeDetailId;
	}

	public String getOutBizNo() {
		return outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	@Override
	public String toString() {
		return "DivisionDetail [id=" + id + ", businessCode=" + businessCode
				+ ", userId=" + userId + ", tradeId=" + tradeId
				+ ", tradeDetailId=" + tradeDetailId + ", amount=" + amount
				+ ", status=" + status + ", date=" + date + "]";
	}

}
