package com.icebreak.p2p.ws.userManage;

import javax.jws.WebService;

import com.icebreak.p2p.ws.userManage.order.UserRegisterDO;
import com.icebreak.p2p.ws.userManage.result.UserRegisterResult;

@WebService
public interface YrdUserRegisterFacade {
	public UserRegisterResult register(UserRegisterDO userRegisterDO);
}
