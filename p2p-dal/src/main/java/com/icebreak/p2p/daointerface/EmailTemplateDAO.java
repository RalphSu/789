package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.EmailTemplate;

public interface EmailTemplateDAO {
	public EmailTemplate getById(long id);
	//统计总数
	public long queryCountByCondition(Map<String, Object> conditions);
	//查询列表
	public List<EmailTemplate> queryListByCondition(
			Map<String, Object> conditions);
	//新增
	public void insertEmailTemplate(EmailTemplate emailTemplate);
	//更新
	public long updateEmailTemplate(EmailTemplate emailTemplate);
}
