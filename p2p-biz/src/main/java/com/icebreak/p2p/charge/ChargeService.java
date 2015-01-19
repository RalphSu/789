package com.icebreak.p2p.charge;

import java.util.Date;
import java.util.List;

import com.icebreak.p2p.dataobject.ChargeProject;
import com.icebreak.p2p.dataobject.ChargeTemplate;
import com.icebreak.p2p.page.Pagination;

public interface ChargeService {
	/**
	 * 根据各种属性查询
	 * @param name 模版名称
	 * @param type 模版类型
	 * @param start 起始行号
	 * @param size 偏移量
	 * @param roles 角色
	 * @return
	 */
	public Pagination<ChargeTemplate> getChargeTemplates(String name, String type, int start, int size, String... roles);
	/**
	 * 根据ID查询ChargeTemplate
	 * @param id
	 * @return
	 */
	public ChargeTemplate lookup(long id);
    /**
     * 修改	
     * @param template
     */
    public void modify(ChargeTemplate template, long[] projectIds, long[] ruleStarts, long[] ruleEnds, double[] ruleValues, String[] ruleMethods);
    /**
     * 根据收费方式获取收费项目
     * @param methods
     * @return
     */
    public List<ChargeProject> getChargeProjectsByMethod(String... methods);
    /**
     * 添加消费项目
     * @param projects
     */
    public void addChargeProjects(String[] methods, String[] projects, Integer[] statuses);
    /**
     * 删除收费项目
     * @param ids
     */
    public void deleteProjects(long... ids);
    /**
     * 添加收费膜模版
     * @param template
     * @param projectIds
     */
    public void addChargeTemplate(ChargeTemplate template, long[] projectIds, long[] ruleStarts, long[] ruleEnds, double[] ruleValues, String[] ruleMethods);
    /**
     * 收费
     * @param userId
     */
    public void charge(long userId, int roleId) throws Exception;
    /**
	 * 收费
	 * @param userId
	 * @param amountFlow
	 * @param templateId
	 * @param date
	 */
    public void charge(long userId, long amountFlow, long templateId, Date date, Long tradeDetailId)throws Exception;
}
