package com.icebreak.p2p.web.util;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.TradeDetailStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeStatisticsUtil {
	protected static final Logger	logger	= LoggerFactory.getLogger(TradeStatisticsUtil.class);

	public static void countInvestedMoney(List<TradeQueryDetail> investTradeDetails,
											Map<String, Object> investCountMap) {
		//投资次数
		int totalTransactions = 0;
		//累计投资金额
		long totalInvestedAmount = 0l;
		//募集中的投资金额
		long collectingInvestedAmount = 0l;
		//募集中的次数
		long totalCollectingInvested = 0l;
		//待还款投资金额
		long toPayInvestedAmount = 0l;
		//已还款投资金额
		long paidInvestedAmount = 0l;
		//待还款投资金额
		long notPaidInvestedAmount = 0l;
		//失败投资次数
		int faildTransactions = 0;
		//失败投资金额
		long faildInvestedAmount = 0l;
		//到期未还款投资金额
		long repayFaildLoanedAmount = 0l;
		//已还款投资本金金额
		long paidInvestedPrincipleAmount = 0l;
		//已还款投资本金数量
		long totalPaidInvestedPrinciple = 0l;
		
		//待还款投资本金金额
		long notPaidInvestedPrincipleAmount = 0l;
		//累计投资收益金额
		long investedProfitAmount = 0l;
		//已回款投资收益金额
		long paidInvestedProfitAmount = 0l;
		
		long totalPaidInvestedProfit = 0l;
		//待回款投资金额
		long notPaidInvestedProfitAmount = 0l;

		//待收本金（包含投资支付成功至还款前的金额）
		long waitGetMoneyAmount = 0l;
		//待收利息
		long waitGetInterestMoneyAmount = 0l;
		
		//待回款次数
		long totalNotPaidInvestedProfit = 0l;
		if (investTradeDetails != null && investTradeDetails.size() > 0) {
			for (TradeQueryDetail tradeDetail : investTradeDetails) {
				if (tradeDetail != null) {
					if (DivisionPhaseEnum.ORIGINAL_PHASE.code().equals(
						tradeDetail.getTransferPhase())) {
						long tradeAmount = tradeDetail.getAmount();
						totalInvestedAmount += tradeAmount;
						totalTransactions++;
						switch (tradeDetail.getStatus()) {
							case YrdConstants.TradeStatus.REPAYING_FAILD:
								repayFaildLoanedAmount += tradeAmount;

								//还款失败，只记录支付成功的记录
								if(TradeDetailStatusEnum.PS.code().equals(tradeDetail.getTradeDetailStatus())){
									waitGetMoneyAmount += tradeAmount;

									notPaidInvestedProfitAmount += tradeAmount;
									totalNotPaidInvestedProfit++;
								}
								break;
							case YrdConstants.TradeStatus.TRADING:


								//未满标，只记录支付成功的记录
								if(TradeDetailStatusEnum.PS.code().equals(tradeDetail.getTradeDetailStatus())){
									collectingInvestedAmount += tradeAmount;
									waitGetMoneyAmount += tradeAmount;
									totalCollectingInvested++;
								} else {
									faildTransactions++;
									faildInvestedAmount += tradeAmount;
								}
								break;
							case YrdConstants.TradeStatus.REPAYING:
								toPayInvestedAmount += tradeAmount;

								//notPaidInvestedAmount += tradeAmount;

								//满标，只记录支付成功的记录
								if(TradeDetailStatusEnum.PS.code().equals(tradeDetail.getTradeDetailStatus())){
									waitGetMoneyAmount += tradeAmount;
									totalNotPaidInvestedProfit++;
									notPaidInvestedProfitAmount += tradeAmount;
								} else {
									faildTransactions++;
									faildInvestedAmount += tradeAmount;
								}
								break;
							case YrdConstants.TradeStatus.FAILED:
								faildTransactions++;
								faildInvestedAmount += tradeAmount;
								break;
							case YrdConstants.TradeStatus.REPAY_FINISH:

								paidInvestedAmount += tradeAmount;
								totalPaidInvestedProfit++;
								if(TradeDetailStatusEnum.PS.code().equals(tradeDetail.getTradeDetailStatus())) {
									totalPaidInvestedPrinciple++;
									paidInvestedPrincipleAmount += tradeAmount;
								} else {
									faildTransactions++;
									faildInvestedAmount += tradeAmount;
								}
								break;
							case YrdConstants.TradeStatus.GUARANTEE_AUDITING:
								if(TradeDetailStatusEnum.PS.code().equals(tradeDetail.getTradeDetailStatus())){
									waitGetMoneyAmount += tradeAmount;
									collectingInvestedAmount += tradeAmount;
									totalCollectingInvested++;
								}
								break;
							case YrdConstants.TradeStatus.DOREPAY:
								toPayInvestedAmount += tradeAmount;

								//待还款，只记录支付成功的记录
								if(TradeDetailStatusEnum.PS.code().equals(tradeDetail.getTradeDetailStatus())){
									waitGetMoneyAmount += tradeAmount;
									notPaidInvestedProfitAmount += tradeAmount;
									totalNotPaidInvestedProfit++;
								}
								break;
							case YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH:
								if(TradeDetailStatusEnum.PS.code().equals(tradeDetail.getTradeDetailStatus())) {
									totalPaidInvestedPrinciple++;
									paidInvestedPrincipleAmount += tradeAmount;
								}
								totalPaidInvestedProfit++;
								break;
							default:
								logger.info("统计异常！");
								break;
						}
					} else {
						long benefitAmount = tradeDetail.getAmount();
						investedProfitAmount += benefitAmount;
						switch (tradeDetail.getStatus()) {
							case YrdConstants.TradeStatus.REPAYING_FAILD:
								//notPaidInvestedProfitAmount += benefitAmount;
								//totalNotPaidInvestedProfit++;
								break;
							case YrdConstants.TradeStatus.TRADING: //不计算待成立的投资  
								break;
							case YrdConstants.TradeStatus.REPAYING:
								notPaidInvestedAmount += benefitAmount;
								//还款阶段记录待收益(但是状态为IT的，即收益创建但是还成功支付，也非支付失败的记录)
								if(DivisionPhaseEnum.REPAY_PHASE.code().equals(
										tradeDetail.getTransferPhase())
										&& !TradeDetailStatusEnum.IT.code().equals(tradeDetail.getTradeDetailStatus())){
									waitGetInterestMoneyAmount+=benefitAmount;
								}
								//notPaidInvestedProfitAmount += benefitAmount;
								//totalNotPaidInvestedProfit++;
								break;
							case YrdConstants.TradeStatus.FAILED://不计算未成立的投资  
								break;
							case YrdConstants.TradeStatus.REPAY_FINISH:
								paidInvestedProfitAmount += benefitAmount;
								totalPaidInvestedProfit++;
								break;
							case YrdConstants.TradeStatus.GUARANTEE_AUDITING:
								//notPaidInvestedProfitAmount += benefitAmount;
								//totalNotPaidInvestedProfit++;
								break;
							case YrdConstants.TradeStatus.DOREPAY:
								//notPaidInvestedProfitAmount += benefitAmount;
								totalNotPaidInvestedProfit++;
								notPaidInvestedAmount += benefitAmount;
								break;
							case YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH:
								paidInvestedProfitAmount += benefitAmount;
								totalPaidInvestedProfit++;
								break;
						}
					}
				}
			}
			notPaidInvestedPrincipleAmount = toPayInvestedAmount + repayFaildLoanedAmount;
			paidInvestedAmount = paidInvestedPrincipleAmount + paidInvestedProfitAmount;
			//notPaidInvestedAmount = notPaidInvestedPrincipleAmount + notPaidInvestedProfitAmount;
		}
		
		investCountMap.put("totalTransactions", totalTransactions);
		investCountMap.put("faildTransactions", faildTransactions);
		investCountMap.put("totalInvestedAmount", MoneyUtil.getMoneyByLong(totalInvestedAmount));
		investCountMap.put("collectingInvestedAmount",
			MoneyUtil.getMoneyByLong(collectingInvestedAmount));
		investCountMap.put("toPayInvestedAmount", MoneyUtil.getMoneyByLong(toPayInvestedAmount));
		investCountMap.put("paidInvestedAmount", MoneyUtil.getMoneyByLong(paidInvestedAmount));
		investCountMap
			.put("notPaidInvestedAmount", MoneyUtil.getMoneyByLong(notPaidInvestedAmount));
		investCountMap.put("faildInvestedAmount", MoneyUtil.getMoneyByLong(faildInvestedAmount));
		investCountMap.put("paidInvestedPrincipleAmount",
			MoneyUtil.getMoneyByLong(paidInvestedPrincipleAmount));
		investCountMap.put("notPaidInvestedPrincipleAmount",
			MoneyUtil.getMoneyByLong(notPaidInvestedPrincipleAmount));
		investCountMap.put("paidInvestedProfitAmount",
			MoneyUtil.getMoneyByLong(paidInvestedProfitAmount));
		investCountMap.put("notPaidInvestedProfitAmount",
			MoneyUtil.getMoneyByLong(notPaidInvestedProfitAmount));
		investCountMap.put("investedProfitAmount", MoneyUtil.getMoneyByLong(investedProfitAmount));
		
		investCountMap.put("totalCollectingInvested", totalCollectingInvested);
		investCountMap.put("totalPaidInvestedPrinciple", totalPaidInvestedPrinciple);
		investCountMap.put("totalNotPaidInvestedProfit", totalNotPaidInvestedProfit);
		investCountMap.put("totalPaidInvestedProfit", totalPaidInvestedProfit);

		investCountMap.put("waitGetMoneyAmount", MoneyUtil.getMoneyByLong(waitGetMoneyAmount));
		investCountMap.put("waitGetInterestMoneyAmount", MoneyUtil.getMoneyByLong(waitGetInterestMoneyAmount));
		
	}
	
	public static void setMoney(Map<String, Object> investCountMap) {
		//投资次数
		int totalTransactions = 0;
		//累计投资金额
		long totalInvestedAmount = 0l;
		//募集中的投资金额
		long collectingInvestedAmount = 0l;
		//募集中的次数
		long totalCollectingInvested = 0l;
		//待还款投资金额
		long toPayInvestedAmount = 0l;
		//已还款投资金额
		long paidInvestedAmount = 0l;
		//待还款投资金额
		long notPaidInvestedAmount = 0l;
		//失败投资次数
		int faildTransactions = 0;
		//失败投资金额
		long faildInvestedAmount = 0l;
		//到期未还款投资金额
		long repayFaildLoanedAmount = 0l;
		//已还款投资本金金额
		long paidInvestedPrincipleAmount = 0l;
		//待还款投资本金金额
		long notPaidInvestedPrincipleAmount = 0l;
		//累计投资收益金额
		long investedProfitAmount = 0l;
		//已回款投资收益金额
		long paidInvestedProfitAmount = 0l;
		
		long totalPaidInvestedProfit = 0l;
		//待回款投资收益金额
		long notPaidInvestedProfitAmount = 0l;
		//待回款次数
		long totalNotPaidInvestedProfit = 0l;

		//待收本金（包含投资支付成功至还款前的金额）
		long waitGetMoneyAmount = 0l;
		//待收利息
		long waitGetInterestMoneyAmount = 0l;

		investCountMap.put("totalTransactions", totalTransactions);
		investCountMap.put("faildTransactions", MoneyUtil.getMoneyByLong(faildTransactions));
		investCountMap.put("totalInvestedAmount", MoneyUtil.getMoneyByLong(totalInvestedAmount));
		investCountMap.put("collectingInvestedAmount",
			MoneyUtil.getMoneyByLong(collectingInvestedAmount));
		investCountMap.put("toPayInvestedAmount", MoneyUtil.getMoneyByLong(toPayInvestedAmount));
		investCountMap.put("paidInvestedAmount", MoneyUtil.getMoneyByLong(paidInvestedAmount));
		investCountMap
			.put("notPaidInvestedAmount", MoneyUtil.getMoneyByLong(notPaidInvestedAmount));
		investCountMap.put("faildInvestedAmount", MoneyUtil.getMoneyByLong(faildInvestedAmount));
		investCountMap.put("paidInvestedPrincipleAmount",
			MoneyUtil.getMoneyByLong(paidInvestedPrincipleAmount));
		investCountMap.put("notPaidInvestedPrincipleAmount",
			MoneyUtil.getMoneyByLong(notPaidInvestedPrincipleAmount));
		investCountMap.put("paidInvestedProfitAmount",
			MoneyUtil.getMoneyByLong(paidInvestedProfitAmount));
		investCountMap.put("notPaidInvestedProfitAmount",
			MoneyUtil.getMoneyByLong(notPaidInvestedProfitAmount));
		investCountMap.put("investedProfitAmount", MoneyUtil.getMoneyByLong(investedProfitAmount));
		
		investCountMap.put("totalCollectingInvested", totalCollectingInvested);
		investCountMap.put("totalNotPaidInvestedProfit", totalNotPaidInvestedProfit);
		investCountMap.put("totalCollectingInvested", totalCollectingInvested);
		investCountMap.put("totalPaidInvestedProfit", totalPaidInvestedProfit);
		investCountMap.put("totalPaidInvestedPrinciple", 0);

		investCountMap.put("waitGetMoneyAmount", MoneyUtil.getMoneyByLong(waitGetMoneyAmount));
		investCountMap.put("waitGetInterestMoneyAmount", MoneyUtil.getMoneyByLong(waitGetInterestMoneyAmount));
	}

	//融资统计
	public static void setLoanCountMoney(Map<String, Object> loanedCountMap) {
		//累计借款金额
		long totalLoanedAmount = 0l;
		
		//待还款金额
		long toPayLoanedAmount = 0l;
		//待还款次数
		long totalToPayLoaned = 0l;
		//已还款金额
		long paidLoanedAmount = 0l;
		//已还款次数
		long totalPaidLoaned = 0l;
		//募集中的借款
		long collectingLoanedAmount = 0l;
		//募集中的借款
		long totalCollectingLoaned = 0l;
		//到期未还款的借款
		long repayFaildLoanedAmount = 0l;
		
		//失败投资次数
		int faildTransactions = 0;
		//失败的借款
		long faildLoanedAmount = 0l;
		//成功的借款
		long successLoanedAmount = 0l;
		//借款次数
		int totalTransactions = 0;
		loanedCountMap.put("totalTransactions", (totalTransactions));
		loanedCountMap.put("faildTransactions", (faildTransactions));
		loanedCountMap.put("totalCollectingLoaned", (totalCollectingLoaned));
		loanedCountMap.put("totalToPayLoaned", (totalToPayLoaned));
		loanedCountMap.put("totalPaidLoaned", (totalPaidLoaned));
		loanedCountMap.put("collectingLoanedAmount",
			MoneyUtil.getMoneyByLong(collectingLoanedAmount));
		loanedCountMap.put("toPayLoanedAmount", MoneyUtil.getMoneyByLong(toPayLoanedAmount));
		loanedCountMap.put("totalLoanedMoney", MoneyUtil.getMoneyByLong(totalLoanedAmount));
		loanedCountMap.put("successLoanedMoney", MoneyUtil.getMoneyByLong(successLoanedAmount));
		loanedCountMap.put("paidLoanedAmount", MoneyUtil.getMoneyByLong(paidLoanedAmount));
		logger.info("collectingLoanedAmount={},faildLoanedAmount={}", collectingLoanedAmount,
			faildLoanedAmount);
	}

	//融资统计
	public static void countLoanedMoney(List<TradeQueryDetail> loanTradeDetails,
										Map<String, Object> loanedCountMap) {
		//累计借款金额
		long totalLoanedAmount = 0l;
		
		//待还款金额
		long toPayLoanedAmount = 0l;
		//待还款次数
		long totalToPayLoaned = 0l;
		//已还款金额
		long paidLoanedAmount = 0l;
		//已还款次数
		long totalPaidLoaned = 0l;
		//募集中的借款
		long collectingLoanedAmount = 0l;
		//募集中的借款
		long totalCollectingLoaned = 0l;
		//到期未还款的借款
		long repayFaildLoanedAmount = 0l;
		
		//失败投资次数
		int faildTransactions = 0;
		//失败的借款
		long faildLoanedAmount = 0l;
		//成功的借款
		long successLoanedAmount = 0l;
		
		//借款次数
		int totalTransactions = 0;
		if (loanTradeDetails != null && loanTradeDetails.size() > 0) {
			totalTransactions = loanTradeDetails.size();
			for (TradeQueryDetail tradeDetail : loanTradeDetails) {
				
				if (tradeDetail != null) {
					long tradeAmount = tradeDetail.getTradeAmount();
					long actualtradeAmount = tradeDetail.getLoanedAmount();
					totalLoanedAmount += tradeAmount;
					switch (tradeDetail.getStatus()) {
						case YrdConstants.TradeStatus.REPAYING_FAILD:
							repayFaildLoanedAmount += actualtradeAmount;
							break;
						case YrdConstants.TradeStatus.TRADING:
							collectingLoanedAmount += tradeAmount;
							totalCollectingLoaned++;
							break;
						case YrdConstants.TradeStatus.REPAYING:
							toPayLoanedAmount += actualtradeAmount;
							totalToPayLoaned++;
							break;
						case YrdConstants.TradeStatus.FAILED:
							faildTransactions++;
							faildLoanedAmount += tradeAmount;
							break;
						case YrdConstants.TradeStatus.REPAY_FINISH:
							paidLoanedAmount += actualtradeAmount;
							totalPaidLoaned++;
							break;
						case YrdConstants.TradeStatus.GUARANTEE_AUDITING:
							collectingLoanedAmount += actualtradeAmount;
							totalCollectingLoaned++;
							break;
						case YrdConstants.TradeStatus.DOREPAY:
							toPayLoanedAmount += actualtradeAmount;
							totalToPayLoaned++;
							break;
						case YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH:
							paidLoanedAmount += actualtradeAmount;
							totalPaidLoaned++;
							break;
					}
				}
			}
			successLoanedAmount = paidLoanedAmount + toPayLoanedAmount + repayFaildLoanedAmount;
		}
		loanedCountMap.put("totalTransactions", (totalTransactions));
		loanedCountMap.put("faildTransactions", (faildTransactions));
		loanedCountMap.put("totalCollectingLoaned", (totalCollectingLoaned));
		loanedCountMap.put("totalToPayLoaned", (totalToPayLoaned));
		loanedCountMap.put("totalPaidLoaned", (totalPaidLoaned));
		loanedCountMap.put("collectingLoanedAmount",
			MoneyUtil.getMoneyByLong(collectingLoanedAmount));
		loanedCountMap.put("toPayLoanedAmount", MoneyUtil.getMoneyByLong(toPayLoanedAmount));
		loanedCountMap.put("totalLoanedMoney", MoneyUtil.getMoneyByLong(totalLoanedAmount));
		loanedCountMap.put("successLoanedMoney", MoneyUtil.getMoneyByLong(successLoanedAmount));
		loanedCountMap.put("paidLoanedAmount", MoneyUtil.getMoneyByLong(paidLoanedAmount));
		logger.info("collectingLoanedAmount={},faildLoanedAmount={}", collectingLoanedAmount,
			faildLoanedAmount);
	}
}
