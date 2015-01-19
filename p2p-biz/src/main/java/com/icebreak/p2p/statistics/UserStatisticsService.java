package com.icebreak.p2p.statistics;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.statistics.order.StatisticsQueryOrder;

public interface UserStatisticsService {
	
	/**
	 * 用户数据统计
	 * @param queryOrder
	 * @return
	 */
	List<Object> userStatisticsList(StatisticsQueryOrder queryOrder);
	
	/**
	 * 统计用户信息
	 * @param conditions
	 * @return
	 */
	long countUserByConditions(Map<String, Object> conditions);
	
	/**
	 * 统计用户信息分页
	 * @param conditions
	 * @return
	 */
	Page<Object> userStatisticsPage(StatisticsQueryOrder queryOrder, PageParam pageParam);
	
}
