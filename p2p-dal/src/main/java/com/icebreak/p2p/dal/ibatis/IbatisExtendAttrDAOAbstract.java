/**
 * www.icebreak.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.icebreak.p2p.dal.ibatis;

/****/
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.icebreak.p2p.dal.dataobject.*;
import com.icebreak.util.lang.util.money.Money;
import com.icebreak.p2p.dal.daointerface.ExtendAttrDAOAbstract;
// auto generated imports
import com.icebreak.p2p.dal.dataobject.ExtendAttrDO;
import org.springframework.dao.DataAccessException;

/**
*
* @create by CodeMaker
* @Description 
* @Version 1.0
* @Author xy
* @Email xy@taogushen.com
* @History
*<li>Author: xy</li>
*<li>Date: </li>
*<li>Version: 1.0</li>
*<li>Content: create</li>
*
*/
@SuppressWarnings("unchecked")
public abstract class IbatisExtendAttrDAOAbstract extends SqlMapClientDaoSupport implements ExtendAttrDAOAbstract {

	/**
	*  Select DB table <tt>extend_attr</tt>.
	*
	*	@param TblBaseId
	*	@return ExtendAttrDO
	*	@throws DataAccessException
	*/
	public ExtendAttrDO select(java.lang.Long TblBaseId) throws DataAccessException {
		if (TblBaseId == null) {
			throw new IllegalArgumentException("Can't find the data object, beacuse the primary key is null.");
		}

		return (ExtendAttrDO)getSqlMapClientTemplate().queryForObject("MS-EXTEND-ATTR-SELECT", TblBaseId);
	}

	/**
	 *  Update DB table <tt>extend_attr</tt>.
	 *
	 *	@param extendAttr
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ExtendAttrDO extendAttr) throws DataAccessException {
    	if (extendAttr == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}

        return getSqlMapClientTemplate().update("MS-EXTEND-ATTR-UPDATE", extendAttr);
    }

	/**
	 *	@param extendAttr
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public java.lang.Long insert(ExtendAttrDO extendAttr) throws DataAccessException {
    	if (extendAttr == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-EXTEND-ATTR-INSERT", extendAttr);

        return extendAttr.getTblBaseId();
    }

	/**
	 *	@param TblBaseId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(String TblBaseId) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-EXTEND-ATTR-DELETE-BY-ID", TblBaseId);
    }
	
}