package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private long id = 0;
	/**
	 * 用户名
	 */
	private String userName = null;
	
	public User(){}
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public User(long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "id:" + id + ", userName:" + userName;
	}
}
