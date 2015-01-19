package com.icebreak.p2p.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.DivisonRuleRoleDao;
import com.icebreak.p2p.dataobject.DivsionRuleRole;

public class DivisonRuleRoleDaoImpl extends SqlMapClientDaoSupport
	implements DivisonRuleRoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DivsionRuleRole> getLstRulRole(String name) {

		return (List<DivsionRuleRole>)getSqlMapClientTemplate().queryForList("DIVSION_RULE_ROLE", name);
	}

	



}
