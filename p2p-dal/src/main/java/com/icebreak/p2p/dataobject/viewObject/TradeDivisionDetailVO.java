package com.icebreak.p2p.dataobject.viewObject;

import com.icebreak.p2p.dataobject.TradeDetail;

public class TradeDivisionDetailVO extends TradeDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//借款人Id
	private long loanerId = 0;
	//借款人real名称
	private String loanerName;
	//角色名称
	private String roleName;
	
	private String status;
	public TradeDivisionDetailVO(){};
	public TradeDivisionDetailVO(TradeDetail tradeDetail) {
		this.setUserId(tradeDetail.getUserId());
		this.setUserBaseId(tradeDetail.getUserBaseId());
		this.setUserName(tradeDetail.getUserName());
		this.setRealName(tradeDetail.getRealName());
		this.setAccountId(tradeDetail.getAccountId());
		this.setAmount(tradeDetail.getAmount());
		this.setDate(tradeDetail.getDate());
		this.setId(tradeDetail.getId());
		this.setNote(tradeDetail.getNote());
		this.setTransferPhase(tradeDetail.getTransferPhase());
		this.setTradeId(tradeDetail.getTradeId());
		this.setRoleId(tradeDetail.getRoleId());
	}
	public long getLoanerId() {
		return loanerId;
	}
	public void setLoanerId(long loanerId) {
		this.loanerId = loanerId;
	}
	public String getLoanerName() {
		return loanerName;
	}
	public void setLoanerName(String loanerName) {
		this.loanerName = loanerName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
