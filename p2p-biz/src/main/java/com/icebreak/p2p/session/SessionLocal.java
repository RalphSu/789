package com.icebreak.p2p.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icebreak.p2p.common.info.SessionMobileSend;
import com.icebreak.p2p.dataobject.Permission;

public class SessionLocal implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	/**
	 * 权限列表
	 */
	private List<Permission>	permissions			= null;
	/**
	 * 用户ID
	 */
	private Long				userId				= null;
	
	private String				userName			= null;

	//用户角色代码串，用|线分割，例如"system|operator|investor"
	private String				roleCodes			= null;
	
	private String				userBaseId			= null;
	
	private String				accountId			= null;
	
	private String				accountName			= null;
	
	private String				realName			= null;
	
	private SessionMobileSend	sessionMobileSend	= null;
	private Map<String, Object>	attibuteMap			= new HashMap<String, Object>();
    private String remoteAddr = null;;

	public void addAttibute(String key, Object object) {
		attibuteMap.put(key, object);
	}

	public Object getAttibute(String key) {
		return attibuteMap.get(key);
	}

	public Object removeObject(String key) {
		return attibuteMap.remove(key);
	}

	public void setAttibuteMap(Map<String, Object> attibuteMap) {
		this.attibuteMap = attibuteMap;
	}

	public Map<String, Object> getAttibuteMap() {
		return this.attibuteMap;
	}


	
	public SessionLocal() {
	}
	
	public SessionLocal(List<Permission> permissions, Long userId, String userBaseId, String roleCodes,
						String accountId, String accountName, String realName, String userName,String remoteAddr) {
		this.permissions = permissions;
		this.userId = userId;
		this.userBaseId = userBaseId;
		this.accountId = accountId;
		this.accountName = accountName;
		this.realName = realName;
		this.userName = userName;
		this.roleCodes = roleCodes;
        this.setRemoteAddr(remoteAddr);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}
	
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserBaseId() {
		return userBaseId;
	}
	
	public void setUserBaseId(String userBaseId) {
		this.userBaseId = userBaseId;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(String roleCodes) {
		this.roleCodes = roleCodes;
	}

	public SessionMobileSend getSessionMobileSend() {
		return sessionMobileSend;
	}
	
	public void setSessionMobileSend(SessionMobileSend sessionMobileSend) {
		this.sessionMobileSend = sessionMobileSend;
	}

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SessionLocal [permissions=");
		builder.append(permissions);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", roleCodes=");
		builder.append(roleCodes);
		builder.append(", userBaseId=");
		builder.append(userBaseId);
		builder.append(", accountId=");
		builder.append(accountId);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", realName=");
		builder.append(realName);
		builder.append(", sessionMobileSend=");
		builder.append(sessionMobileSend);
		builder.append("]");
		return builder.toString();
	}
	
}
