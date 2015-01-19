package com.icebreak.p2p.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.SysParamDAO;
import com.icebreak.p2p.dal.dataobject.SysParamDO;


import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
	
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisSysParamDAO extends SqlMapClientDaoSupport implements SysParamDAO {
	/**
	 *  Insert one <tt>SysParamDO</tt> object to DB table <tt>sys_param</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into sys_param(param_name,param_value,extend_attribute_one,extend_attribute_two,raw_add_time) values (?, ?, ?, ?, ?)</tt>
	 *
	 *	@param sysParam
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public String insert(SysParamDO sysParam) throws DataAccessException {
    	if (sysParam == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-SYS-PARAM-INSERT", sysParam);

        return sysParam.getParamName();
    }

	/**
	 *  Update DB table <tt>sys_param</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_param set param_value=?, extend_attribute_one=?, extend_attribute_two=? where (param_name = ?)</tt>
	 *
	 *	@param sysParam
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(SysParamDO sysParam) throws DataAccessException {
    	if (sysParam == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-SYS-PARAM-UPDATE", sysParam);
    }

	/**
	 *  Delete records from DB table <tt>sys_param</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from sys_param where (param_name = ?)</tt>
	 *
	 *	@param paramName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByParamName(String paramName) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-SYS-PARAM-DELETE-BY-PARAM-NAME", paramName);
    }

	/**
	 *  Query DB table <tt>sys_param</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select param_name, param_value, extend_attribute_one, extend_attribute_two, raw_add_time, raw_update_time from sys_param where (param_name = ?)</tt>
	 *
	 *	@param paramName
	 *	@return SysParamDO
	 *	@throws DataAccessException
	 */	 
    public SysParamDO findById(String paramName) throws DataAccessException {

        return (SysParamDO) getSqlMapClientTemplate().queryForObject("MS-SYS-PARAM-FIND-BY-ID", paramName);

    }

	/**
	 *  Query DB table <tt>sys_param</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select param_name, param_value, extend_attribute_one, extend_attribute_two, raw_add_time, raw_update_time from sys_param</tt>
	 *
	 *	@return List<SysParamDO>
	 *	@throws DataAccessException
	 */	 
    public List<SysParamDO> findAll() throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-SYS-PARAM-FIND-ALL", null);

    }

	/**
	 *  Query DB table <tt>sys_param</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select param_name, param_value, extend_attribute_one, extend_attribute_two, raw_add_time, raw_update_time from sys_param where (param_name LIKE ?) order by param_name ASC</tt>
	 *
	 *	@param paramName
	 *	@return List<SysParamDO>
	 *	@throws DataAccessException
	 */	 
    public List<SysParamDO> findByLike(String paramName) throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-SYS-PARAM-FIND-BY-LIKE", paramName);

    }

	/**
	 *  Query DB table <tt>sys_param</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from sys_param where (param_name = ?)</tt>
	 *
	 *	@param paramName
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long paramInfoQueryCount(String paramName) throws DataAccessException {

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-SYS-PARAM-PARAM-INFO-QUERY-COUNT", paramName);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

	/**
	 *  Query DB table <tt>sys_param</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select param_name, param_value, extend_attribute_one, extend_attribute_two, raw_add_time, raw_update_time from sys_param where (param_name = ?)</tt>
	 *
	 *	@param paramName
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<SysParamDO>
	 *	@throws DataAccessException
	 */	 
    public List<SysParamDO> paramInfoQueryList(String paramName, long limitStart, long pageSize) throws DataAccessException {
        Map param = new HashMap();

        param.put("paramName", paramName);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));

        return getSqlMapClientTemplate().queryForList("MS-SYS-PARAM-PARAM-INFO-QUERY-LIST", param);

    }

}
