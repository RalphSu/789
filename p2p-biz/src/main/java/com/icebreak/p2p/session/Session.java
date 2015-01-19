package com.icebreak.p2p.session;

import com.icebreak.p2p.session.common.Constant;
import com.icebreak.util.session.ByteObject;
import com.icebreak.util.session.RemoteSessionService;
import com.icebreak.util.session.RemoteSessionServiceImpl;

public class Session {
	/**
	 * 远程session服务
	 */
	private static RemoteSessionService remoteSessionService = null;

	/**
	 * 获取远程session服务
	 * @return
	 */
	private static RemoteSessionService getRemoteSession(){
		if(remoteSessionService == null){
			synchronized (Session.class) {
				//remoteSessionService = RmcServiceFactory.getInstance().getRmcService(Constant.host, Constant.port, Constant.name, RemoteSessionService.class);
				//remoteSessionService = RmcServiceFactory.getInstance().getRmcService("127.0.0.1", 8082, "remoteSessionService", RemoteSessionService.class);
				remoteSessionService = new RemoteSessionServiceImpl();
			}
		}
		return remoteSessionService;
	}
	
	/**
	 * 将数据存入session
	 * @param key
	 * @param value
	 */
	public static void setAttribute(String key, Object value){
		getRemoteSession().setAttribute(Constant.getSessionId(), key, ByteObject.object2Bytes(value), Constant.timeout);
	}
	
	/**
	 * 从session中获取数据
	 * @param key
	 * @return
	 */
	public static Object getAttribute(String key){
		byte[] bytes = getRemoteSession().getAttribute(Constant.getSessionId(), key);
		return ByteObject.bytes2Object(bytes);
	}
	
	/**
	 * 从session中移除数据
	 * @param key
	 */
	public static void removeAttribute(String key){
		getRemoteSession().removeAttribute(Constant.getSessionId(), key);
	}
	
	/**
	 * 销毁
	 */
	public static void destroy(){
		getRemoteSession().destroy(Constant.getSessionId());
	}
	
	/**
	 * 获取session id
	 * @return
	 */
	public static String getId(){
		return Constant.getSessionId();
	}

}
