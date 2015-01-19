package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.Role;

public interface RoleDao {
    /**
     * 根据角色ID获取角色
     * @param id
     * @return
     */
	public Role getByRoleId(int id);
	/**
	 * 根据角色代码获取角色
	 * @param code
	 * @return
	 */
	public Role getByRoleCode(String code);
	/**
	 * 添加角色s
	 * @param role
	 */
	public void add(Role role);
	/**
	 * 根据角色ID删除角色
	 * @param id
	 * @return
	 */
	public int deleteByRoleId(int id);
	/**
	 * 根据角色代码删除角色
	 * @param code
	 * @return
	 */
	public int deleteByRoleCode(String code);
	/**
	 * 修改s角色
	 * @param role
	 * @return
	 */
	public int update(Role role);
	/**
	 * 根据角色代码验证该角色是否已经存在
	 * @param code
	 * @return
	 */
	public int exists(String code);
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> getAllRoles(Map<String, Object> params);
	/**
	 * 获取所有角色条数
	 * @return
	 */
	public long getAllRolesCount();
	/**
	 * 获取子角色
	 * @param id
	 * @return
	 */
	public List<Role> getChildrenRoles(Map<String, Object> params);
	/**
	 * 获取子角色条数
	 * @param params
	 * @return
	 */
	public long getChildrenRolesCount(Map<String, Object> params);
	/**
	 * 根据用户ID获取角色
	 * @param id
	 * @return
	 */
	public List<Role> getRolesByUserId(Map<String, Object> params);
	/**
	 * 根据用户ID获取角色的条数
	 * @param params
	 * @return
	 */
	public long getRolesByUserIdCount(Map<String, Object> params);
	/**
	 * 根据用户ID获取可授予的角色
	 * @param params
	 * @return
	 */
	public List<Role> getGrantableRolesByUserId(Map<String, Object> params);
	/**
	 * 根据用户ID获取可授予的角色条数
	 * @param params
	 * @return
	 */
	public long getGrantableRolesByUserIdCount(Map<String, Object> params);
}
