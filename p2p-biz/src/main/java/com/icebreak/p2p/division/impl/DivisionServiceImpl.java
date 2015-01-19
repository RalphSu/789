package com.icebreak.p2p.division.impl;

import com.icebreak.p2p.base.OpenApiBaseService;
import com.icebreak.p2p.common.services.MessageService;
import com.icebreak.p2p.daointerface.*;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.division.DivisionException;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.division.DivisionTemplateYrdService;
import com.icebreak.p2p.integration.openapi.RemoteTradeService;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.TradeOrder;
import com.icebreak.p2p.integration.openapi.result.TradeResult;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.localService.SysClearCacheServiceClient;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.trade.InvestService;
import com.icebreak.p2p.transfer.TransferService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import com.icebreak.p2p.util.*;
import com.icebreak.p2p.ws.enums.*;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DivisionServiceImpl extends OpenApiBaseService implements
		DivisionService {

	private final static String REMOTE_RESULT_SUCCESS = "EXECUTE_SUCCESS";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 转账冻结类型
	 */
	private static String TRANSFERFREEZETYPE = ApplicationConstant.TRANSFER_FREEZE_TYPE;
	/**
	 * 分润模版缓存
	 */
	private static Map<String, List<DivisionTemplate>> divisionTemplateCache = new ConcurrentHashMap<String, List<DivisionTemplate>>();

	private TradeDao tradeDao;

	private DivisionRuleDao divisionRuleDao;

	private TradeDetailDao tradeDetailDao;

	private DivisionDetailDao divisionDetailDao;

	private TransferTradeDao transferTradeDao;

	private DivisionTemplateDao divisionTemplateDao;

	private TradeDivisionDao tradeDivisionDao;

	private TransferService transferService;

	private DivisonRuleRoleDao divisonRuleRoleDao;
	@Autowired
	private LoanDemandManager loanDemandManager;
	@Autowired
	private UserRelationManager userRelationManager;
	@Autowired
	DivisionTemplateYrdService divisionTemplateYrdService;
	@Autowired
	private UserBaseInfoManager userBaseInfoManager;

	@Autowired
	private RoleDao roleDao;
	@Autowired
	protected MessageService messageService;
	@Autowired
	protected InvestService investService;

	@Autowired
	SysClearCacheServiceClient sysClearCacheServiceClient;
	@Autowired
	LoanDemandDAO loanDemandDAO;
	@Autowired
	UserGoldExperienceDao userGoldExperienceDao;

	private String yrdRoleId = SysUserRoleEnum.PLATFORM.code();
	private String guaranteeRoleId = SysUserRoleEnum.GUARANTEE.code();
	private String sponsorRoleId = SysUserRoleEnum.SPONSOR.code();
	private String jjrRoleId = SysUserRoleEnum.BROKER.code();
	private String yxjgRoleId = SysUserRoleEnum.MARKETING.code();
	private String investRoleId = SysUserRoleEnum.INVESTOR.code();
	private long YrdExchangeUserId;
	private String yrdYjfExchangeUserName;
	private long yrdUserId = 0;

	public boolean isInitAccountInfo = false;

	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}

	public void setDivisionTemplateDao(DivisionTemplateDao divisionTemplateDao) {
		this.divisionTemplateDao = divisionTemplateDao;
	}

	public void setTradeDivisionDao(TradeDivisionDao tradeDivisionDao) {
		this.tradeDivisionDao = tradeDivisionDao;
	}

	public void setTransferTradeDao(TransferTradeDao transferTradeDao) {
		this.transferTradeDao = transferTradeDao;
	}

	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	public void setDivisionRuleDao(DivisionRuleDao divisionRuleDao) {
		this.divisionRuleDao = divisionRuleDao;
	}

	public void setTradeDetailDao(TradeDetailDao tradeDetailDao) {
		this.tradeDetailDao = tradeDetailDao;
	}

	public void setDivisionDetailDao(DivisionDetailDao divisionDetailDao) {
		this.divisionDetailDao = divisionDetailDao;
	}

	public void setDivisonRuleRoleDao(DivisonRuleRoleDao divisonRuleRoleDao) {
		this.divisonRuleRoleDao = divisonRuleRoleDao;
	}

	@Override
	public List<DivisionTemplate> getDivisionTemplatesByPhase(String phase,
			String statusEnum) {
		if (divisionTemplateCache.containsKey(phase)) {
			return divisionTemplateCache.get(phase);
		} else {
			List<DivisionTemplate> divisionTemplates = divisionTemplateDao
					.getDivisionTemplatesByPhase(phase);
			divisionTemplateCache.put(phase, divisionTemplates);
			return divisionTemplates;
		}
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addDivisionTemplate(DivisionTemplate template, int[] roleIds,
			double[] percentages) {
		divisionTemplateDao.addDivisionTemplate(template);
		addDivisionRules(template.getId(), roleIds, percentages);
		sysClearCacheServiceClient.clearCache();
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void modifyDivisionTemplate(DivisionTemplate template,
			int[] roleIds, double[] percentages) {
		divisionTemplateDao.modifyDivisionTemplate(template);
		long templateId = template.getId();
		divisionRuleDao.deleteByTemplateId(templateId);
		addDivisionRules(templateId, roleIds, percentages);
		sysClearCacheServiceClient.clearCache();
	}

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void deleteDivisionTemplate(long templateId) {
		divisionTemplateDao.deleteByTemplateId(templateId);
		divisionRuleDao.deleteByTemplateId(templateId);
		sysClearCacheServiceClient.clearCache();
	}

	@Override
	public DivisionTemplate getByTemplateId(long templateId) {
		DivisionTemplate template = divisionTemplateDao
				.getByTemplateId(templateId);
		template.setRules(divisionRuleDao.getByTemplateId(templateId));
		return template;
	}

	@Autowired
	DivisionTemplateYrdService divisionTemplateLoanService;
	public long calVirtualMoneyProfit(TransferTrade division) {
		double virtualMoneyProfit = 0;
		UserGoldExperienceDO userGoldExp = new UserGoldExperienceDO();
		userGoldExp.setUserId(division.getUserId());
		userGoldExp.setStatus("0");
		long tradeDetailId = division.getTradeDetailId();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("detailId",tradeDetailId);
		List<TradeQueryDetail> investDetailList = tradeDetailDao.getTradeDetailByConditions(params);

		TradeQueryDetail tradeDetail = investDetailList.get(0);
		Trade trade = tradeDao.getByTradeId(tradeDetail.getTradeId());

		userGoldExp.setTradeDetailId(tradeDetailId);
		userGoldExp.setTradeId(trade.getId());
		List<UserGoldExperienceDO> virtualMoneyList = userGoldExperienceDao.query(userGoldExp);
		if (ListUtil.isEmpty(virtualMoneyList)) {
			return 0;
		}
		UserGoldExperienceDO usrVirtualMoney = virtualMoneyList.get(0);
		long virtualMoney = usrVirtualMoney.getAmount().longValueExact();
		if (virtualMoney == 0) {
			return 0;
		}

		LoanDemandDO loan = null;
		try {
			loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
		} catch (Exception e) {
			logger.error("查询出错");
			return 0;
		}

		long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
				.getByBaseId(divisionTemplateLoanBaseId);
		List<DivsionRuleRole> repayRolelist = getRuleRole(String.valueOf(divisionTemplateLoan
				.getRepayTemplateId()));

		double profitRate = tradeDetail.getProfitRate();
		for (DivsionRuleRole divsionRuleRole : repayRolelist) {
			if (SysUserRoleEnum.INVESTOR.code().equals(
					String.valueOf(divsionRuleRole.getRoleId()))) {
				profitRate = getDaysRuleRate(divsionRuleRole.getRule(), trade);
			}
		}

		logger.info("当前项目的利率是：" + profitRate);
		BigDecimal bg = new BigDecimal(profitRate * virtualMoney);
		virtualMoneyProfit = (long)bg.setScale(10, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		virtualMoneyProfit = Math.floor(virtualMoneyProfit);
		logger.info("虚拟货币的总收益是：" + profitRate);
		return (long)virtualMoneyProfit;
	}

	/**
	 * 分润
	 * 
	 * @throws Exception
	 * */
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void division(long tradeId) throws Exception {
		initializeAccountInfo();
		Date date = new Date();
		Trade trade = tradeDao.getByTradeIdWithRowLock(tradeId);
		// 还款人账户
		String repayYjfUserName = getYrdYjfExchangeUserName();
		long exchangeUserId = this.YrdExchangeUserId;

		List<TransferTrade> repayDivisionList = queryRepayDivision(tradeId);
		logger.info("curTransferTrade={} rolesTransferTrades.size()={}",
				repayDivisionList, repayDivisionList.size());

		for (TransferTrade division : repayDivisionList) {

			long realAmount = division.getAmount();
			long userId = division.getUserId();
			long virtualMoneyProfit = calVirtualMoneyProfit(division);
			realAmount = realAmount - virtualMoneyProfit;
			doDivision(userId, exchangeUserId, trade.getId(),
					division.getTradeDetailId(), trade.getCode(),
					division.getYjfUserName(), repayYjfUserName,
					realAmount, date);

			tradeDetailDao.updatetStatus(division.getTradeDetailId(),
					TradeDetailStatusEnum.PS.code());
		}

	}

	public List<TransferTrade> queryRepayDivision(long tradeId)
			throws Exception {
		List<TransferTrade> repayDivisionList = new ArrayList<TransferTrade>();
		initializeAccountInfo();
		Trade trade = tradeDao.getByTradeIdWithRowLock(tradeId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateYrdService
				.getByBaseId(divisionTemplateLoanBaseId);
		List<DivsionRuleRole> repayRolelist = getRuleRole(String
				.valueOf(divisionTemplateLoan.getRepayTemplateId()));
		// 还款阶段分润
		if (ListUtil.isNotEmpty(repayRolelist)) {
			for (DivsionRuleRole role : repayRolelist) {
				if (role.getRoleId() == SysUserRoleEnum.INVESTOR.getValue()) {
					continue;
				}

				if (DivisionPhaseEnum.REPAY_PHASE.code()
						.equals(role.getPhase())) {
					Role currentRole = roleDao.getByRoleId(role.getRoleId());
					List<TransferTrade> roleDivisionList = transferTradeDao
							.getPhaseTransferTrades(trade.getId(),
									DivisionPhaseEnum.REPAY_PHASE.code(),
									null,
									new String[]{currentRole.getCode()});
					repayDivisionList.addAll(roleDivisionList);
				}
			}

		}
		return repayDivisionList;
	}

	@Resource
	private RemoteTradeService remoteTradeService;

	/**
	 * 新建交易
	 * 
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void createNewTrade(LoanDemandDO demandDO) throws Exception {
		try {
			Trade trade = new Trade(demandDO.getTradeCode(),
					demandDO.getDemandId(), demandDO.getLoanName(),
					demandDO.getLoanAmount(), demandDO.getInterestRate(),
					demandDO.getLeastInvestAmount(),
					demandDO.getSaturationConditionMethod(),
					demandDO.getSaturationCondition(),
					YrdConstants.TradeStatus.TRADING,
					demandDO.getTimeLimitUnit(), demandDO.getTimeLimit(),
					demandDO.getDeadline(), 0L, demandDO.getDemandDate(),
					demandDO.getLoanNote());
			trade.setIsJoinActivity(demandDO.getIsJoinActivity());
			trade.setLoanType(demandDO.getLoanType());
			trade.setIsNotifyLoaner(YrdConstants.MessageNotifyConstants.ISNOTIFIED_NO);
			tradeDao.addTrade(trade);
			final long tradeId = trade.getId();
			tradeDetailDao.addTradeDetail(new TradeDetail(demandDO
					.getLoanerId(), tradeId, 0L, SysUserRoleEnum.LOANER
					.getValue(), DivisionPhaseEnum.ORIGINAL_PHASE.code(),
					demandDO.getLoanStatement()));
			tradeDivisionDao.addTradeDivision(new TradeDivision(demandDO
					.getDivisionTemplateId(), tradeId));

			TradeOrder order = new TradeOrder();
			order.setSellerUserId(userBaseInfoManager.getRealNameByUserName(
					demandDO.getUserName()).getAccountId());
			order.setTradeAmount(Money.cent(trade.getAmount()));

			String tradeNote = trade.getNote();
			//因为tradeNode将放在url中传输，把敏感符号替换成全角的
			tradeNote = StringUtil.replaceHtml(tradeNote);
			tradeNote = tradeNote.replaceAll("&", "＆");
			tradeNote = tradeNote.replaceAll("\\?", "？");
			tradeNote = tradeNote.replaceAll("%", "％");
			tradeNote = tradeNote.replaceAll("=", "＝");

			if(StringUtil.getWordCount(tradeNote) > 120){
				try {
					order.setTradeMemo(StringUtil.subStr(tradeNote, 110) + "...");
				} catch (UnsupportedEncodingException e) {}
			}else{
				order.setTradeMemo(tradeNote);
			}
			order.setTradeName(trade.getName());
			OpenApiContext context = this.getOpenApiContext();
			TradeResult result = remoteTradeService.createTrade(order, context);

			if (result.isSuccess()) {
				tradeDao.modifyTradeCode(tradeId, result.getTradeNo());
				demandDO.setTradeCode(result.getTradeNo());
				loanDemandDAO.update(demandDO);
			}
		} catch (Exception e) {
			logger.error("createNewTrade", e);
			throw new Exception("创建交易失败");
		}

	}

	/**
	 * 校验交易
	 * 
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
			result.put("message", "该借款需求不处于幕资阶段，不能进行投资");
		} else {
			result.put("status", true);
		}
		return result;
	}

	/**
	 * 校验金额
	 * 
	 * @param amount
	 * @return
	 */
	private Map<String, Object> doCheckAmount(Trade trade, String userId,
			Long amount) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", false);
		if (amount == null || amount <= 0) {
			result.put("message", "投资金额必须大于0");
			return result;
		}

		if ((trade.getLeastInvestAmount() - (trade.getAmount() - trade
				.getLoanedAmount())) <= 0) {
			if (amount < trade.getLeastInvestAmount()) {
				result.put("message", "投资金额不能小于该笔借款的最低投资金额");
				return result;
			}
		}

		if (trade.getLoanedAmount() + amount > trade.getAmount()) {
			result.put("message", "投资金额不能大于该笔借款的可投资金额");
			return result;
		}

		YzzUserAccountQueryResponse queryRequest = apiTool
				.queryUserAccount(userId);

		if (queryRequest.success()) {
			result.put("message", "资金账户不存在");
			return result;
		}

		if (amount > new Money(queryRequest.getAvailableBalance()).getCent()) {
			result.put("message",
					"您的可用余额不足，请<a href=\"/usercenter/rechargePage\">充值</a>");
			return result;
		}
		result.put("status", true);
		return result;
	}

	@Override
	public void invest(TradeDetail detail) throws Exception {
		// investService.investNotify(detail);
	}


	// 按天计算利率
	private static double getDaysRuleRate(double rule, Trade trade) {
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

	/**
	 * 审核投资达到满标状态完成，开始转账
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	public void transfer(Trade trade, long userId) throws Exception {
		// 创建担保机构审核通过审核记录
		logger.info("tradeId为" + trade.getId() + "放款开始...");

		this.initializeAccountInfo();
//		trade = this.tradeDao.getByTradeIdWithRowLock(trade.getId());
		if (trade.getStatus() != YrdConstants.TradeStatus.REPAYING) {
			//当前时间
			Date currentDate = new Date();
			//计算结息日期
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(Calendar.DATE, trade.getTimeLimit());
			trade.setEffectiveDateTime(currentDate);
			trade.setExpireDateTime(cal.getTime());
			// 修改状态
			tradeDao.modifyStatus(trade.getId(), YrdConstants.TradeStatus.REPAYING, currentDate, cal.getTime());
		} else {
			logger.error("重复的放款");
			throw new Exception();
		}
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
//		addLoanAuthRecord(userId, trade.getId(), YrdAuthTypeEnum.MAKELOANLVTWO.code());

		Date date = new Date();
		TransferTrade loanerTransferTrade = transferTradeDao
				.getPhaseTransferTrade(trade.getId(),
						DivisionPhaseEnum.ORIGINAL_PHASE.code(),
						SysUserRoleEnum.LOANER.getRoleCode());
		if (loanerTransferTrade == null) {
			throwDivisionException("转账异常：该交易没有找到借款人，交易ID:" + trade.getId());
		}
		long divisionTemplateLoanBaseId = loan.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateYrdService
				.getByBaseId(divisionTemplateLoanBaseId);
		List<DivsionRuleRole> investRolelist = getRuleRole(String
				.valueOf(divisionTemplateLoan.getInvestTemplateId()));
		// 计算实际到借款人账金额
		double countInvestCutRate = 0f;
		// 融资付息
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
					countInvestCutRate += rate;

				}
			}
		}
		List<TransferTrade> transferTrades = transferTradeDao
				.getPhaseTransferTrades(trade.getId(),
						DivisionPhaseEnum.ORIGINAL_PHASE.code(), TradeDetailStatusEnum.PS.getCode(),
						new String[]{SysUserRoleEnum.INVESTOR.getRoleCode()});
		// 执行转账到借款人
		for (TransferTrade transferTrade : transferTrades) {
			// 解冻投资人金额并转入借款人账户
			transferService.doTransfer(trade.getId(),
					transferTrade.getTradeDetailId(),
					loanerTransferTrade.getYjfUserName(),
					transferTrade.getYjfUserName(), transferTrade.getAmount(),
					YrdConstants.TransferComment.YRD_INVEST, date,
					loanerTransferTrade.getUserId(), transferTrade.getUserId(),
					AmountFlowEnum.AMOUNT_FLOW_INVEST.code());
		}

		List<TransferTrade> invTtransitionTrades = transferTradeDao
				.getPhaseTransferTrades(trade.getId(),
						DivisionPhaseEnum.INVESET_TRANSITION_PHASE.code(), null,
						new String[]{SysUserRoleEnum.PLATFORM.getRoleCode()});
		if (invTtransitionTrades != null && invTtransitionTrades.size() > 0) {
			if (invTtransitionTrades.size() > 1) {
				logger.info("投资转款失败，投资分润转款业务交易重复！");
				throw new RuntimeException("投资转款失败，业务交易重复！: 交易ID--"
						+ trade.getId());
			}

			TransferTrade transferTrade = invTtransitionTrades.get(0);
			transferService.doTransfer(trade.getId(),
					transferTrade.getTradeDetailId(),
					getYrdYjfExchangeUserName(),
					loanerTransferTrade.getYjfUserName(),
					(long) countInvestCutAmount,
					YrdConstants.TransferComment.YRD_DIVISION, date,
					this.YrdExchangeUserId, loanerTransferTrade.getUserId(),
					AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
		} else {
			long tranzDetailID = tradeDetailDao.addTradeDetail(new TradeDetail(
			this.YrdExchangeUserId, trade.getId(),
					(long) countInvestCutAmount, 7,
					DivisionPhaseEnum.INVESET_TRANSITION_PHASE.code(), null));
			transferService.doTransfer(trade.getId(), tranzDetailID,
					getYrdYjfExchangeUserName(),
					loanerTransferTrade.getYjfUserName(),
					(long) countInvestCutAmount,
					YrdConstants.TransferComment.YRD_DIVISION, date,
					this.YrdExchangeUserId, loanerTransferTrade.getUserId(),
					AmountFlowEnum.AMOUNT_FLOW_DIVISION.code());
		}

		//TODO
/*		List<DivsionRuleRole> repayRolelist = getRuleRole(String
				.valueOf(divisionTemplateLoan.getRepayTemplateId()));
		investRolelist.addAll(repayRolelist);
		if (investRolelist != null && investRolelist.size() > 0) {
			// 执行机构转账
			for (DivsionRuleRole roleRule : investRolelist) {
				if (DivisionPhaseEnum.INVESET_PHASE.code().equals(
						roleRule.getPhase())) {
					Role currentRole = roleDao
							.getByRoleId(roleRule.getRoleId());
					List<TransferTrade> rolesTransferTrades = transferTradeDao
							.getPhaseTransferTrades(trade.getId(),
									DivisionPhaseEnum.INVESET_PHASE.code(),
									currentRole.getCode());
					if (rolesTransferTrades != null
							&& rolesTransferTrades.size() > 0) {
						for (TransferTrade curTransferTrade : rolesTransferTrades) {
							// 扣除分润金额
							logger.info("curTransferTrade =="
									+ curTransferTrade
									+ " curTransferTrade.size()="
									+ rolesTransferTrades.size());
							doDivision(curTransferTrade.getUserId(),
									this.YrdExchangeUserId, trade.getId(),
									curTransferTrade.getTradeDetailId(),
									trade.getCode(),
									curTransferTrade.getYjfUserName(),
									getYrdYjfExchangeUserName(),
									curTransferTrade.getAmount(), date);
						}
					}
				}
			}
		}*/

		// 通知借款人，放款，显示时间金额
		List<UserBaseInfoDO> loaners = userBaseInfoManager.queryByType(null,
				String.valueOf(loan.getLoanerId()));
		StringBuilder toLoanerMessage = new StringBuilder();
		if (loaners != null && loaners.size() > 0) {
			String content = YrdConstants.MessageNotifyConstants.LOAN_SUCCESS_LOANER_CONTENT;
			content = content.replace("var1", trade.getName());
			content = content.replace("var2",
					MoneyUtil.getFormatAmount(trade.getLoanedAmount()));
			content = content.replace(
					"var3",
					MoneyUtil.getFormatAmount(trade.getLoanedAmount()
							- (long) countInvestCutAmount));
			content = content.replace("var4",
					DateUtil.simpleFormatYmdhms(new Date()));
			toLoanerMessage.append(content);
//			messageService.notifyUser(loaners.get(0),
//					toLoanerMessage.toString());
		}
	}

	/**
	 * 执行分润
	 * 
	 * @param inUserId
	 * @param tradeId
	 * @param tradeDetailId
	 * @param tradeCode
	 * @param in
	 * @param out
	 * @param amount
	 * @param date
	 */
	private void doDivision(long inUserId, long outUserId1, long tradeId,
			long tradeDetailId, String tradeCode, String in, String out,
			long amount, Date date) {
		if (amount < 1) {
			return;
		}
		String businessCode = BusinessNumberUtil.gainOutBizNoNumber();
		//定时任务可拉起0-->分润成功1
		int status = 0;
		divisionDetailDao.addDivisionDetail(new DivisionDetail(inUserId,
				businessCode, tradeId, tradeDetailId, amount, status, date));
	}

	/**
	 * 批量添加分润规则明细
	 * 
	 * @param roleIds
	 * @param percentages
	 */
	private void addDivisionRules(long templateId, int[] roleIds,
			double[] percentages) {
		if (roleIds != null && percentages != null) {
			for (int i = 0; i < roleIds.length; i++) {
				divisionRuleDao.addRule(new DivisionRule(roleIds[i],
						templateId, percentages[i]));
			}
		}
	}

	/**
	 * 是否满标
	 * 
	 * @return
	 */
	@Override
	public boolean isFullScale(Trade trade) {
		logger.info("判断是否满标：tradeId->" + trade.getId());
		String method = trade.getSaturationConditionMethod();
		if (YrdConstants.TradeStatus.TRADING != trade.getStatus()) {
			return false;
		}
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

	@Override
	public Pagination<DivisionTemplate> getDivisionTemplatesByCondition(
			String name, String status, int start, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("status", status);
		params.put("start", start);
		params.put("size", size);
		return new Pagination<DivisionTemplate>(start,
				divisionTemplateDao
						.getDivisionTemplatesByConditionsCount(params), size,
				divisionTemplateDao.getDivisionTemplatesByConditions(params));
	}

	/**
	 * 抛出异常
	 * 
	 * @param message
	 */
	private void throwDivisionException(String message) {
		throw new DivisionException(message);
	}

	@Override
	public Page<DivisionDetail> getDivisionByConditions(Long userId,
			Long tradeId) {
		int totalSize = divisionDetailDao.getCountByTradeAndUsrCount(tradeId,
				userId);
		return new Page<DivisionDetail>(0, totalSize, totalSize,
				divisionDetailDao.getCountByTradeAndUsr(tradeId, userId));

	}

	@Override
	public List<DivsionRuleRole> getRuleRole(String name) {

		return this.divisonRuleRoleDao.getLstRulRole(name);
	}

	public void setLoanDemandManager(LoanDemandManager loanDemandManager) {
		this.loanDemandManager = loanDemandManager;
	}

	public String getYrdRoleId() {
		return yrdRoleId;
	}

	public void setYrdRoleId(String yrdRoleId) {
		this.yrdRoleId = yrdRoleId;
	}

	public String getGuaranteeRoleId() {
		return guaranteeRoleId;
	}

	public void setGuaranteeRoleId(String guaranteeRoleId) {
		this.guaranteeRoleId = guaranteeRoleId;
	}

	public String getSponsorRoleId() {
		return sponsorRoleId;
	}

	public void setSponsorRoleId(String sponsorRoleId) {
		this.sponsorRoleId = sponsorRoleId;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserRelationManager(UserRelationManager userRelationManager) {
		this.userRelationManager = userRelationManager;
	}

	public void setUserBaseInfoManager(UserBaseInfoManager userBaseInfoManager) {
		this.userBaseInfoManager = userBaseInfoManager;
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

	public void setInvestRoleId(String investRoleId) {
		this.investRoleId = investRoleId;
	}

	public String getInvestRoleId() {
		return investRoleId;
	}

	public void setYrdYjfExchangeUserName(String yrdYjfExchangeUserName) {
		this.yrdYjfExchangeUserName = yrdYjfExchangeUserName;
	}

	public String getYrdYjfExchangeUserName() throws Exception {
		initializeAccountInfo();
		return yrdYjfExchangeUserName;
	}

	public long getYrdUserId() throws Exception {
		initializeAccountInfo();
		return yrdUserId;
	}

	private void initializeAccountInfo() throws Exception {
		if (!isInitAccountInfo) {
			UserBaseInfoDO exchangeUser = userBaseInfoManager
					.queryByAccountId(AppConstantsUtil.getExchangeAccount());
			UserBaseInfoDO financeUser = userBaseInfoManager
					.queryByAccountId(AppConstantsUtil
							.getProfitSharingAccount());
			this.YrdExchangeUserId = exchangeUser.getUserId();
			this.yrdYjfExchangeUserName = exchangeUser.getAccountId();
			this.yrdUserId = (financeUser.getUserId());
			isInitAccountInfo = true;
		}

	}

	@Override
	public void addLoanAuthRecord(Long userId, long tradeId, String authType)
			throws Exception {
		Trade trade = tradeDao.getByTradeId(tradeId);
		LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
				.getDemandId());
		LoanAuthRecord record = new LoanAuthRecord();
		record.setBaseId(BusinessNumberUtil.gainNumber());
		record.setAuthUserId(userId);
		record.setLoanDemandId(loan.getDemandId());
		record.setNote("担保公司融资放款审核");
		record.setAuthType(authType);
		record.setAuthTime(new Date());
		loanDemandManager.addLoanAuthRecord(record);
		logger.info("tradeId为" + tradeId + "放款审核记录保存成功...");
	}

	/**
	 * 修改模板状态
	 * 
	 * @param templateId
	 * @param status
	 */
	@Override
	public void changeDivisionTemplateStatus(long templateId, String status) {
		divisionTemplateDao.changeStatus(status, templateId);
		sysClearCacheServiceClient.clearCache();

	}

	@Override
	public boolean isUseDivisionTemplate(long templateId) {
		long count = divisionTemplateDao.isUseByDivisionTemplateId(templateId,
				templateId);
		return count != 0;
	}

	@Override
	public void clearCache() {
		divisionTemplateCache.clear();
	}

	/**
	 * 投资人的利润流水
	 * 
	 * @throws Exception
	 * 
	 */
	/*
	 * @Transactional(rollbackFor = Exception.class, value =
	 * "transactionManager")
	 * 
	 * @Override public void addRepayInvestorDivisionAmountFlow(long tradeId,
	 * String repayYjfUserId, long repayUserId) throws Exception {
	 * initializeAccountInfo(); Date date = new Date(); Trade trade =
	 * tradeDao.getByTradeIdWithRowLock(tradeId);
	 * 
	 * LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(trade
	 * .getDemandId()); long divisionTemplateLoanBaseId =
	 * loan.getDivisionTemplateId(); DivisionTemplateLoanDO divisionTemplateLoan
	 * = divisionTemplateYrdService .getByBaseId(divisionTemplateLoanBaseId);
	 * List<DivsionRuleRole> repayRolelist = getRuleRole(String
	 * .valueOf(divisionTemplateLoan.getRepayTemplateId())); // 还款阶段分润 if
	 * (repayRolelist != null && repayRolelist.size() > 0) { // 执行机构转账 for
	 * (DivsionRuleRole role : repayRolelist) { if
	 * (DivisionPhaseEnum.REPAY_PHASE.code() .equals(role.getPhase())) { if
	 * (role.getRoleId() != SysUserRoleEnum.INVESTOR.getValue()) { continue; }
	 * Role currentRole = roleDao.getByRoleId(role.getRoleId());
	 * List<TransferTrade> rolesTransferTrades = transferTradeDao
	 * .getPhaseTransferTrades(trade.getId(),
	 * DivisionPhaseEnum.REPAY_PHASE.code(), currentRole.getCode()); if
	 * (rolesTransferTrades != null && rolesTransferTrades.size() > 0) { for
	 * (TransferTrade curTransferTrade : rolesTransferTrades) {
	 * 
	 * logger.info( "curTransferTrade={} rolesTransferTrades.size()={}",
	 * curTransferTrade, rolesTransferTrades.size());
	 * transferService.doTransfer(tradeId, curTransferTrade.getTradeDetailId(),
	 * curTransferTrade.getYjfUserName(), repayYjfUserId,
	 * curTransferTrade.getAmount(), YrdConstants.TransferComment.YRD_REPAY,
	 * date, curTransferTrade.getUserId(), repayUserId,
	 * AmountFlowEnum.AMOUNT_FLOW_REPAY.code()); } }
	 * 
	 * } } } }
	 */

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public List<TransferTrade> addRepayInvestorDivisionAmountFlow(long tradeId,
			String repayYjfUserId, long repayUserId) throws Exception {
		List<TransferTrade> transferTrades = transferTradeDao
				.getPhaseTransferTrades(tradeId,
						DivisionPhaseEnum.REPAY_PHASE.getCode(), null,
						new String[]{SysUserRoleEnum.INVESTOR.getRoleCode()});
		for (TransferTrade transferTrade : transferTrades) {
			long transferAmount = transferTrade.getAmount();
			transferService.doTransfer(tradeId,
					transferTrade.getTradeDetailId(),
					transferTrade.getYjfUserName(), repayYjfUserId,
					transferAmount, YrdConstants.TransferComment.YRD_REPAY,
					new Date(), transferTrade.getUserId(), repayUserId,
					AmountFlowEnum.AMOUNT_FLOW_REPAY.code());
			tradeDetailDao.updatetStatus(transferTrade.getTradeDetailId(),
					TradeDetailStatusEnum.PS.code());
		}

		return transferTrades;
	}
}
