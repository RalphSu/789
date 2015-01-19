package com.icebreak.p2p.daointerface;

import java.util.List;

import com.icebreak.p2p.dataobject.Division;

public interface DivisionDao {
	/**
	 * 获取分润信息
	 * @param tradeId
	 * @return
	 */
	public List<Division> getDivisions(long tradeId, String... roles);
	/**
	 * 获取父级机构的分信息
	 * @param tradeId
	 * @param userId
	 * @return
	 */
	public Division getParentDivision(long tradeId, long userId);
	/**
	 * 根据交易ID和用户ID获取分润规则
	 * @param tradeId
	 * @param userId
	 * @return
	 */
	public double getRuleByTradeIdAndUserId(long tradeId, long userId);
}
