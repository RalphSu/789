package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.integration.openapi.info.ThirdPartAccountInfo;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public class QueryAccountResult extends P2PBaseResult {
	
	private static final long	serialVersionUID	= -1113927000041346504L;
	ThirdPartAccountInfo				thirdPartAccountInfo;
	
	
	public ThirdPartAccountInfo getThirdPartAccountInfo() {
		return this.thirdPartAccountInfo;
	}


	public void setThirdPartAccountInfo(ThirdPartAccountInfo thirdPartAccountInfo) {
		this.thirdPartAccountInfo = thirdPartAccountInfo;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryAccountResult [thirdPartAccountInfo=");
		builder.append(thirdPartAccountInfo);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
