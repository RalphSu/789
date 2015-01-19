package com.icebreak.p2p.daointerface;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.LoanAuthRecord;


public interface LoanAuthRecordDao {
	  /**
	   * 新增借款审核记录
	   * @param LoanAuthRecord
	   */
	  public void addLoanAuthRecord(LoanAuthRecord record) throws DataAccessException;
	  /**
	   * 根据map信息查询是否有该条记录
	   * @return
	   */
	  public long countLoanAuthRecordByCondition(Map<String, Object> conditions);
}
