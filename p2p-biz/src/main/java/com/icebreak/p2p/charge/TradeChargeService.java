package com.icebreak.p2p.charge;

import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;


public interface TradeChargeService {
	
	public long getAmount(Trade trade);
	
	public long getAmount(TradeDetail tradeDetail);
}
