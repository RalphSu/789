package com.icebreak.p2p.front.controller.business.marketing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.AgentInvestorTrade;
import com.icebreak.p2p.dataobject.DivisionTemplateLoanDO;
import com.icebreak.p2p.dataobject.DivsionRuleRole;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserInvestEntry;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.dataobject.viewObject.PersonalInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.SendInformation;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.web.util.TradeUtil;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.UserTypeEnum;
import com.icebreak.util.lang.util.StringUtil;

@Controller
@RequestMapping("marketingCenter")
public class MarketingAgencyController extends BaseAutowiredController {
	String						MARKETING_MANAGE_PATH	= "/front/business/marketing/";
	private final static String	JGAGENTPREFIX			= "";							//机构经纪人前缀 
	private final static int	MEMBERSCALEDEFULT		= 5;							//机构人员规模默认
	private final static int	MEMBERSCALEVIP			= 8;							//机构人员规模高级
	private String				yxjgId					= "10";
	
	public String getYxjgId() {
		return yxjgId;
	}
	
	public void setYxjgId(String yxjgId) {
		this.yxjgId = yxjgId;
	}
	
	//-----------------------------------------------------经纪人管理----------------------------------------------------------------------
	
	@RequestMapping("brokerManage")
	public String brokerManage(HttpSession session, QueryConditions queryConditions,
								PageParam pageParam, Model model) throws Exception {
		session.setAttribute("current", 7);
		queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		Page<PersonalInfoVO> page = personalInfoManager.pageChildrenVO(queryConditions, pageParam);
		model.addAttribute("page", page);
		model.addAttribute("queryConditions", queryConditions);
		return MARKETING_MANAGE_PATH + "marketing-broker-manage.vm";
	}
	
	@RequestMapping("addBroker")
	public String addBroker(HttpSession session, Model model) throws Exception {
		session.setAttribute("current", 7);
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return MARKETING_MANAGE_PATH + "marketing-broker-add.vm";
	}
	
	@RequestMapping("addBrokerSubmit")
	public String addBrokerSubmit(HttpServletRequest request, HttpSession session, String token,
									PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo,
									Model model) throws Exception {
		session.setAttribute("current", 7);
		String return_vm = MARKETING_MANAGE_PATH + "marketing-broker-addNO.vm";
		String getToken = (String) session.getAttribute("token");
		if (getToken != null) {
			if (getToken.equals(token)) {
				session.removeAttribute("token");

				userBaseInfo.setAccountName(AppConstantsUtil.getYrdPrefixion()
											+ userBaseInfo.getUserName());
				userBaseInfo.setLogPassword("888888");
				userBaseInfo.setPayPassword("888888");
				userBaseInfo.setType("GR");
				userBaseInfo.setState("inactive");
				PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
				try {
					returnEnum = personalInfoManager.addPersonalInfo(personalInfo, userBaseInfo, 0,
						new int[] { 1, 11 });
				} catch (Exception e) {
					model.addAttribute("reason", "处理新增账户信息异常！");
					logger.error("处理新增账户信息异常{}", e.getMessage(), e);
					return return_vm;
				}
				if (returnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
					UserBaseInfoDO curUser = userBaseInfoManager.queryByUserName(
						userBaseInfo.getUserName(), 0);
					Map<String, Object> res = addMember(SessionLocalManager.getSessionLocal()
						.getUserId(), curUser.getUserId());
					if (1 == (Integer) res.get("code")) {
						model.addAttribute("res", "1");
						String memNo = (String) res.get("validNo");
						//发送邮件
						mailService.sendBrokerMail(request, SendInformation.sendBrokerMail(
							curUser.getMail(), curUser.getRealName(), memNo, 25L));
					} else {
						model.addAttribute("res", "0");
					}
					String tokenOk = UUID.randomUUID().toString();
					session.setAttribute("tokenOk", tokenOk);
				}
			}
		}
		String getTokenOk = (String) session.getAttribute("tokenOk");
		if (getTokenOk != null) {
			return_vm = MARKETING_MANAGE_PATH + "marketing-broker-addOK.vm";
		}
		model.addAttribute("reason", "处理新增账户信息失败！");
		return return_vm;
	}
	
	public Map<String, Object> addMember(Long parentId, Long childId) {
		logger.info("进入添加机构成员，入参：[{" + parentId + "}]，[{" + childId + "}]");
		String validNo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		try {
			if (childId > 0) {
				if (!checkChildIdForAddMember(childId)) {
					map.put("code", 0);
					map.put("message", "该成员不是经纪人");
					return map;
				}
				List<UserBaseInfoDO> parentJGs = userBaseInfoManager.queryByType("JG",
					String.valueOf(parentId));
				UserBaseInfoDO parentJG = null;
				if (parentJGs != null && parentJGs.size() > 0) {
					parentJG = parentJGs.get(0);
				} else {
					map.put("code", 3);
					map.put("message", "未找到该机构");
					return map;
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
					if (String.valueOf(currentNo).endsWith("4")) {
						currentNo++;
					}
					if (currentNo > endNo) {
						map.put("code", 2);
						map.put("message", "编号已满");
						return map;
					}
					int memberScale = 0;
					if ("高级机构".equals(parentJG.getType())) {
						memberScale = MEMBERSCALEVIP;
					} else {
						memberScale = MEMBERSCALEDEFULT;
					}
					String sino = StringUtils.leftPad(String.valueOf(currentNo), memberScale, "0");
					memberNo = indentity + JGAGENTPREFIX + sino;//串号拼接 
					Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
						null, null, memberNo);
					if (page.getResult() != null && page.getResult().size() > 0) {
						count++;
					} else {
						availabelFlag = true;
						logger.info("可用经纪人编号：" + memberNo);
					}
				}
				validNo = memberNo;
				returnEnum = userRelationManager.insert(new UserRelationDO(parentId, childId,
					memberNo));
			}
			if (returnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
				map.put("code", 1);
				map.put("message", "添加机构成员成功");
				map.put("validNo", validNo);
			} else {
				map.put("code", 0);
				map.put("message", "添加构成员失败");
			}
		} catch (Exception e) {
			map.put("code", 0);
			map.put("message", "添加构成员失败");
			logger.error("结束添加机构成员，发生异常：{}", e.toString(), e);
		}
		logger.error("结束添加机构成员，结果：{}", map);
		return map;
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
	
	//-----------------------------------------------------投资人管理----------------------------------------------------------------------
	@RequestMapping("investorManage/{brokerId}")
	public String investorManage(@PathVariable long brokerId, HttpSession session,
									QueryConditions queryConditions, PageParam pageParam,
									Model model) throws Exception {
		session.setAttribute("current", 7);
		queryConditions.setUserId(brokerId);
		Page<PersonalInfoVO> page = personalInfoManager.pageChildrenVO(queryConditions, pageParam);
		model.addAttribute("page", page);
		model.addAttribute("queryConditions", queryConditions);
		return MARKETING_MANAGE_PATH + "marketing-broker-investor-manage.vm";
	}
	
	//-----------------------------------------------------经纪人详情----------------------------------------------------------------------
	
	@RequestMapping("brokerInfo")
	public String brokerInfo(String userBaseId, Model model) throws Exception {
		UserBaseInfoDO userBase = userBaseInfoManager.queryByUserBaseId(userBaseId);
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
			model.addAttribute("userType", UserTypeEnum.JG.code());
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
		return MARKETING_MANAGE_PATH + "marketing-brokerInfo.vm";
	}
	
	//-----------------------------------------------------营销统计----------------------------------------------------------------------
	
	@RequestMapping("salesCount")
	public String salesCount(HttpSession session, PageParam pageParam, Model model)
																					throws Exception {
		session.setAttribute("current", 6);
		QueryTradeOrder queryConditions = new QueryTradeOrder();
		queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		queryConditions.setRoleId(10L);
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
					repayFaildInvestCount++;
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
		return MARKETING_MANAGE_PATH + "marketing-salesCount.vm";
	}
	
	/**
	 * 营销详情
	 * @param tradeId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("salesDetails/{tradeId}/{detailId}")
	public String salesDetails(@PathVariable long tradeId, @PathVariable long detailId,
								long investDetailId, Model model) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		List<UserInvestEntry> userInvests = tradeService.getEntriesByTradeIdAndDetailId(tradeId,
			investDetailId);
		LoanDemandDO demand = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
		model.addAttribute("loanItem", demand);
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
					if (getYxjgId().equals(String.valueOf(druleRole.getRoleId()))) {
						investAnnualInterest += druleRole.getRule();
					}
					
				}
			}
		}
		if (repayRolelist != null && repayRolelist.size() > 0) {
			for (DivsionRuleRole druleRole : repayRolelist) {
				if (DivisionPhaseEnum.REPAY_PHASE.code().equals(druleRole.getPhase())) {
					if (getYxjgId().equals(String.valueOf(druleRole.getRoleId()))) {
						payAnnualInterest += druleRole.getRule();
					}
				}
			}
		}
		if (userInvests != null && userInvests.size() > 0) {
			for (UserInvestEntry invest : userInvests) {
				if (DivisionPhaseEnum.ORIGINAL_PHASE.code().equals(invest.getTransferPhase())) {
					model.addAttribute("tradeItem", invest);
				}
			}
		}
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tradeId", trade.getId());
		conditions.put("roleId", getYxjgId());
		conditions.put("detailId", detailId);
		List<TradeQueryDetail> yxjgTrades = tradeService.getTradeDetailByConditions(conditions);
		if (yxjgTrades != null && yxjgTrades.size() > 0) {
			TradeDetail detail = yxjgTrades.get(0);
			model.addAttribute("yxjgTrade", detail);
			String repayStatus = TradeUtil.getRepayStatus(trade, detail);
			model.addAttribute("repayStatus", repayStatus);
		}
		double totalAnnualInterest = (investAnnualInterest + payAnnualInterest) * 100;
		model
			.addAttribute("yxjgDivisionInterest",
				new BigDecimal(totalAnnualInterest).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue());
		model.addAttribute("trade", trade);
		return MARKETING_MANAGE_PATH + "marketing-sales-detail.vm";
	}
	
	//-----------------------------------------------------营销交易列表----------------------------------------------------------------------
	
	@RequestMapping("salesList")
	public String salesList(QueryTradeOrder queryConditions, HttpSession session,
							PageParam pageParam, Model model) throws Exception {
		session.setAttribute("current", 6);
		queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		queryConditions.setRoleId(10L);
		Page<AgentInvestorTrade> page = tradeService.pageQueryBrokerInvestorTrade(queryConditions,
			pageParam);
		model.addAttribute("queryConditions", queryConditions);
		model.addAttribute("page", page);
		long totalAmount = tradeService.queryAllAmount(queryConditions);
		model.addAttribute("totalAmount", totalAmount);
		return MARKETING_MANAGE_PATH + "marketing-salesList.vm";
	}
	
	public static void main(String[] args) {
		String s = null;
		System.out.println(s.length());
	}
}
