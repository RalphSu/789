package com.icebreak.p2p.mail;

import java.util.Properties;

import com.icebreak.p2p.util.AppConstantsUtil;

import javax.mail.Session;

public class MailSenderInfo {
	/*
	private static String	mailServer		= CommonConfig.MAIL_MAILSERVER;
	private static String	mailServerport	= CommonConfig.MAIL_MAILSERVERPORT;
	private static String	senderName		= CommonConfig.MAIL_SENDERNAME;
	private static String	passWord		= CommonConfig.MAIL_PASSWORD;
	private static String	nickName		= CommonConfig.MAIL_NICKNAME;*/
	// 发送邮件的服务器的IP和端口 
	//	private String			mailServerHost	= mailServer;
	//	private String			mailServerPort	= mailServerport;
	// 邮件发送者的地址 
	//	private String			fromAddress		= senderName;
	// 邮件接收者的地址 
	private String toAddress;
	// 登录邮件发送服务器的用户名和密码 
	//	private String			userName		= senderName;
	//	private String			password		= passWord;
	// 是否需要身份验证 
	private boolean validate = true;
	// 邮件主题 
	private String subject;
	//发送者昵称
	//	private String			senderNickName	= nickName;
	// 邮件的文本内容 
	private String content;
	// 邮件附件的文件名 
	private String[] attachFiles;

    private static Session sendMailSession ;



    public static synchronized Session getMailSession(Properties pro,MyAuthenticator authenticator){
        if(sendMailSession == null){
            sendMailSession = Session.getInstance(pro,authenticator);
        }
        return sendMailSession;
    }

    public static void clearMailSession (){
        sendMailSession = null;
    }

	
	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", AppConstantsUtil.getMailServer());
		p.put("mail.smtp.port", AppConstantsUtil.getMailServerport());
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}
	
	public String getMailServerHost() {
		return AppConstantsUtil.getMailServer();
	}
	
	public String getMailServerPort() {
		return AppConstantsUtil.getMailServerport();
	}
	
	public boolean isValidate() {
		return validate;
	}
	
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
	public String[] getAttachFiles() {
		return attachFiles;
	}
	
	public void setAttachFiles(String[] attachFiles) {
		this.attachFiles = attachFiles;
	}
	
	public String getFromAddress() {
		return AppConstantsUtil.getMailSenderName();
	}
	
	public String getPassword() {
		return AppConstantsUtil.getMailPassword();
	}
	
	public String getToAddress() {
		return toAddress;
	}
	
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
	public String getUserName() {
		return AppConstantsUtil.getMailUserName();
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
	
	public void setContent(String textContent) {
		this.content = textContent;
	}
	
	public String getSenderNickName() {
		return AppConstantsUtil.getMailNickName();
	}
	
}
