package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.FundsRecordDao;
import com.icebreak.p2p.dataobject.FundsRecord;

public class FundsRecordDaoImpl extends SqlMapClientDaoSupport implements FundsRecordDao {

	@Override
	public void insert(FundsRecord fundsRecord) {
		getSqlMapClientTemplate().insert("MS-FUNDS_RECORD-INSERT", fundsRecord);
	}

	@Override
	public int update(FundsRecord fundsRecord) {
		return getSqlMapClientTemplate().update("MS-FUNDS_RECORD-UPDATE", fundsRecord);
	}

	@Override
	public long queryCountByCondition(Map<String, Object> condition) {

		return (Long)getSqlMapClientTemplate().queryForObject("MS-FUNDS_RECORD-QUERY-COUNT", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FundsRecord> queryFundsRecordsByCondition(
			Map<String, Object> condition) {

		return (List<FundsRecord>)getSqlMapClientTemplate().queryForList("MS-FUNDS_RECORD-QUERY-LIST", condition);
	}

}
