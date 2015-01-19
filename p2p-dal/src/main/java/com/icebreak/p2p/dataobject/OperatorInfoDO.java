package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class OperatorInfoDO extends UserBaseInfoDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long operatorId;
	private String userBaseId;
	private long parentId;
	private short operatorType;
	private String remark;
	private String rem1;
	private Date addTime;
	public long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	public String getUserBaseId() {
		return userBaseId;
	}
	public void setUserBaseId(String userBaseId) {
		this.userBaseId = userBaseId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public short getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(short operatorType) {
		this.operatorType = operatorType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	@Override
	public String toString() {
		return "OperatorInfoDO [operatorId=" + operatorId + ", userBaseId="
				+ userBaseId + ", parentId=" + parentId + ", operatorType="
				+ operatorType + ", remark=" + remark + ", rem1=" + rem1
				+ ", addTime=" + addTime + "]";
	}
	
}
