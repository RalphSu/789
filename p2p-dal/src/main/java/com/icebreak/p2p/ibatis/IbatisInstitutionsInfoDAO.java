package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.InstitutionsInfoDAO;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;


public class IbatisInstitutionsInfoDAO extends SqlMapClientDaoSupport implements InstitutionsInfoDAO {
	/**
	 *  Insert one <tt>InstitutionsInfoDO</tt> object to DB table <tt>institutions_info</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into institutions_info(user_base_id,enterprise_name,organization_code,tax_registration_no,business_license_no,business_license_province,business_license_city,commonly_used_address,business_period,legal_representative_name,legal_representative_card_no,business_license_path,business_license_cachet_path,cert_front_path,cert_back_path,opening_license_path,bank_open_name,bank_card_no,bank_key,bank_province,bank_city,bank_address,institutions_in_code,institutions_them_roughly,referees) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param institutionsInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(InstitutionsInfoDO institutionsInfo) throws DataAccessException {
    	if (institutionsInfo == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-INSTITUTIONS-INFO-INSERT", institutionsInfo);

        return institutionsInfo.getId();
    }

	/**
	 *  Update DB table <tt>institutions_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update institutions_info set user_base_id=?, enterprise_name=?, organization_code=?, tax_registration_no=?, business_license_no=?, business_license_province=?, business_license_city=?, commonly_used_address=?, business_period=?, legal_representative_name=?, legal_representative_card_no=?, business_license_path=?, business_license_cachet_path=?, cert_front_path=?, cert_back_path=?, opening_license_path=?, bank_open_name=?, bank_card_no=?, bank_key=?, bank_province=?, bank_city=?, bank_address=?, institutions_in_code=?, institutions_them_roughly=?, referees=? where (id = ?)</tt>
	 *
	 *	@param institutionsInfo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(InstitutionsInfoDO institutionsInfo) throws DataAccessException {
    	if (institutionsInfo == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-INSTITUTIONS-INFO-UPDATE", institutionsInfo);
    }

	/**
	 *  Delete records from DB table <tt>institutions_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from institutions_info where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int delete(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-INSTITUTIONS-INFO-DELETE", param);
    }

    
	@Override
	public long queryCount(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-INSTITUTIONS-INFO-QUERY_COUNT", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionsInfoDO> queryList(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-INSTITUTIONS-INFO-QUERY_LIST", condition);
	}

	@Override
	public InstitutionsInfoDO query(String userBaseId) throws DataAccessException {
		return (InstitutionsInfoDO) getSqlMapClientTemplate().queryForObject("MS-INSTITUTIONS-INFO-QUERY", userBaseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionsInfoDO> queryListeEnterpriseNameAndUserId(long roleId) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-INSTITUTIONS-INFO-QUERY_USERID_ENTERPRISENAME", roleId);
	}

	@Override
	public InstitutionsInfoDO queryByUserId(long userId) throws DataAccessException {
		return (InstitutionsInfoDO) getSqlMapClientTemplate().queryForObject("MS-INSTITUTIONS-INFO-QUERY_USERID", userId);
	}

	@Override
	public long queryByRoleCount(Map<String, Object> condition)
			throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-INSTITUTIONS-INFO-QUERY_BY_ROLE_COUNT", condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionsInfoDO> queryByRole(Map<String, Object> condition)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-INSTITUTIONS-INFO-QUERY_BY_ROLE", condition);
	}

	@Override
	public long queryChildrenCountByCondition(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-INSTITUTION-INFO-QUERY_COUNT_INVESTOR", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionsInfoDO> queryChildrenListByCondition(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-INSTITUTION-INFO-QUERY_COUNT_INVESTOR_LIST", condition);
	}

	

}
