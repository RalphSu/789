package com.icebreak.p2p.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.O2oJoinApplicationDAO;
import com.icebreak.p2p.dal.dataobject.O2oJoinApplicationDO;


import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
	
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisO2oJoinApplicationDAO extends SqlMapClientDaoSupport implements O2oJoinApplicationDAO {
	/**
	 *  Insert one <tt>O2oJoinApplicationDO</tt> object to DB table <tt>o2o_join_application</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into o2o_join_application(id,company_name,contact_name,human_resources,contact_phone,industry,age,sales_products,company_address,status,row_update_time,row_add_time,annex_url) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param o2oJoinApplication
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(O2oJoinApplicationDO o2oJoinApplication) throws DataAccessException {
    	if (o2oJoinApplication == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-O-2-O-JOIN-APPLICATION-INSERT", o2oJoinApplication);

        return o2oJoinApplication.getId();
    }

	/**
	 *  Query DB table <tt>o2o_join_application</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, company_name, contact_name, human_resources, contact_phone, industry, age, sales_products, company_address, status, row_update_time, row_add_time, annex_url from o2o_join_application where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return O2oJoinApplicationDO
	 *	@throws DataAccessException
	 */	 
    public O2oJoinApplicationDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (O2oJoinApplicationDO) getSqlMapClientTemplate().queryForObject("MS-O-2-O-JOIN-APPLICATION-FIND-BY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>o2o_join_application</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from o2o_join_application where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-O-2-O-JOIN-APPLICATION-DELETE-BY-ID", param);
    }

	/**
	 *  Update DB table <tt>o2o_join_application</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update o2o_join_application set status=?, row_update_time=? where (id = ?)</tt>
	 *
	 *	@param o2oJoinApplication
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateById(O2oJoinApplicationDO o2oJoinApplication) throws DataAccessException {
    	if (o2oJoinApplication == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-O-2-O-JOIN-APPLICATION-UPDATE-BY-ID", o2oJoinApplication);
    }

	/**
	 *  Query DB table <tt>o2o_join_application</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, company_name, contact_name, human_resources, contact_phone, industry, age, sales_products, company_address, status, row_update_time, row_add_time, annex_url from o2o_join_application</tt>
	 *
	 *	@param o2oJoinApplication
	 *	@param limitStart
	 *	@param pageSize
	 *	@param status
	 *	@param contactName
	 *	@return List<O2oJoinApplicationDO>
	 *	@throws DataAccessException
	 */	 
    public List<O2oJoinApplicationDO> findByCondition(O2oJoinApplicationDO o2oJoinApplication, long limitStart, long pageSize, String status, String contactName) throws DataAccessException {
    	if (o2oJoinApplication == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("o2oJoinApplication", o2oJoinApplication);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));
        param.put("status", status);
        param.put("contactName", contactName);

        return getSqlMapClientTemplate().queryForList("MS-O-2-O-JOIN-APPLICATION-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>o2o_join_application</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from o2o_join_application</tt>
	 *
	 *	@param o2oJoinApplication
	 *	@param status
	 *	@param contactName
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long countByCondition(O2oJoinApplicationDO o2oJoinApplication, String status, String contactName) throws DataAccessException {
    	if (o2oJoinApplication == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("o2oJoinApplication", o2oJoinApplication);
        param.put("status", status);
        param.put("contactName", contactName);

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-O-2-O-JOIN-APPLICATION-COUNT-BY-CONDITION", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

	/**
	 *  Query DB table <tt>o2o_join_application</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from o2o_join_application</tt>
	 *
	 *	@return List<Long>
	 *	@throws DataAccessException
	 */	 
    public List<Long> countAll() throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-O-2-O-JOIN-APPLICATION-COUNT-ALL", null);

    }

}
