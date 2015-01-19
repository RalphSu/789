package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class ActivityGiftCount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long tblBaseId = 0;
	/*
	 * 用户id
	 */
	private long userId = 0;
	/*
	 * 礼品名称
	 */
	private String giftName = null;
	private String giftCode;
	private String giftType;
	
	/**
	 * 礼品数
	 */
	private int giftCount;
	/*
	 * 礼品生效时间
	 */
	private Date startTime = null;
	/*
	 * 礼品失效时间
	 */
	private Date endTime = null;
	/*
	 * 状态 0:未开始,1:已开始,2:已结束
	 */
	private int status = 0;
	
	public long getTblBaseId() {
		return tblBaseId;
	}
	public void setTblBaseId(long tblBaseId) {
		this.tblBaseId = tblBaseId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	public String getGiftCode() {
		return giftCode;
	}
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	
	public String getGiftType() {
		return giftType;
	}
	public void setGiftType(String giftType) {
		this.giftType = giftType;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getGiftCount() {
		return giftCount;
	}
	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ActivityGiftCount [tblBaseId=" + tblBaseId + ", userId="
				+ userId + ", giftName=" + giftName + ", giftCode=" + giftCode
				+ ", giftType=" + giftType + ", giftCount=" + giftCount
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", status=" + status + "]";
	}
	
}
