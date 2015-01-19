package com.icebreak.p2p.daointerface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.AgentInvestorTrade;
import com.icebreak.p2p.dataobject.AgentLoanerTrade;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeFlowCode;

public interface TradeDao {
	/**
	 * 根据交易ID获取交易
	 * @return
	 */
	public Trade getByTradeId(long id);
	
	/**
	 * 根据交易号获取交易
	 * @param code
	 * @return
	 */
	public Trade getByTradeCode(String code);
	
	/**
	 * 添加一条交易
	 * @param trade
	 */
	public void addTrade(Trade trade);
	
	/**
	 * 更新交易编号
	 * @param tradeId
	 * @param tradeCode
	 */
	public void modifyTradeCode(long tradeId, String tradeCode);
	
	/**
	 * 根据需求ID获取一条交易
	 * @param id
	 * @return
	 */
	public Trade getByDemandId(long id);

	/**
	 *
	 * @param tradeId
	 * @param status
	 * @param effectiveDateTime 满标日期
	 * @param expireDateTime 结息日期
	 * @return
	 */
	public int modifyStatus(long tradeId, short status, Date effectiveDateTime, Date expireDateTime);

	/**
	 *
	 * @param tradeId
	 * @param status
	 * @return
	 */
	public int modifyStatus(long tradeId, short status);
	
	/**
	 * 增加已几款金额
	 * @param tradeId
	 * @param amount
	 * @return
	 */
	public int addLoanedAmount(long tradeId, long amount);
	
	/**
	 * 修改交易完成时间
	 * @param tradeId
	 * @param date
	 * @return
	 */
	public int modifyFinishDate(long tradeId, Date date);
	
	/**
	 * 根据条件查询,获得交易信息,行数
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 * */
	public long queryCountByCondition(Map<String, Object> condition) throws DataAccessException;
	
	/**
	 * 根据条件查询,获得交易信息
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 * */
	public List<Trade> queryListByCondition(Map<String, Object> condition)
																			throws DataAccessException;
	
	/**
	 * 查询实际的所有融资金额
	 * @param condition
	 * @return
	 */
	public long getAllLoandAmount(Map<String, Object> condition);
	
	/**
	 * 设置生效时间和还款时间
	 * @param tradeId
	 * @return
	 */
	public void addEffectiveDateTime(Long tradeId, Date expireDateTime);
	
	/**
	 * 根据条件查询,获得交易信息,行数 用于定时任务，确保线程安全
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 * */
	public long queryCountByConditionForJob(Map<String, Object> condition);
	
	/**
	 * 根据条件查询,获得交易信息 用于定时任务，确保线程安全
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 * */
	public List<Trade> queryListByConditionForJob(Map<String, Object> condition);
	
	/**
	 * 更新分润机构的转账金额
	 * @param roleId
	 * @param id
	 * @param amount
	 */
	public void updateTradeDetailForOrg(int roleId, long tradeId, double amount);
	
	/**
	 * 营销代理人交易查询
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public long queryAgentInvestorCountByCondition(Map<String, Object> condition);
	
	/**
	 * 营销代理人交易查询list
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public List<AgentInvestorTrade> queryAgentInvestorListByCondition(Map<String, Object> condition);
	
	/**
	 * 统计经纪人下投资的所投资金额
	 * @param condition
	 * @return
	 */
	public long queryAllAmount(Map<String, Object> condition);
	
	/**
	 * 机构借款人条数
	 * @param condition
	 * @return
	 */
	public long queryAgencyLoanerCountByCondition(Map<String, Object> condition);
	
	/**
	 * 机构借款人交易查询list
	 * @param condition
	 * @return
	 */
	public List<AgentLoanerTrade> queryAgencyLoanerListByCondition(Map<String, Object> condition);
	
	/**
	 * 更新通知状态
	 * @param identifier
	 */
	public long updateIsNotifiedLoaner(long tradeId, String identifier);
	
	/**
	 * 加锁查询trade
	 * @param tradeId
	 * @return
	 */
	public Trade getByTradeIdWithRowLock(long tradeId);
	
	/**
	 * 添加充值记录
	 * @param rechargeFlow
	 */
	public long addRechargeFlow(RechargeFlow rechargeFlow);
	
	/**
	 * 查询充值记录
	 * @param condition
	 * @return
	 */
	public List<RechargeFlow> getFlowList(Map<String, Object> condition);
	
	/**
	 * 根据流水号查询
	 * @param outBizNo
	 * @return
	 */
	public RechargeFlow getFlowByBizNo(String outBizNo);
	
	/**
	 * 更新状态
	 * @param rechargeFlow
	 * @return
	 */
	public int updateStatus(RechargeFlow rechargeFlow);
	
	/**
	 * 查询充值记录数量
	 * @param condition
	 * @return
	 */
	public long getFlowCount(Map<String, Object> condition);
	
	/**
	 * 获取用户资金记录和
	 * @param condtion
	 * @return
	 */
	public double getUserAmount(Map<String, Object> condtion);
	
	/**
	 * 查询已投资数量
	 * @param tradeId
	 * @return
	 */
	public long countInvestedTransactions(long tradeId);
	
	/**
	 * 添加投资记录流水号
	 * @param tradeFlow
	 */
	public void addTradeFlowCode(TradeFlowCode tradeFlow);
	
	/**
	 * 查询交易流水号
	 * @param tradeId
	 * @return
	 */
	public List<TradeFlowCode> getTradeFlowCodes(long tradeId);
	
	/**
	 * 查询交易流水号
	 * @param detailId
	 * @return
	 */
	public TradeFlowCode getTradeFlowCodeByDetailId(long detailId);
	
	/**
	 * 查询审核记录
	 * @param conditions
	 * @return
	 */
	public List<TradeFlowCode> getListTradeFlowCode(Map<String, Object> conditions);
	
	/**
	 * 更改原始融资金额
	 * @param id
	 * @return
	 */
	public int updateTradeAmount(long id, long amount);
	
	/**
	 * 执行还款时间修订
	 * @param expireDate
	 * @param tradeId
	 */
	public int executeTradeExpireDateUpdate(Date expireDate, long tradeId);
	
	/**
	 * 更新担保机构合同流水号
	 * @param tradeFlow
	 */
	public int updateTradeFlowCode(TradeFlowCode tradeFlow);
	
	/**
	 * 删除交易
	 * @param tradeId
	 */
	public void deleteTrade(long tradeId);
	
	/**
	 * 删除借款流水号
	 * @param loanDetailId
	 */
	public void deleteTradeFlowCode(long loanDetailId);
	
	/**
	 * 統計已付款分潤金額
	 * @param userId
	 * @return
	 */
	public Long sumPaidDivisionAmount(Long userId);
	
	/**
	 * 统计金额
	 * @param params
	 * @return
	 */
	public long countAmountByParams(Map<String, Object> params);
	
	/**
	 * 统计收益金额
	 * @param params
	 * @return
	 */
	public long countInterestAmountByParams(Map<String, Object> params);
	
	/**
	 * 查询某个用户的对某融资需求的投资次数
	 * @param condition
	 * @return
	 */
	long countInvestTimesByParams(Map<String, Object> condition);
	
	public List<Trade> queryByStatus(int status) throws DataAccessException;
}
