package com.icebreak.p2p.dataobject;

import java.util.Date;

public class AgentInvestorTrade {
	//detailId
	private long detailId;
	//investDetailId
	private long investDetailId;
	//投资人
	private long investorId;
	//经纪人ID
	private long brokerId;
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
	//投资人姓名
	private String investorName;
	//经济人姓名
	private String brokerName;
	//投资金额
	private long investAmount;
	//收益金额
	private long brokerBenifitAmount;
	//投资日期
	private Date investDate;
	//成立日期
	private Date effectiveDate;
	//还款日期
	private Date expireDate;
	/**
	 * 交易状态
	 */
	private short status = 0;

	public long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(long investorId) {
		this.investorId = investorId;
	}

	public long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(long brokerId) {
		this.brokerId = brokerId;
	}

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}

	public String getTransferPhase() {
		return transferPhase;
	}

	public void setTransferPhase(String transferPhase) {
		this.transferPhase = transferPhase;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public long getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(long investAmount) {
		this.investAmount = investAmount;
	}

	public long getBrokerBenifitAmount() {
		return brokerBenifitAmount;
	}

	public void setBrokerBenifitAmount(long brokerBenifitAmount) {
		this.brokerBenifitAmount = brokerBenifitAmount;
	}

	public Date getInvestDate() {
		return investDate;
	}

	public void setInvestDate(Date investDate) {
		this.investDate = investDate;
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

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
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

	public long getDetailId() {
		return detailId;
	}

	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public long getInvestDetailId() {
		return investDetailId;
	}

	public void setInvestDetailId(long investDetailId) {
		this.investDetailId = investDetailId;
	}

	@Override
	public String toString() {
		return "AgentInvestorTrade [detailId=" + detailId + ", investorId="
				+ investorId + ", brokerId=" + brokerId + ", tradeId="
				+ tradeId + ", tradeCode=" + tradeCode + ", tradeName="
				+ tradeName + ", transferPhase=" + transferPhase
				+ ", investorName=" + investorName + ", brokerName="
				+ brokerName + ", investAmount=" + investAmount
				+ ", brokerBenifitAmount=" + brokerBenifitAmount
				+ ", investDate=" + investDate + ", effectiveDate="
				+ effectiveDate + ", expireDate=" + expireDate + ", status="
				+ status + "]";
	}
	
}
