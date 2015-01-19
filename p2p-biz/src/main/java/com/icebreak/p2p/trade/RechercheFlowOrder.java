package com.icebreak.p2p.trade;

import java.util.ArrayList;
import java.util.List;

public class RechercheFlowOrder {
	private long userId;
	private String startTime;
	private String endTime;
	/**
	 * 支付类型:DEDUCT代扣，EBANK网银，WITHDRAW提现
	 */
	private String payType;
	/**
	 * 1成功，0失败，2-处理中
	 */
	private List<Integer> status = new ArrayList<Integer>();
	private String bankCardNo;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public List<Integer> getStatus() {
		return status;
	}
	public void setStatus(List<Integer> status) {
		this.status = status;
	}

}
