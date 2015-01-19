package com.icebreak.p2p.session;

import java.util.List;

import com.icebreak.p2p.authority.impl.AuthorityServiceImpl;
import com.icebreak.p2p.dataobject.Permission;

public class SessionLocalManager {
	/**
	 * session key
	 */
	private final static String SESSION_KEY = "session_key";
	
	/**
	 * 设置当前session局部数据
	 * @param local
	 */
	public static void setSessionLocal(SessionLocal local){
		Session.setAttribute(SESSION_KEY, local);
	}
	
	/**
	 * 获取session局部数据
	 * @return
	 */
	public static SessionLocal getSessionLocal(){
		try {
			return (SessionLocal)Session.getAttribute(SESSION_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取当前用户的权限，没有即获取公共的权限
	 * @return
	 */
	public static List<Permission> getPermissions(){
		SessionLocal local = getSessionLocal();
		if(local == null){
			return AuthorityServiceImpl.getPulicPermissions();
		}
		return local.getPermissions();
	}
	
	/**
	 * 销毁session
	 */
	public static void destroy(){
		Session.destroy();
	}

}
