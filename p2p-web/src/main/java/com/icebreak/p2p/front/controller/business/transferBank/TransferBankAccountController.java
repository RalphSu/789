package com.icebreak.p2p.front.controller.business.transferBank;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.TransferBankAccount;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.util.lang.util.money.MoneyUtil;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("transferBankAccount")
public class TransferBankAccountController extends BaseAutowiredController {
	private static String PATH = "/front/business/transferBank/";
	
	@RequestMapping("launchTransferBankAccount")
	public String launchTransferBankAccount(HttpSession session, Model model) throws Exception {
		String transfer = (String) session.getAttribute("transfer");
		if ("no".equals(transfer)) {
			model.addAttribute("faile", "faile");
			return PATH + "launchTransferBankAccount.vm";
		}
		String money = "0";
		
		YzzUserAccountQueryResponse account = apiTool.queryUserAccount(SessionLocalManager
			.getSessionLocal().getAccountId());
		
		if (account.success()) {
			money = MoneyUtil.toStandardString(account.getAvailableBalance());
		}
		List<BankBasicInfo> bankListPPM = this.bankDataService.getBankBasicInfos();//获取所有银行
		model.addAttribute("bankList", bankListPPM);
		model.addAttribute("money", money);
		return PATH + "launchTransferBankAccount.vm";
	}
	
	@RequestMapping("confirmhTransferBankAccount")
	public String confirmhTransferBankAccount(TransferBankAccount trans, String banklogo,
												HttpSession session, Model model) {
		if ("P".equals(trans.getTransferType())) {
			boolean flag = CommonUtil.checkIdentifyCardNum(trans.getCertNo());
			if (!flag) {
				model.addAttribute("reason", "身份证号错误 ！请填写正确身份证号。");
				return PATH + "completeNO.vm";
			}
			
			//			ValidationBankCardInfo validationCardInfo = new ValidationBankCardInfo();
			//			String extendId = BusinessNumberUtil.gainNumber();
			//			validationCardInfo.setExtendId(extendId);
			//			validationCardInfo.setAccountName(trans.getRealName());
			//			validationCardInfo.setAccountNo(trans.getBankAccountNo());
			//			validationCardInfo.setBankCode(trans.getBankCode());
			//			//"C", "信用卡类型" \"D", "借记卡类型" 平台只接受借记卡
			//			validationCardInfo.setCardType("D");
			//			String cardNo = trans.getCertNo();//二代身份证
			//			String cardOne = cardNo.substring(0, 6) + cardNo.substring(8, 17);//一代身份证
			//			validationCardInfo.setCertNo(cardNo);
			logger.info("申请转账到卡验证个人银行卡信息：" + ",卡号：" + trans.getBankAccountNo() + ",开户名="
						+ trans.getRealName() + ",开户银行=" + trans.getBankName());
			if (YrdConstants.BankCodes.filteredBankCodes.indexOf(trans.getBankCode()) < 0) {
				OpeningBankQueryOrder queryOrder = new OpeningBankQueryOrder();
				queryOrder.setCardNumber(trans.getBankAccountNo());
				queryOrder.setAccountName(trans.getRealName());
				queryOrder.setBankCode(trans.getBankCode());
				queryOrder.setCardType(CardTypeEnum.JJ);
				queryOrder.setCertNo(trans.getCertNo());
				BankInfoResult bankInfoResult = openingBankService.findCardByCardNo(queryOrder);
				boolean isValidate = false;
				if (bankInfoResult.getCardInfo() != null) {
					if (bankInfoResult.getCardInfo().getCardType() == CardTypeEnum.JJ) {
						isValidate = true;
					}
				}
				if (!isValidate) {
					logger.error("银行卡验证失败原因：{}", bankInfoResult);
					model.addAttribute("reason", "验证银行卡信息失败！请正确填写姓名、身份证号、银行卡号");
					return PATH + "completeNO.vm";
				}
			}
		}
		model.addAttribute("info", trans);
		model.addAttribute("banklogo", banklogo);
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return PATH + "completeTransferBankAccount.vm";
	}
	
	@RequestMapping("completeTransferBankAccount")
	public String completeTransferBankAccount(TransferBankAccount trans, String payPassword,
												String token, HttpSession session, Model model)
																								throws Exception {
		return null;
	}
}
