package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.BankInfoDao;
import com.icebreak.p2p.dataobject.BankInfo;

public class BankInfoDaoImpl  extends SqlMapClientDaoSupport implements BankInfoDao {

	@Override
	public void insert(BankInfo bank) throws DataAccessException{

		getSqlMapClientTemplate().insert("MS-BANK-INSERT", bank);

	}

	@Override
	public int update(BankInfo bank) {

		return getSqlMapClientTemplate().update("MS-BANK-UPDATE", bank);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankInfo> getBankListByCondtions(Map<String, Object> conditions) {

		return (List<BankInfo>)getSqlMapClientTemplate().queryForList("MS-BANK-SELECTLIST", conditions);
	}

	@Override
	public long getBankCountByCondtions(Map<String, Object> conditions) {

		return (Long)getSqlMapClientTemplate().queryForObject("MS-BANK-SELECTCOUNT", conditions);
	}

	@Override
	public int deleteById(long bankId) {

		return getSqlMapClientTemplate().delete("MS-BANK-DELETE", bankId);
	}

}
