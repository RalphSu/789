package com.icebreak.p2p.util;

import com.icebreak.p2p.ws.order.SendMailOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SendInformation {
	
	public static SendMailOrder sendMail(String mail, String realName, String accessUrl,
											long templateId) throws Exception {
		Map<String, String> templateParameter = new HashMap<String, String>();
		String url = AppConstantsUtil.getHostHttpUrl();
		templateParameter.put("variable_1", url + "/resources/images/home/body-bg.jpg");
		templateParameter.put("variable_2", url + "/resources/images/home/header-bg.jpg");
		templateParameter.put("variable_3", url);
		templateParameter.put("variable_4", url + "/styles/images/common/logo.jpg");
		templateParameter.put("variable_5", realName);
		templateParameter.put("variable_6", url + accessUrl);
		templateParameter.put("variable_7", url + "/resources/images/common/email-btn.gif");
		SendMailOrder sendMailOrder = new SendMailOrder(Arrays.asList(mail), "业务邮箱", null,
			templateId, templateParameter, "yrd");
		return sendMailOrder;
	}
	
	public static SendMailOrder sendNotifyMail(String mail, String realName, String content,
												long templateId) throws Exception {
		Map<String, String> templateParameter = new HashMap<String, String>();
		//String url = PropertiesUtil.getProperty("yrd.host");
		String url = AppConstantsUtil.getHostHttpUrl();
		templateParameter.put("variable_1", url + "/resources/images/home/body-bg.jpg");
		templateParameter.put("variable_2", url + "/resources/images/home/header-bg.jpg");
		templateParameter.put("variable_3", url);
		templateParameter.put("variable_4", url + "/styles/images/common/logo.jpg");
		templateParameter.put("variable_5", realName);
		templateParameter.put("variable_6", content);
		SendMailOrder sendMailOrder = new SendMailOrder(Arrays.asList(mail), "业务邮箱", null,
			templateId, templateParameter, "yrd");
		return sendMailOrder;
	}
	
	public static SendMailOrder sendNotifyMail(String mail, String realName, String content,
												long templateId, String[] attachs) throws Exception {
		Map<String, String> templateParameter = new HashMap<String, String>();
		String url = AppConstantsUtil.getHostHttpUrl();
		templateParameter.put("variable_1", url + "/resources/images/home/body-bg.jpg");
		templateParameter.put("variable_2", url + "/resources/images/home/header-bg.jpg");
		templateParameter.put("variable_3", url);
		templateParameter.put("variable_4", url + "/styles/images/common/logo.jpg");
		templateParameter.put("variable_5", realName);
		templateParameter.put("variable_6", content);
		SendMailOrder sendMailOrder = new SendMailOrder(Arrays.asList(mail), "业务邮箱", null,
			templateId, templateParameter, "yrd");
		sendMailOrder.setAttachs(attachs);
		
		return sendMailOrder;
	}
	
	public static SendMailOrder sendBrokerMail(String mail, String realName, String brokerNo,
												long templateId) throws Exception {
		Map<String, String> templateParameter = new HashMap<String, String>();
		//String url = PropertiesUtil.getProperty("yrd.host");
		String url = AppConstantsUtil.getHostHttpUrl();
		templateParameter.put("variable_1", url + "/resources/images/home/body-bg.jpg");
		templateParameter.put("variable_2", url + "/resources/images/home/header-bg.jpg");
		templateParameter.put("variable_3", url);
		templateParameter.put("variable_4", url + "/styles/images/common/logo.jpg");
		templateParameter.put("variable_5", realName);
		templateParameter.put("variable_6", brokerNo);
		templateParameter.put("variable_7", url + "/anon/brokerOpenInvestor?NO=" + brokerNo);
		SendMailOrder sendMailOrder = new SendMailOrder(Arrays.asList(mail), "业务邮箱", null,
			templateId, templateParameter, "yrd");
		return sendMailOrder;
	}
	/**
	 * 发送借款请求邮件内容设置
	 * */
	public static SendMailOrder sendLoanRequestMail(String mail,long templateId,
			HttpServletRequest request,String type) throws Exception {
		Map<String, String> templateParameter = new HashMap<String, String>();
		if("1".equalsIgnoreCase(type)){
			templateParameter.put("idcard", request.getParameter("idcard"));
			templateParameter.put("merry", request.getParameter("merry"));
			templateParameter.put("name", request.getParameter("name"));
		}else{
			templateParameter.put("jianjie", request.getParameter("jianjie"));
		}
		templateParameter.put("phone", request.getParameter("phone"));
		templateParameter.put("companyName", request.getParameter("companyName"));
		templateParameter.put("companyID", request.getParameter("companyID"));
		templateParameter.put("companyAdds", request.getParameter("companyAdds"));
		templateParameter.put("busyness", request.getParameter("busyness"));
		templateParameter.put("loanAmout", request.getParameter("loanAmout"));
		templateParameter.put("loanTime", request.getParameter("loanTime"));
		templateParameter.put("rate", request.getParameter("rate"));
		templateParameter.put("paySourse", request.getParameter("paySourse"));
		templateParameter.put("purpose", request.getParameter("purpose"));
		templateParameter.put("danbao", request.getParameter("danbao"));
		templateParameter.put("loanRequestUrl", request.getParameter("loanRequestUrl"));
		SendMailOrder sendMailOrder = new SendMailOrder(Arrays.asList(mail), "业务邮箱", null,
		templateId, templateParameter, "yrd");
		return sendMailOrder;
}
}
