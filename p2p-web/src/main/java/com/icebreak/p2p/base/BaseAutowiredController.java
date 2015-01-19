package com.icebreak.p2p.base;

import com.icebreak.p2p.activity.IActivityService;
import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.base.data.BankDataService;
import com.icebreak.p2p.common.services.*;
import com.icebreak.p2p.dal.daointerface.RepayPlanDAO;
import com.icebreak.p2p.daointerface.GoldExperienceDao;
import com.icebreak.p2p.daointerface.PersonalInfoDAO;
import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.daointerface.UserGoldExperienceDao;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.division.DivisionTemplateYrdService;
import com.icebreak.p2p.index.IndexTradeService;
import com.icebreak.p2p.integration.openapi.*;
import com.icebreak.p2p.integration.openapi.RemoteTradeService;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.enums.UserStatusEnum;
import com.icebreak.p2p.integration.openapi.info.ThirdPartAccountInfo;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.login.LoginService;
import com.icebreak.p2p.mail.IMailSendService;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.service.openingbank.api.OpeningBankService;
import com.icebreak.p2p.statistics.AmountStatisticsService;
import com.icebreak.p2p.statistics.UserStatisticsService;
import com.icebreak.p2p.trade.*;
import com.icebreak.p2p.user.*;
import com.icebreak.p2p.util.ApplicationConstant;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.web.util.RateUtil;
import com.icebreak.p2p.ws.enums.TradeFullConditionEnum;
import com.icebreak.p2p.ws.info.CommonAttachmentInfo;
import com.icebreak.util.lang.exception.ResultException;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.APITool;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.message.yzz.YzzUserSpecialRegisterResponse;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;
import com.yiji.openapi.sdk.service.yzz.YzzGatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseAutowiredController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected RemoteTradeService remoteTradeService;
	@Autowired
	protected InvestService investService;
	@Autowired
	protected RepayPlanDAO repayPlanDAO;
	@Autowired
	protected APITool apiTool;
	@Autowired
	protected YzzGatewayService yzzGatewayService;
	@Autowired
	protected OpenApiGatewayService openApiGatewayService;
	@Autowired
	protected PersonalInfoManager personalInfoManager;
	@Autowired
	protected InstitutionsInfoManager institutionsInfoManager;
	@Autowired
	protected UserBaseInfoManager userBaseInfoManager;
	@Autowired
	protected LoanDemandManager loanDemandManager;
	@Autowired
	protected UserRelationManager userRelationManager;
	@Autowired
	protected IMailSendService mailService;
	@Autowired
	protected BankBaseService bankBaseService;

	@Autowired
	protected AuthorityService authorityService;
	@Autowired
	protected DivisionService divisionService;
	@Autowired
	protected TradeService tradeService;
	@Autowired
	protected CommonAttachmentService commonAttachmentService;
	@Autowired
	protected IndexTradeService indexTradeService;
	@Autowired
	protected LoginService loginService;
	@Autowired
	protected DivisionTemplateYrdService divisionTemplateLoanService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected RegisterService registerService;

	@Autowired
	protected IActivityService iActivityService;

	@Autowired
	protected INotifierService notifierService;

	@Autowired
	protected MessageService yrdMessageService;
	@Autowired
	protected TradeDetailService tradeDetailService;
	@Autowired
	protected TradeDetailDao tradeDetailDao;
	@Autowired
	protected UserStatisticsService userStatisticsService;
	@Autowired
	protected AmountStatisticsService amountStatisticsService;
	@Autowired
	protected RechargeFlowService rechargeFlowService;
	@Autowired
	protected DeductYrdService deductYrdService;
	@Autowired
	protected SysParameterService sysParameterService;
	@Autowired
	protected CustomerService customerService;

	@Autowired
	protected OpeningBankService openingBankService;

	@Autowired
	protected FundsTransferService fundsTransferService;
	@Autowired
	protected BankDataService bankDataService;
	@Autowired
	protected WithdrawQueryService withdrawQueryService;

	@Autowired
	protected WithdrawService withdrawService;

	@Autowired
	protected DepositQueryService depositQueryService;

	@Autowired
	protected SmsManagerService smsManagerService;

	@Autowired
	protected DeductDepositService deductDepositService;
	@Autowired
	protected TradeQueryService tradeQueryService;

	@Autowired
	protected SysFunctionConfigService sysFunctionConfigService;
	@Autowired
	protected PersonalInfoDAO personalInfoDAO;
	@Autowired
	protected UserGoldExperienceDao userGoldExperienceDao;
	@Autowired
	protected GoldExperienceDao goldExperienceDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		String[] nameArray = getDateInputNameArray();
		if (nameArray != null && nameArray.length > 0) {
			for (int i = 0; i < nameArray.length; i++) {
				binder.registerCustomEditor(Date.class, nameArray[i],
						new CustomDateEditor(new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss"), true));

			}
		}
		String[] dateDayNameArray = getDateInputDayNameArray();
		if (dateDayNameArray != null && dateDayNameArray.length > 0) {
			for (int i = 0; i < dateDayNameArray.length; i++) {
				binder.registerCustomEditor(Date.class, dateDayNameArray[i],
						new CustomDateEditor(
								new SimpleDateFormat("yyyy-MM-dd"), true));

			}
		}
		String[] moneyNameArray = getMoneyInputNameArray();
		if (dateDayNameArray != null && moneyNameArray.length > 0) {
			for (int i = 0; i < moneyNameArray.length; i++) {
				binder.registerCustomEditor(Money.class, moneyNameArray[i],
						new CommonBindingInitializer());
			}
		}
	}

	protected String[] getDateInputNameArray() {
		return new String[0];
	}

	protected String[] getDateInputDayNameArray() {
		return new String[0];
	}

	protected String[] getMoneyInputNameArray() {
		return new String[0];
	}

	protected OpenApiContext getOpenApiContext() {
		OpenApiContext openApiContext = new OpenApiContext();
		openApiContext
				.setPartnerId(sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_YJF_BUSINESS_USER_ID));
		openApiContext
				.setSecurityCheckKey(sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_SECURITY_CHECK_KEY));
		openApiContext
				.setOpenApiUrl(sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_OPEN_API_URL_KEY));

		openApiContext
				.setNotifyUrl(sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_RETURN_URL_KEY));
		openApiContext.setOpenApiBizNo(BusinessNumberUtil.gainOutBizNoNumber());
		return openApiContext;
	}

	protected void printHttpResponse(HttpServletResponse response,
			com.alibaba.fastjson.JSONObject jsonobj) {
		try {

			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(jsonobj.toJSONString());
		} catch (IOException e) {
			logger.error("response. getWriter print error ", e);
		}
	}

	public ThirdPartAccountInfo convert(YzzUserAccountQueryResponse response) {
		logger.info("【构造支付账户信息】" + response);
		ThirdPartAccountInfo info = new ThirdPartAccountInfo();
		BeanUtils.copyProperties(response, info,new String[]{"creditAmount", "creditBalance",
				"freezeAmount", "systemAmount", "userStatus"});
		info.setFreezeAmount(new Money(response.getFreezeBalance()));
		if ("正常用户".equals(response.getUserStatus())) {
			info.setUserStatus(UserStatusEnum.NORMAL);
		}
		logger.info("【构造支付账户信息】" + info);
		return info;
	}

//	protected String buildURLWithSign(Map<String, String> params)
//			throws UnsupportedEncodingException {
//		String url;
//		params.put("sign", Signatures.sign(params));
//		url = Constants.OPENAPI_GATEWAY + '?'
//				+ HttpUtil.buildParams(Constants.OPENAPI_GATEWAY, params);
//		return url;
//	}

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


	/**
	 * 填充详情到交易对象
	 * @param indexTrade 传入的需要填充信息的交易对象集合
	 * @return 填充信息后的交易对象集合
	 */
	protected Pagination<IndexTrade> fillDetail(Pagination<IndexTrade> indexTrade)
	{
		for (IndexTrade i : indexTrade.getResult()) {
			Trade trade = tradeService.getByTradeId(i.getTradeId());
			long demandId = i.getDemandId();
			try {
				LoanDemandDO loanDemand = loanDemandManager.queryLoanDemandByDemandId(demandId);
				i.setSponsorName(loanDemand.getLoanPassword());
				i.setRepayDivisionWayMsg(loanDemand.getRepayDivisionWayMsg());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (trade.getDeadline().before(new Date())) {
				if (isFullScale(trade)) {
					i.setPercent("100.0%");
				} else {
					i.setPercent(MoneyUtil.getPercentage(i.getLoanedAmount(), i.getAmount(), i.getLeastInvestAmount()));
				}
			} else {
				i.setPercent(MoneyUtil.getPercentage(i.getLoanedAmount(), i.getAmount(), i.getLeastInvestAmount()));
			}

			i.setStrRaate(RateUtil.getRate(i.getRate()));
			i.setPercentnum(i.getPercent().substring(0,i.getPercent().lastIndexOf(".")));
			//设置项目缩略图
			CommonAttachmentInfo attachmentInfo = commonAttachmentService.queryProImage(String.valueOf(demandId));
			i.setProImage(attachmentInfo != null ? attachmentInfo.getRequestPath() : null);
		}

		return indexTrade;
	}

	protected void registYJFAccountAndUpdateLocal(PersonalInfoDO person,
												  UserBaseInfoDO userBaseInfo,Model model) {
		YzzUserSpecialRegisterResponse registerResponse = null;
		try {
			registerResponse = userBaseInfoManager.doRegYJFAccount(
					person, userBaseInfo);
		} catch (Exception e) {
			throw new ResultException("注册失败", "网络异常");
		}
		if (!registerResponse.registSuccess() || StringUtil.isBlank(registerResponse.getUserId())) {
			throw new ResultException("注册失败", registerResponse.getResultMessage());
		}
		logger.info("注册第三方支付账户成功，ID为：" + registerResponse.getUserId());
		String accountId = registerResponse.getUserId();
		userBaseInfo.setState("normal");
		userBaseInfo.setAccountId(accountId);
		try {
			userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
			personalInfoDAO.update(person);
		} catch (Exception e1) {
			throw new ResultException("注册失败", "更新本地数据失败");
		}
		logger.info("更新本地信息完成!");
		model.addAttribute("accountId", accountId);
		model.addAttribute("modifyPwdAddress", registerResponse.getModifyPwdAddress());
	}

}
