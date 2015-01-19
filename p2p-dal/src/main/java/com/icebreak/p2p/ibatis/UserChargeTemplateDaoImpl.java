package com.icebreak.p2p.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.UserChargeTemplateDao;
import com.icebreak.p2p.dataobject.UserChargeTemplate;

public class UserChargeTemplateDaoImpl extends SqlMapClientDaoSupport implements
		UserChargeTemplateDao {

	@Override
	public void addUserChargeTemplate(UserChargeTemplate userChargeTemplate) {
       getSqlMapClientTemplate().insert("USERCHARGETEMPLATE-ADD", userChargeTemplate);
	}

	@Override
	public int deleteByUserId(long id) {
		return getSqlMapClientTemplate().delete("USERCHARGETEMPLATE-DELETEBYUSERID", id);
	}

	@Override
	public int deleteByTemplateId(long id) {
		return getSqlMapClientTemplate().delete("USERCHARGETEMPLATE-DELETEBYTEMPLATEID", id);
	}

}
