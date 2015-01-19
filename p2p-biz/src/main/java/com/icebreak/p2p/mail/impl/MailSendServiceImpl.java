package com.icebreak.p2p.mail.impl;

import com.icebreak.p2p.base.OpenApiBaseContextServiceBase;
import com.icebreak.p2p.daointerface.EmailTemplateDAO;
import com.icebreak.p2p.dataobject.EmailTemplate;
import com.icebreak.p2p.mail.IMailSendService;
import com.icebreak.p2p.mail.MailSender;
import com.icebreak.p2p.mail.MailSenderInfo;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.ws.order.SendMailOrder;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service
public class MailSendServiceImpl extends OpenApiBaseContextServiceBase implements IMailSendService {
	@Autowired
	private EmailTemplateDAO emailTemplateDao;
	private MailSender sms = new MailSender();
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean sendTextMail(SendMailOrder mailOrder) {
		logger.info("发送文本邮件");
		String customerServiceTelNo = AppConstantsUtil.getCustomerServicePhone();
		MailSenderInfo mailInfo = new MailSenderInfo();
		EmailTemplate template = emailTemplateDao.getById(mailOrder.getTemplateId());
		if (StringUtil.isNotBlank(template.getSubject())) {
			mailInfo.setSubject(template.getSubject());
		} else {
			mailInfo.setSubject(mailOrder.getSubject());
		}
		mailInfo.setToAddress(mailOrder.getTo().get(0));
		String content = template.getContent();
		if (content == null || content.length() < 1) {
			return false;
		}
		Map<String, String> prames = mailOrder.getTemplateParameter();
		Set keySet = prames.keySet();
		Iterator itr = keySet.iterator();
		while (itr.hasNext()) {
			Object key = itr.next();
			String value = prames.get(key);
			content = content.replace("$!{" + key + "}", value);
		}
		mailInfo.setContent(content);
		
		String attachs[] = mailOrder.getAttachs();
		if (attachs != null && attachs.length > 0) {
			mailInfo.setAttachFiles(attachs);
		}
		return getMailSenderInstance().sendTextMail(mailInfo);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean sendHtmlMail(SendMailOrder mailOrder) {
		//发送html格式
		logger.info("发送html格式邮件    templateId:" + mailOrder.getTemplateId());
		MailSenderInfo mailInfo = new MailSenderInfo();
		EmailTemplate template = emailTemplateDao.getById(mailOrder.getTemplateId());
		if (StringUtil.isNotBlank(template.getSubject())) {
			mailInfo.setSubject(template.getSubject());
		} else {
			mailInfo.setSubject(mailOrder.getSubject());
		}
		mailInfo.setToAddress(mailOrder.getTo().get(0));
		String content = template.getContent();
		if (content == null || content.length() < 1) {
			return false;
		}
		String customerServiceTelNo = AppConstantsUtil.getCustomerServicePhone();
		content = content.replace("023-65101000", customerServiceTelNo);
		Map<String, String> prames = mailOrder.getTemplateParameter();
		Set keySet = prames.keySet();
		Iterator itr = keySet.iterator();
		while (itr.hasNext()) {
			Object key = itr.next();
			String value = prames.get(key);
			if(StringUtil.isNotBlank(value)) {
				content = content.replace("$!{" + key + "}", value);
			}
		}
		mailInfo.setContent(content);
		String attachs[] = mailOrder.getAttachs();
		if (attachs != null && attachs.length > 0) {
			mailInfo.setAttachFiles(attachs);
		}
		return getMailSenderInstance().sendHtmlMail(mailInfo);
	}
	
	public MailSender getMailSenderInstance() {
		if (sms != null) {
			sms = new MailSender();
		}
		return sms;
	}
	
	@Override
	public boolean sendMail(SendMailOrder mailOrder, String mailType) {
		if ("HTML".equalsIgnoreCase(mailType)) {
			return sendHtmlMail(mailOrder);
		} else {
			return sendTextMail(mailOrder);
		}
	}
	
	public EmailTemplateDAO getEmailTemplateDao() {
		return emailTemplateDao;
	}
	
	public void setEmailTemplateDao(EmailTemplateDAO emailTemplateDao) {
		this.emailTemplateDao = emailTemplateDao;
	}
	
	public static void main(String[] args) {
		
	}
	
	@Override
	public boolean sendBrokerMail(HttpServletRequest request, SendMailOrder mailOrder) {
		//发送html格式
		logger.info("发送html格式邮件");
		MailSenderInfo mailInfo = new MailSenderInfo();
		EmailTemplate template = emailTemplateDao.getById(mailOrder.getTemplateId());
		if (StringUtil.isNotBlank(template.getSubject())) {
			mailInfo.setSubject(template.getSubject());
		} else {
			mailInfo.setSubject(mailOrder.getSubject());
		}
		mailInfo.setToAddress(mailOrder.getTo().get(0));
		String content = template.getContent();
		if (content == null || content.length() < 1) {
			return false;
		}
		String customerServiceTelNo = AppConstantsUtil.getCustomerServicePhone();
		
		Map<String, String> prames = mailOrder.getTemplateParameter();
		Set<String> keySet = prames.keySet();
		Iterator<String> itr = keySet.iterator();
		while (itr.hasNext()) {
			Object key = itr.next();
			String value = prames.get(key);
			content = content.replace("$!{" + key + "}", value);
		}
		mailInfo.setContent(content);
		String[] attachs = new String[1];
		String servletPath = request.getServletContext().getRealPath("/");
		attachs[0] = servletPath + "/WEB-INF/doc/ppt/销售策略.ppt";
		mailInfo.setAttachFiles(attachs);
		return getMailSenderInstance().sendHtmlMail(mailInfo);
	}
	/**
	 * 发送借款请求邮件
	 * */
	public boolean sendLoanRequestMail(SendMailOrder mailOrder,String type,HttpSession session) {
		//发送html格式
		logger.info("发送html格式邮件给客服:企业或个人的借款请求");
		MailSenderInfo mailInfo = new MailSenderInfo();
		EmailTemplate template = emailTemplateDao.getById(mailOrder.getTemplateId());
		if (StringUtil.isNotBlank(template.getSubject())) {
			mailInfo.setSubject(template.getSubject());
		} else {
			mailInfo.setSubject(mailOrder.getSubject());
		}
		mailInfo.setToAddress(mailOrder.getTo().get(0));
		String content = template.getContent();
		if (content == null || content.length() < 1) {
			return false;
		}
		String customerServiceTelNo = AppConstantsUtil.getCustomerServicePhone();
		
		Map<String, String> prames = mailOrder.getTemplateParameter();
		Set<String> keySet = prames.keySet();
		Iterator<String> itr = keySet.iterator();
		while (itr.hasNext()) {
			Object key = itr.next();
			String value = prames.get(key);
			if(value == null){
				value = "";
			}
			content = content.replace("$!{" + key + "}", value);
		}
		mailInfo.setContent(content);
		return getMailSenderInstance().sendHtmlMail(mailInfo);
	}
}
