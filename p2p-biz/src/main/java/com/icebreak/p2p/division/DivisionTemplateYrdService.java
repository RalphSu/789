package com.icebreak.p2p.division;

import com.icebreak.p2p.dataobject.DivisionTemplateLoanDO;

public interface DivisionTemplateYrdService {
	//插入模板
	long insertDivisionTemplateLoan(long[] templateIds);
	//根据baseId获取模板套装
	DivisionTemplateLoanDO getByBaseId(long baseId);
	//根据templateId获取已经存在的id
	long getBaseIdByTemplateIds(long[] templateIds); 
}
