package com.icebreak.p2p.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icebreak.p2p.base.BaseAutowiredToolsService;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.ProfitAsignInfo;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.user.PersonalInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserRelationManagerImpl extends BaseAutowiredToolsService implements
																		UserRelationManager {
	
	@Autowired
	protected PersonalInfoManager	personalInfoManager;
	@Autowired
	protected UserBaseInfoManager	userBaseInfoManager;
	private final static String		JGAGENTPREFIX		= "";	//机构经纪人前缀 
	private final static String		JJRAGENTPREFIX		= "K";	//经纪人下的投资人前缀
	private final static int		MEMBERSCALEDEFULT	= 5;	//机构人员规模默认
	private final static int		MEMBERSCALEVIP		= 8;	//机构人员规模高级
																
	@Override
	public UserRelationReturnEnum insert(UserRelationDO userRelation) throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		long userRelationId = userRelationDAO.insert(userRelation);
		if (userRelationId > 0) {
			returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserRelationReturnEnum update(UserRelationDO userRelation) throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		long resultSet = userRelationDAO.update(userRelation);
		if (resultSet > 0) {
			returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserRelationReturnEnum delete(long userRelationId) throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		long resultSet = userRelationDAO.delete(userRelationId);
		if (resultSet > 0) {
			returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public int getRelationsCountByConditions(Long userRelationId, Long parentId, Long childId,
												String memberNo) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userRelationId", userRelationId);
		conditions.put("parentId", parentId);
		conditions.put("childId", childId);
		conditions.put("memberNo", memberNo);
		conditions.put("relationStatus", "normal");
		return userRelationDAO.getRelationsCountByConditions(conditions);
	}
	
	@Override
	public Page<UserRelationDO> getRelationsByConditions(Long userRelationId, Long parentId,
															Long childId, String memberNo) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userRelationId", userRelationId);
		conditions.put("parentId", parentId);
		conditions.put("childId", childId);
		conditions.put("memberNo", memberNo);
		int totalSize = userRelationDAO.getRelationsCountByConditions(conditions);
		return new Page<UserRelationDO>(0, totalSize, totalSize,
			userRelationDAO.getRelationsByConditions(conditions));
	}
	
	@Override
	public int countInvestorsInThisJG(long userId) {
		return userRelationDAO.countInvestorsInThisJG(userId);
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public UserRelationReturnEnum changeBroker(long childId, long parentId, String referees)
																							throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		String memo = getMemberNo(parentId);
		Page<UserRelationDO> page = getRelationsByConditions(null, null, childId, null);
		if (page.getResult() != null && page.getResult().size() > 0) {
			UserRelationDO userRelation = page.getResult().get(0);
			if (isMoveMarketting(parentId, userRelation.getParentId())) {
				userRelation.setMemberNo(memo);
			}
			userRelation.setParentId(parentId);
			long resultSet = userRelationDAO.update(userRelation);
			if (resultSet > 0) {
				returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
				UserBaseInfoDO curParentJG = getParentJg(parentId);//获取营销机构
				int countNew = countInvestorsInThisJG(curParentJG.getUserId());
				curParentJG.setExIdentityNo(String.valueOf(countNew));
				userBaseInfoManager.updateUserBaseInfo(curParentJG);
				UserBaseInfoDO thisUser = userBaseInfoManager.queryByUserId(childId);
				if ("GR".equals(thisUser.getType())) {
					PersonalInfoDO person = personalInfoManager.query(thisUser.getUserBaseId());
					person.setReferees(referees);
					personalInfoManager.updatePersonalInfo(person, null, false, null);
				} else {
					InstitutionsInfoDO institution = institutionsInfoDAO.query(thisUser
						.getUserBaseId());
					institution.setReferees(referees);
					institutionsInfoDAO.update(institution);
				}
				
			}
		}
		
		return returnEnum;
	}
	
	private boolean isMoveMarketting(long parentId, long childId) throws Exception {
		boolean flag = true;
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("childId", childId);
		List<UserRelationDO> relations = userRelationDAO.getRelationsByConditions(conditions);
		UserRelationDO thisRelation = relations.get(0);
		UserBaseInfoDO curParentJG = getParentJg(parentId);//获取营销机构
		if (thisRelation.getParentId() == curParentJG.getUserId()) {
			flag = false;
		}
		return flag;
	}
	
	private String getMemberNo(long parentId) throws Exception {
		String memberNo = null;
		Page<UserRelationDO> userRelationsPage = getRelationsByConditions(null, null, parentId,
			null);
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
				int count = countInvestorsInThisJG(curParentJG.getUserId());
				boolean availabelFlag = false;
				while (!availabelFlag) {
					count++;
					int curindex = count;
					if (String.valueOf(curindex).endsWith("4")) {
						curindex++;
					}
					
					int memberScale = 0;
					if ("高级机构".equals(curParentJG.getType())) {
						memberScale = MEMBERSCALEVIP;
					} else {
						memberScale = MEMBERSCALEDEFULT;
					}
					String sino = null;
					if (curindex <= 99999) {
						sino = StringUtils.leftPad(String.valueOf(curindex), memberScale, "0");
					} else {
						sino = curindex + "";
					}
					String jgIdentity = curParentJG.getIdentityName();
					memberNo = jgIdentity + JJRAGENTPREFIX + sino;
					Page<UserRelationDO> page = getRelationsByConditions(null, null, null, memberNo);
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
	
	private UserBaseInfoDO getParentJg(long parentId) throws Exception {
		UserBaseInfoDO curParentJG = null;
		Page<UserRelationDO> userRelationsPage = getRelationsByConditions(null, null, parentId,
			null);
		if (userRelationsPage.getResult() != null && userRelationsPage.getResult().size() > 0) {
			for (int i = 0; i < userRelationsPage.getResult().size(); i++) {
				List<UserBaseInfoDO> curParentJGs = userBaseInfoManager.queryByType("JG",
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
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public UserRelationReturnEnum changeBrokerMarketting(long brokerUserId, long parentId)
																							throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		String memo = getJJRMemberNo(parentId);
		Page<UserRelationDO> page = getRelationsByConditions(null, null, brokerUserId, null);
		if (page.getResult() != null && page.getResult().size() > 0) {
			UserRelationDO userRelation = page.getResult().get(0);
			userRelation.setParentId(parentId);
			userRelation.setMemberNo(memo);
			Page<UserRelationDO> investors = getRelationsByConditions(null, brokerUserId, null,
				null);
			if (investors.getResult() != null && investors.getResult().size() > 0) {
				for (UserRelationDO investor : investors.getResult()) {
					investor.setMemberNo(getNewInvMemberNo(parentId));
					long resultSet2 = userRelationDAO.update(investor);
					if (resultSet2 > 0) {
						UserBaseInfoDO curParentJG = getParentJg(brokerUserId);//获取营销机构
						int countNew = countInvestorsInThisJG(curParentJG.getUserId());
						curParentJG.setExIdentityNo(String.valueOf(countNew));
						userBaseInfoManager.updateUserBaseInfo(curParentJG);
						UserBaseInfoDO thisUser = userBaseInfoManager.queryByUserId(investor
							.getChildId());
						if ("GR".equals(thisUser.getType())) {
							PersonalInfoDO person = personalInfoManager.query(thisUser
								.getUserBaseId());
							person.setReferees(memo);
							personalInfoManager.updatePersonalInfo(person, null, false, null);
						} else {
							InstitutionsInfoDO institution = institutionsInfoDAO.query(thisUser
								.getUserBaseId());
							institution.setReferees(memo);
							institutionsInfoDAO.update(institution);
						}
					}
				}
			}
			long resultSet = userRelationDAO.update(userRelation);
			if (resultSet > 0) {
				returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
			}
		}
		return returnEnum;
	}
	
	private String getNewInvMemberNo(long parentId) throws Exception {
		String memberNo = null;
		List<UserBaseInfoDO> curParentJGs = userBaseInfoManager.queryByType("JG",
			String.valueOf(parentId));
		UserBaseInfoDO curParentJG = null;
		if (curParentJGs != null && curParentJGs.size() > 0) {
			curParentJG = curParentJGs.get(0);
		} else {
			logger.info("经纪人的机构没找到，机构Id" + parentId);
		}
		int count = countInvestorsInThisJG(curParentJG.getUserId());
		boolean availabelFlag = false;
		while (!availabelFlag) {
			count++;
			int curindex = count;
			if (String.valueOf(curindex).endsWith("4")) {
				curindex++;
			}
			
			int memberScale = 0;
			if ("高级机构".equals(curParentJG.getType())) {
				memberScale = MEMBERSCALEVIP;
			} else {
				memberScale = MEMBERSCALEDEFULT;
			}
			
			String sino = StringUtils.leftPad(String.valueOf(curindex), memberScale, "0");
			String jgIdentity = curParentJG.getIdentityName();
			memberNo = jgIdentity + JJRAGENTPREFIX + sino;
			Page<UserRelationDO> page = getRelationsByConditions(null, null, null, memberNo);
			if (page.getResult() != null && page.getResult().size() > 0) {
				availabelFlag = false;
			} else {
				availabelFlag = true;
				logger.info("可用投资人编号：" + memberNo);
			}
		}
		return memberNo;
	}
	
	private String getJJRMemberNo(long parentId) throws Exception {
		List<UserBaseInfoDO> parentJGs = userBaseInfoManager.queryByType("JG",
			String.valueOf(parentId));
		UserBaseInfoDO parentJG = null;
		if (parentJGs != null && parentJGs.size() > 0) {
			parentJG = parentJGs.get(0);
		} else {
			logger.error("未找到该机构-----" + parentId);
		}
		String memberNo = null;
		String indentity = parentJG.getIdentityName();
		int startNo = Integer.parseInt(parentJG.getIdentityStartNo());
		int endNo = Integer.parseInt(parentJG.getIdentityEndNo());
		if (startNo == 0) {
			startNo++;
		}
		//int count = userRelationManager.getRelationsCountByConditions(null, parentId , null , null);
		int count = 0;
		boolean availabelFlag = false;
		while (!availabelFlag) {
			int currentNo = startNo + count;

			if (currentNo > endNo) {
				logger.error("编号已满-----" + parentId);
			}
			int memberScale = 0;

			if ("高级机构".equals(parentJG.getType())) {
				memberScale = MEMBERSCALEVIP;
			} else {
				memberScale = MEMBERSCALEDEFULT;
			}

            String sino = StringUtils.leftPad(String.valueOf(currentNo), memberScale, "0");
            memberNo = indentity + JGAGENTPREFIX + sino;//串号拼接
            Page<UserRelationDO> page2 = getRelationsByConditions(null, null, null, memberNo);

            if(AppConstantsUtil.getBrokerIsNumber()){
                memberScale = parentJG.getIdentityEndNo().length();
            }

			sino = StringUtils.leftPad(String.valueOf(currentNo), memberScale, "0");
			memberNo = indentity + JGAGENTPREFIX + sino;//串号拼接 
			Page<UserRelationDO> page = getRelationsByConditions(null, null, null, memberNo);
			if ((page.getResult() != null && page.getResult().size() > 0) ||(page2.getResult() != null && page2.getResult().size() > 0) ) {
				count++;
			} else {
				availabelFlag = true;
				logger.info("可用经纪人编号：" + memberNo);
			}
		}
		return memberNo;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public UserRelationReturnEnum insertProfit(ProfitAsignInfo profitAsignInfo) throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		try {
			userRelationDAO.addProfit(profitAsignInfo);
			returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
		} catch (Exception e) {
			logger.error("新投资人利润分配异常", e);
			throw new Exception("分配投资利润额度时异常");
		}
		
		return returnEnum;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public UserRelationReturnEnum updateProfit(ProfitAsignInfo profitAsignInfo) throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		try {
			int count = userRelationDAO.updateProfit(profitAsignInfo);
			if (count > 0) {
				returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
			}
		} catch (Exception e) {
			logger.error("分配投资利润额度更新时异常", e);
			throw new Exception("分配投资利润额度更新时异常");
		}
		return returnEnum;
	}
	
	@Override
	public ProfitAsignInfo queryByReceiveId(long receiveId) {
		ProfitAsignInfo info = userRelationDAO.getProfit(receiveId);
		return info;
	}
	
	@Override
	public ProfitAsignInfo queryByReceiveIdAndDistributionId(long receiveId, long distributionId) {
		ProfitAsignInfo info = userRelationDAO.getProfit(receiveId, distributionId);
		return info;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public UserRelationReturnEnum deleteProfitAsign(String tblBaseId) throws Exception {
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		try {
			
			int result = userRelationDAO.deleteProfitAsign(tblBaseId);
			if (result > 0) {
				returnEnum = UserRelationReturnEnum.EXECUTE_SUCCESS;
			}
		} catch (Exception e) {
			logger.error("删除分配投资利润额度时异常", e);
			throw new Exception("删除分配投资利润额度时异常");
		}
		return returnEnum;
	}
}
