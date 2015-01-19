package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.EmailTemplateDAO;
import com.icebreak.p2p.dataobject.EmailTemplate;

public class EmailTemplateDAOImpl extends SqlMapClientDaoSupport implements EmailTemplateDAO{
	
	@Override
	public EmailTemplate getById(long id) {
		return (EmailTemplate)getSqlMapClientTemplate().queryForObject("EMAILTEMPLATE-GETBYTEMPLATEID", id);
	}

	@Override
	public long queryCountByCondition(Map<String, Object> conditions) {
		
		return (Long) getSqlMapClientTemplate().queryForObject("EMAILTEMPLATE-COUNTBYCONDITIONS", conditions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailTemplate> queryListByCondition(
			Map<String, Object> conditions) {

		return (List<EmailTemplate>)getSqlMapClientTemplate().queryForList("EMAILTEMPLATE-LISTBYCONDITIONS", conditions);
	}
	
	@Override
	public void insertEmailTemplate(EmailTemplate emailTemplate) {
		getSqlMapClientTemplate().insert("EMAILTEMPLATE-INSERT", emailTemplate);
		
	}

	@Override
	public long updateEmailTemplate(EmailTemplate emailTemplate) {
		return getSqlMapClientTemplate().update("EMAILTEMPLATE-UPDATE", emailTemplate);
	}
}
