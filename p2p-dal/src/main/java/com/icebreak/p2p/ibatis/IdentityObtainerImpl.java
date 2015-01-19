package com.icebreak.p2p.ibatis;

import java.util.Random;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IdentityObtainer;
public class IdentityObtainerImpl extends SqlMapClientDaoSupport implements IdentityObtainer {
	/**
	 * 随机数
	 */
	private Random random = new Random();

	@Override
	public long getPrimaryKey() {
		return (Long)getSqlMapClientTemplate().queryForObject("COMMON-GETPRIMARYKEY");
	}

	@Override
	public String generateUserName() {
		return (String)getSqlMapClientTemplate().queryForObject("COMMON-GENERATEUSERNAME", random.nextInt(9) + 1);
	}
}
