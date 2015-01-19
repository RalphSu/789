package com.icebreak.p2p.authority.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.PermissionDao;
import com.icebreak.p2p.daointerface.RoleDao;
import com.icebreak.p2p.daointerface.RolePermissonDao;
import com.icebreak.p2p.daointerface.UserDao;
import com.icebreak.p2p.daointerface.UserRoleDao;
import com.icebreak.p2p.dataobject.Permission;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.RolePermission;
import com.icebreak.p2p.dataobject.User;
import com.icebreak.p2p.dataobject.UserRole;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;

import org.springframework.transaction.annotation.Transactional;


public class AuthorityServiceImpl implements AuthorityService {
	/**
	 * 缓存所有的权限
	 */
	private static List<Permission> allPermission = null;
	/**
	 * 缓存公共的权限
	 */
	private static List<Permission> publicPermission = null;
	/**
	 * 是否缓存,默认不缓存
	 */
	private boolean cache = false;
	
	private IdentityObtainer identityObtainer;
	
	private UserDao userDao;
	
	private UserRoleDao userRoleDao;
	
	private RolePermissonDao rolePermissonDao;
	
	private RoleDao roleDao;
	
	private PermissionDao permissionDao;
	
	public void setCache(boolean cache) {
		this.cache = cache;
	}
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}
	
	public void setRolePermissonDao(RolePermissonDao rolePermissonDao) {
		this.rolePermissonDao = rolePermissonDao;
	}
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	
	/**
	 * 获取所有权限
	 * @return
	 */
	public static List<Permission> getAllPermissions() {
		return allPermission;
	}
	
	/**
	 * 获取公共的权限
	 * @return
	 */
	public static List<Permission> getPulicPermissions() {
		return publicPermission;
	}
	
	/**
	 * 初始化缓存
	 */
	public void init() {
		if (cache) {
			cacheAllPermission();
			cachePublicPermission();
		}
	}
	
	/**
	 * 缓存所有权限
	 */
	private void cacheAllPermission() {
		synchronized (AuthorityServiceImpl.class) {
			allPermission = PermissionSorter.sort(permissionDao
				.getAllPermissions(new HashMap<String, Object>()));
		}
	}
	
	/**
	 * 缓存公共权限
	 */
	private void cachePublicPermission() {
		synchronized (AuthorityService.class) {
			publicPermission = PermissionSorter.sort(permissionDao
				.getPermissionsByRoles(getAllChildrenByRoleId(-1)));
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public User addUser(int... roleIds) {
		User user = new User();
		String userName = identityObtainer.generateUserName();
		while (userDao.exists(userName) > 0) {
			userName = identityObtainer.generateUserName();
		}
		user.setUserName(userName);
		userDao.add(user);
		grantRolesToUser(user.getId(), roleIds);
		return user;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public User addUser(String userName, int... roleIds) {
		User user = new User();
		user.setUserName(userName);
		userDao.add(user);
		grantRolesToUser(user.getId(), roleIds);
		return user;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void grantRolesToUser(long userId, int... roleIds) {
		for (int i : roleIds) {
			userRoleDao.add(new UserRole(userId, i));
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void grantPermisssionsToRole(int roleId, int... permissionIds) {
		for (int i : permissionIds) {
			rolePermissonDao.add(new RolePermission(roleId, i));
		}
		cachePublicPermission();
	}
	
	@Override
	public Pagination<Role> getRolesByUserId(long userId, int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("size", size);
		params.put("userId", userId);
		return new Pagination<Role>(start, roleDao.getRolesByUserIdCount(params), size,
			roleDao.getRolesByUserId(params));
	}

	@Override
	public List<Role> getRolesByUserId(long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return roleDao.getRolesByUserId(params);
	}
	
	@Override
	public Pagination<Role> getGrantableRolesByUserId(long userId, int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("start", start);
		params.put("size", size);
		return new Pagination<Role>(start, roleDao.getGrantableRolesByUserIdCount(params), size,
			roleDao.getGrantableRolesByUserId(params));
	}
	
	@Override
	public Pagination<Role> getChildrenByRoleId(int roleId, int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("start", start);
		params.put("size", size);
		return new Pagination<Role>(start, roleDao.getChildrenRolesCount(params), size,
			roleDao.getChildrenRoles(params));
	}
	
	@Override
	public List<Permission> getPermissionsByUserId(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		List<Role> roles = roleDao.getRolesByUserId(params);
		List<Integer> ids = new ArrayList<Integer>();
		for (Role role : roles) {
			ids.addAll(getAllChildrenByRoleId(role.getId()));
		}
		return PermissionSorter.sort(permissionDao.getPermissionsByRoles(ids));
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addRole(Role role) {
		roleDao.add(role);
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addPermission(Permission permission) {
		permissionDao.add(permission);
		cacheAllPermission();
	}
	
	@Override
	public Pagination<Permission> getPermissionsByRoleId(int roleId, int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("start", start);
		params.put("size", size);
		return new Pagination<Permission>(start, permissionDao.getPermissionsByRoleCount(params),
			size, permissionDao.getPermissionsByRole(params));
	}
	
	/**
	 * 递归获取所有子角色ID
	 * @param id
	 * @return
	 */
	private List<Integer> getAllChildrenByRoleId(int id) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", id);
		List<Role> roles = roleDao.getChildrenRoles(params);
		if (roles != null && roles.size() > 0) {
			for (Role role : roles) {
				ids.addAll(getAllChildrenByRoleId(role.getId()));
			}
		}
		return ids;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void deleteRoles(int... roleIds) {
		for (int roleId : roleIds) {
            //系统预置角色不能删除
            boolean sysRole = SysUserRoleEnum.isSysRoleByValue(roleId);
            if(sysRole){
                continue;
            }
			userRoleDao.deleteByRoleId(roleId);
			rolePermissonDao.deleteByRoleId(roleId);
			roleDao.deleteByRoleId(roleId);
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void deletePermissions(int... permissionIds) {
		for (int permissionId : permissionIds) {
			rolePermissonDao.deleteByPermissionId(permissionId);
			permissionDao.deleteByPermissionId(permissionId);
		}
		cachePublicPermission();
		cacheAllPermission();
	}
	
	@Override
	public User getUserByUserId(long userId) {
		return userDao.getByUserId(userId);
	}
	
	@Override
	public User getUserByUserName(String userName) {
		return userDao.getByUserName(userName);
	}
	
	@Override
	public boolean existUserName(String userName) {
		return userDao.exists(userName) > 0;
	}
	
	@Override
	public Role getRoleByRoleId(int roleId) {
		return roleDao.getByRoleId(roleId);
	}
	
	@Override
	public Role getRoleByRoleCode(String roleCode) {
		return roleDao.getByRoleCode(roleCode);
	}
	
	@Override
	public boolean existRoleCode(String roleCode) {
		return roleDao.exists(roleCode) > 0;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void modifyPermission(Permission permission) {
		permissionDao.update(permission);
		cachePublicPermission();
		cacheAllPermission();
	}
	
	@Override
	public boolean existPermisson(String code) {
		return permissionDao.exists(code) > 0;
	}
	
	@Override
	public Pagination<Role> getAllRoles(int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("size", size);
		return new Pagination<Role>(start, roleDao.getAllRolesCount(), size,
			roleDao.getAllRoles(params));
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void modifyRole(Role role) {
		roleDao.update(role);
	}
	
	@Override
	public Pagination<Permission> getAllPermission(int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("size", size);
		return new Pagination<Permission>(start, permissionDao.getAllPermissionsCount(), size,
			permissionDao.getAllPermissions(params));
	}
	
	@Override
	public Pagination<Permission> getGrantablePermissionsByRoleId(int roleId, int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("start", start);
		params.put("size", size);
		return new Pagination<Permission>(start,
			permissionDao.getGrantablePermissionsByRoleIdCount(params), size,
			permissionDao.getGrantablePermissionsByRoleId(params));
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void removePermissions(int roleId, int... permissionIds) {
		for (int i : permissionIds) {
			rolePermissonDao.delete(roleId, i);
		}
		cachePublicPermission();
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void unbindRoles(long userId, int... roleIds) {
		for (int i : roleIds) {
			userRoleDao.delete(userId, i);
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public Permission getPermissionById(int permissionId) {
		
		return permissionDao.getByPermissionId(permissionId);
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void replacePermisssionsToRole(int roleId, int... permissionIds) {
		rolePermissonDao.deleteByRoleId(roleId);
		for (int i : permissionIds) {
			rolePermissonDao.add(new RolePermission(roleId, i));
		}
		cachePublicPermission();
	}
}
