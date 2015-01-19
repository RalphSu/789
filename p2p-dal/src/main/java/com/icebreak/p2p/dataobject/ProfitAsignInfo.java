package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class ProfitAsignInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tblBaseId;
	/*接收者ID*/
	private long receiveId;
	/*贡献者ID*/
	private long distributionId;
	/*贡献额度*/
	private double distributionQuota;
	/*备注*/
	private String note;
	/*创建纪录时间*/
	private Date addTime;
	/*修改纪录时间*/
	private Date modifyTime;
	private String rem1; 
	public String getTblBaseId() {
		return tblBaseId;
	}
	public void setTblBaseId(String tblBaseId) {
		this.tblBaseId = tblBaseId;
	}
	public long getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}
	public long getDistributionId() {
		return distributionId;
	}
	public void setDistributionId(long distributionId) {
		this.distributionId = distributionId;
	}
	public double getDistributionQuota() {
		return distributionQuota;
	}
	public void setDistributionQuota(double distributionQuota) {
		this.distributionQuota = distributionQuota;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRem1() {
		return rem1;
	}
	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}
	
	
}
