package com.icebreak.p2p.statistics;

import java.util.List;

import com.icebreak.p2p.dataobject.ProjectStatisticsInfo;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;

public interface ProjectStatisticsService {
	
	/**
	 * 根据状态、时间等查询项目数量
	 * @param projectOrder
	 * @return
	 */
	public long getProjectCounts(ProjectOrder projectOrder);
	
	/**
	 * 统计项目数量
	 * @param projectOrder
	 * @return
	 */
	public Page<ProjectStatisticsInfo> queryProjectsStatistics(ProjectOrder projectOrder,PageParam pageParam);

}
