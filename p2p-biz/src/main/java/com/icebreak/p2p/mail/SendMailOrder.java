package com.icebreak.p2p.mail;

import java.io.Serializable;

public class SendMailOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 邮件主题 
	private String subject; 
	// 邮件的文本内容 
	private String content; 
	// 邮件接收者的地址 
	private String toAddress;
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
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	@Override
	public String toString() {
		return "SendMailOrder [subject=" + subject + ", content=" + content
				+ ", toAddress=" + toAddress + "]";
	} 
	
}
