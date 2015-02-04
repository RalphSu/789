package com.icebreak.p2p.backstage.controller.usermanage;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.daointerface.UserGoldExperienceDao;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.dataobject.DOEnum.ActivityTypeEnum;
import com.icebreak.p2p.integration.openapi.order.MobileBindOrder;
import com.icebreak.p2p.integration.openapi.order.PersonalCertOrder;
import com.icebreak.p2p.integration.openapi.order.RegisterOrder;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.ApplicationConstant;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.NumberUtil;
import com.icebreak.p2p.web.util.WebUtil;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.p2p.ws.enums.BooleanEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.util.lang.exception.ResultException;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.MoneyUtil;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.message.yzz.YzzUserSpecialRegisterResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("backstage/userManage")
public class PersonalController extends BaseAutowiredController {
	/**
	 * 通用页面路径
	 */
	String USER_MANAGE_PATH = "/backstage/userManage/";
	
	public long getYrdBrokerUserId() throws Exception {
		UserBaseInfoDO brokerUser = userBaseInfoManager.queryByUserName(
			AppConstantsUtil.getDefaultBrokerUserName(), ApplicationConstant.ROLE_ID_BROKER);
		return brokerUser.getUserId();
	}
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "changeLockTime", "startCreateTime", "endCreateTime",
								"startUpdateTime", "endUpdateTime" };
	}

	@RequestMapping("toModifyPwd")
	public String toModifyPwd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String userName = SessionLocalManager.getSessionLocal().getUserName();
		model.addAttribute("userName", userName);
		return USER_MANAGE_PATH + "modifyPwd.vm";
	}

	@RequestMapping("modifyPwd")
	public String modifyPwd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String userName = SessionLocalManager.getSessionLocal().getUserName();
		model.addAttribute("userName", userName);
		String oldPwd = request.getParameter("oldPwd");
		String newPwd1 = request.getParameter("newPwd1");
		String newPwd2 = request.getParameter("newPwd2");
		if(oldPwd == null || "".equals(oldPwd.trim())){
			model.addAttribute("message","请输入旧密码！");
			return USER_MANAGE_PATH + "modifyPwd.vm";
		}

		if(newPwd1 == null || newPwd2 == null || "".equals(newPwd1.trim())){
			model.addAttribute("message","请输入新密码！");
			return USER_MANAGE_PATH + "modifyPwd.vm";
		}

		if(!newPwd1.equals(newPwd2)){
			model.addAttribute("message","两次输入的密码不一致！");
			return USER_MANAGE_PATH + "modifyPwd.vm";
		}
		//执行密码修改
		UserBaseReturnEnum returnEnum = userBaseInfoManager.updateLogPasswordByUserNameAndOldPwd(userName, newPwd1, oldPwd);
		if(returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS){
			model.addAttribute("message","密码修改成功！");
			return USER_MANAGE_PATH + "modifyPwd.vm";
		}

		model.addAttribute("message","密码修改失败！可能的原因：原密码错误？");
		return USER_MANAGE_PATH + "modifyPwd.vm";
	}
	
	@RequestMapping("personalManage")
	public String personalManage(QueryConditions queryConditions, PageParam pageParam,
									HttpServletResponse response, Model model) throws Exception {
		Page<PersonalInfoDO> page = personalInfoManager.page(queryConditions, pageParam);
		model.addAttribute("queryConditions", queryConditions);
		model.addAttribute("page", page);
		//体验金类型
		List<GoldExperienceDO> list = goldExperienceDao.queryMay();
		List<GoldExperienceDO> avaliableList = new ArrayList<GoldExperienceDO>();
		for(GoldExperienceDO goldExperience : list){
			if(goldExperience.getSurplusQuantity() > 0){
				avaliableList.add(goldExperience);
			}
		}
		model.addAttribute("goldExps", avaliableList);
		response.setHeader("Pragma", "No-cache");
		return USER_MANAGE_PATH + "personalManage.vm";
	}
	
	@RequestMapping("unRealNamePass")
	public String unRealNamePass(QueryConditions queryConditions, PageParam pageParam, Model model)
																									throws Exception {
		Page<PersonalInfoDO> page = personalInfoManager
			.queryRealNameSta(queryConditions, pageParam);
		model.addAttribute("queryConditions", queryConditions);
		model.addAttribute("page", page);
		return USER_MANAGE_PATH + "unRealNamePass.vm";
	}
	
	@RequestMapping("personalManage/addPersonalUser")
	public String addPersonalUser(long parentId, Model model) throws Exception {
		model.addAttribute("parentId", parentId);
		model.addAttribute("uploadHost", "");
		if (parentId == 1) {
			return USER_MANAGE_PATH + "addAdmin.vm";
		} else {
			return USER_MANAGE_PATH + "addPersonalUser.vm";
		}
	}
	
	@RequestMapping("personalManage/updatePersonalUser")
	public String updatePersonalUser(String userBaseId, long userId, Model model) throws Exception {
		model.addAttribute("uploadHost", "");
		UserBaseInfoDO userBase = userBaseInfoManager.queryByUserBaseId(userBaseId);
		if ("JG".equals(userBase.getType())) {
			return updateInstitutionsUser(userBaseId, userId, model);
		} else {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			
			List<Role> roleList = authorityService.getRolesByUserId(userId, 1, 12).getResult();
			model.addAttribute("info", personalInfo);
			model.addAttribute("roleList", roleList);
			if (personalInfo.getRealNameAuthentication() == null
				|| "".equals(personalInfo.getRealNameAuthentication())
				|| "NO".equals(personalInfo.getRealNameAuthentication())) {
				model.addAttribute("personlRealNameStatus", "N");
				return USER_MANAGE_PATH + "updatePersonalUser.vm";
			} else {
				if (!"".equals(personalInfo.getBankType()) && personalInfo.getBankType() != null) {
					String bankName = personalInfo.getBankType();
					List<BankBasicInfo> bankList = this.bankDataService.getBankBasicInfos();
					for (BankBasicInfo bankBasicInfo : bankList) {
						if (bankName.equals(bankBasicInfo.getBankCode())) {
							bankName = bankBasicInfo.getBankName();
						}
					}
					model.addAttribute("bankName", bankName);
				}
				return USER_MANAGE_PATH + "personalUserInfo.vm";
			}
		}
	}
	
	public String updateInstitutionsUser(String userBaseId, long userId, Model model)
																						throws Exception {
		model.addAttribute("uploadHost", "");
		List<Role> roleList = authorityService.getRolesByUserId(userId, 1, 12).getResult();
		InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
		model.addAttribute("roleList", roleList);
		model.addAttribute("info", institutionsInfo);
		return USER_MANAGE_PATH + "updateInstitutionsUser.vm";
	}
	
	@RequestMapping("personalManage/detailPersonalInfo")
	public String detailPersonalInfo(String userBaseId, long userId, String memberNo, Model model)
																									throws Exception {
		model.addAttribute("uploadHost", "");
		PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
		List<Role> roleList = authorityService.getRolesByUserId(userId, 1, 12).getResult();
		long parentId = -2;
		if (roleList.get(0).getId() == 12) {
			Page<UserRelationDO> userRelation = userRelationManager.getRelationsByConditions(null,
				null, null, personalInfo.getReferees());
			parentId = userRelation.getResult().get(0).getParentId();
			long childId = userRelation.getResult().get(0).getChildId();
			UserBaseInfoDO user = userBaseInfoManager.queryByUserId(childId);
			model.addAttribute("refereesName", user == null ? "" : user.getRealName());
		} else if (roleList.get(0).getId() == 11) {
			if (memberNo != null && !"".equals(memberNo)) {
				Page<UserRelationDO> userRelation = userRelationManager.getRelationsByConditions(
					null, null, null, memberNo);
				parentId = userRelation.getResult().get(0).getParentId();
			}
			
		}
		UserBaseInfoDO userBase = userBaseInfoManager.queryByUserId(parentId);
		if (userBase != null)
			model.addAttribute("institution", userBase.getRealName());
		model.addAttribute("info", personalInfo);
		model.addAttribute("roleList", roleList);
		if (!"".equals(personalInfo.getBankType()) && personalInfo.getBankType() != null) {
			String bankName = personalInfo.getBankType();
			List<BankBasicInfo> bankList = this.bankDataService.getBankBasicInfos();
			for (BankBasicInfo bankBasicInfo : bankList) {
				if (bankName.equals(bankBasicInfo.getBankCode())) {
					bankName = bankBasicInfo.getBankName();
				}
			}
			model.addAttribute("bankName", bankName);
		}
		return USER_MANAGE_PATH + "detailPersonalInfo.vm";
	}
	
	@ResponseBody
	@RequestMapping("personalManage/addPersonalUserSubmit")
	public Object addPersonalUserSubmit(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo,
										long parentId, int... roleIds) {
		JSONObject jsonobj = new JSONObject();

		//用户注册将手机作为用户名
		userBaseInfo.setUserName(userBaseInfo.getMobile());
		//生成平台关联的托管机构帐户
		userBaseInfo
			.setAccountName(AppConstantsUtil.getYrdPrefixion() + userBaseInfo.getUserName());
		if (personalInfo.getReferees() == null || "".equals(personalInfo.getReferees())) {
			for (int i : roleIds) {
				if (i == ApplicationConstant.ROLE_ID_INVESTOR) {
					try {
						long userId = getYrdBrokerUserId();
						Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(
							null, null, userId, null);
						List<UserRelationDO> lst = page.getResult();
						if (lst.size() > 0) {
							personalInfo.setReferees(lst.get(0).getMemberNo());
						}
						parentId = userId;
					} catch (Exception e) {
						jsonobj.put("code", 0);
						jsonobj.put("message", "创建个人用户失败");
						logger.error("addPersonalUserSubmit", e);
						return jsonobj;
					}
				}
			}
		} else {
			for (int i : roleIds) {
				if (i == ApplicationConstant.ROLE_ID_INVESTOR) {
					Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
						null, null, personalInfo.getReferees());
					List<UserRelationDO> lst = page.getResult();
					if (lst.size() > 0) {
						parentId = lst.get(0).getChildId();
					} else {
						jsonobj.put("code", 0);
						jsonobj.put("message", "该推荐人不可用");
						return jsonobj;
					}
				}
			}
		}
		//FIXME 生成平台关联的托管机构帐户
		userBaseInfo
			.setAccountName(AppConstantsUtil.getYrdPrefixion() + userBaseInfo.getUserName());
		PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		try {
			//设置密码
			userBaseInfo.setLogPassword("888888");
			userBaseInfo.setPayPassword("888888");
			userBaseInfo.setMobileBinding("NO");
			userBaseInfo.setMailBinding("NO");
			if (StringUtil.isNotEmpty(personalInfo.getCertNo())) {
				int gender = CommonUtil.getGender(personalInfo.getCertNo());
				if (gender != 3) {
					personalInfo.setGender(gender);
				}
			}
			if (sysFunctionConfigService.isAllEconomicMan()) {
				boolean hasInvestor = false;
				boolean hasBroker = false;
				int[] roleIdsNew = new int[roleIds.length + 1];
				for (int i = 0; i < roleIds.length; i++) {
					roleIdsNew[i] = roleIds[i];
					if (roleIds[i] == SysUserRoleEnum.INVESTOR.getValue()) {
						roleIdsNew[roleIdsNew.length - 1] = SysUserRoleEnum.BROKER.getValue();
						hasInvestor = true;
					}
					if (roleIds[i] == SysUserRoleEnum.BROKER.getValue()) {
						hasBroker = true;
					}
				}
				if (hasInvestor && !hasBroker) {
					roleIds = roleIdsNew;
				}
			}
			
			returnEnum = personalInfoManager.addPersonalInfo(personalInfo, userBaseInfo, parentId,
				roleIds);

		} catch (Exception e) {
			logger.error("查询用户失败", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建个人用户失败" + e.getMessage());
			return jsonobj;
		}
		if (returnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "创建个人用户成功");
			try {
				userBaseInfo = userBaseInfoManager.queryByUserName(userBaseInfo.getUserName(), 0);
			} catch (Exception e) {
				logger.error("查询用户失败", e);
			}
			this.sendPersonalInfo(personalInfo, userBaseInfo);
			YzzUserSpecialRegisterResponse registerResponse = null;
			try {
				registerResponse = userBaseInfoManager.doRegYJFAccount(personalInfo, userBaseInfo);
				if (!registerResponse.registSuccess() || StringUtil.isBlank(registerResponse.getUserId())) {
					throw new ResultException("注册失败", registerResponse.getResultMessage());
				}
				String accountId=registerResponse.getUserId();
				userBaseInfo.setState("normal");
				userBaseInfo.setAccountId(accountId);
				userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
				personalInfoDAO.update(personalInfo);
			} catch (Exception e) {
				returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
			}
		}

		if (returnEnum == PersonalReturnEnum.EXECUTE_FAILURE) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建个人用户失败");
		}
		return jsonobj;
	}


	@ResponseBody
	@RequestMapping("personalManage/updatePersonalUserSubmit")
	public Object updatePersonalUserSubmit(PersonalInfoDO personalInfo,
											UserBaseInfoDO userBaseInfo, int... roleIds)
																						throws Exception {
		JSONObject jsonobj = new JSONObject();
		if (userBaseInfo.getRealNameAuthentication() == null
			|| "".equals(userBaseInfo.getRealNameAuthentication())) {
			userBaseInfo.setRealNameAuthentication("NO");
		}
		if (userBaseInfo.getMobileBinding() == null || "".equals(userBaseInfo.getMobileBinding())) {
			userBaseInfo.setMobileBinding("NO");
		}
		if (userBaseInfo.getMailBinding() == null || "".equals(userBaseInfo.getMailBinding())) {
			userBaseInfo.setMailBinding("NO");
		}
		if (userBaseInfo.getPwdErrorCount() == null || "".equals(userBaseInfo.getPwdErrorCount())) {
			userBaseInfo.setPwdErrorCount(0);
		}
		if (userBaseInfo != null) {
			if (StringUtil.isEmpty(userBaseInfo.getMail())) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "请填写邮箱");
				return jsonobj;
			}
			
			if (StringUtil.isEmpty(userBaseInfo.getMobile())) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "请填写手机");
				return jsonobj;
			}
			
			UserBaseInfoDO userBase = userBaseInfoManager.queryByUserId(userBaseInfo.getUserId());
			if (userBase != null) {
				
				//不相同同步邮箱
				if (!StringUtil.equalsIgnoreCase(userBaseInfo.getMail(), userBase.getMail())) {
					if (StringUtil.isNotEmpty(userBaseInfo.getMail())
						&& StringUtil.isNotEmpty(userBase.getAccountId())) {
						RegisterOrder order = new RegisterOrder();
						order.setEmail(userBaseInfo.getMail());
						order.setUserId(userBase.getAccountId());
						//TODO
					}
					
				}
				//不相同同步手机
				if (!StringUtil.equalsIgnoreCase(userBaseInfo.getMobile(), userBase.getMobile())) {
					if (StringUtil.isNotEmpty(userBaseInfo.getMobile())
						&& StringUtil.isNotEmpty(userBase.getAccountId())) {
						MobileBindOrder mobileBindOrder = new MobileBindOrder();
						mobileBindOrder.setUserId(userBase.getAccountId());
						mobileBindOrder.setMobile(userBaseInfo.getMobile());
						mobileBindOrder.setIsNew(BooleanEnum.NO);
						P2PBaseResult baseResult = customerService.updateMobileBinding(
							mobileBindOrder, this.getOpenApiContext());
						if (!baseResult.isSuccess()) {
							logger.info("同步手机失败", baseResult.getMessage());
							jsonobj.put("code", 0);
							jsonobj.put("message", baseResult.getMessage());
							return jsonobj;
						}
					}
					
				}
			}
		}
		PersonalReturnEnum returnEnum = personalInfoManager.updatePersonalInfo(personalInfo,
			userBaseInfo, true, roleIds);
		if (returnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "修改个人用户信息成功");
		}
		if (returnEnum == PersonalReturnEnum.EXECUTE_FAILURE) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "修改个人用户信息失败");
		}
		return jsonobj;
	}
	
	@RequestMapping("userBaseInfoManage")
	public String userBaseInfoManage(QueryConditions queryConditions, PageParam pageParam,
										Model model) throws Exception {
		Page<UserBaseInfoDO> page = userBaseInfoManager.allUserInfo(queryConditions, pageParam);
		model.addAttribute("queryConditions", queryConditions);
		model.addAttribute("page", page);
		return USER_MANAGE_PATH + "userManage.vm";
	}
	
	@RequestMapping("userBaseInfoManage/updateUserRole")
	public String updateUserRole(String userBaseId, long userId, Model model) throws Exception {
		UserBaseInfoDO baseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		List<Role> roleList = authorityService.getRolesByUserId(userId, 1, 12).getResult();
		if ("GR".equals(baseInfo.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			model.addAttribute("referees", personalInfo.getReferees());
		}
		Pagination<Role> rolePagination = authorityService.getAllRoles(0, 999999);
		List<Role> roleAll = rolePagination.getResult();
		List<Role> roleSelects = new ArrayList<Role>();
		if (ListUtil.isNotEmpty(roleAll)) {
			for (Role role : roleAll) {
				if (role.getId() == 1 || (role.getId() >= 7 && role.getId() <= 14)) {
					continue;
				}
				roleSelects.add(role);
			}
		}
		model.addAttribute("roleSelects", roleSelects);
		model.addAttribute("info", baseInfo);
		model.addAttribute("roleList", roleList);
		return USER_MANAGE_PATH + "userBaseInfoUpdate.vm";
	}
	
	@ResponseBody
	@RequestMapping("userBaseInfoManage/updateUserRoleSubmit")
	public Object updateUserRoleSubmit(String userBaseId, String type, String state, int... roleIds)
																									throws Exception {
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		userBaseInfo.setState(state);
		if ("JG".equals(type)) {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			InstitutionsReturnEnum returnEnum = institutionsInfoManager.updateInstitutionsInfo(
				institutionsInfo, userBaseInfo, true, roleIds);
			if (returnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "修改机构信息成功");
			}
			if (returnEnum == InstitutionsReturnEnum.EXECUTE_FAILURE) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "修改机构信息失败");
			}
		} else {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			PersonalReturnEnum returnEnum = personalInfoManager.updatePersonalInfo(personalInfo,
				userBaseInfo, true, roleIds);
			if (returnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "修改个人用户信息成功");
			}
			if (returnEnum == PersonalReturnEnum.EXECUTE_FAILURE) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "修改个人用户信息失败");
			}
		}
		
		return jsonobj;
	}
	
	@RequestMapping("userBaseInfoManage/addAdmin")
	public String addAdmin(long parentId, Model model) throws Exception {
		model.addAttribute("parentId", parentId);
		model.addAttribute("uploadHost", "");
		Pagination<Role> rolePagination = authorityService.getAllRoles(0, 999999);
		List<Role> roleList = rolePagination.getResult();
		List<Role> roleSelects = new ArrayList<Role>();
		if (ListUtil.isNotEmpty(roleList)) {
			for (Role role : roleList) {
				if (role.getId() == 1 || (role.getId() >= 7 && role.getId() <= 14)) {
					continue;
				}
				roleSelects.add(role);
			}
		}
		model.addAttribute("roleSelects", roleSelects);
		return USER_MANAGE_PATH + "addAdmin.vm";
	}
	
	/**
	 * 添加后台管理员
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("userBaseInfoManage/addAdminSubmit")
	public Object addAdminSubmit(HttpSession session, HttpServletRequest request, int... roleIds) {
		UserBaseInfoDO userBaseInfo = new UserBaseInfoDO();
		WebUtil.setPoPropertyByRequest(userBaseInfo, request);
		JSONObject json = new JSONObject();
		try {
			PersonalReturnEnum returnEnum = personalInfoManager.addAdmin(userBaseInfo, roleIds);
			if (returnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
				json.put("code", 1);
				json.put("message", "新增管理员成功");
			} else if (returnEnum == PersonalReturnEnum.EXECUTE_FAILURE) {
				json.put("code", 0);
				json.put("message", "新增管理员失败");
			}
		} catch (Exception e) {
			logger.error("新增管理员异常", e);
			json.put("code", 0);
			json.put("message", "新增管理员异常");
		}
		return json;
	}
	
	/**
	 * 添加后台管理员
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("resetPayPassword")
	public Object resetPayPassword(String userBaseId, Model model) {
		JSONObject json = new JSONObject();
		try {
			UserBaseReturnEnum returnEnum = userBaseInfoManager.resetPayPassword(userBaseId);
			if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
				json.put("code", 1);
				json.put("message", "重置支付密码成功");
			} else if (returnEnum == UserBaseReturnEnum.EXECUTE_FAILURE) {
				json.put("code", 0);
				json.put("message", "重置支付密码失败");
			}
		} catch (Exception e) {
			logger.error("重置支付密码异常", e);
			json.put("code", 0);
			json.put("message", "重置支付密码异常");
		}
		return json;
	}
	
	@RequestMapping("changeBroker")
	public String changeBroker(HttpSession session) {
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return USER_MANAGE_PATH + "changeBroker.vm";
	}
	
	@ResponseBody
	@RequestMapping("changeBrokerSubmit")
	public Object changeBrokerSubmit(String userName, String borkerNo, String token,
										HttpSession session) {
		String getToken = (String) session.getAttribute("token");
		long userid = userBaseInfoManager.queryUserId(null, userName, null, "GR");
		if (userid <= 0) {
			userid = userBaseInfoManager.queryUserId(null, userName, null, "JG");
		}
		List<UserRelationDO> userRelation = userRelationManager.getRelationsByConditions(null,
			null, null, borkerNo).getResult();
		long parentId = 0;
		JSONObject json = new JSONObject();
		if (userRelation != null && userRelation.size() > 0) {
			parentId = userRelation.get(0).getChildId();
		}
		try {
			if (token != null && token.equals(getToken)) {
				session.removeAttribute("token");
				UserRelationReturnEnum result = userRelationManager.changeBroker(userid, parentId,
					borkerNo);
				if (result == UserRelationReturnEnum.EXECUTE_SUCCESS) {
					json.put("code", 1);
					json.put("message", "更改成功");
				} else {
					json.put("code", 0);
					json.put("message", "更改失败");
				}
			} else {
				json.put("code", 0);
				json.put("message", "重复提交");
			}
			
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "更改失败");
			logger.error("changeBrokerSubmit", e);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("changeMarkettingSubmit")
	public Object changeMarkettingSubmit(String brokerUserName, String markettingUserName,
											String token, HttpSession session) {
		String getToken = (String) session.getAttribute("token");
		long brokerUserId = userBaseInfoManager.queryUserId(null, brokerUserName, null, "GR");
		long parentId = userBaseInfoManager.queryUserId(null, markettingUserName, null, "JG");
		JSONObject json = new JSONObject();
		try {
			if (token != null && token.equals(getToken)) {
				List<UserBaseInfoDO> parentJGs = userBaseInfoManager.queryByType("JG",
					String.valueOf(parentId));
				UserBaseInfoDO parentJG = null;
				if (parentJGs != null && parentJGs.size() > 0) {
					parentJG = parentJGs.get(0);
				} else {
					json.put("code", 0);
					json.put("message", "机构未找到");
				}
				if (checkChildIdForAddMember(brokerUserId) && brokerUserId > 0 && parentId > 0) {
					session.removeAttribute("token");
					UserRelationReturnEnum result = userRelationManager.changeBrokerMarketting(
						brokerUserId, parentId);
					if (result == UserRelationReturnEnum.EXECUTE_SUCCESS) {
						json.put("code", 1);
						json.put("message", "更改成功");
					} else {
						json.put("code", 0);
						json.put("message", "更改失败");
					}
				} else {
					json.put("code", 0);
					json.put("message", "填写信息有误");
				}
			} else {
				json.put("code", 0);
				json.put("message", "重复提交");
			}
			
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "更改失败");
			logger.error("changeMarkettingSubmit", e);
		}
		return json;
	}
	
	//只有经纪人才能通过
	private boolean checkChildIdForAddMember(long userId) {
		boolean isJJR = false;
		Pagination<Role> rolesPage = authorityService.getRolesByUserId(userId, 0, 99);
		if (rolesPage.getResult() != null && rolesPage.getResult().size() > 0) {
			for (Role role : rolesPage.getResult()) {
				if (SysUserRoleEnum.BROKER.getRoleCode().equals(role.getCode())) {
					isJJR = true;
					return isJJR;
				}
			}
		}
		
		return isJJR;
	}
	
	/**
	 * 后台实名认证
	 * 
	 * @param userBaseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "backRealNameAut")
	public JSONObject backRealNameAut(String userBaseId) {
		JSONObject jsonobj = new JSONObject();
		try {
			UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			jsonobj = this.sendPersonalInfo(personalInfo, userBaseInfo);
		} catch (Exception e) {
			logger.error("后台个人实名发送异常", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "后台个人实名发送异常");
		}
		return jsonobj;
	}
	
	/**
	 * 后台发送实名认证
	 * 
	 * @param personalInfo
	 * @param userBaseInfo
	 * @return
	 */
	public JSONObject sendPersonalInfo(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo) {
		JSONObject jsonobj = new JSONObject();
		if (personalInfo.getCertFrontPath() == null || personalInfo.getCertBackPath() == null) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "未上传身份证照片,实名认证发送失败");
			return jsonobj;
		}
		if (personalInfo.getCertFrontPath().length() > 18
			&& personalInfo.getCertBackPath().length() > 18) {
			PersonalCertOrder person = new PersonalCertOrder();
			person.setNickname(personalInfo.getRealName());//真实姓名
			person.setCardpic(personalInfo.getCertFrontPath());//身份证正面照
			person.setCardpic1(personalInfo.getCertBackPath());//身份证背面照
			person.setCoreCustomerUserId(userBaseInfo.getAccountId());
			if ("longTime".equals(personalInfo.getBusinessPeriod())) {
				person.setCardoff(0);
			} else {
				person.setCardoff(NumberUtil.parseInt(personalInfo.getBusinessPeriod().replaceAll(
					"-", "")));
			}
			person.setCardtype("2");//二代身份证
			person.setCardid(personalInfo.getCertNo());
			
			//			P2PBaseResult result = this.customerService.realNameCertSave(person,
			//				this.getOpenApiContext());
			//TODO
			jsonobj.put("code", 1);
			jsonobj.put("message", "实名认证发送成功");
			userBaseInfo.setRealNameAuthentication("IN");
			try {
				userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
			} catch (Exception e) {
				logger.error("实名认证时,更新用户实名状态失败", e);
			}
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "未上传身份证照片,实名认证发送失败");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("userBaseInfoManage/queryBalance")
	public Object queryBalance(String userBaseId, Model model) throws Exception {
		JSONObject json = new JSONObject();
		//		UserBaseInfoDO user = userBaseInfoManager.queryByUserBaseId(userBaseId);
		
		YzzUserAccountQueryResponse account = apiTool.queryUserAccount(SessionLocalManager
			.getSessionLocal().getAccountId());
		
		if (account.success()) {
			//不可以余额
			json.put("code", "1");
			json.put("freezeAmount", MoneyUtil.toStandardString(account.getFreezeBalance()));
			json.put("availableBalance", MoneyUtil.toStandardString(account.getAvailableBalance()));
			json.put("balance", MoneyUtil.toStandardString(account.getBalance()));
			return json;
		}
		
		json.put("code", "0");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("updateState")
	public Object updateState(String userBaseId, String state) throws Exception {
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO userBaseInfo = null;
		try {
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		} catch (Exception e) {
			logger.error("未找到当前登录的用户", e);
		}
		if (userBaseInfo.getState().equals("locked")) {
			userBaseInfo.setPwdErrorCount(0);
			userBaseInfo.setChangeLockTime(new Date());
			userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		}
		UserBaseReturnEnum returnEnum = userBaseInfoManager.updateState(userBaseId, state);
		if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "修改用户状态成功");
		}
		if (returnEnum == UserBaseReturnEnum.EXECUTE_FAILURE) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "修改用户状态失败");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("common/validationAccountName")
	public Object validationAccountName(String accountName) throws Exception {
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("code", 1);
		jsonobj.put("message", "用户可以用");
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("common/validationUserName")
	public Object validationUserName(String userName) throws Exception {
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户不存在，入参：{}", userName);
		JSONObject jsonobj = new JSONObject();
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.USERNAME_ERROR;
		returnEnum = userBaseInfoManager.validationUserName(userName);
		//验证用户不存在
		if (returnEnum == UserBaseReturnEnum.USERNAME_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "用户可以用");
		}
		if (returnEnum == UserBaseReturnEnum.USERNAME_ERROR) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "用户已存在");
		}
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户不存在，入参：{}", userName);
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("common/validationReferees")
	public Object validationReferees(String referees) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		JSONObject jsonobj = new JSONObject();
		returnEnum = userBaseInfoManager.validationReferees(referees);
		if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "该推荐人可用");
		}
		if (returnEnum == UserBaseReturnEnum.EXECUTE_FAILURE) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "该推荐人不可用");
		}
		return jsonobj;
	}

	@ResponseBody
	@RequestMapping("adduserexp")
	public Map<String, Object> addUserGoldExp(HttpServletRequest request, UserGoldExperienceDO userGoldExp) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userGoldExperienceDao.insert(userGoldExp);
			GoldExperienceDO goldExperience = goldExperienceDao.queryById(userGoldExp.getGoldExpId());
			//对于发放的体验金 减少1
			if(null != goldExperience){
				goldExperience.setSurplusQuantity(goldExperience.getSurplusQuantity() - 1);
				goldExperienceDao.update(goldExperience);
			}
			map.put("msg", "添加成功");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "添加失败");
		}
		return map;
	}
}
