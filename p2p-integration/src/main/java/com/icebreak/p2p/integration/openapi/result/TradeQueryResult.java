package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.ws.result.P2PBaseResult;

public class TradeQueryResult extends P2PBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 5337116736728765620L;
	/**
	 * 地址
	 */
	private String				url;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
