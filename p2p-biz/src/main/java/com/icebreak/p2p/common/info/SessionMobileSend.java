package com.icebreak.p2p.common.info;

import java.io.Serializable;
import java.util.Date;

import com.icebreak.p2p.ws.enums.SmsBizType;

public class SessionMobileSend implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -1449591466737944826L;
	Date						lastSendDate;
	String						code;
	int							equalCount			= 0;
	String						moblie;
	SmsBizType					smsBizType;
	
	public Date getLastSendDate() {
		return lastSendDate;
	}
	
	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getEqualCount() {
		return equalCount;
	}
	
	public void setEqualCount(int equalCount) {
		this.equalCount = equalCount;
	}
	
	public String getMoblie() {
		return moblie;
	}
	
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	
	public SmsBizType getSmsBizType() {
		return smsBizType;
	}
	
	public void setSmsBizType(SmsBizType smsBizType) {
		this.smsBizType = smsBizType;
	}
	
}
