package com.icebreak.p2p.user;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;

public interface RegisterService {
	/**
	 * 个人用户注册
	 * @param session
	 * @param institution
	 * @param personalInfo
	 * @param userBaseInfo
	 * @param token
	 * @return
	 */
	public JSONObject personalRegister(HttpSession session, PersonalInfoDO personalInfo,
			UserBaseInfoDO userBaseInfo,String token)throws Exception;
	/**
	 * 机构用户注册
	 * @param session
	 * @param institution
	 * @param userBaseInfo
	 * @param token
	 * @return
	 */
	public JSONObject institutionsRegister(HttpSession session,InstitutionsInfoDO institution,
			UserBaseInfoDO userBaseInfo,String token);

}
