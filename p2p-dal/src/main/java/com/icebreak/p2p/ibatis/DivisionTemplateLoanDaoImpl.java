package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.DivisionTemplateLoanDao;
import com.icebreak.p2p.dataobject.DivisionTemplateLoanDO;

public class DivisionTemplateLoanDaoImpl extends SqlMapClientDaoSupport implements DivisionTemplateLoanDao{

	@Override
	public long insertDivisionTemplateLoan(
			DivisionTemplateLoanDO divisionTemplateLoanDO) {
		
		return  (Long) getSqlMapClientTemplate().insert("DIVISIONTEMPLATELOAN-ADD", divisionTemplateLoanDO);
	}

	@Override
	public DivisionTemplateLoanDO getByBaseId(long baseId) {

		return (DivisionTemplateLoanDO) getSqlMapClientTemplate().queryForObject("DIVISIONTEMPLATELOAN-GETBYBASEID", baseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getBaseIdByTemplateIds(long[] templateIds) {
		Map<String, Long> map = new HashMap<String, Long>(); 
		if(templateIds.length == 2){
			map.put("investTemplateId", templateIds[0]);
			map.put("repayTemplateId", templateIds[1]);
		}
		List<DivisionTemplateLoanDO> divisionTemplateLoans=  getSqlMapClientTemplate().queryForList("DIVISIONTEMPLATELOAN-GETBASEIDBYPARAMS", map);
		if(divisionTemplateLoans != null && divisionTemplateLoans.size() > 0){
			return divisionTemplateLoans.get(0).getBaseId();
		}
		return 0;
	}
}
