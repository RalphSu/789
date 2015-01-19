package com.icebreak.util.session;

public interface RemoteSessionService {
	/**
	 * 向远程session服务器存数据
	 * @param id session id
	 * @param key 键
	 * @param bytes 数据
	 */
	public void setAttribute(String id, String key, byte[] bytes, long timeout);
	/**
	 * 移除远程session 服务器中德数据
	 * @param id
	 * @param key
	 */
	public void removeAttribute(String id, String key);
	/**
	 * 从远程session 服务器中获取数据
	 * @param id
	 * @param key
	 * @return
	 */
	public byte[] getAttribute(String id, String key);
	/**
	 * 销毁
	 * @param id
	 */
	public void destroy(String id);

}
