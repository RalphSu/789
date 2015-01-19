package com.icebreak.p2p.transfer;

import java.util.Date;

import com.icebreak.p2p.dataobject.AmountFlow;
import com.icebreak.p2p.dataobject.DivisionDetail;

public interface TransferService {
	
	/**
	 * 转账 并记录资金流
	 * @param transferInfo
	 * @param inUserId
	 * @param outUserId
	 * @param type
	 * @return
	 */
	public boolean doTransfer(long tradeId, long tradeDetailId, String in, String out, long amount,
								String memo, Date date, long inUserId, long outUserId, String type);
	
	/**
	 * 转账  不记录资金流
	 * @param in
	 * @param out
	 * @param amount
	 * @param orderNo
	 * @param memo
	 * @param date
	 * @return
	 */
	public boolean doTransfer(String in, String out, long amount, String memo, Date date);
	
	/**
	 * 执行常规转账
	 * @param flowCode
	 * @param in
	 * @param out
	 * @param amount
	 * @param memo
	 * @param date
	 * @return
	 */
	public boolean transfer(String flowCode, String in, String out, long amount, String memo,
							Date date);
	
	/**
	 * 执行投资转账
	 * @param flowCode
	 * @param in
	 * @param out
	 * @param amount
	 * @param memo
	 * @param date
	 * @return
	 */
	public boolean dealTransfer(String flowCode, String in, String out, long amount, String memo,
								Date date);
	
	/**
	 * 判断是否有风险资金转账
	 * @param amountFlow
	 * @return
	 */
	public boolean isRiskAmountFlow(AmountFlow amountFlow);
	
	/**
	 * 判断是否有风险分润资金转账
	 * @param amountFlow
	 * @return
	 */
	public boolean isRiskDevisionDetail(DivisionDetail divisionDetail);
	
	/**
	 * 判断是否还款交易
	 * @param amountFlow Id
	 * @return
	 */
	public boolean isRepayDivision(long id);
	
	/**
	 * 判断是否还款计划 还款交易
	 * @param amountFlow Id
	 * @return
	 */
	public boolean isRepayPlanDivision(long id) ;
}
