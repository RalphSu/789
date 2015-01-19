package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.integration.openapi.info.QueryWithdrawInfo;
import com.icebreak.p2p.ws.result.P2PBaseResult;

import java.util.ArrayList;
import java.util.List;

public class WithdrawQueryResult extends P2PBaseResult {
	
	private static final long		serialVersionUID	= 1579674743834181628L;
	protected int					pageSize			= 10;
	private int						currPage;
	private long size;
	private List<QueryWithdrawInfo> data = new ArrayList<QueryWithdrawInfo>();

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public List<QueryWithdrawInfo> getData() {
		return data;
	}

	public void setData(List<QueryWithdrawInfo> data) {
		this.data = data;
	}
}
