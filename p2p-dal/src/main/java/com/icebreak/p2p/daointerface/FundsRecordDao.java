package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.FundsRecord;

public interface FundsRecordDao {
	/**
	 * 插入一条新资金记录
	 * @param fundsRecord
	 */
	public void insert(FundsRecord fundsRecord)throws DataAccessException;
	/**
	 * 更新一条资金记录
	 * @param fundsRecord
	 * @return
	 */
	public int update(FundsRecord fundsRecord)throws DataAccessException;
	/**
	 * 根据条件统计记录数
	 * @param condition
	 * @return
	 */
	public long queryCountByCondition(Map<String,Object> condition);
	/**
	 * 根据条件查询资金记录
	 * @param condition
	 * @return
	 */
	public List<FundsRecord> queryFundsRecordsByCondition(Map<String,Object> condition);

}
