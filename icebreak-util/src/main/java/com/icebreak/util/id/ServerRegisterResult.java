package com.icebreak.util.id;

import com.icebreak.util.lang.result.ResultBase;

public class ServerRegisterResult extends ResultBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;
	
	private int serverId;
	
	public int getServerId() {
		return serverId;
	}
	
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServerRegisterResult [serverId=").append(serverId).append(", isSuccess()=")
			.append(isSuccess()).append(", getMessage()=").append(getMessage()).append("]");
		return builder.toString();
	}
	
}
