package com.icebreak.p2p.daointerface;
public interface IdentityObtainer {
	/**
	 * 获取主键
	 * @return
	 */
	public long getPrimaryKey();
	/**
	 * 生成用户名
	 * @return
	 */
	public String generateUserName();

}
