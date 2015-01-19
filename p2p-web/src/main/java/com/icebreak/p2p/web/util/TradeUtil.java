package com.icebreak.p2p.web.util;

import java.math.BigDecimal;

import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.TradeFullConditionEnum;

public class TradeUtil {
	
	/**
	 * 获取显示属性
	 * @param method
	 * @param value
	 * @return
	 */
	public static String getSaturationCondition(String method, String value) {
		if(method==null || value==null){
			return null;
		}
		if (method.equals(TradeFullConditionEnum.AMOUNT.code())) {
			return "金额达到" + MoneyUtil.getFormatAmount(Long.parseLong(value)) + "元";
		} else if (method.equals(TradeFullConditionEnum.PERCENTAGE.code())) {
			BigDecimal bg = new BigDecimal(Double.parseDouble(value) * 100);
			double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return "投资金额比例达到" + d + "%";
		} else {
			return "截止时间至" + value;
		}
	}
	
	/**
	 * 获取显示状态
	 * @param trade
	 * @return
	 */
	public static String getNormalTradeStatus(Trade trade) {
		String tradeStatus = YrdConstants.TradeViewCanstants.TRADE_DEFAULT;
		switch (trade.getStatus()) {
			case YrdConstants.TradeStatus.TRADING:
				tradeStatus = YrdConstants.TradeViewCanstants.TRADE_TRADING;
				break;
			case YrdConstants.TradeStatus.FAILED:
				tradeStatus = YrdConstants.TradeViewCanstants.TRADE_FAILD;
				break;
			case YrdConstants.TradeStatus.REPAY_FINISH:
				tradeStatus = YrdConstants.TradeViewCanstants.TRADE_FINISH;
				break;
			case YrdConstants.TradeStatus.REPAYING_FAILD:
				tradeStatus = YrdConstants.TradeViewCanstants.TRADE_REPAY_FAILD;
				break;
			case YrdConstants.TradeStatus.REPAYING:
				tradeStatus = YrdConstants.TradeViewCanstants.TRADE_REPAYING;
				break;
			case YrdConstants.TradeStatus.GUARANTEE_AUDITING:
				tradeStatus = YrdConstants.TradeViewCanstants.GUARANTEE_AUDITING;
				break;
			case YrdConstants.TradeStatus.DOREPAY:
				tradeStatus = YrdConstants.TradeViewCanstants.DOREPAY;
				break;
			case YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH:
				tradeStatus = YrdConstants.TradeViewCanstants.COMPENSATORY_REPAY_FINISH;
				break;
		}
		return tradeStatus;
	}
	
	public static String getRepayStatus(Trade trade, TradeDetail detail) {
		String repayStatus = YrdConstants.TradeViewCanstants.TRADE_DEFAULT;
		if (YrdConstants.TradeStatus.TRADING == trade.getStatus()) {
			repayStatus = YrdConstants.TradeViewCanstants.TRADE_TRADING;
		} else if (YrdConstants.TradeStatus.REPAYING == trade.getStatus()) {
			if (DivisionPhaseEnum.INVESET_PHASE.code().equals(detail.getTransferPhase())) {
				repayStatus = YrdConstants.TradeViewCanstants.TRADE_PAID;
			} else if (DivisionPhaseEnum.REPAY_PHASE.code().equals(detail.getTransferPhase())) {
				repayStatus = YrdConstants.TradeViewCanstants.TRADE_NOT_PAID;
			}
			
		} else if (YrdConstants.TradeStatus.FAILED == trade.getStatus()) {
			repayStatus = YrdConstants.TradeViewCanstants.TRADE_FAILD;
		} else if (YrdConstants.TradeStatus.REPAYING_FAILD == trade.getStatus()) {
			if (DivisionPhaseEnum.INVESET_PHASE.code().equals(detail.getTransferPhase())) {
				repayStatus = YrdConstants.TradeViewCanstants.TRADE_PAID;
			} else if (DivisionPhaseEnum.REPAY_PHASE.code().equals(detail.getTransferPhase())) {
				repayStatus = YrdConstants.TradeViewCanstants.TRADE_NOT_PAID;
			}
		} else if (YrdConstants.TradeStatus.REPAY_FINISH == trade.getStatus()) {
			repayStatus = YrdConstants.TradeViewCanstants.TRADE_PAID;
		} else if (YrdConstants.TradeStatus.GUARANTEE_AUDITING == trade.getStatus()) {
			repayStatus = YrdConstants.TradeViewCanstants.GUARANTEE_AUDITING;
		} else if (YrdConstants.TradeStatus.DOREPAY == trade.getStatus()) {
			if (DivisionPhaseEnum.INVESET_PHASE.code().equals(detail.getTransferPhase())) {
				repayStatus = YrdConstants.TradeViewCanstants.TRADE_PAID;
			} else if (DivisionPhaseEnum.REPAY_PHASE.code().equals(detail.getTransferPhase())) {
				repayStatus = YrdConstants.TradeViewCanstants.TRADE_NOT_PAID;
			}
		} else if (YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH == trade.getStatus()) {
			repayStatus = YrdConstants.TradeViewCanstants.TRADE_PAID;
		}
		
		return repayStatus;
	}
}
