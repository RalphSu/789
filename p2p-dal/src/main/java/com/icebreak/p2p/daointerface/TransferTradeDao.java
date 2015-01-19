package com.icebreak.p2p.daointerface;

import java.util.List;

import com.icebreak.p2p.dataobject.TransferTrade;

public interface TransferTradeDao {
	/**
	 * 根据交易ID和角色代码获取
	 * @param tradeId
	 * @param roleCode
	 * @return
	 */
	public TransferTrade getTransferTrade(long tradeId, String roleCode);
	/**
	 * 根据交易ID获取已借款的金额
	 * @param tradeId
	 * @param roleCode
	 * @return
	 */
	public long getTradeAmount(long tradeId, String roleCode);
	/**
	 * 根据交易ID和角色代码获取
	 * @param tradeId
	 * @param roleCode
	 * @return
	 */
	public List<TransferTrade> getTransferTrades(long tradeId, String... roles);
	/**
	 * 获取分润的TransferTrade
	 * @param tradeId 交易ID
	 * @param status 是否成功
	 * @return
	 */
	public List<TransferTrade> getDivisionTransferTrades(long tradeId, boolean status);
	/**
	 * 根据用户ID集合获取起经纪人的TransferTrade
	 * @param tradeId 交易ID
	 * @param roleId 角色ID
	 * @param ids 投资人id集合
	 * @return
	 */
	public List<TransferTrade> getBrokerTransferTrades(long tradeId, long roleId, List<Long> ids);
	/**
	 * 根据用户ID集合获取起运营机构的TransferTrade
	 * @param tradeId 交易ID
	 * @param roleId 角色ID
	 * @param ids 投资人id集合
	 * @return
	 */
	public List<TransferTrade> getMarketingTransferTrades(long tradeId, long roleId, List<Long> ids);
	/**
	 * 按照阶段寻找交易
	 * @param id
	 * @param code
	 * @param iNVESET_PHASE
	 * @return
	 */
	//public List<TransferTrade> getPhaseTransferTrades(long tradeId,String transferPhase, String tradeDetailStatus, String... roles);
	public List<TransferTrade> getPhaseTransferTrades(long tradeId, String transferPhase, String tradeDetailStatus, String[] roles);

	/**
	 * 按照阶段寻找交易
	 * @param tradeId
	 * @param transferPhase
	 * @param periodNo 期数
	 * @param roles
	 * @return
	 */
	public List<TransferTrade> getPhaseTransferTrades(long tradeId,String transferPhase,int periodNo ,String... roles);
	
	/**
	 * 按照阶段寻找交易
	 * @param id
	 * @param code
	 * @param iNVESET_PHASE
	 * @return
	 */
	public TransferTrade getPhaseTransferTrade(long tradeId, String transferPhase,
			String roleCode);

    public List<TransferTrade> getPhaseTransferTradesCharge(long tradeId,String transferPhase, String... roles);

}
