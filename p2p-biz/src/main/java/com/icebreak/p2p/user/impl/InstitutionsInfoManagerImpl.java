package com.icebreak.p2p.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.base.BaseAutowiredToolsService;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.OperatorInfoDO;
import com.icebreak.p2p.dataobject.User;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.integration.openapi.order.RegisterOrder;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.user.InstitutionsInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;
import com.icebreak.p2p.user.valueobject.InstitutionsContractedInfo;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.ApplicationConstant;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.MD5Util;
import com.icebreak.p2p.util.SendInformation;
import com.icebreak.p2p.ws.enums.MemberScalEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.UserTypeEnum;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.message.yzz.YzzUserSpecialRegisterRequest;
import com.yiji.openapi.sdk.message.yzz.YzzUserSpecialRegisterResponse;

@Service
public class InstitutionsInfoManagerImpl extends BaseAutowiredToolsService implements
																			InstitutionsInfoManager {
	@Autowired
	protected UserBaseInfoManager userBaseInfoManager;
	@Autowired
	protected UserRelationManager userRelationManager;
	@Autowired
	protected IOperatorInfoService operatorInfoService;
	@Autowired
	protected AuthorityService authorityService;
	
	private final static String JJRAGENTPREFIX = "K"; //经纪人下的投资人前缀
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public InstitutionsReturnEnum addInstitutionsInfo(InstitutionsInfoDO institutionsInfo,
														UserBaseInfoDO userBaseInfo, long parentId,
														int... roleIds) throws Exception {
		InstitutionsReturnEnum returnEnum = InstitutionsReturnEnum.EXECUTE_FAILURE;
		User user = null;
		try {
			user = authorityService.addUser(userBaseInfo.getUserName(), roleIds);
		} catch (Exception e) {
			logger.error("addUser===", e);
			throw e;
			
		}
		
		String externalId = BusinessNumberUtil.gainNumber();
		String accountId = "";
		
		RegisterOrder infoOrder = new RegisterOrder();
		infoOrder.setEmail(userBaseInfo.getMail());
		infoOrder.setUserType("B");
		infoOrder.setUserStatus("W");
		infoOrder.setRealName(userBaseInfo.getRealName());
		infoOrder.setUserName(userBaseInfo.getAccountName());
		infoOrder.setEmail(userBaseInfo.getMail());
		
		YzzUserSpecialRegisterRequest request = new YzzUserSpecialRegisterRequest(
			userBaseInfo.getAccountName(), userBaseInfo.getRealName(), userBaseInfo.getMail(),
			userBaseInfo.getMobile(), institutionsInfo.getLegalRepresentativeCardNo());
		request.setCertValidTime(institutionsInfo.getCardPeriod());
		request.setUserType(YzzUserSpecialRegisterRequest.USER_TYPE_MERCHANT);
		YzzUserSpecialRegisterResponse response = yzzGatewayService.yzzUserRegister(request);
		
		if (response.success()) {
			accountId = response.getUserId();
		} else {
			throw new Exception("处理失败");
		}
		
		if (StringUtil.isBlank(accountId)) {
			throw new Exception("处理失败");
		}
		userBaseInfo.setUserBaseId(externalId);
		userBaseInfo.setUserId(user.getId());
		userBaseInfo.setAccountId(accountId);
		String userBaseId = userBaseInfoManager.addUserBaseInfo(userBaseInfo);
		if (StringUtil.isBlank(userBaseId)) {
			throw new Exception("处理失败");
		}
		institutionsInfo.setUserBaseId(userBaseId);
		long resultSet = institutionsInfoDAO.insert(institutionsInfo);
		if (resultSet > 0) {
			// 发送邮件
			if (CommonUtil.checkMobile(userBaseInfo.getAccountName())) {
				this.smsService.sendSMS(
					userBaseInfo.getMobile(),
					"您已经成功注册了" + AppConstantsUtil.getProductName() + "账户，请打开一下链接激活该用户："
							+ AppConstantsUtil.getHostHttpUrl() + "/anon/activate/"
							+ MD5Util.getMD5_32(userBaseInfo.getUserBaseId()),
					this.getOpenApiContext());
			} else {
				mailService.sendMail(SendInformation.sendMail(userBaseInfo.getMail(),
					userBaseInfo.getRealName(),
					"/anon/activate/" + MD5Util.getMD5_32(userBaseInfo.getUserBaseId()),
					ApplicationConstant.EMAIL_TEMPLATE_ID), "HTML");
			}
		}
		returnEnum = InstitutionsReturnEnum.EXECUTE_SUCCESS;
		if (parentId > 0) {
			UserRelationDO userRelation = new UserRelationDO();
			userRelation.setParentId(parentId);
			userRelation.setChildId(user.getId());
			userRelation.setMemberNo(getMemberNo(parentId));
			UserRelationReturnEnum relationReturnEnum = userRelationManager.insert(userRelation);
			if (relationReturnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
				UserBaseInfoDO curParentJG = getParentJg(parentId);//获取营销机构
				int countNew = userRelationManager.countInvestorsInThisJG(curParentJG.getUserId());
				curParentJG.setExIdentityNo(String.valueOf(countNew));
				userBaseInfoManager.updateUserBaseInfo(curParentJG);
			}
			if (relationReturnEnum == UserRelationReturnEnum.EXECUTE_FAILURE) {
				returnEnum = InstitutionsReturnEnum.EXECUTE_FAILURE;
			}
		}
		
		return returnEnum;
	}
	
	private UserBaseInfoDO getParentJg(long parentId) throws Exception {
		UserBaseInfoDO curParentJG = null;
		Page<UserRelationDO> userRelationsPage = userRelationManager.getRelationsByConditions(null,
			null, parentId, null);
		if (userRelationsPage.getResult() != null && userRelationsPage.getResult().size() > 0) {
			for (int i = 0; i < userRelationsPage.getResult().size(); i++) {
				List<UserBaseInfoDO> curParentJGs = userBaseInfoManager.queryByType(
					UserTypeEnum.JG.code(),
					String.valueOf(userRelationsPage.getResult().get(i).getParentId()));
				if (curParentJGs != null && curParentJGs.size() > 0) {
					curParentJG = curParentJGs.get(0);
				} else {
					logger.info("经纪人的机构没找到，机构Id"
								+ userRelationsPage.getResult().get(i).getParentId());
					continue;
				}
			}
		}
		return curParentJG;
	}
	
	private String getMemberNo(long parentId) throws Exception {
		String memberNo = null;
		Page<UserRelationDO> userRelationsPage = userRelationManager.getRelationsByConditions(null,
			null, parentId, null);
		if (userRelationsPage.getResult() != null && userRelationsPage.getResult().size() > 0) {
			for (int i = 0; i < userRelationsPage.getResult().size(); i++) {
				List<UserBaseInfoDO> curParentJGs = userBaseInfoManager.queryByType("JG",
					String.valueOf(userRelationsPage.getResult().get(i).getParentId()));
				UserBaseInfoDO curParentJG = null;
				if (curParentJGs != null && curParentJGs.size() > 0) {
					curParentJG = curParentJGs.get(0);
				} else {
					logger.info("经纪人的机构没找到，机构Id"
								+ userRelationsPage.getResult().get(i).getParentId());
					continue;
				}
				int count = userRelationManager.countInvestorsInThisJG(curParentJG.getUserId());
				boolean availabelFlag = false;
				while (!availabelFlag) {
					count++;
					int curindex = count;
					if (String.valueOf(curindex).endsWith("4")) {
						curindex++;
					}
					
					int memberScale = 0;
					if ("高级机构".equals(curParentJG.getType())) {
						memberScale = MemberScalEnum.VIP.getValue();
					} else {
						memberScale = MemberScalEnum.DEFAULT.getValue();
					}
					
					String sino = StringUtils.leftPad(String.valueOf(curindex), memberScale, "0");
					String jgIdentity = curParentJG.getIdentityName();
					memberNo = jgIdentity + JJRAGENTPREFIX + sino;
					Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
						null, null, memberNo);
					if (page.getResult() != null && page.getResult().size() > 0) {
						availabelFlag = false;
					} else {
						availabelFlag = true;
						logger.info("可用投资人编号：" + memberNo);
					}
				}
			}
		}
		return memberNo;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public InstitutionsReturnEnum updateInstitutionsInfo(InstitutionsInfoDO institutionsInfo,
															UserBaseInfoDO userBaseInfo,
															boolean bool, int... roleIds)
																							throws Exception {
		InstitutionsReturnEnum returnEnum = InstitutionsReturnEnum.EXECUTE_FAILURE;
		try {
			if (bool) {
				this.authorityService.unbindRoles(userBaseInfo.getUserId(),
					SysUserRoleEnum.getJGRoleIds());
				this.authorityService.grantRolesToUser(userBaseInfo.getUserId(), roleIds);
			}
			long resultSet = institutionsInfoDAO.update(institutionsInfo);
			long resultSetTo = userBaseInfoDAO.update(userBaseInfo);
			if (resultSet > 0 && resultSetTo > 0) {
				returnEnum = InstitutionsReturnEnum.EXECUTE_SUCCESS;
			}
		} catch (Exception e) {
			logger.error("未知异常:e={}", e.getMessage(), e);
		}
		
		return returnEnum;
	}
	
	@Override
	public Page<InstitutionsInfoDO> page(QueryConditions queryConditions, PageParam pageParam)
																								throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userBaseId", queryConditions.getUserBaseId());
		condition.put("userName", queryConditions.getUserName());
		condition.put("accountName", queryConditions.getAccountName());
		condition.put("realName", queryConditions.getRealName());
		condition.put("certNo", queryConditions.getCertNo());
		condition.put("state", queryConditions.getState());
		// 查询总条数
		int totalSize = (int) institutionsInfoDAO.queryCount(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		List<InstitutionsInfoDO> list = institutionsInfoDAO.queryList(condition);
		Page<InstitutionsInfoDO> page = new Page<InstitutionsInfoDO>(start, totalSize,
			pageParam.getPageSize(), list);
		
		return page;
	}
	
	@Override
	public InstitutionsInfoDO query(String userBaseId) throws Exception {
		InstitutionsInfoDO institutionsInfo = institutionsInfoDAO.query(userBaseId);
		return institutionsInfo;
	}
	
	@Override
	public List<InstitutionsContractedInfo> queryListeEnterpriseNameAndUserId(long roleId)
																							throws Exception {
		List<InstitutionsContractedInfo> institutionsContractedInfos = new ArrayList<InstitutionsContractedInfo>();
		List<InstitutionsInfoDO> institutionsInfoDOs = institutionsInfoDAO
			.queryListeEnterpriseNameAndUserId(roleId);
		for (InstitutionsInfoDO infos : institutionsInfoDOs) {
			InstitutionsContractedInfo institutionsContractedInfo = new InstitutionsContractedInfo();
			institutionsContractedInfo.setUserId(infos.getUserId());
			institutionsContractedInfo.setEnterpriseName(infos.getEnterpriseName());
			institutionsContractedInfos.add(institutionsContractedInfo);
		}
		return institutionsContractedInfos;
	}
	
	@Override
	public InstitutionsContractedInfo queryByUserId(long userId) throws Exception {
		InstitutionsContractedInfo institutionsContractedInfo = new InstitutionsContractedInfo();
		InstitutionsInfoDO institutionsInfo = institutionsInfoDAO.queryByUserId(userId);
		institutionsContractedInfo.setUserId(institutionsInfo.getUserId());
		institutionsContractedInfo.setEnterpriseName(institutionsInfo.getEnterpriseName());
		return institutionsContractedInfo;
	}
	
	@Override
	public Page<InstitutionsInfoDO> pageByRole(List<Long> roleId, String enterpriseName,
												PageParam pageParam) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("roleId", roleId);
		condition.put("enterpriseName", enterpriseName);
		// 查询总条数
		int totalSize = (int) institutionsInfoDAO.queryByRoleCount(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		List<InstitutionsInfoDO> list = institutionsInfoDAO.queryByRole(condition);
		Page<InstitutionsInfoDO> page = new Page<InstitutionsInfoDO>(start, totalSize,
			pageParam.getPageSize(), list);
		return page;
	}
	
	@Transactional(rollbackFor = RuntimeException.class, value = "transactionManager")
	@Override
	public PersonalReturnEnum addOperatorInstitutionInfo(InstitutionsInfoDO institutionsInfo,
															UserBaseInfoDO userBaseInfo,
															OperatorInfoDO operatorInfo,
															long parentId, int[] roleIds)
																							throws Exception {
		PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		//增加userid
		User user = null;
		try {
			user = authorityService.addUser(userBaseInfo.getUserName(), roleIds);
		} catch (Exception e) {
			logger.error("添加用户addUser", e);
			return returnEnum;
		}
		String externalId = BusinessNumberUtil.gainNumber();
		if (StringUtil.isBlank(userBaseInfo.getAccountId())) {
			throw new Exception("处理失败");
		}
		
		userBaseInfo.setUserId(user.getId());
		userBaseInfo.setUserBaseId(externalId);
		if (StringUtil.isBlank(userBaseInfo.getMail())) {
			userBaseInfo.setMail(userBaseInfo.getAccountName());
		}
		//增加userbase
		String userBaseId = userBaseInfoManager.addUserBaseInfo(userBaseInfo);
		if (StringUtil.isBlank(userBaseId)) {
			throw new Exception("处理失败");
		}
		institutionsInfo.setUserBaseId(userBaseId);
		//增加personal
		long resultSet = institutionsInfoDAO.insert(institutionsInfo);
		returnEnum = PersonalReturnEnum.EXECUTE_SUCCESS;
		if (parentId > 0) {
			operatorInfo.setUserBaseId(userBaseId);
			operatorInfo.setParentId(parentId);
			operatorInfo.setAddTime(new Date());
			long operatorId = operatorInfoService.addOperatorInfo(operatorInfo);
			if (operatorId <= 0) {
				throw new Exception("处理失败");
			}
		}
		return returnEnum;
	}
	
	@Override
	public Page<InstitutionsInfoDO> pageChildren(QueryConditions queryConditions,
													PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userId", queryConditions.getUserId());
		condition.put("userName", queryConditions.getUserName());
		condition.put("realName", queryConditions.getRealName());
		// 查询总条数
		int totalSize = (int) institutionsInfoDAO.queryChildrenCountByCondition(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		List<InstitutionsInfoDO> list = institutionsInfoDAO.queryChildrenListByCondition(condition);
		Page<InstitutionsInfoDO> page = new Page<InstitutionsInfoDO>(start, totalSize,
			pageParam.getPageSize(), list);
		return page;
	}
	
	@Override
	public boolean isFromSameOrgan(String userBaseId1, String userBaseId2) {
		return operatorInfoService.isFromSameOrgan(userBaseId1, userBaseId2);
	}
	
}
