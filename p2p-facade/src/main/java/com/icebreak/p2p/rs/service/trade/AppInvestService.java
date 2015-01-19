package com.icebreak.p2p.rs.service.trade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.Session;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.util.YrdConstants.CommonConfig;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryRequest;

public class AppInvestService extends ServiceBase implements AppService {
	@Autowired
	private final TradeService tradeService = null;
	@Autowired
	private final DivisionService divisionService = null;
	@Autowired
	private final UserBaseInfoManager userBaseInfoManager = null;
	
	@Autowired
	private LoanDemandManager loanDemandManager;
	//投资金额的倍数
	private final double AVAILABELINVESTMULTIPLE = CommonConfig.AVAILABLE_INVEST_MULTIPLE;
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if (SessionLocalManager.getSessionLocal() == null) {
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		long tradeId = Long.parseLong(params.get("tradeId").toString());
		String password = params.get("password").toString();
		String amount = params.get("amount").toString();
		String token = params.get("token").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		
		TradeDetail tradeDetail = new TradeDetail();
		tradeDetail.setTradeId(tradeId);
		tradeDetail.setAmount(Long.parseLong(amount) * 100);
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		Trade trade = tradeService.getByTradeIdWithRowLock(tradeDetail.getTradeId());
		//		String getToken = (String) Session.getAttribute("token");
		result = doCheckTrade(trade);
		logger.info("移动终端投资参数：tradeId=" + tradeId + ",amount=" + amount + ",token=" + token);
		if (trade != null) {
			if (!((Boolean) result.get("status"))) {
				json.put("code", 0);
				json.put("message", result.get("message"));
				return json;
			}
			result = doCheckInvestor(sessionLocal.getUserId(), trade.getId());
			if (!((Boolean) result.get("status"))) {
				json.put("code", 0);
				json.put("message", result.get("message"));
				return json;
			}
			result = doCheckAmount(trade, sessionLocal.getAccountId(), tradeDetail.getAmount());
			if (!((Boolean) result.get("status"))) {
				json.put("code", 0);
				json.put("message", result.get("message"));
				return json;
			}
			//暂定调用解密接口
			try {
				if (!doCheckPayPassword(sessionLocal.getUserBaseId(), password)) {
					json.put("code", 0);
					json.put("message", "支付密码错误");
					return json;
				}
			} catch (Exception e1) {
				logger.error("支付密码交易异常！投资人id：{}" + sessionLocal.getUserId());
				json.put("code", 0);
				json.put("message", "支付密码错误");
				return json;
			}
			Session.removeAttribute("token");
			tradeDetail.setUserId(sessionLocal.getUserId());
			tradeDetail.setAccountId(sessionLocal.getAccountId());
			tradeDetail.setRoleId(12);
			tradeDetail.setTransferPhase(DivisionPhaseEnum.ORIGINAL_PHASE.code());
			try {
				divisionService.invest(tradeDetail);
				json.put("code", 1);
				json.put("message", "投资成功");
				//开始发消息给投资人
				UserBaseInfoDO notifiedUser = userBaseInfoManager
					.queryByUserBaseId(SessionLocalManager.getSessionLocal().getUserBaseId());
				StringBuilder content = new StringBuilder();
				String messageStr = YrdConstants.MessageNotifyConstants.SUCCESS_INVEST_NOTIFY;
				messageStr = messageStr.replace("var1", trade.getName());
				messageStr = messageStr.replace("var2",
					MoneyUtil.getFormatAmount(tradeDetail.getAmount()));
				content.append(messageStr);
				yrdMessageService.notifyUserByType(notifiedUser, content.toString(),
					YrdConstants.MessageNotifyConstants.INVEST_NOTIFY_TYPE);
			} catch (Exception e) {
				logger.error("处理投资失败！投资人id：{}" + sessionLocal.getUserId());
				json.put("code", 0);
				json.put("message", "投资发生异常");
			}
		} else {
			json.put("code", 0);
			json.put("message", "投资失败，重复提交！");
		}
		return json;
	}
	
	/**
	 * 校验密码
	 * @param payPassword
	 * @return
	 * @throws Exception
	 */
	private boolean doCheckPayPassword(String userBaseId, String payPassword) throws Exception {
		//		payPassword = RSAUtils.decryptStringByJs(payPassword);
		return userBaseInfoManager.validationPayPassword(userBaseId, payPassword) == UserBaseReturnEnum.PASSWORD_SUCCESS;
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
		if (amount % (AVAILABELINVESTMULTIPLE * 1000000) != 0) {
			result.put("message", "投资金额必须为" + AVAILABELINVESTMULTIPLE + "万的整数倍");
			return result;
		}
		if (trade.getLoanedAmount() + amount > trade.getAmount()) {
			result.put("message", "投资金额不能大于该笔借款的可投资金额");
			return result;
		}
		YzzUserAccountQueryResponse queryAccountResult = openApiGatewayService
			.userAccountQuery(new YzzUserAccountQueryRequest(userId));
		if (!queryAccountResult.success()) {
			result.put("message", "资金账户不存在");
			return result;
		}
		if (amount > (new Money(queryAccountResult.getAvailableBalance()).getCent())) {
			result.put("message", "您的可用余额不足，请充值");
			return result;
		}
		result.put("status", true);
		return result;
	}
	
	/**
	 * 校验投资
	 * @param tradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkinvest/{tradeId}", method = RequestMethod.POST)
	public Object checkInvest(@PathVariable long tradeId) {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		return doCheckInvestor(sessionLocal.getUserId(), tradeId);
	}
	
	/**
	 * 校验投资人，不能自己给自己投资
	 * @param investorId
	 * @param tradeId
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> doCheckInvestor(long investorId, long tradeId) {
		List<UserBaseInfoDO> users = null;
		try {
			users = userBaseInfoManager.queryByType("GR", String.valueOf(investorId));
		} catch (Exception e) {
			logger.error("用户查询异常", e);
		}
		if (users != null && users.size() > 0) {
			if (!"normal".equals(users.get(0).getState())) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("status", false);
				result.put("message", "用户状态异常");
				return result;
			}
		}
		boolean b = tradeService.check(tradeId, investorId, SysUserRoleEnum.LOANER.getRoleCode());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", !b);
		if (b) {
			result.put("message", "不能给自己投资哦，亲");
		}
		return result;
	}
	
	/**
	 * 校验交易
	 * @param tradeId
	 * @return
	 */
	private Map<String, Object> doCheckTrade(Trade trade) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", false);
		if (trade == null) {
			result.put("message", "该借款需求存在异常");
			return result;
		}
		LoanDemandDO loan = null;
		try {
			loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
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
	
	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appInvest;
	}
	
}
