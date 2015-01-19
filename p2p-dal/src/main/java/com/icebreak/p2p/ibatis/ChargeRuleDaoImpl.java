package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.ChargeRuleDao;
import com.icebreak.p2p.dataobject.ChargeRule;

public class ChargeRuleDaoImpl extends SqlMapClientDaoSupport implements
		ChargeRuleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeRule> getByTemplateId(long id) {
		return (List<ChargeRule>)getSqlMapClientTemplate().queryForList("CHARGERULE-GETBYTEMPLATEID", id);
	}

	@Override
	public int deleteByTemplateId(long id) {
		return getSqlMapClientTemplate().delete("CHARGERULE-DELETEBYTEMPLATEID", id);
	}

	@Override
	public void addChargeRule(ChargeRule rule) {
		getSqlMapClientTemplate().insert("CHARGERULE-ADDCHARGERULE", rule);
	}

	@Override
	public ChargeRule getByAmount(long templateId, long amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateId", templateId);
		params.put("amount", amount);
		return (ChargeRule)getSqlMapClientTemplate().queryForObject("CHARGERULE-GETBYAMOUNT", params);
	}
}
