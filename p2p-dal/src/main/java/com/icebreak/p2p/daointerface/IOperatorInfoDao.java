package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.OperatorInfoDO;

public interface IOperatorInfoDao {
	
	long addOperatorInfo(OperatorInfoDO info);
	
	List<OperatorInfoDO> queryOperatorsByProperties(Map<String, Object> conditions);
	
	long queryOperatorsByPropertiesCount(Map<String, Object> conditions);
	
	//更新
	int updateOperatorInfo(Map<String, Object> conditions);
	
	boolean isFromSameOrgan(Map<String, Object> conditions);
	
}
