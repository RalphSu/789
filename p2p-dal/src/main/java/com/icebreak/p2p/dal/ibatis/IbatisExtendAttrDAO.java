package com.icebreak.p2p.dal.ibatis;

import com.icebreak.p2p.dal.daointerface.ExtendAttrDAO;
import com.icebreak.p2p.dal.dataobject.ExtendAttrDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.icebreak.tgs.dal.dataobject.*;
**/

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
public class IbatisExtendAttrDAO extends SqlMapClientDaoSupport implements ExtendAttrDAO{

    @Override
    public ExtendAttrDO select(Long TblBaseId) throws DataAccessException {
        return (ExtendAttrDO) getSqlMapClientTemplate().queryForList("MS-EXTEND-ATTR-SELECT",TblBaseId);
    }

    @Override
    public String getExtendValue(Long recordId, String extendAttrName) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("recordId", recordId);
        params.put("attrName", extendAttrName);
        List<Object> datas = getSqlMapClientTemplate().queryForList("MS-EXTEND-ATTR-SELECT-ATTR-VALUE", params);
        if(datas != null && datas.size()>0){
            return (String)datas.get(0);
        }
        return null;
    }

    @Override
    public int updateByAttrNameAndReordId(ExtendAttrDO extendAttr) {
        if (extendAttr == null) {
            throw new IllegalArgumentException("Can't update by a null data object.");
        }

        return getSqlMapClientTemplate().update("MS-EXTEND-ATTR-UPDATE-BY-ATTRNAME-AND-RECORDID", extendAttr);
    }

    @Override
    public int update(ExtendAttrDO extendAttr) throws DataAccessException {
        if (extendAttr == null) {
            throw new IllegalArgumentException("Can't update by a null data object.");
        }

        return getSqlMapClientTemplate().update("MS-EXTEND-ATTR-UPDATE", extendAttr);
    }

    @Override
    public Long insert(ExtendAttrDO extendAttr) throws DataAccessException {
        if (extendAttr == null) {
            throw new IllegalArgumentException("Can't insert a null data object into db.");
        }

        getSqlMapClientTemplate().insert("MS-EXTEND-ATTR-INSERT", extendAttr);

        return extendAttr.getRecordId();
    }

    @Override
    public int deleteById(String TblBaseId) throws DataAccessException {
        return getSqlMapClientTemplate().delete("MS-EXTEND-ATTR-DELETE-BY-ID", TblBaseId);
    }
}