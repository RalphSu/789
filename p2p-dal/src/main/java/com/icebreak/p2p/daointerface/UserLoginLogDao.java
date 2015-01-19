package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.UserLoginLog;

public interface UserLoginLogDao {
	/**
	 * 插入一条用户登录记录
	 * @param userLoginLog
	 */
	public void insert(UserLoginLog userLoginLog)throws Exception;
	/**
	 * 查询用户登录记录
	 * @param condition
	 * @return
	 */
	public List<UserLoginLog> queryLog(Map<String,Object> condition);

}
