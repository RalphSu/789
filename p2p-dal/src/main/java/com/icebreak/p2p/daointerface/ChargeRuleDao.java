package com.icebreak.p2p.daointerface;

import java.util.List;

import com.icebreak.p2p.dataobject.ChargeRule;

public interface ChargeRuleDao {
	/**
	 * 根据收费模版ID查询
	 * @param id
	 * @return
	 */
	public List<ChargeRule> getByTemplateId(long id);
	/**
	 * 根据收费模版ID删除
	 * @param id
	 * @return
	 */
	public int deleteByTemplateId(long id);
	/**
	 * 添加规则
	 * @param rule
	 */
	public void addChargeRule(ChargeRule rule);
	/**
	 * 根据收费模版模版ID和金额流量获取收费规则
	 * @param tempateId
	 * @param amout
	 * @return
	 */
	public ChargeRule getByAmount(long templateId, long amount);

}
