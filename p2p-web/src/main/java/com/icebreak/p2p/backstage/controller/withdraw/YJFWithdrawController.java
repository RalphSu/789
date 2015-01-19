package com.icebreak.p2p.backstage.controller.withdraw;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.SignCardInfo;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.sign.SignCardInfoService;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.message.URLResult;
import com.yiji.openapi.sdk.message.common.trade.VerifyFacadeRequest;
import com.yiji.openapi.sdk.message.common.trade.VerifyFacadeResponse;
import com.yiji.openapi.sdk.message.yzz.YzzStrengthenWithdrawRequest;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("invest")
public class YJFWithdrawController extends BaseAutowiredController {
	
	@Autowired
	private UserBaseInfoManager userBaseInfoManager;
	@Resource
	private RechargeFlowService rechargeFlowService;
	@Resource
	private SignCardInfoService signCardInfoService;
	@Resource
	private OpenApiGatewayService openApiGatewayService;


	@RequestMapping("towithdraw")
	public String withdraw(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		SignCardInfo signCardInfo = new SignCardInfo();
		if(null != sessionLocal.getAccountId()) {
			signCardInfo.setUserId(sessionLocal.getAccountId());
			signCardInfo.setSignType(DivisionTypeEnum.WITHDRAW.getCode());
			List<SignCardInfo> list = signCardInfoService.queryList(signCardInfo);
			modelMap.addAttribute("cards", list);
		}
		List<BankBasicInfo> bankBasicInfos = this.bankDataService.getBankBasicInfos();
		modelMap.addAttribute("banks", bankBasicInfos);
		return "front/withdrawals/withdraw.vm";
	}


	@RequestMapping(value = "forwardWithdraw")
	public Object forwardWithdraw(String amount, String cardNo, String bankCode, String pactNo, String token,
																HttpSession session, Model model) {
		try {
			//投资人账户校验
 			SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();

			SignCardInfo signCardInfo = null;
			if(null != pactNo && !"".equals(pactNo)) {
				//已绑定的卡信息
				signCardInfo = signCardInfoService.queryByPactNo(pactNo);
			} else {
				PersonalInfoDO personal = personalInfoManager.query(sessionLocal.getUserBaseId());
				String orderNo = BusinessNumberUtil.gainOutBizNoNumber();
				if(!verifyCardInfo(orderNo, personal.getRealName(), personal.getCertNo(), cardNo, bankCode)) {
					model.addAttribute("message", "输入信息有误，请核对后在从新提现！");
					return "common/error.htm";
				}
				signCardInfo = signCardInfoService.queryOnly(personal.getRealName(), personal.getCertNo(), cardNo, sessionLocal.getAccountId(), DivisionTypeEnum.WITHDRAW.getCode());
				if(null == signCardInfo) {
					signCardInfo = buildSignCardInfo(orderNo, cardNo, personal.getRealName(), personal.getCertNo(), bankCode, sessionLocal.getAccountId());
					signCardInfoService.add(signCardInfo);
				}
			}
			//跳转YJF
			YzzStrengthenWithdrawRequest withdrawRequest = new YzzStrengthenWithdrawRequest();
			withdrawRequest.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			withdrawRequest.setUserId(sessionLocal.getAccountId());
			withdrawRequest.setMoney(amount);
			withdrawRequest.setProvince(signCardInfo.getProvince());
			withdrawRequest.setCity(signCardInfo.getCity());
			withdrawRequest.setCardNo(signCardInfo.getCardNo());
			withdrawRequest.setBankCode(signCardInfo.getBankShort());
			withdrawRequest.setDelay("0");
			withdrawRequest.setPayMode("U");
			withdrawRequest.setSource("frameNesting");
			withdrawRequest.setNotifyUrl(Constants.YzzStrengthenWithdrawNotifyUrl);
			withdrawRequest.setReturnUrl(Constants.YzzStrengthenWithdrawReturnUrl);
			
			addRechargeFlow(sessionLocal, withdrawRequest);
			logger.info("组装跳转易记付提现请求：" + withdrawRequest);
			URLResult result = yzzGatewayService.jumpToYJF(withdrawRequest);
			logger.info("组装跳转易记付提现请求结果：" + result);
			return "redirect:" + result.getUrl();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【提现系统异常】" + e.getMessage());
			model.addAttribute("message", "系统异常,请稍后再试！");
			return "common/error.htm";
		}
	}

	/**
	 *
	 * @param orderNo
	 * @param cardNo
	 * @param cardName
	 * @param certNo
	 * @param bankCode
	 * @param accountId
	 * @return
	 */
	private SignCardInfo buildSignCardInfo(String orderNo, String cardNo, String cardName, String certNo, String bankCode, String accountId) {
		SignCardInfo signCardInfo = new SignCardInfo();
		signCardInfo.setPactNo(orderNo);
		signCardInfo.setCardNo(cardNo);
		signCardInfo.setCardName(cardName);
		signCardInfo.setCertNo(certNo);
		signCardInfo.setBankShort(bankCode);
		signCardInfo.setUserId(accountId);
		signCardInfo.setCardType("D");
		signCardInfo.setStatus("PACT");
		signCardInfo.setSignType(DivisionTypeEnum.WITHDRAW.getCode());//绑卡类型
		return signCardInfo;
	}

	private void addRechargeFlow(SessionLocal sessionLocal,
								YzzStrengthenWithdrawRequest withdrawRequest) {
		RechargeFlow rechargeFlow = new RechargeFlow();
		rechargeFlow.setLocalNo(withdrawRequest.getOrderNo());
		rechargeFlow.setPayType(DivisionTypeEnum.WITHDRAW.getCode());
		rechargeFlow.setUserId(sessionLocal.getUserId());
		rechargeFlow.setYjfAccountId(sessionLocal.getAccountId());
		rechargeFlow.setAmount((new Money(withdrawRequest.getMoney())).getCent());
		rechargeFlow.setPayTime(new Date());
		rechargeFlow.setStatus(-1);
		rechargeFlow.setPayMemo("提现到卡");
		try {
			rechargeFlowService.addRechargeFlow(rechargeFlow);
		} catch (Exception e) {
			logger.error("添加提现收支明细失败", e);
		}
	}


	/**
	 * 卡姓名校验
	 * @param orderNo
	 * @param cardName
	 * @param certNo
	 * @param cardNo
	 * @param bankCode
	 * @return
	 */
	private boolean verifyCardInfo(String orderNo, String cardName, String certNo, String cardNo, String bankCode) {
		VerifyFacadeRequest facadeRequest = new VerifyFacadeRequest();
		facadeRequest.setAccountName(cardName);
		facadeRequest.setAccountNo(cardNo);
		facadeRequest.setBankCode(bankCode);
		facadeRequest.setCertNo(certNo);
		VerifyFacadeResponse response = openApiGatewayService.verifyFacade(facadeRequest);
		if("T".equals(response.getSuccess())) {
			return true;
		}
		return false;
	}

}
