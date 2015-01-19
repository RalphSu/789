package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.AmountStatisticsDao;
import com.icebreak.p2p.dataobject.RechargeStatistics;
import com.icebreak.p2p.dataobject.viewObject.AmountStatisticsInfoVO;

public class AmountStatisticsDaoImpl extends SqlMapClientDaoSupport implements AmountStatisticsDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<AmountStatisticsInfoVO> queryAmountListYear(Map<String,Object> condition){
		return getSqlMapClientTemplate().queryForList("RM-AMOUNT-STATISTICS-LISTBYYEAR", condition);
	}
	
	@Override
	public long queryAmountCountYear(Map<String,Object> condition){
		return (Long)getSqlMapClientTemplate().queryForObject("RM-AMOUNT-STATISTICS-COUNTBYYEAR", condition);
	}

	@Override
	public long queryAmountCountMonth(Map<String, Object> conditions) {
		return  (Long)getSqlMapClientTemplate().queryForObject("RM-AMOUNT-STATISTICS-COUNTBYMONTH", conditions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmountStatisticsInfoVO> queryAmountListMonth(
			Map<String, Object> conditions) {
		return  getSqlMapClientTemplate().queryForList("RM-AMOUNT-STATISTICS-LISTBYMONTH", conditions);
	}

	@Override
	public long queryAmountCountMarketting(Map<String, Object> conditions) {
		return  (Long)getSqlMapClientTemplate().queryForObject("RM-AMOUNT-STATISTICS-COUNTBYMARKETTING", conditions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmountStatisticsInfoVO> queryAmountListMarketting(
			Map<String, Object> conditions) {
		return getSqlMapClientTemplate().queryForList("RM-AMOUNT-STATISTICS-LISTBYMARKETTING", conditions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RechargeStatistics> queryRchargeList(
			Map<String, Object> conditions) {
		return (List<RechargeStatistics>)getSqlMapClientTemplate().queryForList("RECHARGE-STATISTICS-QUERY-LIST", conditions);
	}

	@Override
	public long queryRchargeListCounts(Map<String, Object> conditions) {
		return (Long)getSqlMapClientTemplate().queryForObject("RECHARGE-STATISTICS-QUERY-LIST-COUNTS", conditions);
	}

	@Override
	public RechargeStatistics queryByConditions(Map<String, Object> conditions) {
		return (RechargeStatistics)getSqlMapClientTemplate().queryForObject("RECHARGE-STATISTICS-WITHDRAW-COUNTS", conditions);
	}

}
