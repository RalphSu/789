package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.DivisionTemplate;

import org.springframework.dao.DataAccessException;

public interface DivisionTemplateDao {
	/**
	 * 添加一条分润规则
	 * @param template
	 */
	public void addDivisionTemplate(DivisionTemplate template);
	/**
	 * 根据模版ID删除
	 * @param templateId
	 * @return
	 */
	public int deleteByTemplateId(long templateId);
	/**
	 * 修改一条分润模版
	 * @param template
	 * @return
	 */
	public int modifyDivisionTemplate(DivisionTemplate template);
	/**
	 * 根据模版ID获取一条模版记录
	 * @param templateId
	 * @return
	 */
	public DivisionTemplate getByTemplateId(long templateId);
	/**
	 * 根据条件查询
	 * @param params
	 * @return
	 */
	public List<DivisionTemplate> getDivisionTemplatesByConditions(Map<String, Object> params);
	/**
	 * 根据条件查询条数
	 * @param params
	 * @return
	 */
	public long getDivisionTemplatesByConditionsCount(Map<String, Object> params);
	/**
	 * 根据分润阶段查询分润模版
	 * @param phase
	 * @return
	 */
	public List<DivisionTemplate> getDivisionTemplatesByPhase(String phase);

    public int changeStatus(String templateStatus, long divisionTemplateId) throws DataAccessException;

    public long isUseByDivisionTemplateId(long investTemplateId,long repayTemplateId) throws DataAccessException;

}
