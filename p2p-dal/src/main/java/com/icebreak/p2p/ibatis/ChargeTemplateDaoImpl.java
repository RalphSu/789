package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.ChargeTemplateDao;
import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.dataobject.ChargeTemplate;

public class ChargeTemplateDaoImpl extends SqlMapClientDaoSupport implements
		ChargeTemplateDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public void addChargeTemplate(ChargeTemplate template) {
		getSqlMapClientTemplate().insert("CHARGETEMPLATE-ADD", template);
		template.setId(identityObtainer.getPrimaryKey());
	}

	@Override
	public ChargeTemplate getById(long id) {
		return (ChargeTemplate)getSqlMapClientTemplate().queryForObject("CHARGETEMPLATE-GETBYTEMPLATEID", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeTemplate> getByProperties(Map<String, Object> params) {
		return (List<ChargeTemplate>)getSqlMapClientTemplate().queryForList("CHARGETEMPLATE-GETBYPROPERTIES", params);
	}

	@Override
	public long getCountByProperties(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("CHARGETEMPLATE-GETBYPROPERTIESCOUNT", params);
	}

	@Override
	public int modify(ChargeTemplate template) {
		return getSqlMapClientTemplate().update("CHARGETEMPLATE-MODIFY", template);
	}

	@Override
	public ChargeTemplate getChargeTemplateByUserId(long userId, int roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("roleId", roleId);
		ChargeTemplate template = (ChargeTemplate)getSqlMapClientTemplate().queryForObject("CHARGETEMPLATE-GETBYUSERID-PRIVATE", params);
		if(template == null){
			template = (ChargeTemplate)getSqlMapClientTemplate().queryForObject("CHARGETEMPLATE-GETBYUSERID-PUBLIC", params);
		}
		return template;
	}
}
