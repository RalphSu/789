package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.PopInfoDO;

public interface PopInfoDao {

	void addNotice(PopInfoDO info);

	long queryCountByCondition(Map<String, Object> conditions);

	List<PopInfoDO> queryListByCondition(Map<String, Object> conditions);

	PopInfoDO getByPopId(long popId);

	void updateNotice(PopInfoDO info);

}
