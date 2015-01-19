package com.icebreak.p2p.user;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.viewObject.InvestorInfoVO;
import com.icebreak.p2p.dataobject.viewObject.PersonalInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;

public interface PersonalInfoManager {
	/***
	 * 个人用户开户
	 * 
	 * @param personalInfo
	 * @param userBaseInfo
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public PersonalReturnEnum addPersonalInfo( PersonalInfoDO personalInfo,UserBaseInfoDO userBaseInfo,long parentId,int ...roleIds) throws Exception;
	/**
	 * 个人用户开户(调用openApi接口)
	 * @param personalInfo
	 * @param userBaseInfo
	 * @param parentId
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public PersonalReturnEnum addPersonInfoByOpenApi(PersonalInfoDO personalInfo,UserBaseInfoDO userBaseInfo,
			long parentId,int ...roleIds)throws Exception;

	/***
	 * 个人用户修改
	 * 
	 * @param personalInfo
	 * @param userBaseInfo
	 * @return
	 * @throws Exception
	 */
	public PersonalReturnEnum updatePersonalInfo(PersonalInfoDO personalInfo,UserBaseInfoDO userBaseInfo,boolean bool,int ...roleIds) throws Exception;
	/***
	 * 个人用户管理
	 * 
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<PersonalInfoDO> page(QueryConditions queryConditions,PageParam pageParam)throws Exception;
	
	/***
	 * 根据条件查询个人信息
	 * 
	 * @param userBaseId
	 * @return PersonalInfoDO
	 * @throws Exception
	 */
	public PersonalInfoDO query(String userBaseId) throws Exception;
	
	/***
	 * 投资者管理
	 * 
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<PersonalInfoDO> pageChildren(QueryConditions queryConditions,PageParam pageParam)throws Exception;
	/***
	 * 投资者管理用于显示
	 * 
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<PersonalInfoVO> pageChildrenVO(QueryConditions queryConditions,
			PageParam pageParam);
	
	/**
	 * 根据资金账户查询其证件号
	 * @param accountId
	 * @return
	 */
	public String getCertNoByAccountId(String accountId);
	/**
	 * 新增后台管理员
	 * @param userBaseInfo
	 * @param roleIds
	 * @return
	 */
	public PersonalReturnEnum addAdmin(UserBaseInfoDO userBaseInfo,int ...roleIds)throws Exception;
	/**
	 * 查询所有用户(个人、机构)实名状态
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<PersonalInfoDO> queryRealNameSta(QueryConditions queryConditions,PageParam pageParam)throws Exception;
	/**
	 * 根据实名条件查询其数量
	 * @param queryConditions
	 * @return
	 */
	public long queryCountsRealNameSta(QueryConditions queryConditions);
	/**
	 * 投资人管理
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public Page<InvestorInfoVO> pageChildrenInvestorVO(
			QueryConditions queryConditions, PageParam pageParam);
	/**
	 * 检查该角色下面的身份证是否已存在
	 * @param certNo
	 * @param bROKER
	 * @return
	 */
	public long countCertNoByRole(String certNo, String roleCode);
	/**
	 * 托管机构投资用户注册
	 * @param personalInfo
	 * @param userBaseInfo
	 */
	public JSONObject registerFromYJF(PersonalInfoDO personalInfo,UserBaseInfoDO userBaseInfo,int ...roleIds)throws Exception;
	
	/**
	 * 更新角色加经济人
	 * @param personalInfo
	 * @param userBaseInfo
	 * @param unbindRole
	 * @param parentId
	 * @param brokerNo
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public PersonalReturnEnum updatePersonalInfoAndChangBroke(PersonalInfoDO personalInfo,
																UserBaseInfoDO userBaseInfo,
																boolean unbindRole, long parentId,
																String brokerNo, int... roleIds)
																								throws Exception;
	
}
