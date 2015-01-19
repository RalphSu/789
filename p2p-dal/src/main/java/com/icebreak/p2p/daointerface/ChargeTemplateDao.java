package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.ChargeTemplate;

public interface ChargeTemplateDao {
    /**
     * 添加收费模版
     * @param template
     */
	public void addChargeTemplate(ChargeTemplate template);
	/**
	 * 根据模版查询
	 * @param id
	 * @return
	 */
	public ChargeTemplate getById(long id);
	/**
	 * 根据属性查询
	 * @param params
	 * @return
	 */
	public List<ChargeTemplate> getByProperties(Map<String, Object> params);
	/**
	 * 查询条数
	 * @param params
	 * @return
	 */
	public long getCountByProperties(Map<String, Object> params);
	/**
	 * 修改
	 * @param template
	 * @return
	 */
	public int modify(ChargeTemplate template);
	/**
	 * 根据用户ID获取收费模版，若返回空说明该用户不会被收费
	 * @param id
	 * @return
	 */
	public ChargeTemplate getChargeTemplateByUserId(long userId, int roleId);
}
