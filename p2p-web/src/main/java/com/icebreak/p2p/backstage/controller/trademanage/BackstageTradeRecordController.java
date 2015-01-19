package com.icebreak.p2p.backstage.controller.trademanage;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.front.controller.trade.download.DataMap;
import com.icebreak.p2p.integration.openapi.info.DepositInfo;
import com.icebreak.p2p.integration.openapi.info.QueryWithdrawInfo;
import com.icebreak.p2p.integration.openapi.order.QueryDepositOrder;
import com.icebreak.p2p.integration.openapi.order.WithdrawQueryOrder;
import com.icebreak.p2p.integration.openapi.result.DepositQueryResult;
import com.icebreak.p2p.integration.openapi.result.WithdrawQueryResult;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.p2p.ws.enums.PayTypeEnum;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "backstage")
@Controller
public class BackstageTradeRecordController extends BaseAutowiredController {

	String	PAGE_PATH	= "/backstage/trade/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "startTime", "endTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "minAmountIn", "maxAmountIn" };
	}
	
	@RequestMapping(value = "queryWithdrawalsRecord")
	public String queryWithdraw(WithdrawQueryOrder withdrawQueryOrder, PageParam pageParam,
								Model model) throws ParseException {
		withdrawQueryOrder.setCurrPage(pageParam.getPageNo());
		withdrawQueryOrder.setPageSize(pageParam.getPageSize());
		if (StringUtil.isNotEmpty(withdrawQueryOrder.getUserId())) {
			String userName = withdrawQueryOrder.getUserId();
			model.addAttribute("userName", withdrawQueryOrder.getUserId());
			UserBaseInfoDO userBaseInfo = null;
			try {
				userBaseInfo = userBaseInfoManager.queryByUserName(userName, 0);
				String accountId = userBaseInfo.getAccountId();
				withdrawQueryOrder.setUserId(accountId);
			} catch (Exception e) {
				logger.error("queryWithdraw", e);
				logger.error("查询用户失败", e);
			}
			
		}
		
		//withdraw.query 无UserId有查出其他平台的数据的风险
		WithdrawQueryResult queryResult = new WithdrawQueryResult();
		if (StringUtil.isNotEmpty(withdrawQueryOrder.getUserId())
			|| StringUtil.isNotEmpty(withdrawQueryOrder.getPayNo())) {
			queryResult = this.withdrawQueryService.queryWithdrawService(withdrawQueryOrder);
		}
		
		List<QueryWithdrawInfo> infos = queryResult.getData();
		int totalSize = (int) queryResult.getSize();
		int start = PageParamUtil.startValue(totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		Page<QueryWithdrawInfo> page = new Page<QueryWithdrawInfo>(start, totalSize,
			pageParam.getPageSize(), infos);
		model.addAttribute("page", page);
		if (infos == null) {
			model.addAttribute("size", 0);
		}
		model.addAttribute("queryOrder", withdrawQueryOrder);
		return PAGE_PATH + "WithdrawalsRecord.vm";
	}
	
	private WithdrawQueryOrder setWithdrawalsQueryOrder(WithdrawQueryOrder queryOrder) {
		if (queryOrder.getStartTime() == null) {
			queryOrder.setStartTime(DateUtil.getThreeMonthDay(new Date()));
		}
		if (queryOrder.getEndTime() == null) {
			queryOrder.setEndTime(new Date());
		}
		return queryOrder;
	}
	
	@ResponseBody
	@RequestMapping("syschLocalWithdrawFlow")
	public Object syschLocalWithdrawFlow(WithdrawQueryOrder withdrawQueryOrder, Model model)
																							throws Exception {
		JSONObject json = new JSONObject();
		try {
			
			WithdrawQueryResult withdrawalsPreQueryResult = this.withdrawQueryService
				.queryWithdrawService(this.setWithdrawalsQueryOrder(withdrawQueryOrder));
			withdrawQueryOrder.setPageSize((int) withdrawalsPreQueryResult.getSize());
			WithdrawQueryResult withdrawalsQueryResult = this.withdrawQueryService
				.queryWithdrawService(this.setWithdrawalsQueryOrder(withdrawQueryOrder));
			List<QueryWithdrawInfo> infos = withdrawalsQueryResult.getData();
			if (infos != null)
				for (QueryWithdrawInfo info : infos) {
					RechargeFlow flow = rechargeFlowService.queryByOutBizNo(info.getOutBizNo());
					if (flow != null) {
						int status = 0;
						if ("成功".equals(info.getStatus()) || "true".equals(info.getStatus())) {
							status = 1;
						} else if ("失败".equals(info.getStatus())
									|| "false".equals(info.getStatus())) {
							status = 0;
						} else {
							status = 2;
						}
						if (status != flow.getStatus()) {
							flow.setStatus(status);
							rechargeFlowService.updateStatus(flow);
						}
					} else {
						int status = 0;
						if ("成功".equals(info.getStatus()) || "true".equals(info.getStatus())) {
							status = 1;
						} else if ("失败".equals(info.getStatus())
									|| "false".equals(info.getStatus())) {
							status = 0;
						} else {
							status = 2;
						}
						RechargeFlow rechargeFlow = new RechargeFlow();
//						if (info.getAmountIn().getCent() / 100 > 999999999) {
//							rechargeFlow.setAmount(999999999);
//						} else {
//							rechargeFlow.setAmount(info.getAmountIn().getCent() / 100);
//						}
						rechargeFlow.setBankAccountNo(info.getAccountNo());
						rechargeFlow.setBankAcountName(info.getAccountName());
						rechargeFlow.setOutBizNo(info.getOutBizNo());
//						rechargeFlow.setPayTime(info.getPayTime());
						String payType = PayTypeEnum.WITHDRAW.code();
						rechargeFlow.setPayType(payType);
						rechargeFlow.setStatus(status);
						rechargeFlow.setBankName(info.getBankName());
						UserBaseInfoDO user = userBaseInfoManager
							.queryByAccountId(info.getUserId());
						if (user != null) {
							rechargeFlow.setUserId(user.getUserId());
						} else {
							logger.error("未找到充值的" + AppConstantsUtil.getProductName() + "账户");
							rechargeFlow.setUserId(0);
						}
						rechargeFlow.setYjfAccountId(info.getUserId());
						System.out.println("订单号：" + rechargeFlow.getOutBizNo() + "金额:"
											+ rechargeFlow.getAmount());
						rechargeFlowService.addRechargeFlow(rechargeFlow);
					}
				}
		} catch (Exception e) {
			logger.error("queryWithdraw", e);
			json.put("code", "0");
			json.put("message", "同步出错！");
		}
		json.put("code", "1");
		json.put("message", "已成功同步！");
		return json;
	}
	

	@ResponseBody
	@RequestMapping("syschLocalRechargeFlow")
	public Object syschLocalRechargeFlow(QueryDepositOrder queryOrder, Model model)
																					throws Exception {
		JSONObject json = new JSONObject();
		try {
			DepositQueryResult queryPreResult = this.depositQueryService
				.depositQueryService(queryOrder);
			logger.info("同步充值记录,入参是：{}", queryOrder);
//			queryOrder.setPageSize((int) queryPreResult.getCount());
			DepositQueryResult queryResult = depositQueryService.depositQueryService(queryOrder);
			List<DepositInfo> infos = queryResult.getData();
			for (DepositInfo info : infos) {
				RechargeFlow flow = rechargeFlowService.queryByOutBizNo(info.getOutBizNo());
				if (flow != null) {
					int status = 0;
					if ("DEPOSITED".equals(info.getStatus()) || "SUCCESS".equals(info.getStatus())
						|| "true".equals(info.getStatus())) {
						status = 1;
					} else if ("FAILURE".equals(info.getStatus())
								|| "CANCELED".equals(info.getStatus())
								|| "false".equals(info.getStatus())) {
						status = 0;
					} else {
						status = 2;
					}
					if (status != flow.getStatus()) {
						flow.setStatus(status);
						rechargeFlowService.updateStatus(flow);
					}
				} else {
					int status = 0;
					if ("DEPOSITED".equals(info.getStatus()) || "SUCCESS".equals(info.getStatus())
						|| "true".equals(info.getStatus())) {
						status = 1;
					} else if ("FAILURE".equals(info.getStatus())
								|| "CANCELED".equals(info.getStatus())
								|| "false".equals(info.getStatus())) {
						status = 0;
					} else {
						status = 2;
					}
					RechargeFlow rechargeFlow = new RechargeFlow();
					rechargeFlow.setBankAccountNo(info.getAccountNo());
					rechargeFlow.setOutBizNo(info.getOutBizNo());
					rechargeFlow.setStatus(status);
					rechargeFlow.setBankName(info.getBankName());
					UserBaseInfoDO user = userBaseInfoManager.queryByAccountId(info.getUserId());
					if (user != null) {
						rechargeFlow.setUserId(user.getUserId());
					} else {
						logger.error("未找到充值的" + AppConstantsUtil.getProductName() + "账户");
						rechargeFlow.setUserId(0);
					}
					rechargeFlow.setYjfAccountId(info.getUserId());
					System.out.println("订单号：" + rechargeFlow.getOutBizNo() + "金额:"
										+ rechargeFlow.getAmount());
					rechargeFlowService.addRechargeFlow(rechargeFlow);
				}
			}
			json.put("code", "1");
			json.put("message", "已成功同步！");
		} catch (Exception e) {
			logger.error("changeStatus", e);
			json.put("code", "0");
			json.put("message", "同步出错！");
		}
		return json;
	}
	
	/**
	 * 查询代扣充值记录
	 */
	@RequestMapping("deductTopUp")
	public String deductRecord(QueryTradeOrder queryTradeOrder, PageParam pageParam, String type,
								HttpSession session, Model model) throws Exception {
		String path = PAGE_PATH + "deductRecord.vm";

		if (type == null || "".equals(type)) {
			String accountId = SessionLocalManager.getSessionLocal().getAccountId();
			queryTradeOrder.setAccountId(accountId);
		}
		queryTradeOrder.setPayType("DEDUCT");
		Page<RechargeFlow> page = null;
		try {
			page = tradeService.getFlow(queryTradeOrder, pageParam);
            DataMap.setOjbMap("DEDUCT", page.getResult());
		} catch (Exception e) {
			logger.error("充值记录查询失败！", e);
			model.addAttribute("faild", "充值记录查询失败！");
			return path;
		}
		model.addAttribute("page", page);
		return path;
	}
	
	@RequestMapping("loadBankCard")
	public String loadBankCard(Model model) throws Exception {
		List<BankBasicInfo> bankList = new ArrayList<BankBasicInfo>();
		List<BankBasicInfo> bankListOpenApi = bankDataService.getDeductBank();
		if (bankListOpenApi != null && bankListOpenApi.size() > 0) {
			List<String> listBankCode = new ArrayList<String>();
			listBankCode.add("ABC");//农业银行
			listBankCode.add("ICBC");//中国工商银行
			listBankCode.add("CCB");//中国建设银行
			listBankCode.add("CEB");//中国光大银行
			listBankCode.add("CIB");//兴业银行
			listBankCode.add("CMBC");//民生银行
			listBankCode.add("CITIC");//中信银行
			listBankCode.add("CMB");//招商银行
			for (BankBasicInfo bank : bankListOpenApi) {
				if (listBankCode.contains(bank.getBankCode())) {
					bankList.add(bank);
				}
			}
		} else {
			List<BankBasicInfo> bankListPPM = bankDataService.getBankBasicInfos();//获取所有银行
			List<String> listBankCode = new ArrayList<String>();
			listBankCode.add("ABC");//农业银行
			listBankCode.add("ICBC");//中国工商银行
			listBankCode.add("CCB");//中国建设银行
			listBankCode.add("CEB");//中国光大银行
			listBankCode.add("CIB");//兴业银行
			listBankCode.add("CMBC");//民生银行
			listBankCode.add("CQRCB");//重庆农村商业银行
			listBankCode.add("CITIC");//中信银行
			listBankCode.add("CMB");//招商银行
			for (BankBasicInfo bank : bankListPPM) {
				if (listBankCode.contains(bank.getBankCode())) {
					bankList.add(bank);
				}
			}
		}
		model.addAttribute("bankList", bankList);
		return PAGE_PATH + "loadBankCard.vm";
	}
	
	@ResponseBody
	@RequestMapping("loadBankCard/validateBankCardTool")
	public Object validateBankCardTool(String idCardNo, String bankOpenName, String bankCardNo,
										String bankType, String bankKey, String bankProvince,
										String bankCity, String bankAddress, Model model)
																							throws Exception {
		JSONObject json = new JSONObject();
		try {
			OpeningBankQueryOrder validationCardInfo = new OpeningBankQueryOrder();
			
			validationCardInfo.setAccountName(bankOpenName);
			validationCardInfo.setCardNumber(bankCardNo);
			validationCardInfo.setBankCode(bankType);
			//"C", "信用卡类型" \"D", "借记卡类型" 平台只接受借记卡
			validationCardInfo.setCardType(CardTypeEnum.JJ);
			String cardNo = idCardNo;//二代身份证
			String cardOne = cardNo.substring(0, 6) + cardNo.substring(8, 17);//一代身份证
			validationCardInfo.setCertNo(cardNo);
			logger.info("绑卡业务申请号：,卡号：" + bankCardNo);
			if (YrdConstants.BankCodes.filteredBankCodes.indexOf(bankType) < 0) {
				//调用openAPI 验证银行信息
				BankInfoResult bankInfoResult = this.openingBankService.findCardByCardNo(
					validationCardInfo);
				if (bankInfoResult.getCardInfo() == null) {
					json.put("code", 0);
					json.put("message", "验证失败");
					return json;
				}
			}
			json.put("code", 1);
			json.put("message", "验证成功");
		} catch (Exception e) {
			logger.error("validateBankCardTool", e);
			json.put("code", 0);
			json.put("message", "验证失败");
		}
		return json;
	}
}
