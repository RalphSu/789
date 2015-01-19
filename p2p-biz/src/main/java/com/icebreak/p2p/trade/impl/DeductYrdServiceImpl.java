package com.icebreak.p2p.trade.impl;

import com.icebreak.p2p.base.BaseAutowiredService;
import com.icebreak.p2p.dataobject.BankInfo;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.ext.enums.PublicTagEnum;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.DepositDeductOrder;
import com.icebreak.p2p.integration.openapi.result.DeductDepositResult;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.DeductYrdService;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.trade.WithdrawalService;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.ws.enums.CertTypeEnum;
import com.icebreak.p2p.ws.enums.UserTypeEnum;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.message.common.funds.DeductDepositApplyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DeductYrdServiceImpl extends BaseAutowiredService implements DeductYrdService {
	@Autowired
	protected WithdrawalService	withdrawalService;
	@Autowired
	protected TradeService		tradeService;
	
	@Override
	@Transactional
	public DeductDepositResult deductDeposit(DepositDeductOrder deductOrder) throws Exception {
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoDAO.queryByUserBaseId(userBaseId);
		DeductDepositResult baseResult = new DeductDepositResult();
		BankInfo bankInfo = null;
		
		if (UserTypeEnum.GR.code().equals(userBaseInfo.getType())) {//个人用户代扣参数设置
		
			deductOrder.setPublicTagEnum(PublicTagEnum.PERSONAL);
			deductOrder.setCertType(CertTypeEnum.Identity_Card);
			
		}
		if (UserTypeEnum.JG.code().equals(userBaseInfo.getType())) {//机构用户代扣参数设置
			InstitutionsInfoDO institutionsInfo = institutionsInfoDAO.query(userBaseId);
			deductOrder.setPublicTagEnum(PublicTagEnum.PUBLIC);//对公代扣
		}
		bankInfo = bankBaseService.query19BankInfo(deductOrder.getBankCode());
		//可以做限额，代码删掉了，若有需求查SVN历史记录
        OpenApiContext apiContext = this.getOpenApiContext();
		deductOrder.setUserId(userBaseInfo.getAccountId());
		deductOrder.setOrderNo(apiContext.getOpenApiBizNo());
		//业务号
		logger.info("调用openApi代扣充值接口，入参：{" + "cerNo:" + deductOrder.getCertNo() + ",bankAccountNo:"
					+ deductOrder.getBankAccountNo() + ",bankAccountName:"
					+ deductOrder.getAccountName() + ",bankCode:" + deductOrder.getBankCode()
					+ ",publicTag:" + deductOrder.getPublicTagEnum() + ",amount:"
					+ deductOrder.getAmount());

		logger.info("更新本地充值流水，入参：{}", deductOrder);
		RechargeFlow rechargeFlow = this.buildRechargeFlow(deductOrder);
		long flowId = tradeService.addRechargeFlow(rechargeFlow);
		rechargeFlow.setFlowId(flowId);
		DeductDepositApplyResponse response = deductDepositService.applyDeductDeposit(deductOrder, apiContext);
		if (StringUtil.equalsIgnoreCase("T",response.getSuccess())) {
		} else {
			if ("BIZ_PROCESSING".equals(response.getResultCode())) {
			} else {
				if ("ICBC".equals(deductOrder.getBankCode())
						&& (StringUtil.indexOf(baseResult.getMemo(), "您输入的证件号、姓名或手机号有误(6630021)") > -1 || StringUtil
						.indexOf(baseResult.getMessage(), "您输入的证件号、姓名或手机号有误(6630021)") > -1)) {
					try {
						String cardOne = deductOrder.getCertNo().substring(0, 6)
								+ deductOrder.getCertNo().substring(8, 17);//一代身份证
						deductOrder.setCertNo(cardOne);
						deductOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
						response = deductDepositService.applyDeductDeposit(deductOrder,
								this.getOpenApiContext());
					} catch (Exception e) {
						logger.error("二次代扣异常", e);
					}
				}
			}
		}
		//		rechargeFlow.setFlowId(flowId);
		//		rechargeFlow.setDepositId(deductDepositResult.getDepositId());
		//		tradeService.updateRechargeFlow(rechargeFlow);
		logger.info("代扣充值结果:" + baseResult);
		boolean deductReturn = false;
		if (StringUtil.equalsIgnoreCase("T", response.getSuccess())) {
			if (StringUtil.equalsIgnoreCase("EXECUTE_SUCCESS", response.getResultCode())) {
				rechargeFlow.setStatus(1);
			} else if(StringUtil.equalsIgnoreCase("BIZ_PROCESSING", response.getResultCode())) {
				rechargeFlow.setStatus(2);
				baseResult.setMessage("快捷申请成功");
			}
			rechargeFlow.setOutBizNo(response.getDepositId());
			baseResult.setSuccess(true);
			baseResult.setMessage("快捷充值成功");
		} else {
			baseResult.setSuccess(false);
			rechargeFlow.setStatus(0);
			rechargeFlow.setOutBizNo(response.getDepositId());
			baseResult.setMessage(baseResult.getMessage() + "(" + baseResult.getMemo() + ")");
		}

		try {
			tradeService.updateStatus(rechargeFlow);
		} catch (Exception e) {
			logger.error(baseResult.getMessage(), e);
		}
		return baseResult;
	}
	
	private RechargeFlow buildRechargeFlow(DepositDeductOrder deductOrder) {
		RechargeFlow rechargeFlow = new RechargeFlow();
		rechargeFlow.setLocalNo(deductOrder.getOrderNo());
		rechargeFlow.setAmount(deductOrder.getAmount().getCent());
		rechargeFlow.setBankAccountNo(deductOrder.getBankAccountNo());
		rechargeFlow.setBankAcountName(deductOrder.getBankAccountName());
		int deductReturn = 2;
		rechargeFlow.setPayTime(new Date());
		rechargeFlow.setBankName(deductOrder.getBankName());
		rechargeFlow.setPayType(DivisionTypeEnum.DEPOSIT.getCode());
		rechargeFlow.setPayMemo("代扣充值");
		rechargeFlow.setStatus(deductReturn);
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		rechargeFlow.setUserId(sessionLocal.getUserId());
		rechargeFlow.setYjfAccountId(sessionLocal.getAccountId());
		return rechargeFlow;
	}
}
