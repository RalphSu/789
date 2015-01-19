package com.icebreak.p2p.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.CommonDistrictDAO;
import com.icebreak.p2p.dal.dataobject.CommonDistrictDO;


import java.util.List;
import org.springframework.dao.DataAccessException;

@SuppressWarnings("unchecked")

public class IbatisCommonDistrictDAO extends SqlMapClientDaoSupport implements CommonDistrictDAO {
	/**
	 *  Query DB table <tt>common_district</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from common_district</tt>
	 *
	 *	@return List<CommonDistrictDO>
	 *	@throws DataAccessException
	 */	 
    public List<CommonDistrictDO> getAll() throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-COMMON-DISTRICT-GET-ALL", null);

    }

	/**
	 *  Query DB table <tt>common_district</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from common_district where (nb_no = ?)</tt>
	 *
	 *	@param nbNo
	 *	@return CommonDistrictDO
	 *	@throws DataAccessException
	 */	 
    public CommonDistrictDO getByDistrictNo(String nbNo) throws DataAccessException {

        return (CommonDistrictDO) getSqlMapClientTemplate().queryForObject("MS-COMMON-DISTRICT-GET-BY-DISTRICT-NO", nbNo);

    }

	/**
	 *  Query DB table <tt>common_district</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from common_district</tt>
	 *
	 *	@param areaName
	 *	@return CommonDistrictDO
	 *	@throws DataAccessException
	 */	 
    public CommonDistrictDO getByDistrictName(String areaName) throws DataAccessException {

        return (CommonDistrictDO) getSqlMapClientTemplate().queryForObject("MS-COMMON-DISTRICT-GET-BY-DISTRICT-NAME", areaName);

    }

	/**
	 *  Query DB table <tt>common_district</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from common_district</tt>
	 *
	 *	@param areaName
	 *	@return CommonDistrictDO
	 *	@throws DataAccessException
	 */	 
    public CommonDistrictDO getByProvinceName(String areaName) throws DataAccessException {

        return (CommonDistrictDO) getSqlMapClientTemplate().queryForObject("MS-COMMON-DISTRICT-GET-BY-PROVINCE-NAME", areaName);

    }

	/**
	 *  Query DB table <tt>common_district</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from common_district</tt>
	 *
	 *	@param areaName
	 *	@return CommonDistrictDO
	 *	@throws DataAccessException
	 */	 
    public CommonDistrictDO getByCityName(String areaName) throws DataAccessException {

        return (CommonDistrictDO) getSqlMapClientTemplate().queryForObject("MS-COMMON-DISTRICT-GET-BY-CITY-NAME", areaName);

    }

}
