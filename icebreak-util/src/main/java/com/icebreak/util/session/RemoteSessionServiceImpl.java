package com.icebreak.util.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteSessionServiceImpl implements RemoteSessionService {
	/**
	 * 用于存储session
	 */
	protected static Map<String, RemoteSessionLocal> sessionLocals = new ConcurrentHashMap<String, RemoteSessionLocal>();

	@Override
	public void setAttribute(String id, String key, byte[] bytes, long timeout) {
		RemoteSessionLocal remoteSessionLocal = getSessionLocal(id, timeout);
		remoteSessionLocal.getSession().put(key, bytes);
		remoteSessionLocal.setLastAccessTime(System.currentTimeMillis());
	}

	@Override
	public void removeAttribute(String id, String key) {
		RemoteSessionLocal remoteSessionLocal = sessionLocals.get(id);
		if(remoteSessionLocal != null){
			remoteSessionLocal.getSession().remove(key);
			remoteSessionLocal.setLastAccessTime(System.currentTimeMillis());
		}
	}

	@Override
	public byte[] getAttribute(String id, String key) {
		RemoteSessionLocal remoteSessionLocal = sessionLocals.get(id);
		if(remoteSessionLocal != null){
			remoteSessionLocal.setLastAccessTime(System.currentTimeMillis());
			return remoteSessionLocal.getSession().get(key);
		}
		return null;
	}

	@Override
	public void destroy(String id) {
		sessionLocals.remove(id);
	}
	
	/**
	 * 远程session局部变量
	 * @param id
	 * @param timeout
	 * @return
	 */
	private RemoteSessionLocal getSessionLocal(String id, long timeout){
		RemoteSessionLocal sessionLocal = sessionLocals.get(id);
		if(sessionLocal == null){
			sessionLocal = new RemoteSessionLocal(timeout);
			sessionLocals.put(id, sessionLocal);
		}
		return sessionLocal;
	}

}
