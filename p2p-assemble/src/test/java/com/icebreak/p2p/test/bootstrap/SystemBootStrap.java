package com.icebreak.p2p.test.bootstrap;

import java.math.BigDecimal;

import com.icebreak.util.test.TomcatBootstrapHelper;

public class SystemBootStrap {
	
	public static void main(String[] args) {
//		new TomcatBootstrapHelper(8888, false, "dev").start();
		BigDecimal bd = new BigDecimal("0");
		System.out.println(bd.doubleValue());
	}
}
