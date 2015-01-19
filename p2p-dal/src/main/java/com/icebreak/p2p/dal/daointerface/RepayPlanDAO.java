package com.icebreak.p2p.dal.daointerface;

import com.icebreak.p2p.dal.dataobject.RepayPlanDO;

import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Date;

@SuppressWarnings("rawtypes")
public interface RepayPlanDAO {
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
	public long insert(RepayPlanDO repayPlan) throws DataAccessException;

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
	public int update(RepayPlanDO repayPlan) throws DataAccessException;

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
	public RepayPlanDO findById(long repayPlanId) throws DataAccessException;

	/**
	 * 
	 * @param repayPlanId
	 * @return
	 * @throws DataAccessException
	 */
	public RepayPlanDO findByIdwithrowLock(long repayPlanId)
			throws DataAccessException;

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
			throws DataAccessException;

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
	public int deleteById(long repayPlanId) throws DataAccessException;

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
	public int deleteByTradeId(long tradeId) throws DataAccessException;

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
	public long findAllCount() throws DataAccessException;

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
			throws DataAccessException;

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
			throws DataAccessException;

	public List<RepayPlanDO> findByConditionGuarantee(RepayPlanDO repayPlan,
			long limitStart, long pageSize, Date startRepayDate,
			Date endRepayDate, Date startActualRepayDate,
			Date endActualRepayDate, List statusList, long guaranteeId)
			throws DataAccessException;

	public long findByConditionCountGuarantee(RepayPlanDO repayPlan,
			Date startRepayDate, Date endRepayDate, Date startActualRepayDate,
			Date endActualRepayDate, List statusList, long guaranteeId)
			throws DataAccessException;

	public List<RepayPlanDO> query(RepayPlanDO repayPlan)
			throws DataAccessException;

}
