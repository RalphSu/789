package com.icebreak.p2p.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.base.BaseAutowiredToolsService;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.dataobject.viewObject.InvestorInfoVO;
import com.icebreak.p2p.dataobject.viewObject.PersonalInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.user.PersonalInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.*;
import com.icebreak.p2p.ws.enums.MemberScalEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.UserTypeEnum;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PersonalInfoManagerImpl extends BaseAutowiredToolsService implements
																		PersonalInfoManager {
	@Autowired
	protected UserBaseInfoManager userBaseInfoManager;
	@Autowired
	protected UserRelationManager userRelationManager;
	
	@Autowired
	protected AuthorityService authorityService;
	
	public long getDefaultBrokerUserId() throws Exception {
		UserBaseInfoDO brokerUser = userBaseInfoManager.queryByUserName(
			AppConstantsUtil.getDefaultBrokerUserName(), SysUserRoleEnum.BROKER.getValue());
		return brokerUser.getUserId();
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public PersonalReturnEnum addPersonalInfo(PersonalInfoDO personalInfo,
												UserBaseInfoDO userBaseInfo, long parentId,
												int... roleIds) throws Exception {
		PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		User user = null;
		try {
			user = authorityService.addUser(userBaseInfo.getUserName(), roleIds);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addUser error userBaseInfo={},roleIds={}", e);
			throw new Exception("db Exception");
		}
		
		if (parentId <= 0) {
			for (int i : roleIds) {
				if (i == ApplicationConstant.ROLE_ID_INVESTOR) {
					parentId = getDefaultBrokerUserId();
				}
			}
		}
		if (parentId > 0) {
			userRelationProcess(parentId, returnEnum, user);
		}
		
		saveAndSendEmail(personalInfo, userBaseInfo, user);
		returnEnum = PersonalReturnEnum.EXECUTE_SUCCESS;
		return returnEnum;
	}
	
	// 注册并发送邮件
	protected void saveAndSendEmail(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo,
									User user)
																							throws Exception {
		userBaseInfo.setUserId(user.getId());
		userBaseInfo.setUserBaseId(BusinessNumberUtil.gainNumber());
		String userBaseId = null;
		try {
			userBaseId = userBaseInfoManager.addUserBaseInfo(userBaseInfo);
		} catch (Exception e) {
			logger.error("数据库异常", e);
			throw new Exception("数据库异常");
		}
		personalInfo.setUserBaseId(userBaseId);
		long personId = personalInfoDAO.insert(personalInfo);
		if (personId > 0 && !"normal".equals(userBaseInfo.getState())) {
			try {
				mailService.sendMail(SendInformation.sendMail(userBaseInfo.getMail(),
				userBaseInfo.getUserName(),
					"/anon/activate/" + MD5Util.getMD5_32(userBaseInfo.getUserBaseId()),
					ApplicationConstant.EMAIL_TEMPLATE_ID), "HTML");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
				
		}
	}

	protected PersonalReturnEnum userRelationProcess(long parentId, PersonalReturnEnum returnEnum,
														User user) throws Exception {
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
			returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		}
		return returnEnum;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public PersonalReturnEnum addPersonInfoByOpenApi(PersonalInfoDO personalInfo,
														UserBaseInfoDO userBaseInfo, long parentId,
														int... roleIds) throws Exception {
		PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		User user = null;
		try {
			user = authorityService.addUser(userBaseInfo.getUserName(), roleIds);
		} catch (Exception e) {
			logger.error("addUser error userBaseInfo={},roleIds={}", e);
			throw e;
		}
		String accountId = "";
		String externalId = BusinessNumberUtil.gainNumber();
		
		saveAndSendEmail(personalInfo, userBaseInfo, user);
		returnEnum = PersonalReturnEnum.EXECUTE_SUCCESS;
		
		if (parentId <= 0) {
			for (int i : roleIds) {
				if (i == ApplicationConstant.ROLE_ID_INVESTOR) {
					parentId = getDefaultBrokerUserId();
				}
			}
		}
		if (parentId > 0) {
			returnEnum = userRelationProcess(parentId, returnEnum, user);
		}
		return returnEnum;
		
	}
	
	private UserBaseInfoDO getParentJg(long parentId) throws Exception {
		UserBaseInfoDO curParentJG = null;
		Page<UserRelationDO> userRelationsPage = userRelationManager.getRelationsByConditions(null,
			null, parentId, null);
		if (userRelationsPage.getResult() != null && userRelationsPage.getResult().size() > 0) {
			for (int i = 0; i < userRelationsPage.getResult().size(); i++) {
				long tempParentId = userRelationsPage.getResult().get(i).getParentId();
				long index = 0;
				long countTotal = 20;
				while (curParentJG == null && index < countTotal) {
					index++;
					UserBaseInfoDO tempCurParentJG = userBaseInfoManager
						.queryByUserId(tempParentId);
					if (tempCurParentJG != null) {
						if (UserTypeEnum.JG.code().equals(tempCurParentJG.getType())) {
							curParentJG = tempCurParentJG;
							break;
						} else {
							Page<UserRelationDO> userRelationsPage1 = userRelationManager
								.getRelationsByConditions(null, null, tempParentId, null);
							if (ListUtil.isNotEmpty(userRelationsPage1.getResult())) {
								tempParentId = userRelationsPage1.getResult().get(i).getParentId();
								continue;
							}
						}
						
					} else {
						logger.info("经纪人的机构没找到，机构Id"
									+ userRelationsPage.getResult().get(i).getParentId());
						break;
					}
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
				UserBaseInfoDO curParentJG = null;
				long timeCount = 20;
				long index = 0;
				long tempParentId = userRelationsPage.getResult().get(i).getParentId();
				while (curParentJG == null && index < timeCount) {
					index++;
					UserBaseInfoDO parentJG = userBaseInfoManager.queryByUserId(tempParentId);
					
					if (parentJG != null) {
						if (UserTypeEnum.JG.code().equals(parentJG.getType())) {
							curParentJG = parentJG;
							break;
						} else {
							Page<UserRelationDO> userRelationsPage1 = userRelationManager
								.getRelationsByConditions(null, null, parentJG.getUserId(), null);
							if (ListUtil.isNotEmpty(userRelationsPage1.getResult())) {
								tempParentId = userRelationsPage1.getResult().get(0).getParentId();
								continue;
							}
							logger.info("经纪人的机构没找到，机构Id"
										+ userRelationsPage.getResult().get(i).getParentId());
							break;
						}
						
					} else {
						logger.info("经纪人的机构没找到，机构Id"
									+ userRelationsPage.getResult().get(i).getParentId());
						continue;
					}
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
					if (UserTypeEnum.GJJG.code().equals(curParentJG.getType())) {
						memberScale = MemberScalEnum.VIP.getValue();
					} else {
						memberScale = MemberScalEnum.DEFAULT.getValue();
					}
					String sino = null;
					if (curindex <= 99999) {
						sino = StringUtils.leftPad(String.valueOf(curindex), memberScale, "0");
					} else {
						sino = curindex + "";
					}
					String jgIdentity = curParentJG.getIdentityName();
					memberNo = jgIdentity + ApplicationConstant.JJRAGENTPREFIX + sino;
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
	public PersonalReturnEnum updatePersonalInfo(PersonalInfoDO personalInfo,
													UserBaseInfoDO userBaseInfo,
													boolean unbindRole, int... roleIds)
																						throws Exception {
		PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		if (unbindRole) {
			this.authorityService.unbindRoles(userBaseInfo.getUserId(),
				SysUserRoleEnum.getBaseRoleIds());
			this.authorityService.grantRolesToUser(userBaseInfo.getUserId(), roleIds);
		}
		long resultSet = 0;
		long resultSetTo = 0;
		if (personalInfo != null) {
			resultSet = personalInfoDAO.update(personalInfo);
		} else {
			resultSet = 1;
		}
		if (userBaseInfo != null) {
			resultSetTo = userBaseInfoDAO.update(userBaseInfo);
		} else {
			resultSetTo = 1;
		}
		if (resultSet > 0 && resultSetTo > 0) {
			returnEnum = PersonalReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public Page<PersonalInfoDO> page(QueryConditions queryConditions, PageParam pageParam)
																							throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		if (queryConditions.getUserBaseId() != null)
			condition.put("userBaseId", queryConditions.getUserBaseId().trim());
		if (queryConditions.getUserName() != null)
			condition.put("userName", queryConditions.getUserName().trim());
		if (queryConditions.getAccountName() != null)
			condition.put("accountName", queryConditions.getAccountName().trim());
		if (queryConditions.getRealName() != null)
			condition.put("realName", queryConditions.getRealName().trim());
		if (queryConditions.getCertNo() != null)
			condition.put("certNo", queryConditions.getCertNo().trim());
		if (queryConditions.getMemberNo() != null)
			condition.put("memberNo", queryConditions.getMemberNo().trim());
		if (queryConditions.getMobile() != null)
			condition.put("mobile", queryConditions.getMobile().trim());
		condition.put("state", queryConditions.getState());
		condition.put("role", queryConditions.getRole());
		if (queryConditions.getReferees() != null)
			condition.put("referees", queryConditions.getReferees().trim());
		// 查询总条数
		int totalSize = (int) personalInfoDAO.queryCount(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		List<PersonalInfoDO> list = personalInfoDAO.queryList(condition);
		Page<PersonalInfoDO> page = new Page<PersonalInfoDO>(start, totalSize,
			pageParam.getPageSize(), list);
		
		return page;
	}
	
	@Override
	public Page<PersonalInfoDO> queryRealNameSta(QueryConditions queryConditions,
													PageParam pageParam) throws Exception {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		if (queryConditions.getUserName() != null)
			condition.put("userName", queryConditions.getUserName().trim());
		if (queryConditions.getAccountName() != null)
			condition.put("accountName", queryConditions.getAccountName().trim());
		if (queryConditions.getRealName() != null)
			condition.put("realName", queryConditions.getRealName().trim());
		condition.put("startCreateTime", queryConditions.getStartCreateTime());
		condition.put("endCreateTime", queryConditions.getEndCreateTime());
		condition.put("startUpdateTime", queryConditions.getStartUpdateTime());
		condition.put("endUpdateTime", queryConditions.getEndUpdateTime());
		if (!"".equals(queryConditions.getRealNameAuthentication())
			&& queryConditions.getRealNameAuthentication() != null)
			condition.put("realNameAuthentication", queryConditions.getRealNameAuthentication());
		// 查询总条数
		int totalSize = (int) personalInfoDAO.queryAllCountByConditions(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		List<PersonalInfoDO> list = personalInfoDAO.queryAllByConditions(condition);
		for (PersonalInfoDO personInfo : list) {
			if (personInfo.getRealNameAuthentication() == null
				|| "".equals(personInfo.getRealNameAuthentication())) {
				personInfo.setRealNameAuthentication("N");
			}
		}
		Page<PersonalInfoDO> page = new Page<PersonalInfoDO>(start, totalSize,
			pageParam.getPageSize(), list);
		return page;
	}
	
	@Override
	public long queryCountsRealNameSta(QueryConditions queryConditions) {
		long counts = 0;
		Map<String, Object> condition = new HashMap<String, Object>();
		if (queryConditions.getUserName() != null)
			condition.put("userName", queryConditions.getUserName().trim());
		if (queryConditions.getAccountName() != null)
			condition.put("accountName", queryConditions.getAccountName().trim());
		if (queryConditions.getRealName() != null)
			condition.put("realName", queryConditions.getRealName().trim());
		condition.put("startCreateTime", queryConditions.getStartCreateTime());
		condition.put("endCreateTime", queryConditions.getEndCreateTime());
		condition.put("startUpdateTime", queryConditions.getStartUpdateTime());
		condition.put("endUpdateTime", queryConditions.getEndUpdateTime());
		if (!"".equals(queryConditions.getRealNameAuthentication())
			&& queryConditions.getRealNameAuthentication() != null) {
			condition.put("realNameAuthentication", queryConditions.getRealNameAuthentication());
		}
		counts = personalInfoDAO.queryAllCountByConditions(condition);
		return counts;
	}
	
	@Override
	public PersonalInfoDO query(String userBaseId) throws Exception {
		PersonalInfoDO personalInfo = personalInfoDAO.query(userBaseId);
		return personalInfo;
	}
	
	@Override
	public Page<PersonalInfoDO> pageChildren(QueryConditions queryConditions, PageParam pageParam)
																									throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userId", queryConditions.getUserId());
		condition.put("userName", queryConditions.getUserName());
		condition.put("realName", queryConditions.getRealName());
		// 查询总条数
		int totalSize = (int) personalInfoDAO.queryChildrenCountByCondition(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		List<PersonalInfoDO> list = personalInfoDAO.queryChildrenListByCondition(condition);
		Page<PersonalInfoDO> page = new Page<PersonalInfoDO>(start, totalSize,
			pageParam.getPageSize(), list);
		return page;
	}
	
	@Override
	public Page<PersonalInfoVO> pageChildrenVO(QueryConditions queryConditions, PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userId", queryConditions.getUserId());
		condition.put("userName", queryConditions.getUserName());
		condition.put("realName", queryConditions.getRealName());
		// 查询总条数
		//int totalSize = (int) personalInfoDAO.queryChildrenCountByCondition(condition);
		long totalSize = userBaseInfoManager.queryChildrenCountByCondition(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		//List<PersonalInfoDO> list=  personalInfoDAO.queryChildrenListByCondition(condition);
		List<UserBaseInfoDO> list = userBaseInfoManager.queryChildrenListByCondition(condition);
		List<PersonalInfoVO> voList = new ArrayList<PersonalInfoVO>(list.size());
		if (list != null && list.size() > 0) {
			for (UserBaseInfoDO curDO : list) {
				PersonalInfoVO personalInfoVO = new PersonalInfoVO();
				MiscUtil.copyPoObject(personalInfoVO, curDO);
				//personalInfoVO = (PersonalInfoVO) objectMap(curDO, personalInfoVO);
				personalInfoVO.setUserId(curDO.getUserId());
				personalInfoVO.setUserName(curDO.getUserName());
				personalInfoVO.setState(curDO.getState());
				personalInfoVO.setRowAddTime(curDO.getRowAddTime());
				Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
					null, curDO.getUserId(), null);
				List<UserRelationDO> lst = page.getResult();
				if (lst != null && lst.size() > 0) {
					personalInfoVO.setMemberNo(lst.get(0).getMemberNo());
				}
				voList.add(personalInfoVO);
			}
		}
		Page<PersonalInfoVO> page = new Page<PersonalInfoVO>(start, totalSize,
			pageParam.getPageSize(), voList);
		return page;
	}
	
	@Override
	public String getCertNoByAccountId(String accountId) {
		
		if (!accountId.equals("") && accountId != null) {
			return personalInfoDAO.getCertNoByAccountId(accountId);
		} else {
			return "fail";
		}
		
	}
	
	@Override
	public PersonalReturnEnum addAdmin(UserBaseInfoDO userBaseInfo, int... roleIds)
																					throws Exception {
		PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		//增加userid
		User user = null;
		try {
			user = authorityService.addUser(userBaseInfo.getUserName(), roleIds);
		} catch (Exception e) {
			logger.error("添加用户addAdmin", e);
			return returnEnum;
		}
		String externalId = BusinessNumberUtil.gainNumber();
		userBaseInfo.setUserId(user.getId());
		userBaseInfo.setUserBaseId(externalId);
		userBaseInfo.setRowAddTime(Calendar.getInstance().getTime());
		userBaseInfo.setPayPassword("888888");
		//增加userbase
		String userBaseId = userBaseInfoManager.addUserBaseInfo(userBaseInfo);
		if (StringUtil.isBlank(userBaseId)) {
			throw new Exception("处理失败");
		}
		PersonalInfoDO personInfo = new PersonalInfoDO();
		personInfo.setUserBaseId(userBaseId);
		personInfo.setRealName(userBaseInfo.getRealName());
		long resultSet = personalInfoDAO.insert(personInfo);
		if (resultSet > 0) {
			returnEnum = PersonalReturnEnum.EXECUTE_SUCCESS;
		} else {
			return returnEnum;
		}
		
		return returnEnum;
	}
	
	@Override
	public Page<InvestorInfoVO> pageChildrenInvestorVO(QueryConditions queryConditions,
														PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userId", queryConditions.getUserId());
		condition.put("userName", queryConditions.getUserName());
		condition.put("realName", queryConditions.getRealName());
		// 查询总条数
		//int totalSize = (int) personalInfoDAO.queryChildrenCountByCondition(condition);
		long totalSize = userBaseInfoManager.queryChildrenCountByCondition(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		//List<PersonalInfoDO> list=  personalInfoDAO.queryChildrenListByCondition(condition);
		List<UserBaseInfoDO> list = userBaseInfoManager.queryChildrenListByCondition(condition);
		List<InvestorInfoVO> voList = new ArrayList<InvestorInfoVO>(list.size());
		if (list != null && list.size() > 0) {
			for (UserBaseInfoDO curDO : list) {
				InvestorInfoVO personalInfoVO = new InvestorInfoVO();
				MiscUtil.copyPoObject(personalInfoVO, curDO);
				//personalInfoVO = (InvestorInfoVO);
				personalInfoVO.setUserId(curDO.getUserId());
				personalInfoVO.setUserName(curDO.getUserName());
				personalInfoVO.setState(curDO.getState());
				personalInfoVO.setRowAddTime(curDO.getRowAddTime());
				Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
					null, curDO.getUserId(), null);
				List<UserRelationDO> lst = page.getResult();
				if (lst != null && lst.size() > 0) {
					personalInfoVO.setMemberNo(lst.get(0).getMemberNo());
				}
				//获取该投资人的额外收益信息
				ProfitAsignInfo profitAsignInfo = userRelationManager.queryByReceiveId(curDO
					.getUserId());
				if (profitAsignInfo != null) {
					personalInfoVO.setDistributionQuota(profitAsignInfo.getDistributionQuota());
				}
				voList.add(personalInfoVO);
			}
		}
		Page<InvestorInfoVO> page = new Page<InvestorInfoVO>(start, totalSize,
			pageParam.getPageSize(), voList);
		return page;
	}
	
	@Override
	public long countCertNoByRole(String certNo, String roleCode) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("certNo", certNo);
		conditions.put("roleCode", roleCode);
		return personalInfoDAO.countCertNoByRole(conditions);
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public JSONObject registerFromYJF(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo,
										int... roleIds) throws Exception {
		JSONObject json = new JSONObject();
		long parentId = 0l;
		
		if ("".equals(personalInfo.getReferees()) || personalInfo.getReferees() == null) {
			try {
				Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
					null, getDefaultBrokerUserId(), null);
				List<UserRelationDO> lst = page.getResult();
				if (lst.size() > 0) {
					parentId = getDefaultBrokerUserId();
					personalInfo.setReferees(lst.get(0).getMemberNo());
				}
			} catch (Exception e) {
				json.put("code", 0);
				json.put("message", "服务处理异常");
				logger.error("服务处理异常", e);
				return json;
			}
		} else {
			Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null,
				null, personalInfo.getReferees());
			List<UserRelationDO> lst = page.getResult();
			if (lst.size() > 0) {
				parentId = lst.get(0).getChildId();
			} else {
				json.put("code", 0);
				json.put("message", "推荐人不存在");
				logger.error("message", "推荐人不存在");
				return json;
			}
		}
		String accountId = userBaseInfo.getAccountId();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("accountId", accountId);
		condition.put("limitStart", 0);
		condition.put("pageSize", 10);
		List<UserBaseInfoDO> userBaseList = userBaseInfoDAO.queryAllUserInfoList(condition);
		condition.put("userName", userBaseInfo.getUserName());
		long count = userBaseInfoDAO.validationUserName(condition);
		if (userBaseList.size() > 0) {
			json.put("code", 1);
			json.put("message", "此用户资金账户已存在" + AppConstantsUtil.getProductName());
			return json;
		} else if (count > 0) {
			json.put("code", 0);
			json.put("message", "此用户已存在" + AppConstantsUtil.getProductName());
			return json;
		} else {
			User user = null;
			try {
				user = authorityService.addUser(userBaseInfo.getUserName(), roleIds);
			} catch (Exception e) {
				logger.error("添加用户registerFromYJF", e);
				json.put("code", 0);
				json.put("message", "托管机构用户新增到User中失败");
				return json;
			}
			String externalId = BusinessNumberUtil.gainNumber();
			userBaseInfo.setUserBaseId(externalId);
			userBaseInfo.setUserId(user.getId());
			userBaseInfo.setRealNameAuthentication("IS");
			userBaseInfo.setMailBinding("IS");
			userBaseInfo.setMobileBinding("IS");
			userBaseInfo.setState("normal");
			userBaseInfo.setType("GR");
			String userBaseId = userBaseInfoManager.addUserBaseInfo(userBaseInfo);
			if (StringUtil.isBlank(userBaseId)) {
				throw new Exception("处理失败");
			}
			personalInfo.setUserBaseId(userBaseId);
			personalInfoDAO.insert(personalInfo);
			if (parentId <= 0) {
				for (int i : roleIds) {
					if (i == 12) {
						parentId = getDefaultBrokerUserId();
					}
				}
			}
			if (parentId > 0) {
				UserRelationDO userRelation = new UserRelationDO();
				userRelation.setParentId(parentId);
				userRelation.setChildId(user.getId());
				userRelation.setMemberNo(getMemberNo(parentId));
				UserRelationReturnEnum relationReturnEnum = userRelationManager
					.insert(userRelation);
				if (relationReturnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
					UserBaseInfoDO curParentJG = getParentJg(parentId);//获取营销机构
					int countNew = userRelationManager.countInvestorsInThisJG(curParentJG
						.getUserId());
					curParentJG.setExIdentityNo(String.valueOf(countNew));
					userBaseInfoManager.updateUserBaseInfo(curParentJG);
					json.put("code", 1);
					json.put("message", "新增用户到" + AppConstantsUtil.getProductName() + "成功");
				}
				if (relationReturnEnum == UserRelationReturnEnum.EXECUTE_FAILURE) {
					json.put("code", 0);
					json.put("message", "托管机构用户新增到机构时失败");
				}
			}
		}
		return json;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public PersonalReturnEnum updatePersonalInfoAndChangBroke(PersonalInfoDO personalInfo,
																UserBaseInfoDO userBaseInfo,
																boolean unbindRole, long parentId,
																String brokerNo, int... roleIds)
																								throws Exception {
		PersonalReturnEnum returnEnum = updatePersonalInfo(personalInfo, userBaseInfo, unbindRole,
			roleIds);
		if (returnEnum != PersonalReturnEnum.EXECUTE_SUCCESS) {
			throw new Exception("更新失败");
		}
		UserRelationReturnEnum returnEnum1 = userRelationManager.changeBroker(
			userBaseInfo.getUserId(), parentId, brokerNo);
		if (returnEnum1 != UserRelationReturnEnum.EXECUTE_SUCCESS) {
			throw new Exception("变更经济人失败");
		}
		return PersonalReturnEnum.EXECUTE_SUCCESS;
	}
}
