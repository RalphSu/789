package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class GiftUseRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private long				tblBaseId			= 0;
	/*
	 * 用户id
	 */
	private long				userId				= 0;
	/*
	 * 用户name
	 */
	private String				userName			= null;
	/*
	 * 礼品名称
	 */
	private String				giftName			= null;
	/**
	 * 对应的业务编号
	 */
	private String				bizNumber;
	
	/**
	 * 对应的外部订单号
	 */
	private String				outBizNo;
	/*
	 * 使用时间
	 */
	private Date				useTime				= null;
	/*
	 * 使用数量
	 */
	private int					useAmount			= 0;
	/**
	 * 备注
	 */
	private String				note;
	/**
	 * 使用状态
	 */
	private int					status;
	
	public long getTblBaseId() {
		return tblBaseId;
	}
	
	public void setTblBaseId(long tblBaseId) {
		this.tblBaseId = tblBaseId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public Date getUseTime() {
		return useTime;
	}
	
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	
	public int getUseAmount() {
		return useAmount;
	}
	
	public void setUseAmount(int useAmount) {
		this.useAmount = useAmount;
	}
	
	public String getBizNumber() {
		return bizNumber;
	}
	
	public void setBizNumber(String bizNumber) {
		this.bizNumber = bizNumber;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getOutBizNo() {
		return outBizNo;
	}
	
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GiftUseRecord [tblBaseId=");
		builder.append(tblBaseId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", giftName=");
		builder.append(giftName);
		builder.append(", bizNumber=");
		builder.append(bizNumber);
		builder.append(", outBizNo=");
		builder.append(outBizNo);
		builder.append(", useTime=");
		builder.append(useTime);
		builder.append(", useAmount=");
		builder.append(useAmount);
		builder.append(", note=");
		builder.append(note);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
