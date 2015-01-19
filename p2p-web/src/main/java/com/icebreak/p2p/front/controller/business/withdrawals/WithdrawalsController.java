package com.icebreak.p2p.front.controller.business.withdrawals;

import java.text.NumberFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.BankInfo;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.integration.openapi.enums.UserStatusEnum;
import com.icebreak.p2p.integration.openapi.info.ThirdPartAccountInfo;
import com.icebreak.p2p.integration.openapi.order.WithdrawOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.WithdrawalService;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.util.AESEncrypt;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.ws.enums.PayTypeEnum;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.MoneyUtil;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;

@Controller
@RequestMapping("withdrawals")
public class WithdrawalsController extends UserAccountInfoBaseController {
	@Autowired
	WithdrawalService withdrawalService;
	private final String VM_PATH = "/front/withdrawals/";
	
	private void getAccount(Model model) {
		//资金资料
		YzzUserAccountQueryResponse account = apiTool.queryUserAccount(SessionLocalManager
			.getSessionLocal().getAccountId());
		
		if (account.success()) {
			
			model.addAttribute("balance", MoneyUtil.toStandardString(account.getBalance()));
			model.addAttribute("freezeAmount",
				MoneyUtil.toStandardString(account.getFreezeBalance()));
			model.addAttribute("availableBalance",
				MoneyUtil.toStandardString(account.getAvailableBalance()));
		} else {
			model.addAttribute("balance", "资金信息查询失败");
			model.addAttribute("freezeAmount", "资金信息查询失败");
			model.addAttribute("availableBalance", "资金信息查询失败");
		}
		
	}

	@RequestMapping("newLaunchWithdrawals")
	private String newLaunchWithdrawals(HttpSession session, Model model, HttpServletResponse response)
			throws Exception {

		session.setAttribute("current", 1);
		this.getAccountInfo(model);
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);

		if (!"IS".equals(userBaseInfo.getRealNameAuthentication())) {
			model.addAttribute("fail", "实名认证未通过！");
			return VM_PATH + "cardDraw.vm";
		}
		if (!"normal".equals(userBaseInfo.getState())) {
			model.addAttribute("fail", "帐户状态异常,无法提现申请！");
			return VM_PATH + "cardDraw.vm";
		}
		ThirdPartAccountInfo accountInfo = this.getAccountInfo(model);
		if (accountInfo.getUserStatus() == UserStatusEnum.UNACTIVATED) {
			model.addAttribute("fail", "支付账户未激活！");
			return VM_PATH + "cardDraw.vm";
		}

		String accountId = SessionLocalManager.getSessionLocal().getAccountId();
		WithdrawOrder order = new WithdrawOrder();
		order.setUserId(accountId);
		WithdrawResult withdrawResult = this.withdrawService.gotoWithdrawUrl(order,
				this.getOpenApiContext());
		model.addAttribute("withdrawUrl", withdrawResult.getUrl());
		return VM_PATH + "cardDraw.vm";
	}
	
	@RequestMapping("launchWithdrawals")
	private String launchWithdrawals(HttpSession session, Model model, HttpServletResponse response)
																									throws Exception {
		
		session.setAttribute("current", 1);
		this.getAccountInfo(model);
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		
		if (!"IS".equals(userBaseInfo.getRealNameAuthentication())) {
			model.addAttribute("fail", "实名认证未通过！");
			return VM_PATH + "launchWithdrawals.vm";
		}
		if (!"normal".equals(userBaseInfo.getState())) {
			model.addAttribute("fail", "帐户状态异常,无法提现申请！");
			return VM_PATH + "launchWithdrawals.vm";
		}
		ThirdPartAccountInfo accountInfo = this.getAccountInfo(model);
		if (accountInfo.getUserStatus() == UserStatusEnum.UNACTIVATED) {
			model.addAttribute("fail", "支付账户未激活！");
			return VM_PATH + "launchWithdrawals.vm";
		}
		
		String accountId = SessionLocalManager.getSessionLocal().getAccountId();
		WithdrawOrder order = new WithdrawOrder();
		order.setUserId(accountId);
		WithdrawResult withdrawResult = this.withdrawService.gotoWithdrawUrl(order,
			this.getOpenApiContext());
		model.addAttribute("withdrawUrl", withdrawResult.getUrl());
		return VM_PATH + "launchWithdrawals.vm";
		
		//		
		//		if (StringUtil.isBlank(bankCardNo_4)) {
		//			this.getAccount(model);
		//			model.addAttribute("reason", "未关联银行卡");
		//			return VM_PATH + "completeWithdrawalsNO.vm";
		//		}
		//		
		//		double difference = bankBaseService.getDifference(bankInfo, bankCardNo,
		//			PayTypeEnum.WITHDRAW.code());
		//		NumberFormat nf = NumberFormat.getInstance();
		//		nf.setGroupingUsed(false);
		//		model.addAttribute("bankName", bankInfo.getBankName());
		//		model.addAttribute("singleWithdrawalLimit", bankInfo.getSingleWithdrawalLimit());
		//		model.addAttribute("oddWithdrawalLimit", bankInfo.getOddWithdrawalLimit());
		//		model.addAttribute("difference", nf.format(difference));
		//		model.addAttribute("money", MoneyUtil.money2Double(money));
		//		Map<String, Object> conditions = new HashMap<String, Object>();
		//		conditions.put("userId", SessionLocalManager.getSessionLocal().getUserId());
		//		conditions.put("giftType", PayTypeEnum.WITHDRAW.code());
		//		long giftCount = iActivityService.getActivityGiftCount(conditions);
		//		if (giftCount > 0) {
		//			model.addAttribute("showGift", "yes");
		//		}
		//		model.addAttribute("giftCount", giftCount);
		//		return VM_PATH + "launchWithdrawals.vm";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("confirmhWithdrawals")
	private String confirmhWithdrawals(String bankCardNo_4, String banklogo, String real,
										String fees, String amount, String coupons,
										HttpSession session, Model model,
										HttpServletResponse response) throws Exception {
		
		if (StringUtil.isBlank(bankCardNo_4)) {
			this.getAccount(model);
			model.addAttribute("reason", "未关联银行卡");
			return VM_PATH + "completeWithdrawalsNO.vm";
		}
		if (StringUtil.isBlank(banklogo)
			|| StringUtil.isBlank(SessionLocalManager.getSessionLocal().getRealName())) {
			this.launchWithdrawals(session, model, response);
		}
		BankInfo bankInfo = new BankInfo();
		String bankCardNo = "";
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		if ("GR".equals(userBaseInfo.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			bankInfo = bankBaseService.query19BankInfo(personalInfo.getBankType());
			bankCardNo = personalInfo.getBankCardNo();
			model.addAttribute("bankOpenName", personalInfo.getBankOpenName());
			model.addAttribute("bankCardNo_4", personalInfo.getBankCardNo());
		}
		if ("JG".equals(userBaseInfo.getType())) {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			bankInfo = bankBaseService.query19BankInfo(institutionsInfo.getBankType());
			bankCardNo = institutionsInfo.getBankCardNo();
			model.addAttribute("bankOpenName", institutionsInfo.getAccountId());
			model.addAttribute("bankCardNo_4", institutionsInfo.getBankCardNo());
		}
		
		double difference = bankBaseService.getDifference(bankInfo, bankCardNo,
			PayTypeEnum.WITHDRAW.code());
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		if (Double.parseDouble(amount) > bankInfo.getSingleWithdrawalLimit()) {
			this.getAccount(model);
			model.addAttribute("reason", "您的划出金额大于银行单笔可划出金额。您绑定的" + bankInfo.getBankName()
											+ "的单日划出限额为" + bankInfo.getOddDeductLimit() + "元,单笔限额为"
											+ bankInfo.getSingleWithdrawalLimit() + "元。您目前可划出额度为"
											+ nf.format(difference) + "元");
			return VM_PATH + "completeWithdrawalsNO.vm";
		} else if (Double.parseDouble(amount) > difference) {
			this.getAccount(model);
			model.addAttribute("reason", "您的划出金额大于单日可划出金额。您绑定的" + bankInfo.getBankName()
											+ "的单日划出限额为" + bankInfo.getOddDeductLimit() + "元,单笔限额为"
											+ bankInfo.getSingleWithdrawalLimit() + "元。您目前可划出额度为"
											+ nf.format(difference) + "元");
			return VM_PATH + "completeWithdrawalsNO.vm";
		}
		model.addAttribute("amount", amount);
		model.addAttribute("banklogo", banklogo);
		model.addAttribute("realName", SessionLocalManager.getSessionLocal().getRealName());
		model.addAttribute("real", real);
		model.addAttribute("fees", fees);
		model.addAttribute("coupons", coupons);
		model.addAttribute("date",
			DateUtil.simpleDateFormatmdhChinese(DateUtil.getAfterDay(new Date())));
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return VM_PATH + "completeWithdrawals.vm";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("completeWithdrawals")
	private String completeWithdrawals(String amount, String payPassword, String token,
										String coupons, HttpSession session, Model model)
																							throws Exception {
		String getToken = (String) session.getAttribute("token");
		String key = (String) session.getAttribute(session.getId());
		AESEncrypt aesEncrypt = new AESEncrypt();
		aesEncrypt.setIvParameter(key);
		aesEncrypt.setsKey(key);
		try {
			payPassword = aesEncrypt.decrypt(payPassword);
		} catch (Exception e) {
			logger.error("decrypt异常{}", e.getMessage(), e);
			return VM_PATH + "completeWithdrawalsNO.vm";
		}
		UserBaseReturnEnum returnEnum = userBaseInfoManager.validationPayPassword(
			SessionLocalManager.getSessionLocal().getUserBaseId(), payPassword);
		model.addAttribute("date",
			DateUtil.simpleDateFormatmdhChinese(DateUtil.getAfterDay(new Date())));
		model.addAttribute("amount", amount);
		this.getAccount(model);
		if (returnEnum == UserBaseReturnEnum.PASSWORD_SUCCESS && token.equals(getToken)) {
			session.removeAttribute("token");
			//TODO 调取体现接口
			//			boolean bool= this.withdrawals(amount);
			JSONObject json = withdrawalService.applyWithdrawOpenApi(amount, coupons);
			this.getAccount(model);
			if ((Integer) json.get("code") == 1) {
				session.setAttribute("ws", "success");
				return VM_PATH + "completeWithdrawalsOK.vm";
			} else {
				model.addAttribute("reason", json.get("message"));
				session.setAttribute("ws", "false");
				return VM_PATH + "completeWithdrawalsNO.vm";
			}
		} else {
			String ws = (String) session.getAttribute("ws");
			if ("success".equals(ws)) {
				return VM_PATH + "completeWithdrawalsOK.vm";
			} else {
				model.addAttribute("reason", "处理提现失败");
				return VM_PATH + "completeWithdrawalsNO.vm";
			}
		}
	}
	
}
