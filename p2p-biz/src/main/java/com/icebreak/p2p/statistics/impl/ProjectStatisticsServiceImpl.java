package com.icebreak.p2p.statistics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.daointerface.ProjectStatisticsDao;
import com.icebreak.p2p.dataobject.ProjectStatisticsInfo;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.statistics.ProjectOrder;
import com.icebreak.p2p.statistics.ProjectStatisticsService;
import com.icebreak.p2p.util.CommonUtil;

public class ProjectStatisticsServiceImpl implements ProjectStatisticsService {
	@Autowired
	ProjectStatisticsDao projectStatisticsDao;
	
	@Override
	public long getProjectCounts(ProjectOrder projectOrder){
		Map<String,Object> condition = new HashMap<String, Object>();
		long counts = 0;
		condition.put("startTime", projectOrder.getStartTime());
		condition.put("endTime", projectOrder.getEndTime());
		condition.put("status", projectOrder.getStatus());
		condition.put("dimension", projectOrder.getDimension());
		counts = projectStatisticsDao.getCountsProjectQuantityStatus(condition);
		CommonUtil.clearMap(condition);
		return counts;
	}

	@Override
	public Page<ProjectStatisticsInfo> queryProjectsStatistics(
			ProjectOrder projectOrder,PageParam pageParam) {
		List<ProjectStatisticsInfo> info = null;
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("startTime", projectOrder.getStartTime());
		condition.put("endTime", projectOrder.getEndTime());
		condition.put("dimension", projectOrder.getDimension());
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		int totalSize = (int) projectStatisticsDao.queryCountsByCondition(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),pageParam.getPageNo());
		condition.put("limitStart", start);
		info = projectStatisticsDao.queryListProject(condition);
		Page<ProjectStatisticsInfo> page = new Page<ProjectStatisticsInfo>(start, totalSize, pageParam.getPageSize(), info);
		CommonUtil.clearMap(condition);//清空map
		return page;
	}

}
