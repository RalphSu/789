package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.UserAddressDao;
import com.icebreak.p2p.dataobject.user.UserAddressInfoDO;

public class UserAddessDaoImpl extends SqlMapClientDaoSupport implements UserAddressDao {

	@Override
	public void insertUserAddress(UserAddressInfoDO userAddress) {
		getSqlMapClientTemplate().insert("USERADDRESS-INSERT", userAddress);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAddressInfoDO> queryUserAddress(Map<String, Object> params) {
		return (List<UserAddressInfoDO>)getSqlMapClientTemplate().queryForList("USERADDRESS-QUERYUSERADDRESS", params);
	}
}
