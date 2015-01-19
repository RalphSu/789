package com.icebreak.p2p.daointerface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.IndexTrade;

public interface IndexTradeDao {
	/**
	 * 根据条件查询
	 * @param params
	 * @return
	 */
	public List<IndexTrade> getByProperties(Map<String, Object> params);
	/**
	 * 根据条件查询条数
	 * @param params
	 * @return
	 */
	public long getByPropertiesCount(Map<String, Object> params);
	/**
	 * 查询最后投资日期
	 * @param params
	 * @return
	 */
	public Date getInvestCompleteTime(long tradeId);

}
