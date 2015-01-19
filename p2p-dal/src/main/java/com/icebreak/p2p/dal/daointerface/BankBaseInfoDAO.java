package com.icebreak.p2p.dal.daointerface;

import com.icebreak.p2p.dal.dataobject.BankBaseInfoDO;

import org.springframework.dao.DataAccessException;
import java.util.List;

public interface BankBaseInfoDAO {
	/**
	 *  Insert one <tt>BankBaseInfoDO</tt> object to DB table <tt>bank_base_info</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into bank_base_info(bank_code,bank_name,withholding_amount,withdraw_amount,signed_way,is_stop,logo_url,memo,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param bankBaseInfo
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public String insert(BankBaseInfoDO bankBaseInfo) throws DataAccessException;

	/**
	 *  Query DB table <tt>bank_base_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select bank_code, bank_name, withholding_amount, withdraw_amount, signed_way, is_stop, logo_url, memo, raw_add_time, raw_update_time from bank_base_info where (bank_code = ?)</tt>
	 *
	 *	@param bankCode
	 *	@return BankBaseInfoDO
	 *	@throws DataAccessException
	 */	 
    public BankBaseInfoDO findById(String bankCode) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>bank_base_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from bank_base_info where (bank_code = ?)</tt>
	 *
	 *	@param bankCode
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(String bankCode) throws DataAccessException;

	/**
	 *  Update DB table <tt>bank_base_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update bank_base_info set bank_name=?, withholding_amount=?, withdraw_amount=?, signed_way=?, is_stop=?, logo_url=?, memo=?, raw_add_time=?, raw_update_time=? where (bank_code = ?)</tt>
	 *
	 *	@param bankBaseInfo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(BankBaseInfoDO bankBaseInfo) throws DataAccessException;

	/**
	 *  Query DB table <tt>bank_base_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select bank_code, bank_name, withholding_amount, withdraw_amount, signed_way, is_stop, logo_url, memo, raw_add_time, raw_update_time from bank_base_info where (is_stop = 1)</tt>
	 *
	 *	@return List<BankBaseInfoDO>
	 *	@throws DataAccessException
	 */	 
    public List<BankBaseInfoDO> findAllBankConfig() throws DataAccessException;

	/**
	 *  Query DB table <tt>bank_base_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select bank_code, bank_name, withholding_amount, withdraw_amount, signed_way, is_stop, logo_url, memo, raw_add_time, raw_update_time from bank_base_info</tt>
	 *
	 *	@return List<BankBaseInfoDO>
	 *	@throws DataAccessException
	 */	 
    public List<BankBaseInfoDO> findAllBankConfigIgnoredStatus() throws DataAccessException;

	/**
	 *  Query DB table <tt>bank_base_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from bank_base_info</tt>
	 *
	 *	@param bankBaseInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long searchDataCount(BankBaseInfoDO bankBaseInfo) throws DataAccessException;

	/**
	 *  Query DB table <tt>bank_base_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select bank_code, bank_name, withholding_amount, withdraw_amount, signed_way, is_stop, logo_url, memo, raw_add_time, raw_update_time from bank_base_info</tt>
	 *
	 *	@param bankBaseInfo
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<BankBaseInfoDO>
	 *	@throws DataAccessException
	 */	 
    public List<BankBaseInfoDO> findBankWithCondition(BankBaseInfoDO bankBaseInfo, long limitStart, long pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>bank_base_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from bank_base_info</tt>
	 *
	 *	@param bankBaseInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long searchDataCountWithCondition(BankBaseInfoDO bankBaseInfo) throws DataAccessException;

}
