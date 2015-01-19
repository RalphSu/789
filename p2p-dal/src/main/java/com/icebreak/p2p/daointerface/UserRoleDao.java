package com.icebreak.p2p.daointerface;

import java.util.List;

import com.icebreak.p2p.dataobject.UserRole;

public interface UserRoleDao {
	/**
	 * 添加一条记录
	 * @param userRole
	 */
	public void add(UserRole userRole);
	/**
	 * 删除
	 * @param userId
	 * @param roleId
	 */
	public int delete(long userId, int roleId);
	/**
	 * 根据用户ID删除
	 * @param userId
	 * @return
	 */
	public int deleteByUserId(long userId);
	/**
	 * 根据角色ID删除
	 * @param roleId
	 * @return
	 */
	public int deleteByRoleId(int roleId);
	/**
	 * 
	 * @return
	 */
	public List<UserRole> getUserRoles(String[] statuses, String[] roleCodes);

}
