package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.RechargeStatistics;
import com.icebreak.p2p.dataobject.viewObject.AmountStatisticsInfoVO;


public interface AmountStatisticsDao {
	/**
	 * 查询list列表年度
	 * @param condition
	 * @return
	 */
	public long queryAmountCountYear(Map<String,Object> condition);
	/**
	 * 查询条数年度
	 * @param condition
	 * @return
	 */
	public List<AmountStatisticsInfoVO> queryAmountListYear(Map<String,Object> condition);
	/**
	 * 查询条数月度
	 * @param conditions
	 * @return
	 */
	public long queryAmountCountMonth(Map<String, Object> conditions);
	/**
	 * 查询list列表月度
	 * @param conditions
	 * @return
	 */
	public List<AmountStatisticsInfoVO> queryAmountListMonth(
			Map<String, Object> conditions);
	/**
	 * 查询列表营销机构条数
	 * @param conditions
	 * @return
	 */
	public long queryAmountCountMarketting(Map<String, Object> conditions);
	/**
	 * 查询list列表营销机构
	 * @param conditions
	 * @return
	 */
	public List<AmountStatisticsInfoVO> queryAmountListMarketting(
			Map<String, Object> conditions);
	
	
	/**
	 * 查询充值、提现记录列表
	 * @param conditions
	 * @return
	 */
	public List<RechargeStatistics> queryRchargeList(Map<String, Object> conditions);
	/**
	 * 根据条件查询充值、提现数量
	 * @param conditions
	 * @return
	 */
	public long queryRchargeListCounts(Map<String, Object> conditions);
	/**
	 *根据条件(状态、银行、时间)查询充值、提现成功或失败数量
	 * @param conditions
	 * @return
	 */
	public RechargeStatistics queryByConditions(Map<String, Object> conditions);
}
