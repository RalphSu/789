package com.icebreak.p2p.front.controller.business.guarantee;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.util.*;
import com.icebreak.p2p.ws.info.CommonAttachmentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.CommonBindingInitializer;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.viewObject.LoanDemandTradeVO;
import com.icebreak.p2p.front.controller.trade.download.InvestReceiptPDFCreator;
import com.icebreak.p2p.integration.openapi.info.ThirdPartAccountInfo;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.InvestService;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.web.util.RateProcessorUtil;
import com.icebreak.p2p.web.util.TradeUtil;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;

@Controller
@RequestMapping("guaranteeCenter")
public class GuaranteeCenterController extends UserAccountInfoBaseController {
	/** 返回页面路径 */
	String GUARANTEE_MANAGE_PATH = "/front/business/guarantee/";
	
	private long guaranteeUserId = 0;
	@Autowired
	IOperatorInfoService operatorInfoService;
	@Autowired
	InvestService investService;
	
	@Autowired
	protected InvestReceiptPDFCreator receiptPDFCreator;
	
	/**
	 * 工具类处理金额 /时间
	 * */
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Money.class, "minAmountIn", new CommonBindingInitializer());
		binder.registerCustomEditor(Money.class, "maxAmountIn", new CommonBindingInitializer());
		binder
			.registerCustomEditor(Date.class, "startTime", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "endTime", new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 查询借款管理
	 * */
	@RequestMapping("newGuaranteeManager")
	public String newGuaranteeManager(QueryTradeOrder queryConditions, PageParam pageParam,
								   Model model, HttpSession session) {
		logger.info("担保函管理,入参：{}", queryConditions);
		session.setAttribute("current", 5);
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		List<TradeStatusDO> guaranteeStatus = TradeStatusDO.buildTradeStatus();
		try {
			queryConditions.setGuaranteeId(getGuaranteeUserId());
			Page<LoanDemandTradeVO> page = loanDemandManager.pageQueryTradeForGuarantee(
					queryConditions, pageParam);
			model.addAttribute("page", page);
			long totalAmount = countTotalAmount(page.getResult());
			model.addAttribute("totalAmount", totalAmount);
		} catch (Exception e) {
			logger.info("担保函管理,查询异常{}", e.getMessage(), e);
		}
		model.addAttribute("guaranteeStatus", guaranteeStatus);
		model.addAttribute("queryConditions", queryConditions);
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userBaseId", userBaseId);
		conditions.put("limitStart", 0);
		conditions.put("pageSize", 9999);
		List<OperatorInfoDO> operatorInfos = operatorInfoService
				.queryOperatorsByProperties(conditions);
		if (operatorInfos != null && operatorInfos.size() > 0) {
			if (2 == operatorInfos.get(0).getOperatorType()
					|| 3 == operatorInfos.get(0).getOperatorType()) {
				model.addAttribute("auditOperator", "yes");
			} else {
				model.addAttribute("auditOperator", "no");
			}
		} else {
			model.addAttribute("auditOperator", "no");
		}
		logger.info("担保函管理,查询结束");
		return GUARANTEE_MANAGE_PATH + "newGuarantee_entries.vm";
	}

	/**
	 * 查询借款管理
	 * */
	@RequestMapping("guaranteeManager")
	public String guaranteeManager(QueryTradeOrder queryConditions, PageParam pageParam,
									Model model, HttpSession session) {
		logger.info("担保函管理,入参：{}", queryConditions);
		session.setAttribute("current", 5);
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		List<TradeStatusDO> guaranteeStatus = TradeStatusDO.buildTradeStatus();
		try {
			queryConditions.setGuaranteeId(getGuaranteeUserId());
			Page<LoanDemandTradeVO> page = loanDemandManager.pageQueryTradeForGuarantee(
				queryConditions, pageParam);
			model.addAttribute("page", page);
			long totalAmount = countTotalAmount(page.getResult());
			model.addAttribute("totalAmount", totalAmount);
		} catch (Exception e) {
			logger.info("担保函管理,查询异常{}", e.getMessage(), e);
		}
		model.addAttribute("guaranteeStatus", guaranteeStatus);
		model.addAttribute("queryConditions", queryConditions);
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userBaseId", userBaseId);
		conditions.put("limitStart", 0);
		conditions.put("pageSize", 9999);
		List<OperatorInfoDO> operatorInfos = operatorInfoService
			.queryOperatorsByProperties(conditions);
		if (operatorInfos != null && operatorInfos.size() > 0) {
			if (2 == operatorInfos.get(0).getOperatorType()
				|| 3 == operatorInfos.get(0).getOperatorType()) {
				model.addAttribute("auditOperator", "yes");
			} else {
				model.addAttribute("auditOperator", "no");
			}
		} else {
			model.addAttribute("auditOperator", "no");
		}
		logger.info("担保函管理,查询结束");
		return GUARANTEE_MANAGE_PATH + "guarantee_entries.vm";
	}
	
	/**
	 * 查询借款发布审核管理
	 * */
	@RequestMapping("loanDeployGuaranteeAuth")
	public String loanDeployGuaranteeAuth(QueryTradeOrder queryConditions, PageParam pageParam,
											Model model, HttpSession session) {
		logger.info("借款发布审核管理,入参：{}", queryConditions);
		session.setAttribute("current", 5);
		try {
			queryConditions.setGuaranteeId(getGuaranteeUserId());
			Page<LoanDemandTradeVO> page = loanDemandManager.pageQueryTradeForGuaranteeAuth(
				queryConditions, pageParam);
			model.addAttribute("page", page);
			long totalAmount = countTotalAmount(page.getResult());
			model.addAttribute("totalAmount", totalAmount);
		} catch (Exception e) {
			logger.info("借款发布审核管理,查询异常{}", e.getMessage(), e);
		}
		logger.info("借款发布审核管理,查询结束");
		return GUARANTEE_MANAGE_PATH + "guarantee_auth_entries.vm";
	}
	
	/**
	 * 统计合计金额
	 * @param result
	 * @return
	 */
	private long countTotalAmount(List<LoanDemandTradeVO> result) {
		long totalAmount = 0;
		if (result != null && result.size() > 0) {
			for (LoanDemandTradeVO trade : result) {
				totalAmount += trade.getBenefitAmount();
			}
		}
		return totalAmount;
	}
	
	/**
	 * 查询交易详情
	 * @throws Exception
	 * */
	@RequestMapping("guaranteeDetails")
	public String guaranteeDetails(long demandId, String operate, Model model, HttpSession session)
																									throws Exception {
		model.addAttribute("pdfhost", "");
		logger.info("担保函详情,查询开始");

		//获取项目图片
		CommonAttachmentInfo proImageInfo = commonAttachmentService.queryProImage(String.valueOf(demandId));
		String proImageURL = (proImageInfo != null ? proImageInfo.getRequestPath() : AppConstantsUtil.getProjectDefaultThumbnailUrl());
		model.addAttribute("proImageURL", proImageURL);

		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		boolean investsDownLoadable = false;
		long countInvestTimes = 0;
		
		try {
			
			String token = UUID.randomUUID().toString();
			LoanDemandDO loanDemand = loanDemandManager.queryLoanDemandByDemandId(demandId);
			UserBaseInfoDO user = userBaseInfoManager.getRealNameByUserName(loanDemand
				.getUserName());
			model.addAttribute("realName", user.getRealName());
			Map<String, Object> newCondition = new HashMap<String, Object>();
			newCondition.put("tradeCode", loanDemand.getTradeCode());
			newCondition.put("limitStart", 0);
			newCondition.put("pageSize", 1000);
			List<Trade> trades = tradeService.getTradesByCondition(newCondition);
			Trade trade = null;
			if (trades != null && trades.size() > 0) {
				trade = trades.get(0);
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("roleId", 12L);
				conditions.put("tradeId", trade.getId());
				conditions.put("transferPhase", DivisionPhaseEnum.ORIGINAL_PHASE.code());
				List<TradeQueryDetail> details = loanDemandManager
					.getTradeDetailByConditions(conditions);
				if (details != null && details.size() > 0) {
					List<UserInvestEntry> userInvestEntrys = new ArrayList<UserInvestEntry>();
					for (TradeDetail detail : details) {
						List<UserInvestEntry> userInvests = tradeService
							.getEntriesByTradeIdAndDetailId(trade.getId(), detail.getId());
						if (userInvests != null && userInvests.size() > 0) {
							userInvestEntrys.add(userInvests.get(0));
						}
					}
					model.addAttribute("investorTradeDetails", userInvestEntrys);
				}
				List<TradeDetail> loanedTradeDetails = tradeService.getByTradeIdAndRoles(
					trade.getId(), SysUserRoleEnum.LOANER.getRoleCode());
				QueryTradeOrder queryTradeOrder = new QueryTradeOrder();
				queryTradeOrder.setTradeCode(trade.getCode());
				queryTradeOrder.setUserId(loanDemand.getLoanerId());
				queryTradeOrder.setRoleId(13L);
				PageParam pageParam = new PageParam();
				pageParam.setPageSize(10000);
				Page<Trade> loanedpage = tradeService.pageQueryTrade(queryTradeOrder, pageParam);
				//获取资金信息
				
				YzzUserAccountQueryResponse account = apiTool.queryUserAccount(SessionLocalManager
					.getSessionLocal().getAccountId());
				Money availableBalance = null;
				if (account.success()) {
					availableBalance = new Money(account.getAvailableBalance());
				} else {
					logger.error("查询资金信息异常，accounId"
									+ SessionLocalManager.getSessionLocal().getAccountId());
					throw new Exception("查询资金信息异常，accounId-->"
										+ SessionLocalManager.getSessionLocal().getAccountId());
				}
				List<TradeDetailDTO> loanerTradeDetails = new ArrayList<TradeDetailDTO>(
					loanedTradeDetails.size());
				if (loanedTradeDetails != null) {
					for (int i = 0; i < loanedTradeDetails.size(); i++) {
						TradeDetailDTO tradeDetailDTO = new TradeDetailDTO();
						tradeDetailDTO.setTradeDetail(loanedTradeDetails.get(i));
						for (Trade curTrade : loanedpage.getResult()) {
							if (tradeDetailDTO.getTradeDetail().getTradeId() == curTrade.getId()) {
								tradeDetailDTO.setFinishDate(curTrade.getEffectiveDateTime());
								tradeDetailDTO.setLoanedAmount(curTrade.getLoanedAmount());
							}
						}
						loanerTradeDetails.add(tradeDetailDTO);
					}
				}
				model.addAttribute("loanerTradeDetails", loanerTradeDetails);
				model.addAttribute("trade", trade);
				if (availableBalance.getCent() < trade.getAmount()) {
					model.addAttribute("poorAmount",
						(trade.getLoanedAmount()) - (availableBalance.getCent()));
				} else {
					model.addAttribute("poorAmount", -1);
				}
			}
			model.addAttribute("loanDemand", loanDemand);
			if (trade == null) {
				return GUARANTEE_MANAGE_PATH + "guarantee_details_noTrade.vm";
			} else {
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("roleId", 13L);
				conditions.put("tradeId", trade.getId());
				List<TradeQueryDetail> details = loanDemandManager
					.getTradeDetailByConditions(conditions);
				if (details != null && details.size() > 0) {
					TradeFlowCode tradeFlow = tradeService
						.queryInvestFlowCodesByTradeDetailId(details.get(0).getId());
					if (tradeFlow != null) {
						String loanFlowCode = tradeFlow.getTradeFlowCode();
						model.addAttribute("loanFlowCode", loanFlowCode);
					} else {
						Map<String, Object> cond = new HashMap<String, Object>();
						cond.put("roleId", 12L);
						cond.put("tradeId", trade.getId());
						cond.put("transferPhase", DivisionPhaseEnum.ORIGINAL_PHASE.code());
						List<TradeQueryDetail> des = loanDemandManager
							.getTradeDetailByConditions(cond);
						if (des != null && des.size() > 0) {
							for (TradeDetail detail : des) {
								List<UserInvestEntry> userInvests = tradeService
									.getEntriesByTradeIdAndDetailId(trade.getId(), detail.getId());
								if (userInvests != null && userInvests.size() > 0) {
									String str = userInvests.get(0).getInvestflowCode();
									model.addAttribute("loanFlowCode",
										str.subSequence(0, str.length() - 4) + "R");
								}
							}
						}
					}
				}
			}
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("userId", getGuaranteeUserId());
			conditions.put("roleId", 8L);
			conditions.put("tradeId", trade.getId());
			List<TradeQueryDetail> details = loanDemandManager
				.getTradeDetailByConditions(conditions);
			if (details != null && details.size() > 0) {
				TradeDetail detail = details.get(0);
				model.addAttribute("guaranteeBenifitAmount", detail.getAmount());
				String repayStatus = TradeUtil.getRepayStatus(trade, detail);
				model.addAttribute("repayStatus", repayStatus);
			}
			model.addAttribute("tradeStatus", TradeUtil.getNormalTradeStatus(trade));
			//还款阶段利率
			LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
			long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
			DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
				.getByBaseId(divisionTemplateLoanBaseId);
			List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getInvestTemplateId()));
			List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getRepayTemplateId()));
			double investAnnualInterest = 0;
			double payAnnualInterest = 0;
			double guaranteeAnnualInterest = 0;
			double investInterest = 0;
			double payInterest = 0;
			double repayCutAmount = 0;
			if (investRolelist != null && investRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : investRolelist) {
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(druleRole.getPhase())) {
						investAnnualInterest += druleRole.getRule();
						BigDecimal bg = new BigDecimal(RateProcessorUtil.getDaysRuleRate(
							druleRole.getRule(), trade));
						investInterest += bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
						if (YrdConstants.RoleId.GUARANTEE.equals(String.valueOf(druleRole
							.getRoleId()))) {
							guaranteeAnnualInterest += druleRole.getRule();
						}
					}
				}
			}
			if (repayRolelist != null && repayRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : repayRolelist) {
					payAnnualInterest += druleRole.getRule();
					if (DivisionPhaseEnum.REPAY_PHASE.code().equals(druleRole.getPhase())) {
						BigDecimal bg = new BigDecimal(RateProcessorUtil.getDaysRuleRate(
							druleRole.getRule(), trade));
						double interest = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
						double amount = trade.getLoanedAmount() * interest;
						amount = Math.round(amount);
						repayCutAmount += amount;
						payInterest += interest;
						if (YrdConstants.RoleId.GUARANTEE.equals(String.valueOf(druleRole
							.getRoleId()))) {
							guaranteeAnnualInterest += druleRole.getRule();
						}
					}
				}
			}
			double totalAnnualInterest = (investAnnualInterest + payAnnualInterest) * 100;
			totalAnnualInterest = new BigDecimal(totalAnnualInterest).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
			guaranteeAnnualInterest = new BigDecimal(guaranteeAnnualInterest * 100).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
			repayCutAmount = Math.round(repayCutAmount);
			long repayAmount = trade.getLoanedAmount() + (long) repayCutAmount;
			model.addAttribute("repayCutAmount", (long) repayCutAmount);
			model.addAttribute("repayAmount", repayAmount);
			model.addAttribute("operate", operate);
			session.setAttribute("token", token);
			model.addAttribute("totalAnnualInterest", totalAnnualInterest);
			model.addAttribute("guaranteeAnnualInterest", guaranteeAnnualInterest);
		} catch (Exception e) {
			logger.info("担保函详情,查询异常{}", e.getMessage(), e);
		}
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("loanDemandId", demandId);
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
		Map<String, Object> conditions2 = new HashMap<String, Object>();
		conditions2.put("loanDemandId", demandId);
		conditions2.put("authType", "MAKELOANLVTWO");
		long count2 = loanDemandManager.countLoanAuthRecordByCondition(conditions2);
		if (count2 > 0) {
			List<LoanAuthRecord> reccord2 = loanDemandManager
				.getLoanAuthRecordByCondition(conditions2);
			List<UserBaseInfoDO> users = userBaseInfoManager.queryByType("JG",
				String.valueOf(reccord2.get(0).getAuthUserId()));
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
			model.addAttribute("auditrecord2", reccord2.get(0));
			model.addAttribute("audit", "yes");
		}
		Map<String, Object> opConditions = new HashMap<String, Object>();
		opConditions.put("userBaseId", SessionLocalManager.getSessionLocal().getUserBaseId());
		opConditions.put("limitStart", 0);
		opConditions.put("pageSize", 9999);
		List<OperatorInfoDO> operatorInfos = operatorInfoService
			.queryOperatorsByProperties(opConditions);
		if (operatorInfos != null && operatorInfos.size() > 0) {
			if (2 == operatorInfos.get(0).getOperatorType()) {
				Map<String, Object> conditions3 = new HashMap<String, Object>();
				conditions3.put("loanDemandId", demandId);
				conditions3.put("authType", "MAKELOANLVONE");
				long count3 = loanDemandManager.countLoanAuthRecordByCondition(conditions3);
				if (count3 <= 0) {
					model.addAttribute("authOperator", "level1");
				}
			} else if (3 == operatorInfos.get(0).getOperatorType()) {
				Map<String, Object> conditions3 = new HashMap<String, Object>();
				conditions3.put("loanDemandId", demandId);
				conditions3.put("authType", "MAKELOANLVONE");
				long count3 = loanDemandManager.countLoanAuthRecordByCondition(conditions3);
				if (count3 > 0) {
					model.addAttribute("authOperator", "level2");
				}
			}
		}
		
		//是否投资过某融资需求，决定是否允许下载合同，担保函 
		if (sessionLocal != null) {
			
			this.initAccountInfo(model);
			
			countInvestTimes = tradeService.countInvestTimes(SessionLocalManager.getSessionLocal()
				.getUserBaseId(), demandId, null);
			
			//是否可下载某融资需求的投资人列表
			investsDownLoadable = institutionsInfoManager.isFromSameOrgan(SessionLocalManager
				.getSessionLocal().getUserBaseId(), auditUserBaseId);
		}
		
		model.addAttribute("downLoadableInvests", investsDownLoadable);
		model.addAttribute("countInvestTimes", countInvestTimes);
		return GUARANTEE_MANAGE_PATH + "query_guarantee_details.vm";
	}
	
	//-----------------------------------------------------营销统计----------------------------------------------------------------------
	
	@RequestMapping("salesCount")
	public String salesCount(HttpSession session, PageParam pageParam, Model model)
																					throws Exception {
		session.setAttribute("current", 5);
		QueryTradeOrder queryConditions = new QueryTradeOrder();
		queryConditions.setUserId(getGuaranteeUserId());
		queryConditions.setRoleId(8L);
		pageParam.setPageSize(999999999);
		Page<AgentLoanerTrade> page = tradeService.pageQueryAgencyLoanerTrade(queryConditions,
			pageParam);
		long collectingInvestCount = 0;
		long auditingInvestCount = 0;
		long repaingInvestCount = 0;
		long faildInvestCount = 0;
		long repayFaildInvestCount = 0;
		long doRepayInvestCount = 0;
		long repayFinishInvestCount = 0;
		long compensatoryRepayFinishInvestCount = 0;
		long collectingInvestAmount = 0;
		long paidInvestAmount = 0;
		long faildInvestAmount = 0;
		long waitToPayInvestAmount = 0;
		long totalAmount = 0;
		if (page.getResult() != null && page.getResult().size() > 0) {
			for (AgentLoanerTrade trade : page.getResult()) {
				if (YrdConstants.TradeStatus.TRADING == trade.getStatus()) {
					//collectingInvestCount++;
					collectingInvestAmount += trade.getAgencyBenifitAmount();
				} else if (YrdConstants.TradeStatus.REPAYING == trade.getStatus()) {
					repaingInvestCount++;
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(trade.getTransferPhase())) {
						paidInvestAmount += trade.getAgencyBenifitAmount();
					} else if (DivisionPhaseEnum.REPAY_PHASE.code()
						.equals(trade.getTransferPhase())) {
						waitToPayInvestAmount += trade.getAgencyBenifitAmount();
					}
				} else if (YrdConstants.TradeStatus.FAILED == trade.getStatus()) {
					faildInvestCount++;
					faildInvestAmount += trade.getAgencyBenifitAmount();
				} else if (YrdConstants.TradeStatus.REPAYING_FAILD == trade.getStatus()) {
					repayFaildInvestCount++;
					if (DivisionPhaseEnum.INVESET_PHASE.equals(trade.getTransferPhase())) {
						paidInvestAmount += trade.getAgencyBenifitAmount();
					} else if (DivisionPhaseEnum.REPAY_PHASE.code()
						.equals(trade.getTransferPhase())) {
						waitToPayInvestAmount += trade.getAgencyBenifitAmount();
					}
				} else if (YrdConstants.TradeStatus.REPAY_FINISH == trade.getStatus()) {
					repayFinishInvestCount++;
					paidInvestAmount += trade.getAgencyBenifitAmount();
				} else if (YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH == trade.getStatus()) {
					compensatoryRepayFinishInvestCount++;
					paidInvestAmount += trade.getAgencyBenifitAmount();
				} else if (YrdConstants.TradeStatus.GUARANTEE_AUDITING == trade.getStatus()) {
					auditingInvestCount++;
					waitToPayInvestAmount += trade.getAgencyBenifitAmount();
				} else if (YrdConstants.TradeStatus.DOREPAY == trade.getStatus()) {
					doRepayInvestCount++;
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(trade.getTransferPhase())) {
						paidInvestAmount += trade.getAgencyBenifitAmount();
					} else if (DivisionPhaseEnum.REPAY_PHASE.code()
						.equals(trade.getTransferPhase())) {
						waitToPayInvestAmount += trade.getAgencyBenifitAmount();
					}
				}
			}
		}
		totalAmount = paidInvestAmount + waitToPayInvestAmount;
		model.addAttribute("page", page);
		QueryTradeOrder guaranteeQueryConditions = new QueryTradeOrder();
		guaranteeQueryConditions.setGuaranteeId(getGuaranteeUserId());
		Page<LoanDemandTradeVO> guaranteePage = loanDemandManager.pageQueryTradeForGuarantee(
			guaranteeQueryConditions, pageParam);
		if (guaranteePage.getResult() != null && guaranteePage.getResult().size() > 0) {
			for (LoanDemandTradeVO vo : guaranteePage.getResult()) {
				if (YrdConstants.GuaranteeTradeStatus.GUARANTEE_SIGN
					.equals(vo.getGuaranteeStatus())) {
					collectingInvestCount++;
				}
			}
		}
		model.addAttribute("collectingInvestCount", collectingInvestCount);
		model.addAttribute("repaingInvestCount", repaingInvestCount);
		model.addAttribute("faildInvestCount", faildInvestCount);
		model.addAttribute("repayFaildInvestCount", repayFaildInvestCount);
		model.addAttribute("repayFinishInvestCount", repayFinishInvestCount);
		model.addAttribute("collectingInvestAmount", collectingInvestAmount);
		model.addAttribute("auditingInvestCount", auditingInvestCount);
		model.addAttribute("doRepayInvestCount", doRepayInvestCount);
		model
			.addAttribute("compensatoryRepayFinishInvestCount", compensatoryRepayFinishInvestCount);
		model.addAttribute("paidInvestAmount", paidInvestAmount);
		model.addAttribute("faildInvestAmount", faildInvestAmount);
		model.addAttribute("waitToPayInvestAmount", waitToPayInvestAmount);
		model.addAttribute("totalAmount", totalAmount);
		return GUARANTEE_MANAGE_PATH + "guarantee-salesCount.vm";
	}
	
	public long getGuaranteeUserId() {
		try {
			String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
			long userId = SessionLocalManager.getSessionLocal().getUserId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			long count = userBaseInfoManager.getRolesByUserIdCount(params);
			if (count > 0) {
				List<Role> roles = userBaseInfoManager.getRolesByUserId(params);
				for (Role role : roles) {
					if (SysUserRoleEnum.OPERATOR.getRoleCode().equals(role.getCode())) {
						Map<String, Object> conditions = new HashMap<String, Object>();
						conditions.put("userBaseId", userBaseId);
						conditions.put("limitStart", 0);
						conditions.put("pageSize", 9999);
						List<OperatorInfoDO> operatorInfos = operatorInfoService
							.queryOperatorsByProperties(conditions);
						if (operatorInfos != null && operatorInfos.size() > 0) {
							List<UserBaseInfoDO> curParentJGs = userBaseInfoManager.queryByType(
								"JG", String.valueOf(operatorInfos.get(0).getParentId()));
							if (curParentJGs != null && curParentJGs.size() > 0) {
								userId = curParentJGs.get(0).getUserId();
							}
						}
					}
				}
			}
			guaranteeUserId = userId;
		} catch (Exception e) {
			logger.error("获取担保机构userId失败", e);
		}
		
		return guaranteeUserId;
	}
	
	public void setGuaranteeUserId(long guaranteeUserId) {
		this.guaranteeUserId = guaranteeUserId;
	}
	
	@ResponseBody
	@RequestMapping("validationCheckPwd")
	public Object validationCheckPwd(String checkPassword) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		JSONObject jsonobj = new JSONObject();
		checkPassword = RSAUtils.decryptStringByJs(checkPassword);
		returnEnum = userBaseInfoManager.validationPayPassword(SessionLocalManager
			.getSessionLocal().getUserBaseId(), checkPassword);
		if (returnEnum == UserBaseReturnEnum.PASSWORD_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "审核密码正确");
		}
		if (returnEnum == UserBaseReturnEnum.PASSWORD_ERROR) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "审核密码错误");
		}
		return jsonobj;
	}
	
	/**
	 * 跳转设置密码界面
	 * */
	@RequestMapping("anthPasswordPage")
	public String anthPasswordPage(Model model, HttpSession session) {
		logger.info("审核一级密码设置和修改,入参：{}");
		String passwordType1 = "AUTHLEVELONE";
		String passwordType2 = "AUTHLEVELTWO";
		long countLv1 = userService.countUserPwdExistByType(SessionLocalManager.getSessionLocal()
			.getUserBaseId(), passwordType1);
		if (countLv1 > 0) {
			model.addAttribute("alreadySetOne", "true");
		}
		long countLv2 = userService.countUserPwdExistByType(SessionLocalManager.getSessionLocal()
			.getUserBaseId(), passwordType2);
		if (countLv2 > 0) {
			model.addAttribute("alreadySetTwo", "true");
		}
		logger.info("审核一级密码设置和修改结束");
		return GUARANTEE_MANAGE_PATH + "guarantee-password.vm";
	}
	
	/**
	 * 新增或者修改审核密码
	 * @param session
	 * @param password
	 * @param newPassword
	 * @param newPassword2
	 * @param isSetOne
	 * @param isSetTwo
	 * @param type
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("authPasswordSubmit")
	public Object authPasswordSubmit(HttpSession session, String password, String newPassword,
										String newPassword2, String isSetOne, String isSetTwo,
										String type, Model model) {
		JSONObject jsonobj = new JSONObject();
		if (StringUtil.isNotBlank(isSetOne) || StringUtil.isNotBlank(isSetTwo)) {
			if ("true".equals(isSetOne) || "true".equals(isSetTwo)) {
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("userBaseId", SessionLocalManager.getSessionLocal().getUserBaseId());
				if ("lvOne".equals(type)) {
					conditions.put("passwordType", "AUTHLEVELONE");
				} else {
					conditions.put("passwordType", "AUTHLEVELTWO");
				}
				List<UserPasswordExtend> userPasswordExtends = userService
					.getUserEXPwdBYconditions(conditions);
				if (userPasswordExtends != null && userPasswordExtends.size() > 0) {
					UserPasswordExtend userPasswordExtend = userPasswordExtends.get(0);
					if ("lvOne".equals(type)) {
						userPasswordExtend.setPassword(newPassword);
					} else {
						userPasswordExtend.setPassword(newPassword2);
					}
					userPasswordExtend.setRowUpdateTime(new Date());
					int result = userService.updateUserPasswordExtend(userPasswordExtend);
					if (1 == result) {
						jsonobj.put("code", 1);
						if ("lvOne".equals(type)) {
							jsonobj.put("message", "一级审核密码修改成功！");
						} else {
							jsonobj.put("message", "二级审核密码修改成功！");
						}
					} else {
						jsonobj.put("code", 0);
						if ("lvOne".equals(type)) {
							jsonobj.put("message", "一级审核密码修改失败！");
						} else {
							jsonobj.put("message", "二级审核密码修改失败！");
						}
						
					}
				}
				
			} else {
				jsonobj.put("code", 0);
				if ("lvOne".equals(type)) {
					jsonobj.put("message", "一级审核密码修改失败！");
				} else {
					jsonobj.put("message", "二级审核密码修改失败！");
				}
			}
			
		} else {
			UserPasswordExtend userPasswordExtend = new UserPasswordExtend();
			
			if ("lvOne".equals(type)) {
				userPasswordExtend.setPasswordType("AUTHLEVELONE");
				userPasswordExtend.setPassword(newPassword);
			} else {
				userPasswordExtend.setPasswordType("AUTHLEVELTWO");
				userPasswordExtend.setPassword(newPassword2);
			}
			userPasswordExtend.setUserBaseId(SessionLocalManager.getSessionLocal().getUserBaseId());
			userPasswordExtend.setUserId(SessionLocalManager.getSessionLocal().getUserId());
			userPasswordExtend.setRowAddTime(new Date());
			if (1 == userService.addUserPasswordExtend(userPasswordExtend)) {
				jsonobj.put("code", 1);
				if ("lvOne".equals(type)) {
					jsonobj.put("message", "一级审核密码添加成功！");
				} else {
					jsonobj.put("message", "二级审核密码添加成功！");
				}
			} else {
				jsonobj.put("code", 0);
				if ("lvOne".equals(type)) {
					jsonobj.put("message", "一级审核密码添加失败！");
				} else {
					jsonobj.put("message", "二级审核密码添加失败！");
				}
				
			}
		}
		return jsonobj;
	}
	
	/**
	 * 找回密码
	 * @param session
	 * @param level
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findAuthPwd/{level}")
	public Object findAuthPwd(HttpSession session, @PathVariable String level) {
		JSONObject jsonobj = new JSONObject();
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userBaseId", SessionLocalManager.getSessionLocal().getUserBaseId());
		if ("lvOne".equals(level)) {
			conditions.put("passwordType", "AUTHLEVELONE");
		} else {
			conditions.put("passwordType", "AUTHLEVELTWO");
		}
		List<UserPasswordExtend> userPasswordExtends = userService
			.getUserEXPwdBYconditions(conditions);
		if (userPasswordExtends != null && userPasswordExtends.size() > 0) {
			UserPasswordExtend userPasswordExtend = userPasswordExtends.get(0);
			userPasswordExtend.setPassword("888888");
			userPasswordExtend.setRowUpdateTime(new Date());
			int result = userService.updateUserPasswordExtend(userPasswordExtend);
			if (1 == result) {
				jsonobj.put("code", 1);
				if ("lvOne".equals(level)) {
					jsonobj.put("message", "重置一级审核密码为888888,请立即修改！");
				} else {
					jsonobj.put("message", "重置二级审核密码为888888,请立即修改！");
				}
			} else {
				jsonobj.put("code", 0);
				if ("lvOne".equals(level)) {
					jsonobj.put("message", "重置一级审核密码失败！");
				} else {
					jsonobj.put("message", "重置二级审核密码失败！");
				}
				
			}
		}
		return jsonobj;
	}
	
	/**
	 * 审核密码校验
	 * @param session
	 * @param pwd
	 * @param type
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("authPasswordCheck")
	public Object authPasswordCheck(HttpSession session, String pwd, String type, Model model) {
		JSONObject jsonobj = new JSONObject();
		String passwordType = null;
		if ("lvOne".equals(type)) {
			passwordType = "AUTHLEVELONE";
		} else {
			passwordType = "AUTHLEVELTWO";
		}
		//long result = userService.validationUserPassword(pwd, SessionLocalManager.getSessionLocal().getUserBaseId(), passwordType);
		UserBaseReturnEnum returnEnum = null;
		try {
			returnEnum = userBaseInfoManager.validationPayPassword(SessionLocalManager
				.getSessionLocal().getUserBaseId(), pwd);
			if (returnEnum == UserBaseReturnEnum.PASSWORD_SUCCESS) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "密码匹配！");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "密码不匹配！");
			}
		} catch (Exception e1) {
			logger.error("validationPayPassword异常", e1);
			jsonobj.put("code", 0);
			jsonobj.put("message", "密码不匹配！");
		}
		return jsonobj;
	}
	
	/**
	 * 担保机构审核详情
	 */
	
	@RequestMapping("loanDeployGuartAuthDetail/{demandId}")
	public String loanDeployGuartAuthDetail(@PathVariable long demandId, Model model,
											HttpSession session) {
		logger.info("担保函详情,查询开始");
		try {
			String token = UUID.randomUUID().toString();
			LoanDemandDO loanDemand = loanDemandManager.queryLoanDemandByDemandId(demandId);
			model.addAttribute("loanDemand", loanDemand);
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("loanDemandId", demandId);
			conditions.put("authType", "DEPLOYLVTWO");
			long count = loanDemandManager.countLoanAuthRecordByCondition(conditions);
			if (count <= 0) {
				model.addAttribute("notAuth", "true");
			}
			//还款阶段利率
			LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(demandId);
			long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
			DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
				.getByBaseId(divisionTemplateLoanBaseId);
			List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getInvestTemplateId()));
			List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getRepayTemplateId()));
			double investAnnualInterest = 0;
			double payAnnualInterest = 0;
			double guaranteeAnnualInterest = 0;
			if (investRolelist != null && investRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : investRolelist) {
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(druleRole.getPhase())) {
						double bg = CommonUtil.mulDI(druleRole.getRule(), 100);
						investAnnualInterest = CommonUtil.addDD(investAnnualInterest, bg);
						if (YrdConstants.RoleId.GUARANTEE.equals(String.valueOf(druleRole
							.getRoleId()))) {
							guaranteeAnnualInterest += druleRole.getRule();
						}
					}
				}
			}
			if (repayRolelist != null && repayRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : repayRolelist) {
					double bg = CommonUtil.mulDI(druleRole.getRule(), 100);
					payAnnualInterest = CommonUtil.addDD(payAnnualInterest, bg);
					if (DivisionPhaseEnum.REPAY_PHASE.code().equals(druleRole.getPhase())) {
						if (YrdConstants.RoleId.GUARANTEE.equals(String.valueOf(druleRole
							.getRoleId()))) {
							guaranteeAnnualInterest += druleRole.getRule();
						}
					}
				}
			}
			double totalAnnualInterest = investAnnualInterest + payAnnualInterest;
			session.setAttribute("token", token);
			model.addAttribute("totalAnnualInterest", totalAnnualInterest);
			model.addAttribute("guaranteeAnnualInterest", guaranteeAnnualInterest);
		} catch (Exception e) {
			logger.error("担保函详情,查询异常{}", e.getMessage(), e);
		}
		return GUARANTEE_MANAGE_PATH + "guarantee_details_noTrade.vm";
	}
	
	@ResponseBody
	@RequestMapping("guaranteeDeployAuditing")
	public Object guaranteeDeployAuditing(HttpSession session, long demandId, String token,
											Model model) {
		JSONObject jsonobj = new JSONObject();
		String getToken = (String) session.getAttribute("token");
		try {
			if (token.equals(getToken) && demandId > 0) {
				session.removeAttribute("token");
				logger.info("demandId为" + demandId + "发布前审核...");
				LoanAuthRecord record = new LoanAuthRecord();
				record.setBaseId(BusinessNumberUtil.gainNumber());
				record.setAuthUserId(getGuaranteeUserId());
				record.setLoanDemandId(demandId);
				record.setNote("担保公司发布借款审核");
				record.setAuthType("DEPLOYLVTWO");
				record.setAuthTime(new Date());
				loanDemandManager.addLoanAuthRecord(record);
				jsonobj.put("code", 1);
				jsonobj.put("message", "发布成功！");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "重复提交！");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "发布失败！");
			logger.error("guaranteeDeployAuditing{}", e.getMessage(), e);
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("guaranteeMakeLoanAuditing")
	public Object guaranteeMakeLoanAuditing(HttpSession session, long demandId, long tradeId,
											Model model) throws Exception {
		JSONObject jsonobj = new JSONObject();
		try {
			Trade trade = tradeService.getByTradeId(tradeId);
			if (trade != null && trade.getEffectiveDateTime() == null) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "亲，请先生成合同！");
				return jsonobj;
			}
			divisionService.addLoanAuthRecord(SessionLocalManager.getSessionLocal().getUserId(),
				tradeId, "MAKELOANLVONE");
			jsonobj.put("code", 1);
			jsonobj.put("message", "审核成功！");
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "审核失败！");
			logger.error("guaranteeMakeLoanAuditing{}", e.getMessage(), e);
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("guaranteeMakeContract")
	public Object guaranteeMakeContract(HttpSession session, long demandId, long tradeId,
										Model model) throws Exception {
		JSONObject jsonobj = new JSONObject();
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(demandId);
		try {
			//增加担保函流水号
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("userId", getGuaranteeUserId()); //当前操作员对应的担保机构的userId  operator_info.parent_id  
			conditions.put("roleId", 8L);
			conditions.put("tradeId", tradeId);
			List<TradeQueryDetail> details = loanDemandManager
				.getTradeDetailByConditions(conditions);
			if (details != null && details.size() > 0) { //该融资需求必须要有担保机构参与分润，才能生成担保合同（ trade_detail里有role_id =8 的user_id）
				TradeFlowCode tradeFlow = tradeService.queryInvestFlowCodesByTradeDetailId(details
					.get(0).getId());
				if (tradeFlow == null) {
					tradeService.addGuaranteeTradeFlowCode(loan.getGuaranteeId(), tradeId);
				} else {
					tradeService.updateGuaranteeTradeFlowCode(loan.getGuaranteeId(), tradeId,
						tradeFlow);
				}
			}
			jsonobj.put("code", 1);
			jsonobj.put("message", "成功创建合同！");
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建合同失败！");
			logger.error("guaranteeMakeContract{}", e.getMessage(), e);
		}
		return jsonobj;
	}
	
	/**
	 * 担保机构审核完成之后放款进入还款阶段
	 * @param tradeId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("guaranteeAuditingFinish")
	public Object guaranteeAuditingFinish(long tradeId, String token, HttpSession session) {
		logger.info("担保机构审核完成开始放款转账，入参：[tradeId={}],", tradeId);
		Map<String, Object> map = new HashMap<String, Object>();
		Trade trade = tradeService.getByTradeId(tradeId);
		String getToken = (String) session.getAttribute("token");
		try {
			if (token.equals(getToken) && trade != null) {
				session.removeAttribute("token");
				logger.info("tradeId为" + trade.getId() + "计算转账金额，转账中...");
				divisionService.transfer(trade, SessionLocalManager.getSessionLocal().getUserId());
				LoanDemandDO loan = loanDemandManager
					.queryLoanDemandByDemandId(trade.getDemandId());
				tradeService.addRepayPlan(tradeId);
				
				map.put("code", 1);
				map.put("message", "放款成功");
			} else {
				map.put("code", 0);
				map.put("message", "请勿重复提交");
			}
		} catch (Exception e) {
			logger.error("放款失败" + e.getMessage(), e);
			map.put("code", 0);
			map.put("message", "放款失败");
		}
		return map;
	}
	
}
