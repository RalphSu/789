package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class TradeDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id = 0;
	
	private String orderNo;
	
	private long userId = 0;
	private String userBaseId;
	private String userName;
	private String realName;
	private String accountId;
	private int roleId = 0;
	private long tradeId = 0;
	private long amount = 0;
	private Date date = new Date();
	/**
	 * 描述
	 */
	private String note = null;
	/**
	 * 转账阶段
	 */
	private String transferPhase;
	
	/**
	 * 收益类型
	 */
	private int profitType;
	
	/**
	 * 年化收益率
	 */
	private double profitRate;
	/**
	 * 外部订单号
	 */
	private String extOrder;
	
	/**
	 * 还款期数
	 */
	private int repayPeriodNo;
	/**
	 * 还款总期数
	 */
	private int repayPeriodCount;
	/**
	 * 还款时间
	 */
	private Date repayDate;
	
	/**
	 * 实际还款日期
	 */
	private Date actualRepayDate;
	
	private String tradeDetailStatus;
	
	public TradeDetail() {
	}
	
	public TradeDetail(long userId, String accountId, int roleId, long tradeId, long amount,
						String transferPhase, String note) {
		this.userId = userId;
		this.accountId = accountId;
		this.roleId = roleId;
		this.tradeId = tradeId;
		this.amount = amount;
		this.transferPhase = transferPhase;
		this.note = note;
	}
	
	public TradeDetail(long userId, long tradeId, long amount, int roleId, String transferPhase,
						String note) {
		this.userId = userId;
		this.tradeId = tradeId;
		this.amount = amount;
		this.roleId = roleId;
		this.note = note;
		this.transferPhase = transferPhase;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
	
	public String getUserBaseId() {
		return userBaseId;
	}
	
	public void setUserBaseId(String userBaseId) {
		this.userBaseId = userBaseId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTransferPhase() {
		return transferPhase;
	}
	
	public void setTransferPhase(String transferPhase) {
		this.transferPhase = transferPhase;
	}
	
	public int getProfitType() {
		return profitType;
	}
	
	public void setProfitType(int profitType) {
		this.profitType = profitType;
	}
	
	public double getProfitRate() {
		return profitRate;
	}
	
	public void setProfitRate(double profitRate) {
		this.profitRate = profitRate;
	}
	
	public String getExtOrder() {
		return extOrder;
	}
	
	public void setExtOrder(String extOrder) {
		this.extOrder = extOrder;
	}
	
	public int getRepayPeriodNo() {
		return repayPeriodNo;
	}
	
	public void setRepayPeriodNo(int repayPeriodNo) {
		this.repayPeriodNo = repayPeriodNo;
	}
	
	public int getRepayPeriodCount() {
		return repayPeriodCount;
	}
	
	public void setRepayPeriodCount(int repayPeriodCount) {
		this.repayPeriodCount = repayPeriodCount;
	}
	
	public Date getRepayDate() {
		return repayDate;
	}
	
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	public Date getActualRepayDate() {
		return actualRepayDate;
	}
	
	public void setActualRepayDate(Date actualRepayDate) {
		this.actualRepayDate = actualRepayDate;
	}
	
	public String getTradeDetailStatus() {
		return tradeDetailStatus;
	}
	
	public void setTradeDetailStatus(String tradeDetailStatus) {
		this.tradeDetailStatus = tradeDetailStatus;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Override
	public String toString() {
		return "TradeDetail [id=" + id + ", userId=" + userId + ", userBaseId=" + userBaseId
				+ ", userName=" + userName + ", realName=" + realName + ", accountId=" + accountId
				+ ", roleId=" + roleId + ", tradeId=" + tradeId + ", amount=" + amount + ", date="
				+ date + ", note=" + note + ", transferPhase=" + transferPhase + ", profitType="
				+ profitType + ", profitRate=" + profitRate + ", repayPeriodNo=" + repayPeriodNo
				+ ", repayPeriodCount=" + repayPeriodCount + ", repayDate=" + repayDate
				+ ", extOrder=" + extOrder + "]";
	}
	
}
