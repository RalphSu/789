package com.icebreak.p2p.login.impl;

import com.icebreak.p2p.daointerface.UserBaseInfoDAO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.login.LoginService;

public class LoginServiceImpl implements LoginService {
	
	private UserBaseInfoDAO userBaseInfoDAO = null;
	
	public void setUserBaseInfoDAO(UserBaseInfoDAO userBaseInfoDAO) {
		this.userBaseInfoDAO = userBaseInfoDAO;
	}

	@Override
	public UserBaseInfoDO login(String userName, String password) {
		return userBaseInfoDAO.login(userName, password);
	}
}
