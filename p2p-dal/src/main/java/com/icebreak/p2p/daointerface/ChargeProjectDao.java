package com.icebreak.p2p.daointerface;

import java.util.List;

import com.icebreak.p2p.dataobject.ChargeProject;


public interface ChargeProjectDao {
    /**
     * 根据方式查询
     * @param method
     * @return
     */
	public List<ChargeProject> getByMethods(String... methods);
	/**
	 * 添加
	 * @param project
	 */
	public void addChargeProject(ChargeProject project);
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(long id);
	/**
	 * 根据收费模版ID查询
	 * @param templateId
	 * @return
	 */
	public List<ChargeProject> getByTemplateId(long templateId);
	/**
	 * 根据模版ID和状态查询
	 * @param templateId
	 * @param status
	 * @return
	 */
	public List<ChargeProject> getByTemplateIdAndStatus(long templateId, int status);
}
