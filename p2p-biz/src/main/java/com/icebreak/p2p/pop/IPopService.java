package com.icebreak.p2p.pop;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.PopInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;

public interface IPopService {

	void addNotice(PopInfoDO info);

	Page<PopInfoDO> getPageByConditions(PageParam pageParam,
			Map<String, Object> conditions);

	PopInfoDO getByPopId(long popId);

	void updateNotice(PopInfoDO info);

	List<PopInfoDO> getListByConditions(Map<String, Object> conditions);
	
	void increaseHit(long popId);

}
