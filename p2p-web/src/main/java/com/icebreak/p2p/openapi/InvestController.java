package com.icebreak.p2p.openapi;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserGoldExperienceDO;
import com.icebreak.p2p.integration.openapi.result.InvestReturnRequest;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.*;
import com.icebreak.p2p.web.util.WebUtil;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.APITool;
import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("invest")
public class InvestController extends BaseAutowiredController {

	// 投资金额的倍数
	private final double AVAILABELINVESTMULTIPLE = ApplicationConstant.AVAILABLE_INVEST_MULTIPLE;

	@ResponseBody
	@RequestMapping(value = "goinvest", method = RequestMethod.POST)
	public Object goinvest(Long tradeId, Long amount, String token,
			HttpSession session, HttpServletRequest request, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("investStatus", false);

		// 重复提交校验
		String getToken = (String) session.getAttribute("token");
		logger.info("token=" + token + ",distributed token=" + getToken);
		if (token == null || !token.equals(getToken)) {
			result.put("message", "请不要重复申请投资");
			return result;
		}
		// 交易校验
		Trade trade = tradeService.getByTradeIdWithRowLock(tradeId);
		result = doCheckTrade(trade, result);
		Boolean b = (Boolean) result.get("status");
		if (b == null || !b) {
			return result;
		}
		// 投资人账户校验
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal==null) {
			result.put("isLogin", false);
			return result;
		}
		result = doCheckInvestor(sessionLocal.getUserId(), trade.getId(),
				result);
		b = (Boolean) result.get("status");
		if (b == null || !b) {
			return result;
		}

		long realPayMoney = amount;
		String sVirtualMoney = request.getParameter("virtualMoney");
		Money virtualMoney = Money.zero();
		if (StringUtil.isNotBlank(sVirtualMoney)) {
			virtualMoney = new Money(sVirtualMoney);
			if(virtualMoney.getCent() > amount) {
				result.put("message", "投资金额不能小于体验金金额");
				return result;
			}

			//判断此是否有可使用的虚拟货币
			//判断此交易可以使用虚拟货币
			UserGoldExperienceDO userGoldExp = new UserGoldExperienceDO();
			userGoldExp.setUserId(sessionLocal.getUserId());
			userGoldExp.setStatus("1");
			List<UserGoldExperienceDO> virtualMoneyList = userGoldExperienceDao.query(userGoldExp);
			if (ListUtil.isNotEmpty(virtualMoneyList)) {
				if(!allowUseVirtalMoney(trade)){
					//如果不允许使用体验金
					result.put("message", "本产品不能使用体验金!");
					return result;
				}
				UserGoldExperienceDO virtualMoneyDO = virtualMoneyList.get(0);
				BigDecimal dbVirtualMoney = virtualMoneyDO.getAmount();
				if ((new Money(dbVirtualMoney)).equals(virtualMoney)) {
					//如果有体验金 真实付款金额为投资金额减去体验金
					realPayMoney = amount - virtualMoney.getCent();
					trade.setNote(String.valueOf(virtualMoneyDO.getId()));
				} else {
					result.put("message", "输入的体验金与实际体验不符");
					return result;
				}
			}
		}


		// 校验金额
		result = doCheckAmount(trade, sessionLocal.getAccountId(), realPayMoney,
				result);
		b = (Boolean) result.get("status");
		if (b == null || !b) {
			return result;
		}
		session.setAttribute("amount", amount);
		Map<String, String> params = APITool.initBaseParams("payerApply");
		params.put("returnUrl", Constants.InvestReturnUrl);
		params.put("notifyUrl", Constants.investNotifyUrl);
		params.put("errorNotifyUrl", Constants.investNotifyUrl);
		params.put("payerUserId", sessionLocal.getAccountId());
		params.put("tradeNo", trade.getCode());
		params.put("tradeAmount", MoneyUtil.getMoney(realPayMoney));
		params.put("tradeName", trade.getName());
		String tradeNote = trade.getNote();
		if(tradeNote == null) tradeNote="";

		//因为tradeNode将放在url中传输，把敏感符号替换成全角的
		tradeNote = StringUtil.replaceHtml(tradeNote);
		tradeNote = tradeNote.replaceAll("&", "＆");
		tradeNote = tradeNote.replaceAll("\\?", "？");
		tradeNote = tradeNote.replaceAll("%", "％");
		tradeNote = tradeNote.replaceAll("=", "＝");

		if(StringUtil.getWordCount(tradeNote) > 120){
			try {
				params.put("tradeMemo", StringUtil.subStr(tradeNote, 110) + "...");
			} catch (UnsupportedEncodingException e) {}
		}else{
			params.put("tradeMemo", tradeNote);
		}


		String orderNo = params.get("orderNo");
		tradeService.saveInvest(trade, realPayMoney, orderNo);

		String url = null;
		try {
			url = apiTool.makeRequestUrl(params);
		} catch (Exception e) {
			logger.error("Invest occur exception", e);
			result.put("message", "出现未知异常，请重试或则联系客服人员。");
			return result;
		}

		if (url != null) {
			result.put("redirectURL", url);
			result.put("investStatus", true);
		}
		return result;
	}

	private boolean allowUseVirtalMoney(Trade trade) {
		return StringUtil.equalsIgnoreCase(trade.getIsJoinActivity(), "Y");
	}

	@ResponseBody
	@RequestMapping(value = "return/invest", method = RequestMethod.POST)
	public Object repayNotify(HttpServletRequest request) {

		Map<String, String> params = WebUtil.getRequestMap(request);
		InvestReturnRequest returnRequest = new InvestReturnRequest();
		MiscUtil.setBeanByMap(params, returnRequest);

		return null;
	}


	/**
	 * 校验是否可以投资
	 * 
	 * @param tradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checktrade/{tradeId}", method = RequestMethod.POST)
	public Object checkTrade(@PathVariable long tradeId) {
		Map<String, Object> result = new HashMap<String, Object>();
		return doCheckTrade(tradeService.getByTradeId(tradeId), result);
	}


	/**
	 * 校验支付金额
	 * 
	 * @param amount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkamount/{tradeId}", method = RequestMethod.POST)
	public Object checkAmount(@PathVariable long tradeId, Long amount) {
		Trade trade = tradeService.getByTradeId(tradeId);
		Map<String, Object> result = new HashMap<String, Object>();
		return doCheckAmount(trade, SessionLocalManager.getSessionLocal()
				.getAccountId(), amount, result);
	}


	/**
	 * 校验金额
	 * 
	 * @param amount
	 * @return
	 */
	private Map<String, Object> doCheckAmount(Trade trade, String userId,
			Long amount, Map<String, Object> result) {
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
		//1元起投就去掉了这个整数倍验证
//		if (amount % (AVAILABELINVESTMULTIPLE * 10000) != 0) {
//			result.put("message", "投资金额必须为" + AVAILABELINVESTMULTIPLE * 100
//					+ "元的整数倍");
//			return result;
//		}
		if (trade.getLoanedAmount() + amount > trade.getAmount()) {
			result.put("message", "投资金额不能大于该笔借款的可投资金额");
			return result;
		}

		YzzUserAccountQueryResponse account = apiTool
				.queryUserAccount(SessionLocalManager.getSessionLocal()
						.getAccountId());
		logger.info("投资查询资金账户" + account);
		if (!account.success()) {
			result.put("message", "资金账户不存在");
			return result;
		}
		if (amount > (new Money(account.getAvailableBalance())).getCent()) {
			result.put("message", "您的可用余额不足，请充值");
			result.put("code", "-1");
			return result;
		}
		result.put("status", true);
		return result;
	}

	/**
	 * 查看借款需求详情
	 * 
	 * @param demandId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lookup/{demandId},{tradeId}")
	public String lookup(@PathVariable long demandId,
			@PathVariable long tradeId, HttpSession session, Model model)
			throws Exception {
		String token = UUID.randomUUID().toString();
		Trade trade = tradeService.getByTradeId(tradeId);
		model.addAttribute("investableAmount",
				trade.getAmount() - trade.getLoanedAmount());
		model.addAttribute("item",
				loanDemandManager.queryLoanDemandByDemandId(demandId));
		model.addAttribute("tradeId", tradeId);
		model.addAttribute("trade", trade);
		session.setAttribute("token", token);
		return "front/index/index_invest_detail.vm";
	}

	/**
	 * 校验投资
	 * 
	 * @param tradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkinvest/{tradeId}", method = RequestMethod.POST)
	public Object checkInvest(@PathVariable long tradeId) {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		Map<String, Object> result = new HashMap<String, Object>();
		return doCheckInvestor(sessionLocal.getUserId(), tradeId, result);
	}

	@ResponseBody
	@RequestMapping(value = "isInvest", method = RequestMethod.POST)
	public Object isInvest(long demandId) {
		JSONObject json = new JSONObject();
		Date date = new Date();
		try {
			LoanDemandDO loan = loanDemandManager
					.queryLoanDemandByDemandId(demandId);
			if (loan.getInvestAvalibleTime() != null) {
				if (date.before(loan.getInvestAvalibleTime())) {
					json.put("code", 2);
					json.put("message", "暂不能进行投资");
				} else {
					json.put("code", 1);
					json.put("message", "可以进行投资");
				}
			} else {
				json.put("code", 1);
				json.put("message", "可以进行投资");
			}
		} catch (Exception e) {
			logger.error("时间转换异常", e);
			json.put("code", 2);
			json.put("message", "暂不能进行投资");
		}
		return json;
	}

	/**
	 * 校验交易
	 * 
	 * @param trade
	 * @return
	 */
	private Map<String, Object> doCheckTrade(Trade trade,
			Map<String, Object> result) {
		result.put("status", false);
		if (trade == null) {
			result.put("message", "该借款需求存在异常");
			return result;
		}
		LoanDemandDO loan = null;
		try {
			loan = loanDemandManager.queryLoanDemandByDemandId(trade
					.getDemandId());
		} catch (Exception e) {
			logger.error("查询借款需求失败", e);
		}
		Date curDate = new Date();
		if (loan.getInvestAvalibleTime() != null) {
			if (curDate.before(loan.getInvestAvalibleTime())) {
				result.put("message", "还没到投资时间");
				return result;
			}
		}
		if (trade.getStatus() != YrdConstants.TradeStatus.TRADING) {
			result.put("message", "该借款需不处于幕资阶段，不能进行投资");
		} else {
			result.put("status", true);
		}
		return result;
	}

	/**
	 * 校验投资人，不能自己给自己投资
	 * 
	 * @param investorId
	 * @param tradeId
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> doCheckInvestor(long investorId, long tradeId,
			Map<String, Object> result) {
		List<UserBaseInfoDO> users = null;
		try {
			users = userBaseInfoManager.queryByType("GR",
					String.valueOf(investorId));
		} catch (Exception e) {
			logger.error("用户查询异常", e);
		}
		if (users != null && users.size() > 0) {
			if (!"normal".equals(users.get(0).getState())) {
				result.put("status", false);
				result.put("message", "用户状态异常");
				return result;
			}
		}
		boolean b = tradeService.check(tradeId, investorId,
				SysUserRoleEnum.LOANER.getRoleCode());
		result.put("status", !b);
		if (b) {
			result.put("message", "不能给自己投资哦，亲");
		}
		return result;
	}

}
