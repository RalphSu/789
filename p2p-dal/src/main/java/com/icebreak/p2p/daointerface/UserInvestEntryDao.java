package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.UserInvestEntry;

public interface UserInvestEntryDao {
	/**
	 * 根据各种属性查询
	 * @param params
	 * @return
	 */
	public List<UserInvestEntry> getByProperties(Map<String, Object> params);
	/**
	 * 根据各种属性查询条数
	 * @param params
	 * @return
	 */
	public long getCountByProperties(Map<String, Object> params);
	/**
	 * 根据交易ID获取投资条目
	 * @param detailId 
	 * @param tradeId, detailId
	 * @return
	 */
	public List<UserInvestEntry> getEntriesByTradeParams(Map<String, Object> params);
	/**
	 * 统计已投资金额
	 * @param conditions
	 * @return
	 */
	public long getAllInverstAmount(Map<String,Object> conditions);

}
