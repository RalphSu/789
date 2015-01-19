package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.DivisionTemplateLoanDO;

public interface DivisionTemplateLoanDao {
	//插入对象
	long insertDivisionTemplateLoan(
			DivisionTemplateLoanDO divisionTemplateLoanDO);
	/**
	 * 获取对象
	 * @param baseId
	 * @return
	 */
	DivisionTemplateLoanDO getByBaseId(long baseId);
	/**
	 * 获取baseID
	 * @param templateIds
	 * @return
	 */
	long getBaseIdByTemplateIds(long[] templateIds);

}
