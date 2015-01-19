package com.icebreak.p2p.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredToolsService;
import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.daointerface.UserLoginLogDao;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.user.InstitutionsInfoManager;
import com.icebreak.p2p.user.PersonalInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.yiji.openapi.sdk.message.yzz.YzzUserSpecialRegisterRequest;
import com.yiji.openapi.sdk.message.yzz.YzzUserSpecialRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBaseInfoManagerImpl extends BaseAutowiredToolsService implements
																		UserBaseInfoManager {
	@Autowired
	protected UserRelationManager		userRelationManager;
	@Autowired
	protected InstitutionsInfoManager	institutionsInfoManager;
	@Autowired
	protected TradeService				tradeService;
	@Autowired
	protected PersonalInfoManager personalInfoManager;
	@Autowired
	UserLoginLogDao						userLoginLogDao;
	@Autowired
	private TradeDetailDao				tradeDetailDao;
	
	@Override
	public String addUserBaseInfo(UserBaseInfoDO userBaseInfo) throws Exception {
		return userBaseInfoDAO.insert(userBaseInfo);
	}
	
	@Override
	public UserBaseReturnEnum updateUserBaseInfo(UserBaseInfoDO userBaseInfo) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		long resultSet = userBaseInfoDAO.update(userBaseInfo);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseReturnEnum validationLogPassword(String userBaseId, String logPassword)
																							throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.PASSWORD_ERROR;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("logPassword", logPassword);
		long resultSet = userBaseInfoDAO.validationLogPassword(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.PASSWORD_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseReturnEnum updateLogPassword(String userBaseId, String logPassword)
																						throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("logPassword", logPassword);
		long resultSet = userBaseInfoDAO.updateLogPassword(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}

	@Override
	public UserBaseReturnEnum updateLogPasswordByUserNameAndOldPwd(String userName, String newPwd, String oldPwd) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userName", userName);
		condition.put("newPwd", newPwd);
		condition.put("oldPwd", oldPwd);
		long resultSet = userBaseInfoDAO.updateLogPasswordByUserNameAndOldPwd(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}

	@Override
	public UserBaseReturnEnum validationPayPassword(String userBaseId, String payPassword)
																							throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.PASSWORD_ERROR;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("payPassword", payPassword);
		long resultSet = userBaseInfoDAO.validationPayPassword(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.PASSWORD_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseReturnEnum updatePayPassword(String userBaseId, String payPassword)
																						throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("payPassword", payPassword);
		long resultSet = userBaseInfoDAO.updatePayPassword(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseReturnEnum updateLogPasswordAndPayPassword(String userBaseId,
																String logPassword,
																String payPassword)
																					throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("logPassword", logPassword);
		condition.put("payPassword", payPassword);
		long resultSet = userBaseInfoDAO.updateLogPasswordAndPayPassword(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseReturnEnum updateState(String userBaseId, String state) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("state", state);
		long resultSet = userBaseInfoDAO.updateState(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseReturnEnum updateAccountId(String userBaseId, String accountId) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("accountId", accountId);
		long resultSet = userBaseInfoDAO.updateAccountId(condition);
		if (resultSet > 0) {
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseReturnEnum validationUserName(String userName) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.USERNAME_ERROR;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userName", userName);
		long resultSet = userBaseInfoDAO.validationUserName(condition);
		if (resultSet <= 0) {
			returnEnum = UserBaseReturnEnum.USERNAME_SUCCESS;
		}
		return returnEnum;
	}
	
	@Override
	public UserBaseInfoDO queryByUserName(String userName, long roleId) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userName", userName);
		if (roleId > 0) {
			condition.put("roleId", roleId);
		}
		UserBaseInfoDO userBaseInfo = null;
		List<UserBaseInfoDO> userBaseInfos = userBaseInfoDAO.queryByUserName(condition);
		if (userBaseInfos != null && userBaseInfos.size() > 0) {
			userBaseInfo = userBaseInfos.get(0);
		}
		return userBaseInfo;
	}
	
	@Override
	public UserBaseInfoDO queryByUserBaseId(String userBaseId) throws Exception {
		UserBaseInfoDO userBaseInfo = userBaseInfoDAO.queryByUserBaseId(userBaseId);
		return userBaseInfo;
	}
	
	@Override
	public UserBaseInfoDO queryByMD5UserBaseId(String MD5UserBaseId) throws Exception {
		UserBaseInfoDO userBaseInfo = userBaseInfoDAO.queryByMD5UserBaseId(MD5UserBaseId);
		return userBaseInfo;
	}
	
	@Override
	public List<UserBaseInfoDO> queryByType(String type, String userId) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("type", type);
		condition.put("userId", userId);
		List<UserBaseInfoDO> userBaseInfoList = userBaseInfoDAO.queryByType(condition);
		return userBaseInfoList;
	}
	
	@Override
	public long queryUserId(String identityName, String userName, String realName, String type) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userName", userName);
		condition.put("realName", realName);
		condition.put("identityName", identityName);
		condition.put("type", type);
		return userBaseInfoDAO.queryUserId(condition);
		
	}
	
	@Override
	public String queryUserBaseId(String identityName, String type) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("identityName", identityName);
		condition.put("type", type);
		return userBaseInfoDAO.queryUserBaseId(condition);
	}
	
	@Override
	public UserBaseInfoDO getRealNameByUserName(String name) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userName", name);
		return userBaseInfoDAO.queryRealname(condition);
	}
	
	@Override
	public long getRolesByUserIdCount(Map<String, Object> params) {
		return roleDao.getRolesByUserIdCount(params);
	}
	
	@Override
	public List<Role> getRolesByUserId(Map<String, Object> params) {
		return roleDao.getRolesByUserId(params);
	}
	
	@Override
	public List<TradeQueryDetail> getTradeDetailByConditions(Map<String, Object> conditions) {
		
		return tradeDetailDao.getTradeDetailByConditions(conditions);
	}
	
	@Override
	public UserBaseReturnEnum validationReferees(String referees) {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null, null,
			referees);
		List<UserRelationDO> lst = page.getResult();
		if (lst.size() > 0)
			returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		return returnEnum;
	}
	
	@Override
	public Page<UserBaseInfoDO> allUserInfo(QueryConditions queryConditions, PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userBaseId", queryConditions.getUserBaseId());
		condition.put("userName", queryConditions.getUserName());
		condition.put("accountName", queryConditions.getAccountName());
		condition.put("accountId", queryConditions.getAccountId());
		condition.put("realName", queryConditions.getRealName());
		condition.put("certNo", queryConditions.getCertNo());
		condition.put("state", queryConditions.getState());
		// 查询总条数
		int totalSize = (int) userBaseInfoDAO.queryAllUserInfoCount(condition);
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		// 查询总集合
		List<UserBaseInfoDO> list = userBaseInfoDAO.queryAllUserInfoList(condition);
		Page<UserBaseInfoDO> page = new Page<UserBaseInfoDO>(start, totalSize,
			pageParam.getPageSize(), list);
		return page;
	}
	
	@Override
	public UserBaseReturnEnum resetPayPassword(String userBaseId) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userBaseId", userBaseId);
		condition.put("payPassword", "888888");
		userBaseInfoDAO.updatePayPassword(condition);
		returnEnum = UserBaseReturnEnum.EXECUTE_SUCCESS;
		return returnEnum;
	}
	
	@Override
	public String getBaseId(long userId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", userId);
		return userBaseInfoDAO.queryUserBaseId(condition);
	}
	
	
	@Override
	public JSONObject validationBankCardByOpenApi(OpeningBankQueryOrder cardInfo) {
		JSONObject json = new JSONObject();
		BankInfoResult bankInfoResult = this.openingBankService.findCardByCardNo(cardInfo);
		if (bankInfoResult.getCardInfo() != null) {
			json.put("code", 1);
			json.put("message", "验证成功");
		} else {
			json.put("code", 0);
			json.put("message", bankInfoResult.getMessage());
		}
		return json;
	}
	
	@Override
	public JSONObject registerByOpenApi(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo) {
		return null;
	}
	
	@Override
	public UserBaseInfoDO queryByUserId(long userId) {
		
		return userBaseInfoDAO.queryByUserId(userId);
	}
	
	@Override
	public long countMailOrMobileByRole(Map<String, Object> map) {
		
		return userBaseInfoDAO.countMailOrMobileByRole(map);
	}
	
	@Override
	public UserBaseInfoDO queryByAccountId(String accountId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("accountId", accountId);
		condition.put("limitStart", 0);
		condition.put("pageSize", 10);
		List<UserBaseInfoDO> users = userBaseInfoDAO.queryAllUserInfoList(condition);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addUserLoginLog(UserLoginLog userLoginLog) throws Exception {
		try {
			userLoginLogDao.insert(userLoginLog);
		} catch (Exception e) {
			logger.error("新增用户登录信息异常", e);
			throw new Exception("新增用户登录信息异常");
		}
		
	}
	
	@Override
	public List<UserLoginLog> queryUserLoginLogByUserId(long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<UserLoginLog> logList = userLoginLogDao.queryLog(map);
		return logList;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void executeUnlockUserJob() {
		List<UserBaseInfoDO> lockedUsers = userBaseInfoDAO.queryLockedUserList();
		for (UserBaseInfoDO user : lockedUsers) {
			try {
				user.setPwdErrorCount(0);
				user.setState("normal");
				updateUserBaseInfo(user);
			} catch (Exception e) {
				logger.error("重置锁定用户信息失败", e);
			}
		}
	}
	
	@Override
	public long queryChildrenCountByCondition(Map<String, Object> condition) {
		return userBaseInfoDAO.queryChildrenCountByCondition(condition);
	}
	
	@Override
	public List<UserBaseInfoDO> queryChildrenListByCondition(Map<String, Object> condition) {
		return userBaseInfoDAO.queryChildrenListByCondition(condition);
	}
	
	@Override
	public String getCertNoByUserId(long userId) {
		String certNo = null;
		UserBaseInfoDO user = queryByUserId(userId);
		try {
			PersonalInfoDO person = personalInfoManager.query(user.getUserBaseId());
			certNo = person.getCertNo();
		} catch (Exception e) {
			logger.error("getCertNoByUserId is error", e);
		}
		
		return certNo;
	}
	
	public YzzUserSpecialRegisterResponse doRegYJFAccount(PersonalInfoDO personalInfo,
															UserBaseInfoDO userBaseInfo) {
		try {
			YzzUserSpecialRegisterRequest request = new YzzUserSpecialRegisterRequest(
					userBaseInfo.getAccountName(), userBaseInfo.getRealName(), userBaseInfo.getMail(),
					userBaseInfo.getMobile(), personalInfo.getCertNo());
			request.setCertValidTime(personalInfo.getBusinessPeriod());
			YzzUserSpecialRegisterResponse registerResponse = yzzGatewayService
					.yzzUserRegister(request);
			return registerResponse;
		} catch (Exception e) {
			return null;
		}

	}
	
}
