package com.icebreak.p2p.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.annotation.Transactional;

import com.icebreak.p2p.daointerface.UserDao;
import com.icebreak.p2p.daointerface.UserRoleDao;
import com.icebreak.p2p.dataobject.User;
import com.icebreak.p2p.dataobject.UserPasswordExtend;
import com.icebreak.p2p.dataobject.UserRole;
import com.icebreak.p2p.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {
	protected final Logger					logger		= LoggerFactory.getLogger(this.getClass());
	private static Map<String, List<User>>	users		= new ConcurrentHashMap<String, List<User>>();
	
	private UserDao							userDao;
	
	private UserRoleDao						userRoleDao	= null;
	
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public List<User> getUserByStatus(String status) {
		if (users.containsKey(status)) {
			return users.get(status);
		} else {
			List<User> list = userDao.getByStatus(status);
			users.put(status, list);
			return list;
		}
	}
	
	@Override
	public void refresh() {
		synchronized (UserServiceImpl.class) {
			users.clear();
		}
	}
	
	@Override
	public List<UserRole> getUserRoles(String[] userStatuses, String[] roleCodes) {
		return userRoleDao.getUserRoles(userStatuses, roleCodes);
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int addUserPasswordExtend(UserPasswordExtend userPasswordExten) {
		try {
			userDao.addUserPasswordExtend(userPasswordExten);
		} catch (Exception e) {
			logger.error("添加用户addUserPasswordExtend", e);
			return 0;
		}
		
		return 1;
		
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int updateUserPasswordExtend(UserPasswordExtend userPasswordExten) {
		return userDao.updateUserPasswordExtend(userPasswordExten);
	}
	
	@Override
	public long validationUserPassword(String password, String userBaseId, String passwordType) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("password", password);
		conditions.put("userBaseId", userBaseId);
		conditions.put("passwordType", passwordType);
		return userDao.validationUserPassword(conditions);
	}
	
	@Override
	public long countUserPwdExistByType(String userBaseId, String passwordType) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userBaseId", userBaseId);
		conditions.put("passwordType", passwordType);
		return userDao.countUserPwdExistByCondition(conditions);
	}
	
	@Override
	public List<UserPasswordExtend> getUserEXPwdBYconditions(Map<String, Object> conditions) {
		return userDao.getUserEXPwdBYconditions(conditions);
	}
}
