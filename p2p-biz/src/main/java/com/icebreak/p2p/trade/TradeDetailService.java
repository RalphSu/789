package com.icebreak.p2p.trade;

import java.util.Map;

public interface TradeDetailService  {

	long sumAmountByCondition(Map<String, Object> params);
	//统计金额map
	Map<String, Object> createAmounMap(long id);

}
