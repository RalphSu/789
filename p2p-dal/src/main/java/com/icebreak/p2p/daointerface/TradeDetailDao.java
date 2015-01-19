package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.InvestDetailDO;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TradeQueryDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TradeDetailDao {
	/**
	 * 添加以条交易明细
	 */
	public long addTradeDetail(TradeDetail detail);
	
	/**
	 * 投资
	 * @param detail
	 */
	public void invest(TradeDetail detail);
	
	/**
	 * 根据交易ID查询和角色交易明细
	 * @param tradeId
	 * @return
	 */
	public List<TradeDetail> getByTradeIdAndRoles(long tradeId, String... roles);
	
	/**
	 * 根据用户ID获取该用户在托管机构的资金账户
	 * @param userId
	 * @return
	 */
	public String getYjfUserNameByUserId(long userId);
	
	/**
	 * 获取投资金额
	 * @return
	 */
	public long getAmount(long userId, String roleCode, short... status);
	
	/**
	 * 校验交易
	 * @param tradeId
	 * @param userId
	 * @param role
	 * @return
	 */
	public int check(long tradeId, long userId, String role);
	
	/**
	 * 获取tradeDetails
	 * @param conditions
	 * @return
	 */
	public List<TradeQueryDetail> getTradeDetailByConditions(Map<String, Object> conditions);
	
	/**
	 * 根据投资ID查询分润交易
	 * @param detailId
	 * @return
	 */
	public List<TradeDetail> getInvestProfitTrade(long detailId);
	
	/**
	 * 统计分润交易数量
	 * @param conditions
	 * @return
	 */
	public long getTradeDivisionDetailByConditionsCount(Map<String, Object> conditions);
	
	/**
	 * 查询分润交易
	 * @param conditions
	 * @return
	 */
	public List<TradeDetail> getTradeDivisionDetailByConditions(Map<String, Object> conditions);


    public List<TradeQueryDetail> getCollectionByConditions(Map<String, Object> conditions);

    public long getCollectionByConditionsCount(Map<String, Object> conditions);
	
	/**
	 * 删除借款交易
	 * @param loanDetailId
	 */
	public void deleteloanDetail(long loanDetailId);
	
	/**
	 * 投資詳情記錄
	 * @param condition
	 * @return
	 */
	public List<InvestDetailDO> queryInvestDetail(Map<String, Object> condition);
	
	/**
	 * 统计交易金额
	 * @param params
	 * @return
	 */
	public long sumAmountByCondition(Map<String, Object> params);

	void updateRepaydate(Long tradeDetailId, Date repayDate);
	
	void updatetActualRepayDate(Long tradeDetailId, Date actualRepayDate);
	
	void updatetStatus(Long tradeDetailId, String  status);

	public TradeDetail getById(long tradeDetailId);

}
