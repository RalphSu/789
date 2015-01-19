package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.UserLoginLogDao;
import com.icebreak.p2p.dataobject.UserLoginLog;

public class UserLoginLogDaoImpl extends SqlMapClientDaoSupport
	implements UserLoginLogDao {

	@Override
	public void insert(UserLoginLog userLoginLog) throws Exception{
		getSqlMapClientTemplate().insert("MS-USERlOGINLOG-INSERT", userLoginLog);
	}

	@Override
	public List<UserLoginLog> queryLog(Map<String, Object> condition) {
		
		List<UserLoginLog> lst = (List<UserLoginLog>)getSqlMapClientTemplate().
				queryForList("MS-USERlOGINLOG-SELECT", condition);
		return lst;
	}

}
