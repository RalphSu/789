package com.icebreak.p2p.dal.daointerface;


import com.icebreak.p2p.dal.dataobject.OperationJournalDO;

import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

 @SuppressWarnings("rawtypes") 
public interface OperationJournalDAO {
	 
    public long insert(OperationJournalDO operationJournal) throws DataAccessException;

    public int update(OperationJournalDO operationJournal) throws DataAccessException;

    public OperationJournalDO findByIdentity(long identity) throws DataAccessException;

    public List<OperationJournalDO> queryOperationJournalPageList(OperationJournalDO operationJournal, Date operatorTimeStart, Date operatorTimeEnd, long limitStart, long pageSize) throws DataAccessException;

    public long queryOperationJournalPageListCount(OperationJournalDO operationJournal, Date operatorTimeStart, Date operatorTimeEnd) throws DataAccessException;

}
