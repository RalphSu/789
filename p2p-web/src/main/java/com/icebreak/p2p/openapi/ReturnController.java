package com.icebreak.p2p.openapi;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dal.dataobject.RepayPlanDO;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.SignCardInfo;
import com.icebreak.p2p.integration.openapi.result.InvestReturnRequest;
import com.icebreak.p2p.integration.openapi.result.RepayReturnRequest;
import com.icebreak.p2p.notify.RepayNotifyHandler;
import com.icebreak.p2p.sign.SignCardInfoService;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.web.util.WebUtil;
import com.icebreak.p2p.ws.enums.RepayPlanStatusEnum;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.message.common.funds.EBankDepositApplyResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("boot")
public class ReturnController extends BaseAutowiredController {
	
	private final String VM_PATH = "front/return/";

	@Resource
	private RepayNotifyHandler repayNotifyHandler;

	@Resource
	private SignCardInfoService signCardInfoService;

	/**
	 * 投资结果回推
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "return/invest", method = RequestMethod.POST)
	public Object investReturn(HttpServletRequest request, Model model) {

		Map<String, String> params = WebUtil.getRequestMap(request);
		InvestReturnRequest returnRequest = new InvestReturnRequest();
		MiscUtil.setBeanByMap(params, returnRequest);

		try {
			investService.investReturn(returnRequest);
		} catch (Exception e) {
			logger.error("投资回推处理失败", e);
		}
		model.addAttribute("result", returnRequest);
		return VM_PATH + "resultPage.vm";
	}

	/**
	 * 还款结果
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "return/repay")
	public Object repayReturn(HttpServletRequest request) {


		String tradeNo = request.getParameter("tradeNo");
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		Map<String, String> params = WebUtil.getRequestMap(request);
		RepayReturnRequest returnRepay = new RepayReturnRequest();
		MiscUtil.setBeanByMap(params, returnRepay);

		RepayPlanDO queryParam = new RepayPlanDO();
		queryParam.setRepayNo(returnRepay.getOrderNo());
		List<RepayPlanDO> planList = repayPlanDAO.query(queryParam);
		RepayPlanDO repayPlan = null;
		if (ListUtil.isNotEmpty(planList)) {
			repayPlan = planList.get(0);

			RepayPlanStatusEnum status = null;
			if(StringUtil.equalsIgnoreCase("trade_success",returnRepay.getTradeStatus())) {
				status = RepayPlanStatusEnum.APPLY_SUCCESS;
			} else if (StringUtil.equalsIgnoreCase("trade_error",returnRepay.getTradeStatus())) {
				status = RepayPlanStatusEnum.APPLY_FAIL;
			}
			repayPlan.setStatus(status.getCode());
			repayPlan.setCreateBatchNo(returnRepay.getCreateBatchNo());
			repayPlanDAO.update(repayPlan);
		}

		return VM_PATH + "resultPage.vm";
	}

	/**
	 * 签约结果回推
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "return/signManyBank")
	public String signManyBankReturn(HttpServletRequest request, SignCardInfo signCardInfo) {
//		Map<String, String> params = WebUtil.getRequestMap(request);
//		RepayReturnRequest returnRepay = new RepayReturnRequest();
//		MiscUtil.setBeanByMap(params, returnRepay);
		signCardInfoService.add(signCardInfo);
//		repayNotifyHandler.handleNotify(nty);
		return "/";
	}


	/**
	 * 网银充值回跳
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "return/deposit", method = RequestMethod.POST)
	public Object eBankDepositReturn(HttpServletRequest request, Model model) {

		Map<String, String> params = WebUtil.getRequestMap(request);
		EBankDepositApplyResponse response = new EBankDepositApplyResponse();
		MiscUtil.setBeanByMap(params, response);

		try {
			RechargeFlow flow = rechargeFlowService.queryByLocalNo(response.getOrderNo());
			//如果后台通知已经回来了，则不需要再次处理了
			if (flow.getStatus() != 1) {
				flow.setOutBizNo(response.getDepositId());
				if (StringUtil.equalsIgnoreCase("true", response.getIsSuccess())) {
					flow.setStatus(2);
				}
				rechargeFlowService.updateStatus(flow);
			}
		} catch (Exception e) {
			logger.error("处理失败", e);
		}
		model.addAttribute("result", response.getIsSuccess());
		return VM_PATH + "resultPage.vm";
	}

	@RequestMapping(value = "return/test")
	public Object test(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", "20141107010000067557");//会员号 必填
		param.put("upUserNo", "20141104020000065851");//商户码  必填
		param.put("pactNo", "00000000000000002399");
		param.put("cardNo", "6228480471036223413");
		param.put("certNo", "410222198906094026");
		param.put("certType", "D");
		param.put("cardName", "大胖");
		param.put("userPhoneNo", "15826138575");
		param.put("bankShort", "ABC");
		param.put("bankName", "农业银行");
		param.put("status", "PACT");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("http://localhost:8888/boot/return/signManyBank"), param);
		return modelAndView;
	}

}
