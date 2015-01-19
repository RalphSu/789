package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.user.UserAddressInfoDO;


public interface UserAddressDao {
	public void insertUserAddress(UserAddressInfoDO userAddress);
	public List<UserAddressInfoDO> queryUserAddress(Map<String, Object> params);
}
