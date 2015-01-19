package com.icebreak.p2p.trade.impl;

import com.icebreak.p2p.base.BaseAutowiredToolsService;
import com.icebreak.p2p.dal.daointerface.RepayPlanDAO;
import com.icebreak.p2p.dal.dataobject.RepayPlanDO;
import com.icebreak.p2p.daointerface.*;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.dataobject.viewObject.AmounFlowVO;
import com.icebreak.p2p.dataobject.viewObject.TradeDivisionDetailVO;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.division.DivisionTemplateYrdService;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.integration.openapi.order.RepayOrder;
import com.icebreak.p2p.integration.openapi.order.RepaySubOrder;
import com.icebreak.p2p.integration.openapi.order.RepayTradeOrder;
import com.icebreak.p2p.integration.openapi.result.RepayTradeResult;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.InvestService;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.trade.RechercheFlowOrder;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.transfer.TransferService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.*;
import com.icebreak.p2p.ws.enums.*;
import com.icebreak.p2p.ws.info.RepayPlanInfo;
import com.icebreak.p2p.ws.order.CalculateLoanCostOrder;
import com.icebreak.p2p.ws.result.CalculateLoanCostResult;
import com.icebreak.p2p.ws.service.RepayPlanService;
import com.icebreak.p2p.ws.service.query.order.RepayPlanQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.message.yzz.RepayNotify;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class TradeServiceImpl extends BaseAutowiredToolsService implements
		TradeService {

	private InvestmentDao investmentDao = null;

	private TradeDao tradeDao = null;

	private TradeDetailDao tradeDetailDao = null;

	private TransferService transferService = null;

	private TransferTradeDao transferTradeDao = null;

	private UserInvestEntryDao userInvestEntryDao = null;

	private DivisionService divisionService = null;
	@Autowired
	private UserBaseInfoManager userBaseInfoManager;

	@Autowired
	private LoanDemandManager loanDemandManager;
	@Autowired
	private DivisionDetailDao divisionDetailDao;
	@Autowired
	private DivisionTemplateYrdService divisionTemplateLoanService;
	@Autowired
	protected InvestService investService;
	@Autowired
	private AmountFlowDao amountFlowDao;
	@Autowired
	RepayPlanDAO repayPlanDAO;
	@Autowired
	RepayPlanService repayPlanService;

	// 默认提前五天通知还款
	private String advancedNotifyDay = "5";
	/**
	 * 平台金融在托管机构平台中的用户ID:用于存储中转资金
	 */
	private long yrdExchangeUserId = 0;
	/**
	 * 平台在托管机构的账户:用于存储中转资金
	 */
	private String yrdYjfExchangeUserName;
	/**
	 * 平台在自身平台中的用户ID：用于分润
	 */
	private long yrdUserId = 0;
	/**
	 * 平台中转账户userName
	 */
	private String exchangeUserName;
	/**
	 * 平台分润账户userName
	 */
	private String financeUserName;
	/**
	 * 平台金融角色id
	 */
	private final String platformRoleId = SysUserRoleEnum.PLATFORM.code();
	/**
	 * 担保机构角色id
	 */
	private final String guaranteeRoleId = SysUserRoleEnum.GUARANTEE.code();
	/**
	 * 保荐机构角色id
	 */
	private final String sponsorRoleId = SysUserRoleEnum.SPONSOR.code();

	public boolean isInitAccountInfo = false;

	public String getPlatformRoleId() {
		return platformRoleId;
	}

	public String getGuaranteeRoleId() {
		return guaranteeRoleId;
	}

	public String getSponsorRoleId() {
		return sponsorRoleId;
	}

	public String getAdvancedNotifyDay() {
		return advancedNotifyDay;
	}

	public void setAdvancedNotifyDay(String advancedNotifyDay) {
		this.advancedNotifyDay = advancedNotifyDay;
	}

	public void setDivisionService(DivisionService divisionService) {
		this.divisionService = divisionService;
	}

	public void setUserInvestEntryDao(UserInvestEntryDao userInvestEntryDao) {
		this.userInvestEntryDao = userInvestEntryDao;
	}

	public String getFinanceUserName() {
		return financeUserName;
	}

	public void setFinanceUserName(String financeUserName) {
		this.financeUserName = financeUserName;
	}

	public String getExchangeUserName() {
		return exchangeUserName;
	}

	public void setExchangeUserName(String exchangeUserName) {
		this.exchangeUserName = exchangeUserName;
	}

	public void setTransferTradeDao(TransferTradeDao transferTradeDao) {
		this.transferTradeDao = transferTradeDao;
	}

	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}

	public void setTradeDetailDao(TradeDetailDao tradeDetailDao) {
		this.tradeDetailDao = tradeDetailDao;
	}

	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	public void setInvestmentDao(InvestmentDao investmentDao) {
		this.investmentDao = investmentDao;
	}

	@Override
	public void modifyStatus(long tradeId, short status) {
		tradeDao.modifyStatus(tradeId, status);
	}

	@Override
	public void modifyStatus(long tradeId, short status, TradeDetail tradeDetail) {
		tradeDao.modifyStatus(tradeId, status);
	}

	@Override
	public Pagination<Investment> getByProperties(long tradeId, int start,
			int size, Integer status, String receiveRealName,
			String receiveUserName, String payRealName, String payUserName,
			Long startAmount, Long endAmount, Date startDate, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("size", size);
		if (status != null && status > 0) {
			params.put("status", status);
		}
		params.put("receiveRealName", receiveRealName);
		params.put("receiveUserName", receiveUserName);
		params.put("payRealName", payRealName);
		params.put("payUserName", payUserName);
		params.put("startAmount", startAmount);
		params.put("endAmount", endAmount);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("tradeId", tradeId);
		return new Pagination<Investment>(start,
				investmentDao.getCountByProperties(params), size,
				investmentDao.getByProperties(params));
	}

	@Override
	public Investment getById(long id) {
		return investmentDao.getById(id);
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public RepayOrder gotoYJFRepay(long repayUserId, long demandId,
			long repayPlanId) throws Exception {

		List<UserBaseInfoDO> repayUsers = userBaseInfoManager.queryByType(null,
				String.valueOf(repayUserId));
		UserBaseInfoDO repayUser = null;
		if (ListUtil.isNotEmpty(repayUsers)) {
			repayUser = repayUsers.get(0);
			if (!"normal".equals(repayUser.getState())) {
				logger.error("还款人用户状态异常，userId，{}", repayUserId);
				throw new Exception("user state error");
			}
		} else {
			logger.error("查询还款人信息异常，userId，{}", repayUserId);
			throw new Exception("can not find repay user");
		}

		Trade trade = tradeDao.getByDemandId(demandId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());

		RepayPlanInfo repayPlanInfo = null;

		if (DivisionWayEnum.SIT_WAY.code().equals(loan.getRepayDivisionWay())) {
			RepayPlanQueryOrder repayPlanQueryOrder = new RepayPlanQueryOrder();
			repayPlanQueryOrder.setRepayDivisionWay(DivisionWayEnum.SIT_WAY
					.code());
			repayPlanQueryOrder.setPageSize(999999);
			repayPlanQueryOrder.setPageNumber(1);
			repayPlanQueryOrder.setTradeId(trade.getId());
			repayPlanQueryOrder.setPeriodNo(1);
			QueryBaseBatchResult<RepayPlanInfo> result = repayPlanService
					.queryRepayPlanInfo(repayPlanQueryOrder);
			if (ListUtil.isNotEmpty(result.getPageList())) {
				repayPlanInfo = result.getPageList().get(0);
				repayPlanId = repayPlanInfo.getRepayPlanId(); // 获取第一期还款计划id
			}
		} else {
			if (repayPlanId == 0) {
				throw new RuntimeException("请求参数不完整，repayPlanId=" + repayPlanId
						+ "，还款计划id不为0");
			}
		}

		// 获取资金信息
		Money availableBalance = null;

		YzzUserAccountQueryResponse accountResult = apiTool
				.queryUserAccount(repayUser.getAccountId());

		if (accountResult.success()) {
			availableBalance = new Money(accountResult.getAvailableBalance());
		} else {
			throw new Exception("获取资金信息异常");
		}
		
		RepayPlanDO repayPlan = repayPlanDAO.findById(repayPlanId);

		if(StringUtil.equalsIgnoreCase("PS",repayPlan.getStatus())||StringUtil.equalsIgnoreCase("AS",repayPlan.getStatus())){
			throw new Exception("正在还款中或已完成还款。");
		}

		if(StringUtil.isBlank(repayPlan.getCreateRepayNo())) {
			// 创建还款交易
			RepayTradeOrder order = new RepayTradeOrder();
			order.setPayerUserId(repayUser.getAccountId());
			order.setTradeAmount(repayPlanInfo.getAmount());
			order.setTradeMemo(repayPlanInfo.getNote());
			order.setTradeName(repayPlanInfo.getTradeName() + "还款");
			RepayTradeResult result = repayService.createRepayTrade(order,
					this.getOpenApiContext());
			if (!result.isSuccess()) {
				return null;
			}

			repayPlan.setCreateRepayNo(result.getTradeNo());
			
			repayPlan.setStatus(RepayPlanStatusEnum.APPLY.getCode());
			try {
				repayPlanDAO.update(repayPlan);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		long repayAmount = 0;
		double repayCutAmount = 0;
		// 除投资人外的分润金额
		double repayDivisionAmount = 0;
		// 到期归还本金及利息,用户余额是否充足
		if (DivisionWayEnum.SIT_WAY.code().equals(loan.getRepayDivisionWay())) {
			// 还款阶段利率
			long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
			DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
					.getByBaseId(divisionTemplateLoanBaseId);
			List<DivsionRuleRole> repayRolelist = divisionService
					.getRuleRole(String.valueOf(divisionTemplateLoan
							.getRepayTemplateId()));
			double payInterest = 0;
			if (repayRolelist != null && repayRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : repayRolelist) {
					if (DivisionPhaseEnum.REPAY_PHASE.code().equals(
							druleRole.getPhase())) {
						BigDecimal bg = new BigDecimal(getDaysRuleRate(
								druleRole.getRule(), trade));
						repayCutAmount += Math.round(trade.getLoanedAmount()
								* bg.setScale(10, BigDecimal.ROUND_HALF_UP)
										.doubleValue());
						payInterest += bg
								.setScale(10, BigDecimal.ROUND_HALF_UP)
								.doubleValue();

						if (druleRole.getRoleId() != SysUserRoleEnum.INVESTOR
								.getValue()) {
							repayDivisionAmount += Math.round(trade
									.getLoanedAmount()
									* bg.setScale(10, BigDecimal.ROUND_HALF_UP)
											.doubleValue());
						}
					}
				}
			}
			repayAmount = trade.getLoanedAmount() + (long) repayCutAmount;
			if (availableBalance.getCent() < repayAmount) {
				logger.info("该用户余额不足---用户id：" + repayUser.getUserId());
				return null;
			}
		}

		String repayYjfUserId = tradeDetailDao
				.getYjfUserNameByUserId(repayUserId);

		logger.info("repayUserId :" + repayUserId + " " + repayYjfUserId + " "
				+ repayUser.getAccountId());

		long tradeId = trade.getId();

		RepayOrder repayOrder = new RepayOrder();

		// 到期归还本金及利息,用户余额是否充足
		if (DivisionWayEnum.SIT_WAY.code().equals(loan.getRepayDivisionWay())) {

			// 还投资人本金流水
			List<TransferTrade> investTradeList = transferTradeDao
					.getPhaseTransferTrades(tradeId,
							DivisionPhaseEnum.ORIGINAL_PHASE.code(),
							TradeDetailStatusEnum.PS.code(), //限制状态为：投资成功
							new String[]{SysUserRoleEnum.INVESTOR.getRoleCode()});

			// 投资人的利润流水
			List<TransferTrade> investDevisionList = transferTradeDao
					.getPhaseTransferTrades(tradeId,
							DivisionPhaseEnum.REPAY_PHASE.getCode(),
							null, //不限制状态
							new String[]{SysUserRoleEnum.INVESTOR.getRoleCode()});

			// 中间账户转给还款阶段的分润账户
			List<TransferTrade> divisionList = divisionService
					.queryRepayDivision(tradeId);

			List<RepaySubOrder> subOrders = new ArrayList<RepaySubOrder>();
			for (int i = 0; i < investTradeList.size(); i++) {
				TransferTrade tt = investTradeList.get(i);
				RepaySubOrder subOrder = new RepaySubOrder();
				Money transferAmount = Money.cent(tt.getAmount());

				TransferTrade td = investDevisionList.get(i);
				if (tt.getUserId() == td.getUserId()) {
					transferAmount.addTo(Money.cent(td.getAmount()));
				}

				subOrder.setTransferAmount(transferAmount.toString());
				subOrder.setMemo("还款");
				subOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
				subOrder.setPayeeUserId(tt.getYjfUserName());
				subOrder.setTradeName("还款");
				subOrders.add(subOrder);
			}



			repayOrder.setPayerUserId(repayUser.getAccountId());
			repayOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			repayOrder.setRefTradeNo(trade.getCode());
			//除投资人外其他分润
			if(repayDivisionAmount > 0) {
				RepaySubOrder shardOrder = new RepaySubOrder();
				shardOrder.setMemo("分润");
				shardOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
				shardOrder.setPayeeUserId(Constants.EXCHANGE_ACCOUNT);
				shardOrder.setTradeName("分润");
				shardOrder
						.setTransferAmount(Money.cent((long) repayDivisionAmount).toString());
				repayOrder.setShardOrder(shardOrder);
			}
			repayOrder.setSubOrders(subOrders);
			repayOrder.setTradeNo(repayPlan.getCreateRepayNo());
		}
		//还款的交易唯一编号
		repayPlan.setRepayNo(repayOrder.getOrderNo());
		repayPlan.setStatus(RepayPlanStatusEnum.APPLY.getCode());
		repayPlanDAO.update(repayPlan);

		return repayOrder;
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int repayNotify(RepayNotify notify) throws Exception {

		Date date = new Date();

		RepayPlanDO repayPlan = new RepayPlanDO();
		//如果前台页面没有正常返回，则没有batchNo,所以要做特殊处理
//		repayPlan.setRepayNo(notify.getTradeNo());
//		repayPlan.setCreateBatchNo(notify.getCreateBatchNo());
		repayPlan.setCreateRepayNo(notify.getTradeNo());
		List<RepayPlanDO> planList = repayPlanDAO.query(repayPlan);
		if (ListUtil.isNotEmpty(planList)) {
			RepayPlanDO plan = null;
			if (planList.size() > 1) {
				for (RepayPlanDO repay : planList) {
					if(StringUtil.equalsIgnoreCase(repay.getCreateBatchNo(),notify.getCreateBatchNo())){
						plan = repay;
					}
				}
			} else {
				plan = planList.get(0);
			}

			long tradeId = plan.getTradeId();
			logger.info("中间账户转给还款阶段的分润账户");
			divisionService.division(tradeId);

			logger.info("添加还款收支明细");
			RechargeFlow rechargeFlow = new RechargeFlow();
			rechargeFlow.setLocalNo(BusinessNumberUtil.getSerialNo());
			rechargeFlow.setOutBizNo(notify.getTradeNo());
			rechargeFlow.setAmount(plan.getAmount().getCent());
			rechargeFlow.setUserId(plan.getRepayUserId());
			Date now = new Date();
			rechargeFlow.setPayTime(now);
			rechargeFlow.setStatus(1);
			rechargeFlow.setPayType(DivisionTypeEnum.PROFIT.getCode());
			rechargeFlow.setPayMemo("分润");
			rechargeFlow.setPayFinishTime(now);
			try {
				//借款人
				rechargeFlowService.addRechargeFlow(rechargeFlow);
				//投资人本金利息还款交易记录
				repayRechargeFlow(tradeId);
			} catch (Exception e) {
				logger.info("添加收支明细失败.");
			}

			logger.info("修改借款交易的状态为：3");
			tradeDao.modifyStatus(tradeId, (short) 3);
			tradeDao.modifyFinishDate(tradeId, date);

			// notifyLoaner(trade, loan, repayCutAmount);
			// notifyInvestor(trade);

			logger.info("更新还款计划的状态为：REPAY_SUCCESS");
			repayPlanService.updateStatus(plan.getRepayPlanId(),
					RepayPlanStatusEnum.REPAY_SUCCESS.getCode());
			repayPlanService.updateActualRepayDate(plan.getRepayPlanId(),
					new Date());
		}
		logger.info("还款成功!");
		return 0;
	}

	private int repayRechargeFlow(long tradeId) {
		//构建本金还款记录
		Map<String, Object> original = new HashMap<String, Object>();
		original.put("tradeId", tradeId);
		List<String> status = new ArrayList<String>();
//		status.add("original");
		status.add("PS");
		original.put("status", status);
		original.put("transferPhase", "original");
		addAmountFlow(original);
		//构建利息还款记录
		Map<String, Object> repay = new HashMap<String, Object>();
		repay.put("tradeId", tradeId);
		repay.put("transferPhase", "repay");
		addAmountFlow(repay);
		return 0;
	}

	public void addAmountFlow(Map<String, Object> map) {
		logger.info("【还款回推，查询TradeDetail投资数据参数】" + map);
		List<TradeQueryDetail> tradeQueryDetails = tradeDetailDao.getCollectionByConditions(map);
		logger.info("【还款回推，查询TradeDetail投资数据参数】" + tradeQueryDetails);
		for(TradeQueryDetail tradeDetail : tradeQueryDetails) {

			RechargeFlow rechargeFlow = new RechargeFlow();
			rechargeFlow.setLocalNo(BusinessNumberUtil.getSerialNo());
			rechargeFlow.setAmount(tradeDetail.getAmount());
			rechargeFlow.setUserId(tradeDetail.getUserId());
			Date now = new Date();
			rechargeFlow.setPayTime(now);
			rechargeFlow.setStatus(1);
			rechargeFlow.setPayFinishTime(now);
			if(null != tradeDetail.getOrderNo()) {
				rechargeFlow.setPayType(DivisionTypeEnum.REPAY.getCode());
				rechargeFlow.setPayMemo("还款本金");
			} else {
				rechargeFlow.setPayType(DivisionTypeEnum.PROFIT.getCode());
				rechargeFlow.setPayMemo("分润");
			}
		}
	}

	/**
	 * 通知投资人
	 * 
	 * @param trade
	 * @throws Exception
	 */
	private void notifyInvestor(Trade trade) {
		try {
			long tradeId = trade.getId();
			Map<String, Object> investCondition = new HashMap<String, Object>();
			investCondition.put("tradeId", tradeId);
			List<InvestDetailDO> investDetails = tradeDetailDao
					.queryInvestDetail(investCondition);
			for (InvestDetailDO investDetailDO : investDetails) {
				long investAmount = investDetailDO.getAmount();
				long interestAmount = investDetailDO.getInterestAmount();
				StringBuilder toInvestorMessage = new StringBuilder();
				String content = YrdConstants.MessageNotifyConstants.REPAY_NOTIFY_INVESTOR_CONTENT;
				content = content.replace("var1", trade.getName());
				content = content.replace("var2",
						MoneyUtil.getFormatAmount(investAmount));
				content = content.replace("var3",
						DateUtil.simpleFormatYmdhms(new Date()));
				content = content.replace("var4",
						MoneyUtil.getFormatAmount(interestAmount));
				toInvestorMessage.append(content);
				UserBaseInfoDO investor = null;
				List<UserBaseInfoDO> investors = userBaseInfoManager
						.queryByType(null,
								String.valueOf(investDetailDO.getUserId()));
				if (investors != null && investors.size() > 0) {
					investor = investors.get(0);
				} else {
					logger.error("查询投资人信息异常，userId，{}",
							investDetailDO.getUserId());
				}
				messageService.notifyUser(investor,
						toInvestorMessage.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通知投资人
	 * 
	 * @param trade
	 * @throws Exception
	 */
	private void notifyInvestor4RepayPlan(Trade trade,
			RepayPlanInfo repayPlanInfo) {
		try {
			long tradeId = trade.getId();
			List<TransferTrade> transferTrades = transferTradeDao
					.getPhaseTransferTrades(tradeId,
							DivisionPhaseEnum.REPAY_PHASE.code(),
							repayPlanInfo.getPeriodNo(),
							SysUserRoleEnum.INVESTOR.getRoleCode());
			Map<Long, Money> map = queryInvestorsOriginalAmount(trade);
			for (TransferTrade transferTrade : transferTrades) {
				long transferAmount = transferTrade.getAmount();
				Money money = new Money();
				money.setCent(transferAmount);
				// 最后一期算本金
				if ((repayPlanInfo == null)
						|| (repayPlanInfo.getPeriodNo() == repayPlanInfo
								.getPeriodCount())) {
					Money investAmount = map.get(transferTrade.getUserId());
					if (investAmount != null) {
						money.addTo(investAmount);
					}
				}
				StringBuilder toInvestorMessage = new StringBuilder();
				String content = YrdConstants.MessageNotifyConstants.REPAY_NOTIFY_INVESTOR_CONTENT_4PLAN;
				content = content.replace("var1", trade.getName());
				content = content.replace("var3",
						DateUtil.simpleFormatYmdhms(new Date()));
				content = content.replace("var4", money.toStandardString());
				content = content.replace("var5", repayPlanInfo.getPeriodNo()
						+ "");
				toInvestorMessage.append(content);
				UserBaseInfoDO investor = null;
				List<UserBaseInfoDO> investors = userBaseInfoManager
						.queryByType(null,
								String.valueOf(transferTrade.getUserId()));
				if (investors != null && investors.size() > 0) {
					investor = investors.get(0);
				} else {
					logger.error("查询投资人信息异常，userId，{}",
							transferTrade.getUserId());
				}
				messageService.notifyUser(investor,
						toInvestorMessage.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 求投资人，原始投资金额
	 * 
	 * @param trade
	 * @return
	 */
	private Map<Long, Money> queryInvestorsOriginalAmount(Trade trade) {
		Map<Long, Money> map = new HashMap<Long, Money>();
		try {
			List<TransferTrade> transferTrades = transferTradeDao
					.getPhaseTransferTrades(trade.getId(),
							DivisionPhaseEnum.ORIGINAL_PHASE.code(), null,
							new String[]{SysUserRoleEnum.INVESTOR.getRoleCode()});
			for (TransferTrade transferTrade : transferTrades) {
				Money money = new Money();
				money.setCent(transferTrade.getAmount());
				Money totalAmount = map.get(transferTrade.getUserId());
				if (totalAmount == null) {
					totalAmount = new Money();
					totalAmount.addTo(money);
					map.put(transferTrade.getUserId(), totalAmount);
				} else {
					totalAmount.addTo(money);
				}
			}

		} catch (Exception e) {
			logger.error("求投资人的原始投资金异常:e={}", e.getMessage(), e);
		}

		return map;
	}

	/**
	 * 通知借款人
	 * 
	 * @param trade
	 * @param loan
	 * @param repayCutAmount
	 * @throws Exception
	 */
	private void notifyLoaner(Trade trade, LoanDemandDO loan,
			double repayCutAmount) {
		List<UserBaseInfoDO> loaners;
		try {
			loaners = userBaseInfoManager.queryByType(null,
					String.valueOf(loan.getLoanerId()));
			StringBuilder toLoanerMessage = new StringBuilder();
			if (loaners != null && loaners.size() > 0) {
				String content = YrdConstants.MessageNotifyConstants.REPAY_NOTIFY_LOANER_CONTENT;
				content = content.replace("var1", trade.getName());
				content = content.replace("var2",
						MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
				content = content.replace(
						"var3",
						MoneyUtil.getFormatAmount(trade.getLoanedAmount()
								+ (long) repayCutAmount));
				content = content.replace("var4",
						DateUtil.simpleFormatYmdhms(new Date()));
				toLoanerMessage.append(content);
				messageService.notifyUser(loaners.get(0),
						toLoanerMessage.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 通知借款人
	 * 
	 * @param trade
	 * @param loan
	 * @param repayCutAmount
	 * @throws Exception
	 */
	private void notifyLoaner4RepayPlan(Trade trade, LoanDemandDO loan,
			double repayCutAmount, RepayPlanInfo repayPlanInfo) {
		List<UserBaseInfoDO> loaners;
		try {
			loaners = userBaseInfoManager.queryByType(null,
					String.valueOf(loan.getLoanerId()));
			StringBuilder toLoanerMessage = new StringBuilder();
			if (loaners != null && loaners.size() > 0) {
				String content = YrdConstants.MessageNotifyConstants.REPAY_NOTIFY_LOANER_CONTENT_4PLAN;
				content = content.replace("var1", trade.getName());
				content = content.replace("var2",
						MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
				content = content.replace("var3", repayPlanInfo.getAmount()
						.toStandardString());
				content = content.replace("var4",
						DateUtil.simpleFormatYmdhms(new Date()));
				content = content.replace("var5", repayPlanInfo.getPeriodNo()
						+ "");
				toLoanerMessage.append(content);
				messageService.notifyUser(loaners.get(0),
						toLoanerMessage.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 每期还款计划金额 : 融资人 ->中间账户(最后一期包含会包含本金) -> 投资人（仅利息）
	 * 
	 * @param trade
	 * @param sysDate
	 * @param repayUser
	 * @param repayPlanInfo
	 * @throws Exception
	 */
	private void repayPeriodProcess(Trade trade, Date sysDate,
			UserBaseInfoDO repayUser, RepayPlanInfo repayPlanInfo)
			throws Exception {

		long tradeId = trade.getId();

		// 中转账号转至每个投资人（利息）
		List<TransferTrade> transferTrades = transferTradeDao
				.getPhaseTransferTrades(tradeId,
						DivisionPhaseEnum.REPAY_PHASE.code(),
						repayPlanInfo.getPeriodNo(),
						SysUserRoleEnum.INVESTOR.getRoleCode());
		for (TransferTrade transferTrade : transferTrades) {
			long transferAmount = transferTrade.getAmount();
			transferService.doTransfer(tradeId,
					transferTrade.getTradeDetailId(),
					transferTrade.getYjfUserName(), repayUser.getAccountId(),
					transferAmount, YrdConstants.TransferComment.YRD_REPAY,
					sysDate, transferTrade.getUserId(), repayUser.getUserId(),
					AmountFlowEnum.AMOUNT_FLOW_REPAY.code());

			tradeDetailDao.updatetActualRepayDate(
					transferTrade.getTradeDetailId(), sysDate);
			tradeDetailDao.updatetStatus(transferTrade.getTradeDetailId(),
					TradeDetailStatusEnum.PS.code());

		}
	}

	private List<TransferTrade> addRepayPrincipalAmountFlow(
			String repayYjfUserName, long repayUserId, long tradeId,
			Date sysDate) throws Exception {
		List<TransferTrade> transferTrades = transferTradeDao
				.getPhaseTransferTrades(tradeId,
						DivisionPhaseEnum.ORIGINAL_PHASE.code(), null,
						new String[]{SysUserRoleEnum.INVESTOR.getRoleCode()});
		for (TransferTrade transferTrade : transferTrades) {
			long transferAmount = transferTrade.getAmount();
			transferService.doTransfer(tradeId,
					transferTrade.getTradeDetailId(),
					transferTrade.getYjfUserName(), repayYjfUserName,
					transferAmount, YrdConstants.TransferComment.YRD_REPAY,
					sysDate, transferTrade.getUserId(), repayUserId,
					AmountFlowEnum.AMOUNT_FLOW_REPAY.code());
			tradeDetailDao.updatetStatus(transferTrade.getTradeDetailId(),
					TradeDetailStatusEnum.PS.code());
		}

		return transferTrades;
	}

	private void addRepayAllMoneyAmountFlow(long repayUserId,
			String repayYjfUserName, Date sysDate, Trade trade, long repayAmount)
			throws Exception {

		long tradeId = trade.getId();
		List<TransferTrade> repTtransitionTrades = transferTradeDao
				.getPhaseTransferTrades(trade.getId(),
						DivisionPhaseEnum.REPAY_TRANSITION_PHASE.code(), null,
						new String[]{SysUserRoleEnum.PLATFORM.getRoleCode()});

		long tradeDetailId = 0;

		if (repTtransitionTrades != null && repTtransitionTrades.size() > 0) {
			if (repTtransitionTrades.size() > 1) {
				logger.info("还款失败，还款业务交易重复！还款人ID--{},还款金额：{}", repayUserId,
						MoneyUtil.getFormatAmount(repayAmount));
				throw new RuntimeException("还款失败，还款业务交易重复！: 还款人ID--"
						+ repayUserId + ",还款金额："
						+ MoneyUtil.getFormatAmount(repayAmount));
			}
			TransferTrade transferTrade = repTtransitionTrades.get(0);
			// 转入中转账户
			tradeDetailId = transferTrade.getTradeDetailId();
			transferService.doTransfer(tradeId, tradeDetailId,
					getYrdYjfExchangeUserName(), repayYjfUserName, repayAmount,
					YrdConstants.TransferComment.YRD_DIVISION, sysDate,
					getYrdExchangeUserId(), repayUserId,
					AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
		} else {
			tradeDetailId = tradeDetailDao.addTradeDetail(new TradeDetail(
			getYrdExchangeUserId(), trade.getId(),
					repayAmount, 7, DivisionPhaseEnum.REPAY_TRANSITION_PHASE
							.code(), null));
			transferService.doTransfer(tradeId, tradeDetailId,
					getYrdYjfExchangeUserName(), repayYjfUserName, repayAmount,
					YrdConstants.TransferComment.YRD_DIVISION, sysDate,
					getYrdExchangeUserId(), repayUserId,
					AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
		}

		tradeDetailDao.updatetStatus(tradeDetailId,
				TradeDetailStatusEnum.PS.code());

	}

	private void addRepayDivisionMoneyAmountFlow(long repayUserId,
			String repayYjfUserName, Date sysDate, Trade trade,
			long divisionAmount) throws Exception {

		long tradeId = trade.getId();
		List<TransferTrade> repTtransitionTrades = transferTradeDao
				.getPhaseTransferTrades(trade.getId(),
						DivisionPhaseEnum.REPAY_TRANSITION_PHASE.code(), null,
						new String[]{SysUserRoleEnum.PLATFORM.getRoleCode()});

		long tradeDetailId = 0;

		if (ListUtil.isEmpty(repTtransitionTrades)) {
			if (repTtransitionTrades.size() > 1) {
				logger.info("还款失败，还款业务交易重复！还款人ID--{},还款金额：{}", repayUserId,
						MoneyUtil.getFormatAmount(divisionAmount));
				throw new RuntimeException("还款失败，还款业务交易重复！: 还款人ID--"
						+ repayUserId + ",还款金额："
						+ MoneyUtil.getFormatAmount(divisionAmount));
			}
			TransferTrade transferTrade = repTtransitionTrades.get(0);
			// 转入中转账户
			tradeDetailId = transferTrade.getTradeDetailId();
			transferService.doTransfer(tradeId, tradeDetailId,
					getYrdYjfExchangeUserName(), repayYjfUserName,
					divisionAmount, YrdConstants.TransferComment.YRD_DIVISION,
					sysDate, getYrdExchangeUserId(), repayUserId,
					AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
		} else {
			tradeDetailId = tradeDetailDao.addTradeDetail(new TradeDetail(
					getYrdExchangeUserId(), trade.getId(), divisionAmount, 7,
					DivisionPhaseEnum.REPAY_TRANSITION_PHASE.code(), null));
			transferService.doTransfer(tradeId, tradeDetailId,
					getYrdYjfExchangeUserName(), repayYjfUserName,
					divisionAmount, YrdConstants.TransferComment.YRD_DIVISION,
					sysDate, getYrdExchangeUserId(), repayUserId,
					AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
		}

		tradeDetailDao.updatetStatus(tradeDetailId,
				TradeDetailStatusEnum.PS.code());

	}

	// 按天计算利率
	private double getDaysRuleRate(double rule, Trade trade) {
		String timeLimitUnit = trade.getTimeLimitUnit();
		double timeLimit = trade.getTimeLimit();
		double days = 0;
		if (LoanLimitUnitEnum.LOAN_BY_DAY.code().equals(timeLimitUnit)) {
			days = timeLimit;
		} else if (LoanLimitUnitEnum.LOAN_BY_YEAR.code().equals(timeLimitUnit)) {
			days = timeLimit * YrdConstants.TimeRelativeConstants.DAYSOFAYEAR;
		} else {
			days = Math.round(timeLimit
					* YrdConstants.TimeRelativeConstants.DAYSOFAYEAR / 12);
		}
		BigDecimal bg = new BigDecimal(rule
				/ YrdConstants.TimeRelativeConstants.DAYSOFAYEAR * days);
		double daysRate = bg.setScale(10, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return daysRate;
	}

	@Override
	public Pagination<UserInvestEntry> getUserInvestEntries(long userId,
			int start, int size, Integer status, Date startDate, Date endDate,
			String code, String loanRealName, String loanUserName,
			Long startAmount, Long endAmount, String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("start", start);
		params.put("size", size);
		if (status != null && status > 0) {
			params.put("status", status);
		}
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("code", code);
		params.put("loanerRealName", loanRealName);
		params.put("loanerUserName", loanUserName);
		params.put("startAmount", startAmount == null ? null
				: startAmount * 100);
		params.put("endAmount", endAmount == null ? null : endAmount * 100);
		params.put("name", name);
		return new Pagination<UserInvestEntry>(start,
				userInvestEntryDao.getCountByProperties(params), size,
				userInvestEntryDao.getByProperties(params));
	}

	@Override
	public Trade getByTradeId(long tradeId) {
		return tradeDao.getByTradeId(tradeId);
	}

	@Override
	public long getAmount(long userId, String roleCode, short... statuses) {
		return tradeDetailDao.getAmount(userId, roleCode, statuses);
	}

	@Override
	public Page<Trade> pageQueryTrade(QueryTradeOrder queryTradeOrder,
			PageParam pageParam) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		if (queryTradeOrder.getUserId() > 0) {
			condition.put("userId", queryTradeOrder.getUserId());
		}

		condition.put("roleId", queryTradeOrder.getRoleId());
		condition.put("tradeCode", queryTradeOrder.getTradeCode());
		condition.put("tradeName", queryTradeOrder.getTradeName());
		condition.put("userName", queryTradeOrder.getUserName());
		condition.put("maiLoanAmount",
				queryTradeOrder.getMaiLoanAmount() == null ? null
						: queryTradeOrder.getMaiLoanAmount() * 100);
		condition.put("maxLoanAmount",
				queryTradeOrder.getMaxLoanAmount() == null ? null
						: queryTradeOrder.getMaxLoanAmount() * 100);
		condition.put("guaranteeName", queryTradeOrder.getGuaranteeName());
		condition.put("sponsorName", queryTradeOrder.getSponsorName());
		if (StringUtil.isBlank(queryTradeOrder.getStartDate())) {
			// String startDate=DateUtil.getWeekdayBeforeNow(new
			// Date())+" 00:00:00";
			// condition.put("maiDemandDate",startDate );
		} else {
			String startDate = queryTradeOrder.getStartDate() + " 00:00:00";
			condition.put("maiDemandDate", startDate);
			queryTradeOrder.setStartDate(queryTradeOrder.getStartDate());
		}
		if (StringUtil.isBlank(queryTradeOrder.getEndDate())) {
			String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
			condition.put("maxDemandDate", endDate);
			queryTradeOrder.setEndDate(DateUtil.simpleFormat(new Date()));
		} else {
			String endDate = queryTradeOrder.getEndDate() + " 23:59:59";
			condition.put("maxDemandDate", DateUtil.parse(endDate));
			queryTradeOrder.setEndDate(queryTradeOrder.getEndDate());
		}
		if (StringUtil.isBlank(queryTradeOrder.getStartExpireDate())) {
			// String startDate=DateUtil.getWeekdayBeforeNow(new
			// Date())+" 00:00:00";
			// condition.put("maiDemandDate",startDate );
		} else {
			String startExpireDate = queryTradeOrder.getStartExpireDate()
					+ " 00:00:00";
			condition.put("maiExpireDate", startExpireDate);
			queryTradeOrder.setStartExpireDate(queryTradeOrder
					.getStartExpireDate());
		}
		if (StringUtil.isBlank(queryTradeOrder.getEndExpireDate())) {
			// 不填就不计算
		} else {
			String endExpireDate = queryTradeOrder.getEndExpireDate()
					+ " 23:59:59";
			condition.put("maxExpireDate", DateUtil.parse(endExpireDate));
			queryTradeOrder
					.setEndExpireDate(queryTradeOrder.getEndExpireDate());
		}
		condition.put("executionDateTime",
				queryTradeOrder.getExecutionDateTime());
		condition.put("effectiveDateTime",
				queryTradeOrder.getEffectiveDateTime());
		if (queryTradeOrder.getStatus() != null
				&& queryTradeOrder.getStatus().size() > 0) {
			if (queryTradeOrder.getStatus().get(0) != "") {
				if (Integer.parseInt(queryTradeOrder.getStatus().get(0)) > 0) {
					condition.put("status", queryTradeOrder.getStatus());
				}
			}
		}
		long totalSize = tradeDao.queryCountByCondition(condition);
		List<Trade> result = tradeDao.queryListByCondition(condition);
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		Page<Trade> page = new Page<Trade>(start, totalSize,
				pageParam.getPageSize(), result);
		return page;
	}

	@Override
	public long getAllLoanedAmountByOrder(QueryTradeOrder queryTradeOrder) {

		Map<String, Object> condition = new HashMap<String, Object>();
		if (queryTradeOrder.getUserId() > 0) {
			condition.put("userId", queryTradeOrder.getUserId());
		}
		condition.put("roleId", queryTradeOrder.getRoleId());
		condition.put("tradeCode", queryTradeOrder.getTradeCode());
		condition.put("userName", queryTradeOrder.getUserName());
		condition.put("maiLoanAmount",
				queryTradeOrder.getMaiLoanAmount() == null ? null
						: queryTradeOrder.getMaiLoanAmount() * 100);
		condition.put("maxLoanAmount",
				queryTradeOrder.getMaxLoanAmount() == null ? null
						: queryTradeOrder.getMaxLoanAmount() * 100);
		condition.put("guaranteeName", queryTradeOrder.getGuaranteeName());
		condition.put("sponsorName", queryTradeOrder.getSponsorName());
		if (StringUtil.isBlank(queryTradeOrder.getStartDate())) {
			// String startDate=DateUtil.getWeekdayBeforeNow(new
			// Date())+" 00:00:00";
			// condition.put("maiDemandDate",startDate );
		} else {
			String startDate = queryTradeOrder.getStartDate() + " 00:00:00";
			condition.put("maiDemandDate", startDate);
			queryTradeOrder.setStartDate(queryTradeOrder.getStartDate());
		}
		if (StringUtil.isBlank(queryTradeOrder.getEndDate())) {
			String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
			condition.put("maxDemandDate", endDate);
			queryTradeOrder.setEndDate(DateUtil.simpleFormat(new Date()));
		} else {
			String endDate = queryTradeOrder.getEndDate() + " 23:59:59";
			condition.put("maxDemandDate", DateUtil.parse(endDate));
			queryTradeOrder.setEndDate(queryTradeOrder.getEndDate());
		}
		condition.put("executionDateTime",
				queryTradeOrder.getExecutionDateTime());
		condition.put("effectiveDateTime",
				queryTradeOrder.getEffectiveDateTime());
		if (queryTradeOrder.getStatus() != null
				&& queryTradeOrder.getStatus().size() > 0) {
			if (queryTradeOrder.getStatus().get(0) != "") {
				if (Integer.parseInt(queryTradeOrder.getStatus().get(0)) > 0) {
					condition.put("status", queryTradeOrder.getStatus());
				}
			}
		}
		return tradeDao.getAllLoandAmount(condition);
	}

	@Override
	public List<UserInvestEntry> getEntriesByTradeId(long tradeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (tradeId > 0) {
			params.put("tradeId", tradeId);
		}
		return userInvestEntryDao.getEntriesByTradeParams(params);
	}

	@Override
	public List<TradeDetail> getByTradeIdAndRoles(long tradeId, String... roles) {
		List<TradeDetail> tradeDetails = tradeDetailDao.getByTradeIdAndRoles(
				tradeId, roles);
		return tradeDetails;
	}

	@Override
	public boolean check(long tradeId, long userId, String role) {
		return tradeDetailDao.check(tradeId, userId, role) > 0;
	}

	@Override
	public void addEffectiveDateTime(Long tradeId) {
		Date expireDateTime = null;
		Trade trade = getByTradeId(tradeId);
		String time_limit_unit = trade.getTimeLimitUnit();
		int timeLimit = trade.getTimeLimit();
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		if (LoanLimitUnitEnum.LOAN_BY_DAY.code().equals(time_limit_unit)) {
			calendar.add(Calendar.DATE, timeLimit);
			expireDateTime = calendar.getTime();
		} else if (LoanLimitUnitEnum.LOAN_BY_MONTH.code().equals(
				time_limit_unit)) {
			calendar.add(Calendar.MONTH, timeLimit);
			expireDateTime = calendar.getTime();

		} else if (LoanLimitUnitEnum.LOAN_BY_PEROIDW.code().equals(
				time_limit_unit)) {
			calendar.add(Calendar.MONTH, timeLimit);
			expireDateTime = calendar.getTime();

		} else if (LoanLimitUnitEnum.LOAN_BY_YEAR.code()
				.equals(time_limit_unit)) {
			calendar.add(Calendar.YEAR, timeLimit);
			expireDateTime = calendar.getTime();
		}
		// 指定标准时间 暂不实施
		// String standardExpireDateTime =
		// DateUtil.simpleFormat(expireDateTime)+" 17:00:00";
		// expireDateTime = DateUtil.parse(standardExpireDateTime);
		tradeDao.addEffectiveDateTime(tradeId, expireDateTime);
	}

	@Override
	public Page<Trade> pageQueryTradeForJob(QueryTradeOrder queryTradeOrder,
			PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		if (queryTradeOrder.getUserId() > 0) {
			condition.put("userId", queryTradeOrder.getUserId());
		}
		condition.put("roleId", queryTradeOrder.getRoleId());
		condition.put("tradeCode", queryTradeOrder.getTradeCode());
		condition.put("userName", queryTradeOrder.getUserName());
		condition.put("maiLoanAmount",
				queryTradeOrder.getMaiLoanAmount() == null ? null
						: queryTradeOrder.getMaiLoanAmount() * 100);
		condition.put("maxLoanAmount",
				queryTradeOrder.getMaxLoanAmount() == null ? null
						: queryTradeOrder.getMaxLoanAmount() * 100);
		condition.put("guaranteeName", queryTradeOrder.getGuaranteeName());
		condition.put("sponsorName", queryTradeOrder.getSponsorName());
		condition.put("executionDateTime",
				queryTradeOrder.getExecutionDateTime());
		condition.put("effectiveDateTime",
				queryTradeOrder.getEffectiveDateTime());
		condition.put("expireDateTime", queryTradeOrder.getExpireDateTime());
		condition.put("status", queryTradeOrder.getStatus());
		condition
				.put("repayDivisionWay", queryTradeOrder.getRepayDivisionWay());

		long totalSize = tradeDao.queryCountByConditionForJob(condition);
		List<Trade> result = tradeDao.queryListByConditionForJob(condition);
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		Page<Trade> page = new Page<Trade>(start, totalSize,
				pageParam.getPageSize(), result);
		return page;
	}

	/**
	 * 查询截止时间未满标的业务和当前已满标的业务(用于发通知消息)
	 */
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void executeOverDueLoanDemandProcessJob() {
		// 查询已满标的项目发送通知消息
		executeNotifyLoanDemandFullJob();
		QueryTradeOrder queryTradeOrder = new QueryTradeOrder();
		queryTradeOrder.setRoleId(13L);
		// queryTradeOrder.setExecutionDateTime(DateUtil.simpleFormatYmdhms(new
		// Date()));
		List<String> status = new ArrayList<String>();
		status.add(String.valueOf(YrdConstants.TradeStatus.TRADING));
		queryTradeOrder.setStatus(status);
		PageParam pageParam = new PageParam();
		pageParam.setPageSize(99999);
		pageParam.setPageNo(1);
		Page<Trade> page = null;
		try {
			page = pageQueryTradeForJob(queryTradeOrder, pageParam);
			if (page.getResult() != null && page.getResult().size() > 0) {
				for (int i = 0; i < page.getResult().size(); i++) {
					Trade trade = page.getResult().get(i);
					if ((trade.getLoanedAmount() > 0
							&& divisionService.isFullScale(trade) && trade
							.getDeadline().getTime() < (new Date()).getTime())
							|| trade.getAmount() == trade.getLoanedAmount()) {
						logger.info("tradeId为" + trade.getId()
								+ "的交易达到满标状态,将转入担保机构审核状态！");
						try {
							logger.info("tradeId为" + trade.getId()
									+ "确认融资完成创建其他角色的子交易...");
							createRelativeTrades(trade);
						} catch (Exception e) {
							logger.error(
									"查询到期满标交易更改交易状态异常---tradeid:："
											+ trade.getId() + "---异常原因："
											+ e.getMessage() + "---异常时间："
											+ new Date().toString(), e);
						}
					} else if (trade.getDeadline().getTime() < (new Date())
							.getTime()) {
						logger.info("tradeId为" + trade.getId()
								+ "的交易未达到满标状态,需转入未成立状态！");
						try {
							denyloanDemandUnfull(trade);
						} catch (Exception e) {
							logger.error(
									"查询到期未满标交易更改交易状态异常---tradeid:："
											+ trade.getId() + "---异常原因："
											+ e.getMessage() + "---异常时间："
											+ new Date().toString(), e);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询到期未满标任务异常时间：" + new Date().toString());
		}
	}

	private void executeNotifyLoanDemandFullJob() {
		logger.info("查询满标状态借款发通知,开始！");
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("roleId", 13L);
			List<String> status = new ArrayList<String>();
			status.add(String.valueOf(YrdConstants.TradeStatus.TRADING));
			condition.put("status", status);
			condition.put("isNotifiedLoaner",
					YrdConstants.MessageNotifyConstants.ISNOTIFIED_NO);
			condition.put("limitStart", 0);
			condition.put("pageSize", 99999);
			List<Trade> result = tradeDao.queryListByConditionForJob(condition);
			if (result != null && result.size() > 0) {
				for (Trade trade : result) {
					if ((trade.getLoanedAmount() > 0
							&& divisionService.isFullScale(trade) && trade
							.getDeadline().getTime() < (new Date()).getTime())
							|| trade.getAmount() == trade.getLoanedAmount()) {
						LoanDemandDO loanDemand = loanDemandManager
								.queryLoanDemandByDemandId(trade.getDemandId());
						long loanerUserId = loanDemand.getLoanerId();
						// 通知借款人
						List<UserBaseInfoDO> loaner = userBaseInfoManager
								.queryByType(null, String.valueOf(loanerUserId));
						if (loaner != null) {
							StringBuilder loanMessage = new StringBuilder();
							loanMessage
									.append("您有一笔融资,交易名称：" + trade.getName())
									.append(",申请融资金额：￥"
											+ MoneyUtil.getFormatAmount(trade
													.getAmount())
											+ "元,已融资金额：￥"
											+ MoneyUtil.getFormatAmount(trade
													.getLoanedAmount()))
									.append("元,")
									.append("已达到满标状态，请及时登录"
											+ AppConstantsUtil.getProductName()
											+ "金融平台hostLink进行核实,如有任何疑问,详询"
											+ AppConstantsUtil.getProductName()
											+ "金融客服！");
							messageService.notifyUser(loaner.get(0),
									loanMessage.toString());
						}
						tradeDao.updateIsNotifiedLoaner(
								trade.getId(),
								YrdConstants.MessageNotifyConstants.ISNOTIFIED_YES);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询满标借款任务异常！", e);
		}
		logger.info("查询满标借款任务结束时间：" + new Date().toString());

	}

	@Override
	public List<Trade> getTradesByCondition(Map<String, Object> newCondition) {

		return tradeDao.queryListByCondition(newCondition);
	}

	@Override
	public void updateTradeDetailForOrg(int roleId, long tradeId, double amount) {
		tradeDao.updateTradeDetailForOrg(roleId, tradeId, amount);
	}

	public void setLoanDemandManager(LoanDemandManager loanDemandManager) {
		this.loanDemandManager = loanDemandManager;
	}

	public void setUserBaseInfoManager(UserBaseInfoManager userBaseInfoManager) {
		this.userBaseInfoManager = userBaseInfoManager;
	}

	public void setDivisionDetailDao(DivisionDetailDao divisionDetailDao) {
		this.divisionDetailDao = divisionDetailDao;
	}

	public long getYrdUserId() throws Exception {
		initializeYrdAccountInfo();
		return yrdUserId;
	}

	public long getYrdExchangeUserId() throws Exception {
		initializeYrdAccountInfo();
		return yrdExchangeUserId;
	}

	public String getYrdYjfExchangeUserName() throws Exception {
		initializeYrdAccountInfo();
		return yrdYjfExchangeUserName;
	}

	public void setYrdUserId(long yrdUserId) {
		this.yrdUserId = yrdUserId;
	}

	public void setYrdExchangeUserId(long yrdExchangeUserId) {
		this.yrdExchangeUserId = yrdExchangeUserId;
	}

	public void setYrdYjfExchangeUserName(String yrdYjfExchangeUserName) {
		this.yrdYjfExchangeUserName = yrdYjfExchangeUserName;
	}

	@Override
	public Page<AgentInvestorTrade> pageQueryBrokerInvestorTrade(
			QueryTradeOrder queryTradeOrder, PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userId", queryTradeOrder.getUserId());
		condition.put("roleId", queryTradeOrder.getRoleId());
		condition.put("tradeCode", queryTradeOrder.getTradeCode());
		condition.put("userName", queryTradeOrder.getUserName());
		if (!StringUtil.isBlank(queryTradeOrder.getStartDate())) {
			String startDate = queryTradeOrder.getStartDate() + " 00:00:00";
			condition.put("maiDemandDate", startDate);
			queryTradeOrder.setStartDate(startDate);
		}
		if (StringUtil.isBlank(queryTradeOrder.getEndDate())) {
			String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
			condition.put("maxDemandDate", endDate);
			queryTradeOrder.setEndDate(DateUtil.simpleFormat(new Date()));
		} else {
			String endDate = queryTradeOrder.getEndDate() + " 23:59:59";
			condition.put("maxDemandDate", endDate);
		}
		condition.put("status", queryTradeOrder.getStatus());
		long totalSize = tradeDao.queryAgentInvestorCountByCondition(condition);
		List<AgentInvestorTrade> result = tradeDao
				.queryAgentInvestorListByCondition(condition);
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		Page<AgentInvestorTrade> page = new Page<AgentInvestorTrade>(start,
				totalSize, pageParam.getPageSize(), result);
		return page;
	}

	@Override
	public List<TradeQueryDetail> getTradeDetailByConditions(
			Map<String, Object> conditions) {

		return tradeDetailDao.getTradeDetailByConditions(conditions);
	}

	@Override
	public Page<AgentLoanerTrade> pageQueryAgencyLoanerTrade(
			QueryTradeOrder queryConditions, PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		condition.put("userId", queryConditions.getUserId());
		condition.put("roleId", queryConditions.getRoleId());
		condition.put("tradeCode", queryConditions.getTradeCode());
		condition.put("userName", queryConditions.getUserName());
		if (!StringUtil.isBlank(queryConditions.getStartDate())) {
			String startDate = queryConditions.getStartDate() + " 00:00:00";
			condition.put("maiDemandDate", startDate);
			queryConditions.setStartDate(startDate);
		}
		if (StringUtil.isBlank(queryConditions.getEndDate())) {
			String endDate = DateUtil.simpleFormatYmdhms(new Date())
					+ " 23:59:59";
			condition.put("maxDemandDate", endDate);
			queryConditions.setEndDate(endDate);
		} else {
			String endDate = queryConditions.getEndDate() + " 23:59:59";
			condition.put("maxDemandDate", DateUtil.parse(endDate));
		}
		condition.put("status", queryConditions.getStatus());
		long totalSize = tradeDao.queryAgencyLoanerCountByCondition(condition);
		List<AgentLoanerTrade> result = tradeDao
				.queryAgencyLoanerListByCondition(condition);
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		Page<AgentLoanerTrade> page = new Page<AgentLoanerTrade>(start,
				totalSize, pageParam.getPageSize(), result);
		return page;
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void executeRepayAdvanceNotificationJob() throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("roleId", SysUserRoleEnum.LOANER.getValue());
		List<String> status = new ArrayList<String>();
		status.add(String.valueOf(YrdConstants.TradeStatus.REPAYING));
		condition.put("status", status);
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, Integer.parseInt(advancedNotifyDay));
		Date expireDateTime = calendar.getTime();
		condition.put("expireDateTime", expireDateTime);
		condition.put("isNotifiedLoaner",
				YrdConstants.MessageNotifyConstants.ISNOTIFIED_NO);
		condition.put("limitStart", 0);
		condition.put("pageSize", 1);
		List<Trade> result = tradeDao.queryListByConditionForJob(condition);
		if (result != null && result.size() > 0) {
			for (Trade trade : result) {
				LoanDemandDO loanDemand = loanDemandManager
						.queryLoanDemandByDemandId(trade.getDemandId());
				long loanerUserId = loanDemand.getLoanerId();
				// 通知借款人
				List<UserBaseInfoDO> loaner = userBaseInfoManager.queryByType(
						null, String.valueOf(loanerUserId));
				if (loaner != null) {
					StringBuilder loanMessage = new StringBuilder();
					loanMessage
							.append("您有一笔借款,借款名称：" + trade.getName())
							.append(",借款金额：￥"
									+ MoneyUtil.getFormatAmount(trade
											.getLoanedAmount()))
							.append("元,将于")
							.append(DateUtil.simpleFormat(trade
									.getExpireDateTime()))
							.append("到期，请及时处理还款，如果你还未还款系统将自动为您扣款，请于扣款日中午12点前存入足够资金，以免扣款失败。具体金额以实际扣款为准,请登录：hostLink进行核实,如有任何疑问,详询"
									+ AppConstantsUtil.getProductName()
									+ "金融客服！");
					messageService.notifyUser(loaner.get(0),
							loanMessage.toString());
					// 通知运营人员
					StringBuilder message = new StringBuilder();
					String content = YrdConstants.MessageNotifyConstants.REPAY_ADVANCED_NOTIFY;
					UserBaseInfoDO toUser = new UserBaseInfoDO();
					content = content.replace("var1", trade.getName());
					content = content.replace("var2",
							MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
					content = content.replace("var3", DateUtil
							.simpleFormatYmdhms(trade.getExpireDateTime()));
					message.append(content);

					setToUserDetail(toUser);
					messageService.notifyUser(toUser, message.toString());
				}
				tradeDao.updateIsNotifiedLoaner(trade.getId(),
						YrdConstants.MessageNotifyConstants.ISNOTIFIED_YES);
			}
		}
	}

	/**
	 *
	 * @param toUser
	 */
	protected void setToUserDetail(UserBaseInfoDO toUser) {
		toUser.setRealName("运营001号");
		toUser.setMail(AppConstantsUtil.getCustomerServiceEmail());
		toUser.setMobile(AppConstantsUtil.getCustomerServiceMobile());
	}

	@Override
	public List<TradeDivisionDetailVO> getDevisionDetailsByTradeId(long tradeId)
			throws Exception {
		Trade trade = getByTradeId(tradeId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		long loanerId = loan.getLoanerId();
		List<UserBaseInfoDO> loaners = userBaseInfoManager.queryByType(null,
				String.valueOf(loanerId));
		UserBaseInfoDO loaner = null;
		if (loaners != null && loaners.size() > 0) {
			loaner = loaners.get(0);
		} else {
			throw new Exception("can not find loaner user");
		}
		List<TradeDivisionDetailVO> tradeDivisionDetailVO = new ArrayList<TradeDivisionDetailVO>();
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tradeId", tradeId);
		List<TradeQueryDetail> tradeDetails = tradeDetailDao
				.getTradeDetailByConditions(conditions);
		for (TradeDetail tradeDetail : tradeDetails) {
			if (DivisionPhaseEnum.INVESET_PHASE.code().equals(
					tradeDetail.getTransferPhase())
					|| DivisionPhaseEnum.REPAY_PHASE.code().equals(
							tradeDetail.getTransferPhase())) {
				TradeDivisionDetailVO detailVo = new TradeDivisionDetailVO(
						tradeDetail);
				detailVo.setLoanerId(loanerId);
				detailVo.setLoanerName(loaner.getRealName());
				Role role = roleDao.getByRoleId(tradeDetail.getRoleId());
				detailVo.setRoleName(role.getName());
				detailVo.setStatus(getNormalTradeStatus(trade));
				tradeDivisionDetailVO.add(detailVo);
			}
		}
		return tradeDivisionDetailVO;
	}

	/**
	 * 获取显示状态
	 * 
	 * @param trade
	 * @return
	 */
	public static String getNormalTradeStatus(Trade trade) {
		String tradeStatus = YrdConstants.TradeViewCanstants.TRADE_DEFAULT;
		switch (trade.getStatus()) {
		case YrdConstants.TradeStatus.TRADING:
			tradeStatus = YrdConstants.TradeViewCanstants.TRADE_TRADING;
			break;
		case YrdConstants.TradeStatus.FAILED:
			tradeStatus = YrdConstants.TradeViewCanstants.TRADE_FAILD;
			break;
		case YrdConstants.TradeStatus.REPAY_FINISH:
			tradeStatus = YrdConstants.TradeViewCanstants.TRADE_FINISH;
			break;
		case YrdConstants.TradeStatus.REPAYING_FAILD:
			tradeStatus = YrdConstants.TradeViewCanstants.TRADE_REPAY_FAILD;
			break;
		case YrdConstants.TradeStatus.REPAYING:
			tradeStatus = YrdConstants.TradeViewCanstants.TRADE_REPAYING;
			break;
		case YrdConstants.TradeStatus.GUARANTEE_AUDITING:
			tradeStatus = YrdConstants.TradeViewCanstants.GUARANTEE_AUDITING;
			break;
		case YrdConstants.TradeStatus.DOREPAY:
			tradeStatus = YrdConstants.TradeViewCanstants.DOREPAY;
			break;
		case YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH:
			tradeStatus = YrdConstants.TradeViewCanstants.COMPENSATORY_REPAY_FINISH;
			break;
		}
		return tradeStatus;
	}

	@Override
	public List<AmounFlowVO> getTradeFlowByTradeId(long tradeId)
			throws Exception {
		List<AmountFlowTrade> flowTrades = amountFlowDao
				.getAmountFlowTradesByTradeId(tradeId);
		List<AmounFlowVO> amFlowVos = new ArrayList<AmounFlowVO>();
		if (flowTrades != null && flowTrades.size() > 0) {
			for (AmountFlowTrade flowTrade : flowTrades) {
				AmountFlow amFlow = amountFlowDao
						.getAmountFlowByFlowId(flowTrade.getAmountFlowId());
				AmounFlowVO amFlowVo = new AmounFlowVO(amFlow.getFlowCode(),
						amFlow.getAmountOut(), amFlow.getAmountIn(),
						amFlow.getAmount(), amFlow.getStatus(),
						amFlow.getNote(), amFlow.getDate(),
						amFlow.getInUserId(), amFlow.getOutUserId(),
						amFlow.getType());
				amFlowVo.setFlowType(AmountFlowEnum.AMOUNT_FLOW_TYPE_NORMAL
						.code());
				List<UserBaseInfoDO> inners = userBaseInfoManager.queryByType(
						null, String.valueOf(amFlow.getInUserId()));
				UserBaseInfoDO inner = null;
				if (inners != null && inners.size() > 0) {
					inner = inners.get(0);
				} else {
					throw new Exception("can not find loaner user");
				}
				List<UserBaseInfoDO> outers = userBaseInfoManager.queryByType(
						null, String.valueOf(amFlow.getOutUserId()));
				UserBaseInfoDO outer = null;
				if (outers != null && outers.size() > 0) {
					outer = outers.get(0);
				} else {
					throw new Exception("can not find loaner user");
				}
				amFlowVo.setInUserName(inner.getRealName());
				amFlowVo.setOutUserName(outer.getRealName());
				amFlowVos.add(amFlowVo);
			}
		}
		return amFlowVos;
	}

	@Override
	public List<AmounFlowVO> getDivisionFlowByTradeId(long tradeId)
			throws Exception {
		List<DivisionDetail> flowTrades = divisionDetailDao
				.getByTradeIdAndRoles(tradeId, (String[]) null);
		List<AmounFlowVO> amFlowVos = new ArrayList<AmounFlowVO>();
		if (flowTrades != null && flowTrades.size() > 0) {
			for (DivisionDetail flowTrade : flowTrades) {
				String flowAmountIn = null;
				List<UserBaseInfoDO> inners = userBaseInfoManager.queryByType(
						null, String.valueOf(flowTrade.getUserId()));
				UserBaseInfoDO inner = null;
				if (inners != null && inners.size() > 0) {
					inner = inners.get(0);
				} else {
					throw new Exception("can not find loaner user");
				}
				flowAmountIn = inner.getAccountId();
				AmounFlowVO amFlowVo = new AmounFlowVO(
						flowTrade.getBusinessCode(),
						getYrdYjfExchangeUserName(), flowAmountIn,
						flowTrade.getAmount(), flowTrade.getStatus(),
						YrdConstants.TransferComment.YRD_DIVISION,
						flowTrade.getDate(), flowTrade.getUserId(),
						getYrdExchangeUserId(),
						AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
				amFlowVo.setFlowType(AmountFlowEnum.AMOUNT_FLOW_TYPE_DIVISION
						.code());

				List<UserBaseInfoDO> outers = userBaseInfoManager.queryByType(
						null, String.valueOf(getYrdExchangeUserId()));
				UserBaseInfoDO outer = null;
				if (outers != null && outers.size() > 0) {
					outer = outers.get(0);
				} else {
					throw new Exception("can not find loaner user");
				}
				amFlowVo.setInUserName(inner.getRealName());
				amFlowVo.setOutUserName(outer.getRealName());
				amFlowVos.add(amFlowVo);
			}
		}
		return amFlowVos;
	}

	@Override
	public List<UserInvestEntry> getEntriesByTradeIdAndDetailId(long tradeId,
			long detailId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (tradeId > 0) {
			params.put("tradeId", tradeId);
		}
		if (detailId > 0) {
			params.put("detailId", detailId);
		}
		return userInvestEntryDao.getEntriesByTradeParams(params);
	}

	private void initializeYrdAccountInfo() throws Exception {

		if (!isInitAccountInfo) {
			UserBaseInfoDO exchangeUser = userBaseInfoManager
					.queryByAccountId(AppConstantsUtil.getExchangeAccount());
			UserBaseInfoDO financeUser = userBaseInfoManager
					.queryByAccountId(AppConstantsUtil
							.getProfitSharingAccount());
			this.yrdExchangeUserId = exchangeUser.getUserId();
			this.yrdYjfExchangeUserName = exchangeUser.getAccountId();
			this.yrdUserId = (financeUser.getUserId());
			isInitAccountInfo = true;
		}
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void executeAutoChangeExpireLoanJob() {
		QueryTradeOrder queryTradeOrder = new QueryTradeOrder();
		queryTradeOrder.setRoleId(13L);
		List<String> status = new ArrayList<String>();
		status.add(String.valueOf(YrdConstants.TradeStatus.REPAYING));
		queryTradeOrder.setExpireDateTime(DateUtil
				.simpleFormatYmdhms(new Date()));
		queryTradeOrder.setStatus(status);
		PageParam pageParam = new PageParam();
		pageParam.setPageSize(99999);
		pageParam.setPageNo(1);
		Page<Trade> page = null;
		try {
			page = pageQueryTradeForJob(queryTradeOrder, pageParam);
			if (page.getResult() != null && page.getResult().size() > 0) {
				for (int i = 0; i < page.getResult().size(); i++) {
					Trade trade = page.getResult().get(i);
					long demandId = trade.getDemandId();
					List<UserInvestEntry> userInvestEntry = getEntriesByTradeId(trade
							.getId());
					if (userInvestEntry != null && userInvestEntry.size() > 0) {
						long loanerUserId = userInvestEntry.get(0)
								.getLoanerId();
						try {
							logger.error("借款到期日自动转入待还款：[demandId={}]", demandId);
							modifyStatus(trade.getId(),
									YrdConstants.TradeStatus.DOREPAY);
							// 通知借款人
							List<UserBaseInfoDO> loaner = userBaseInfoManager
									.queryByType(null,
											String.valueOf(loanerUserId));
							if (loaner != null) {
								StringBuilder loanMessage = new StringBuilder();
								String content = YrdConstants.MessageNotifyConstants.AUTO_CHANGE_EXPIRELOANTODOREPAY_CONTENT;
								content = content.replace("var1",
										trade.getName());
								content = content.replace("var2", MoneyUtil
										.getFormatAmount(trade
												.getLoanedAmount()));
								loanMessage.append(content);
								messageService.notifyUser(loaner.get(0),
										loanMessage.toString());
							}
						} catch (Exception e) {
							logger.error("借款到期日自动转入待还款失败", e);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询借款到期日自动转入待还款失败", e);
		}
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void createRelativeTrades(Trade trade) throws Exception {

		trade = tradeDao.getByTradeIdWithRowLock(trade.getId());
		// 修改状态
		modifyStatus(trade.getId(), YrdConstants.TradeStatus.GUARANTEE_AUDITING);
		// 重置通知消息业务状态
		tradeDao.updateIsNotifiedLoaner(trade.getId(),
				YrdConstants.MessageNotifyConstants.ISNOTIFIED_NO);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
				.getByBaseId(divisionTemplateLoanBaseId);
		List<DivsionRuleRole> investRolelist = divisionService
				.getRuleRole(String.valueOf(divisionTemplateLoan
						.getInvestTemplateId()));
		// 投资成立中转金额
		double countInvestCutAmount = 0;
		if (investRolelist != null && investRolelist.size() > 0) {
			for (DivsionRuleRole roleRule : investRolelist) {
				if (DivisionPhaseEnum.INVESET_PHASE.code().equals(
						roleRule.getPhase())) {
					BigDecimal bg = new BigDecimal(getDaysRuleRate(
							roleRule.getRule(), trade));
					double rate = bg.setScale(10, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					countInvestCutAmount += Math.round(rate
							* trade.getLoanedAmount());
				}
			}
		}
		tradeDetailDao.addTradeDetail(new TradeDetail(
		getYrdExchangeUserId(), trade.getId(),
				(long) countInvestCutAmount, SysUserRoleEnum.PLATFORM.getValue(),
				DivisionPhaseEnum.INVESET_TRANSITION_PHASE.code(), null));
		/** 计算还款应还款总额 */
		List<DivsionRuleRole> repayRolelist = divisionService
				.getRuleRole(String.valueOf(divisionTemplateLoan
						.getRepayTemplateId()));
		double repayCutAmount = 0;
		if (repayRolelist != null && repayRolelist.size() > 0) {
			for (DivsionRuleRole druleRole : repayRolelist) {
				if (DivisionPhaseEnum.REPAY_PHASE.code().equals(
						druleRole.getPhase())) {
					BigDecimal bg = new BigDecimal(getDaysRuleRate(
							druleRole.getRule(), trade));
					repayCutAmount += Math.round(trade.getLoanedAmount()
							* bg.setScale(10, BigDecimal.ROUND_HALF_UP)
									.doubleValue());
				}
			}
		}
		long repayAmount = trade.getLoanedAmount() + (long) repayCutAmount;
		tradeDetailDao.addTradeDetail(new TradeDetail(
		getYrdExchangeUserId(), trade.getId(), repayAmount,
				SysUserRoleEnum.PLATFORM.getValue(),
				DivisionPhaseEnum.REPAY_TRANSITION_PHASE.code(), null));
		// 计算担保公司，平台，保荐机构费用
		investRolelist.addAll(repayRolelist);
		if (investRolelist != null && investRolelist.size() > 0) {
			// 添加机构交易
			for (DivsionRuleRole roleRule : investRolelist) {
				logger.info("getGuaranteeRoleId=" + getGuaranteeRoleId()
						+ " roleRule=" + roleRule.getRoleId());
				if (getGuaranteeRoleId().equals(
						String.valueOf(roleRule.getRoleId()))) {
					BigDecimal bg = new BigDecimal(getDaysRuleRate(
							roleRule.getRule(), trade)
							* trade.getLoanedAmount());
					double amount = bg.setScale(10, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					amount = Math.floor(amount);
					tradeDetailDao.addTradeDetail(new TradeDetail(loan
							.getGuaranteeId(), trade.getId(), (long) amount, 8,
							roleRule.getPhase(), "")); // loan.getGuaranteeStatement()
														// //太长会数据插入异常 直接不要
				} else if (getSponsorRoleId().equals(
						String.valueOf(roleRule.getRoleId()))) {
					BigDecimal bg = new BigDecimal(getDaysRuleRate(
							roleRule.getRule(), trade)
							* trade.getLoanedAmount());
					double amount = bg.setScale(10, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					amount = Math.floor(amount);
					tradeDetailDao.addTradeDetail(new TradeDetail(loan
							.getSponsorId(), trade.getId(), (long) amount, 9,
							roleRule.getPhase(), "")); // loan.getGuaranteeStatement()
														// //太长会数据插入异常 直接不要
				} else if (getPlatformRoleId().equals(
						String.valueOf(roleRule.getRoleId()))) {
					BigDecimal bg = new BigDecimal(getDaysRuleRate(
							roleRule.getRule(), trade)
							* trade.getLoanedAmount());
					double amount = bg.setScale(10, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					amount = Math.round(amount);
					tradeDetailDao.addTradeDetail(new TradeDetail(
					getYrdUserId(), trade.getId(),
							(long) amount, 7, roleRule.getPhase(), null));
				}
			}
		}

		// 修改状态
		modifyStatus(trade.getId(), YrdConstants.TradeStatus.GUARANTEE_AUDITING);

		if (sysFunctionConfigService.isTradeFeeCharge()) {
		}

		// 通知运营人员
		StringBuilder message = new StringBuilder();
		String content = YrdConstants.MessageNotifyConstants.DEMAND_CONFIRMED_NOTIFY;
		UserBaseInfoDO toUser = new UserBaseInfoDO();
		content = content.replace("var1", trade.getName());
		content = content.replace("var2",
				MoneyUtil.getFormatAmount(trade.getAmount()));
		content = content.replace("var3",
				MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
		message.append(content);
		setToUserDetail(toUser);
		messageService.notifyUser(toUser, message.toString());
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public Trade getByTradeIdWithRowLock(long tradeId) {
		return tradeDao.getByTradeIdWithRowLock(tradeId);
	}

	@Override
	public long addRechargeFlow(RechargeFlow rechargeFlow) throws Exception {
		if (rechargeFlow == null) {
			throw new Exception("can not find rechargeFlow");
		} else {
			return tradeDao.addRechargeFlow(rechargeFlow);
		}

	}

	@Override
	public List<RechargeFlow> getFlow(String payType, int status,
			Date startTime, Date endTime) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("payType", payType);
		condition.put("status", status);
		condition.put("startTime", startTime);
		condition.put("endTime", endTime);
		return tradeDao.getFlowList(condition);
	}

	@Override
	public RechargeFlow queryByOutBizNo(String outBizNo) {
		RechargeFlow rechargeFlow = tradeDao.getFlowByBizNo(outBizNo);
		return rechargeFlow;
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int updateStatus(RechargeFlow rechargeFlow) {
		int status = 0;
		try {
			status = tradeDao.updateStatus(rechargeFlow);
		} catch (Exception e) {
			logger.error("更新充值或提现流水异常", e);
		}
		return status;
	}

	@Override
	public double getUserSumAmount(RechercheFlowOrder rechercheFlowOrder) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("bankAccountNo", rechercheFlowOrder.getBankCardNo()
				.trim());
		condition.put("payType", rechercheFlowOrder.getPayType().trim());
		condition.put("endTime", rechercheFlowOrder.getEndTime().trim());
		condition.put("startTime", rechercheFlowOrder.getStartTime());
		condition.put("status", rechercheFlowOrder.getStatus());
		condition.put("userId", rechercheFlowOrder.getUserId());
		return tradeDao.getUserAmount(condition);
	}

	@Override
	public Page<RechargeFlow> getFlow(QueryTradeOrder queryTradeOrder,
			PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("payType", queryTradeOrder.getPayType());
		if (queryTradeOrder.getStatus() != null
				&& queryTradeOrder.getStatus().size() > 0) {
			if (queryTradeOrder.getStatus().get(0) != "") {
				if (Integer.parseInt(queryTradeOrder.getStatus().get(0)) >= 0) {
					condition.put("status", queryTradeOrder.getStatus().get(0));
				}
			}
		}
		condition.put("startTime", queryTradeOrder.getStartDate());
		condition.put("endTime", queryTradeOrder.getEndDate());
		condition.put("accountId", queryTradeOrder.getAccountId());
		long totalSize = tradeDao.getFlowCount(condition);
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		condition.put("limitStart", start);
		condition.put("pageSize", pageParam.getPageSize());
		List<RechargeFlow> result = tradeDao.getFlowList(condition);
		List<RechargeFlow> resultList = new ArrayList<RechargeFlow>();
		if (ListUtil.isNotEmpty(result)) {
			for (RechargeFlow flow : result) {
				String bankAccountNo = flow.getBankAccountNo();
				if (StringUtil.isNotEmpty(bankAccountNo)) {
					if (bankAccountNo.length() > 4) {
						bankAccountNo = bankAccountNo.substring(0, 4)
								+ "********"
								+ bankAccountNo.substring(
										bankAccountNo.length() - 4,
										bankAccountNo.length());
					}
				}
				flow.setBankAccountNo(bankAccountNo);
				resultList.add(flow);
			}
		}

		// Page<Trade> page = new Page<Trade>(start,
		// totalSize,pageParam.getPageSize(), result);
		Page<RechargeFlow> page = new Page<RechargeFlow>(start, totalSize,
				pageParam.getPageSize(), resultList);
		return page;
	}

	@Override
	public long countInvestedTransactions(long tradeId) {

		return tradeDao.countInvestedTransactions(tradeId);
	}

	@Override
	public void addTradeFlowCode(TradeFlowCode tradeFlow) {
		tradeDao.addTradeFlowCode(tradeFlow);
	}

	@Override
	public List<String> queryInvestFlowCodesByTradeId(long tradeId) {
		List<String> investFlowCodes = new ArrayList<String>();
		List<TradeFlowCode> tradeFlowCodes = tradeDao
				.getTradeFlowCodes(tradeId);
		if (tradeFlowCodes != null && tradeFlowCodes.size() > 0) {
			for (TradeFlowCode flow : tradeFlowCodes) {
				investFlowCodes.add(flow.getTradeFlowCode());
			}
		}
		return investFlowCodes;
	}

	@Override
	public TradeFlowCode queryFlowCodeByDetailId(long tradeDetailId) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tradeDetailId", tradeDetailId);
		List<TradeFlowCode> flowCode = tradeDao
				.getListTradeFlowCode(conditions);
		if (flowCode != null && flowCode.size() > 0) {
			return flowCode.get(0);
		}
		return null;
	}

	@Override
	public TradeFlowCode queryInvestFlowCodesByTradeDetailId(long detailId) {

		return tradeDao.getTradeFlowCodeByDetailId(detailId);
	}

	@Override
	public List<TradeDetail> getInvestProfitTrade(long detailId) {

		return tradeDetailDao.getInvestProfitTrade(detailId);
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addGuaranteeTradeFlowCode(long guaranteeId, long tradeId)
			throws Exception {
		// 添加计息时间
		addEffectiveDateTime(tradeId);

		Trade trade = tradeDao.getByTradeId(tradeId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		/*
		 * Date date = new Date(); SimpleDateFormat simple = new
		 * SimpleDateFormat("yyyy-MM-dd"); String strDate = simple.format(date);
		 * SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd"); String
		 * strDate = simple.format(date); Map<String, Object> conditions = new
		 * HashMap<String, Object>(); conditions.put("authUserId", guaranteeId);
		 * conditions.put("authType", YrdAuthTypeEnum.MAKELOANLVTWO.code());
		 * conditions.put("startTime", strDate + " 00:00:00");
		 * conditions.put("endTime", strDate + " 23:59:59");
		 */

		TradeFlowCode tradeFlow = new TradeFlowCode();
		tradeFlow.setTblBaseId(BusinessNumberUtil.gainNumber());
		Map<String, Object> condition2 = new HashMap<String, Object>();
		condition2.put("userId", guaranteeId);
		condition2.put("roleId", 8l);
		condition2.put("tradeId", tradeId);
		List<TradeQueryDetail> details = tradeDetailDao
				.getTradeDetailByConditions(condition2);
		if (details != null && details.size() > 0) {
			tradeFlow.setTradeDetailId(details.get(0).getId());
		} else {
			throw new Exception("未找到担保机构交易");
		}
		// SimpleDateFormat simpleTwice = new SimpleDateFormat("yyyyMMdd");
		// strDate = simpleTwice.format(date);

		String guaranteeLicenseNoNew = null;
		String contractNo = null;
		List<UserBaseInfoDO> guarantees = userBaseInfoManager.queryByType("JG",
				String.valueOf(guaranteeId));
		if (guarantees != null && guarantees.size() > 0) {
			UserBaseInfoDO guarantee = guarantees.get(0);
			if (YrdConstants.GuaranteeAuthFilterCanstants.GUARANTEE_EDU
					.equals(guarantee.getRealName())) {
				if (loan.getGuaranteeLicenseNo().indexOf("函") >= 0) {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo()
							.replace("函", "保");
				} else {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo();
				}
				contractNo = guaranteeLicenseNoNew + "H";
			} else {
				if (loan.getGuaranteeLicenseNo().indexOf("意") >= 0) {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo()
							.replace("意承", "担");
				} else {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo();
				}
				contractNo = guaranteeLicenseNoNew.replace("担", "") + "H";
			}
		}
		// 更新担保函编号
		loan.setGuaranteeLicenseNo(guaranteeLicenseNoNew);
		loanDemandManager.updateLoanDemand(loan);
		tradeFlow.setRowAddTime(new Date());
		tradeFlow.setNote("担保合同流水号");
		tradeFlow.setTradeFlowCode(contractNo);
		addTradeFlowCode(tradeFlow);
		logger.info("tradeId为" + tradeId + "担保函流水保存成功...");
	}

	public void addRepayPlan(long tradeId) throws Exception {
		Trade trade = tradeDao.getByTradeId(tradeId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
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
		List<DivsionRuleRole> repayRolelist = investService.getRuleRole(String
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
				investService.updateInvestDetailRepayDate(detail.getId(),
						trade.getExpireDateTime());
			}
		}

		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("roleId", SysUserRoleEnum.INVESTOR.getValue());
		conditions.put("tradeId", trade.getId());
		conditions.put("transferPhase", DivisionPhaseEnum.REPAY_PHASE.code());
		List<TradeQueryDetail> details = loanDemandManager
				.getTradeDetailByConditions(conditions);

		if (DivisionWayEnum.MONTH_WAY.getCode().equals(
				loanDemand.getRepayDivisionWay())) {

			// 更新“按月付息”每期付息时间
			if (details != null && details.size() > 0) {
				for (TradeDetail detail : details) {
					int repayPeriodNo = detail.getRepayPeriodNo();
					if (repayPeriodNo > 0) {
						investService
								.updateInvestDetailRepayDate(detail.getId(),
										DateUtil.getDateByMonth(nowDate,
												repayPeriodNo));
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
					investService.updateInvestDetailRepayDate(
							detail.getId(),
							DateUtil.getDateByMonth(nowDate,
									trade.getMonthCount()));
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

	/**
	 * 保存还款计划
	 * 
	 * @param trade
	 * @param loanDemand
	 * @param nowDate
	 * @param repayAll
	 * @param investAmount
	 * @param month
	 * @param row
	 * @param isLast
	 */
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

	// 通知用户未满标，退回冻结金额
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void denyloanDemandUnfull(Trade trade) throws Exception {
		// 先改为未满标状态
		modifyStatus(trade.getId(), YrdConstants.TradeStatus.FAILED);
		List<UserInvestEntry> userInvestEntry = getEntriesByTradeId(trade
				.getId());
		if (userInvestEntry != null && userInvestEntry.size() > 0) {
			long loanerUserId = userInvestEntry.get(0).getLoanerId();
			// 通知借款人
			List<UserBaseInfoDO> loaner = userBaseInfoManager.queryByType(null,
					String.valueOf(loanerUserId));
			if (loaner != null) {
				StringBuilder loanMessage = new StringBuilder();
				String content = YrdConstants.MessageNotifyConstants.UNFULL_LOANDEMAND_OVERDUE_CONTENT;
				content = content.replace("var1", trade.getName());
				content = content.replace("var2",
						MoneyUtil.getFormatAmount(trade.getAmount()));
				content = content.replace("var3",
						MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
				loanMessage.append(content);
				messageService.notifyUser(loaner.get(0),
						loanMessage.toString());
			}
			// 通知投资人
			for (UserInvestEntry investEntry : userInvestEntry) {
				if (DivisionPhaseEnum.ORIGINAL_PHASE.code().equals(
						investEntry.getTransferPhase())) {
					long investorUserId = investEntry.getInvestorId();
					List<UserBaseInfoDO> investor = userBaseInfoManager
							.queryByType(null, String.valueOf(investorUserId));
					if (investor != null) {
						// 解冻金额
						String memo = "借款交易：" + trade.getName()
								+ ",融资失败，该笔借款投资金额全部解冻";
						StringBuilder investorMessage = new StringBuilder();
						String content = YrdConstants.MessageNotifyConstants.UNFULL_INVESTEDlOAND_OVERDUE_CONTENT;
						content = content.replace("var1", trade.getName());
						content = content.replace("var2", MoneyUtil
								.getFormatAmount(investEntry.getAmount()));
						investorMessage.append(content);
						messageService.notifyUser(investor.get(0),
								investorMessage.toString());
					}
				}

			}
		}
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void updateTradeExpireDate(Date expireDate, long tradeId) {
		try {
			logger.info("更新交易还款时间异常tradeId：---" + "tradeId");
			int result = tradeDao.executeTradeExpireDateUpdate(expireDate,
					tradeId);
		} catch (Exception e) {
			logger.error("更新交易还款时间异常tradeId：---" + "tradeId", e);
		}
	}

	public static void main(String[] args) {
		String guaranteeLicenseNoNew = "";
		String guaranteeLicenseNo = "渝台TRB意[2013]字001号";
		String strDate = "20131112";
		int count = 1;
		if (guaranteeLicenseNo.indexOf("意") > 0) {
			String prefix = guaranteeLicenseNo.substring(0,
					guaranteeLicenseNo.indexOf("意"));
			guaranteeLicenseNoNew = prefix + "担[" + strDate.substring(0, 4)
					+ "]字" + strDate.substring(4, strDate.length())
					+ StringUtils.leftPad(String.valueOf(++count), 3, "0")
					+ "号";
		} else {
			String prefix = guaranteeLicenseNo.substring(0,
					guaranteeLicenseNo.indexOf("TRB") + 3);
			guaranteeLicenseNoNew = prefix + "担[" + strDate.substring(0, 4)
					+ "]字" + strDate.substring(4, strDate.length())
					+ StringUtils.leftPad(String.valueOf(++count), 3, "0")
					+ "号";
		}
		System.out.println(guaranteeLicenseNoNew);
	}

	@Override
	public Page<TradeDivisionDetailVO> getDevisionDetailsPage(
			Map<String, Object> conditions, PageParam pageParam)
			throws Exception {
		long tradeId = (Long) conditions.get("tradeId");
		Trade trade = getByTradeId(tradeId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		long loanerId = loan.getLoanerId();
		List<UserBaseInfoDO> loaners = userBaseInfoManager.queryByType(null,
				String.valueOf(loanerId));
		UserBaseInfoDO loaner = null;
		if (loaners != null && loaners.size() > 0) {
			loaner = loaners.get(0);
		} else {
			logger.error("loaners is nulll");
			throw new Exception("can not find loaner user");
		}
		List<TradeDivisionDetailVO> tradeDivisionDetailVO = new ArrayList<TradeDivisionDetailVO>();
		conditions.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		conditions.put("pageSize", pageParam.getPageSize());
		List<TradeDetail> tradeDetails = tradeDetailDao
				.getTradeDivisionDetailByConditions(conditions);
		long totalSize = tradeDetailDao
				.getTradeDivisionDetailByConditionsCount(conditions);
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		for (TradeDetail tradeDetail : tradeDetails) {
			if (DivisionPhaseEnum.INVESET_PHASE.code().equals(
					tradeDetail.getTransferPhase())
					|| DivisionPhaseEnum.REPAY_PHASE.code().equals(
							tradeDetail.getTransferPhase())) {
				TradeDivisionDetailVO detailVo = new TradeDivisionDetailVO(
						tradeDetail);
				detailVo.setLoanerId(loanerId);
				detailVo.setLoanerName(loaner.getRealName());
				Role role = roleDao.getByRoleId(tradeDetail.getRoleId());
				detailVo.setRoleName(role.getName());
				detailVo.setStatus(getNormalTradeStatus(trade));
				tradeDivisionDetailVO.add(detailVo);
			}
		}
		return new Page<TradeDivisionDetailVO>(start, totalSize,
				pageParam.getPageSize(), tradeDivisionDetailVO);
	}

	@Override
	public Page<AmounFlowVO> getTradeFlowPageByParams(
			Map<String, Object> conditions, PageParam pageParam)
			throws Exception {
		conditions.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		conditions.put("pageSize", pageParam.getPageSize());
		long totalSize = amountFlowDao
				.getAmountFlowTradesByParamsCount(conditions);
		List<AmountFlowTrade> flowTrades = amountFlowDao
				.getAmountFlowTradesByParams(conditions);
		List<AmounFlowVO> amFlowVos = new ArrayList<AmounFlowVO>();
		if (flowTrades != null && flowTrades.size() > 0) {
			for (AmountFlowTrade flowTrade : flowTrades) {
				AmountFlow amFlow = amountFlowDao
						.getAmountFlowByFlowId(flowTrade.getAmountFlowId());
				AmounFlowVO amFlowVo = new AmounFlowVO(amFlow.getFlowCode(),
						amFlow.getAmountOut(), amFlow.getAmountIn(),
						amFlow.getAmount(), amFlow.getStatus(),
						amFlow.getNote(), amFlow.getDate(),
						amFlow.getInUserId(), amFlow.getOutUserId(),
						amFlow.getType());
				amFlowVo.setFlowType(AmountFlowEnum.AMOUNT_FLOW_TYPE_NORMAL
						.code());
				List<UserBaseInfoDO> inners = userBaseInfoManager.queryByType(
						null, String.valueOf(amFlow.getInUserId()));
				UserBaseInfoDO inner = null;
				if (inners != null && inners.size() > 0) {
					inner = inners.get(0);
					amFlowVo.setInUserName(inner.getRealName());
				}
				List<UserBaseInfoDO> outers = userBaseInfoManager.queryByType(
						null, String.valueOf(amFlow.getOutUserId()));
				UserBaseInfoDO outer = null;
				if (outers != null && outers.size() > 0) {
					outer = outers.get(0);
					amFlowVo.setOutUserName(outer.getRealName());
				}

				amFlowVos.add(amFlowVo);
			}
		}

		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		return new Page<AmounFlowVO>(start, totalSize, pageParam.getPageSize(),
				amFlowVos);
	}

	@Override
	public Page<AmounFlowVO> getDivisionTradeFlowPageByParams(
			Map<String, Object> conditions, PageParam pageParam)
			throws Exception {
		// List<DivisionDetail> flowTrades =
		// divisionDetailDao.getByTradeIdAndRoles(tradeId, null);
		conditions.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		conditions.put("pageSize", pageParam.getPageSize());
		long totalSize = divisionDetailDao
				.getDivisionAmountFlowTradesByParamsCount(conditions);
		List<DivisionDetail> flowTrades = divisionDetailDao
				.getDivisionAmountFlowTradesByParams(conditions);
		List<AmounFlowVO> amFlowVos = new ArrayList<AmounFlowVO>();
		if (flowTrades != null && flowTrades.size() > 0) {
			for (DivisionDetail flowTrade : flowTrades) {
				String flowAmountIn = null;
				List<UserBaseInfoDO> inners = userBaseInfoManager.queryByType(
						null, String.valueOf(flowTrade.getUserId()));
				UserBaseInfoDO inner = null;
				if (inners != null && inners.size() > 0) {
					inner = inners.get(0);
				} else {
					throw new Exception("can not find loaner user");
				}
				flowAmountIn = inner.getAccountId();
				AmounFlowVO amFlowVo = new AmounFlowVO(
						flowTrade.getBusinessCode(),
						getYrdYjfExchangeUserName(), flowAmountIn,
						flowTrade.getAmount(), flowTrade.getStatus(),
						YrdConstants.TransferComment.YRD_DIVISION,
						flowTrade.getDate(), flowTrade.getUserId(),
						getYrdExchangeUserId(),
						AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
				amFlowVo.setFlowType(AmountFlowEnum.AMOUNT_FLOW_TYPE_DIVISION
						.code());

				List<UserBaseInfoDO> outers = userBaseInfoManager.queryByType(
						null, String.valueOf(getYrdExchangeUserId()));
				UserBaseInfoDO outer = null;
				if (outers != null && outers.size() > 0) {
					outer = outers.get(0);
				} else {
					throw new Exception("can not find loaner user");
				}
				amFlowVo.setInUserName(inner.getRealName());
				amFlowVo.setOutUserName(outer.getRealName());
				amFlowVos.add(amFlowVo);
			}
		}
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());
		return new Page<AmounFlowVO>(start, totalSize, pageParam.getPageSize(),
				amFlowVos);
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void updateGuaranteeTradeFlowCode(long guaranteeId, long tradeId,
			TradeFlowCode tradeFlowCode) throws Exception {
		// 添加计息时间
		addEffectiveDateTime(tradeId);

		Trade trade = tradeDao.getByTradeId(tradeId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());

		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simple.format(date);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("authUserId", guaranteeId);
		conditions.put("authType", YrdAuthTypeEnum.MAKELOANLVTWO.code());
		conditions.put("startTime", strDate + " 00:00:00");
		conditions.put("endTime", strDate + " 23:59:59");

		SimpleDateFormat simpleTwice = new SimpleDateFormat("yyyyMMdd");
		strDate = simpleTwice.format(date);

		String guaranteeLicenseNoNew = null;
		String contractNo = null;
		List<UserBaseInfoDO> guarantees = userBaseInfoManager.queryByType(
				UserTypeEnum.JG.code(), String.valueOf(guaranteeId));
		if (guarantees != null && guarantees.size() > 0) {
			UserBaseInfoDO guarantee = guarantees.get(0);
			if (YrdConstants.GuaranteeAuthFilterCanstants.GUARANTEE_EDU
					.equals(guarantee.getRealName())) {
				if (loan.getGuaranteeLicenseNo().indexOf("函") >= 0) {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo()
							.replace("函", "保");
				} else {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo();
				}
				contractNo = guaranteeLicenseNoNew + "H";
			} else {
				if (loan.getGuaranteeLicenseNo().indexOf("意") >= 0) {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo()
							.replace("意承", "担");
				} else {
					guaranteeLicenseNoNew = loan.getGuaranteeLicenseNo();
				}

				contractNo = guaranteeLicenseNoNew.replace("担", "") + "H";
			}
		}
		// 更新担保函编号
		loan.setGuaranteeLicenseNo(guaranteeLicenseNoNew);
		loanDemandManager.updateLoanDemand(loan);
		// 更新合同号
		tradeFlowCode.setTradeFlowCode(contractNo);
		tradeFlowCode.setRowAddTime(new Date());
		updateTradeFlowCode(tradeFlowCode);

		logger.info("tradeId为" + tradeId + "担保函流水保存成功...");
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	private void updateTradeFlowCode(TradeFlowCode tradeFlowCode) {
		tradeDao.updateTradeFlowCode(tradeFlowCode);
	}

	@Override
	public long queryAllAmount(QueryTradeOrder queryTradeOrder) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", queryTradeOrder.getUserId());
		condition.put("roleId", queryTradeOrder.getRoleId());
		condition.put("tradeCode", queryTradeOrder.getTradeCode());
		condition.put("userName", queryTradeOrder.getUserName());
		if (!StringUtil.isBlank(queryTradeOrder.getStartDate())) {
			String startDate = queryTradeOrder.getStartDate() + " 00:00:00";
			condition.put("maiDemandDate", startDate);
			queryTradeOrder.setStartDate(startDate);
		}
		if (StringUtil.isBlank(queryTradeOrder.getEndDate())) {
			String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
			condition.put("maxDemandDate", endDate);
			queryTradeOrder.setEndDate(DateUtil.simpleFormat(new Date()));
		} else {
			String endDate = queryTradeOrder.getEndDate() + " 23:59:59";
			condition.put("maxDemandDate", DateUtil.parse(endDate));
		}
		condition.put("status", queryTradeOrder.getStatus());
		return tradeDao.queryAllAmount(condition);
	}

	@Override
	public long getAllAmount(long userId, Integer status, Date startDate,
			Date endDate, String code, String loanRealName,
			String loanUserName, Long startAmount, Long endAmount, String name) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userId", userId);
		conditions.put("status", status);
		conditions.put("startDate", startDate);
		conditions.put("endDate", endDate);
		conditions.put("code", code);
		conditions.put("loanRealName", loanRealName);
		conditions.put("loanUserName", loanUserName);
		conditions.put("startAmount", startAmount);
		conditions.put("endAmount", endAmount);
		conditions.put("name", name);
		return userInvestEntryDao.getAllInverstAmount(conditions);
	}

	@Override
	public long countInvestTimes(String userBaseId, Long demandId, Long tradeId)
			throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("userBaseId", userBaseId);

		if (demandId == null && tradeId == null) {
			throw new Exception("参数 demandId和tradeId必须任意选择一个");
		}
		if (demandId != null) {
			conditions.put("demandId", demandId);
		}
		if (tradeId != null) {
			conditions.put("tradeId", tradeId);
		}
		return tradeDao.countInvestTimesByParams(conditions);
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public long offLineTrade(long tradeId) throws Exception {
		try {
			Trade trade = tradeDao.getByTradeId(tradeId);
			LoanDemandDO demandDO = loanDemandDAO
					.queryLoanDemandByDemandId(trade.getDemandId());
			Map<String, Object> condition2 = new HashMap<String, Object>();
			condition2.put("userId", demandDO.getLoanerId());
			condition2.put("roleId", 13l);
			condition2.put("tradeId", trade.getId());
			List<TradeQueryDetail> details = tradeDetailDao
					.getTradeDetailByConditions(condition2);
			if (details != null && details.size() > 0) {
				long loanDetailId = details.get(0).getId();
				tradeDao.deleteTradeFlowCode(loanDetailId);
				tradeDetailDao.deleteloanDetail(loanDetailId);
			}
			tradeDao.deleteTrade(tradeId);
		} catch (Exception e) {
			throw new Exception("下线出错了--tradeId" + tradeId, e);
		}
		return 1;
	}

	@Override
	public Trade getByDemandId(long demandId) {
		return tradeDao.getByDemandId(demandId);
	}

	@Override
	public Long sumPaidDivisionAmount(Long userId) {

		return tradeDao.sumPaidDivisionAmount(userId);
	}

	@Override
	public long countAmountByParams(Map<String, Object> params) {
		long sumAmount = tradeDao.countAmountByParams(params);
		if (params != null) {
			params = null;
		}
		return sumAmount;
	}

	@Override
	public long countInterestAmountByParams(Map<String, Object> params) {

		return tradeDao.countInterestAmountByParams(params);
	}

	@Override
	public Page<TradeQueryDetail> queryCollectionPage(
			Map<String, Object> conditions, PageParam pageParam)
			throws Exception {
		conditions.put("limitStart",
				(pageParam.getPageNo() - 1) * pageParam.getPageSize());
		conditions.put("pageSize", pageParam.getPageSize());
		String startDate = (String) conditions.get("startDate");
		if (StringUtil.isNotBlank(startDate)) {
			conditions.put("startDate", DateUtil.parse(startDate + " 00:00:00"));
		}

		String endDate = (String) conditions.get("endDate");

		if (StringUtil.isNotBlank(endDate)) {
			conditions.put("endDate", DateUtil.parse(endDate + " 23:59:59"));
		}

		List<TradeQueryDetail> tradeDetails = tradeDetailDao
				.getCollectionByConditions(conditions);
		long totalSize = tradeDetailDao
				.getCollectionByConditionsCount(conditions);
		int start = PageParamUtil.startValue((int) totalSize,
				pageParam.getPageSize(), pageParam.getPageNo());

		return new Page<TradeQueryDetail>(start, totalSize,
				pageParam.getPageSize(), tradeDetails);

	}

	@Override
	public CalculateLoanCostResult calculateLoanCost(
			CalculateLoanCostOrder loanCostOrder) {
		CalculateLoanCostResult loanCostResult = new CalculateLoanCostResult();
		List<DivsionRuleRole> list = divisionService.getRuleRole(loanCostOrder
				.getTemplateId() + "");
		loanCostResult = getInterestByRole(list, loanCostOrder);
		return loanCostResult;
	}

	public CalculateLoanCostResult getInterestByRole(
			List<DivsionRuleRole> list, CalculateLoanCostOrder loanCostOrder) {
		CalculateLoanCostResult loanCostResult = new CalculateLoanCostResult();
		double loanInterest = 0;
		double investorInterest = 0;
		String[] roleName = new String[list.size()];
		double[] divisionRule = new double[list.size()];
		int i = 0;
		for (DivsionRuleRole d : list) {
			BigDecimal bg = new BigDecimal(d.getRule() * 100);
			loanInterest += bg.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			if (SysUserRoleEnum.INVESTOR.getValue() == d.getRoleId()) {
				investorInterest += bg.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			}
			Role role = roleDao.getByRoleId(d.getRoleId());
			roleName[i] = role.getName();
			divisionRule[i] = d.getRule();
			i++;
		}
		if (sysFunctionConfigService.isTradeFeeCharge()) {
			if (loanCostOrder.getLoanAmount() != null
					&& loanCostOrder.getLoanAmount().greaterThan(Money.zero())
					&& loanCostOrder.getTimeLimitUnit() != null
					&& loanCostOrder.getTimeLimit() > 0) {
				Money tradeChargeAmount = loanCostOrder.getLoanAmount()
						.multiply(new BigDecimal(0.002));
				BigDecimal rate = new BigDecimal(0);
				if (loanCostOrder.getTimeLimitUnit() == LoanPeriodUnitEnum.LOAN_BY_DAY) {
					rate = tradeChargeAmount
							.multiply(new BigDecimal(360))
							.getAmount()
							.divide(new BigDecimal(loanCostOrder.getTimeLimit()),
									BigDecimal.ROUND_HALF_UP)
							.divide(loanCostOrder.getLoanAmount().getAmount(),
									3, BigDecimal.ROUND_HALF_UP);
				} else if (loanCostOrder.getTimeLimitUnit() == LoanPeriodUnitEnum.LOAN_BY_YEAR) {
					rate = tradeChargeAmount
							.getAmount()
							.divide(new BigDecimal(loanCostOrder.getTimeLimit()),
									BigDecimal.ROUND_HALF_UP)
							.divide(loanCostOrder.getLoanAmount().getAmount(),
									3, BigDecimal.ROUND_HALF_UP);
				} else {
					rate = tradeChargeAmount
							.multiply(new BigDecimal(12))
							.getAmount()
							.divide(new BigDecimal(loanCostOrder.getTimeLimit()),
									BigDecimal.ROUND_HALF_UP)
							.divide(loanCostOrder.getLoanAmount().getAmount(),
									3, BigDecimal.ROUND_HALF_UP);
				}
				loanCostResult.setTradeChargeAmount(tradeChargeAmount);
				loanCostResult.setTradeChargeRate(rate.doubleValue() * 100);
			}
		}
		loanCostResult.setDivisionRule(divisionRule);
		loanCostResult.setRoleName(roleName);
		loanCostResult.setSuccess(true);
		loanCostResult.setLoanInterest(loanInterest);
		loanCostResult.setInvestorInterest(investorInterest);
		return loanCostResult;
	}

	/*
	 * @Transactional(rollbackFor = Exception.class, value =
	 * "transactionManager")
	 * 
	 * @Override public int repay(long repayUserId, long demandId, long
	 * repayPlanId) throws Exception {
	 * 
	 * Date date = new Date(); List<UserBaseInfoDO> repayUsers =
	 * userBaseInfoManager.queryByType(null, String.valueOf(repayUserId));
	 * UserBaseInfoDO repayUser = null; if (ListUtil.isNotEmpty(repayUsers)) {
	 * repayUser = repayUsers.get(0); if
	 * (!"normal".equals(repayUser.getState())) {
	 * logger.error("还款人用户状态异常，userId，{}", repayUserId); throw new
	 * Exception("user state error"); } } else {
	 * logger.error("查询还款人信息异常，userId，{}", repayUserId); throw new
	 * Exception("can not find repay user"); }
	 * 
	 * Trade trade = tradeDao.getByDemandId(demandId); LoanDemandDO loan =
	 * loanDemandManager.queryLoanDemandByDemandId(trade .getDemandId());
	 * 
	 * RepayPlanInfo repayPlanInfo = null;
	 * 
	 * if (DivisionWayEnum.SIT_WAY.code().equals(loan.getRepayDivisionWay())) {
	 * RepayPlanQueryOrder repayPlanQueryOrder = new RepayPlanQueryOrder();
	 * repayPlanQueryOrder.setRepayDivisionWay(DivisionWayEnum.SIT_WAY .code());
	 * repayPlanQueryOrder.setPageSize(999999);
	 * repayPlanQueryOrder.setPageNumber(1);
	 * repayPlanQueryOrder.setTradeId(trade.getId());
	 * repayPlanQueryOrder.setPeriodNo(1); QueryBaseBatchResult<RepayPlanInfo>
	 * result = repayPlanService .queryRepayPlanInfo(repayPlanQueryOrder); if
	 * (ListUtil.isNotEmpty(result.getPageList())) { repayPlanInfo =
	 * result.getPageList().get(0); repayPlanId =
	 * repayPlanInfo.getRepayPlanId(); // 获取第一期还款计划id } } else { if (repayPlanId
	 * == 0) { throw new RuntimeException("请求参数不完整，repayPlanId=" + repayPlanId +
	 * "，还款计划id不为0"); } }
	 * 
	 * // 获取资金信息 Money availableBalance = null;
	 * 
	 * YzzUserAccountQueryResponse accountResult = apiTool
	 * .queryUserAccount(repayUser.getAccountId());
	 * 
	 * if (accountResult.success()) { availableBalance = new
	 * Money(accountResult.getAvailableBalance()); } else { throw new
	 * Exception("获取资金信息异常"); }
	 * 
	 * // 创建还款交易 RepayTradeOrder order = new RepayTradeOrder();
	 * order.setPayerUserId(repayUser.getAccountId());
	 * order.setTradeAmount(repayPlanInfo.getAmount());
	 * order.setTradeMemo(repayPlanInfo.getNote());
	 * order.setTradeName(repayPlanInfo.getTradeName() + "还款"); RepayTradeResult
	 * result = repayService.createRepayTrade(order, this.getOpenApiContext());
	 * if (!result.isSuccess()) { return 0; }
	 * 
	 * long repayAmount = 0; double repayCutAmount = 0; // 除投资人外的分润金额 double
	 * repayDivisionAmount = 0; if
	 * (DivisionWayEnum.SIT_WAY.code().equals(loan.getRepayDivisionWay())) { //
	 * 到期归还本金及利息,用户余额是否充足 // 还款阶段利率 long divisionTemplateLoanBaseId =
	 * loan.getDivisionTemplateId(); DivisionTemplateLoanDO divisionTemplateLoan
	 * = divisionTemplateLoanService .getByBaseId(divisionTemplateLoanBaseId);
	 * List<DivsionRuleRole> repayRolelist = divisionService
	 * .getRuleRole(String.valueOf(divisionTemplateLoan .getRepayTemplateId()));
	 * double payInterest = 0; if (repayRolelist != null && repayRolelist.size()
	 * > 0) { for (DivsionRuleRole druleRole : repayRolelist) { if
	 * (DivisionPhaseEnum.REPAY_PHASE.code().equals( druleRole.getPhase())) {
	 * BigDecimal bg = new BigDecimal(getDaysRuleRate( druleRole.getRule(),
	 * trade)); repayCutAmount += Math.round(trade.getLoanedAmount()
	 * bg.setScale(10, BigDecimal.ROUND_HALF_UP) .doubleValue()); payInterest +=
	 * bg .setScale(10, BigDecimal.ROUND_HALF_UP) .doubleValue();
	 * 
	 * if (druleRole.getRoleId() != SysUserRoleEnum.INVESTOR .getValue()) {
	 * repayDivisionAmount += Math.round(trade .getLoanedAmount()
	 * bg.setScale(10, BigDecimal.ROUND_HALF_UP) .doubleValue()); } } } }
	 * repayAmount = trade.getLoanedAmount() + (long) repayCutAmount; if
	 * (availableBalance.getCent() < repayAmount) {
	 * logger.info("该用户余额不足---用户id：" + repayUser.getUserId()); return 1; } }
	 * else if (DivisionWayEnum.MONTH_WAY.code().equals(
	 * loan.getRepayDivisionWay())) {// 按月还息到期还本,用户余额是否充足
	 * 
	 * repayPlanInfo = repayPlanService
	 * .findByRepayPlanIdwithrowLock(repayPlanId); if (repayPlanInfo == null) {
	 * logger.info("该还款计划id：{}不存在" + repayPlanId); throw new
	 * RuntimeException("还款计划不存在"); } if
	 * (!RepayPlanStatusEnum.NOTPAY.code().equals( repayPlanInfo.getStatus())) {
	 * logger.info("该还款计划id：{}不存在" + repayPlanId); throw new
	 * RuntimeException("该还款计划状态不再进行还款"); } if
	 * (repayPlanInfo.getAmount().greaterThan(availableBalance)) {
	 * logger.info("该用户余额不足---用户id：" + repayUser.getUserId()); throw new
	 * RuntimeException("用户余额不足"); } repayAmount =
	 * repayPlanInfo.getAmount().getCent(); // ? }
	 * 
	 * String repayYjfUserId = tradeDetailDao
	 * .getYjfUserNameByUserId(repayUserId);
	 * 
	 * logger.info("repayUserId :" + repayUserId + " " + repayYjfUserId + " " +
	 * repayUser.getAccountId());
	 * 
	 * long tradeId = trade.getId();
	 * 
	 * // 到期归还本金及利息,用户余额是否充足 if
	 * (DivisionWayEnum.SIT_WAY.code().equals(loan.getRepayDivisionWay())) {
	 * 
	 * // 还投资人本金流水 List<TransferTrade> investTradeList =
	 * addRepayPrincipalAmountFlow( repayYjfUserId, repayUserId, tradeId, date);
	 * // 投资人的利润流水 List<TransferTrade> investDevisionList = divisionService
	 * .addRepayInvestorDivisionAmountFlow(tradeId, repayYjfUserId,
	 * repayUserId);
	 * 
	 * // 中间账户转给还款阶段的分润账户 divisionService.division(tradeId);
	 * 
	 * // 除投资人外的分润总金额存入流水 addRepayDivisionMoneyAmountFlow(repayUserId,
	 * repayYjfUserId, date, trade, (long) repayDivisionAmount);
	 * 
	 * List<RepaySubOrder> subOrders = new ArrayList<RepaySubOrder>();
	 * 
	 * for (int i = 0; i < investTradeList.size(); i++) { TransferTrade tt =
	 * investTradeList.get(i); RepaySubOrder subOrder = new RepaySubOrder();
	 * Money transferAmount = Money.cent(tt.getAmount());
	 * 
	 * TransferTrade td = investDevisionList.get(i); if (tt.getUserId() ==
	 * td.getUserId()) { transferAmount.addTo(new Money(td.getAmount())); }
	 * 
	 * subOrder.setTransferAmount(transferAmount); subOrder.setMemo("还款");
	 * subOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
	 * subOrder.setPayeeUserId(tt.getYjfUserName());
	 * subOrder.setTradeName("还款"); subOrders.add(subOrder); }
	 * 
	 * RepaySubOrder shardOrder = new RepaySubOrder(); shardOrder.setMemo("");
	 * shardOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
	 * shardOrder.setPayeeUserId(Constants.EXCHANGE_ACCOUNT);
	 * shardOrder.setTradeName("分润"); shardOrder
	 * .setTransferAmount(Money.cent((long) repayDivisionAmount));
	 * 
	 * RepayOrder repayOrder = new RepayOrder();
	 * repayOrder.setPayerUserId(repayUser.getAccountId());
	 * repayOrder.setRefTradeNo(trade.getCode());
	 * repayOrder.setShardOrder(shardOrder); repayOrder.setSubOrders(subOrders);
	 * repayOrder.setTradeNo(repayPlanInfo.getNote());
	 * 
	 * // 更新状态 Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("userId", repayUserId); List<Role> userRoles =
	 * roleDao.getRolesByUserId(params); short tradeStatus =
	 * YrdConstants.TradeStatus.REPAY_FINISH; for (Role userRole : userRoles) {
	 * if (SysUserRoleEnum.GUARANTEE.getRoleCode().equals( userRole.getCode()))
	 * { tradeStatus = YrdConstants.TradeStatus.COMPENSATORY_REPAY_FINISH; } }
	 * tradeDao.modifyStatus(tradeId, tradeStatus);
	 * tradeDao.modifyFinishDate(tradeId, date);
	 * 
	 * // 通知借款人 notifyLoaner(trade, loan, repayCutAmount); // 通知投资人
	 * notifyInvestor(trade);
	 * 
	 * } else if (DivisionWayEnum.MONTH_WAY.code().equals(
	 * loan.getRepayDivisionWay())) { dealRepayByMonth(repayUserId, date,
	 * repayUser, trade, loan, repayPlanInfo, repayCutAmount,
	 * repayDivisionAmount, repayYjfUserId, tradeId); }
	 * 
	 * repayPlanService.updateStatus(repayPlanId,
	 * RepayPlanStatusEnum.PAYID.getCode());
	 * repayPlanService.updateActualRepayDate(repayPlanId, new Date());
	 * 
	 * logger.info("还款成功---用户id：" + repayUser.getUserId()); return 0; }
	 */


	public void saveInvest(Trade trade, Long amount, String orderNo) {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		TradeDetail tradeDetail = new TradeDetail();
		tradeDetail.setUserId(sessionLocal.getUserId());
		tradeDetail.setAccountId(sessionLocal.getAccountId());
		tradeDetail.setUserName(sessionLocal.getUserName());
		tradeDetail.setRoleId(12);
		tradeDetail
				.setTransferPhase(DivisionPhaseEnum.ORIGINAL_PHASE.getCode());
		tradeDetail.setTradeId(trade.getId());
		tradeDetail.setAmount(amount);
		tradeDetail.setTradeDetailStatus(TradeDetailStatusEnum.IT
				.getCode());
		tradeDetail.setOrderNo(orderNo);
		tradeDetail.setNote(trade.getNote());
		try {
			investService.saveInvestOriginalTradeDetail(tradeDetail);
			// divisionService.invest(tradeDetail);
		} catch (Exception e) {
			logger.error("投资失败，数据异常！投资人id：{}" + sessionLocal.getUserId(), e);
		}

	}
}
