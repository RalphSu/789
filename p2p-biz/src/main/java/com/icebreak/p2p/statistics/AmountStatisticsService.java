package com.icebreak.p2p.statistics;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.RechargeStatistics;
import com.icebreak.p2p.dataobject.viewObject.AmountStatisticsInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.statistics.order.StatisticsQueryOrder;

public interface AmountStatisticsService {
	
	/**
	 * 用户数据统计
	 * @param queryOrder
	 * @return
	 */
	List<Object> amountStatisticsList(StatisticsQueryOrder queryOrder);
	
	/**
	 * 统计用户信息
	 * @param conditions
	 * @return
	 */
	long sumAmountByConditions(Map<String, Object> conditions);
	
	/**
	 * 统计用户信息分页
	 * @param conditions
	 * @return
	 */
	Page<AmountStatisticsInfoVO> amountStatisticsPage(StatisticsQueryOrder queryOrder,
														PageParam pageParam);
	
	/**
	 * 分页统计充值、提现
	 * @param queryOrder
	 * @param pageParam
	 * @return
	 */
	public Page<RechargeStatistics> rechargeStatisticsPage(StatisticsQueryOrder queryOrder,
															PageParam pageParam);
	
	public RechargeStatistics getRechargeStatistics(StatisticsQueryOrder queryOrder);
	
}
