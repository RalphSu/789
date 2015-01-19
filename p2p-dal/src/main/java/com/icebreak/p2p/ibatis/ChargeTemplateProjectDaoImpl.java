package com.icebreak.p2p.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.ChargeTemplateProjectDao;
import com.icebreak.p2p.dataobject.ChargeTemplateProject;

public class ChargeTemplateProjectDaoImpl extends SqlMapClientDaoSupport
		implements ChargeTemplateProjectDao {

	@Override
	public void addChargeTemplateProject(ChargeTemplateProject chargeTemplateProject) {
        getSqlMapClientTemplate().insert("CHARGETEMPLATEPROJECT-ADD", chargeTemplateProject);
	}

	@Override
	public int deleteByTemplateId(long id) {
		return getSqlMapClientTemplate().delete("CHARGETEMPLATEPROJECT-DELETEBYTEMPLATEID", id);
	}

	@Override
	public int deleteByProjectId(long id) {
		return getSqlMapClientTemplate().delete("CHARGETEMPLATEPROJECT-DELETEBYPROJECTID", id);
	}

}
