package com.icebreak.p2p.web.util;

import java.math.BigDecimal;

public class RateUtil {
	public static String getRate(double value){
		BigDecimal bg = new BigDecimal(value * 100);
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d+"%";
	}

}
