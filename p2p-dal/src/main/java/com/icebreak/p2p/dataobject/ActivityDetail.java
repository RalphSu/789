package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class ActivityDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long tblBaseId = 0;
	/*
	 * 活动id
	 */
	private long activityId = 0;
	
	private String activityName;
	
	/*
	 * 用户id
	 */
	private long userId = 0;
	private String userName =null;
	private String realName =null;
	/*
	 * 是否是推荐人 0-推荐人 1-被推荐人
	 */
	private int type = 1;
	/*
	 * 关联用户id
	 */
	private long relationId = 0;
	private String relatedUserName =null;
	private String relatedRealName =null;
	/**
	 * 礼品名称
	 */
	private String giftName;
	private String giftCode;
	/*
	 * 免费体现次数（礼品数量）
	 */
	private int giftNumber = 0;
	private Date addTime;
	private Date finishTime;
	/*
	 * 状态  0:未激活,1:未认证,2:已生效
	 */
	private int status = 0;
	private String  rem1 = null;
	
	
	public long getTblBaseId() {
		return tblBaseId;
	}
	public void setTblBaseId(long tblBaseId) {
		this.tblBaseId = tblBaseId;
	}
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getRelationId() {
		return relationId;
	}
	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public int getGiftNumber() {
		return giftNumber;
	}
	public void setGiftNumber(int giftNumber) {
		this.giftNumber = giftNumber;
	}

	public String getRem1() {
		return rem1;
	}
	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	
	public String getRelatedUserName() {
		return relatedUserName;
	}
	public void setRelatedUserName(String relatedUserName) {
		this.relatedUserName = relatedUserName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRelatedRealName() {
		return relatedRealName;
	}
	public void setRelatedRealName(String relatedRealName) {
		this.relatedRealName = relatedRealName;
	}
	@Override
	public String toString() {
		return "ActivityDetail [tblBaseId=" + tblBaseId + ", activityId="
				+ activityId + ", activityName=" + activityName + ", userId="
				+ userId + ", userName=" + userName + ", realName=" + realName
				+ ", type=" + type + ", relationId=" + relationId
				+ ", relatedUserName=" + relatedUserName + ", relatedRealName="
				+ relatedRealName + ", giftName=" + giftName + ", giftCode="
				+ giftCode + ", giftNumber=" + giftNumber + ", addTime="
				+ addTime + ", finishTime=" + finishTime + ", status=" + status
				+ ", rem1=" + rem1 + "]";
	}
}
