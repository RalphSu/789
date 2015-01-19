package com.icebreak.p2p.user;

import java.util.List;

import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.OperatorInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.valueobject.InstitutionsContractedInfo;
import com.icebreak.p2p.user.valueobject.QueryConditions;

public interface InstitutionsInfoManager {
	/***
	 * 机构用户开户
	 * 
	 * @param institutionsInfo
	 * @return
	 * @throws Exception
	 */
	public InstitutionsReturnEnum addInstitutionsInfo(InstitutionsInfoDO institutionsInfo,
														UserBaseInfoDO userBaseInfo, long parentId,
														int... roleIds) throws Exception;
	
	/***
	 * 机构用户修改
	 * 
	 * @param institutionsInfo
	 * @return
	 * @throws Exception
	 */
	public InstitutionsReturnEnum updateInstitutionsInfo(InstitutionsInfoDO institutionsInfo,
															UserBaseInfoDO userBaseInfo,
															boolean bool, int... roleIds)
																							throws Exception;
	
	/***
	 * 机构用户管理
	 * 
	 * @param institutionsInfo
	 * @return
	 * @throws Exception
	 */
	public Page<InstitutionsInfoDO> page(QueryConditions queryConditions, PageParam pageParam)
																								throws Exception;
	
	/***
	 * 根据条件查询机构信息
	 * 
	 * @param userBaseId
	 * @return PersonalInfoDO
	 * @throws Exception
	 */
	public InstitutionsInfoDO query(String userBaseId) throws Exception;
	
	/***
	 * 根据条件查询对应机构信息集合
	 * 
	 * @param roleId
	 * @return list
	 * @throws Exception
	 */
	public List<InstitutionsContractedInfo> queryListeEnterpriseNameAndUserId(long roleId)
																							throws Exception;
	
	/***
	 * 根据条件查询机构信息
	 * 
	 * @param userId
	 * @return PersonalInfoDO
	 * @throws Exception
	 */
	public InstitutionsContractedInfo queryByUserId(long userId) throws Exception;
	
	/***
	 * 根据角色管理机构用户
	 * 
	 * @param institutionsInfo
	 * @return
	 * @throws Exception
	 */
	public Page<InstitutionsInfoDO> pageByRole(List<Long> roleId, String enterpriseName,
												PageParam pageParam) throws Exception;
	
	/***
	 * 用户机构下的操作员开户
	 * @param institutionsInfo
	 * @param userBaseInfo
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public PersonalReturnEnum addOperatorInstitutionInfo(InstitutionsInfoDO institutionsInfo,
															UserBaseInfoDO userBaseInfo,
															OperatorInfoDO operatorInfo,
															long parentId, int[] roleIds)
																							throws Exception;
	
	/***
	 * 操作员管理
	 * 
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<InstitutionsInfoDO> pageChildren(QueryConditions queryConditions,
													PageParam pageParam);
	
	/**
	 * 两个操作人员是否来自同一担保机构
	 * @param userId1
	 * @param userId2
	 * @return true :来自同一担保机构
	 */
	boolean isFromSameOrgan(String userBaseId1, String userBaseId2);
	
}
