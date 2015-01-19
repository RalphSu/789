package com.icebreak.p2p.dal.daointerface;

import com.icebreak.p2p.dal.dataobject.CommonDistrictDO;

import java.util.List;
import org.springframework.dao.DataAccessException;

 @SuppressWarnings("rawtypes") 
public interface CommonDistrictDAO {
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
    public List<CommonDistrictDO> getAll() throws DataAccessException;

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
    public CommonDistrictDO getByDistrictNo(String nbNo) throws DataAccessException;

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
    public CommonDistrictDO getByDistrictName(String areaName) throws DataAccessException;

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
    public CommonDistrictDO getByProvinceName(String areaName) throws DataAccessException;

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
    public CommonDistrictDO getByCityName(String areaName) throws DataAccessException;

}
