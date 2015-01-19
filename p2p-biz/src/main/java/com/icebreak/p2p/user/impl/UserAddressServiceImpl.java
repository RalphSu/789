package com.icebreak.p2p.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.daointerface.UserAddressDao;
import com.icebreak.p2p.dataobject.user.UserAddressInfoDO;
import com.icebreak.p2p.user.UserAddressService;
import com.icebreak.p2p.user.result.UserAddressReturnEnum;
@Service
public class UserAddressServiceImpl implements  UserAddressService{
	@Autowired 
	private UserAddressDao userAddressDao;

	@Override
	public UserAddressReturnEnum insertUserAddress(UserAddressInfoDO userAddress) {
		return null;
	}

	@Override
	public List<UserAddressInfoDO> queryUserAddress(Map<String, Object> params) {
		return null;
	}
}
