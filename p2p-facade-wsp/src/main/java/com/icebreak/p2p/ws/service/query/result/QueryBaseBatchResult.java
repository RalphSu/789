package com.icebreak.p2p.ws.service.query.result;

import com.icebreak.p2p.ws.base.PageComponent;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.util.lang.util.money.Money;

import java.util.List;

public class QueryBaseBatchResult<T> extends P2PBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 420002915574977408L;
	long totalCount = 0;
	Money totalMoney = new Money(0);
	long pageSize = 10;
	long pageNumber = 1;
	List<T> pageList;
	
	public void initPageParam(PageComponent component) {
		this.setTotalCount(component.getRowCount());
		this.setPageSize(component.getPageSize());
		this.setPageNumber(component.getCurPage());
	}
	
	public List<T> getPageList() {
		return pageList;
	}
	
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Money getTotalMoney() {
		return this.totalMoney;
	}
	
	public void setTotalMoney(Money totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryBaseBatchResult [totalCount=");
		builder.append(totalCount);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append(", pageList=");
		builder.append(pageList);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
