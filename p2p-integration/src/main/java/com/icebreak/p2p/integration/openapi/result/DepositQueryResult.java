package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.integration.openapi.info.DepositInfo;
import com.icebreak.p2p.ws.result.P2PBaseResult;

import java.util.List;

public class DepositQueryResult extends P2PBaseResult {
	
	private static final long	serialVersionUID	= 4580141920460247004L;
	protected int				pageSize			= 10;
	private List<DepositInfo> data;
	private int currPage;
	private long size;

	public int getCurrPage() {
		return currPage;
	}
	
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<DepositInfo> getData() {
		return data;
	}
	
	public void setData(List<DepositInfo> data) {
		this.data = data;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "DepositQueryResult{" + "pageSize=" + pageSize + ", data=" + data + ", currPage="
				+ currPage + ", size=" + size + '}';
	}
}
