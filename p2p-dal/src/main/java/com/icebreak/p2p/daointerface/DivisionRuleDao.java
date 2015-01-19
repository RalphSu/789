package com.icebreak.p2p.daointerface;

import java.util.List;

import com.icebreak.p2p.dataobject.DivisionRule;

public interface DivisionRuleDao {
	/**
	 * 添加分润规则
	 */
	public void addRule(DivisionRule divisionRule);
	/**
	 * 根据分润模版ID获取分润明细
	 * @param templateId
	 * @return
	 */
	public List<DivisionRule> getByTemplateId(long templateId);
	/**
     * 根据模版ID删除
     * @param templateId
     * @return
     */
	public int deleteByTemplateId(long templateId);
}
