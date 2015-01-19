package com.icebreak.p2p.ws.service.query.result;

import com.icebreak.util.lang.util.money.Money;

public class TradeDetailBatchResult<T> extends QueryBaseBatchResult<T> {
	
	private static final long serialVersionUID = -1565329981640523879L;
	Money totalAmount = new Money();
	
	public Money getTotalAmount() {
		return this.totalAmount;
	}
	
	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TradeDetailBatchResult [totalAmount=");
		builder.append(totalAmount);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
