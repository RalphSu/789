package com.icebreak.p2p.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.BankBaseInfoDAO;
import com.icebreak.p2p.dal.dataobject.BankBaseInfoDO;


import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
	
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisBankBaseInfoDAO extends SqlMapClientDaoSupport implements BankBaseInfoDAO {
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
    public String insert(BankBaseInfoDO bankBaseInfo) throws DataAccessException {
    	if (bankBaseInfo == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-BANK-BASE-INFO-INSERT", bankBaseInfo);

        return bankBaseInfo.getBankCode();
    }

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
    public BankBaseInfoDO findById(String bankCode) throws DataAccessException {

        return (BankBaseInfoDO) getSqlMapClientTemplate().queryForObject("MS-BANK-BASE-INFO-FIND-BY-ID", bankCode);

    }

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
    public int deleteById(String bankCode) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-BANK-BASE-INFO-DELETE-BY-ID", bankCode);
    }

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
    public int update(BankBaseInfoDO bankBaseInfo) throws DataAccessException {
    	if (bankBaseInfo == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-BANK-BASE-INFO-UPDATE", bankBaseInfo);
    }

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
    public List<BankBaseInfoDO> findAllBankConfig() throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-BANK-BASE-INFO-FIND-ALL-BANK-CONFIG", null);

    }

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
    public List<BankBaseInfoDO> findAllBankConfigIgnoredStatus() throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-BANK-BASE-INFO-FIND-ALL-BANK-CONFIG-IGNORED-STATUS", null);

    }

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
    public long searchDataCount(BankBaseInfoDO bankBaseInfo) throws DataAccessException {
    	if (bankBaseInfo == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-BANK-BASE-INFO-SEARCH-DATA-COUNT", bankBaseInfo);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

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
    public List<BankBaseInfoDO> findBankWithCondition(BankBaseInfoDO bankBaseInfo, long limitStart, long pageSize) throws DataAccessException {
    	if (bankBaseInfo == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("bankBaseInfo", bankBaseInfo);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));

        return getSqlMapClientTemplate().queryForList("MS-BANK-BASE-INFO-FIND-BANK-WITH-CONDITION", param);

    }

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
    public long searchDataCountWithCondition(BankBaseInfoDO bankBaseInfo) throws DataAccessException {
    	if (bankBaseInfo == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-BANK-BASE-INFO-SEARCH-DATA-COUNT-WITH-CONDITION", bankBaseInfo);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}
