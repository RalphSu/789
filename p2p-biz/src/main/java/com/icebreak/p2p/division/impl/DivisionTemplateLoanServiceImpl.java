package com.icebreak.p2p.division.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.daointerface.DivisionTemplateLoanDao;
import com.icebreak.p2p.dataobject.DivisionTemplateLoanDO;
import com.icebreak.p2p.division.DivisionTemplateYrdService;
@Service
public class DivisionTemplateLoanServiceImpl implements DivisionTemplateYrdService{
	@Autowired
	private DivisionTemplateLoanDao divisionTemplateLoanDao;

	public void setDivisionTemplateLoanDao(
			DivisionTemplateLoanDao divisionTemplateLoanDao) {
		this.divisionTemplateLoanDao = divisionTemplateLoanDao;
	}

	@Override
	public long insertDivisionTemplateLoan(long[] templateIds) {

		if(templateIds == null || templateIds.length == 0 ){
			return 0;
		}
		return divisionTemplateLoanDao.insertDivisionTemplateLoan(new DivisionTemplateLoanDO(templateIds[0],templateIds[1]));
	}

	@Override
	public DivisionTemplateLoanDO getByBaseId(long baseId) {

		return divisionTemplateLoanDao.getByBaseId(baseId);
	}

	@Override
	public long getBaseIdByTemplateIds(long[] templateIds) {
		if(templateIds == null || templateIds.length == 0 ){
			return 0;
		}
		return divisionTemplateLoanDao.getBaseIdByTemplateIds(templateIds);
	} 
	
}
