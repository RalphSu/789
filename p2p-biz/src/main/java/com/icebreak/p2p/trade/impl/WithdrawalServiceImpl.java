package com.icebreak.p2p.trade.impl;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.activity.IActivityService;
import com.icebreak.p2p.activity.enums.ActivityReturnEnum;
import com.icebreak.p2p.base.OpenApiBaseService;
import com.icebreak.p2p.common.services.BankBaseService;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.ext.enums.OptionEnum;
import com.icebreak.p2p.integration.openapi.WithdrawService;
import com.icebreak.p2p.integration.openapi.order.WithdrawOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawResult;
import com.icebreak.p2p.service.openingbank.order.OpeningBankByDisNameAndBankIdOrder;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.RechercheFlowOrder;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.trade.WithdrawalService;
import com.icebreak.p2p.user.InstitutionsInfoManager;
import com.icebreak.p2p.user.PersonalInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.SysCommand;
import com.icebreak.p2p.ws.enums.BooleanEnum;
import com.icebreak.p2p.ws.enums.PayTypeEnum;
import com.icebreak.util.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class WithdrawalServiceImpl extends OpenApiBaseService implements WithdrawalService {
	
	@Autowired
	protected IActivityService			iActivityService;
	@Autowired
	protected PersonalInfoManager		personalInfoManager;
	@Autowired
	protected InstitutionsInfoManager	institutionsInfoManager;
	@Autowired
	protected TradeService				tradeService;
	@Autowired
	protected UserBaseInfoManager		userBaseInfoManager;
	
	@Autowired
	protected BankBaseService			bankBaseService;
	
	@Autowired
	protected WithdrawService			withdrawService;
	
	private double						QUOTA	= 5000000.00;		//默认为500万为单笔限额，超过则走人工审核提现
																	
	@Override
	public JSONObject applyWithdrawOpenApi(String amount, String coupons) throws Exception {
		JSONObject json = new JSONObject();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String strDate = format.format(date);
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		long userId = SessionLocalManager.getSessionLocal().getUserId();
		RechercheFlowOrder rechercheFlowOrder = new RechercheFlowOrder();
		rechercheFlowOrder.setUserId(userId);
		List<Integer> status = new ArrayList<Integer>();
		status.add(1);
		status.add(2);
		rechercheFlowOrder.setStatus(status);
		rechercheFlowOrder.setPayType(PayTypeEnum.WITHDRAW.code());//提现
		rechercheFlowOrder.setStartTime(strDate + " 00:00:00");
		rechercheFlowOrder.setEndTime(strDate + " 23:59:59");
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		double withdrawalAmount = 0.0;
		if ("GR".equals(userBaseInfo.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			rechercheFlowOrder.setBankCardNo(personalInfo.getBankCardNo());
			withdrawalAmount = tradeService.getUserSumAmount(rechercheFlowOrder);//绑定的此银行卡已划出金额
			BankInfo bankInfo = bankBaseService.query19BankInfo(personalInfo.getBankType());
			WithdrawOrder pWithdrawOrder = this.setWithdrawalPerson(personalInfo, amount, coupons);
			logger.info("个人提现,调用openAPI接口，入参：amount=" + pWithdrawOrder.getAmount() + ",accountId="
						+ pWithdrawOrder.getUserId() + ",bankCardNo="
						+ pWithdrawOrder.getBankAccountNo() + ",bankName="
						+ pWithdrawOrder.getBankAccountName() + ",bankCode="
						+ pWithdrawOrder.getBankCode());
			if (bankInfo != null) {//获取银行
				QUOTA = bankInfo.getSingleWithdrawalLimit();//银行限额
				double lastAmount = bankInfo.getOddWithdrawalLimit() - withdrawalAmount;
				if (Double.parseDouble(amount) > lastAmount) {
					json.put("code", 0);
					json.put(
						"message",
						"您划出超过银行限额！您绑定的" + bankInfo.getBankName() + "单日限额为"
								+ bankInfo.getOddWithdrawalLimit() + "元。今日还可划出金额为"
								+ nf.format(lastAmount) + "元");
					return json;
				}
				if (Double.parseDouble(amount) <= QUOTA) {
					json = this.applyWithdraw(pWithdrawOrder, coupons);
				} else {
					json.put("code", 0);
					json.put("message", "单笔划出超过银行限额！您绑定的" + bankInfo.getBankName() + "的单笔限额为"
										+ bankInfo.getSingleWithdrawalLimit() + "元,单日限额为"
										+ bankInfo.getOddWithdrawalLimit() + "元");
				}
			} else {
				json.put("code", 0);
				json.put("message", "您绑定的银行暂不支持划出,请重新绑定!");
			}
		} else if ("JG".equals(userBaseInfo.getType())) {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			rechercheFlowOrder.setBankCardNo(institutionsInfo.getBankCardNo());
			withdrawalAmount = tradeService.getUserSumAmount(rechercheFlowOrder);//绑定的此银行卡已划出金额
			BankInfo bankInfo = bankBaseService.query19BankInfo(institutionsInfo.getBankType());
			WithdrawOrder iWithdrawOrder = this.setWithdrawalInstitution(institutionsInfo, amount,
				coupons);
			logger.info("个人提现,调用openAPI接口，入参：amount=" + iWithdrawOrder.getAmount() + ",accountId="
						+ iWithdrawOrder.getUserId() + ",bankCardNo="
						+ iWithdrawOrder.getBankAccountNo() + ",bankName="
						+ iWithdrawOrder.getBankAccountName() + ",bankCode="
						+ iWithdrawOrder.getBankCode());
			if (bankInfo != null) {//获取银行
				QUOTA = bankInfo.getSingleWithdrawalLimit();//银行限额
				double lastAmount = bankInfo.getOddWithdrawalLimit() - withdrawalAmount;
				if (Double.parseDouble(amount) > lastAmount) {
					json.put("code", 0);
					json.put(
						"message",
						"您划出超过银行限额！您绑定的" + bankInfo.getBankName() + "单日限额为"
								+ bankInfo.getOddWithdrawalLimit() + "元。今日还可划出金额为"
								+ nf.format(lastAmount) + "元");
					return json;
				}
				if (Double.parseDouble(amount) <= QUOTA) {
					json = this.applyWithdraw(iWithdrawOrder, coupons);
				} else {
					json.put("code", 0);
					json.put("message", "单笔划出超过银行限额！您绑定的" + bankInfo.getBankName() + "的单笔限额为"
										+ bankInfo.getSingleWithdrawalLimit() + "元,单日限额为"
										+ bankInfo.getOddWithdrawalLimit() + "元");
				}
			} else {
				json.put("code", 0);
				json.put("message", "您绑定的银行暂不支持划出,请重新绑定!");
			}
		}
		return json;
	}
	
	/**
	 * 
	 * @param withdrawInfo
	 * @param service
	 * @param coupons 提现时是否使用抵消提现费用 YES使用
	 * @return
	 * @throws Exception
	 */
	public JSONObject applyWithdraw(WithdrawOrder withdrawOrder, String coupons) throws Exception {
		
		withdrawOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
		OpeningBankByDisNameAndBankIdOrder nameAndBankIdOrder = new OpeningBankByDisNameAndBankIdOrder();
		nameAndBankIdOrder.setDistrictName(withdrawOrder.getCityName());
		nameAndBankIdOrder.setBankId(withdrawOrder.getBankCode());
		fillBankLasalle(withdrawOrder, nameAndBankIdOrder);
		WithdrawResult withdrawResult = withdrawService.applyWithdrawService(withdrawOrder,
			this.getOpenApiContext());
		
		JSONObject json = new JSONObject();
		
		if (withdrawResult.isSuccess()) {
			json.put("code", 1);
			json.put("message", "提现成功");
		} else {
			json.put("code", 0);
			json.put("message", "提现失败,");
		}
		String bizNumber = withdrawOrder.getOrderNo();
		if (BooleanEnum.YES.code().equals(coupons)) {
			String giftName = null;
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("userId", SessionLocalManager.getSessionLocal().getUserId());
			conditions.put("giftType", PayTypeEnum.WITHDRAW.code());
			List<Integer> status = new ArrayList<Integer>();
			status.add(1);
			conditions.put("status", status);
			conditions.put("limitStart", 0);
			conditions.put("pageSize", 999);
			List<ActivityGiftCount> giftCountList = iActivityService
				.getActivityGiftCountListByConditions(conditions);
			if (giftCountList != null && giftCountList.size() > 0) {
				giftName = giftCountList.get(0).getGiftName();
			}
			GiftUseRecord giftUseRecord = new GiftUseRecord();
			giftUseRecord.setBizNumber(bizNumber);
			giftUseRecord.setGiftName(giftName);
			giftUseRecord.setNote("提现时使用抵消提现费用");
			giftUseRecord.setStatus(2); //0失败//1成功//2待处理
			giftUseRecord.setUseAmount(1);
			giftUseRecord.setUserId(SessionLocalManager.getSessionLocal().getUserId());
			giftUseRecord.setUserName(SessionLocalManager.getSessionLocal().getUserName());
			giftUseRecord.setUseTime(new Date());
			try {
				ActivityReturnEnum returnEnum = iActivityService.addGiftUseRecord(giftUseRecord);
				if (ActivityReturnEnum.EXECUTE_SUCCESS == returnEnum) {
					logger.info("新增提现优惠券记录成功");
				}
			} catch (Exception e) {
				logger.error("新增提现优惠券记录失败", e);
			}
		}
		logger.info("更新本地提现流水，入参：{}", withdrawOrder);
		RechargeFlow rechargeFlow = this.buildRechargeFlow(withdrawOrder, withdrawResult);
		tradeService.addRechargeFlow(rechargeFlow);
		logger.info("更新本地提现流水，结束！");
		logger.info("调用openApi代扣提现接口，出参：{}", withdrawResult);
		logger.info("提现结果:" + withdrawResult);
		return json;
	}
	
	/**
	 * @param withdrawOrder
	 * @param nameAndBankIdOrder
	 */
	protected void fillBankLasalle(WithdrawOrder withdrawOrder,
									OpeningBankByDisNameAndBankIdOrder nameAndBankIdOrder) {
		BankInfoResult bankInfoResult = openingBankService.getOpeningBankByDistrictNameAndBankId(
			nameAndBankIdOrder);
		withdrawOrder.setBankCnapsNo(bankInfoResult.getBankInfo().getChilds().get(0)
			.getBankLasalle());
	}
	
	/**
	 * 个人提现参数设置
	 * @param personalInfo
	 * @param amount
	 * @return
	 */
	private WithdrawOrder setWithdrawalPerson(PersonalInfoDO personalInfo, String amount,
												String coupons) {
		
		WithdrawOrder order = new WithdrawOrder();
		order.setUserId(personalInfo.getAccountId());
		order.setAmount(new Money(amount));
		order.setBankAccountNo(personalInfo.getBankCardNo());
		order.setBankAccountName(personalInfo.getBankOpenName());
		order.setProvName(personalInfo.getBankProvince());
		order.setCityName(personalInfo.getBankCity());
		order.setBankCode(personalInfo.getBankType());
		order.setPublicTag(OptionEnum.N);
		return order;
	}
	
	/**
	 * 机构用户提现参数设置
	 * @param institutionsInfo
	 * @param amount
	 * @return
	 */
	public WithdrawOrder setWithdrawalInstitution(InstitutionsInfoDO institutionsInfo,
													String amount, String coupons) {
		
		WithdrawOrder order = new WithdrawOrder();
		order.setUserId(institutionsInfo.getAccountId());
		order.setAmount(new Money(amount));
		order.setBankAccountNo(institutionsInfo.getBankCardNo());
		order.setBankAccountName(institutionsInfo.getBankOpenName());
		order.setProvName(institutionsInfo.getBankProvince());
		order.setCityName(institutionsInfo.getBankCity());
		order.setBankCode(institutionsInfo.getBankType());
		order.setPublicTag(OptionEnum.Y);
		return order;
	}
	
	/**
	 * 设置本地流水参数
	 * @param deductInfo
	 * @param openAPIResult
	 * @return
	 */
	private RechargeFlow buildRechargeFlow(WithdrawOrder withdrawOrder,
											WithdrawResult withdrawResult) {
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = null;
		try {
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		} catch (Exception e1) {
			logger.error("代扣充值流水处理获取用户信息失败！", e1);
		}
		String bankType = null;
		if (SysCommand.GR.equals(userBaseInfo.getType())) {
			PersonalInfoDO personalInfo = null;
			try {
				personalInfo = personalInfoManager.query(userBaseId);
			} catch (Exception e) {
				logger.error("代扣充值流水处理获取用户信息失败！", e);
			}
			bankType = personalInfo.getBankType();
		}
		if (SysCommand.JG.equals(userBaseInfo.getType())) {
			InstitutionsInfoDO institutionsInfo = null;
			try {
				institutionsInfo = institutionsInfoManager.query(userBaseId);
			} catch (Exception e) {
				logger.error("代扣充值流水处理获取用户信息失败！", e);
			}
			bankType = institutionsInfo.getBankType();
		}
		RechargeFlow rechargeFlow = new RechargeFlow();
		rechargeFlow.setAmount(withdrawOrder.getAmount().getAmount().doubleValue());
		rechargeFlow.setBankAccountNo(withdrawOrder.getBankAccountNo());
		rechargeFlow.setBankAcountName(withdrawOrder.getBankAccountName());
		
		int deductReturn = 0;
		if (withdrawResult.isSuccess()) {
			deductReturn = 2;
			
			rechargeFlow.setOutBizNo(withdrawResult.getOutBizNo());
			rechargeFlow.setPayTime(new Date());
			rechargeFlow.setBankName(bankBaseService.query19BankInfo(withdrawOrder.getBankCode())
				.getBankName());
		} else {
			rechargeFlow.setPayTime(new Date());
			rechargeFlow.setBankName(bankBaseService.query19BankInfo(bankType).getBankName());
			rechargeFlow.setOutBizNo(withdrawOrder.getOrderNo());
		}
		rechargeFlow.setPayType(PayTypeEnum.WITHDRAW.code());
		rechargeFlow.setStatus(deductReturn);
		rechargeFlow.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		rechargeFlow.setYjfAccountId(SessionLocalManager.getSessionLocal().getAccountId());
		return rechargeFlow;
	}
	
}
