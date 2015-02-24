package com.oms.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="")
public class Message {
	@XmlElement(name="ToUserName", required=true)
	String ToUserName;
	@XmlElement(name="FromUserName", required=true)
	String FromUserName;
	@XmlElement(name="CreateTime", type=Integer.class, required=true)
	Integer CreateTime;
	@XmlElement(name="MsgType", required=true)
	String MsgType;
	@XmlElement(name="MsgId", type=Long.class, required=false)
	Long MsgId;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Integer getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Integer createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public Long getMsgId() {
		return MsgId;
	}
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}
}
