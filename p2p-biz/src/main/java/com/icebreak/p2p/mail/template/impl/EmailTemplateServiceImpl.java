package com.icebreak.p2p.mail.template.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.BaseAutowiredService;
import com.icebreak.p2p.daointerface.EmailTemplateDAO;
import com.icebreak.p2p.dataobject.EmailTemplate;
import com.icebreak.p2p.mail.template.EmailTemplateService;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
@Service
public class EmailTemplateServiceImpl extends BaseAutowiredService implements EmailTemplateService {
	@Autowired
	EmailTemplateDAO emailTemplateDao;
	@Override
	public EmailTemplate getById(long id) {
		return emailTemplateDao.getById(id);
	}
	@Override
	public Page<EmailTemplate> getPageByConditions(PageParam pageParam,
			Map<String, Object> conditions) {
		conditions.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		conditions.put("pageSize", pageParam.getPageSize());
		long totalSize = emailTemplateDao.queryCountByCondition(conditions);
		List<EmailTemplate> result = emailTemplateDao.queryListByCondition(conditions);
		int start = PageParamUtil.startValue((int) totalSize,pageParam.getPageSize(), pageParam.getPageNo());
		return new Page<EmailTemplate>(start, totalSize,pageParam.getPageSize(), result);
	}
	@Override
	public void insertEmailTemplate(EmailTemplate emailTemplate) {
		emailTemplateDao.insertEmailTemplate(emailTemplate);
	}
	@Override
	public void updateEmailTemplate(EmailTemplate mailTemplate) {
		emailTemplateDao.updateEmailTemplate(mailTemplate);
	}

}
