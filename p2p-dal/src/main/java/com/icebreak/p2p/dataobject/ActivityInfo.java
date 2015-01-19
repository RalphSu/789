package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class ActivityInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long tblBaseId = 0;
	/*
	 * 活动名称
	 */
	private String activityName = null;
	/**
	 * 派送礼品代号
	 */
	private String sendGiftCode ;
	/*
	 * 活动开始时间
	 */
	private Date startTime = null;
	/*
	 * 活动结束时间
	 */
	private Date endTime = null;
	/*
	 * 活动状态
	 */
	private int status = 0;
	/*
	 * 活动描述
	 */
	private String description = null;	
	/*
	 * 属性对
	 */
	private String properties = null;
	private String rem1;
	
	public long getTblBaseId() {
		return tblBaseId;
	}
	public void setTblBaseId(long tblBaseId) {
		this.tblBaseId = tblBaseId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getSendGiftCode() {
		return sendGiftCode;
	}
	public void setSendGiftCode(String sendGiftCode) {
		this.sendGiftCode = sendGiftCode;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRem1() {
		return rem1;
	}
	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}	
}
