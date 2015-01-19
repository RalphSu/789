package com.icebreak.p2p.dataobject.viewObject;

import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.TradeStatusDO;

public class LoanDemandTradeVO extends LoanDemandDO{
	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 担保函状态
	 */
	private String guaranteeStatus;
	/**
	 *交易id
	 */
	private long tradeId;
	/**
	 *担保费 
	 */
	private long benefitAmount;
	
	TradeStatusDO tradeStatusDO;
	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}

	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}

	public long getBenefitAmount() {
		return benefitAmount;
	}

	public void setBenefitAmount(long benefitAmount) {
		this.benefitAmount = benefitAmount;
	}

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}

	public TradeStatusDO getTradeStatusDO() {
		return tradeStatusDO;
	}

	public void setTradeStatusDO(TradeStatusDO tradeStatusDO) {
		this.tradeStatusDO = tradeStatusDO;
	}
	
	
	
}
