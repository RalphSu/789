package com.icebreak.p2p.authority;

import java.util.List;

import com.icebreak.p2p.dataobject.Permission;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.User;
import com.icebreak.p2p.page.Pagination;

public interface AuthorityService {
	/**
	 * 添加用户
	 * @param roleId
	 * @return
	 */
	public User addUser(int... roleIds);
	
	/**
	 * 添加用户
	 * @param userName
	 * @param roleId
	 * @return
	 */
	public User addUser(String userName, int... roleIds);
	
	/**
	 * 给用户授予角色
	 */
	public void grantRolesToUser(long userId, int... roleIds);
	
	/**
	 * 给角色授予权限
	 * @param roleId
	 * @param permissionId
	 */
	public void grantPermisssionsToRole(int roleId, int... permissionIds);
	
	/**
	 * 根据用户获取角色
	 * @param id
	 * @return
	 */
	public Pagination<Role> getRolesByUserId(long userId, int start, int size);
	
	/**
	 * 根据父角色获取子角色
	 * @param roleId
	 * @param start
	 * @param sizer
	 * @return
	 */
	public Pagination<Role> getChildrenByRoleId(int roleId, int start, int size);
	
	/**
	 * 根据用户ID获取权限
	 * @param id
	 * @return
	 */
	public List<Permission> getPermissionsByUserId(long id);
	
	/**
	 * 添加角色
	 * @param role
	 */
	public void addRole(Role role);
	
	/**
	 * 添加权限
	 * @param permission
	 */
	public void addPermission(Permission permission);
	
	/**
	 * 修改权限信息
	 * @param permission
	 */
	public void modifyPermission(Permission permission);
	
	/**
	 * 分页查询所有权限信息
	 * @param start
	 * @param size
	 * @return
	 */
	public Pagination<Permission> getAllPermission(int start, int size);
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void deleteRoles(int... roleIds);
	
	/**
	 * 删除权限
	 * @param permissionIds
	 */
	public void deletePermissions(int... permissionIds);
	
	/**
	 * 根据角色ID获取权限
	 * @return
	 */
	public Pagination<Permission> getPermissionsByRoleId(int roleId, int start, int size);
	
	/**
	 * 根据角色ID获取可授予的权限
	 * @param roleId
	 * @param start
	 * @param size
	 * @return
	 */
	public Pagination<Permission> getGrantablePermissionsByRoleId(int roleId, int start, int size);
	
	/**
	 * 根据用户ID获取用户
	 * @param userId
	 * @return
	 */
	public User getUserByUserId(long userId);
	
	/**
	 * 根据用户名获取用户
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName);
	
	/**
	 * 是否存在用户名
	 * @param userName
	 * @return
	 */
	public boolean existUserName(String userName);
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public Pagination<Role> getAllRoles(int page, int size);
	
	/**
	 * 根据用户ID获取可授予的角色
	 * @param userId
	 * @param start
	 * @param size
	 * @return
	 */
	public Pagination<Role> getGrantableRolesByUserId(long userId, int start, int size);


	/**
	 * 获取用户的所有角色
	 * @param userId
	 * @return
	 */
	public List<Role> getRolesByUserId(long userId);

	/**
	 * 根据角色ID获取角色
	 * @param roleId
	 * @return
	 */
	public Role getRoleByRoleId(int roleId);
	
	/**
	 * 根据角色代码获取角色
	 * @param roleName
	 * @return
	 */
	public Role getRoleByRoleCode(String roleCode);
	
	/**
	 * 修改角色
	 * @param role
	 */
	public void modifyRole(Role role);
	
	/**
	 * 验证是否存在角色代码
	 * @param roleCode
	 * @return
	 */
	public boolean existRoleCode(String roleCode);
	
	/**
	 * 验证是否存在权限代码
	 * @param code
	 * @return
	 */
	public boolean existPermisson(String code);
	
	/**
	 * 给角色解除权限
	 * @param roleId
	 * @param permissionIds
	 */
	public void removePermissions(int roleId, int... permissionIds);
	
	/**
	 * 给用户解除角色
	 * @param userId
	 * @param roleIds
	 */
	public void unbindRoles(long userId, int... roleIds);
	
	/**
	 * 根据ID获取权限
	 * @param id
	 * @return
	 */
	public Permission getPermissionById(int permissionId);
	
	/**
	 * 更新角色权限（先删除，再添加）
	 * @param roleId
	 * @param permissionId
	 */
	public void replacePermisssionsToRole(int roleId, int... permissionIds);
}
