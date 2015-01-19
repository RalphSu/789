package com.icebreak.p2p.ibatis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.ChargeProjectDao;
import com.icebreak.p2p.dataobject.ChargeProject;

public class ChargeProjectDaoImpl extends SqlMapClientDaoSupport implements
		ChargeProjectDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeProject> getByMethods(String... methods) {
		return (List<ChargeProject>)getSqlMapClientTemplate().queryForList("CHARGEPROJECT-GETBYMETHODS", Arrays.asList(methods));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeProject> getByTemplateId(long id) {
		return (List<ChargeProject>)getSqlMapClientTemplate().queryForList("CHARGEPROJECT-GETBYTEMPLATEID", id);
	}

	@Override
	public void addChargeProject(ChargeProject project) {
		getSqlMapClientTemplate().insert("CHARGEPROJECT-ADD", project);
	}

	@Override
	public int deleteById(long id) {
		return getSqlMapClientTemplate().delete("CHARGEPROJECT-DELETEBYID", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeProject> getByTemplateIdAndStatus(long templateId,
			int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateId", templateId);
		params.put("status", status);
		return (List<ChargeProject>)getSqlMapClientTemplate().queryForList("CHARGEPROJECT-GETBYTEMPLATEIDANDSTATUS", params);
	}
}
