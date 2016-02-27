package com.icebreak.p2p.front.controller.trade.query;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.CommonBindingInitializer;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.front.controller.trade.download.DataMap;
import com.icebreak.p2p.front.controller.trade.download.InvestReceiptPDFCreator;
import com.icebreak.p2p.integration.openapi.RepayService;
import com.icebreak.p2p.integration.openapi.info.QueryWithdrawInfo;
import com.icebreak.p2p.integration.openapi.order.RepayOrder;
import com.icebreak.p2p.integration.openapi.order.WithdrawQueryOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawQueryResult;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.web.util.RateProcessorUtil;
import com.icebreak.p2p.ws.enums.*;
import com.icebreak.p2p.ws.info.CommonAttachmentInfo;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.APITool;
import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("tradeQuery")
public class TradeQueryController extends UserAccountInfoBaseController {
	private final String vm_path = "/front/trade/query/";

	@Autowired
	protected InvestReceiptPDFCreator receiptPDFCreator;
	@Autowired
	IOperatorInfoService operatorInfoService;

	/**
	 * 工具类处理金额 /时间
	 * */
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Money.class, "minAmountIn",
				new CommonBindingInitializer());
		binder.registerCustomEditor(Money.class, "maxAmountIn",
				new CommonBindingInitializer());
		binder.registerCustomEditor(Date.class, "startTime",
				new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "endTime",
				new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 查询借款管理
	 * */
	@RequestMapping("borrowingRecord")
	public String borrowingRecord(QueryTradeOrder queryTradeOrder,
			PageParam pageParam, Model model, HttpSession session) {
		try {

			SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
			if (sessionLocal != null && sessionLocal.getAccountId() != null) {
				this.initAccountInfo(model);
			}
			queryTradeOrder.setUserId(SessionLocalManager.getSessionLocal()
					.getUserId());
			queryTradeOrder.setRoleId(13L);

			Page<Trade> page = tradeService.pageQueryTrade(queryTradeOrder,
					pageParam);
			long allLoanedAmount = tradeService
					.getAllLoanedAmountByOrder(queryTradeOrder);
			model.addAttribute("allLoanedAmount",
					MoneyUtil.getFormatAmount(allLoanedAmount));
			model.addAttribute("queryTradeOrder", queryTradeOrder);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("pageQueryTrade异常{}", e.getMessage(), e);
		}
		return vm_path + "borrowingRecord.vm";
	}

	/**
	 * 统计合计金额
	 *
	 * @param result
	 * @return
	 */
	private long countLoanTotalAmount(List<Trade> result) {
		long totalAmount = 0;
		if (result != null && result.size() > 0) {
			for (Trade trade : result) {
				totalAmount += trade.getLoanedAmount();
			}
		}
		return totalAmount;
	}

	/**
	 * 统计合计金额
	 *
	 * @param result
	 * @return
	 */
	private long countInvestTotalAmount(List<UserInvestEntry> result) {
		long totalAmount = 0;
		if (result != null && result.size() > 0) {
			for (UserInvestEntry trade : result) {
				totalAmount += trade.getAmount();
			}
		}
		return totalAmount;
	}

	/**
	 * 查借款详情/和还款
	 * */
	@RequestMapping("borrowingDetails")
	public String borrowingDetails(long tradeId, String operate,
			HttpSession session, Model model) {
		model.addAttribute("pdfhost", "");
		try {

			SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
			if (sessionLocal != null && sessionLocal.getAccountId() != null) {
				this.initAccountInfo(model);
			}

			String token = UUID.randomUUID().toString();
			Trade trade = tradeService.getByTradeId(tradeId);

			//获取项目图片
			CommonAttachmentInfo proImageInfo = commonAttachmentService.queryProImage(String.valueOf(trade.getDemandId()));
			String proImageURL = (proImageInfo != null ? proImageInfo.getRequestPath() : AppConstantsUtil.getProjectDefaultThumbnailUrl());
			model.addAttribute("proImageURL", proImageURL);

			LoanDemandDO loanDemand = loanDemandManager
					.queryLoanDemandByDemandId(trade.getDemandId());

			if (DivisionWayEnum.MONTH_WAY.code().equals(
					loanDemand.getRepayDivisionWay())) { // 按月还款的项目到“还款管理”处还款
				return "redirect:/repay/list";
			}

			Map<String, Object> conditions5 = new HashMap<String, Object>();
			conditions5.put("roleId", 12L);
			conditions5.put("tradeId", trade.getId());
			conditions5.put("transferPhase",
					DivisionPhaseEnum.ORIGINAL_PHASE.code());
			List<TradeQueryDetail> details5 = loanDemandManager
					.getTradeDetailByConditions(conditions5);
			if (details5 != null && details5.size() > 0) {
				List<UserInvestEntry> userInvestEntrys = new ArrayList<UserInvestEntry>();
				for (TradeDetail detail : details5) {
					List<UserInvestEntry> userInvests = tradeService
							.getEntriesByTradeIdAndDetailId(trade.getId(),
									detail.getId());
					if (userInvests != null && userInvests.size() > 0) {
						userInvestEntrys.add(userInvests.get(0));
					}
				}
				model.addAttribute("investorTradeDetails", userInvestEntrys);
			}

			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("loanDemandId", trade.getDemandId());
			conditions.put("authType", "MAKELOANLVONE");
			long count1 = loanDemandManager
					.countLoanAuthRecordByCondition(conditions);
			String auditUserBaseId = "";
			if (count1 > 0) {
				List<LoanAuthRecord> reccord1 = loanDemandManager
						.getLoanAuthRecordByCondition(conditions);
				List<UserBaseInfoDO> users = userBaseInfoManager.queryByType(
						"JG", String.valueOf(reccord1.get(0).getAuthUserId()));
				auditUserBaseId = users.get(0).getUserBaseId();
				model.addAttribute("auditUser1", users.get(0).getUserName());
				// 备注
				Map<String, Object> opConditions = new HashMap<String, Object>();
				opConditions.put("userBaseId", users.get(0).getUserBaseId());
				opConditions.put("limitStart", 0);
				opConditions.put("pageSize", 100);
				List<OperatorInfoDO> operatorInfos = operatorInfoService
						.queryOperatorsByProperties(opConditions);
				model.addAttribute("remark1", operatorInfos.get(0).getRemark());
				// END
				model.addAttribute("auditrecord1", reccord1.get(0));
				model.addAttribute("audit", "yes");
			}

			conditions = new HashMap<String, Object>();
			conditions.put("loanDemandId", trade.getDemandId());
			conditions.put("authType", "MAKELOANLVTWO");
			count1 = loanDemandManager
					.countLoanAuthRecordByCondition(conditions);

			if (count1 > 0) {
				List<LoanAuthRecord> reccord1 = loanDemandManager
						.getLoanAuthRecordByCondition(conditions);
				List<UserBaseInfoDO> users = userBaseInfoManager.queryByType(
						"JG", String.valueOf(reccord1.get(0).getAuthUserId()));
				model.addAttribute("auditUser2", users.get(0).getUserName());
				// 备注
				Map<String, Object> opConditions = new HashMap<String, Object>();
				opConditions.put("userBaseId", users.get(0).getUserBaseId());
				opConditions.put("limitStart", 0);
				opConditions.put("pageSize", 100);
				List<OperatorInfoDO> operatorInfos = operatorInfoService
						.queryOperatorsByProperties(opConditions);
				model.addAttribute("remark2", operatorInfos.get(0).getRemark());
				// END
				model.addAttribute("auditrecord2", reccord1.get(0));

			}

			List<TradeDetail> loanedTradeDetails = tradeService
					.getByTradeIdAndRoles(trade.getId(),
							SysUserRoleEnum.LOANER.getRoleCode());
			QueryTradeOrder queryTradeOrder = new QueryTradeOrder();
			queryTradeOrder.setTradeCode(trade.getCode());
			queryTradeOrder.setUserId(SessionLocalManager.getSessionLocal()
					.getUserId());
			queryTradeOrder.setRoleId(13L);
			PageParam pageParam = new PageParam();
			pageParam.setPageSize(10000);
				Page<Trade> loanedpage = tradeService.pageQueryTrade(
					queryTradeOrder, pageParam);
			// 获取资金信息
			YzzUserAccountQueryResponse account = apiTool
					.queryUserAccount(SessionLocalManager.getSessionLocal()
							.getAccountId());

			Money availableBalance = null;
			if (account.success()) {

				availableBalance = new Money(account.getAvailableBalance());
			} else {
				logger.error("查询资金信息异常，accounId"
						+ SessionLocalManager.getSessionLocal().getAccountId());
				throw new Exception("查询资金信息异常，accounId-->"
						+ SessionLocalManager.getSessionLocal().getAccountId());
			}
			if (divisionService.isFullScale(trade)) {
				logger.info("tradeId为" + trade.getId() + "的交易达到满标");
				model.addAttribute("fitRequiredStatus", "0");
			}
			List<TradeDetailDTO> loanerTradeDetails = new ArrayList<TradeDetailDTO>(
					loanedTradeDetails.size());
			if (loanedTradeDetails != null) {
				for (int i = 0; i < loanedTradeDetails.size(); i++) {
					TradeDetailDTO tradeDetailDTO = new TradeDetailDTO();
					tradeDetailDTO.setTradeDetail(loanedTradeDetails.get(i));
					for (Trade curTrade : loanedpage.getResult()) {
						if (tradeDetailDTO.getTradeDetail().getTradeId() == curTrade
								.getId()) {
							tradeDetailDTO.setFinishDate(curTrade
									.getEffectiveDateTime());
							tradeDetailDTO.setLoanedAmount(curTrade
									.getLoanedAmount());
						}
					}
					loanerTradeDetails.add(tradeDetailDTO);
				}
			}
			conditions = new HashMap<String, Object>();
			conditions.put("userId", SessionLocalManager.getSessionLocal()
					.getUserId());
			conditions.put("tradeId", tradeId);
			List<TradeQueryDetail> details = loanDemandManager
					.getTradeDetailByConditions(conditions);// 获取交易详情
			long tradeDetailId = 0;
			if (details != null && details.size() > 0) {
				TradeDetail detail = details.get(0);
				tradeDetailId = detail.getId();
			}
			TradeFlowCode tradeFlowCode = tradeService
					.queryFlowCodeByDetailId(tradeDetailId);// 获取审核记录
			// 融资流水号
			String flowCode = "";
			if (tradeFlowCode != null) {
				flowCode = tradeFlowCode.getTradeFlowCode();
				model.addAttribute("flowCode", flowCode);
			} else {
				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("roleId", 12L);
				cond.put("tradeId", trade.getId());
				cond.put("transferPhase",
						DivisionPhaseEnum.ORIGINAL_PHASE.code());
				List<TradeQueryDetail> de = loanDemandManager
						.getTradeDetailByConditions(cond);
				if (de != null && de.size() > 0) {
					for (TradeDetail detail : de) {
						List<UserInvestEntry> userInvests = tradeService
								.getEntriesByTradeIdAndDetailId(trade.getId(),
										detail.getId());
						if (userInvests != null && userInvests.size() > 0) {
							String str = userInvests.get(0).getInvestflowCode();
							model.addAttribute("flowCode",
									str.subSequence(0, str.length() - 4) + "R");
						}
					}
				}
			}

			Map<String, Object> con = new HashMap<String, Object>();
			con.put("loanDemandId", trade.getDemandId());
			con.put("authType", "MAKELOANLVTWO");
			long count = loanDemandManager.countLoanAuthRecordByCondition(con);
			if (count > 0) {
				model.addAttribute("audit", "yes");
			}
			// 还款阶段利率
			LoanDemandDO loan = loanDemandManager
					.queryLoanDemandByDemandId(trade.getDemandId());
			long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
			DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
					.getByBaseId(divisionTemplateLoanBaseId);
			List<DivsionRuleRole> investRolelist = divisionService
					.getRuleRole(String.valueOf(divisionTemplateLoan
							.getInvestTemplateId()));
			List<DivsionRuleRole> repayRolelist = divisionService
					.getRuleRole(String.valueOf(divisionTemplateLoan
							.getRepayTemplateId()));
			double investAnnualInterest = 0;
			double payAnnualInterest = 0;
			double investInterest = 0;
			double payInterest = 0;
			double repayCutAmount = 0;
			if (investRolelist != null && investRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : investRolelist) {
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(
							druleRole.getPhase())) {
						investAnnualInterest += druleRole.getRule();
						BigDecimal bg = new BigDecimal(
								RateProcessorUtil.getDaysRuleRate(
										druleRole.getRule(), trade));
						investInterest += bg.setScale(10,
								BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}
			if (repayRolelist != null && repayRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : repayRolelist) {
					if (DivisionPhaseEnum.REPAY_PHASE.code().equals(
							druleRole.getPhase())) {
						payAnnualInterest += druleRole.getRule();
						BigDecimal bg = new BigDecimal(
								RateProcessorUtil.getDaysRuleRate(
										druleRole.getRule(), trade));
						double interest = bg.setScale(10,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						double amount = trade.getLoanedAmount() * interest;
						amount = Math.round(amount);
						repayCutAmount += amount;
						payInterest += interest;
					}
				}
			}
			double totalAnnualInterest = (investAnnualInterest + payAnnualInterest) * 100;
			totalAnnualInterest = new BigDecimal(totalAnnualInterest).setScale(
					2, BigDecimal.ROUND_HALF_UP).doubleValue();
			repayCutAmount = Math.round(repayCutAmount);
			long repayAmount = trade.getLoanedAmount() + (long) repayCutAmount;
			model.addAttribute("repayCutAmount", (long) repayCutAmount);
			model.addAttribute("repayAmount", repayAmount);
			model.addAttribute("loanerTradeDetails", loanerTradeDetails);
			model.addAttribute("trade", trade);
			model.addAttribute("loanDemand", loanDemand);
			model.addAttribute("operate", operate);
			session.setAttribute("token", token);
			model.addAttribute("totalAnnualInterest", totalAnnualInterest);

			if (availableBalance.getCent() < trade.getLoanedAmount()) {
				model.addAttribute("poorAmount", (trade.getLoanedAmount())
						- (availableBalance.getCent()));
			} else {
				model.addAttribute("poorAmount", -1);
			}
		} catch (Exception e) {
			logger.error("pageQueryTrade异常{}", e.getMessage(), e);
		}
		return vm_path + "query_borrowingDetails.vm";
	}

	@ResponseBody
	@RequestMapping("manualReimbursement")
	public Object manualReimbursement(long tradeId, long demandId,
			String smsCode, String mobile, String business, String token,
			HttpSession session) {

		logger.info("手动还款，入参：[demandId={}],", demandId);
		String getToken = (String) session.getAttribute("token");
		Map<String, Object> map = new HashMap<String, Object>();

		SmsCodeResult smsCodeResult = this.smsManagerService.verifySmsCode(
				mobile, SmsBizType.getByCode(business), smsCode, true);

		if (!smsCodeResult.isSuccess()) {
			map.put("code", 0);
			map.put("message", "短信验证码错误");
			return map;
		}
		RepayOrder order = null;
		try {
			if (token.equals(getToken) && demandId > 0) {
				session.removeAttribute("token");

				SessionLocal sl = SessionLocalManager.getSessionLocal();

				order = tradeService.gotoYJFRepay(sl.getUserId(),
						demandId, 0);
				if (order == null) {
					map.put("code", 0);
					map.put("message", "还款失败");
					return map;
				}
			} else {
				map.put("code", 0);
				map.put("message", "还款失败重复提交");
				return map;
			}
		} catch (Exception e) {
			logger.error("还款失败异常{}", e.getMessage(), e);
			map.put("code", 0);
			map.put("message", "还款失败");
			return map;
		}

		Map<String, String> params = APITool.initBaseParams(RepayService.tradePoolPay);

		params.put("tradeNo", order.getTradeNo());
		params.put("refTradeNo", order.getRefTradeNo());
		params.put("payerUserId", order.getPayerUserId());
		params.put("subOrders", JSONObject.toJSONString(order.getSubOrders()));
		//当除投资人之外的分润大于0时，才传递shardOrder
		if(order.getShardOrder() != null && Double.valueOf(order.getShardOrder().getTransferAmount()) > 0) {
			params.put("shardOrder", "[" + JSONObject.toJSONString(order.getShardOrder()) + "]");
		}
		params.put("returnUrl", Constants.repayReturnUrl);
		params.put("orderNo", order.getOrderNo());

		String url = null;
		try {
			url = apiTool.makeRequestUrl(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		map.put("code", 1);
		map.put("message", url);
		return map;
	}

	/**
	 * 查询投资记录
	 */
	@RequestMapping("entries/{size}/{page}")
	public String entries(@PathVariable int size, @PathVariable int page,
			Integer status, String startDate, String endDate, String code,
			String loanRealName, String loanUserName, Long startAmount,
			Long endAmount, String name, Model model, HttpSession session) {

		this.initAccountInfo(model);
		Pagination<UserInvestEntry> pagination = tradeService
				.getUserInvestEntries(
						// 分页查询
						SessionLocalManager.getSessionLocal().getUserId(),
						(page - 1) * size, size, status,
						(startDate == null || startDate.length() < 1) ? null
								: DateUtil.parse(startDate + " 00:00:00"),
						(endDate == null || endDate.length() < 1) ? null
								: DateUtil.parse(endDate + " 23:59:59"), code,
						loanRealName, loanUserName, startAmount, endAmount,
						name);

		model.addAttribute("page", pagination);
		long totalAmount = countInvestTotalAmount(pagination.getResult());
		model.addAttribute("totalAmount", totalAmount);
		long allAmount = tradeService.getAllAmount(SessionLocalManager
				.getSessionLocal().getUserId(), null, null, null, null, null,
				null, null, null, null);
		model.addAttribute("allAmount", MoneyUtil.getFormatAmount(allAmount));
		// session.setAttribute("UserInvestEntry", all.getResult());
		return vm_path + "invest_entries.vm";
	}

	@RequestMapping("invest/{size}/{page}")
	public String invest(@PathVariable int size, @PathVariable int page,
			Integer status, String startDate, String endDate, String code,
			String loanRealName, String loanUserName, Long startAmount,
			Long endAmount, String name, Model model, HttpSession session) {

		this.initAccountInfo(model);
		Pagination<UserInvestEntry> pagination = tradeService
				.getUserInvestEntries(
						// 分页查询
						SessionLocalManager.getSessionLocal().getUserId(),
						(page - 1) * size, size, status,
						(startDate == null || startDate.length() < 1) ? null
								: DateUtil.parse(startDate + " 00:00:00"),
						(endDate == null || endDate.length() < 1) ? null
								: DateUtil.parse(endDate + " 23:59:59"), code,
						loanRealName, loanUserName, startAmount, endAmount,
						name);

		model.addAttribute("page", pagination);
		long totalAmount = countInvestTotalAmount(pagination.getResult());
		model.addAttribute("totalAmount", totalAmount);
		long allAmount = tradeService.getAllAmount(SessionLocalManager
				.getSessionLocal().getUserId(), null, null, null, null, null,
				null, null, null, null);
		model.addAttribute("allAmount", MoneyUtil.getFormatAmount(allAmount));
		// session.setAttribute("UserInvestEntry", all.getResult());
		return vm_path + "investProject.vm";
	}

	/**
	 * 查看投资详情
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryInvestDetails/{tradeId}")
	public String queryInvestDetails(@PathVariable long tradeId, long detailId, Model model)
																							throws Exception {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		boolean investsDownLoadable = false;
		long countInvestTimes = 0;

		model.addAttribute("pdfhost", "");
		Trade trade = tradeService.getByTradeId(tradeId);
		model.addAttribute("investableAmount", trade.getAmount() - trade.getLoanedAmount());

		//获取项目图片
		CommonAttachmentInfo proImageInfo = commonAttachmentService.queryProImage(String.valueOf(trade.getDemandId()));
		String proImageURL = (proImageInfo != null ? proImageInfo.getRequestPath() : AppConstantsUtil.getProjectDefaultThumbnailUrl());
		model.addAttribute("proImageURL", proImageURL);

		model.addAttribute("loanDemand",
			loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId()));
		List<UserInvestEntry> userInvests = tradeService.getEntriesByTradeIdAndDetailId(tradeId,
				detailId);
		long investorId = 0;
		if (userInvests != null && userInvests.size() > 0) {
			model.addAttribute("tradeItem", userInvests.get(0));
			investorId = userInvests.get(0).getInvestorId();
		}
		
		long divisionAmount = 0;
		long profitAmount = 0;
		List<TradeDetail> details = tradeService.getInvestProfitTrade(detailId);
		if (details != null && details.size() > 0) {
			for (TradeDetail detail : details) {
				if (detail.getProfitType() > 0) {
					profitAmount += detail.getAmount();
					model.addAttribute("showExtraProfit", "yes");
					model.addAttribute("extInterest", detail.getProfitRate());
				} else {
					divisionAmount += detail.getAmount();
				}
			}
		}
		model.addAttribute("profitAmount", profitAmount);
		model.addAttribute("divisionAmount", divisionAmount);
		String investFlowCode = null;
		TradeFlowCode tradeFlow = tradeService.queryInvestFlowCodesByTradeDetailId(detailId);
		if (tradeFlow != null) {
			investFlowCode = tradeFlow.getTradeFlowCode();
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanDemandId", trade.getDemandId());
		condition.put("authType", "MAKELOANLVTWO");
		long count = loanDemandManager.countLoanAuthRecordByCondition(condition);
		if (count > 0) {
			model.addAttribute("audit", "yes");
		}
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("roleId", 8L);
		cond.put("tradeId", tradeId);
		List<TradeQueryDetail> det = loanDemandManager.getTradeDetailByConditions(cond);
		if (det != null && det.size() > 0) {
			TradeFlowCode tf = tradeService.queryInvestFlowCodesByTradeDetailId(det.get(0).getId());
			if (tf != null) {
				model.addAttribute("contractNo", tf.getTradeFlowCode());
			}
		}
		LoanDemandDO demand = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
		long divisionTemplateId = demand.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
			.getByBaseId(divisionTemplateId);
		List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getInvestTemplateId()));
		List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getRepayTemplateId()));
		// 只计算经纪人
		double totalAnnualInterest = 0;
		investRolelist.addAll(repayRolelist);
		if (investRolelist != null && investRolelist.size() > 0) {
			for (DivsionRuleRole druleRole : investRolelist) {
				if (DivisionPhaseEnum.INVESET_PHASE.code().equals(druleRole.getPhase())) {
					if ("11".equals(String.valueOf(druleRole.getRoleId()))) {
						totalAnnualInterest += druleRole.getRule();
					}
				}
			}
		}
		
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
			// 备注
			Map<String, Object> opConditions = new HashMap<String, Object>();
			opConditions.put("userBaseId", users.get(0).getUserBaseId());
			opConditions.put("limitStart", 0);
			opConditions.put("pageSize", 100);
			List<OperatorInfoDO> operatorInfos = operatorInfoService
				.queryOperatorsByProperties(opConditions);
			model.addAttribute("remark1", operatorInfos.get(0).getRemark());
			// END
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
			// 备注
			Map<String, Object> opConditions = new HashMap<String, Object>();
			opConditions.put("userBaseId", users.get(0).getUserBaseId());
			opConditions.put("limitStart", 0);
			opConditions.put("pageSize", 100);
			List<OperatorInfoDO> operatorInfos = operatorInfoService
				.queryOperatorsByProperties(opConditions);
			model.addAttribute("remark2", operatorInfos.get(0).getRemark());
			// END
			model.addAttribute("auditrecord2", reccord1.get(0));
			
		}
		
		// 是否投资过某融资需求，决定是否允许下载合同，担保函
		if (sessionLocal != null) {
			this.initAccountInfo(model);
			countInvestTimes = tradeService.countInvestTimes(SessionLocalManager.getSessionLocal()
				.getUserBaseId(), trade.getDemandId(), null);
			
			// 是否可下载某融资需求的投资人列表
			investsDownLoadable = institutionsInfoManager.isFromSameOrgan(SessionLocalManager
				.getSessionLocal().getUserBaseId(), auditUserBaseId);
		}
		
		model.addAttribute("downLoadableInvests", investsDownLoadable);
		model.addAttribute("countInvestTimes", countInvestTimes);
		model.addAttribute("detailId", detailId);
		model.addAttribute("investFlowCode", investFlowCode);
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
		model.addAttribute("trade", trade);
		return vm_path + "query_invest_detail.vm";
	}

	/**
	 * 查询代扣充值记录
	 */
	@RequestMapping("deductTopUp")
	public String deductRecord(QueryTradeOrder queryTradeOrder, PageParam pageParam, String type,
								HttpSession session, Model model)
			throws Exception {
		String path = vm_path + "deductTopUp.vm";
		if (type == null || "".equals(type)) {
			String accountId = SessionLocalManager.getSessionLocal().getAccountId();
			queryTradeOrder.setAccountId(accountId);
		} else {
			path = "/backstage/trade/deductRecord.vm";
		}
		queryTradeOrder.setPayType("DEDUCT");
		Page<RechargeFlow> page = null;
		try {
			page = tradeService.getFlow(queryTradeOrder, pageParam);
		} catch (Exception e) {
			logger.error("充值记录查询失败！", e);
			model.addAttribute("faild", "充值记录查询失败！");
			return vm_path + "deductTopUp.vm";
		}
		model.addAttribute("page", page);
		return path;
	}

	/**
	 * 查询提现记录
	 */
	@RequestMapping("withdrawals")
	public String withdrawals(WithdrawQueryOrder queryOrder,
			PageParam pageParam, HttpSession session, Model model)
			throws Exception {
		logger.info("查询提现记录,入参：{}", queryOrder);
		session.setAttribute("current", 2);
		queryOrder.setCurrPage(pageParam.getPageNo());
		queryOrder.setPageSize(pageParam.getPageSize());
		queryOrder.setBizId("EASY_LOAN");// 平台
		queryOrder.setUserId(SessionLocalManager.getSessionLocal()
				.getAccountId());
		WithdrawQueryResult queryResult = this.withdrawQueryService
				.queryWithdrawService(queryOrder);
		int start = PageParamUtil.startValue((int) queryResult.getSize(),
				pageParam.getPageSize(), pageParam.getPageNo());
		List<QueryWithdrawInfo> infos = queryResult.getData();
		if (pageParam.getPageNo() == 1) {
			DataMap.setOjbMap("withdraw", infos);
		}
		// session.setAttribute("withdraw", infos);
		Page<QueryWithdrawInfo> page = new Page<QueryWithdrawInfo>(start,
			(int) queryResult.getSize(), pageParam.getPageSize(), infos);
		model.addAttribute("page", page);
		session.setAttribute("queryOrder", queryOrder);
		// 求成功提现金额和
		Money sumMoney = new Money(0);

		queryOrder.setCurrPage(1);
		queryOrder.setPageSize(999999);
		WithdrawQueryResult result = withdrawQueryService.queryWithdrawService(queryOrder);
		infos = result.getData();
		if (infos != null) {
			for (QueryWithdrawInfo info : infos) {
				if ("成功".equals(info.getStatus())) {
					sumMoney.addTo(new Money(info.getAmountIn()));
				}
			}
		}

		model.addAttribute("sumMoney", sumMoney);
		return vm_path + "withdrawals.vm";
	}


	/**
	 * 满标之后点击进入担保机构审核阶段
	 * 
	 * @param tradeId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("confirmFinishCollect")
	public Object manualReimbursement(long tradeId, String token,
			HttpSession session) {
		logger.info("确认融资完成进入担保机构审核中，入参：[tradeId={}],", tradeId);
		Map<String, Object> map = new HashMap<String, Object>();
		Trade trade = tradeService.getByTradeId(tradeId);
		String getToken = (String) session.getAttribute("token");
		try {
			if (token.equals(getToken) && trade != null) {
				session.removeAttribute("token");
				logger.info("tradeId为" + trade.getId() + "确认融资完成进入担保机构审核中...");
				tradeService.createRelativeTrades(trade);
				logger.info("tradeId为" + trade.getId() + "确认融资完成创建其他角色的子交易...");
				map.put("code", 1);
				map.put("message", "确认完成融资成功");
			} else {
				map.put("code", 0);
				map.put("message", "请勿重复提交");
			}
		} catch (Exception e) {
			map.put("code", 0);
			map.put("message", "服务处理失败,请重新尝试");
			logger.error("服务处理失败,请重新尝试{}", e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 查看投资详情凭证
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("investReceipt/{tradeId}")
	public String investReceipt(@PathVariable long tradeId, long detailId,
			Model model) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		model.addAttribute("loanItem", loanDemandManager
				.queryLoanDemandByDemandId(trade.getDemandId()));
		List<UserInvestEntry> userInvests = tradeService
				.getEntriesByTradeIdAndDetailId(tradeId, detailId);
		long totalAmount = 0;
		if (userInvests != null && userInvests.size() > 0) {
			model.addAttribute("tradeItem", userInvests.get(0));
			model.addAttribute("investor", userInvests.get(0)
					.getInvestorUserName());
			model.addAttribute("investorReal", userInvests.get(0)
					.getInvestorRealName());
			model.addAttribute("loannerReal", userInvests.get(0)
					.getLoanerRealName());
			model.addAttribute("loanner", userInvests.get(0)
					.getLoanerUserName());
			totalAmount = userInvests.get(0).getAmount();
		}

		long divisionAmount = 0;
		long profitAmount = 0;
		List<TradeDetail> details = tradeService.getInvestProfitTrade(detailId);
		if (details != null && details.size() > 0) {
			for (TradeDetail detail : details) {
				divisionAmount += detail.getAmount();
				if (detail.getProfitType() > 0) {
					profitAmount += detail.getAmount();
				}
			}
		}
		totalAmount += divisionAmount;
		model.addAttribute("divisionAmount", divisionAmount);
		model.addAttribute("totalAmount", totalAmount - profitAmount);
		String investFlowCode = null;
		TradeFlowCode tradeFlow = tradeService
				.queryInvestFlowCodesByTradeDetailId(detailId);
		if (tradeFlow != null) {
			investFlowCode = tradeFlow.getTradeFlowCode();
			model.addAttribute("investFlowCodeState", tradeFlow.getState());
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanDemandId", trade.getDemandId());
		condition.put("authType", "MAKELOANLVTWO");
		long count = loanDemandManager
				.countLoanAuthRecordByCondition(condition);
		if (count > 0) {
			model.addAttribute("audit", "yes");
		}
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.put("roleId", 8L);
		cond.put("tradeId", tradeId);
		List<TradeQueryDetail> det = loanDemandManager
				.getTradeDetailByConditions(cond);
		if (det != null && det.size() > 0) {
			TradeFlowCode tf = tradeService
					.queryInvestFlowCodesByTradeDetailId(det.get(0).getId());
			if (tf != null) {
				model.addAttribute("contractNo", tf.getTradeFlowCode());
			}
		}
		LoanDemandDO demand = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		long divisionTemplateId = demand.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
				.getByBaseId(divisionTemplateId);
		List<DivsionRuleRole> investRolelist = divisionService
				.getRuleRole(String.valueOf(divisionTemplateLoan
						.getInvestTemplateId()));
		List<DivsionRuleRole> repayRolelist = divisionService
				.getRuleRole(String.valueOf(divisionTemplateLoan
						.getRepayTemplateId()));
		// 只计算经纪人
		double totalAnnualInterest = 0;
		investRolelist.addAll(repayRolelist);
		if (investRolelist != null && investRolelist.size() > 0) {
			for (DivsionRuleRole druleRole : investRolelist) {
				if (DivisionPhaseEnum.INVESET_PHASE.code().equals(
						druleRole.getPhase())) {
					if (SysUserRoleEnum.BROKER.code().equals(
							String.valueOf(druleRole.getRoleId()))) {
						totalAnnualInterest += druleRole.getRule();
					}

				}
			}
		}
		model.addAttribute("detailId", detailId);
		model.addAttribute("investFlowCode", investFlowCode);
		model.addAttribute("trade", trade);
		return "front/trade/query/investReceipt.vm";
	}

	@RequestMapping("investReceiptDownLoad/{tradeId}")
	public void downLoadInvestReceipt(@PathVariable long tradeId,
			long detailId, String checkType, HttpServletResponse response,
			HttpServletRequest request, Model model) throws Exception {

		String servletPath = request.getServletContext().getRealPath("/");

//		byte[] data = receiptPDFCreator.creatFileData4Receipt(tradeId,
//				detailId, servletPath);
//
//		// byte[] data = creatFileData4PDFVoucher(tradeId, detailId,
//		// servletPath);
//
//		downloadPDF(checkType, response, data);
	}

	private void downloadPDF(String checkType, HttpServletResponse response,
			byte[] data) throws IOException {
		OutputStream servletOS;
		String extName = "投资凭证.pdf";
		if ("downLoad".equals(checkType)) {
			response.setContentType("application/pdf");
			try {
				response.addHeader(
						"Content-Disposition",
						"attachment; filename="
								+ new String(extName.getBytes("GB2312"),
										"ISO8859-1"));
			} catch (UnsupportedEncodingException e1) {

				logger.error("UnsupportedEncodingException ", e1);
			}
		}

		servletOS = response.getOutputStream();
		servletOS.write(data);
		servletOS.flush();
		servletOS.close();
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
			if (trade.getLoanedAmount() >= scale
					|| trade.getLoanedAmount() >= trade.getAmount()) {
				return true;
			}
		} else if (method.equalsIgnoreCase(TradeFullConditionEnum.PERCENTAGE
				.code())) {
			double percentage = ((double) trade.getLoanedAmount() / (double) trade
					.getAmount());
			if (percentage >= Double
					.parseDouble(trade.getSaturationCondition())) {
				return true;
			}
		} else if (method.equalsIgnoreCase(TradeFullConditionEnum.DATE.code())) {
			if (System.currentTimeMillis() >= DateUtil.parse(
					trade.getSaturationCondition()).getTime()) {
				return true;
			}
		}
		return false;
	}

}
