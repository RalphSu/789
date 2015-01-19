package com.icebreak.p2p.ws.userManage.impl;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.ws.security.base.WSServiceBase;
import com.icebreak.p2p.ws.userManage.YrdUserRegisterFacade;
import com.icebreak.p2p.ws.userManage.order.UserRegisterDO;
import com.icebreak.p2p.ws.userManage.result.UserRegisterResult;

public class YrdUserRegisterFacadeImpl extends WSServiceBase implements YrdUserRegisterFacade {
	
	@Override
	public UserRegisterResult register(UserRegisterDO userRegisterDO) {
		UserBaseInfoDO userBaseInfoDO = new UserBaseInfoDO();
		PersonalInfoDO personalInfoDO = new PersonalInfoDO();
		
		userBaseInfoDO.setAccountId(userRegisterDO.getAccountId());
		userBaseInfoDO.setAccountName(userRegisterDO.getAccountName());
		userBaseInfoDO.setRealName(userRegisterDO.getRealName());
		userBaseInfoDO.setUserName(userRegisterDO.getUserName());
		userBaseInfoDO.setMail(userRegisterDO.getMail());
		userBaseInfoDO.setMobile(userRegisterDO.getMobile());
		userBaseInfoDO.setLogPassword(userRegisterDO.getLogPassword());
		userBaseInfoDO.setPayPassword(userRegisterDO.getPayPassword());
		
		personalInfoDO.setRealName(userRegisterDO.getRealName());
		personalInfoDO.setCertNo(userRegisterDO.getCertNo());
		personalInfoDO.setCertBackPath(userRegisterDO.getCertBackPath());
		personalInfoDO.setCertFrontPath(userRegisterDO.getCertFrontPath());
		personalInfoDO.setBusinessPeriod(userRegisterDO.getBusinessPeriod());
		personalInfoDO.setReferees(userRegisterDO.getReferess());
		JSONObject jsonobj = new JSONObject();
		UserRegisterResult result = new UserRegisterResult();
		try {
		} catch (Exception e) {
			logger.error("托管机构用户注册异常", e);
			result.setCode("0");
			result.setMessage("托管机构用户注册异常");
		}
		return result;
	}
	
}
