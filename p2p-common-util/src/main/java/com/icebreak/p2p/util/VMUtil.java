package com.icebreak.p2p.util;

import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.util.charge.enums.BankEnum;

public class VMUtil {

	public static String getBankName(String code) {
		BankEnum _enum = BankEnum.getByCode(code);
		if (_enum != null) {
			return _enum.getMessage();
		}
		return null;
	}
	
	public static String getDivisionName(String code) {
		DivisionTypeEnum _enum = DivisionTypeEnum.getByCode(code);
		if (_enum != null) {
			return _enum.getMessage();
		}
		return null;
	}
	
	public static String isDivisionIn(String code) {
		String re = "";
		DivisionTypeEnum _enum = DivisionTypeEnum.getByCode(code);
		switch (_enum) {
			case DEPOSIT:
			case REPAY:
			case PROFIT:
				re = "IN";
				break;
			default:
				re = "OUT";
				break;
		}
		return re;
	}

	public static String isDivisionIn(String code, String role) {
		String re = "IN";
		if("loaner".equals(role)) {
			re = "OUT";
		}
		DivisionTypeEnum _enum = DivisionTypeEnum.getByCode(code);
		switch (_enum) {
			case DEPOSIT:
				return "IN";
			case REPAY:
				return re;
			case PROFIT:
				return re;
			case LOAN:
				return "IN";
			case INVEST:
				return "OUT";
			case FEE:
				return "OUT";
			case WITHDRAW:
				return "OUT";
			default:
				re = "OUT";
				break;
		}
		return re;
	}
}
