package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.LoanDemandDAO;
import com.icebreak.p2p.dataobject.LoanAuthRecord;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;


public class IbatisLoanDemandDAO extends SqlMapClientDaoSupport implements LoanDemandDAO {

	public long insert(LoanDemandDO loanDemand) throws DataAccessException {
		if (loanDemand == null) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		getSqlMapClientTemplate().insert("MS-LOAN-DEMAND-INSERT", loanDemand);
		return loanDemand.getDemandId();
	}

	public int update(LoanDemandDO loanDemand) throws DataAccessException {
		if (loanDemand == null) {
			throw new IllegalArgumentException("Can't update by a null data object.");
		}
		return getSqlMapClientTemplate().update("MS-LOAN-DEMAND-UPDATE",loanDemand);
	}

	public int delete(long demandId) throws DataAccessException {
		Long param = new Long(demandId);
		return getSqlMapClientTemplate().delete("MS-LOAN-DEMAND-DELETE", param);
	}
	
	@Override
	public LoanDemandDO queryLoanDemandByDemandId(long demandId) throws DataAccessException {
		return (LoanDemandDO) getSqlMapClientTemplate().queryForObject("MS-ELOAN-DEMAND-QUERY_LOANDEMAND_BY_DEMANDID", demandId);
	}
	
	@Override
	public long queryCountByCondition(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-ELOAN-DEMAND-QUERY_COUNT_BY_CONDITION", condition);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDemandDO> queryListByCondition(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-ELOAN-DEMAND-QUERY_LIST_BY_CONDITION", condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDemandDO> queryOfflineListByCondtion(Map<String, Object> condition) throws DataAccessException{
		return getSqlMapClientTemplate().queryForList("MS-ELOAN-DEMAND-QUERY_OFFLINE_LIST_BY_CONDITION", condition);
	}
	
	@Override
	public long queryOfflineCountByCondition(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-ELOAN-DEMAND-QUERY_OFFLINE_COUNT_BY_CONDITION", condition);
	}

	@Override
	public int passInDismiss(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().update("MS-LOAN-DEMAND-UPDATE-PASS-IN-DISMISS", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDemandDO> queryListByConditionForJob(
			Map<String, Object> condition) {
		return getSqlMapClientTemplate().queryForList("MS-ELOAN-DEMAND-QUERY_LIST_BY_CONDITION_FORJOB", condition);
	}

	@Override
	public long queryCountByConditionForGuarantee(Map<String, Object> condition) {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-ELOAN-DEMAND-GUARANTEE_COUNT_BY_CONDITION", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDemandDO> queryListByConditionForGuarantee(
			Map<String, Object> condition) {
		return (List<LoanDemandDO>) getSqlMapClientTemplate().queryForList("MS-ELOAN-DEMAND-GUARANTEE_LIST_BY_CONDITION", condition);
	}

	@Override
	public long checkLicenceNo(Map<String,Object> conditions) throws DataAccessException {

		return (Long)getSqlMapClientTemplate().queryForObject("MS-CHECKLICENSENO", conditions);
	}
	public int updateFileUploadUrlById(Map<String, Object> condition)
			throws DataAccessException{
		if(condition.size()==0){
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		return getSqlMapClientTemplate().update("MS-FILEUPLOAD-URL-UPDATE", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAuthRecord> getLoanAuthRecordByCondition(
			Map<String, Object> conditions) {
		
		return (List<LoanAuthRecord>) getSqlMapClientTemplate().queryForList("LOAN-AUTH-RECORD-LISTBYCONDITIONS", conditions);
	}

	@Override
	public long getEstablishCounts(Map<String, Object> condition) {
		return (Long)getSqlMapClientTemplate().queryForObject("LOAN-NOLETTERCONTRACT-COUNTS", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDemandDO> queryEstablishList(
			Map<String, Object> condition) {
		return (List<LoanDemandDO>) getSqlMapClientTemplate().queryForList("LOAN-NOLETTERCONTRACT-LIST", condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Trade> queryWaitRepayLoanList(Map<String, Object> condition){
		return (List<Trade>) getSqlMapClientTemplate().queryForList("LOAN-REPAY-LIST", condition);
	}
	
	@Override
	public long getCountsWaitRepayLoan(Map<String, Object> condition){
		return (Long)getSqlMapClientTemplate().queryForObject("LOAN-REPAY-COUNTS", condition);
	}

	@Override
	public long getAmountsByStatus(Map<String, Object> condition) {
		return (Long)getSqlMapClientTemplate().queryForObject("LOAN-STATUS-AMOUNTS", condition);
	}
	
}
