package com.icebreak.p2p.common.info;

import java.io.Serializable;
import java.util.Date;

import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.util.lang.util.StringUtil;

public class MoblieSendData implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -9052899375070718812L;
	Date						lastSendDate;
	Date						createDate;
	String						sendMobileNumber;
	long						sendCount;
	SmsBizType					modulesEnum;
	String						userName;
	public final static long	MAX_SEND_TIMES		= 50;
	
	public MoblieSendData(String sendMobileNumber) {
		lastSendDate = new Date();
		sendCount = 1;
		this.sendMobileNumber = sendMobileNumber;
		
	}
	
	public MoblieSendData(String sendMobileNumber, SmsBizType modulesEnum) {
		lastSendDate = new Date();
		sendCount = 1;
		this.sendMobileNumber = sendMobileNumber;
		this.modulesEnum = modulesEnum;
	}
	
	public String getSendDataKey() {
		String key = sendMobileNumber + modulesEnum.code();
		return key;
	}
	
	public String getSendDataUserKey() {
		String key = null;
		if (StringUtil.isNotEmpty(userName))
			key = sendMobileNumber + modulesEnum.code() + userName;
		else
			key = getSendDataKey();
		return key;
	}
	
	public void addCount() {
		sendCount++;
	}
	
	public Date getLastSendDate() {
		return lastSendDate;
	}
	
	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}
	
	public String getSendMobileNumber() {
		return sendMobileNumber;
	}
	
	public void setSendMobileNumber(String sendMobileNumber) {
		this.sendMobileNumber = sendMobileNumber;
	}
	
	public long getSendCount() {
		return sendCount;
	}
	
	public void setSendCount(long sendCount) {
		this.sendCount = sendCount;
	}
	
	public SmsBizType getModulesEnum() {
		return modulesEnum;
	}
	
	public void setModulesEnum(SmsBizType modulesEnum) {
		this.modulesEnum = modulesEnum;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
