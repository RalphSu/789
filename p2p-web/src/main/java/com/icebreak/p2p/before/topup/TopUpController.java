package com.icebreak.p2p.before.topup;

import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.dataobject.SignCardInfo;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.ext.enums.DepositStatusEnum;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.integration.openapi.enums.UserStatusEnum;
import com.icebreak.p2p.integration.openapi.info.ThirdPartAccountInfo;
import com.icebreak.p2p.integration.openapi.order.DepositDeductOrder;
import com.icebreak.p2p.integration.openapi.result.DeductDepositResult;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.sign.SignCardInfoService;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.base.info.BankConfigInfo;
import com.icebreak.p2p.ws.enums.CertTypeEnum;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usercenter/")
public class TopUpController extends UserAccountInfoBaseController {

	@Resource
	private SignCardInfoService signCardInfoService;

	@RequestMapping("deposit")
	public String withholdingIndex(HttpServletRequest request, Model model) {
		ThirdPartAccountInfo accountInfo = this.getAccountInfo(model);
		logger.info("【第三方支付账户信息】" + accountInfo);
		try {
			if (accountInfo.getUserStatus() == UserStatusEnum.NORMAL) {
				SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
				SignCardInfo signCardInfo = new SignCardInfo();
				signCardInfo.setUserId(sessionLocal.getAccountId());
				signCardInfo.setSignType(DivisionTypeEnum.SIGN.code());
				List<SignCardInfo> list = signCardInfoService.queryList(signCardInfo);
				/*快捷支付要用到，先屏蔽
				 * Map<String, SignCardInfo> signBankMap = new HashMap<String, SignCardInfo>();
				for (SignCardInfo cardInfo : list) {
					BankConfigInfo bankConfigInfo = bankDataService.loadBankConfigInfo(cardInfo.getBankShort());
					cardInfo.setBankGifUrl(bankConfigInfo.getLogoUrl());
					signBankMap.put(cardInfo.getBankShort(), cardInfo);
				}
				request.getSession().setAttribute(YrdConstants.SesssionKey.SIGN_BANK_KEY, signBankMap);*/
				model.addAttribute("signBankInfos", list);
				model.addAttribute("thirdPartAccountInfo", accountInfo);
				return returnVm("deposit.vm");
			}
		}catch (Exception e) {
			logger.info("【跳转提现页面异常】" + e.getMessage());
			throw e;
		}
		return null;
	}

	@RequestMapping("depositSubmit")
	public String withholdingIndex(String rechargeAmount, String bankCode, String validateCode,
									Model model, HttpSession session) {
		
		Money money = new Money(rechargeAmount);
		initAccountInfo(model);
		UserBaseInfoDO userBaseInfo = (UserBaseInfoDO) model.asMap().get("userBaseInfo");
		SmsCodeResult smsCodeResult = smsManagerService.verifySmsCode(userBaseInfo.getMobile(),
			SmsBizType.DEPOSIT, validateCode, true);
		if (smsCodeResult.isSuccess()) {
			deduct(money, bankCode, model, session);
		} else {
			model.addAttribute("isSuccess", false);
			model.addAttribute("message", "验证码输入错误");
		}
		
		return returnVm("depositResult.vm");
	}
	
	@SuppressWarnings("unchecked")
	public void deduct(Money money, String bankCode, Model model, HttpSession session) {
		SessionLocal local = SessionLocalManager.getSessionLocal();
		
		Map<String, SignCardInfo> signBankMap = (Map<String, SignCardInfo>) session
			.getAttribute(YrdConstants.SesssionKey.SIGN_BANK_KEY);
		if (signBankMap == null) {
			model.addAttribute("isSuccess", false);
			model.addAttribute("message", "未签约银行");
			return;
		}
		SignCardInfo signCardInfo = signBankMap.get(bankCode);
		if (signCardInfo == null) {
			model.addAttribute("isSuccess", false);
			model.addAttribute("message", "未知的银行bankCode=" + bankCode);
			return;
		}
		if (!StringUtil.equals(signCardInfo.getCardName(), local.getRealName())) {
			model.addAttribute("isSuccess", false);
			model.addAttribute("message", "银行卡账户名和当前用户名称不一致");
			return;
		}
		DepositDeductOrder deductOrder = new DepositDeductOrder();
		deductOrder.setAmount(money);
		deductOrder.setAccountName(local.getAccountName());
		deductOrder.setBankAccountName(signCardInfo.getCardName());
		deductOrder.setBankAccountNo(signCardInfo.getCardNo());
		deductOrder.setBankCode(bankCode);
		deductOrder.setCertNo(signCardInfo.getCertNo());
		deductOrder.setCertType(CertTypeEnum.Identity_Card);
		deductOrder.setProvName("重庆市");
		deductOrder.setCityName("重庆市");
		deductOrder.setUserId(local.getAccountId());
		deductOrder.setMemo("快捷充值");
		deductOrder.setBankName(signCardInfo.getBankName());
		
		DeductDepositResult baseResult = new DeductDepositResult();
		try {
			baseResult = this.deductYrdService.deductDeposit(deductOrder);
			if (baseResult.isSuccess()
				&& baseResult.getDepositStatusEnum() == DepositStatusEnum.SUCCESS) {
				model.addAttribute("isSuccess", baseResult.isSuccess());
				initAccountInfo(model, false);
			} else if (baseResult.isSuccess()) {
				model.addAttribute("isProcessing", true);
			} else {
				model.addAttribute("message", baseResult.getMessage());
			}
			
		} catch (Exception e) {
			logger.error("deductDeposit Exception", e);
			model.addAttribute("isSuccess", false);
			model.addAttribute("message", "系统异常");
		}
		
	}
	
	private String returnVm(String vm) {
		String fullVm = "/before/topup/";
		return fullVm + vm;
	}
	
}
