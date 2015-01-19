package com.icebreak.p2p.util;

import com.icebreak.util.lang.util.money.Money;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyUtil {
	//金额显示格式
	private static final String FORMATTEXT = "###,##0";
	//显示精度
	private static int PRECISION = 2;
	//显示零
	private static String ZERO = "0.00";
	
	public static String getMoney(long m) {
		return new Money((m / 100), (int) (m % 100)).toString();
	}
	
	public static Money getMoneyByLong(long m) {
		Money money = new Money();
		money.setCent(m);
		return money;
	}
	
	/**
	 * 还需N万， 求N
	 * @param amount
	 * @param loanedamount
	 * @return
	 */
	public static double getMoneyByw(long amount, long loanedamount) {
		return (((double) amount - (double) loanedamount) / 1000000.00);
	}
	
	/**
	 * wan
	 * @param amount
	 * @return
	 */
	public static double getMoneyByw(long amount) {
		return (amount / 1000000.00);
	}
	
	/**
	 * 字符串转为long
	 * @param s
	 * @return
	 */
	public static long parseLong(String s) {

		return Long.parseLong(s);
	}
	
	/**
	 * 计算百分百
	 * @param molecular 分子
	 * @param denominator 分母
	 * @return
	 */
	public static String getPercentage(long molecular, long denominator, long leastAmount) {
		//		if((denominator - molecular) < leastAmount){
		//			return "100%";
		//		}
		if (denominator == 0) {
			return "0.00%";
		}
		float f = ((float) (((double) molecular) / ((double) denominator))) * 100;
		f = new BigDecimal(f).setScale(PRECISION, BigDecimal.ROUND_HALF_UP).floatValue();
		return f + "%";
		
	}
	
	/**
	 * 格式化金额 input long
	 */
	
	public static String getFormatAmount(long formatAmount) {
		double fromAmount = formatAmount;
		double fenAmount = new BigDecimal(fromAmount / 100).setScale(PRECISION,
			BigDecimal.ROUND_HALF_UP).doubleValue();
		String amount = String.valueOf(fenAmount);
		if (amount == null || amount.length() < 1) {
			return ZERO;
		}
		NumberFormat formater = null;
		double number = Double.parseDouble(amount);
		if (PRECISION > 0) {
			StringBuffer buff = new StringBuffer();
			buff.append(FORMATTEXT).append(".");
			for (int i = 0; i < PRECISION; i++) {
				buff.append("0");
			}
			formater = new DecimalFormat(buff.toString());
		} else {
			formater = new DecimalFormat(FORMATTEXT);
		}
		return formater.format(number);
	}
	
	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	public static String digitUppercase(long formatAmount) {
		double fromAmount = formatAmount;
		double n = new BigDecimal(fromAmount / 100).setScale(PRECISION, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };
		String head = n < 0 ? "负" : "";
		n = Math.abs(n);
		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
				.replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);
		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零")
					.replaceAll("^整$", "零元整");
	}
	
	/**
	 * 获取年华利率
	 * @param value
	 * @return
	 */
	public static double getPercentage(double value) {
		BigDecimal bg = new BigDecimal(value * 100);
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}

    public static String getPercentageUppercase(double value) {
        BigDecimal bg = new BigDecimal(value * 100);
        double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return numberToChinese(d);
    }


    private final static String[] STR_NUMBER = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

    /** 定义数组存放位数的大写 */
    private final static String[] STR_MODIFY = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟" };

    /**
     * 转化整数部分
     *
     * @param tempString
     * @return 返回整数部分
     */
    private static String getInteger(String tempString) {
        /** 用来保存整数部分数字串 */
        String strInteger = null;//
        /** 记录"."所在位置 */
        int intDotPos = tempString.indexOf(".");
        int intSignPos = tempString.indexOf("-");
        if (intDotPos == -1)
            intDotPos = tempString.length();
        /** 取出整数部分 */
        strInteger = tempString.substring(intSignPos + 1, intDotPos);
        strInteger = new StringBuffer(strInteger).reverse().toString();
        StringBuffer sbResult = new StringBuffer();
        for (int i = 0; i < strInteger.length(); i++) {
            sbResult.append(STR_MODIFY[i]);
            sbResult.append(STR_NUMBER[strInteger.charAt(i) - 48]);
        }

        sbResult = sbResult.reverse();
        replace(sbResult, "零拾", "零");
        replace(sbResult, "零佰", "零");
        replace(sbResult, "零仟", "零");
        replace(sbResult, "零万", "万");
        replace(sbResult, "零亿", "亿");
        replace(sbResult, "零零", "零");
        replace(sbResult, "零零零", "零");
        /** 这两句不能颠倒顺序 */
        replace(sbResult, "零零零零万", "");
        replace(sbResult, "零零零零", "");
        /** 这样读起来更习惯. */
        replace(sbResult, "壹拾亿", "拾亿");
        replace(sbResult, "壹拾万", "拾万");
        /** 删除个位上的零 */
        if (sbResult.charAt(sbResult.length() - 1) == '零' && sbResult.length() != 1)
            sbResult.deleteCharAt(sbResult.length() - 1);
        if (strInteger.length() == 2) {
            replace(sbResult, "壹拾", "拾");
        }
        /** 将结果反转回来. */
        return sbResult.toString();
    }

    /**
     * 转化小数部分 例：输入22.34返回叁肆
     *
     * @param tempString
     * @return
     */
    private static String getFraction(String tempString) {
        String strFraction = null;
        int intDotPos = tempString.indexOf(".");
        /** 没有点说明没有小数，直接返回 */
        if (intDotPos == -1)
            return "";
        strFraction = tempString.substring(intDotPos + 1);
        StringBuffer sbResult = new StringBuffer(strFraction.length());
        for (int i = 0; i < strFraction.length(); i++) {
            sbResult.append(STR_NUMBER[strFraction.charAt(i) - 48]);
        }
        return sbResult.toString();
    }

    /**
     * 判断传入的字符串中是否有.如果有则返回点
     *
     * @param tempString
     * @return
     */
    private static String getDot(String tempString) {
        return tempString.indexOf(".") != -1 ? "点" : "";
    }

    /**
     * 判断传入的字符串中是否有-如果有则返回负
     *
     * @param tempString
     * @return
     */
    private static String getSign(String tempString) {
        return tempString.indexOf("-") != -1 ? "负" : "";
    }

    /**
     * 将一个数字转化为金额
     *
     * @param tempNumber 传入一个double的变量
     * @return 返一个转换好的字符串
     */
    public static String numberToChinese(double tempNumber) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#########");
        String pTemp = String.valueOf(df.format(tempNumber));
        StringBuffer sbResult = new StringBuffer(getSign(pTemp) + getInteger(pTemp) + getDot(pTemp) + getFraction(pTemp));
        return sbResult.toString();
    }

    public static String numberToChinese(BigDecimal tempNumber) {
        return numberToChinese(tempNumber.doubleValue());
    }

    /**
     * 替代字符
     *
     * @param pValue
     * @param pSource
     * @param pDest
     */
    private static void replace(StringBuffer pValue, String pSource, String pDest) {
        if (pValue == null || pSource == null || pDest == null)
            return;
        /** 记录pSource在pValue中的位置 */
        int intPos = 0;
        do {
            intPos = pValue.toString().indexOf(pSource);
            /** 没有找到pSource */
            if (intPos == -1)
                break;
            pValue.delete(intPos, intPos + pSource.length());
            pValue.insert(intPos, pDest);
        } while (true);
    }
	
	
	/**
	 * 将格式化后的金额转成double类型
	 * @param value
	 * @return
	 */
	public static double money2Double(String value) {
		String str = value.replaceAll(",", "");
		return Double.parseDouble(str);
	}
	
	public static String getFormatAmountByMoney(Money formatAmount) {
		return getFormatAmount(formatAmount.getCent());
	}

	public static String formatAmountByDouble(double amount) {
		return getFormatAmount((long)amount);
	}
	
	public static void main(String[] args) {
		System.out.println(getPercentage(99, 100, 2));
	}
}
