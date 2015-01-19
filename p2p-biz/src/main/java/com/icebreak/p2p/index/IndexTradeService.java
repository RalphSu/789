package com.icebreak.p2p.index;

import java.util.Map;

import com.icebreak.p2p.dataobject.IndexTrade;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.ws.service.query.order.IndexQueryOrder;

public interface IndexTradeService {
	
	public Pagination<IndexTrade> getIndexTrades(int start, int size, IndexQueryOrder queryOrder);
	
	public Pagination<IndexTrade> getIndexTrades(int start, int size,String guarantee, String timeUnit, Integer startTime, Integer endTime, Long startAmount, Long endAmount, Double startRate, Double endRate);

	public Pagination<IndexTrade> getIndexTrades(Map<String, Object> conditions);

}
