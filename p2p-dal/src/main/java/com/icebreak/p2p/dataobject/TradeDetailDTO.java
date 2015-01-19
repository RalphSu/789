package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class TradeDetailDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long loanedAmount;
	private Date  finishDate;
	private TradeDetail tradeDetail;
	public long getLoanedAmount() {
		return loanedAmount;
	}
	public void setLoanedAmount(long loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public TradeDetail getTradeDetail() {
		return tradeDetail;
	}
	public void setTradeDetail(TradeDetail tradeDetail) {
		this.tradeDetail = tradeDetail;
	}
	
}
