package com.icebreak.p2p.web.util;

import java.math.BigDecimal;

import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.enums.LoanLimitUnitEnum;

public class RateProcessorUtil {
	
	//按天计算利率
	public static double getDaysRuleRate(double rule, Trade trade) {
		String timeLimitUnit = trade.getTimeLimitUnit();
		double timeLimit = trade.getTimeLimit();
		double days = 0;
		if (LoanLimitUnitEnum.LOAN_BY_DAY.code().equals(timeLimitUnit)) {
			days = timeLimit;
		} else if (LoanLimitUnitEnum.LOAN_BY_YEAR.code().equals(timeLimitUnit)) {
			days = timeLimit * YrdConstants.TimeRelativeConstants.DAYSOFAYEAR;
		} else {
			days = Math.round(timeLimit * YrdConstants.TimeRelativeConstants.DAYSOFAYEAR / 12);
		}
		BigDecimal bg = new BigDecimal(rule / YrdConstants.TimeRelativeConstants.DAYSOFAYEAR * days);
		double daysRate = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		return daysRate;
	}
}
