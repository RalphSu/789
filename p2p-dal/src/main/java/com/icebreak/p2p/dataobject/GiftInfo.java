package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class GiftInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long tblBaseId;
	private String giftName;
	private String giftCode;
	private String giftType;
	private Date startTime;
	private Date endTime;
	private int giftStatus;
	private String description;
	
	public long getTblBaseId() {
		return tblBaseId;
	}
	public void setTblBaseId(long tblBaseId) {
		this.tblBaseId = tblBaseId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGiftStatus() {
		return giftStatus;
	}
	public void setGiftStatus(int giftStatus) {
		this.giftStatus = giftStatus;
	}
}
