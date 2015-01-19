package com.icebreak.p2p.front.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.integration.openapi.enums.RealNameBusinessStatusEnum;
import com.icebreak.p2p.integration.openapi.info.ThirdPartAccountInfo;
import com.icebreak.p2p.integration.openapi.order.EBankDepositDeductOrder;
import com.icebreak.p2p.integration.openapi.result.CustomerResult;
import com.icebreak.p2p.integration.openapi.result.DeductDepositResult;
import com.icebreak.p2p.integration.openapi.result.RealNameStatusResult;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.Session;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.web.util.TradeStatisticsUtil;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.TradeDetailStatusEnum;
import com.icebreak.p2p.ws.enums.UserTypeEnum;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.lang.util.money.MoneyUtil;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.message.common.trade.QueryBindBankCardRequest;
import com.yiji.openapi.sdk.message.common.trade.QueryBindBankCardResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("usercenter")
public class UserBaseController extends UserAccountInfoBaseController {

	private static final String CLASS = "class=\"light\"";
	@Autowired
	protected IOperatorInfoService operatorInfoService;
	/** 返回页面路径 */
	String USER_MANAGE_PATH = "/front/user/activation/";
	String USER_MANAGE_FRIEND_PATH = "/front/user/friend/";
	
	@ResponseBody
	@RequestMapping("getBindEmail")
	public Object getBindEmail(String userName) throws Exception {
		logger.info("获取" + AppConstantsUtil.getProductName() + "金融用户邮箱，入参：{}", userName);
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO baseUser = userBaseInfoManager.queryByUserName(userName, 0);
		if (baseUser != null) {
			String mail = baseUser.getMail();
			String[] strMail = mail.split("@");
			jsonobj.put("code", 1);
			jsonobj.put("mail", mail);
			jsonobj.put("message", strMail[0].substring(0, 3) + "********@" + strMail[1]);
			jsonobj.put("mobile", baseUser.getMobile());
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "");
		}
		logger.info("获取" + AppConstantsUtil.getProductName() + "金融用户邮箱，出参：{}", userName);
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("validationIsUserName")
	public Object validationIsUserName(String userName) throws Exception {
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户存在，入参：{}", userName);
		JSONObject jsonobj = new JSONObject();
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.USERNAME_ERROR;
		returnEnum = userBaseInfoManager.validationUserName(userName);
		if (returnEnum == UserBaseReturnEnum.USERNAME_SUCCESS) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "用户不存在");
		} else if (returnEnum == UserBaseReturnEnum.USERNAME_ERROR) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "用户已存在");
		}
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户存在，出参：{}", jsonobj);
		return jsonobj;
	}
	
	// ------------------------------------------------------------账户首页----------------------------------------------------------------------------

	@RequestMapping("home")
	public String userHome(HttpSession session, Model model) {
		session.setAttribute("current", 1);
		Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null,
				SessionLocalManager.getSessionLocal().getUserId(), null);
		List<UserRelationDO> lst = page.getResult();
		if (lst.size() > 0) {
			model.addAttribute("userMemoNo", lst.get(0).getMemberNo());
		} else {
			model.addAttribute("userMemoNo", null);
		}
		//获取登录信息
		List<UserLoginLog> loginLog = userBaseInfoManager
				.queryUserLoginLogByUserId(SessionLocalManager.getSessionLocal().getUserId());
		if (loginLog != null) {
			if (loginLog.size() > 1) {
				model.addAttribute("loginAddress", loginLog.get(1).getLoginAddress());
				model.addAttribute("loginTime", loginLog.get(1).getLoginTime());
			} else if (loginLog.size() == 0) {
				model.addAttribute("noLoginLog", "true");
			} else {
				model.addAttribute("loginAddress", loginLog.get(0).getLoginAddress());
				model.addAttribute("loginTime", loginLog.get(0).getLoginTime());
				model.addAttribute("firstLogin", "true");
			}
		} else {
			model.addAttribute("noLoginLog", "true");
		}
		//用户信息
		UserBaseInfoDO userBaseInfo = getUserBaseInfo(session, model);
		model.addAttribute("accountId", userBaseInfo.getAccountId());
		String transferLimit = AppConstantsUtil.getYrdTransferLimit();
		boolean flag = false;
		if (StringUtil.isNotEmpty(transferLimit)) {
			String[] transfer = transferLimit.split(",");
			for (String str : transfer) {
				if (userBaseInfo.getUserName().equals(str)) {
					flag = true;
				}
			}
		}
		if (flag) {
			session.setAttribute("transfer", "yes");
		} else {
			session.setAttribute("transfer", "no");
		}

		//操作员查询
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userBaseId", userBaseInfo.getUserBaseId());
		conditions.put("limitStart", 0);
		conditions.put("pageSize", 9999);
		List<OperatorInfoDO> operatorInfos = operatorInfoService
				.queryOperatorsByProperties(conditions);
		if (operatorInfos != null && operatorInfos.size() > 0) {
			session.setAttribute("operator", "yes");
			if (2 == operatorInfos.get(0).getOperatorType()
					|| 3 == operatorInfos.get(0).getOperatorType()) {

			} else {
				session.setAttribute("tasks", "two");
			}
		} else {
			session.setAttribute("operator", "no");
		}
		initAccountInfo(model, false);

		//更新本地账户的实名认证状态
		ThirdPartAccountInfo accountInfo = getAccountInfo(model);
		updateLocalAccountByRemote(userBaseInfo, accountInfo);
		//获取当前用户体验金
		BigDecimal userGodExpSum = userGoldExperienceDao.countByUserId(userBaseInfo.getUserId());
		model.addAttribute("userGodExpSum", userGodExpSum);
		try {
			countInvestAndLoan(session, model);
		} catch (Exception e) {
			logger.error("countInvestAndLoan errror", e);
		}
		model.addAttribute("home", CLASS);
		return USER_MANAGE_PATH + "investIndex.vm";
	}
	
	@ResponseBody
	@RequestMapping("checkThirdpartAccountcomplete")
	public Object checkThirdpartAccountComplete(HttpSession session) throws Exception{
		//检查当前用户是否有三方支付账户的ID，由此来判断是否已经注册了三方账户
		JSONObject jsonObject  = new JSONObject();
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		if(null != userBaseInfo.getAccountId() && "" != userBaseInfo.getAccountId()){
			jsonObject.put("code", 1);
			jsonObject.put("message", "操作成功");
		}else{
			jsonObject.put("code", 0);
			jsonObject.put("message", "操作失败");
		}
		return jsonObject;
	}
	

	// ------------------------------------------------------------跳转收银台----------------------------------------------------------------------------
	@RequestMapping("rechargePage")
	public String rechargePage(HttpServletRequest request, HttpServletResponse response)
																					throws UnsupportedEncodingException {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		String userId = sessionLocal.getAccountId();
		String amount = request.getParameter("rechargeAmount");
		
		EBankDepositDeductOrder deductOrder = new EBankDepositDeductOrder();
		deductOrder.setUserId(userId);
		deductOrder.setDepositAmount(amount);
		DeductDepositResult depositResult = this.deductDepositService.applyEBankDeposit(deductOrder);
		
		RechargeFlow rechargeFlow = new RechargeFlow();
		rechargeFlow.setPayType(DivisionTypeEnum.DEPOSIT.getCode());
		rechargeFlow.setPayMemo("网银充值");
		rechargeFlow.setUserId(sessionLocal.getUserId());
		rechargeFlow.setYjfAccountId(sessionLocal.getAccountId());
		rechargeFlow.setPayTime(new Date());
		rechargeFlow.setStatus(-1);
		rechargeFlow.setLocalNo(depositResult.getDepositId());
		rechargeFlow.setAmount((new Money(amount)).getCent());
		try {
			rechargeFlowService.addRechargeFlow(rechargeFlow);
		} catch (Exception e) {
			logger.error("添加网银充值收支明细失败:", e);
			return null;
		}
		try {
			response.sendRedirect(depositResult.getUrl());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	
	// ------------------------------------------------------------账户基本资料查询----------------------------------------------------------------------------
	
	@RequestMapping("userBasicInfo")
	public String userBasicInfo(HttpSession session, Model model) {
		session.setAttribute("current", 1);
		try {
			this.queryUserInfo(model);
		} catch (Exception e) {
			logger.error("查询银行信息异常", e);
		}
		return USER_MANAGE_PATH + "userBasicInfo.vm";
	}
	
	@RequestMapping("userRealNameInfo")
	public String userRealNameInfo(Model model, HttpSession session) throws Exception {
		try {
			this.queryUserInfo(model);
		} catch (Exception e) {
			logger.error("查询银行信息异常", e);
		}
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfoDO = userBaseInfoManager.queryByUserBaseId(userBaseId);
		if (!"IS".equals(userBaseInfoDO.getRealNameAuthentication())) {
			RealNameStatusResult statusResult = this.customerService.queryRealNameCert(
				userBaseInfoDO.getAccountId(), this.getOpenApiContext());
			if (statusResult.getBusinessStatusEnum() == RealNameBusinessStatusEnum.CHECK_PASSED) {
				userBaseInfoDO.setRealNameAuthentication("IS");
				try {
					userBaseInfoManager.updateUserBaseInfo(userBaseInfoDO);
				} catch (Exception e) {
					logger.error("更新实名状态异常", e);
				}
				userBaseInfoDO = userBaseInfoManager.queryByUserBaseId(userBaseId);
			}
		}
		model.addAttribute("uploadHost", "");
		session.setAttribute("token", UUID.randomUUID().toString());
		this.initAccountInfo(model);
		if ("GR".equals(userBaseInfoDO.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			if (userBaseInfoDO.getRealNameAuthentication() != null
				&& !"NO".equals(userBaseInfoDO.getRealNameAuthentication())
				&& StringUtil.isNotBlank(personalInfo.getBankCardNo())) {
				if (!personalInfo.getRealName().equals(personalInfo.getBankOpenName())) {
					model.addAttribute("notMatch", "notMatch");
				}
			}
			return USER_MANAGE_PATH + "userRealNameInfo.vm";
		} else {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			if (userBaseInfoDO.getRealNameAuthentication() != null
				&& !"NO".equals(userBaseInfoDO.getRealNameAuthentication())
				&& StringUtil.isNotBlank(institutionsInfo.getBankCardNo())) {
				if (!institutionsInfo.getEnterpriseName()
					.equals(institutionsInfo.getBankOpenName())) {
					model.addAttribute("notMatch", "notMatch");
				}
			}
			return USER_MANAGE_PATH + "enterpriseInfo.vm";
		}
	}

	@RequestMapping("setting")
	public String setting(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			initAccountInfo(model);
			QueryBindBankCardRequest param = new QueryBindBankCardRequest();
			String userId = SessionLocalManager.getSessionLocal().getAccountId();
			if(null != userId) {
				param.setUserId(SessionLocalManager.getSessionLocal().getAccountId());
				QueryBindBankCardResponse cardResult = openApiGatewayService.queryBindBankCard(param);
				model.addAttribute("cardResult", cardResult);
			}
			setCss("setting","",model);
		} catch (Exception e) {
			logger.error("initAccountInfo error", e);
		}
		return USER_MANAGE_PATH + "newAcountSetting.vm";
	}


	@RequestMapping("userBankInfo")
	public String userBankInfo(Model model) {
		try {
			this.queryUserInfo(model);
		} catch (Exception e) {
			logger.error("查询银行信息异常", e);
		}
		return USER_MANAGE_PATH + "userBankInfo.vm";
	}
	
	// ------------------------------------------------------------账户基本资料修改----------------------------------------------------------------------------
	
	@ResponseBody
	@RequestMapping("updateUserBasicInfo")
	public Object updateUserBasicInfo(String mail, String mobile, String gender,
										String businessLicenseProvince, String businessLicenseCity)
																									throws Exception {
		JSONObject jsonobj = new JSONObject();
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		userBaseInfo.setMail(mail);
		userBaseInfo.setMobile(mobile);
		if ("GR".equals(userBaseInfo.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			personalInfo.setGender(Integer.parseInt(gender));
			jsonobj = this.updateUserInfo(personalInfo, null, userBaseInfo);
		}
		if ("JG".equals(userBaseInfo.getType())) {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			institutionsInfo.setBusinessLicenseProvince(businessLicenseProvince);
			institutionsInfo.setBusinessLicenseCity(businessLicenseCity);
			jsonobj = this.updateUserInfo(null, institutionsInfo, userBaseInfo);
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("updateUserRealNameInfo")
	public Object updateUserRealNameInfo(HttpSession session, String realName, String certNo,
											String businessPeriod, String conCertNo,
											String certFrontPath, String certBackPath, String token)
																									throws Exception {
		JSONObject jsonobj = new JSONObject();
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		//		Map<String,Object> resultMap = new HashMap<String, Object>();
		String getToken = (String) session.getAttribute("token");
		if ("GR".equals(userBaseInfo.getType()) && token.equals(getToken)) {
			//个人的时候需要同步更新user_base_info的真实名称
			userBaseInfo.setRealName(realName);
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			// 改变值
			personalInfo.setRealName(realName);
			personalInfo.setCertNo(certNo);
			int gender = CommonUtil.getGender(certNo);
			if (gender != 3) {
				personalInfo.setGender(gender);
			}
			personalInfo.setBusinessPeriod(businessPeriod);
			personalInfo.setCertFrontPath(certFrontPath);
			personalInfo.setCertBackPath(certBackPath);
			//调用修改方法
			jsonobj = this.updateUserInfo(personalInfo, null, userBaseInfo);
			session.removeAttribute("token");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("updateUserBankInfo")
	public Object updateUserBankInfo(String bankOpenName, String bankCardNo, String bankType,
										String bankKey, String bankProvince, String bankCity,
										String bankAddress) throws Exception {
		JSONObject jsonobj = new JSONObject();
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		if ("GR".equals(userBaseInfo.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			// 改变值
			personalInfo.setBankOpenName(bankOpenName);
			personalInfo.setBankCardNo(bankCardNo);
			personalInfo.setBankType(bankType);
			personalInfo.setBankKey(bankKey);
			personalInfo.setBankProvince(bankProvince);
			personalInfo.setBankCity(bankCity);
			personalInfo.setBankAddress(bankAddress);
			//调用修改方法
			jsonobj = this.updateUserInfo(personalInfo, null, userBaseInfo);
		}
		if ("JG".equals(userBaseInfo.getType())) {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			// 改变值
			institutionsInfo.setBankOpenName(bankOpenName);
			institutionsInfo.setBankCardNo(bankCardNo);
			institutionsInfo.setBankType(bankType);
			institutionsInfo.setBankKey(bankKey);
			institutionsInfo.setBankProvince(bankProvince);
			institutionsInfo.setBankCity(bankCity);
			institutionsInfo.setBankAddress(bankAddress);
			//调用修改方法
			jsonobj = this.updateUserInfo(null, institutionsInfo, userBaseInfo);
		}
		return jsonobj;
	}
	
	private JSONObject updateUserInfo(PersonalInfoDO personalInfo,
										InstitutionsInfoDO institutionsInfo,
										UserBaseInfoDO userBaseInfo) throws Exception {
		JSONObject jsonobj = new JSONObject();
		PersonalReturnEnum personalReturnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		InstitutionsReturnEnum institutionsReturnEnum = InstitutionsReturnEnum.EXECUTE_FAILURE;
		if (UserTypeEnum.GR.code().equals(userBaseInfo.getType())) {
			personalReturnEnum = personalInfoManager.updatePersonalInfo(personalInfo, userBaseInfo,
				false, new int[] {});
		}
		if (UserTypeEnum.JG.code().equals(userBaseInfo.getType())) {
			institutionsReturnEnum = institutionsInfoManager.updateInstitutionsInfo(
				institutionsInfo, userBaseInfo, false, new int[] {});
		}
		//更改userBaseInfo
		UserBaseReturnEnum userBaseReturnEnum = userBaseInfoManager
			.updateUserBaseInfo(userBaseInfo);
		if ((personalReturnEnum == PersonalReturnEnum.EXECUTE_SUCCESS && userBaseReturnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS)
			|| (institutionsReturnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS && userBaseReturnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS)) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "保存成功");
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "保存失败");
		}
		return jsonobj;
	}
	
	//----------------------------------------------------------------修改用户状态--------------------------------------------------------------------
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
	@RequestMapping("validationReferees")
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
	
	@RequestMapping("shareWithFriends")
	public String shareWithFriends(HttpSession session, Model model) throws Exception {
		String host = AppConstantsUtil.getHostHttpUrl();
		String recomandUrl = host;
		if (SessionLocalManager.getSessionLocal() != null) {
			boolean isJJR = false;
			boolean isTZR = false;
			String memNo = null;
			long userId = SessionLocalManager.getSessionLocal().getUserId();
			Pagination<Role> rolesPage = authorityService.getRolesByUserId(userId, 0, 99);
			if (rolesPage.getResult() != null && rolesPage.getResult().size() > 0) {
				for (Role role : rolesPage.getResult()) {
					Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
						null, SessionLocalManager.getSessionLocal().getUserId(), null);
					List<UserRelationDO> lst = page.getResult();
					if (lst.size() > 0) {
						memNo = lst.get(0).getMemberNo();
					}
					if (SysUserRoleEnum.BROKER.getRoleCode().equals(role.getCode())) {
						isJJR = true;
					} else if (SysUserRoleEnum.INVESTOR.getRoleCode().equals(role.getCode())) {
						isTZR = true;
					}
				}
				if (isJJR) {
					recomandUrl += "/anon/brokerOpenInvestor?NO=" + memNo;
				} else if (isTZR) {
					recomandUrl += "/anon/brokerOpenInvestor?NO=" + memNo;
				} else {
					recomandUrl += "/anon/register";
				}
			}
		} else {
			recomandUrl += "/anon/register";
		}
		model.addAttribute("recomandUrl", recomandUrl);
		return USER_MANAGE_FRIEND_PATH + "shareWithFriends.vm";
	}
	
	/**
	 * 获取资金信息
	 */
	@ResponseBody
	@RequestMapping("queryAccountInfo")
	public Object queryAccountInfo() {
		JSONObject jsonobj = new JSONObject();
		//用户信息
		UserBaseInfoDO userBaseInfo = null;
		try {
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
				.getSessionLocal().getUserBaseId());
		} catch (Exception e) {
			logger.error("未找到当前登录的用户", e);
		}
		if (userBaseInfo.getRealNameAuthentication() == ""
			|| userBaseInfo.getRealNameAuthentication() == null
			|| "IN".equals(userBaseInfo.getRealNameAuthentication())) {
			RealNameStatusResult statusResult = this.customerService.queryRealNameCert(
				userBaseInfo.getAccountId(), this.getOpenApiContext());
			if (statusResult.getBusinessStatusEnum() == RealNameBusinessStatusEnum.CHECK_PASSED) {
				userBaseInfo.setRealNameAuthentication("IS");
				try {
					userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
				} catch (Exception e) {
					logger.error("更新实名状态异常", e);
				}
			} else {
				
				RealNameBusinessStatusEnum status = statusResult.getBusinessStatusEnum();
				if (status.isChecking()) {
					userBaseInfo.setRealNameAuthentication("IN");
					try {
						userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
					} catch (Exception e) {
						logger.error("更新实名状态异常", e);
					}
				} else if (status.isDeny()) {
					userBaseInfo.setRealNameAuthentication("NO");
					try {
						userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
					} catch (Exception e) {
						logger.error("更新实名状态异常", e);
					}
				}
			}
		}
		jsonobj.put("realNameAuth", userBaseInfo.getRealNameAuthentication());
		
		YzzUserAccountQueryResponse account = apiTool.queryUserAccount(SessionLocalManager
			.getSessionLocal().getAccountId());
		if (account.success()) {
			jsonobj.put("code", 1);
			jsonobj.put("balance", MoneyUtil.toStandardString(account.getBalance()));
			jsonobj.put("freezeAmount", MoneyUtil.toStandardString(account.getFreezeBalance()));
			jsonobj.put("availableBalance",
				MoneyUtil.toStandardString(account.getAvailableBalance()));
			jsonobj.put("userStatus", account.getUserStatus());
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "查询资金信息失败");
		}
		
		return jsonobj;
	}
	
	@RequestMapping("countInvestAndLoan")
	public String countInvestAndLoan(HttpSession session, Model model) throws Exception {
		//投资统计//借款统计
		Map<String, Object> investCountMap = new HashMap<String, Object>();
		Map<String, Object> loanedCountMap = new HashMap<String, Object>();
		Map<String, Object> conditions = new HashMap<String, Object>();
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		conditions.put("userId", sessionLocal.getUserId());
		if(AppConstantsUtil.getProfitSharingAccount().equals(sessionLocal.getAccountId())) {
			conditions.put("roleId", SysUserRoleEnum.PLATFORM.getValue());
		} else {
			conditions.put("roleId", SysUserRoleEnum.INVESTOR.getValue());
		}
		//conditions.put("transferPhase", DivisionPhaseEnum.ORIGINAL_PHASE.code());
		//以投资人角色
		List<TradeQueryDetail> investTradeDetails = userBaseInfoManager
			.getTradeDetailByConditions(conditions);
		if (investTradeDetails != null && investTradeDetails.size() > 0) {
			TradeStatisticsUtil.countInvestedMoney(investTradeDetails, investCountMap);
		} else {
			//没有数据设置默认值
			TradeStatisticsUtil.setMoney(investCountMap);
		}
		conditions.put("roleId", SysUserRoleEnum.LOANER.getValue());
		//以借款人角色
		List<TradeQueryDetail> loanTradeDetails = userBaseInfoManager
			.getTradeDetailByConditions(conditions);
		if (loanTradeDetails != null && loanTradeDetails.size() > 0) {
			TradeStatisticsUtil.countLoanedMoney(loanTradeDetails, loanedCountMap);
		} else {
			//没有数据设置默认值
			TradeStatisticsUtil.setLoanCountMoney(loanedCountMap);
		}
		model.addAttribute("investCount", investCountMap);
		model.addAttribute("loanedCount", loanedCountMap);
		return USER_MANAGE_PATH + "countInvestAndLoan.vm";
	}
	

	@RequestMapping("forwardYJFRealNameCert")
	public void forwardYJFRealNameCert(HttpServletRequest request, HttpServletResponse response)
																								throws Exception {
		String userId = SessionLocalManager.getSessionLocal().getAccountId();
		CustomerResult result = customerService.gotoYjfRealNameCert(userId);
		response.sendRedirect(result.getUrl());
	}

	/**
	 * 前台企业用户更新实名资料
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateEnterpriseInfo")
	public Object updateEnterpriseInfo(InstitutionsInfoDO institutions, UserBaseInfoDO userBase,
										String activaStep) throws Exception {
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		
		institutionsInfo.setEnterpriseName(institutions.getEnterpriseName());//企业名称
		institutionsInfo.setOrganizationCode(institutions.getOrganizationCode());//组织机构代码
		institutionsInfo.setTaxRegistrationNo(institutions.getTaxRegistrationNo());//税务登记号
		institutionsInfo.setBusinessLicenseNo(institutions.getBusinessLicenseNo());//营业执照注册号
		institutionsInfo.setBusinessLicenseProvince(institutions.getBusinessLicenseProvince());//开户所在地-省份
		institutionsInfo.setBusinessLicenseCity(institutions.getBusinessLicenseCity());//开户所在地-城市
		institutionsInfo.setCommonlyUsedAddress(institutions.getCommonlyUsedAddress());//常用地址
		institutionsInfo.setZipCode(institutions.getZipCode());//邮政编码
		institutionsInfo.setLegalRepresentativeName(institutions.getLegalRepresentativeName());//法人代表姓名
		institutionsInfo.setLegalRepresentativeCardNo(institutions.getLegalRepresentativeCardNo());//法人身份证号码
		institutionsInfo.setCardPeriod(institutions.getCardPeriod());//法人身份证到期时间
		institutionsInfo.setContactName(institutions.getContactName());//常用联系人
		institutionsInfo.setContactCertNo(institutions.getContactCertNo());//联系人身份证号码
		institutionsInfo.setComPhone(institutions.getComPhone());//公司联系电话(座机)
		institutionsInfo.setBusinessPeriod(institutions.getBusinessPeriod());//经营期限
		institutionsInfo.setBusinessLicensePath(institutions.getBusinessLicensePath());//企业营业执照副本扫描件
		institutionsInfo.setCertFrontPath(institutions.getCertFrontPath());//法人身份证正面照
		institutionsInfo.setCertBackPath(institutions.getCertBackPath());//法人身份证背面照
		institutionsInfo.setOpeningLicensePath(institutions.getOpeningLicensePath());//开户许可证
		
		userBaseInfo.setMobile(userBase.getMobile());
		JSONObject json = new JSONObject();
		json = this.updateUserInfo(null, institutionsInfo, userBaseInfo);//更新
		return json;
	}
	
	/**
	 * 验证用户扩展类的密码
	 * @param password
	 * @param userBaseId
	 * @param passwordType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("validationExtendPassword")
	public Object validationExtendPassword(String password, String userBaseId, String passwordType) {
		JSONObject json = new JSONObject();
		if (userBaseId != null && !"".equals(userBaseId)) {
			long count = userService.validationUserPassword(password, userBaseId, passwordType);
			if (count > 0) {
				json.put("code", 1);
				json.put("message", "密码验证成功");
			} else {
				json.put("code", 0);
				json.put("message", "密码验证失败");
			}
		} else {
			json.put("code", 0);
			json.put("message", "参数不全，不能进行密码验证");
		}
		return json;
	}
	
	@RequestMapping("refreeCenter")
	public String refreeCenter(HttpSession session, PageParam pageParam, Model model)
																						throws Exception {
		Map<String, Object> giftNewConditions = new HashMap<String, Object>();
		List<Integer> status = new ArrayList<Integer>();
		status.add(0);
		status.add(1);
		status.add(2);
		giftNewConditions.put("status", status);
		giftNewConditions.put("type", 1);
		giftNewConditions.put("relationId", SessionLocalManager.getSessionLocal().getUserId());
		Page<ActivityDetail> page = iActivityService.getActivityDetailPage(giftNewConditions,
			pageParam);
		model.addAttribute("page", page);
		return USER_MANAGE_FRIEND_PATH + "refreeCenter.vm";
	}
	
	@Override
	protected ThirdPartAccountInfo getAccountInfo(Model model) {
		ThirdPartAccountInfo accountInfo = (ThirdPartAccountInfo) model.asMap().get(
			"thirdPartAccountInfo");
		if (accountInfo == null) {
			initAccountInfo(model);
			accountInfo = (ThirdPartAccountInfo) model.asMap().get("thirdPartAccountInfo");
		}
		return accountInfo;
	}
	
	/**
	 * 更新本地账户的实名认证状态
	 * @param userBaseInfo
	 * @param accountInfo
	 */
	private void updateLocalAccountByRemote(UserBaseInfoDO userBaseInfo,
											ThirdPartAccountInfo accountInfo) {
		
		if (accountInfo == null || userBaseInfo == null) {
			return;
		}
		
		//特殊情况 实名认证不一致  “认证成功”
		if ("已认证".equals(accountInfo.getCertifyStatus())) {
			if (!"IS".equals(userBaseInfo.getRealNameAuthentication())) {
				userBaseInfo.setRealNameAuthentication("IS");
				userBaseInfo.setRealName(accountInfo.getRealName());
				
				if (UserTypeEnum.GR.code().equals(userBaseInfo.getType())) {
					try {
						PersonalInfoDO personalInfoDO = null;
						personalInfoDO = personalInfoManager.query(userBaseInfo.getUserBaseId());
						if(accountInfo.getCertNo() != null && !"".equals(accountInfo.getCertNo().trim())){
							personalInfoDO.setCertNo(accountInfo.getCertNo());
						}
						personalInfoDO.setRealNameAuthentication("IS");
						personalInfoDO.setRealName(accountInfo.getRealName());
						personalInfoManager.updatePersonalInfo(personalInfoDO, userBaseInfo, false,
							null);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				} else {
					try {
						InstitutionsInfoDO institutionsInfo = null;
						institutionsInfo = institutionsInfoManager.query(userBaseInfo
							.getUserBaseId());
						institutionsInfo.setRealNameAuthentication("IS");
						institutionsInfoManager.updateInstitutionsInfo(institutionsInfo,
							userBaseInfo, false, null);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				
			}
		}
		
		//实名“认证被驳回”，同步本地数据库
		if ("被驳回".equals(accountInfo.getCertifyStatus())) {
			if (!"NO".equals(userBaseInfo.getRealNameAuthentication())) {
				userBaseInfo.setRealNameAuthentication("NO");
				userBaseInfo.setRealName(accountInfo.getRealName());
				
				if (UserTypeEnum.GR.code().equals(userBaseInfo.getType())) {
					try {
						PersonalInfoDO personalInfoDO = null;
						personalInfoDO = personalInfoManager.query(userBaseInfo.getUserBaseId());
						personalInfoDO.setCertNo(accountInfo.getCertNo());
						personalInfoDO.setRealNameAuthentication("NO");
						personalInfoDO.setRealName(accountInfo.getRealName());
						personalInfoManager.updatePersonalInfo(personalInfoDO, userBaseInfo, false,
							null);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				} else {
					try {
						InstitutionsInfoDO institutionsInfo = null;
						institutionsInfo = institutionsInfoManager.query(userBaseInfo
							.getUserBaseId());
						institutionsInfo.setRealNameAuthentication("NO");
						institutionsInfoManager.updateInstitutionsInfo(institutionsInfo,
							userBaseInfo, false, null);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}
	
}
