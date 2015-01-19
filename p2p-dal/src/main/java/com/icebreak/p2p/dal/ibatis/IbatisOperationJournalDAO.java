package com.icebreak.p2p.dal.ibatis;

import com.icebreak.p2p.dal.daointerface.OperationJournalDAO;
import com.icebreak.p2p.dal.dataobject.OperationJournalDO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisOperationJournalDAO extends SqlMapClientDaoSupport implements OperationJournalDAO {
	/**
	 *  Insert one <tt>OperationJournalDO</tt> object to DB table <tt>operation_journal</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into operation_journal(base_module_name,permission_name,operation_content,memo,operator_id,operator_name,operator_ip,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param operationJournal
	 *	@return long
	 *	@throws org.springframework.dao.DataAccessException
	 */
    public long insert(OperationJournalDO operationJournal) throws DataAccessException {
    	if (operationJournal == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}

        getSqlMapClientTemplate().insert("MS-OPERATION-JOURNAL-INSERT", operationJournal);

        return operationJournal.getIdentity();
    }

    public int update(OperationJournalDO operationJournal) throws DataAccessException {
    	if (operationJournal == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-OPERATION-JOURNAL-UPDATE", operationJournal);
    }

    public OperationJournalDO findByIdentity (long identity) throws DataAccessException {
        Long param = new Long(identity);

        return (OperationJournalDO) getSqlMapClientTemplate().queryForObject("MS-OPERATION-JOURNAL-FIND-BY-IDENTITY", param);

    }

    public List<OperationJournalDO> queryOperationJournalPageList(OperationJournalDO operationJournal, Date operatorTimeStart, Date operatorTimeEnd, long limitStart, long pageSize) throws DataAccessException {
    	if (operationJournal == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("operationJournal", operationJournal);
        param.put("operatorTimeStart", operatorTimeStart);
        param.put("operatorTimeEnd", operatorTimeEnd);
        param.put("limitStart", limitStart);
        param.put("pageSize", pageSize);

        return getSqlMapClientTemplate().queryForList("MS-OPERATION-JOURNAL-QUERY-OPERATION-JOURNAL-PAGE-LIST", param);

    }

    public long queryOperationJournalPageListCount(OperationJournalDO operationJournal, Date operatorTimeStart, Date operatorTimeEnd) throws DataAccessException {
    	if (operationJournal == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("operationJournal", operationJournal);
        param.put("operatorTimeStart", operatorTimeStart);
        param.put("operatorTimeEnd", operatorTimeEnd);
	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-OPERATION-JOURNAL-QUERY-OPERATION-JOURNAL-PAGE-LIST-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}
