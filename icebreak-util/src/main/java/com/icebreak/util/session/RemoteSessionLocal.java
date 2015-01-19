package com.icebreak.util.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RemoteSessionLocal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 超时时间，若小于等于0则永远不会超时，默认永远不会超时
	 */
	private long timeout = 0;
	/**
	 * 创建时间
	 */
	private long createTime = 0;
	/**
	 * 最后一次访问时间
	 */
	private long lastAccessTime = 0;
	/**
	 * session中德数据
	 */
	private Map<String, byte[]> session = null;

	public RemoteSessionLocal(long timeout) {
		this.timeout = timeout;
		createTime = System.currentTimeMillis();
		lastAccessTime = createTime;
		session = new HashMap<String, byte[]>();
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Map<String, byte[]> getSession() {
		return session;
	}

	public void setSession(Map<String, byte[]> session) {
		this.session = session;
	}

	/**
	 * 是否超时
	 * @return
	 */
	public boolean isTimeout(){
		if(timeout < 1){
			return false;
		}
		return System.currentTimeMillis() - lastAccessTime >= timeout;
	}
}
