package com.icebreak.p2p.front.controller.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.AgentInvestorTrade;
import com.icebreak.p2p.dataobject.DivisionTemplateLoanDO;
import com.icebreak.p2p.dataobject.DivsionRuleRole;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.LoanAuthRecord;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.OperatorInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.ProfitAsignInfo;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserInvestEntry;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.dataobject.viewObject.InvestorInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.upload.UploadPictures;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.web.util.TradeUtil;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.TradeFullConditionEnum;
import com.icebreak.util.lang.util.StringUtil;

@Controller
@RequestMapping("investorManager")
public class BrokerOpenInvestorController extends UserAccountInfoBaseController {
	/**
	 * 返回页面路径
	 */
	String			USER_MANAGE_PATH	= "/front/user/activation/";
	String			BROCKER_MANAGE_PATH	= "/front/user/broker/";
	private String	jjrRoleId			= "11";
	
	protected IOperatorInfoService	operatorInfoService;
	//-----------------------------------------------------投资者开户----------------------------------------------------------------------
	
	UploadPictures	uploadPictures		= new UploadPictures();
	
	public String getJjrRoleId() {
		return jjrRoleId;
	}
	
	public void setJjrRoleId(String jjrRoleId) {
		this.jjrRoleId = jjrRoleId;
	}
	
	@RequestMapping("uploadPictures")
	public String uploadPictures(HttpServletRequest request) throws Exception {
		return null;
	}
	
	@RequestMapping("investorOpenAccount")
	public String investorOpenAccount(HttpSession session, Model model) throws Exception {
		this.initAccountInfo(model);
		session.setAttribute("current", 3);
		model.addAttribute("uploadHost", "");
		long brokerId = SessionLocalManager.getSessionLocal().getUserId();
		Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null,
			brokerId, null);
		List<UserRelationDO> lst = page.getResult();
		if (lst.size() > 0) {
			logger.info("该经纪人可以给投资人开户");
			model.addAttribute("availabelBroker", true);
			
		} else {
			model.addAttribute("availabelBroker", false);
			logger.info("该经纪人不可以给投资人开户");
		}
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		//      return USER_MANAGE_PATH + "investorOpenAccount.vm";
		
		return USER_MANAGE_PATH + "investorOpenAccountYrd.vm";
	}
	
	@RequestMapping("realNameAuthentication")
	public String realNameAuthentication(HttpSession session, PersonalInfoDO personalInfo,
											UserBaseInfoDO userBaseInfo, Model model)
																						throws Exception {
		session.setAttribute("personalInfo", personalInfo);
		session.setAttribute("userBaseInfo", userBaseInfo);
		return USER_MANAGE_PATH + "realNameAuthentication.vm";
	}
	
	@RequestMapping("realNameAuthenticationSubmit")
	public String realNameAuthenticationSubmit(HttpSession session, PersonalInfoDO personalInfo,
												UserBaseInfoDO userBaseInfo, Model model)
																							throws Exception {
		model.addAttribute("newDate", new Date());
		return USER_MANAGE_PATH + "realNameAuthenticationOk.vm";
	}
	
	@RequestMapping("bindingBankCard")
	public String bindingBankCard(HttpSession session, PersonalInfoDO personalInfo,
									UserBaseInfoDO userBaseInfo, Model model) throws Exception {
		List<BankBasicInfo> bankBasicInfos = this.bankDataService.getBankBasicInfos();
		model.addAttribute("bankBasicInfos", bankBasicInfos);
		return USER_MANAGE_PATH + "bindingBankCard.vm";
	}
	
	@RequestMapping("addlogPasswordAndPayPassword")
	public String addlogPasswordAndPayPassword(HttpSession session, String bankOpenName,
												String bankCardNo, String bankKey, String bankType,
												String bankProvince, String bankCity,
												String bankAddress) throws Exception {
		PersonalInfoDO personalInfo = (PersonalInfoDO) session.getAttribute("personalInfo");
		if (personalInfo == null) {
			return USER_MANAGE_PATH + "investorOpenAccountNO.vm";
		}
		
		personalInfo.setBankOpenName(bankOpenName);
		personalInfo.setBankCardNo(bankCardNo);
		personalInfo.setBankKey(bankKey);
		personalInfo.setBankType(bankType);
		personalInfo.setBankProvince(bankProvince);
		personalInfo.setBankCity(bankCity);
		personalInfo.setBankAddress(bankAddress);
		session.setAttribute("personalInfo", personalInfo);
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return USER_MANAGE_PATH + "addlogPasswordAndPayPassword.vm";
	}
	
	@RequestMapping("investorOpenAccountSubmit")
	public String investorOpenAccountSubmit(HttpSession session, String token,
											InstitutionsInfoDO institutionInfo,
											PersonalInfoDO personalInfo,
											UserBaseInfoDO userBaseInfo, Model model) {
		this.initAccountInfo(model);
		String return_vm = USER_MANAGE_PATH + "investorOpenAccountNO.vm";
		if ("GR".equals(userBaseInfo.getType())) {
			logger.info("经纪人开投资人户，入参1：{}，入参2：{}", personalInfo, userBaseInfo);
			return investorOpenGRAccountSubmit(session, token, personalInfo, userBaseInfo, model);
		} else if ("JG".equals(userBaseInfo.getType())) {
			logger.info("经纪人开投资人户，入参1：{}，入参2：{}", institutionInfo, userBaseInfo);
			return investorOpenJGAccountSubmit(session, token, institutionInfo, userBaseInfo, model);
		} else {
			model.addAttribute("reason", "不支持的账户类型！");
			return return_vm;
		}
	}
	
	public String investorOpenGRAccountSubmit(HttpSession session, String token,
												PersonalInfoDO personalInfo,
												UserBaseInfoDO userBaseInfo, Model model) {
		String return_vm = USER_MANAGE_PATH + "investorOpenAccountNO.vm";
		String getToken = (String) session.getAttribute("token");
		if (getToken != null) {
			if (getToken.equals(token)) {
				session.removeAttribute("token");
				//生成平台关联的托管机构帐户
				userBaseInfo.setAccountName(AppConstantsUtil.getYrdPrefixion()
											+ userBaseInfo.getUserName());
				userBaseInfo.setLogPassword("888888");
				userBaseInfo.setPayPassword("888888");
				userBaseInfo.setState("inactive");
				Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
					null, SessionLocalManager.getSessionLocal().getUserId(), null);
				List<UserRelationDO> lst = page.getResult();
				if (lst.size() > 0) {
					personalInfo.setReferees(lst.get(0).getMemberNo());
				}
				PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
				try {
					int[] roles = new int[] { SysUserRoleEnum.PUBLIC.getValue(),
							SysUserRoleEnum.INVESTOR.getValue() };
					if (sysFunctionConfigService.isAllEconomicMan()) {
						roles = new int[] { SysUserRoleEnum.PUBLIC.getValue(),
								SysUserRoleEnum.INVESTOR.getValue(),
								SysUserRoleEnum.BROKER.getValue() };
					}
					
					returnEnum = personalInfoManager.addPersonalInfo(personalInfo, userBaseInfo,
						SessionLocalManager.getSessionLocal().getUserId(), roles);
				} catch (Exception e) {
					model.addAttribute("reason", "处理新增账户信息失败！");
					return return_vm;
				}
				if (returnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
					String tokenOk = UUID.randomUUID().toString();
					session.setAttribute("tokenOk", tokenOk);
				}
			}
		}
		String getTokenOk = (String) session.getAttribute("tokenOk");
		if (getTokenOk != null) {
			return_vm = USER_MANAGE_PATH + "investorOpenAccountOK.vm";
		}
		model.addAttribute("reason", "处理新增账户信息失败！");
		return return_vm;
	}
	
	public String investorOpenJGAccountSubmit(HttpSession session, String token,
												InstitutionsInfoDO institutionInfo,
												UserBaseInfoDO userBaseInfo, Model model) {
		String return_vm = USER_MANAGE_PATH + "investorOpenAccountNO.vm";
		String getToken = (String) session.getAttribute("token");
		if (getToken != null) {
			if (getToken.equals(token)) {
				session.removeAttribute("token");
				//生成平台关联的托管机构帐户
				userBaseInfo.setAccountName(AppConstantsUtil.getYrdPrefixion()
											+ userBaseInfo.getUserName());
				userBaseInfo.setLogPassword("888888");
				userBaseInfo.setPayPassword("888888");
				userBaseInfo.setState("inactive");
				Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
					null, SessionLocalManager.getSessionLocal().getUserId(), null);
				List<UserRelationDO> lst = page.getResult();
				if (lst.size() > 0) {
					institutionInfo.setReferees(lst.get(0).getMemberNo());
				}
				InstitutionsReturnEnum returnEnum = InstitutionsReturnEnum.EXECUTE_FAILURE;
				try {
					returnEnum = institutionsInfoManager.addInstitutionsInfo(institutionInfo,
						userBaseInfo, SessionLocalManager.getSessionLocal().getUserId(), new int[] {
								1, 12 });
				} catch (Exception e) {
					model.addAttribute("reason", "处理新增账户信息失败！");
					return return_vm;
				}
				if (returnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS) {
					String tokenOk = UUID.randomUUID().toString();
					session.setAttribute("tokenOk", tokenOk);
				}
			}
		}
		String getTokenOk = (String) session.getAttribute("tokenOk");
		if (getTokenOk != null) {
			return_vm = USER_MANAGE_PATH + "investorOpenAccountOK.vm";
		}
		model.addAttribute("reason", "处理新增账户信息失败！");
		return return_vm;
	}
	
	//-----------------------------------------------------投资者管理----------------------------------------------------------------------
	
	@RequestMapping("investorManage")
	public String investorManage(HttpSession session, QueryConditions queryConditions,
									PageParam pageParam, Model model) throws Exception {
		this.initAccountInfo(model);
		
		queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		//Page<PersonalInfoVO>  page=personalInfoManager.pageChildrenVO(queryConditions, pageParam);
		Page<InvestorInfoVO> page = personalInfoManager.pageChildrenInvestorVO(queryConditions,
			pageParam);
		model.addAttribute("page", page);
		model.addAttribute("queryConditions", queryConditions);
		return USER_MANAGE_PATH + "investorManage.vm";
	}
	
	//-----------------------------------------------------投资者详情----------------------------------------------------------------------
	
	@RequestMapping("investorInfo")
	public String investorInfo(String userBaseId, Model model) throws Exception {
		UserBaseInfoDO userBase = userBaseInfoManager.queryByUserBaseId(userBaseId);
		this.initAccountInfo(model);
		if ("JG".equals(userBase.getType())) {
			InstitutionsInfoDO institutionInfo = institutionsInfoManager.query(userBaseId);
			Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null,
				institutionInfo.getUserId(), null);
			List<UserRelationDO> lst = page.getResult();
			if (lst.size() > 0) {
				model.addAttribute("userMemoNo", lst.get(0).getMemberNo());
			} else {
				model.addAttribute("userMemoNo", "");
			}
			List<BankBasicInfo> bankBasicInfos = this.bankDataService.getBankBasicInfos();
			String bankType = institutionInfo.getBankType();
			for (BankBasicInfo info : bankBasicInfos) {
				if (info.getBankCode().equals(bankType)) {
					bankType = info.getLogoUrl();
				}
			}
			institutionInfo.setBankType(bankType);
			String bankCardNo = institutionInfo.getBankCardNo();
			if (bankCardNo != null) {
				if (bankCardNo.length() > 4 && StringUtil.isNotBlank(bankCardNo)) {
					bankCardNo = bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length());
				}
			}
			model.addAttribute("userType", "JG");
			institutionInfo.setBankCardNo(bankCardNo);
			model.addAttribute("info", institutionInfo);
		} else {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null,
				personalInfo.getUserId(), null);
			List<UserRelationDO> lst = page.getResult();
			if (lst.size() > 0) {
				model.addAttribute("userMemoNo", lst.get(0).getMemberNo());
			} else {
				model.addAttribute("userMemoNo", "");
			}
			List<BankBasicInfo> bankBasicInfos = this.bankDataService.getBankBasicInfos();
			String bankType = personalInfo.getBankType();
			for (BankBasicInfo info : bankBasicInfos) {
				if (info.getBankCode().equals(bankType)) {
					bankType = info.getLogoUrl();
				}
			}
			personalInfo.setBankType(bankType);
			String bankCardNo = personalInfo.getBankCardNo();
			if (bankCardNo != null) {
				if (bankCardNo.length() > 4 && StringUtil.isNotBlank(bankCardNo)) {
					bankCardNo = bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length());
				}
			}
			model.addAttribute("userType", "GR");
			personalInfo.setBankCardNo(bankCardNo);
			model.addAttribute("info", personalInfo);
		}
		return USER_MANAGE_PATH + "investorInfo.vm";
	}
	
	//-----------------------------------------------------营销统计----------------------------------------------------------------------
	
	@RequestMapping("salesCount")
	public String salesCount(HttpSession session, PageParam pageParam, Model model)
																					throws Exception {
		session.setAttribute("current", 6);
		QueryTradeOrder queryConditions = new QueryTradeOrder();
		queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		queryConditions.setRoleId(11L);
		pageParam.setPageSize(999999999);
		Page<AgentInvestorTrade> page = tradeService.pageQueryBrokerInvestorTrade(queryConditions,
			pageParam);
		long collectingInvestCount = 0;
		long repaingInvestCount = 0;
		long faildInvestCount = 0;
		long repayFaildInvestCount = 0;
		long repayFinishInvestCount = 0;
		long collectingInvestAmount = 0;
		long paidInvestAmount = 0;
		long waitToPayInvestAmount = 0;
		long faildInvestAmount = 0;
		long totalAmount = 0;
		if (page.getResult() != null && page.getResult().size() > 0) {
			for (AgentInvestorTrade trade : page.getResult()) {
				if (YrdConstants.TradeStatus.TRADING == trade.getStatus()) {
					collectingInvestCount++;
					collectingInvestAmount += trade.getBrokerBenifitAmount();
				} else if (YrdConstants.TradeStatus.REPAYING == trade.getStatus()) {
					repaingInvestCount++;
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(trade.getTransferPhase())) {
						paidInvestAmount += trade.getBrokerBenifitAmount();
					} else if (DivisionPhaseEnum.REPAY_PHASE.code()
						.equals(trade.getTransferPhase())) {
						waitToPayInvestAmount += trade.getBrokerBenifitAmount();
					}
					
				} else if (YrdConstants.TradeStatus.FAILED == trade.getStatus()) {
					faildInvestCount++;
					faildInvestAmount += trade.getBrokerBenifitAmount();
				} else if (YrdConstants.TradeStatus.REPAYING_FAILD == trade.getStatus()) {
					repayFaildInvestCount++;
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(trade.getTransferPhase())) {
						paidInvestAmount += trade.getBrokerBenifitAmount();
					} else if (DivisionPhaseEnum.REPAY_PHASE.code()
						.equals(trade.getTransferPhase())) {
						waitToPayInvestAmount += trade.getBrokerBenifitAmount();
					}
				} else if (YrdConstants.TradeStatus.REPAY_FINISH == trade.getStatus()) {
					repayFinishInvestCount++;
					paidInvestAmount += trade.getBrokerBenifitAmount();
				} else if (YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH == trade.getStatus()) {
					repayFinishInvestCount++;
					paidInvestAmount += trade.getBrokerBenifitAmount();
				} else if (YrdConstants.TradeStatus.GUARANTEE_AUDITING == trade.getStatus()) {
					//collectingInvestCount++;
					collectingInvestAmount += trade.getBrokerBenifitAmount();
				} else if (YrdConstants.TradeStatus.DOREPAY == trade.getStatus()) {
					repaingInvestCount++;
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(trade.getTransferPhase())) {
						paidInvestAmount += trade.getBrokerBenifitAmount();
					} else if (DivisionPhaseEnum.REPAY_PHASE.code()
						.equals(trade.getTransferPhase())) {
						waitToPayInvestAmount += trade.getBrokerBenifitAmount();
					}
				}
			}
		}
		totalAmount = paidInvestAmount + waitToPayInvestAmount;
		model.addAttribute("page", page);
		model.addAttribute("collectingInvestCount", collectingInvestCount);
		model.addAttribute("repaingInvestCount", repaingInvestCount);
		model.addAttribute("faildInvestCount", faildInvestCount);
		model.addAttribute("repayFaildInvestCount", repayFaildInvestCount);
		model.addAttribute("repayFinishInvestCount", repayFinishInvestCount);
		model.addAttribute("collectingInvestAmount", collectingInvestAmount);
		model.addAttribute("paidInvestAmount", paidInvestAmount);
		model.addAttribute("faildInvestAmount", faildInvestAmount);
		model.addAttribute("waitToPayInvestAmount", waitToPayInvestAmount);
		model.addAttribute("totalAmount", totalAmount);
		return BROCKER_MANAGE_PATH + "broker-salesCount.vm";
	}
	
	/**
	 * 营销详情
	 * 
	 * @param tradeId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("salesDetails/{tradeId}/{detailId}")
	public String salesDetails(@PathVariable long tradeId, @PathVariable long detailId,
								long investDetailId, Model model) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("loanDemandId", trade.getDemandId());
		conditions.put("authType", "MAKELOANLVONE");
		long count1 = loanDemandManager.countLoanAuthRecordByCondition(conditions);
		String auditUserBaseId = "";
		if (count1 > 0) {
			List<LoanAuthRecord> reccord1 = loanDemandManager
				.getLoanAuthRecordByCondition(conditions);
			List<UserBaseInfoDO> users = userBaseInfoManager.queryByType("JG",
				String.valueOf(reccord1.get(0).getAuthUserId()));
			auditUserBaseId = users.get(0).getUserBaseId();
			model.addAttribute("auditUser1", users.get(0).getUserName());
			
			//备注
			Map<String, Object> opConditions = new HashMap<String, Object>();
			opConditions.put("userBaseId", users.get(0).getUserBaseId());
			opConditions.put("limitStart", 0);
			opConditions.put("pageSize", 100);
			List<OperatorInfoDO> operatorInfos = operatorInfoService
				.queryOperatorsByProperties(opConditions);
			model.addAttribute("remark1", operatorInfos.get(0).getRemark());
			//END
			
			
			model.addAttribute("auditrecord1", reccord1.get(0));
			model.addAttribute("audit", "yes");
		}
		
		conditions = new HashMap<String, Object>();
		conditions.put("loanDemandId", trade.getDemandId());
		conditions.put("authType", "MAKELOANLVTWO");
		count1 = loanDemandManager.countLoanAuthRecordByCondition(conditions);
		
		if (count1 > 0) {
			List<LoanAuthRecord> reccord1 = loanDemandManager
				.getLoanAuthRecordByCondition(conditions);
			List<UserBaseInfoDO> users = userBaseInfoManager.queryByType("JG",
				String.valueOf(reccord1.get(0).getAuthUserId()));
			model.addAttribute("auditUser2", users.get(0).getUserName());
			//备注
			Map<String, Object> opConditions = new HashMap<String, Object>();
			opConditions.put("userBaseId", users.get(0).getUserBaseId());
			opConditions.put("limitStart", 0);
			opConditions.put("pageSize", 100);
			List<OperatorInfoDO> operatorInfos = operatorInfoService
				.queryOperatorsByProperties(opConditions);
			model.addAttribute("remark2", operatorInfos.get(0).getRemark());
			//END
			
			
			model.addAttribute("auditrecord2", reccord1.get(0));
			
		}
		List<TradeDetail> investDetails = tradeService.getInvestProfitTrade(investDetailId);
		model.addAttribute("investableAmount", trade.getAmount() - trade.getLoanedAmount());
		if (investDetails != null && investDetails.size() > 0) {
			for (TradeDetail detail : investDetails) {
				if (detail.getProfitType() > 0) {
					model.addAttribute("showExtraProfit", "yes");
					model.addAttribute("extInterest", detail.getProfitRate());
				}
			}
		}
		List<UserInvestEntry> userInvests = tradeService.getEntriesByTradeIdAndDetailId(tradeId,
			investDetailId);
		LoanDemandDO demand = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
		model.addAttribute("loanDemand", demand);
		long divisionTemplateId = demand.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
			.getByBaseId(divisionTemplateId);
		List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
			.valueOf(divisionTemplateLoan.getInvestTemplateId()));
		List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
			.valueOf(divisionTemplateLoan.getRepayTemplateId()));
		//只计算经纪人
		double investAnnualInterest = 0;
		double payAnnualInterest = 0;
		if (investRolelist != null && investRolelist.size() > 0) {
			for (DivsionRuleRole druleRole : investRolelist) {
				if (DivisionPhaseEnum.INVESET_PHASE.code().equals(druleRole.getPhase())) {
					if (getJjrRoleId().equals(String.valueOf(druleRole.getRoleId()))) {
						investAnnualInterest += druleRole.getRule();
					}
					
				}
			}
		}
		if (repayRolelist != null && repayRolelist.size() > 0) {
			for (DivsionRuleRole druleRole : repayRolelist) {
				if (DivisionPhaseEnum.REPAY_PHASE.code().equals(druleRole.getPhase())) {
					if (getJjrRoleId().equals(String.valueOf(druleRole.getRoleId()))) {
						payAnnualInterest += druleRole.getRule();
					}
				}
			}
		}
		long investorId = 0;
		if (userInvests != null && userInvests.size() > 0) {
			for (UserInvestEntry invest : userInvests) {
				if (DivisionPhaseEnum.ORIGINAL_PHASE.code().equals(invest.getTransferPhase())) {
					model.addAttribute("tradeItem", invest);
					investorId = invest.getInvestorId();
				}
			}
		}
		conditions = new HashMap<String, Object>();
		conditions.put("tradeId", trade.getId());
		conditions.put("roleId", getJjrRoleId());
		conditions.put("detailId", detailId);
		List<TradeQueryDetail> jjrTrades = tradeService.getTradeDetailByConditions(conditions);
		if (jjrTrades != null && jjrTrades.size() > 0) {
			TradeDetail detail = jjrTrades.get(0);
			model.addAttribute("jjrTrade", detail);
			String repayStatus = TradeUtil.getRepayStatus(trade, detail);
			model.addAttribute("repayStatus", repayStatus);
		}
		double totalAnnualInterest = (investAnnualInterest + payAnnualInterest) * 100;
		double totolInterest = new BigDecimal(totalAnnualInterest).setScale(2,
			BigDecimal.ROUND_HALF_UP).doubleValue();
		String percent = "0%";
		
		if (trade.getDeadline().before(new Date())) {
			if (isFullScale(trade)) {
				percent = "100.0%";
			} else {
				percent = MoneyUtil.getPercentage(trade.getLoanedAmount(), trade.getAmount(),
					trade.getLeastInvestAmount());
			}
		} else {
			percent = MoneyUtil.getPercentage(trade.getLoanedAmount(), trade.getAmount(),
				trade.getLeastInvestAmount());
		}
		model.addAttribute("percent", percent);
		model.addAttribute("jjrDivisionInterest", totolInterest);
		model.addAttribute("trade", trade);
		return BROCKER_MANAGE_PATH + "/broker-sales-detail.vm";
	}
	
	//-----------------------------------------------------营销交易列表----------------------------------------------------------------------
	
	@RequestMapping("salesList")
	public String salesList(QueryTradeOrder queryConditions, HttpSession session,
							PageParam pageParam, Model model) throws Exception {
		
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		
		model.addAttribute("queryConditions", queryConditions);
		queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		queryConditions.setRoleId(11L);
		if (queryConditions.getStatus() != null && queryConditions.getStatus().size() > 0) {
			if ("00".equals(queryConditions.getStatus().get(0))) {
				queryConditions.setStatus(null);
			}
		}
		Page<AgentInvestorTrade> page = tradeService.pageQueryBrokerInvestorTrade(queryConditions,
			pageParam);
		long totalAmount = countTotalAmount(page.getResult());
		long allAmount = tradeService.queryAllAmount(queryConditions);
		model.addAttribute("allAmount", MoneyUtil.getFormatAmount(allAmount));
		model.addAttribute("page", page);
		model.addAttribute("totalAmount", totalAmount);
		if (queryConditions.getStatus() != null && queryConditions.getStatus().size() > 0) {
			model.addAttribute("status", queryConditions.getStatus().get(0));
		}
		return BROCKER_MANAGE_PATH + "broker-salesList.vm";
	}
	
	/**
	 * 统计合计金额
	 * 
	 * @param result
	 * @return
	 */
	private long countTotalAmount(List<AgentInvestorTrade> result) {
		long totalAmount = 0;
		if (result != null && result.size() > 0) {
			for (AgentInvestorTrade trade : result) {
				totalAmount += trade.getBrokerBenifitAmount();
			}
		}
		return totalAmount;
	}
	
	@RequestMapping("profitAsignInfo")
	public String profitAsignInfo(String userBaseId, HttpSession session, Model model)
																						throws Exception {
		
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		
		model.addAttribute("customerId", userBaseId);
		UserBaseInfoDO reciever = userBaseInfoManager.queryByUserBaseId(userBaseId);
		Long receiveId = reciever.getUserId();
		ProfitAsignInfo profitAsignInfo = userRelationManager.queryByReceiveId(receiveId);
		if (profitAsignInfo != null) {
			model.addAttribute("alreadySet", "yes");
			model.addAttribute("distributionQuota",
				CommonUtil.mul(profitAsignInfo.getDistributionQuota(), 100));
			model.addAttribute("customer", reciever.getRealName());
			model.addAttribute("tblBaseId", profitAsignInfo.getTblBaseId());
			model.addAttribute("note", profitAsignInfo.getNote());
		}
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return BROCKER_MANAGE_PATH + "broker-asign-profit.vm";
	}
	
	@ResponseBody
	@RequestMapping("setQuota")
	public Object setQuota(String customerId, String tblBaseId, double distributionQuota,
							String note, String token, HttpSession session) {
		logger.info("设置佣金额度，入参：[customerId={},tblBaseId={}],", customerId, tblBaseId);
		Map<String, Object> map = new HashMap<String, Object>();
		String getToken = (String) session.getAttribute("token");
		if (distributionQuota >= 0 && distributionQuota <= 100) {
			distributionQuota = CommonUtil.div(distributionQuota, 100);
			try {
				if (token.equals(getToken)) {
					session.removeAttribute("token");
					UserBaseInfoDO reciever = userBaseInfoManager.queryByUserBaseId(customerId);
					Long distributionId = SessionLocalManager.getSessionLocal().getUserId();
					Long receiveId = reciever.getUserId();
					if (StringUtil.isNotEmpty(tblBaseId)) {
						ProfitAsignInfo profitAsignInfo = userRelationManager
							.queryByReceiveId(receiveId);
						profitAsignInfo.setDistributionQuota(distributionQuota);
						profitAsignInfo.setModifyTime(new Date());
						profitAsignInfo.setNote(note);
						UserRelationReturnEnum returnEnum = userRelationManager
							.updateProfit(profitAsignInfo);
						if (returnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
							map.put("code", 1);
							map.put("message", "设置佣金成功");
						} else {
							map.put("code", 0);
							map.put("message", "设置佣金失败");
						}
					} else {
						ProfitAsignInfo profitAsignInfo = new ProfitAsignInfo();
						String uuid = UUID.randomUUID().toString();
						profitAsignInfo.setTblBaseId(uuid);
						profitAsignInfo.setAddTime(new Date());
						profitAsignInfo.setDistributionId(distributionId);
						profitAsignInfo.setReceiveId(receiveId);
						profitAsignInfo.setDistributionQuota(distributionQuota);
						profitAsignInfo.setNote(note);
						UserRelationReturnEnum returnEnum = userRelationManager
							.insertProfit(profitAsignInfo);
						;
						if (returnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
							map.put("code", 1);
							map.put("message", "设置佣金成功");
						} else {
							map.put("code", 0);
							map.put("message", "设置佣金失败");
						}
					}
				} else {
					map.put("code", 0);
					map.put("message", "请勿重复提交");
				}
			} catch (Exception e) {
				map.put("code", 0);
				map.put("message", "设置佣金异常");
			}
		} else {
			map.put("code", 0);
			map.put("message", "额度介于0-100之间");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("distroyQuota")
	public Object distroyQuota(String tblBaseId, String token, HttpSession session) {
		logger.info("取消佣金额度，入参：[tblBaseId={}],", tblBaseId);
		Map<String, Object> map = new HashMap<String, Object>();
		String getToken = (String) session.getAttribute("token");
		try {
			if (token.equals(getToken)) {
				session.removeAttribute("token");
				if (StringUtil.isNotEmpty(tblBaseId)) {
					UserRelationReturnEnum returnEnum = userRelationManager
						.deleteProfitAsign(tblBaseId);
					if (returnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
						map.put("code", 1);
						map.put("message", "取消佣金分配成功");
					} else {
						map.put("code", 0);
						map.put("message", "取消佣金分配失败");
					}
				}
			} else {
				map.put("code", 0);
				map.put("message", "请勿重复提交");
			}
		} catch (Exception e) {
			map.put("code", 0);
			map.put("message", "取消佣金分配异常");
		}
		return map;
	}
	
	/**
	 * 是否满标
	 *
	 * @return
	 */
	
	public boolean isFullScale(Trade trade) {
		String method = trade.getSaturationConditionMethod();
		if (method.equalsIgnoreCase(TradeFullConditionEnum.AMOUNT.code())) {
			long scale = Long.parseLong(trade.getSaturationCondition());
			if (trade.getLoanedAmount() >= scale || trade.getLoanedAmount() >= trade.getAmount()) {
				return true;
			}
		} else if (method.equalsIgnoreCase(TradeFullConditionEnum.PERCENTAGE.code())) {
			double percentage = ((double) trade.getLoanedAmount() / (double) trade.getAmount());
			if (percentage >= Double.parseDouble(trade.getSaturationCondition())) {
				return true;
			}
		} else if (method.equalsIgnoreCase(TradeFullConditionEnum.DATE.code())) {
			if (System.currentTimeMillis() >= DateUtil.parse(trade.getSaturationCondition())
				.getTime()) {
				return true;
			}
		}
		return false;
	}
}
