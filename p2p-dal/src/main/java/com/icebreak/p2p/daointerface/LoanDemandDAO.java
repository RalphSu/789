package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.LoanAuthRecord;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;

public interface LoanDemandDAO {
	/**
	 * 添加,借款需求信息
	 * 
	 * @param loanDemand
	 * @return long
	 * @throws DataAccessException
	 * */
	public long insert(LoanDemandDO loanDemand) throws DataAccessException;
	/**
	 * 检查担保函编号编号
	 * @param licenceNo
	 * @return
	 * @throws DataAccessException
	 */
	public long checkLicenceNo(Map<String,Object> conditions) throws DataAccessException;

	/**
	 * 修改,借款需求信息
	 * 
	 * @param loanDemand
	 * @return long
	 * @throws DataAccessException
	 * */
	public int update(LoanDemandDO loanDemand) throws DataAccessException;

	/**
	 * 删除,借款需求信息
	 * 
	 * @param demandId
	 * @return long
	 * @throws DataAccessException
	 * */
	public int delete(long demandId) throws DataAccessException;
	
	/**
	 * 根据条件查询,获得借款需求信息
	 * 
	 * @param demandId
	 * @return long
	 * @throws DataAccessException
	 * */
	public LoanDemandDO queryLoanDemandByDemandId(long demandId) throws DataAccessException;

	/**
	 * 根据条件查询,获得借款需求信息,行数
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 * */
	public long queryCountByCondition(Map<String, Object> condition) throws DataAccessException;

	/**
	 * 根据条件查询,获得借款需求信息,集合
	 * 
	 * @param condition
	 * @return list
	 * @throws DataAccessException
	 * */
	public List<LoanDemandDO> queryListByCondition(Map<String, Object> condition) throws DataAccessException;
	
	/**
	 * 审核借款需求
	 * 
	 * @param demandId
	 * @param publishDate
	 * @param demandId
	 * @return long
	 * @throws DataAccessException
	 * */
	public int passInDismiss(Map<String, Object> condition) throws DataAccessException;
	
	/**
	 * 定时任务执行记录查询
	 * @param condition
	 * @return
	 */
	public List<LoanDemandDO> queryListByConditionForJob(
			Map<String, Object> condition);
	/**
	 * 查询担保机构下的交易记录条数
	 * @param condition
	 * @return
	 */
	public long queryCountByConditionForGuarantee(Map<String, Object> condition);
	/**
	 * 查询担保机构下的交易记录
	 * @param condition
	 * @return
	 */
	public List<LoanDemandDO> queryListByConditionForGuarantee(
			Map<String, Object> condition);
	/**
	 * 修改上传图片地址
	 * @param id
	 * @param newUrl
	 * @return
	 */
	public int updateFileUploadUrlById(Map<String, Object> condition)throws DataAccessException;
	/**
	 * 查询担保机构下的审核记录
	 * @param conditions
	 * @return
	 */
	public List<LoanAuthRecord> getLoanAuthRecordByCondition(
			Map<String, Object> conditions);
	/**
	 * 查询下线需求
	 * @param condition
	 * @return
	 * @throws DataAccessException
	 */
	public List<LoanDemandDO> queryOfflineListByCondtion(Map<String, Object> condition) throws DataAccessException;
	
	/**
	 * 统计下线需求数量
	 * @param condition
	 * @return
	 * @throws DataAccessException
	 */
	public long queryOfflineCountByCondition(Map<String, Object> condition) throws DataAccessException;
	/**
	 * 项目数量 
	 * @param condition
	 * @return
	 */
	public long getEstablishCounts(Map<String,Object> condition);
	/**项目列表
	 * @param condition
	 * @return
	 */
	public List<LoanDemandDO> queryEstablishList(Map<String,Object> condition);
	/**
	 * 查询待还款项目列表
	 * @param condition
	 * @return
	 */
	public List<Trade> queryWaitRepayLoanList(Map<String, Object> condition);
	/**
	 * 查询待还款项目数量
	 * @param condition
	 * @return
	 */
	public long getCountsWaitRepayLoan(Map<String, Object> condition);
	/**
	 * 统计不同状态下项目的金额
	 * @param condition
	 * @return
	 */
	public long getAmountsByStatus(Map<String,Object> condition);
}
