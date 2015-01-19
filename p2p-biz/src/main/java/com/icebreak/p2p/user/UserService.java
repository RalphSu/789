package com.icebreak.p2p.user;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.User;
import com.icebreak.p2p.dataobject.UserPasswordExtend;
import com.icebreak.p2p.dataobject.UserRole;

public interface UserService {
    /**
     * 根据状态获取用户
     * @param status
     * @return
     */
	public List<User> getUserByStatus(String status);
	/**
	 * 同步缓存
	 */
	public void refresh();
	/**
	 * 获取用户角色
	 * @param userStatuses
	 * @param roleCodes
	 * @return
	 */
	public List<UserRole> getUserRoles(String[] userStatuses, String[] roleCodes);
	/**
	 * 新增用户密码扩展
	 * @param userPasswordExten
	 */
	public int addUserPasswordExtend(UserPasswordExtend userPasswordExten);
	/**
	 * 更新用户密码扩展
	 * 
	 * @param userPasswordExten
	 * @return
	 */
	public int updateUserPasswordExtend(UserPasswordExtend userPasswordExten);
	/**
	 * 根据用户及密码类型验证密码
	 * @param password
	 * @param userBaseid
	 * @param passwordType
	 * @return
	 */
	public long validationUserPassword(String password,String userBaseid,String passwordType);
	/**
	 * 根据用户baseId和密码类型查询是否设置密码
	 * @param userBaseId
	 * @param passwordType
	 * @return
	 */
	public long countUserPwdExistByType(String userBaseId, String passwordType);
	/**
	 * 根据用户信息类型查询密码记录
	 * @return
	 */
	public List<UserPasswordExtend> getUserEXPwdBYconditions(Map<String, Object> conditions);
}
