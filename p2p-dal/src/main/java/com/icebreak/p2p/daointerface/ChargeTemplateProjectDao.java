package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.ChargeTemplateProject;

public interface ChargeTemplateProjectDao {
	/**
	 * 添加
	 * @param chargeTemplateProject
	 */
	public void addChargeTemplateProject(ChargeTemplateProject chargeTemplateProject);
	/**
	 * 根据模版ID删除
	 * @param id
	 * @return
	 */
	public int deleteByTemplateId(long id);
	/**
	 * 根据收费项目ID删除
	 * @param id
	 * @return
	 */
	public int deleteByProjectId(long id);

}
