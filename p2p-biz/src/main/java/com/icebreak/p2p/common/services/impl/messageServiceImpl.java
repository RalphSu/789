package com.icebreak.p2p.common.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.OpenApiBaseService;
import com.icebreak.p2p.common.services.MessageService;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.mail.IMailSendService;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.ApplicationConstant;
import com.icebreak.p2p.util.SendInformation;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.util.lang.util.StringUtil;

@Service
public class messageServiceImpl extends OpenApiBaseService implements MessageService {
	
	@Autowired
	protected IMailSendService mailService;
	private String host;
	private String urlHostEmail;
	private String urlHostSms;
	/**
	 * 通知类型
	 */
	private String notifyType;
	
	void initPrivateValue() {
		if (StringUtil.isEmpty(host)) {
			synchronized (this) {
				if (StringUtil.isEmpty(host)) {
					String httpHost = this.sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_RETURN_URL_KEY);
					host = this.sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_HOST);
					urlHostEmail = "<a href='" + httpHost + "'>" + host + "</a>";
					urlHostSms = host;
					notifyType = this.sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_NOTIFY_TYPE);
				}
			}
		}
	}
	
	@Override
	public void notifyUser(UserBaseInfoDO notifiedUser, String content) {
		initPrivateValue();
		content = content.replace("你", "您");
		String mobile = notifiedUser.getMobile();
		String smsContent = content.replace("hostLink", urlHostSms);
		smsContent += "客服电话：" + AppConstantsUtil.getCustomerServicePhone();
		String emailContent = content.replace("hostLink", urlHostEmail);
		if (YrdConstants.MessageNotifyConstants.NOTIFY_TYPE_SMS.equals(notifyType)) {
			content = YrdConstants.MessageNotifyConstants.GREETING_CONTENT
						+ notifiedUser.getRealName() + "," + smsContent;
			try {
				this.smsService.sendSMS(mobile, content, this.getOpenApiContext());
			} catch (Exception e) {
				logger.error("error:--发送短信异常", e);
			}
		} else if (YrdConstants.MessageNotifyConstants.NOTIFY_TYPE_EMAIL.equals(notifyType)) {
//			try {
//				mailService.sendMail(SendInformation.sendNotifyMail(notifiedUser.getMail(),notifiedUser.getRealName(), emailContent, 23L), "HTML");
//			} catch (Exception e) {
//				logger.error("error:--发送邮件异常", e);
//			}
			content = YrdConstants.MessageNotifyConstants.GREETING_CONTENT
					+ notifiedUser.getRealName() + "," + smsContent;
			try {
				this.smsService.sendSMS(mobile, content, this.getOpenApiContext());
			} catch (Exception e) {
				logger.error("error:--发送短信异常", e);
			}
		} else {
			try {
				mailService.sendMail(
					SendInformation.sendNotifyMail(notifiedUser.getMail(),
						notifiedUser.getRealName(), emailContent, 23L), "HTML");
			} catch (Exception e) {
				logger.error("error:--发送邮件异常", e);
				
			}
			content = YrdConstants.MessageNotifyConstants.GREETING_CONTENT
						+ notifiedUser.getRealName() + "," + smsContent;
			try {
				this.smsService.sendSMS(mobile, content, this.getOpenApiContext());
			} catch (Exception e) {
				logger.error("error:---发送短信异常", e);
			}
		}
	}
	
	@Override
	public void notifyUserByType(UserBaseInfoDO notifiedUser, String content, String notify_Type) {
		initPrivateValue();
		content = content.replace("你", "您");
		String mobile = notifiedUser.getMobile();
		String smsContent = content.replace("hostLink", urlHostSms);
		smsContent += "客服电话：" + AppConstantsUtil.getCustomerServicePhone();
		String emailContent = content.replace("hostLink", urlHostEmail);
		if (YrdConstants.MessageNotifyConstants.NOTIFY_TYPE_SMS.equals(notify_Type)) {
			content = YrdConstants.MessageNotifyConstants.GREETING_CONTENT
						+ notifiedUser.getRealName() + "," + smsContent;
			try {
				this.smsService.sendSMS(mobile, content, this.getOpenApiContext());
			} catch (Exception e) {
				logger.error("error:---发送短信异常", e);
			}
		} else if (YrdConstants.MessageNotifyConstants.NOTIFY_TYPE_EMAIL.equals(notify_Type)) {
			try {
				mailService.sendMail(
					SendInformation.sendNotifyMail(notifiedUser.getMail(),
						notifiedUser.getRealName(), emailContent, 23L), "HTML");
			} catch (Exception e) {
				logger.error("error:--发送邮件异常", e);
			}
		} else {
			try {
				mailService.sendMail(
					SendInformation.sendNotifyMail(notifiedUser.getMail(),
						notifiedUser.getRealName(), emailContent, 23L), "HTML");
			} catch (Exception e) {
				logger.error("error:--发送邮件异常", e);
			}
			content = YrdConstants.MessageNotifyConstants.GREETING_CONTENT
						+ notifiedUser.getRealName() + "," + smsContent;
			try {
				smsService.sendSMS(mobile, content, this.getOpenApiContext());
			} catch (Exception e) {
				logger.error("error:--发送短信异常", e);
			}
		}
	}
	
	@Override
	public void notifyUserByEmail(UserBaseInfoDO notifiedUser, String content, String attachs[]) {
		initPrivateValue();
		content = content.replace("你", "您");
		String emailContent = content.replace("hostLink", urlHostEmail);
		
		try {
			mailService.sendMail(SendInformation.sendNotifyMail(notifiedUser.getMail(),
				notifiedUser.getRealName(), emailContent, 23L, attachs), "HTML");
		} catch (Exception e) {
			logger.error("error:--发送邮件异常", e);
		}
	}

	@Override
	public void notifyUsersBySms(List<UserBaseInfoDO> notifyUsers, String content) {
		initPrivateValue();
		String smsContent = content.replace("hostLink", urlHostSms);
		smsContent += "客服电话：" + AppConstantsUtil.getCustomerServicePhone();
		for(UserBaseInfoDO user : notifyUsers){
			try{
//				logger.info(user.getMobile()+smsContent);
				this.smsService.sendSMS(user.getMobile(), smsContent, this.getOpenApiContext());
			}catch(Exception e){
				logger.error("error:--短信发送异常：" + user.getRealName() + "--" + user.getMobile(), e);
			}
		}
	}
	
}
