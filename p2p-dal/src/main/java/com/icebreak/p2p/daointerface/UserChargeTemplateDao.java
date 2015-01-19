package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.UserChargeTemplate;

public interface UserChargeTemplateDao {
	/**
	 * 添加
	 * @param userChargeTemplate
	 */
	public void addUserChargeTemplate(UserChargeTemplate userChargeTemplate);
	/**
	 * 根据用户ID删除
	 * @param id
	 * @return
	 */
	public int deleteByUserId(long id);
	/**
	 * 根据模版ID删除
	 * @param id
	 * @return
	 */
	public int deleteByTemplateId(long id);

}
