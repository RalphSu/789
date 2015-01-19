package com.icebreak.p2p.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.DivisionRuleDao;
import com.icebreak.p2p.dataobject.DivisionRule;

public class DivisionRuleDaoImpl extends SqlMapClientDaoSupport implements DivisionRuleDao {

	@Override
	public void addRule(DivisionRule divisionRule) {
		getSqlMapClientTemplate().insert("DIVISIONRULE-ADDRULE", divisionRule);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DivisionRule> getByTemplateId(long templateId) {
		return (List<DivisionRule>)getSqlMapClientTemplate().queryForList("DIVISIONRULE-GETBYTEMPLATEID", templateId);
	}

	@Override
	public int deleteByTemplateId(long templateId) {
		return getSqlMapClientTemplate().delete("DIVISIONRULE-DELETEBYTEMPLATEID", templateId);
	}
}
