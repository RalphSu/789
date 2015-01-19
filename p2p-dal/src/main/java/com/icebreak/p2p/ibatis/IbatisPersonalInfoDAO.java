package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.PersonalInfoDAO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;


public class IbatisPersonalInfoDAO extends SqlMapClientDaoSupport implements PersonalInfoDAO {
	/**
	 *  Insert one <tt>PersonalInfoDO</tt> object to DB table <tt>personal_info</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into personal_info(user_base_id,real_name,cert_no,business_period,cert_front_path,cert_back_path,bank_open_name,bank_card_no,bank_key,bank_province,bank_city,bank_address,gender,referees) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param personalInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(PersonalInfoDO personalInfo) throws DataAccessException {
    	if (personalInfo == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PERSONAL-INFO-INSERT", personalInfo);

        return personalInfo.getId();
    }

	/**
	 *  Update DB table <tt>personal_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update personal_info set user_base_id=?, real_name=?, cert_no=?, business_period=?, cert_front_path=?, cert_back_path=?, bank_open_name=?, bank_card_no=?, bank_key=?, bank_province=?, bank_city=?, bank_address=?, gender=?, referees=? where (id = ?)</tt>
	 *
	 *	@param personalInfo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(PersonalInfoDO personalInfo) throws DataAccessException {
    	if (personalInfo == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-PERSONAL-INFO-UPDATE", personalInfo);
    }

	/**
	 *  Delete records from DB table <tt>personal_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from personal_info where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int delete(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-PERSONAL-INFO-DELETE", param);
    }

    
	@Override
	public long queryCount(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-PERSONAL-INFO-QUERY_COUNT", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalInfoDO> queryList(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-PERSONAL-INFO-QUERY_LIST", condition);
	}

	@Override
	public PersonalInfoDO query(String userBaseId) throws DataAccessException {
		return (PersonalInfoDO) getSqlMapClientTemplate().queryForObject("MS-PERSONAL-INFO-QUERY", userBaseId);
	}

	@Override
	public long queryChildrenCountByCondition(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-PERSONAL-INFO-QUERY_COUNT_INVESTOR", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalInfoDO> queryChildrenListByCondition(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-PERSONAL-INFO-QUERY_COUNT_INVESTOR_LIST", condition);
	}

	@Override
	public String getCertNoByAccountId(String accountId) {

		return (String)getSqlMapClientTemplate().queryForObject("MS-PERSONAL-INFO-CERTNO", accountId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalInfoDO> queryAllByConditions(
			Map<String, Object> conditions) throws DataAccessException {

		return getSqlMapClientTemplate().queryForList("MS-INFO-QUERY_ALLLIST", conditions);
	}

	@Override
	public long queryAllCountByConditions(Map<String, Object> conditions)
			throws DataAccessException {

		return (Long)getSqlMapClientTemplate().queryForObject("MS-INFO-QUERY_ALLLCOUNT", conditions);
	}

	@Override
	public long countCertNoByRole(Map<String,Object > conditions) {
		
		return  (Long)getSqlMapClientTemplate().queryForObject("MS-INFO-COUNT_CERTNO_BYCONDITION", conditions);
	}


}
