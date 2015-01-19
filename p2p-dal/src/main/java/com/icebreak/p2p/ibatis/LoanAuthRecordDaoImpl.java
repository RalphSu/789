package com.icebreak.p2p.ibatis;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.LoanAuthRecordDao;
import com.icebreak.p2p.dataobject.LoanAuthRecord;
public class LoanAuthRecordDaoImpl extends SqlMapClientDaoSupport implements LoanAuthRecordDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public void addLoanAuthRecord(LoanAuthRecord record)
			throws DataAccessException {

		getSqlMapClientTemplate().insert("LOAN-AUTH-RECORD-INSERT", record);
	}

	@Override
	public long countLoanAuthRecordByCondition(Map<String, Object> conditions) {
		return (Long)getSqlMapClientTemplate().queryForObject("LOAN-AUTH-RECORD-COUNTBYCONDITIONS", conditions);
	}
	
}
