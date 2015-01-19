/**
 * www.icebreak.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.icebreak.p2p.dal.daointerface;
// auto generated imports

import java.util.HashMap;
import java.util.List;
import com.icebreak.util.lang.util.money.Money;
/****/
import com.icebreak.p2p.dal.dataobject.*;
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

public abstract interface ExtendAttrDAOAbstract {

    /**
    *  Select DB table <tt>extend_attr</tt>.
    *
    *	@param TblBaseId
    *	@return ExtendAttrDO
    *	@throws DataAccessException
    */
    public ExtendAttrDO select(java.lang.Long TblBaseId) throws DataAccessException;

    /***
     * 获取扩展属性值
     * @param recordId
     * @param extendAttrName
     * @return
     */
    public String getExtendValue(java.lang.Long recordId, String extendAttrName);


    /***
     * 根据属性key和记录ID修改值
     * @param extendAttr
     * @return
     */
    public int updateByAttrNameAndReordId(ExtendAttrDO extendAttr);

    /**
	 *  Update DB table <tt>extend_attr</tt>.
	 *
	 *	@param extendAttr
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ExtendAttrDO extendAttr) throws DataAccessException;

	/**
	 *  Insert one <tt>ExtendAttrDO</tt> object to DB table <tt>extend_attr</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *
	 *	@param extendAttr
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public java.lang.Long insert(ExtendAttrDO extendAttr) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>yjh_av_ex_ea</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *
	 *	@param TblBaseId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(String TblBaseId) throws DataAccessException;
	
}