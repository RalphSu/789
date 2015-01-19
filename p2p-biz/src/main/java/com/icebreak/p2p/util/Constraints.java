package com.icebreak.p2p.util;

import com.icebreak.util.lang.util.ConstraintUtil;

public class Constraints {
	public static ConstraintUtil SMS_OR_MAIL_CON = ConstraintUtil.newBuilder().count(10)
		.interval(60).build();
	public static ConstraintUtil DATA_CON = ConstraintUtil.newBuilder().count(100).interval(60)
		.build();
	public static ConstraintUtil DATA_CON_MAX = ConstraintUtil.newBuilder().count(100).interval(10)
		.build();
	
	public static ConstraintUtil DATA_CON_LOGIN = ConstraintUtil.newBuilder().count(100)
		.interval(10).build();
}
