package com.icebreak.p2p.user;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.user.UserAddressInfoDO;
import com.icebreak.p2p.user.result.UserAddressReturnEnum;

public interface UserAddressService {
	public UserAddressReturnEnum insertUserAddress(UserAddressInfoDO userAddress);
	public List<UserAddressInfoDO> queryUserAddress(Map<String,Object> params);
}
