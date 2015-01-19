package com.icebreak.p2p.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.RepayPlanDAO;
import com.icebreak.p2p.dal.dataobject.RepayPlanDO;

import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class IbatisRepayPlanDAO extends SqlMapClientDaoSupport implements
		RepayPlanDAO {
	/**
	 * Insert one <tt>RepayPlanDO</tt> object to DB table <tt>repay_plan</tt>,
	 * return primary key
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>insert into repay_plan(repay_plan_id,period_no,period_count,trade_name,trade_id,repay_user_id,repay_user_name,repay_user_real_name,repay_division_way,amount,original_amount,status,repay_date,actual_repay_date,raw_add_time,note) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 * 
	 * @param repayPlan
	 * @return long
	 * @throws DataAccessException
	 */
	public long insert(RepayPlanDO repayPlan) throws DataAccessException {
		if (repayPlan == null) {
			throw new IllegalArgumentException(
					"Can't insert a null data object into db.");
		}

		getSqlMapClientTemplate().insert("MS-REPAY-PLAN-INSERT", repayPlan);

		return repayPlan.getRepayPlanId();
	}

	/**
	 * Update DB table <tt>repay_plan</tt>.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>update repay_plan set period_no=?, period_count=?, trade_name=?, trade_id=?, repay_user_id=?, repay_user_name=?, repay_user_real_name=?, repay_division_way=?, amount=?, original_amount=?, status=?, repay_date=?, actual_repay_date=?, note=? where (repay_plan_id = ?)</tt>
	 * 
	 * @param repayPlan
	 * @return int
	 * @throws DataAccessException
	 */
	public int update(RepayPlanDO repayPlan) throws DataAccessException {
		if (repayPlan == null) {
			throw new IllegalArgumentException(
					"Can't update by a null data object.");
		}

		return getSqlMapClientTemplate().update("MS-REPAY-PLAN-UPDATE",
				repayPlan);
	}

	/**
	 * Query DB table <tt>repay_plan</tt> for records.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select repay_plan_id, period_no, period_count, trade_name, trade_id, repay_user_id, repay_user_name, repay_user_real_name, repay_division_way, amount, original_amount, status, repay_date, actual_repay_date, raw_add_time, raw_update_time, note from repay_plan where (repay_plan_id = ?)</tt>
	 * 
	 * @param repayPlanId
	 * @return RepayPlanDO
	 * @throws DataAccessException
	 */
	public RepayPlanDO findById(long repayPlanId) throws DataAccessException {
		Long param = new Long(repayPlanId);

		return (RepayPlanDO) getSqlMapClientTemplate().queryForObject(
				"MS-REPAY-PLAN-FIND-BY-ID", param);

	}

	public RepayPlanDO findByIdwithrowLock(long repayPlanId)
			throws DataAccessException {

		Long param = new Long(repayPlanId);

		return (RepayPlanDO) getSqlMapClientTemplate().queryForObject(
				"MS-REPAY-PLAN-FIND-BY-ID-WITHROW-LOCK", param);

	}

	/**
	 * Query DB table <tt>repay_plan</tt> for records.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select repay_plan_id, period_no, period_count, trade_name, trade_id, repay_user_id, repay_user_name, repay_user_real_name, repay_division_way, amount, original_amount, status, repay_date, actual_repay_date, raw_add_time, raw_update_time, note from repay_plan where (trade_id = ?)</tt>
	 * 
	 * @param tradeId
	 * @return List<RepayPlanDO>
	 * @throws DataAccessException
	 */
	public List<RepayPlanDO> findByTradeId(long tradeId)
			throws DataAccessException {
		Long param = new Long(tradeId);

		return getSqlMapClientTemplate().queryForList(
				"MS-REPAY-PLAN-FIND-BY-TRADE-ID", param);

	}

	/**
	 * Delete records from DB table <tt>repay_plan</tt>.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>delete from repay_plan where (repay_plan_id = ?)</tt>
	 * 
	 * @param repayPlanId
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteById(long repayPlanId) throws DataAccessException {
		Long param = new Long(repayPlanId);

		return getSqlMapClientTemplate().delete("MS-REPAY-PLAN-DELETE-BY-ID",
				param);
	}

	/**
	 * Delete records from DB table <tt>repay_plan</tt>.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>delete from repay_plan where (trade_id = ?)</tt>
	 * 
	 * @param tradeId
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteByTradeId(long tradeId) throws DataAccessException {
		Long param = new Long(tradeId);

		return getSqlMapClientTemplate().delete(
				"MS-REPAY-PLAN-DELETE-BY-TRADE-ID", param);
	}

	/**
	 * Query DB table <tt>repay_plan</tt> for records.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select COUNT(*) from repay_plan</tt>
	 * 
	 * @return long
	 * @throws DataAccessException
	 */
	public long findAllCount() throws DataAccessException {

		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-REPAY-PLAN-FIND-ALL-COUNT", null);

		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}

	}

	/**
	 * Query DB table <tt>repay_plan</tt> for records.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select repay_plan_id, period_no, period_count, trade_name, trade_id, repay_user_id, repay_user_name, repay_user_real_name, repay_division_way, amount, original_amount, status, repay_date, actual_repay_date, raw_add_time, raw_update_time, note from repay_plan where (1 = 2)</tt>
	 * 
	 * @param repayPlan
	 * @param limitStart
	 * @param pageSize
	 * @param startRepayDate
	 * @param endRepayDate
	 * @param statusList
	 * @return List<RepayPlanDO>
	 * @throws DataAccessException
	 */
	public List<RepayPlanDO> findByCondition(RepayPlanDO repayPlan,
			long limitStart, long pageSize, Date startRepayDate,
			Date endRepayDate, Date startActualRepayDate,
			Date endActualRepayDate, List statusList)
			throws DataAccessException {
		if (repayPlan == null) {
			throw new IllegalArgumentException(
					"Can't select by a null data object.");
		}

		Map param = new HashMap();

		param.put("repayPlan", repayPlan);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("startRepayDate", startRepayDate);
		param.put("endRepayDate", endRepayDate);
		param.put("statusList", statusList);

		return getSqlMapClientTemplate().queryForList(
				"MS-REPAY-PLAN-FIND-BY-CONDITION", param);

	}

	/**
	 * Query DB table <tt>repay_plan</tt> for records.
	 * 
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select COUNT(*) from repay_plan</tt>
	 * 
	 * @param repayPlan
	 * @param startRepayDate
	 * @param endRepayDate
	 * @param statusList
	 * @return long
	 * @throws DataAccessException
	 */
	public long findByConditionCount(RepayPlanDO repayPlan,
			Date startRepayDate, Date endRepayDate, Date startActualRepayDate,
			Date endActualRepayDate, List statusList)
			throws DataAccessException {
		if (repayPlan == null) {
			throw new IllegalArgumentException(
					"Can't select by a null data object.");
		}

		Map param = new HashMap();

		param.put("repayPlan", repayPlan);
		param.put("startRepayDate", startRepayDate);
		param.put("endRepayDate", endRepayDate);
		param.put("statusList", statusList);

		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-REPAY-PLAN-FIND-BY-CONDITION-COUNT", param);

		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}

	}

	public List<RepayPlanDO> findByConditionGuarantee(RepayPlanDO repayPlan,
			long limitStart, long pageSize, Date startRepayDate,
			Date endRepayDate, Date startActualRepayDate,
			Date endActualRepayDate, List statusList, long guaranteeId)
			throws DataAccessException {
		if (repayPlan == null) {
			throw new IllegalArgumentException(
					"Can't select by a null data object.");
		}

		Map param = new HashMap();

		param.put("repayPlan", repayPlan);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("startRepayDate", startRepayDate);
		param.put("endRepayDate", endRepayDate);
		param.put("statusList", statusList);

		return getSqlMapClientTemplate().queryForList(
				"MS-REPAY-PLAN-FIND-BY-CONDITION", param);

	}

	public long findByConditionCountGuarantee(RepayPlanDO repayPlan,
			Date startRepayDate, Date endRepayDate, Date startActualRepayDate,
			Date endActualRepayDate, List statusList, long guaranteeId)
			throws DataAccessException {
		if (repayPlan == null) {
			throw new IllegalArgumentException(
					"Can't select by a null data object.");
		}

		Map param = new HashMap();

		param.put("repayPlan", repayPlan);
		param.put("startRepayDate", startRepayDate);
		param.put("endRepayDate", endRepayDate);
		param.put("statusList", statusList);

		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-REPAY-PLAN-FIND-BY-CONDITION-COUNT", param);

		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}

	}

	@Override
	public List<RepayPlanDO> query(RepayPlanDO repayPlan)
			throws DataAccessException {

		if (repayPlan == null) {
			throw new IllegalArgumentException(
					"Can't select by a null data object.");
		}

		return getSqlMapClientTemplate().queryForList("MS-REPAY-PLAN-QUERY",
				repayPlan);
	}

}
