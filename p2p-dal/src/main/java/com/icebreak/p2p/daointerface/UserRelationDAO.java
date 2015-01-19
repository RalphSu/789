package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.ProfitAsignInfo;
import com.icebreak.p2p.dataobject.UserRelationDO;

public interface UserRelationDAO {
	/***
	 * 添加
	 * 
	 * @param userRelation
	 * @throws DataAccessException
	 */
	public long insert(UserRelationDO userRelation) throws DataAccessException;
	
	/***
	 * 修改
	 * 
	 * @param userRelation
	 * @throws DataAccessException
	 */
	public int update(UserRelationDO userRelation) throws DataAccessException;
	
	/***
	 * 删除
	 * 
	 * @param userRelationId
	 * @throws DataAccessException
	 */
	public int delete(long userRelationId) throws DataAccessException;
	
	/**
	 * 按条件统计relations个数
	 * @param userRelationId
	 * @param parentId
	 * @param childId
	 * @param memberNo
	 * @return
	 */
	public int getRelationsCountByConditions(Map<String, Object> conditions);
	
	/**
	 * 按条件查询relations
	 * @param userRelationId
	 * @param parentId
	 * @param childId
	 * @param memberNo
	 * @return
	 */
	public List<UserRelationDO> getRelationsByConditions(Map<String, Object> conditions);
	
	/**
	 * 统计该机构下的人数
	 * @param userId
	 * @return
	 */
	public int countInvestorsInThisJG(long userId);
	
	/**
	 * 新增一条经纪人分配利润额度记录
	 * @param profitAsignInfo
	 */
	public void addProfit(ProfitAsignInfo profitAsignInfo) throws DataAccessException;
	
	/**
	 * 更新额度
	 * @param profitAsignInfo
	 * @return
	 */
	public int updateProfit(ProfitAsignInfo profitAsignInfo);
	
	/**
	 * 查询投资人分配额度信息
	 * @param receiveId
	 * @return
	 */
	public ProfitAsignInfo getProfit(long receiveId);
	
	/**
	 * 删除对应收益分配记录
	 * @param tblBaseId
	 * @return
	 */
	public int deleteProfitAsign(String tblBaseId);
	
	/**
	 * @param receiveId
	 * @param distributionId
	 * @return
	 */
	ProfitAsignInfo getProfit(long receiveId, long distributionId);
	
}
