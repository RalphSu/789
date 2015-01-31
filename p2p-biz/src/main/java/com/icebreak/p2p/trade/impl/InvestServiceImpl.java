package com.icebreak.p2p.trade.impl;

import com.icebreak.p2p.base.BaseBizService;
import com.icebreak.p2p.dal.daointerface.RepayPlanDAO;
import com.icebreak.p2p.dal.dataobject.RepayPlanDO;
import com.icebreak.p2p.daointerface.DivisonRuleRoleDao;
import com.icebreak.p2p.daointerface.TradeDao;
import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.daointerface.UserGoldExperienceDao;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.division.DivisionTemplateYrdService;
import com.icebreak.p2p.integration.openapi.result.InvestReturnRequest;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.trade.InvestService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import com.icebreak.p2p.util.*;
import com.icebreak.p2p.ws.enums.*;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryRequest;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.message.yzz.InvestApplyNotify;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InvestServiceImpl extends BaseBizService implements InvestService {
	
	@Autowired
	protected DivisionService divisionService;
	@Autowired
	DivisionTemplateYrdService divisionTemplateLoanService;

	@Autowired
	private RepayPlanDAO repayPlanDAO;
	@Autowired
	UserGoldExperienceDao userGoldExperienceDao;
	@Autowired
	private TradeDao tradeDao;
	@Autowired
	private TradeDetailDao tradeDetailDao;
	@Autowired
	private LoanDemandManager loanDemandManager;
	@Autowired
	private UserRelationManager userRelationManager;
	@Autowired
	private UserBaseInfoManager userBaseInfoManager;
	@Autowired
	private DivisonRuleRoleDao divisonRuleRoleDao;
	private String jjrRoleId = SysUserRoleEnum.BROKER.code();
	private String yxjgRoleId = SysUserRoleEnum.MARKETING.code();
	private String investRoleId = SysUserRoleEnum.INVESTOR.code();
	
	//按天计算利率
	private static double getDaysRuleRate(double rule, Trade trade) {
		String timeLimitUnit = trade.getTimeLimitUnit();
		double timeLimit = trade.getTimeLimit();
		double days = 0;
		if (LoanLimitUnitEnum.LOAN_BY_DAY.code().equals(timeLimitUnit)) {
			days = timeLimit;
		} else if (LoanLimitUnitEnum.LOAN_BY_YEAR.code().equals(timeLimitUnit)) {
			days = timeLimit * YrdConstants.TimeRelativeConstants.DAYSOFAYEAR;
		} else {
			days = Math.round(timeLimit * YrdConstants.TimeRelativeConstants.DAYSOFAYEAR / 12);
		}
		//计算当前日期与起息日期之间的差 实际计息日要用总天数扣除当日
		if(null != trade.getEffectiveDateTime()){
			Calendar endTime = Calendar.getInstance();
			Calendar beginTime = Calendar.getInstance();
			beginTime.setTime(trade.getEffectiveDateTime());
			int cutDays = (int) ((beginTime.getTimeInMillis() - endTime.getTimeInMillis())/1000/60/60/24);
			days = days - cutDays;
			days = (days < 0 ? 0 : days);
		}
		BigDecimal bg = new BigDecimal(rule / YrdConstants.TimeRelativeConstants.DAYSOFAYEAR * days);
		double daysRate = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		return daysRate;
	}
	
	public String getJjrRoleId() {
		return jjrRoleId;
	}
	
	public void setJjrRoleId(String jjrRoleId) {
		this.jjrRoleId = jjrRoleId;
	}
	
	public String getYxjgRoleId() {
		return yxjgRoleId;
	}
	
	public void setYxjgRoleId(String yxjgRoleId) {
		this.yxjgRoleId = yxjgRoleId;
	}
	
	public String getInvestRoleId() {
		return investRoleId;
	}
	
	public void setInvestRoleId(String investRoleId) {
		this.investRoleId = investRoleId;
	}
	
	@Override
	public List<DivsionRuleRole> getRuleRole(String name) {
		return this.divisonRuleRoleDao.getLstRulRole(name);
	}

	/**
	 * 投资 若达到满标状态就转帐
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void investProcessor(TradeDetail detail) throws Exception {

		//保存投资原始资金
		TradeDetail detailSaved = saveInvestOriginalTradeDetail(detail);
		//给各个参与角色分润
		addDetailDivision(detailSaved);

		tradeDao.modifyStatus(detailSaved.getTradeId(), YrdConstants.TradeStatus.TRADING);//这里修改状态是为了触发收费操作
		//投资流水号
		addTradeFlowCode(detailSaved);

		//如果本次投资成功后，投资已满，就通知借款人
		notifyLoanerIfFull(detailSaved);

	}

	public void createRelativeTrades(Trade trade) throws Exception {

		//当前时间
		Date currentDate = new Date();
		//计算结息日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, trade.getTimeLimit());
		trade.setEffectiveDateTime(currentDate);
		trade.setExpireDateTime(cal.getTime());
//		trade = tradeDao.getByTradeIdWithRowLock(trade.getId());
		// 修改状态
		tradeDao.modifyStatus(trade.getId(), YrdConstants.TradeStatus.REPAYING, currentDate, cal.getTime());
		// 重置通知消息业务状态
		tradeDao.updateIsNotifiedLoaner(trade.getId(), YrdConstants.MessageNotifyConstants.ISNOTIFIED_NO);
	}

	protected void setToUserDetail(UserBaseInfoDO toUser) {
		toUser.setRealName("运营001号");
		toUser.setMail(AppConstantsUtil.getCustomerServiceEmail());
		toUser.setMobile(AppConstantsUtil.getCustomerServiceMobile());
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void investNotify(InvestApplyNotify notify) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("orderNo", notify.getOrderNo());
		List<TradeQueryDetail> list = tradeDetailDao.getTradeDetailByConditions(conditions);
		TradeQueryDetail detail = null;
		if (ListUtil.isNotEmpty(list)) {
			detail = list.get(0);
			logger.info("校验投资交易流水成功：" + detail.getId());
			if (!(StringUtil.equalsIgnoreCase(TradeDetailStatusEnum.IT.getCode(),
				detail.getTradeDetailStatus()) || StringUtil.equalsIgnoreCase(
				TradeDetailStatusEnum.AS.getCode(), detail.getTradeDetailStatus()))) {
				logger.info("该交易的状态为：" + detail.getTradeDetailStatus() + ",可能出错了哦");
				return;
			}
			TradeDetailStatusEnum status = null;
			if (StringUtil.equalsIgnoreCase("SUCCESS", notify.getExecuteStatus())) {
				status = TradeDetailStatusEnum.PS;
			} else if (StringUtil.equalsIgnoreCase("FAIL", notify.getExecuteStatus())) {
				status = TradeDetailStatusEnum.PF;
				tradeDetailDao.updatetStatus(detail.getId(), status.getCode());
				return;
			}

			int userVirtualMoneyId = -1;
			try {
				userVirtualMoneyId = Integer.parseInt(detail.getNote());
			} catch (NumberFormatException e) {

			}
			if (userVirtualMoneyId > 0) {
				UserGoldExperienceDO virtualMoney = userGoldExperienceDao.getById(userVirtualMoneyId);
				if (virtualMoney != null) {
					logger.info("修改用户体验金记录状态为0");
					virtualMoney.setStatus("0");
					virtualMoney.setTradeDetailId(detail.getId());
					virtualMoney.setTradeId(detail.getTradeId());
					userGoldExperienceDao.update(virtualMoney);
				}
			}

			tradeDao.addLoanedAmount(detail.getTradeId(), detail.getAmount());
			// 保存投资原始资金
//			TradeDetail detailSaved = saveInvestOriginalTradeDetail(detail);
			tradeDetailDao.updatetStatus(detail.getId(), status.getCode());
			// 给各个参与角色分润
			addDetailDivision(detail);

			tradeDao.modifyStatus(detail.getTradeId(), YrdConstants.TradeStatus.TRADING);// 这里修改状态是为了触发收费操作
			// 投资流水号
//			addTradeFlowCode(detail);

			// 如果本次投资成功后，投资已满，就通知借款人
			notifyLoanerIfFull(detail);
		}
	}

	private void tradeFinish(Trade trade) throws Exception {
		logger.info("融资完成开始放款转账，入参：[tradeId={}],", trade);
		Map<String, Object> map = new HashMap<String, Object>();
//		Trade trade = tradeService.getByTradeId(tradeId);
		logger.info("tradeId为" + trade.getId() + "计算转账金额，转账中...");
		divisionService.transfer(trade, 0L);

		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
		//添加还款计划
		addRepayPlan(trade, loan);

	}

	private void addRepayPlan(Trade trade, LoanDemandDO loanDemand)
			throws Exception {
		List<RepayPlanDO> planList = repayPlanDAO.findByTradeId(trade.getId());
		if (planList.size() > 0) {
			repayPlanDAO.deleteByTradeId(trade.getId()); // 防止多次生成合同时，多次生成还款计划
		}

		Date nowDate = new Date();
		// 获取模板
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
				.getByBaseId(divisionTemplateLoanBaseId);
		List<DivsionRuleRole> repayRolelist = getRuleRole(String
				.valueOf(divisionTemplateLoan.getRepayTemplateId()));

		Map<String, Object> conditions2 = new HashMap<String, Object>();
		conditions2.put("roleId", SysUserRoleEnum.INVESTOR.getValue());
		conditions2.put("tradeId", trade.getId());
		conditions2.put("transferPhase",
				DivisionPhaseEnum.ORIGINAL_PHASE.code());
		List<TradeQueryDetail> originalDetails = loanDemandManager
				.getTradeDetailByConditions(conditions2);

		// 更新原始资金的还款日期
		if (originalDetails != null && originalDetails.size() > 0) {
			for (TradeDetail detail : originalDetails) {
				int repayPeriodNo = detail.getRepayPeriodNo();
				updateInvestDetailRepayDate(detail.getId(), trade.getExpireDateTime());
			}
		}

		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("roleId", SysUserRoleEnum.INVESTOR.getValue());
		conditions.put("tradeId", trade.getId());
		conditions.put("transferPhase", DivisionPhaseEnum.REPAY_PHASE.code());
		List<TradeQueryDetail> details = loanDemandManager
				.getTradeDetailByConditions(conditions);

		if (DivisionWayEnum.MONTH_WAY.getCode().equals(loanDemand.getRepayDivisionWay())) {

			// 更新“按月付息”每期付息时间
			if (details != null && details.size() > 0) {
				for (TradeDetail detail : details) {
					int repayPeriodNo = detail.getRepayPeriodNo();
					if (repayPeriodNo > 0) {
						updateInvestDetailRepayDate(detail.getId(), DateUtil.getDateByMonth(nowDate, repayPeriodNo));
					}
				}
			}

			double repayCutAmount = 0; // 所有投资人还款阶段各月利息总额
			double repayOtherAmount = 0; // 其他人还款阶段分润（利息）总额
			if (ListUtil.isNotEmpty(repayRolelist)) {
				for (DivsionRuleRole druleRole : repayRolelist) {
					if (druleRole.getRoleId() == SysUserRoleEnum.INVESTOR
							.getValue()) { // 所有投资人利息总额
						double temp = getDaysRuleRate(druleRole.getRule(),
								trade) * trade.getLoanedAmount();
						repayCutAmount = repayCutAmount + temp;
					} else {
						double temp = getDaysRuleRate(druleRole.getRule(),
								trade) * trade.getLoanedAmount();
						repayOtherAmount = repayOtherAmount + temp;
					}
				}
			}
			int month = trade.getMonthCount();

			double repayAmountPerMonth = new BigDecimal(repayCutAmount / month)
					.doubleValue();// 每个月需还给投资人的利息金额

			repayAmountPerMonth = Math.floor(repayAmountPerMonth);

			double repayAll = repayAmountPerMonth + repayOtherAmount
					+ trade.getLoanedAmount(); // 最后一个月还款总额

			if (repayCutAmount > 0) {
				for (int i = 0; i < month; i++) {
					boolean isLast = false;
					if (i == month - 1) {
						isLast = true;
					}
					storeRepayPlanDomain(trade, loanDemand, nowDate, repayAll,
							repayCutAmount, month, i + 1, isLast);
				}
			} else {
				boolean isLast = true;
				storeRepayPlanDomain(trade, loanDemand, nowDate, repayAll,
						repayCutAmount, month, 1, isLast);
			}

		} else {

			// 更新“到期归还本金及利息”，付息日期
			if (details != null && details.size() > 0) {
				for (TradeDetail detail : details) {
					updateInvestDetailRepayDate(detail.getId(), DateUtil.getDateByMonth(nowDate, trade.getMonthCount()));
				}
			}

			double repayCutAmount = 0; // 投资人还款阶段分润总额
			if (ListUtil.isNotEmpty(repayRolelist)) {
				for (DivsionRuleRole druleRole : repayRolelist) {
					double temp = getDaysRuleRate(druleRole.getRule(), trade)
							* trade.getLoanedAmount();
					repayCutAmount = repayCutAmount + temp;
				}
			}
			double repayAmount = trade.getLoanedAmount() + repayCutAmount;
			boolean isLast = true;
			storeRepayPlanDomain(trade, loanDemand, nowDate, repayAmount, 0, 1,
					1, isLast);
		}
	}

	private void storeRepayPlanDomain(Trade trade, LoanDemandDO loanDemand,
																		Date nowDate, double repayAll, double investAmount, int month,
																		int row, boolean isLast) {

		RepayPlanDO repayPlanDO = createRepayPlan(trade, loanDemand, nowDate,
				repayAll, investAmount, month, row, isLast);
		repayPlanDAO.insert(repayPlanDO);

	}

	private RepayPlanDO createRepayPlan(Trade trade, LoanDemandDO loanDemand,
																			Date nowDate, double repayAll, double investAmount, int month,
																			int row, boolean isLast) {

		RepayPlanDO repayPlanDO = new RepayPlanDO();
		repayPlanDO.setNote(String.valueOf(month));
		repayPlanDO.setPeriodNo(row);
		if (isLast) {
			repayPlanDO.setAmount(new Money(repayAll / 100));
			repayPlanDO.setOriginalAmount(new Money(
					trade.getLoanedAmount() / 100));
		} else {
			double repayAmountPerMonth = new BigDecimal(investAmount / month)
					.doubleValue();
			repayAmountPerMonth = Math.floor(repayAmountPerMonth);
			repayPlanDO.setAmount(new Money(repayAmountPerMonth / 100));

		}
		repayPlanDO.setOriginalAmount(new Money(trade.getLoanedAmount() / 100));
		repayPlanDO.setStatus(RepayPlanStatusEnum.NOTPAY.code());
		repayPlanDO.setRawAddTime(nowDate);
		repayPlanDO.setRawUpdateTime(nowDate);
		Date repayDate = null;
		if (DivisionWayEnum.SIT_WAY.code().equals(
				loanDemand.getRepayDivisionWay())) {
			repayDate = trade.getExpireDateTime();
		} else {
			repayDate = DateUtil.getDateByMonth(nowDate, row);
		}

		repayPlanDO.setRepayDate(repayDate);
		repayPlanDO.setRepayDivisionWay(loanDemand.getRepayDivisionWay());
		repayPlanDO.setRepayUserId(loanDemand.getLoanerId());
		repayPlanDO.setRepayUserName(loanDemand.getUserName());
		repayPlanDO.setRepayUserRealName(loanDemand.getUserName());
		repayPlanDO.setTradeId(trade.getId());
		repayPlanDO.setTradeName(trade.getName());
		repayPlanDO.setPeriodCount(month);
		return repayPlanDO;
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void investReturn(InvestReturnRequest request) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("orderNo", request.getOrderNo());
		List<TradeQueryDetail> list = tradeDetailDao.getTradeDetailByConditions(conditions);
		TradeQueryDetail detail = null;
		if (ListUtil.isNotEmpty(list)) {
			detail = list.get(0);
			if (StringUtil.isNotBlank(detail.getTradeDetailStatus()) && detail.getTradeDetailStatus().startsWith("P")) {
				return;
			}
			TradeDetailStatusEnum status = null;
			if (StringUtil.equalsIgnoreCase("trade_success", request.getTradeStatus())) {
				status = TradeDetailStatusEnum.AS;
			} else {
				status = TradeDetailStatusEnum.AF;
			}
			tradeDetailDao.updatetStatus(detail.getId(), status.getCode());
		}

	}
	
	/**
	 * 保存投资原始资金
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	public TradeDetail saveInvestOriginalTradeDetail(TradeDetail detail) throws Exception {
		if (detail.getRoleId() != 12
			|| !DivisionPhaseEnum.ORIGINAL_PHASE.code().equals(detail.getTransferPhase())) {
			throw new Exception("保存投资原始资金异常");
		}
		long tradeId = detail.getTradeId();
		Trade trade = tradeDao.getByTradeIdWithRowLock(tradeId);
		Map<String, Object> result = doCheckTrade(trade);
		result = doCheckAmount(trade, detail.getAccountId(), detail.getAmount());
		if (!((Boolean) result.get("status"))) {
			throw new RuntimeException("投资处理失败！:投资人ID--" + detail.getUserId() + ",投资金额："
										+ detail.getAmount() + "原因：" + result.get("message"));
		}
		//		tradeDao.addLoanedAmount(tradeId, detail.getAmount());
		long detailId = tradeDetailDao.addTradeDetail(detail);
		detail.setId(detailId); //
		return detail;
	}
	
	/**
	 * 给各个参与角色分润
	 * @param detail
	 * @throws Exception
	 */
	protected void addDetailDivision(TradeDetail detail) throws Exception {
		Trade trade = tradeDao.getByTradeIdWithRowLock(detail.getTradeId());
		//获取模板
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
		long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
			.getByBaseId(divisionTemplateLoanBaseId);
		List<DivsionRuleRole> investRolelist = getRuleRole(String.valueOf(divisionTemplateLoan
			.getInvestTemplateId()));
		List<DivsionRuleRole> repayRolelist = getRuleRole(String.valueOf(divisionTemplateLoan
			.getRepayTemplateId()));
		//investRolelist.addAll(repayRolelist);
		//计算经纪人和营销机构金额(有可能两个阶段都有配置)

		if (DivisionWayEnum.SIT_WAY.getCode().equals(loan.getRepayDivisionWay())) {
			addDetailDivisionRule(detail, trade, investRolelist);
			addDetailDivisionRule(detail, trade, repayRolelist);
		} else if (DivisionWayEnum.MONTH_WAY.getCode().equals(loan.getRepayDivisionWay())) { // 按月付息
			addDetailDivisionRule(detail, trade, investRolelist);
			addDetailMonthInterestRule(detail, trade, repayRolelist);
		}
	}
	
	/**
	 * 生成投资流水
	 * @param detail
	 * @throws Exception
	 */
	protected void addTradeFlowCode(TradeDetail detail) throws Exception {
		long tradeId = detail.getTradeId();
		long detailId = detail.getId();
		Trade trade = tradeDao.getByTradeIdWithRowLock(tradeId);
		//获取模板
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
		TradeFlowCode tradeFlow = new TradeFlowCode();
		tradeFlow.setTblBaseId(BusinessNumberUtil.gainNumber());
		tradeFlow.setTradeDetailId(detailId);
		long countIndex = tradeDao.countInvestedTransactions(tradeId);
		tradeFlow.setTradeFlowCode(loan.getGuaranteeLicenseNo() + "T"
									+ StringUtils.leftPad(String.valueOf(countIndex), 3, "0"));
		tradeFlow.setRowAddTime(new Date());
		tradeFlow.setNote("投资流水号");
		tradeDao.addTradeFlowCode(tradeFlow);
	}
	
	/**
	 * 如果本次投资成功后，投资已满，就通知借款人
	 * @param detail 本次投资详情
	 * @throws Exception
	 */
	protected void notifyLoanerIfFull(TradeDetail detail) throws Exception {

		Trade trade = tradeDao.getByTradeIdWithRowLock(detail.getTradeId());

		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());

//		long gainedAmount1 = trade.getLoanedAmount() + detail.getAmount();
		long tradeFullAmount = 0L;
		if(TradeFullConditionEnum.AMOUNT.code().equals(trade.getSaturationConditionMethod())) {
			tradeFullAmount = Long.valueOf(trade.getSaturationCondition());
		} else if(TradeFullConditionEnum.PERCENTAGE.code().equals(trade.getSaturationConditionMethod())) {
			tradeFullAmount = Math.round(trade.getAmount() * Double.valueOf(trade.getSaturationCondition()));
		}
		if (trade.getLoanedAmount() >= tradeFullAmount) {

			//更改交易状态
			createRelativeTrades(trade);
			//融资完成放款
			tradeFinish(trade);

			//通知借款人，有人投资已满额，显示时间金额
			List<UserBaseInfoDO> loaners = userBaseInfoManager.queryByType(null,
				String.valueOf(loan.getLoanerId()));
			StringBuilder toLoanerMessage = new StringBuilder();
			if (loaners != null && loaners.size() > 0) {
				String content = YrdConstants.MessageNotifyConstants.LOAN_DEMAND_INVESTED_CONTENT;
//				content = content.replace("productName", AppConstantsUtil.getProductName());
				content = content.replace("var1", trade.getName());
				content = content.replace("var2", MoneyUtil.getFormatAmount(trade.getAmount()));
				content = content.replace("var3",MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
//					MoneyUtil.getFormatAmount(trade.getLoanedAmount() + detail.getAmount()));
				toLoanerMessage.append(content);
				messageService.notifyUser(loaners.get(0), toLoanerMessage.toString());
			}

			// 通知运营人员
			StringBuilder message = new StringBuilder();
			String content = YrdConstants.MessageNotifyConstants.DEMAND_CONFIRMED_NOTIFY;
			UserBaseInfoDO toUser = new UserBaseInfoDO();
			content = content.replace("var1", trade.getName());
			content = content.replace("var2", MoneyUtil.getFormatAmount(trade.getAmount()));
			content = content.replace("var3", MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
			message.append(content);
			setToUserDetail(toUser);
			messageService.notifyUser(toUser, message.toString());
	}
	}
	
	private void addDetailDivisionRule(TradeDetail detail, Trade trade,
										List<DivsionRuleRole> investRolelist) throws Exception {
		for (DivsionRuleRole divsionRuleRole : investRolelist) {
			if (SysUserRoleEnum.MARKETING.code()
				.equals(String.valueOf(divsionRuleRole.getRoleId()))) {
				addMarketingRelationUser(detail, trade, divsionRuleRole);
			} else if (SysUserRoleEnum.BROKER.code().equals(
				String.valueOf(divsionRuleRole.getRoleId()))) {
				addBrokerRelationUser(detail, trade, divsionRuleRole);
			} else if (SysUserRoleEnum.INVESTOR.code().equals(String.valueOf(divsionRuleRole.getRoleId()))) {
				addInvestor(detail, trade, divsionRuleRole);
			} else if (SysUserRoleEnum.PLATFORM.code().equals(String.valueOf(divsionRuleRole.getRoleId()))) {
				if(divsionRuleRole.getRule() > 0) {
//					addPlatform(detail, trade, divsionRuleRole);
				}
			}
		}
	}
	
	private void addDetailMonthInterestRule(TradeDetail detail, Trade trade,
											List<DivsionRuleRole> repayRolelist) throws Exception {
		for (DivsionRuleRole divsionRuleRole : repayRolelist) {
			if (SysUserRoleEnum.MARKETING.code()
				.equals(String.valueOf(divsionRuleRole.getRoleId()))) {
				addMarketingRelationUser(detail, trade, divsionRuleRole);
			} else if (SysUserRoleEnum.BROKER.code().equals(
				String.valueOf(divsionRuleRole.getRoleId()))) {
				addBrokerRelationUser(detail, trade, divsionRuleRole);
			} else if (SysUserRoleEnum.INVESTOR.code().equals(
				String.valueOf(divsionRuleRole.getRoleId()))) {
				// addInvestor(detail, trade, divsionRuleRole);
				addInvestorByMonthInterest(detail, trade, divsionRuleRole);
			}
		}

	}
	
	private void addInvestorByMonthInterest(TradeDetail detail, Trade trade,
											DivsionRuleRole divsionRuleRole) {
		int monthes = trade.getTimeLimit();

		double investAmount = 0;
		long tradeId = trade.getId();
		long detailId = detail.getId();
		BigDecimal bg = new BigDecimal(getDaysRuleRate(divsionRuleRole.getRule(), trade)
										* detail.getAmount());
		investAmount = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		double monthMoney = investAmount / monthes;
		// investAmount = Math.floor(investAmount);
		monthMoney = Math.floor(monthMoney);

		for (int i = 0; i < monthes; i++) {
			TradeDetail tradeDetail = new TradeDetail(detail.getUserId(), tradeId,
				(long) monthMoney, 12, divsionRuleRole.getPhase(), String.valueOf(detailId));
			tradeDetail.setRepayPeriodNo(i + 1);
			tradeDetail.setRepayPeriodCount(monthes);
			tradeDetailDao.addTradeDetail(tradeDetail);
		}
	}

	private void addPlatform(TradeDetail detail, Trade trade, DivsionRuleRole divsionRuleRole) {
		double platformAmount = 0;
		long tradeId = trade.getId();
		long detailId = detail.getId();
		BigDecimal bg = new BigDecimal(getDaysRuleRate(divsionRuleRole.getRule(), trade)
				* trade.getLoanedAmount());
		platformAmount = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		platformAmount = Math.floor(platformAmount);
		// 添加投资人
		if (platformAmount > 0) {
			UserBaseInfoDO profitUser = userBaseInfoManager.queryByAccountId(AppConstantsUtil.getProfitSharingAccount());
			tradeDetailDao.addTradeDetail(new TradeDetail(profitUser.getUserId(), tradeId,
					(long) platformAmount, 7, divsionRuleRole.getPhase(), String.valueOf(detailId)));
		}
	}

	private void addInvestor(TradeDetail detail, Trade trade, DivsionRuleRole divsionRuleRole) {
		double investAmount = 0;
		long tradeId = trade.getId();
		long detailId = detail.getId();
		BigDecimal bg = new BigDecimal(getDaysRuleRate(divsionRuleRole.getRule(), trade)
										* detail.getAmount());
		investAmount = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		investAmount = Math.floor(investAmount);
		// 添加投资人
		if (investAmount > 0)
			tradeDetailDao.addTradeDetail(new TradeDetail(detail.getUserId(), tradeId,
				(long) investAmount, 12, divsionRuleRole.getPhase(), String.valueOf(detailId)));
	}
	
	private void addBrokerRelationUser(TradeDetail detail, Trade trade,
										DivsionRuleRole divsionRuleRole) throws Exception {
		long detailId = detail.getId();
		long tradeId = trade.getId();
		Page<UserRelationDO> userRelationsPage = userRelationManager.getRelationsByConditions(null,
			null, detail.getUserId(), null);

		UserBaseInfoDO jjrUserBaseInfo = null;
		if (ListUtil.isNotEmpty(userRelationsPage.getResult())) {
			UserRelationDO relationDO = userRelationsPage.getResult().get(0);
			List<UserBaseInfoDO> curParentJjrs = userBaseInfoManager.queryByType(null,
				String.valueOf(relationDO.getParentId()));
			if (ListUtil.isNotEmpty(curParentJjrs)) {
				if (UserTypeEnum.GR.code().equals(curParentJjrs.get(0).getType())) {
					jjrUserBaseInfo = curParentJjrs.get(0);
				}
			}
			if (jjrUserBaseInfo == null && this.sysFunctionConfigService.isAllEconomicMan()) {
				jjrUserBaseInfo = userBaseInfoManager.queryByUserId(detail.getUserId());
			}
		}
		if (jjrUserBaseInfo != null) {
			ProfitAsignInfo profitAsignInfo = userRelationManager
				.queryByReceiveIdAndDistributionId(detail.getUserId(), jjrUserBaseInfo.getUserId());
			double distributionRule = 0;
			double distributionAmount = 0;
			if (profitAsignInfo != null) {
				distributionRule = CommonUtil.mul(divsionRuleRole.getRule(),
					profitAsignInfo.getDistributionQuota());
			}
			double actualRule = divsionRuleRole.getRule() - distributionRule;
			BigDecimal bg = new BigDecimal(getDaysRuleRate(actualRule, trade) * detail.getAmount());
			double jjrAmount = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
			jjrAmount = Math.floor(jjrAmount);
			if (distributionRule > 0) {
				BigDecimal bgdis = new BigDecimal(getDaysRuleRate(distributionRule, trade)
													* detail.getAmount());
				distributionAmount = bgdis.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
				distributionAmount = Math.floor(distributionAmount);
				//添加经纪人配置收益过的投资人的额外交易
				TradeDetail extraDetail = new TradeDetail(detail.getUserId(), tradeId,
					(long) distributionAmount, 12, divsionRuleRole.getPhase(),
					String.valueOf(detailId));
				extraDetail.setProfitType(1);
				extraDetail.setProfitRate(distributionRule);
				tradeDetailDao.addTradeDetail(extraDetail);
			}

			//添加经纪人
			tradeDetailDao.addTradeDetail(new TradeDetail(jjrUserBaseInfo.getUserId(), tradeId,
				(long) jjrAmount, 11, divsionRuleRole.getPhase(), String.valueOf(detailId)));
		}

	}
	
	private void addMarketingRelationUser(TradeDetail detail, Trade trade,
											DivsionRuleRole divsionRuleRole) throws Exception {
		double yxJGAmount = 0;
		BigDecimal bg = new BigDecimal(getDaysRuleRate(divsionRuleRole.getRule(), trade)
										* detail.getAmount());
		yxJGAmount = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		yxJGAmount = Math.floor(yxJGAmount);

		UserBaseInfoDO curParentJjr = null;
		if (curParentJjr == null) {
			try {
				curParentJjr = userBaseInfoManager.queryByUserName(detail.getUserName(),
					SysUserRoleEnum.BROKER.getValue());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (curParentJjr == null) {
			Page<UserRelationDO> userRelationsPage = userRelationManager.getRelationsByConditions(
				null, null, detail.getUserId(), null);
			if (userRelationsPage.getResult() != null && userRelationsPage.getResult().size() > 0) {
				List<UserBaseInfoDO> curParentJjrs = userBaseInfoManager.queryByType(null,
					String.valueOf(userRelationsPage.getResult().get(0).getParentId()));
				if (curParentJjrs != null && curParentJjrs.size() > 0) {
					if (UserTypeEnum.GR.code().equals(curParentJjrs.get(0).getType())) {
						curParentJjr = curParentJjrs.get(0);
					}

				}
			}
		}
		if (curParentJjr != null) {
			//获取营销机构
			UserBaseInfoDO curParentJG = null;
			int maxLength = 20;
			int index = 0;
			long curParentJjrUserId = curParentJjr.getUserId();
			while (curParentJG == null && index < maxLength) {
				index++;
				Page<UserRelationDO> userRelationsJGPage = userRelationManager
					.getRelationsByConditions(null, null, curParentJjrUserId, null);
				if (userRelationsJGPage.getResult() != null
					&& userRelationsJGPage.getResult().size() > 0) {
					long parentUserId = userRelationsJGPage.getResult().get(0).getParentId();
					UserBaseInfoDO parentUser = userBaseInfoManager.queryByUserId(parentUserId);
					if (parentUser != null) {
						if (UserTypeEnum.JG.code().equals(parentUser.getType())) {
							curParentJG = parentUser;
							break;
						} else {
							curParentJjrUserId = parentUser.getUserId();
						}
					}
				} else {
					break;
				}
			}
			if (curParentJG != null) {
				//添加营销机构
				tradeDetailDao.addTradeDetail(new TradeDetail(curParentJG.getUserId(), trade
					.getId(), (long) yxJGAmount, 10, divsionRuleRole.getPhase(), String
					.valueOf(detail.getId())));
			}

		}

		//		double yxJGAmount = 0;
		//		long tradeId = trade.getId();
		//		long detailId = detail.getId();
		//		BigDecimal bg = new BigDecimal(getDaysRuleRate(
		//				divsionRuleRole.getRule(), trade)
		//				* detail.getAmount());
		//		yxJGAmount = bg.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		//		yxJGAmount = Math.floor(yxJGAmount);
		//		// 获取经纪人
		//		Page<UserRelationDO> userRelationsPage = userRelationManager
		//				.getRelationsByConditions(null, null, detail.getUserId(), null);
		//		if (userRelationsPage.getResult() != null
		//				&& userRelationsPage.getResult().size() > 0) {
		//			List<UserBaseInfoDO> curParentJjrs = userBaseInfoManager
		//					.queryByType(
		//							null,
		//							String.valueOf(userRelationsPage.getResult().get(0)
		//									.getParentId()));
		//			UserBaseInfoDO curParentJjr = null;
		//			if (curParentJjrs != null && curParentJjrs.size() > 0) {
		//				curParentJjr = curParentJjrs.get(0);
		//				// 获取营销机构
		//				Page<UserRelationDO> userRelationsJGPage = userRelationManager
		//						.getRelationsByConditions(null, null,
		//								curParentJjr.getUserId(), null);
		//				if (userRelationsJGPage.getResult() != null
		//						&& userRelationsJGPage.getResult().size() > 0) {
		//					List<UserBaseInfoDO> curParentJGs = userBaseInfoManager
		//							.queryByType(
		//									UserTypeEnum.JG.code(),
		//									String.valueOf(userRelationsJGPage
		//											.getResult().get(0).getParentId()));
		//					UserBaseInfoDO curParentJG = null;
		//					if (curParentJGs != null && curParentJGs.size() > 0) {
		//						curParentJG = curParentJGs.get(0);
		//						// 添加营销机构
		//						tradeDetailDao.addTradeDetail(new TradeDetail(
		//								curParentJG.getUserId(), tradeId,
		//								(long) yxJGAmount, 10, divsionRuleRole
		//										.getPhase(), String.valueOf(detailId)));
		//					}
		//				}
		//			}
		//		}
	}
	
	/**
	 * 校验交易
	 * @param trade
	 * @return
	 */
	private Map<String, Object> doCheckTrade(Trade trade) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", false);
		if (trade == null) {
			result.put("message", "该借款需求存在异常");
			return result;
		}
		if (trade.getStatus() != YrdConstants.TradeStatus.TRADING) {
			result.put("message", "该借款需不处于幕资阶段，不能进行投资");
		} else {
			result.put("status", true);
		}
		return result;
	}
	
	/**
	 * 校验金额
	 * @param amount
	 * @return
	 */
	private Map<String, Object> doCheckAmount(Trade trade, String userId, Long amount) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", false);
		if (amount == null || amount <= 0) {
			result.put("message", "投资金额必须大于0");
			return result;
		}
		
		if ((trade.getLeastInvestAmount() - (trade.getAmount() - trade.getLoanedAmount())) <= 0) {
			if (amount < trade.getLeastInvestAmount()) {
				result.put("message", "投资金额不能小于该笔借款的最低投资金额");
				return result;
			}
		}
		
		if (trade.getLoanedAmount() + amount > trade.getAmount()) {
			result.put("message", "投资金额不能大于该笔借款的可投资金额");
			return result;
		}
		YzzUserAccountQueryRequest queryRequest = new YzzUserAccountQueryRequest(userId);
		YzzUserAccountQueryResponse accountResult = openApiGatewayService
			.userAccountQuery(queryRequest);
		if (!accountResult.success()) {
			result.put("message", "资金账户不存在");
			return result;
		}
		
		result.put("status", true);
		return result;
	}
	
	@Override
	public List<InvestDetailDO> queryInvestDetail(Map<String, Object> condition) {
		return tradeDetailDao.queryInvestDetail(condition);
	}
	
	@Override
	public void updateInvestDetailRepayDate(long detailId, Date repayDate) {
		tradeDetailDao.updateRepaydate(detailId, repayDate);
	}
	
}
