package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.UserDao;
import com.icebreak.p2p.dataobject.User;
import com.icebreak.p2p.dataobject.UserPasswordExtend;
public class UserDaoImpl extends SqlMapClientDaoSupport implements UserDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public User getByUserId(long id) {
		return (User)getSqlMapClientTemplate().queryForObject("USER-GETBYUSERID", id);
	}
    
	@Override
	public User getByUserName(String userName) {
		return (User)getSqlMapClientTemplate().queryForObject("USER-GETBYUSERNAME", userName);
	}

	@Override
	public void add(User user) {
		getSqlMapClientTemplate().insert("USER-INSERT", user);
		user.setId(identityObtainer.getPrimaryKey());
	}

	@Override
	public int deleteByUserId(long id) {
		return getSqlMapClientTemplate().delete("USER-DELETEBYUSERID", id);
	}

	@Override
	public int deleteByUserName(String userName) {
		return getSqlMapClientTemplate().delete("USER-DELETEBYUSERNAME", userName);
	}

	@Override
	public int exists(String userName) {
		return (Integer)getSqlMapClientTemplate().queryForObject("USER-EXISTS", userName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getByStatus(String status) {
		return (List<User>)getSqlMapClientTemplate().queryForList("USER-GETBYSTATUS", status);
	}

	@Override
	public void addUserPasswordExtend(UserPasswordExtend userPasswordExten)
			throws DataAccessException {

		getSqlMapClientTemplate().insert("USER-PASSWORD-EXTEND-INSERT", userPasswordExten);
		
	}

	@Override
	public int updateUserPasswordExtend(UserPasswordExtend userPasswordExten)
			throws DataAccessException {

		return getSqlMapClientTemplate().update("USER-PASSWORD-EXTEND-UPDATE", userPasswordExten);
	}

	@Override
	public long validationUserPassword(Map<String, Object> conditions)
			throws DataAccessException {

		return (Long)getSqlMapClientTemplate().queryForObject("USER-PASSWORD-EXTEND_PASSWORD_CHECK", conditions);
	}

	@Override
	public long countUserPwdExistByCondition(Map<String, Object> conditions) {
		return (Long)getSqlMapClientTemplate().queryForObject("USER-PASSWORD-EXTEND-CHECK-PASSWORD-EXIST", conditions);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPasswordExtend> getUserEXPwdBYconditions(Map<String, Object> conditions) {
		return (List<UserPasswordExtend>)getSqlMapClientTemplate().queryForList("USER-PASSWORD-EXTEND-BYCONDITIONS", conditions);
	}
	
}
