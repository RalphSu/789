package com.icebreak.p2p.dataobject;

import java.util.Date;

public class AgentLoanerTrade {
	//detailId
	private long detailId;
	//借款人
	private long loanerId;
	//担保机构ID
	private long agencyId;
	//交易ID
	private long tradeId;
	/**
	 * 交易号
	 */
	private String tradeCode = null;
	/**
	 * 交易名称
	 */
	private String tradeName = null;
	//还款阶段
	private String transferPhase;
	//借款人姓名
	private String loanerName;
	//担保机构姓名
	private String  agencyName;
	//借款金额
	private long loanAmount;
	//收益金额
	private long  agencyBenifitAmount;
	//投资日期
	private Date loanDate;
	//成立日期
	private Date effectiveDate;
	//还款日期
	private Date expireDate;
	/**
	 * 交易状态
	 */
	private short status = 0;
	public long getDetailId() {
		return detailId;
	}
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	public long getLoanerId() {
		return loanerId;
	}
	public void setLoanerId(long loanerId) {
		this.loanerId = loanerId;
	}
	public long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(long agencyId) {
		this.agencyId = agencyId;
	}
	public long getTradeId() {
		return tradeId;
	}
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getTransferPhase() {
		return transferPhase;
	}
	public void setTransferPhase(String transferPhase) {
		this.transferPhase = transferPhase;
	}
	public String getLoanerName() {
		return loanerName;
	}
	public void setLoanerName(String loanerName) {
		this.loanerName = loanerName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public long getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}
	public long getAgencyBenifitAmount() {
		return agencyBenifitAmount;
	}
	public void setAgencyBenifitAmount(long agencyBenifitAmount) {
		this.agencyBenifitAmount = agencyBenifitAmount;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}

	
	
}
