package com.icebreak.p2p.user;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.ProfitAsignInfo;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;

public interface UserRelationManager {
	/***
	 * 添加
	 * 
	 * @param userRelation
	 * @throws DataAccessException
	 */
	public UserRelationReturnEnum insert(UserRelationDO userRelation) throws Exception;
	
	/***
	 * 修改
	 * 
	 * @param userRelation
	 * @throws DataAccessException
	 */
	public UserRelationReturnEnum update(UserRelationDO userRelation) throws Exception;
	
	/***
	 * 删除
	 * 
	 * @param userRelationId
	 * @throws DataAccessException
	 */
	public UserRelationReturnEnum delete(long userRelationId) throws Exception;
	
	/**
	 * 按条件统计relations个数
	 * @param userRelationId
	 * @param parentId
	 * @param childId
	 * @param memberNo
	 * @return
	 */
	public int getRelationsCountByConditions(Long userRelationId, Long parentId, Long childId,
												String memberNo);
	
	/**
	 * 按条件查询relations
	 * @param userRelationId
	 * @param parentId
	 * @param childId
	 * @param memberNo
	 * @return
	 */
	public Page<UserRelationDO> getRelationsByConditions(Long userRelationId, Long parentId,
															Long childId, String memberNo);
	
	/**
	 * 统计该机构下的投资人数量
	 * @param userId
	 * @return
	 */
	public int countInvestorsInThisJG(long userId);
	
	/***
	 * 修改经纪人
	 * 
	 * @param userRelation
	 * @throws DataAccessException
	 */
	public UserRelationReturnEnum changeBroker(long childId, long parentId, String referees)
																							throws Exception;
	
	/***
	 * 修改营销机构
	 * 
	 * @param userRelation
	 * @throws DataAccessException
	 */
	public UserRelationReturnEnum changeBrokerMarketting(long brokerUserId, long parentId)
																							throws Exception;
	
	/**
	 * 新增经纪人下投资的利润分配额度
	 * @param profitAsignInfo
	 * @return
	 */
	public UserRelationReturnEnum insertProfit(ProfitAsignInfo profitAsignInfo) throws Exception;
	
	/**
	 * 更新分配额度
	 * @param profitAsignInfo
	 * @return
	 */
	public UserRelationReturnEnum updateProfit(ProfitAsignInfo profitAsignInfo) throws Exception;
	
	/**
	 * 根据投资人ID查询
	 * @param receiveId
	 * @return
	 */
	public ProfitAsignInfo queryByReceiveId(long receiveId);
	
	/**
	 * 删除对应收益分配记录
	 * @param tblBaseId
	 * @return
	 */
	public UserRelationReturnEnum deleteProfitAsign(String tblBaseId) throws Exception;
	
	/**
	 * @param receiveId
	 * @param distributionId
	 * @return
	 */
	ProfitAsignInfo queryByReceiveIdAndDistributionId(long receiveId, long distributionId);
}
