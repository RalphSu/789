package com.icebreak.p2p.activity;

import java.io.Serializable;
import java.util.Date;

public class QueryActivityOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long tblBaseId;
	private long userId;
	private long activityId;
	private String activityName;
	private String giftName;
	private String userName;
	private String sendGiftCode;
	private int status;
	private Date startTime;
	private Date endTime;
	private String bizNumber;
	private String type;
	private String descripton;
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
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSendGiftCode() {
		return sendGiftCode;
	}
	public void setSendGiftCode(String sendGiftCode) {
		this.sendGiftCode = sendGiftCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getBizNumber() {
		return bizNumber;
	}
	public void setBizNumber(String bizNumber) {
		this.bizNumber = bizNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescripton() {
		return descripton;
	}
	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}
}
