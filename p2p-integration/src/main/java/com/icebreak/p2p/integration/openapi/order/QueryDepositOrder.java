package com.icebreak.p2p.integration.openapi.order;

import com.icebreak.util.service.Order;

import java.util.Date;

public class QueryDepositOrder extends BaseOrder implements Order {

	private static final long	serialVersionUID	= -5644015434898215842L;

	/** 当前页 */
	protected int				currPage			= 1;
	
	/** 页大小 0 表示不分页 */
	protected int				pageSize			= 10;
	
	protected String depositId;
	
	/** 账户id */
	protected String			userId;
	
	protected String depositCode;
	
	/** 起始时间,业务发生时间 */
	protected Date				startTime;
	
	/** 终止时间,业务发生时间 */
	protected Date				endTime;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
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
	
	public String getDepositId() {
		return depositId;
	}
	
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getDepositCode() {
		return depositCode;
	}
	
	public void setDepositCode(String depositCode) {
		this.depositCode = depositCode;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public void check() {
	}
	

}
