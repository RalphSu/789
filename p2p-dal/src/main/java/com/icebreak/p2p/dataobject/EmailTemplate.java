package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class EmailTemplate implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private long id;
	/**
	 * 主题
	 */
	private String subject;
	/**
	 *内容 
	 */
	private String content;
	/**
	 *更新时间 
	 */
	private Date updateTime;
	/**
	*添加时间
	*/
	private Date addTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	@Override
	public String toString() {
		return "EmailTemplate [id=" + id + ", subject=" + subject
				+ ", content=" + content + ", updateTime=" + updateTime
				+ ", addTime=" + addTime + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
