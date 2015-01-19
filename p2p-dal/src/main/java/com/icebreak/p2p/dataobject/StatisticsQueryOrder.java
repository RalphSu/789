package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.List;

public class StatisticsQueryOrder implements Serializable{
	/**
	 * 统计查询条件
	 */
	private static final long serialVersionUID = 1L;
	private String queryType;
	private String startDate;
	private String endDate;
	private String status;
	private String bankName;
	private String userType;
	private List<String> payType;
	
	private StatisticsQueryOrder() {
		super();

	}
	
	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	

	public List<String> getPayType() {
		return payType;
	}

	public void setPayType(List<String> payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return "StatisticsQueryOrder [queryType=" + queryType + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
	
}
