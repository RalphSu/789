package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.ProjectStatisticsInfo;

public interface ProjectStatisticsDao {
	/**
	 * 查询项目数量
	 * @return
	 */
	public long getCountsProjectQuantityStatus(Map<String,Object> condition);
	/**根据年份统计
	 * @param condition
	 * @return
	 */
	public List<ProjectStatisticsInfo> queryListProject(Map<String,Object> condition);
	/**
	 * 查询总数量
	 * @param condition
	 * @return
	 */
	public long queryCountsByCondition(Map<String,Object> condition);

}
