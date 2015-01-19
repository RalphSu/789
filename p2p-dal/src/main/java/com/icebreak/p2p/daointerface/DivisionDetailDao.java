package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.DivisionDetail;

public interface DivisionDetailDao {
	/**
	 * 添加一条分润明细记录
	 * @param detail
	 */
	public void addDivisionDetail(DivisionDetail detail);
	/**
	 * 更改分润明细状态
	 * @param id 分润明细状态ID
	 * @param status 状态
	 * @return
	 */
	public int modifyStatus(long id, int status);
	
	public void update(DivisionDetail detail);
	
	
	/**
	 * 根据交易ID，状态，和角色获取条数
	 * @param tradeId
	 * @param status
	 * @param roles
	 * @return
	 */
	public int getCount(long tradeId, long userId, boolean status);
	/**
	 * 根据交易ID，状态，和角色更新金额
	 * @param tradeId
	 * @param userId
	 * @param status
	 * @param amount
	 */
	public int modifyAmount(long tradeId, long userId, boolean status, long amount);
	/**
	 * 根据交易ID查询和角色交易明细
	 * @param tradeId
	 * @return
	 */
	public List<DivisionDetail> getByTradeIdAndRoles(long tradeId, String... roles);
	/**
	 * 根据交易id和用户id查询交易明细
	 * @param tradeId
	 * @param userId
	 * @return
	 */
	public int getCountByTradeAndUsrCount(Long tradeId, Long userId);
	/**
	 * 根据交易id和用户id查询交易明细
	 * @param tradeId
	 * @param userId
	 * @return
	 */
	public List<DivisionDetail> getCountByTradeAndUsr(Long tradeId,
			Long userId);
	/**
	 * 根据状态获取
	 * @param status
	 * @return
	 */
	public List<DivisionDetail> getByStatus(int status);
	/**
	 * 根据条件获取数据条数
	 * @param conditions
	 * @return
	 */
	public long getDivisionAmountFlowTradesByParamsCount(
			Map<String, Object> conditions);
	/**
	 *  根据条件获取数据条目
	 * @param conditions
	 * @return
	 */
	public List<DivisionDetail> getDivisionAmountFlowTradesByParams(
			Map<String, Object> conditions);
	/**
	 *  根据tradeDetailId获取已转账数据条目
	 * @param tradeDetailId
	 * @return
	 */
	public int transDivisionCountByTradeDetailId(long tradeDetailId);
	
	public List<DivisionDetail> query(DivisionDetail divisionDetail);

}
