package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.TradeDivision;

public interface TradeDivisionDao {
	/**
	 * 添加一条交易分润
	 * @param tradeDivision
	 */
	public void addTradeDivision(TradeDivision tradeDivision);
	/**
	 * 根据交易ID获取
	 * @param tradeId
	 * @return
	 */
	public TradeDivision getByTradeId(long tradeId);

}
