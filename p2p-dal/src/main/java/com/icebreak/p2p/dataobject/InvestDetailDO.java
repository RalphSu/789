package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class InvestDetailDO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 投资明细ID */
	private Long detailId;
	/** 托管机构用户ID */
	private String accountId;
	/** 平台用户ID */
	private long userId;
	/** 金额(分) */
	private long amount;
	/** 利息收益(分) 投标成功后，由易9回传 */
	private long interestAmount;
	
	/** 外部订单号 */
	private String extOrder;
	
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public long getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(long interestAmount) {
		this.interestAmount = interestAmount;
	}

	public String getExtOrder() {
		return extOrder;
	}
	public void setExtOrder(String extOrder) {
		this.extOrder = extOrder;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "InvestDetailDO [detailId=" + detailId + ", accountId="
				+ accountId + ", userId=" + userId + ", amount=" + amount
				+ ", interestAmount=" + interestAmount + ", extOrder="
				+ extOrder + "]";
	}
}
