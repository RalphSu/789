package com.icebreak.p2p.daointerface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.AmountFlow;
import com.icebreak.p2p.dataobject.AmountFlowTrade;

public interface AmountFlowDao {
	/**
	 * 添加一条资金流向记录
	 * @param amountFlow
	 */
	public long addAmountFlow(AmountFlow amountFlow);
	
	/**
	 * 根据状态获取资金流向记录
	 * @param status
	 * @return
	 */
	public List<AmountFlow> getAmountFlowsByStatus(boolean status);
	
	/**
	 * 根据用户ID获取在某个时间段的资金总流量
	 * @param userId 用户ID
	 * @param start 起始时间
	 * @param end 结束时间
	 * @return
	 */
	public Long getTotalAmountFlow(long userId, Date start, Date end);
	
	/**
	 * 更改状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int modifyStatus(long id, int status);
	
	/**
	 * 关联交易与流水
	 * @param amountFlowTrade
	 */
	public void addAmountFlowTrade(AmountFlowTrade amountFlowTrade);
	
	/**
	 * 根据flowId获取资金流向记录
	 * @param flowId
	 * @return
	 */
	public AmountFlow getAmountFlowByFlowId(long flowId);
	
	/**
	 * 根据tradeId获取资金流向记录
	 * @param tradeId
	 * @return
	 */
	public List<AmountFlowTrade> getAmountFlowTradesByTradeId(long tradeId);
	
	/**
	 * 根据条件获取资金流向记录统计数量
	 * @param tradeId
	 * @return
	 */
	public long getAmountFlowTradesByParamsCount(Map<String, Object> conditions);
	
	/**
	 * 根据条件获取资金流向记录
	 * @param tradeId
	 * @return
	 */
	public List<AmountFlowTrade> getAmountFlowTradesByParams(Map<String, Object> conditions);
	
	/**
	 * 根据flowId获取资金流向交易记录
	 * @param flowId
	 * @return
	 */
	public AmountFlowTrade getAmountFlowTradeByFlowId(long id);
	
	/**
	 * 查询当前业务ID是否已执行转账
	 * @param tradeDetailId
	 * @param type
	 * @return
	 */
	public int transFlowCountByTradeDetailIdType(long tradeDetailId, String type);
	
	/**
	 * 判断转账类型是否还款中转转账
	 * @param tradeDetailId
	 * @param type
	 * @return
	 */
	public String getTransPhaseByTradeDetailId(long id);
	
	/**
	 * 判断转账类型是否按月还款中转转账
	 * @param id
	 * @return
	 */
	public int getRepayPeriodNOByTradeDetailId(long id);
	
	public List<AmountFlow> queryPayTogetherForUpdate(long tradeId, int status);

	public int update(String id, String flowCode, Boolean status);
}
