package com.icebreak.p2p.user;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.OperatorInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.ws.enums.OperatorInfoEnum;

public interface IOperatorInfoService {
	long addOperatorInfo(OperatorInfoDO info);
	
	List<OperatorInfoDO> queryOperatorsByProperties(Map<String, Object> conditions);
	
	Page<OperatorInfoDO> queryOperatorPage(QueryConditions queryConditions, PageParam pageParam);
	
	//更新
	OperatorInfoEnum updateOperatorInfo(OperatorInfoDO operatorInfo);
	
	/**
	 * 两个操作人员是否来自同一担保机构
	 * @param userBaseId1
	 * @param userBaseId2
	 * @return true :来自同一担保机构
	 */
	boolean isFromSameOrgan(String userBaseId1, String userBaseId2);
	
}
