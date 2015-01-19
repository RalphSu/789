package com.icebreak.p2p.mail.template;

import java.util.Map;

import com.icebreak.p2p.dataobject.EmailTemplate;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;

public interface EmailTemplateService {
	public EmailTemplate getById(long id);

	public Page<EmailTemplate> getPageByConditions(PageParam pageParam,
			Map<String, Object> conditions);

	public void insertEmailTemplate(EmailTemplate emailTemplate);

	public void updateEmailTemplate(EmailTemplate mailTemplate);
}
