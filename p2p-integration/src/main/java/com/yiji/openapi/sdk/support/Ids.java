package com.yiji.openapi.sdk.support;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiji.openapi.sdk.util.Dates;

/**
 * orderNo生成器
 * 
 * 简单实现：支持分布式环境，单机秒级并发1000
 * 
 *
 * 
 */
public class Ids {

	private static Ids ids = new Ids();

	private static final Logger logger = LoggerFactory.getLogger(Ids.class);
	private volatile int sequ = 0;

	public static Ids getInstance() {
		return ids;
	}

	/**
	 * 生产新orderNo
	 * 
	 * 总共16字符长度 yyMMddHHmm+3位本机IP末三位+3位随机数字
	 * 
	 * 
	 * @return
	 */
	public String getOrderNo() {
		String orderNo = Dates.format(new Date(), "yyMMddHHmm") + getNodeFlag()
				+ StringUtils.leftPad(String.valueOf(sequ++), 3, "0");
		if (sequ >= 1000) {
			sequ = 0;
		}
		return orderNo;
	}

	private String getNodeFlag() {
		String ipPostfix = null;
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			ipPostfix = StringUtils.substringAfterLast(ip, ".");
		} catch (Exception e) {
			logger.warn("生产OrderNo要素本机IP获取失败:" + e.getMessage());
		}
		return StringUtils.leftPad(ipPostfix, 3, "0");
	}

	private Ids() {
		super();
	}

	public static void main(String[] args) throws Exception {
		Ids ig = Ids.getInstance();
		int count = 1000;
		Map<String, Object> orderNos = new HashMap<String, Object>(count);
		String orderNo = null;
		for (int i = 0; i < count; i++) {
			orderNo = ig.getOrderNo();
			System.out.println((i + 1) + " : " + orderNo);
			orderNos.put(orderNo, 0);
		}
		System.out.println("count:" + count + ", orderNos size:" + orderNos.size());
	}

}
