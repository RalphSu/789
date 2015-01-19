package com.icebreak.p2p.common.result;

import com.icebreak.p2p.common.enums.SmsCodeEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public class SmsCodeResult extends P2PBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -2017055815087643810L;
	SmsCodeEnum					smsCodeEnum			= SmsCodeEnum.Fail;
	
	public SmsCodeEnum getSmsCodeEnum() {
		return smsCodeEnum;
	}
	
	public void setSmsCodeEnum(SmsCodeEnum smsCodeEnum) {
		this.smsCodeEnum = smsCodeEnum;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmsCodeResult [smsCodeEnum=");
		builder.append(smsCodeEnum);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
