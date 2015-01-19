package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.integration.openapi.enums.RealNameBusinessStatusEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public class RealNameStatusResult extends P2PBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 5998956869098507976L;
	RealNameBusinessStatusEnum	businessStatusEnum	= RealNameBusinessStatusEnum.UNAUTHERIZED;
	private String				msg;
	
	public RealNameBusinessStatusEnum getBusinessStatusEnum() {
		return businessStatusEnum;
	}
	
	public void setBusinessStatusEnum(RealNameBusinessStatusEnum businessStatusEnum) {
		this.businessStatusEnum = businessStatusEnum;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RealNameStatusResult [businessStatusEnum=");
		builder.append(businessStatusEnum);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
