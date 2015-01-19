package com.icebreak.p2p.mail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.icebreak.p2p.ws.order.SendMailOrder;

public interface IMailSendService {
	public boolean sendTextMail(SendMailOrder mailOrder);
	
	public boolean sendHtmlMail(SendMailOrder mailOrder);
	
	public boolean sendMail(SendMailOrder mailOrder, String mailType);
	
	public boolean sendLoanRequestMail(SendMailOrder mailOrder,String type,HttpSession session);
	
	public boolean sendBrokerMail(HttpServletRequest request, SendMailOrder sendBrokerMail);
}
